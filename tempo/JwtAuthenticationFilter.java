// package com.rentme.security;

// import java.io.IOException;

// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain)
//             throws ServletException, IOException {

//         String path = request.getRequestURI();
//         System.out.println("FILTER ACTIVATED: " + path); // لتأكيد تنفيذ الفلتر

//         // ✅ تجاوز مسارات الصور
//         if (path.startsWith("/profile_pics/") || path.startsWith("/tools_imgs/")) {
//             System.out.println("SKIPPED JWT FILTER FOR: " + path);
//             filterChain.doFilter(request, response);
//             return;
//         }

//         // ❗ هنا المنطق الحقيقي للتحقق من JWT (يمكنك تعديله لاحقًا)
//         // حاليًا: لا يفعل شيء، فقط يمرّر الطلبات الأخرى
//         filterChain.doFilter(request, response);
//     }
// }

package com.rentme.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
                                          String path = request.getRequestURI();
     System.out.println("FILTER ACTIVATED: " + path); // لتأكيد تنفيذ الفلتر
     if (path.startsWith("/profile_pics/") || path.startsWith("/tools_imgs/")) {
                    System.out.println("SKIPPED JWT FILTER FOR: " + path);
                    filterChain.doFilter(request, response);
                    return;
                }
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            System.out.println("authHeader ====>" + authHeader);
            return;
        }

        final String token = authHeader.substring(7);
        final String userEmail = jwtUtil.extractEmail(token);

        System.out.println("🟢 Token: " + token);
        System.out.println("🟢 Email from token: " + userEmail);

        if (userEmail != null) {
            /*
            Object currentAuth = SecurityContextHolder.getContext().getAuthentication();
            */
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

            boolean alreadyAuthenticated = currentAuth != null &&
                    ((org.springframework.security.core.Authentication) currentAuth).isAuthenticated() &&
                    !(currentAuth.getPrincipal() instanceof String &&
                      currentAuth.getPrincipal().equals("anonymousUser"));

            if (!alreadyAuthenticated) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                System.out.println("📛 userDetails class: " + userDetails.getClass().getName());

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("✅ Set user in SecurityContext: " +
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().getName());
                } else {
                    System.out.println("❌ Token validation failed");
                }
            } else {
                System.out.println("ℹ️ User already authenticated, skipping...");
            }
        }
        //Todo: // مؤقتًا: السماح بمرور أي طلب آخر


        filterChain.doFilter(request, response);
    }
}
