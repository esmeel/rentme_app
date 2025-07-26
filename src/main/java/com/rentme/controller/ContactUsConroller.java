package com.rentme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentme.data_transfer_objects.contacUsDTO;
import com.rentme.repository.ContactUsRepository;
import com.rentme.service.ContactUsService;

@RestController
@RequestMapping("/api/contact-us")
public class ContactUsConroller {

  ContactUsRepository contactUsRepository;
  ContactUsService contactUsService;

  public ContactUsConroller(ContactUsRepository contactUsRepository,
      ContactUsService contactUsService) {

    this.contactUsRepository = contactUsRepository;
    this.contactUsService = contactUsService;

  }

  @PostMapping("/send-message")
  public ResponseEntity<?> messages(@RequestBody contacUsDTO dto) {
    System.err.println("Message: " + dto.toString());
    contactUsService.receiveMessage(dto);

    return ResponseEntity.ok("Message sent");
  }

}
