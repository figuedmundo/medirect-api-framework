package com.medirect.api.utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String NUMERIC_CHARSET = "0123456789";
    private static final String ALPHA_NUMERIC_CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public static String randomNumber(int length) {
        return generateRandomString(length, NUMERIC_CHARSET);
    }

    public static String randomString(int length) {
        return generateRandomString(length, ALPHA_NUMERIC_CHARSET);
    }

    private static String generateRandomString(int length, String charset) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(charset.length());
            result.append(charset.charAt(randomIndex));
        }
        return result.toString();
    }

    public static String getDate() {
        return getDate(null);
    }

    // Get the current date or a date with an offset
    public static String getDate(String offset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now();

        if (offset == null || offset.isEmpty()) {
            return date.format(formatter);
        }

        Pattern pattern = Pattern.compile("([+-]?\\d+)([dwhyms])");
        Matcher matcher = pattern.matcher(offset);

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "d":
                    date = date.plusDays(value);
                    break;
                case "w":
                    date = date.plusWeeks(value);
                    break;
                case "y":
                    date = date.plusYears(value);
                    break;
                default:
                    throw new IllegalArgumentException("Not supported: " + unit);
            }
        }
        return date.format(formatter);
    }

    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
