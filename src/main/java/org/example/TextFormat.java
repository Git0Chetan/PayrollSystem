package org.example;

public class TextFormat {
    /**
     * Reverses the characters in the given string.
     * Returns null if input is null.
     */
    public static String reverse(String s) {
        if (s == null) return null;
        return new StringBuilder(s).reverse().toString();
    }

    /**
     * Checks if the given string is a palindrome (ignoring case and non-letter characters).
     * Returns false for null input.
     */
    public static boolean isPalindrome(String s) {
        if (s == null) return false;
        String cleaned = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    /**
     * Converts the input to upper case.
     * Returns null if input is null.
     */
    public static String toUpperCase(String s) {
        if (s == null) return null;
        return s.toUpperCase();
    }

    /**
     * Counts the number of non-overlapping occurrences of sub in text.
     */
    public static int countOccurrences(String text, String sub) {
        if (text == null || sub == null || sub.isEmpty()) return 0;
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * Removes all vowels from the input string.
     */
    public static String removeVowels(String s) {
        if (s == null) return null;
        return s.replaceAll("[AEIOUaeiou]", "");
    }
}