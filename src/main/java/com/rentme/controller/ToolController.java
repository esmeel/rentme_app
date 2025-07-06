package com.rentme.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Globals.IpLocal;
import com.rentme.data_transfer_objects.ToolResponseDTO;
import com.rentme.model.Tool;
import com.rentme.model.User;
import com.rentme.repository.ToolRepository;
import com.rentme.security.JwtUtil;
import com.rentme.service.ToolService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tools")
public class ToolController {
    private final ToolRepository toolRepository;

    @Autowired
    public ToolController(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Autowired
    private ToolService toolService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private com.rentme.repository.UserRepository userRepository;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addTool(HttpServletRequest request,
            @RequestPart("file") MultipartFile file) {
        try {
            String token = jwtUtil.extractTokenFromRequest(request);
            String email = jwtUtil.extractUsername(token);
            User owner = userRepository.findByEmail(email);
            if (owner == null)
                return ResponseEntity.badRequest().body("User not found");

            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String powerType = request.getParameter("powerType");
            String toolCondition = request.getParameter("toolCondition");
            String accessories = request.getParameter("accessories");
            boolean accessoriesRequired = Boolean.parseBoolean(request.getParameter("accessoriesRequired"));
            boolean available = true;
            int riskLevel = Integer.parseInt(request.getParameter("riskLevel"));

            // حفظ الصورة
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            String uploadDir = "uploads/tools_imgs";
            Path path = Paths.get(System.getProperty("user.dir"), uploadDir, filename);

            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Tool tool = new Tool();
            tool.setName(name);
            tool.setDescription(description);
            tool.setPrice(price);
            tool.setPowerType(powerType);
            tool.setToolCondition(toolCondition);
            tool.setAccessories(accessories);
            tool.setAccessoriesRequired(accessoriesRequired);
            tool.setRiskLevel(riskLevel);
            tool.setImageUrl(IpLocal. get() + "uploads/tools_imgs/" + filename);
            tool.setOwner(owner);
            tool.setAvailable(available);

            Tool savedTool = toolService.save(tool);
            return ResponseEntity.ok(savedTool);
        } catch (IOException | NumberFormatException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getToolById(@PathVariable Long id) {
        return toolRepository.findById(id)
                .map(tool -> ResponseEntity.ok(new ToolResponseDTO(tool)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{toolId}")
    public ResponseEntity<?> deleteTool(@PathVariable Long toolId) {
        try {
            Tool tool = toolService.findById(toolId);
            if (tool == null) {
                return ResponseEntity.status(404).body("Tool not found");
            }
            toolService.delete(toolId);
            return ResponseEntity.ok("Tool deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting tool: " + e.getMessage());
        }
    }

    @PutMapping("/update/{toolId}")
    public ResponseEntity<?> updateTool(
            HttpServletRequest request,
            @PathVariable Long toolId,
            @RequestBody Map<String, String> data) {
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email);
        Tool tool = toolService.findById(toolId);

        if (user == null || tool == null || !tool.getOwner().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("Unauthorized or tool not found");
        }

        tool.setName(data.get("name"));
        tool.setDescription(data.get("description"));
        tool.setPrice(Double.parseDouble(data.get("price")));
        tool.setPowerType(data.get("powerType"));
        tool.setToolCondition(data.get("toolCondition"));
        tool.setAccessories(data.get("accessories"));
        tool.setAccessoriesRequired(Boolean.parseBoolean(data.get("accessoriesRequired")));
        tool.setRiskLevel(Integer.parseInt(data.get("riskLevel")));

        Tool updatedTool = toolService.save(tool);
        return ResponseEntity.ok(updatedTool);
    }

    @GetMapping("/my-tools-with-others/{ownerId}")
    public ResponseEntity<List<Tool>> getMyToolsWithOthers(@PathVariable Long ownerId) {
        List<Tool> tools = toolRepository.findByOwnerIdAndAvailableFalse(ownerId);

        return ResponseEntity.ok(tools);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTools() {
        List<Tool> tools = toolService.findAll(); // تأكّد أن هذه موجودة في ToolService
        List<ToolResponseDTO> response = tools.stream()
                .map(ToolResponseDTO::new)
                .collect(Collectors.toList());

        // response.toArray());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-owner/{ownerId}")
    public ResponseEntity<?> getToolsByOwner(@PathVariable Long ownerId) {

        List<Tool> tools = toolService.findByOwnerId(ownerId);
        List<ToolResponseDTO> response = tools.stream()
                .map(ToolResponseDTO::new)
                .collect(Collectors.toList());

        // response.toArray());
        return ResponseEntity.ok(response);

    }

}
