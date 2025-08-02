package com.rentme.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FirebaseInitializer {

 @PostConstruct
 public void initialize() {
  try {
   InputStream serviceAccount =
     getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");

   if (serviceAccount == null) {
    throw new IllegalStateException("firebase-service-account.json not found in resources folder");
   }

   FirebaseOptions options =
     FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

   if (FirebaseApp.getApps().isEmpty()) {
    FirebaseApp.initializeApp(options);
    System.out.println("✅ Firebase initialized");
   }

  } catch (IOException e) {
   throw new RuntimeException("❌ Firebase initialization failed", e);
  }
 }
}
