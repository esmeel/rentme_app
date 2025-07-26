package com.rentme.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.rentme.data_transfer_objects.contacUsDTO;
import com.rentme.model.ContactUs;
import com.rentme.model.MessageStatus;
import com.rentme.repository.ContactUsRepository;

@Service

public class ContactUsService {
 ContactUsRepository contactUsRepository;


 public ContactUsService(ContactUsRepository contactUsRepository) {
  this.contactUsRepository = contactUsRepository;


 }

 public void receiveMessage(contacUsDTO dto) {
  ContactUs msg = new ContactUs();
  msg.setEmail(dto.getEmail());
  msg.setMessage(dto.getMessage());
  msg.setSentAt(LocalDate.now());
  msg.setStatus(MessageStatus.NEW);
  msg.setSubject(dto.getSubject());
  msg.setUserId(dto.getUserId());
  msg.setUserName(dto.getUserName());
  contactUsRepository.save(msg);
 }

}
