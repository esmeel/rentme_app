package com.rentme.controller;

import com.rentme.service.FileUploadService;
import com.Globals.EsIp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload/identity")
    public ResponseEntity<?> uploadIdentityDoc(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }

        String savedName = fileUploadService.storeFile(file, "identity");

        String fileUrl = IpLocal.theIP + "uploads/identity/" + savedName;

        return ResponseEntity.ok(fileUrl);
    }
}
