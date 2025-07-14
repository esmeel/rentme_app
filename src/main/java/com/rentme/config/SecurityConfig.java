package com.rentme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rentme.security.CustomFilterExcluder;
import com.rentme.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
                                // 🔒 أولًا: حماية مسارات الأدمن
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/admin/identity-requests").hasRole("ADMIN")

                                // 🔓 ثانيًا: المسارات المفتوحة للجميع
                                .requestMatchers("/profile_pics/**", "/tools_imgs/**",
                                                "/uploads/**", "/error", "/api/tools/add",
                                                "/api/tool-reviews/**", "/api/tools/all",
                                                "/api/users/**", "/api/users/login",
                                                "/api/users/register", "/api/users/update",
                                                "/api/users/saved-tools",
                                                "/api/users/saved-tools/**",
                                                "/api/tools/by-owner/**", "/api/tools/**",
                                                "/api/tool-reviews/**", "/api/tools/update/**",
                                                "/api/users/save-tool/**",
                                                "/api/users/unsave-tool/**", "/api/user-reviews/**",
                                                "/api/fcm-token/", "/ws/chat/**", "/ws/**",
                                                "/api/chat-rooms/**", "/api/rentals/**",
                                                // "/api/rentals/respond", "/api/rentals/incoming",
                                                "/api/notification", "/api/notification/**",
                                                "/api/users/google-login",
                                                "/api/users/facebook-login",
                                                "/admin/identity-requests/**",
                                                "/api/upload/identity", "/api/upload/identity/**",
                                                "/api/identity-requests/status", // identity-requests/status
                                                "/api/identity-requests/**", /////
                                                "/api/tools/my-tools-with-others/**",
                                                "/api/schedule/**")
                                .permitAll()

                                // 🔐 باقي مسارات /api تتطلب توثيق
                                .requestMatchers("/api/**").authenticated())
                                .addFilterBefore(new CustomFilterExcluder(jwtAuthenticationFilter),
                                                UsernamePasswordAuthenticationFilter.class)

                                .httpBasic(httpBasic -> httpBasic.disable())
                                .formLogin(form -> form.loginPage("/admin/login")
                                                .defaultSuccessUrl("/admin/identity-requests", true)
                                                .permitAll())
                                .logout(logout -> logout.logoutUrl("/admin/logout")
                                                .logoutSuccessUrl("/admin/login?logout"))

                                .build();

        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
                        throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
