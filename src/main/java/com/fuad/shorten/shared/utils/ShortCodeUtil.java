package com.fuad.shorten.shared.utils;

import com.fuad.shorten.db.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class ShortCodeUtil {
    private final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SHORT_CODE_LENGTH = 8;

    @Autowired
    LinkRepository linkRepository;

    public String getShortCode(String originalUrl) {
        String shortCode;
        boolean isUnique;
        long timestamp = System.currentTimeMillis();
        int attempt = 0;
        do {
            shortCode = generateShortCode(originalUrl, timestamp, attempt);
            isUnique = !linkRepository.existsByShortCode(shortCode);
            attempt++;
        } while (!isUnique && attempt < 10);

        if (!isUnique) {
            throw new RuntimeException("Failed to generate unique short_code after " + attempt + " attempts");
        }

        return shortCode;
    }

    private String toBase62(long value) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(BASE62_CHARS.charAt((int) (value % 62)));
            value /= 62;
        } while (value > 0);
        while (sb.length() < SHORT_CODE_LENGTH) {
            sb.append(BASE62_CHARS.charAt(0)); // Pad with '0'
        }
        return sb.reverse().substring(0, SHORT_CODE_LENGTH);
    }

    private String generateShortCode(String originalUrl, long timestamp, int attempt) {
        try {
            String input = originalUrl + timestamp + attempt;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            long value = Math.abs(new BigInteger(hash).longValue() % (long) Math.pow(BASE62_CHARS.length(), SHORT_CODE_LENGTH));
            return toBase62(value);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
