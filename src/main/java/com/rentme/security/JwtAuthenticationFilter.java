package com.rentme.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rentme.model.User;
import com.rentme.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        System.out.println("🔍 JwtFilter called on: " + request.getRequestURI());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Token");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final String userEmail = jwtUtil.extractEmail(token);
        System.out.println(" Token got:== " + token);
        System.out.println(" Email got:== " + userEmail);

        if (userEmail != null) {
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            boolean alreadyAuthenticated = currentAuth != null &&
                    currentAuth.isAuthenticated() &&
                    !(currentAuth.getPrincipal() instanceof String &&
                            currentAuth.getPrincipal().equals("anonymousUser"));

            if (!alreadyAuthenticated) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtUtil.validateToken(token, userDetails)) {
                    // 👇 استرجاع المستخدم من قاعدة البيانات
                    User user = userRepository.findByEmail(userEmail);
                    if (user == null) {
                        throw new RuntimeException("User not found");
                    }

                    // 👇 بناء Authorities من الدور
                    List<GrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + user.getRole()));

                    // 👇 إعداد التوثيق داخل السياق الأمني
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities);

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("✅ Set user with role: " + user.getRole());
                } else {
                    System.out.println("❌ Token validation failed");
                }
            } else {
                System.out.println("ℹ️ User already authenticated, skipping...");
            }
        }

        filterChain.doFilter(request, response);
    }
}
