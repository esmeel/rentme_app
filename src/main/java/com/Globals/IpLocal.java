package com.Globals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IpLocal {
  public static final String theIP = get();

  public static String get() {
    String defaultIP = "http://localhost:8080/";
    try (BufferedReader br = new BufferedReader(new FileReader("local_ip.txt"))) {
      String ip = br.readLine();
      if (ip != null && !ip.trim().isEmpty()) {
        if (!ip.endsWith("/"))
          ip += "/";
        return ip;
      }
    } catch (IOException e) {
      System.err.println("Could not read local_ip.txt, using default IP.");
    }
    return defaultIP;
  }
}
