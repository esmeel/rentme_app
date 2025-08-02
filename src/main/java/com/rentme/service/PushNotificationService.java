package com.rentme.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

 public void sendNotification(String title, String body, String fcmToken) {
  try {
   Notification notification = Notification.builder().setTitle(title).setBody(body).build();

   Message message = Message.builder().setToken(fcmToken).setNotification(notification)
     .putData("click_action", "FLUTTER_NOTIFICATION_CLICK").build();

   String response = FirebaseMessaging.getInstance().send(message);
   System.out.println("✅ Notification sent successfully: " + response);

  } catch (FirebaseMessagingException e) {
   System.err.println("❌ Error sending FCM notification: " + e.getMessage());
  }
 }
}
