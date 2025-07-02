package com.rentme.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleTokenVerifier {

  private static final String CLIENT_ID = "313622790637-7ma6d3g1ladn6nfcquume4cgjnriur1u.apps.googleusercontent.com"; // نفس
                                                                                                                      // الذي
                                                                                                                      // استخدمته
                                                                                                                      // في
                                                                                                                      // Flutter

  public static GoogleIdToken.Payload verify(String idTokenString) {
    try {
      GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
          GoogleNetHttpTransport.newTrustedTransport(),
          JacksonFactory.getDefaultInstance())
          .setAudience(Collections.singletonList(CLIENT_ID))
          .build();

      GoogleIdToken idToken = verifier.verify(idTokenString);
      if (idToken != null) {
        return idToken.getPayload();
      } else {
        return null;
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
