package com.rentme.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomFilterExcluder extends OncePerRequestFilter {

  private final JwtAuthenticationFilter jwtFilter;

  public CustomFilterExcluder(JwtAuthenticationFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    String path = request.getRequestURI();

    // ✅ تخطى JWT لأي طلب إداري
    if (path.startsWith("/admin")) {
      filterChain.doFilter(request, response);
    } else {
      jwtFilter.doFilter(request, response, filterChain);
    }
  }
}
