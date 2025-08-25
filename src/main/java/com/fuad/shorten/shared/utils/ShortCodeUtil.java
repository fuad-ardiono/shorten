package com.fuad.shorten.shared.utils;

import com.fuad.shorten.db.repository.LinkRepository;
import com.fuad.shorten.shared.exception.http.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class ShortCodeUtil {
    private final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SHORT_CODE_LENGTH = 8;
    public final String RESERVED_SHORT_CODE_KEY = "reserved_short_code";

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    RedisSetUtil redisSetUtil;

    public void reserveSortCode() {
        Long totalReserved = redisSetUtil.getSetSize(RESERVED_SHORT_CODE_KEY).orElse(0L);

        if (totalReserved <= 0) {
            long reservedShortCodeCount = 1000L;
            long counter = 0L;

            while (counter < reservedShortCodeCount) {
                String shortCode = getShortCode(UUID.randomUUID().toString());
                redisSetUtil.addToSet(RESERVED_SHORT_CODE_KEY, shortCode);

                counter++;
            }
        }
    }

    public String popShortCode(String originalUrl) {
        Long totalReserved = redisSetUtil.getSetSize(RESERVED_SHORT_CODE_KEY).orElse(0L);

        if (totalReserved > 0) {
            return (String) redisSetUtil.popFromSet(RESERVED_SHORT_CODE_KEY);
        } else {
            reserveSortCode();
            return getShortCode(originalUrl);
        }
    }

    public String getShortCode(String originalUrl) {
        String shortCode;
        boolean isUnique;
        boolean isReserved;
        long timestamp = System.currentTimeMillis();
        int attempt = 0;
        do {
            shortCode = generateShortCode(originalUrl, timestamp, attempt);
            char firstCharShortCode = shortCode.charAt(0);
            isUnique = !linkRepository.existsByShortCodeFirstAndShortCode(firstCharShortCode, shortCode);
            isReserved = redisSetUtil.isMember(RESERVED_SHORT_CODE_KEY, shortCode).orElse(false);
            attempt++;
        } while (!isUnique && !isReserved && attempt < 10);

        if (!isUnique) {
            throw new BadRequestException("Failed to generate unique short_code after " + attempt + " attempts");
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
            throw new BadRequestException("SHA-256 not available");
        }
    }
}
