
package com.rentme.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class AdminAuthController {



  @GetMapping("/admin/login")
  public String adminLogin() {
    return "admin_login"; // هذا يعيد ملف admin_login.html من مجلد templates
  }


  @GetMapping("/identity-requests")
  public String showPendingRequests(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("👤 Logged-in: " + auth.getName());
    System.out.println("🎖 Roles: " + auth.getAuthorities());
    return "identity_requests";
  }

}
