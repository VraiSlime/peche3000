package com.peche3000.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("motdepasse123 : " + encoder.encode("motdepasse123"));
        System.out.println("nopass : " + encoder.encode("nopass"));
    }
}
