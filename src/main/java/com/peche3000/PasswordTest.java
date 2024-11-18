package com.peche3000;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String adminHash = encoder.encode("admin123");
        String userHash = encoder.encode("user123");

        System.out.println("Hash pour admin123 : " + adminHash);
        System.out.println("Hash pour user123 : " + userHash);
    }
}
