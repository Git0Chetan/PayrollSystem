package org.example;

public class TextUtils {

    public static String reverse(String s) {
        if (s == null) return null;
        return new StringBuilder(s).reverse().toString();
    }

    public static boolean isPalindrome(String s) {
        if (s == null) return false;
        String cleaned = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    public static int countVowels(String s) {
        if (s == null) return 0;
        int count = 0;
        for (char c : s.toCharArray()) {
            if ("aeiouAEIOU".indexOf(c) >= 0) count++;
        }
        return count;
    }

    public static String toUpperCase(String s) {
        if (s == null) return null;
        return s.toUpperCase();
    }

    public static String removeVowels(String s) {
        if (s == null) return null;
        return s.replaceAll("[AEIOUaeiou]", "");
    }
}
