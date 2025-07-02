package com.rentme.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String baseDir;

    public String storeFile(MultipartFile file, String subfolder) {
        System.out.println("****__ storeFile Called ");

        try {
            Path targetDir = Paths.get(baseDir).resolve(subfolder).normalize();
            Files.createDirectories(targetDir);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetPath = targetDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("**** fileName "+ fileName);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("فشل رفع الملف", ex);
        }
    }
}
