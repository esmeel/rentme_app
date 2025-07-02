package com.rentme.controller;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Globals.EsIp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.rentme.data_transfer_objects.FacebookLoginRequestDTO;
import com.rentme.data_transfer_objects.GoogleLoginRequestDTO;
import com.rentme.data_transfer_objects.ToolResponseDTO;
import com.rentme.data_transfer_objects.UserResponseDTO;
import com.rentme.model.Tool;
import com.rentme.model.User;
import com.rentme.repository.UserRepository;
import com.rentme.security.JwtUtil;
import com.rentme.service.ToolService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private ToolService toolService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;
    /////

    public UserController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        System.out.println("UserController created!!");
    }

    /// Update:
    @PostMapping("/update")
    public ResponseEntity<?> updateUserProfile(
            @RequestPart("name") String name,
            @RequestPart("email") String email,
            @RequestPart("phone") String phone,
            @RequestPart("city") String city,
            @RequestPart("country") String country,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request) {
        System.out.println("🔧 Received request: " + request);

        try {

            String token = jwtUtil.extractTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Missing or invalid Authorization header");
            }
            String emailFromToken = jwtUtil.extractUsername(token);

            User user = userRepository.findByEmail(emailFromToken);
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setCity(city);
            user.setCountry(country);

            // save profile picture if provided
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get("uploads/profile_pics/");
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                String fullUrl = EsIp.theIP + "uploads/profile_pics/" + fileName;

                user.setProfilePicUrl(fullUrl);
                userRepository.save(user);
            }

            userRepository.save(user);

            return ResponseEntity.ok(user.getProfilePicUrl());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    private String saveProfileImageFromUrl(String imageUrl) {
        try {
            String extension = "jpg"; // Google profile images are usually JPEG
            String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + extension;

            Path uploadPath = Paths.get("uploads", "profile_pics");
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);

            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return EsIp.theIP + "uploads/profile_pics/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        System.out.println("--------------------------register method! ---" + user.toString());
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // System.exit(2);
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData)
            throws JsonProcessingException {
        String email = loginData.get("email");
        String password = loginData.get("password");

        User user = userRepository.findByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");

        }

        String token = jwtUtil.generateToken(email);
        UserResponseDTO userDTO = new UserResponseDTO(user); // رد مختصر بدون أدوات

        System.out.println("User to be sent back: " + userDTO.toString());
        System.out.println(new ObjectMapper().writeValueAsString(userDTO));

        return ResponseEntity.ok(Map.of("token", token, "user", userDTO));
    }

    //////////////////////
    /// google-login"
    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequestDTO request) {
        try {
            // ✅ تحقق من صحة التوكن باستخدام GoogleIdTokenVerifier
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections
                            .singletonList("462915657678-0l5vg6r0ik75a1s3hkggog7b39hu0ha4.apps.googleusercontent.com")) // Web
                                                                                                                        // client
                                                                                                                        // ID
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid ID token"));
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            User user = userRepository.findByEmail(email);
            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setName((String) payload.get("name"));
                user.setLoginProvider("google");
                user.setVerified(false);
                user.setCity("Not set");
                user.setCountry("Not set");
                user.setPhone("Not set");

                // ✅ تحميل صورة البروفايل إن وجدت
                String profilePicUrl = "";
                String pictureUrl = (String) payload.get("picture");
                if (pictureUrl != null && !pictureUrl.isEmpty()) {
                    try {
                        profilePicUrl = saveProfileImageFromUrl(pictureUrl);
                        user.setProfilePicUrl(profilePicUrl != null ? profilePicUrl : "");
                    } catch (IOException e) {
                        System.err.println("⚠️ Unable to save profile picture: " + e.getMessage());
                        user.setProfilePicUrl("");
                    }
                } else {
                    user.setProfilePicUrl("");
                }

                userRepository.save(user);
            }

            // ✅ توليد التوكن والردّ
            String token = jwtUtil.generateToken(user.getEmail());
            UserResponseDTO userDTO = new UserResponseDTO(user);

            return ResponseEntity.ok(Map.of("token", token, "user", userDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Exception: " + e.getMessage()));
        }
    }

    @PostMapping("/facebook-login")
    public ResponseEntity<?> facebookLogin(@RequestBody FacebookLoginRequestDTO request) {
        try {
            User user = userRepository.findByEmail(request.getEmail());

            if (user == null) {
                user = new User();
                user.setEmail(request.getEmail());
                user.setName(request.getName());
                user.setLoginProvider("facebook");
                user.setVerified(false);
                if (request.getPhotoUrl() != null && !request.getPhotoUrl().isEmpty()) {
                    String profilePicUrl = saveProfileImageFromUrl(request.getPhotoUrl());
                    if (profilePicUrl != null) {
                        user.setProfilePicUrl(profilePicUrl);
                    }
                }

                userRepository.save(user);
            }

            String token = jwtUtil.generateToken(user.getEmail());
            UserResponseDTO userDTO = new UserResponseDTO(user);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", userDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println();
        System.out.println("User to be sent back: " + user.toString());
        System.out.println();
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PutMapping("/fcm-token")
    public ResponseEntity<?> updateFcmToken(@RequestBody Map<String, String> body,
            HttpServletRequest request) {
        String token = body.get("token");
        String email = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest(request));

        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.err.println("User not found");
        }

        user.setFcmToken(token);
        userRepository.save(user);

        return ResponseEntity.ok().body(Map.of("message", "Token saved"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // remove "Bearer "
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        System.out.println("User to be sent back: " + user.toString());

        return ResponseEntity.ok(user);
    }

    // saved-tools
    @GetMapping("/saved-tools")
    public ResponseEntity<List<ToolResponseDTO>> getSavedTools(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<ToolResponseDTO> savedTools = user.getSavedToolsIds().stream()
                .map(toolService::findById)
                .filter(Objects::nonNull)
                .map(ToolResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(savedTools);
    }

    @PostMapping("/save-tool/{toolId}")
    public ResponseEntity<?> saveTool(HttpServletRequest request, @PathVariable Long toolId) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email);
        Tool tool = toolService.findById(toolId);

        if (user == null || tool == null) {
            return ResponseEntity.badRequest().body("User or Tool not found");
        }

        user.getSavedToolsIds().add(tool.getId());
        userRepository.save(user);
        return ResponseEntity.ok("Tool saved");
    }

    @PostMapping("/unsave-tool/{toolId}")
    public ResponseEntity<?> unsaveTool(HttpServletRequest request, @PathVariable Long toolId) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email);
        Tool tool = toolService.findById(toolId);

        if (user == null || tool == null) {
            return ResponseEntity.badRequest().body("User or Tool not found");
        }

        user.getSavedToolsIds().remove(tool.getId());
        userRepository.save(user);
        return ResponseEntity.ok("Tool unsaved");
    }

}
