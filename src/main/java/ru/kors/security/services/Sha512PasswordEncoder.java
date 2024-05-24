package ru.kors.security.services;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digested = md.digest(rawPassword.toString().getBytes());
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xFF & digested[i]));
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Bad algorithm");
        }
        return sb.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return encodedPassword.equals(hashedPassword);
    }
}
