package com.rentme.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Globals.EsIp;

@Service
public class FileStorageService {

    public String saveUserProfileImage(MultipartFile file, Long userId) {
        try {
            // حدد المسار المطلق لمجلد "uploads/users"
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "users");

            // أنشئ المجلد إن لم يكن موجودًا
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // اسم الصورة معرّف المستخدم
            String filename = "user_" + userId + ".jpg";

            // المسار الكامل للصورة
            Path filePath = uploadPath.resolve(filename);

            // احفظ الصورة (يستبدل إذا كانت موجودة)
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // المسار الذي يُستخدم من جهة الواجهة (Flutter)
            return IpLocal.theIP + "uploads/users/" + filename;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store image");
        }
    }
}
