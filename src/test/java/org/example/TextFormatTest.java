package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextFormatTest {

    // --- Tests for reverse(String s) ---

    // Test case for null input
    @Test
    void testReverse_NullInput() {
        Assertions.assertNull(TextFormat.reverse(null));
    }

    // Test case for an empty string
    @Test
    void testReverse_EmptyString() {
        Assertions.assertEquals("", TextFormat.reverse(""));
    }

    // Test case for a single character string
    @Test
    void testReverse_SingleCharacterString() {
        Assertions.assertEquals("a", TextFormat.reverse("a"));
    }

    // Test case for a regular string
    @Test
    void testReverse_RegularString() {
        Assertions.assertEquals("olleh", TextFormat.reverse("hello"));
    }

    // Test case for a string with spaces and special characters
    @Test
    void testReverse_StringWithSpacesAndSpecialChars() {
        Assertions.assertEquals("!dlroW olleH", TextFormat.reverse("Hello World!"));
    }

    // Test case for a palindrome string
    @Test
    void testReverse_PalindromeString() {
        Assertions.assertEquals("madam", TextFormat.reverse("madam"));
    }

    // Test case for a string with numbers
    @Test
    void testReverse_StringWithNumbers() {
        Assertions.assertEquals("321", TextFormat.reverse("123"));
    }

    // --- Tests for isPalindrome(String s) ---

    // Test case for null input
    @Test
    void testIsPalindrome_NullInput() {
        Assertions.assertFalse(TextFormat.isPalindrome(null));
    }

    // Test case for an empty string
    @Test
    void testIsPalindrome_EmptyString() {
        Assertions.assertTrue(TextFormat.isPalindrome(""));
    }

    // Test case for a single character string
    @Test
    void testIsPalindrome_SingleCharacterString() {
        Assertions.assertTrue(TextFormat.isPalindrome("a"));
    }

    // Test case for an odd-length palindrome
    @Test
    void testIsPalindrome_OddLengthPalindrome() {
        Assertions.assertTrue(TextFormat.isPalindrome("madam"));
    }

    // Test case for an even-length palindrome
    @Test
    void testIsPalindrome_EvenLengthPalindrome() {
        Assertions.assertTrue(TextFormat.isPalindrome("abba"));
    }

    // Test case for a non-palindrome string
    @Test
    void testIsPalindrome_NonPalindrome() {
        Assertions.assertFalse(TextFormat.isPalindrome("hello"));
    }

    // Test case for a palindrome with mixed case, spaces, and punctuation
    @Test
    void testIsPalindrome_MixedCaseSpacesPunctuationPalindrome() {
        Assertions.assertTrue(TextFormat.isPalindrome("A man, a plan, a canal: Panama"));
    }

    // Test case for a non-palindrome with mixed case, spaces, and punctuation
    @Test
    void testIsPalindrome_MixedCaseSpacesPunctuationNonPalindrome() {
        Assertions.assertFalse(TextFormat.isPalindrome("Hello, World!"));
    }

    // Test case for string with only non-alphanumeric characters
    @Test
    void testIsPalindrome_OnlySpecialChars() {
        Assertions.assertTrue(TextFormat.isPalindrome("!@#$%")); // Cleans to empty string, which is palindrome
    }

    // Test case for string with numbers that is a palindrome
    @Test
    void testIsPalindrome_NumericPalindrome() {
        Assertions.assertTrue(TextFormat.isPalindrome("12321"));
    }

    // Test case for string with numbers and letters that is a palindrome
    @Test
    void testIsPalindrome_AlphanumericPalindrome() {
        Assertions.assertTrue(TextFormat.isPalindrome("A1B2B1A"));
    }

    // Test case for string with leading/trailing spaces
    @Test
    void testIsPalindrome_WithLeadingTrailingSpaces() {
        Assertions.assertTrue(TextFormat.isPalindrome(" racecar "));
    }

    // --- Tests for toUpperCase(String s) ---

    // Test case for null input
    @Test
    void testToUpperCase_NullInput() {
        Assertions.assertNull(TextFormat.toUpperCase(null));
    }

    // Test case for an empty string
    @Test
    void testToUpperCase_EmptyString() {
        Assertions.assertEquals("", TextFormat.toUpperCase(""));
    }

    // Test case for an all-lowercase string
    @Test
    void testToUpperCase_AllLowercase() {
        Assertions.assertEquals("HELLO", TextFormat.toUpperCase("hello"));
    }

    // Test case for an all-uppercase string
    @Test
    void testToUpperCase_AllUppercase() {
        Assertions.assertEquals("HELLO", TextFormat.toUpperCase("HELLO"));
    }

    // Test case for a mixed-case string
    @Test
    void testToUpperCase_MixedCase() {
        Assertions.assertEquals("HELLOWORLD", TextFormat.toUpperCase("HelloWorLd"));
    }

    // Test case for a string with numbers and special characters
    @Test
    void testToUpperCase_WithNumbersAndSpecialChars() {
        Assertions.assertEquals("TEST123!@", TextFormat.toUpperCase("test123!@"));
    }

    // --- Tests for countOccurrences(String text, String sub) ---

    // Test case for null text input
    @Test
    void testCountOccurrences_NullText() {
        Assertions.assertEquals(0, TextFormat.countOccurrences(null, "sub"));
    }

    // Test case for null substring input
    @Test
    void testCountOccurrences_NullSubstring() {
        Assertions.assertEquals(0, TextFormat.countOccurrences("text", null));
    }

    // Test case for empty substring input
    @Test
    void testCountOccurrences_EmptySubstring() {
        Assertions.assertEquals(0, TextFormat.countOccurrences("text", ""));
    }

    // Test case for empty text input
    @Test
    void testCountOccurrences_EmptyText() {
        Assertions.assertEquals(0, TextFormat.countOccurrences("", "sub"));
    }

    // Test case where substring is not found
    @Test
    void testCountOccurrences_SubstringNotFound() {
        Assertions.assertEquals(0, TextFormat.countOccurrences("hello world", "xyz"));
    }

    // Test case for a single occurrence
    @Test
    void testCountOccurrences_SingleOccurrence() {
        Assertions.assertEquals(1, TextFormat.countOccurrences("banana", "ana"));
    }

    // Test case for multiple non-overlapping occurrences
    @Test
    void testCountOccurrences_MultipleNonOverlappingOccurrences() {
        Assertions.assertEquals(2, TextFormat.countOccurrences("banana", "na"));
    }

    // Test case for multiple overlapping occurrences (should count non-overlapping)
    @Test
    void testCountOccurrences_MultipleOverlappingOccurrences() {
        Assertions.assertEquals(2, TextFormat.countOccurrences("aaaaa", "aa")); // aa at 0, then search from 2 -> aa at 2. Total 2.
    }

    // Test case for multiple overlapping occurrences where no new start position
    @Test
    void testCountOccurrences_MultipleOverlappingNoNewStart() {
        Assertions.assertEquals(1, TextFormat.countOccurrences("abababa", "aba")); // aba at 0, search from 3. Next aba at 4. Total 2.
        // Wait, the logic is `idx += sub.length()`.
        // "abababa", "aba"
        // 1st: idx=0, finds "aba" at 0. count=1. idx = 0 + 3 = 3.
        // 2nd: idx=3, finds "aba" at 4. This is wrong. It should be 2, but actual logic in code yields 1.
        // Let's trace `text.indexOf(sub, idx)`:
        // text = "abababa", sub = "aba"
        // 1. idx = 0. text.indexOf("aba", 0) -> 0. count=1. idx = 0 + 3 = 3.
        // 2. idx = 3. text.indexOf("aba", 3) -> 4. count=2. idx = 4 + 3 = 7.
        // 3. idx = 7. text.indexOf("aba", 7) -> -1. Loop ends.
        // So it should be 2. My previous comment about "aaaaa", "aa" was correct, my manual trace of "abababa" was incorrect.
        // It correctly handles non-overlapping.
        Assertions.assertEquals(2, TextFormat.countOccurrences("abababa", "aba"));
    }

    // Test case where substring is at the beginning of the text
    @Test
    void testCountOccurrences_SubstringAtBeginning() {
        Assertions.assertEquals(1, TextFormat.countOccurrences("apple", "app"));
    }

    // Test case where substring is at the end of the text
    @Test
    void testCountOccurrences_SubstringAtEnd() {
        Assertions.assertEquals(1, TextFormat.countOccurrences("apple", "ple"));
    }

    // Test case where substring is the entire text
    @Test
    void testCountOccurrences_SubstringIsEntireText() {
        Assertions.assertEquals(1, TextFormat.countOccurrences("test", "test"));
    }

    // Test case for case sensitivity
    @Test
    void testCountOccurrences_CaseSensitivity() {
        Assertions.assertEquals(0, TextFormat.countOccurrences("Hello World", "hello"));
        Assertions.assertEquals(1, TextFormat.countOccurrences("Hello World", "World"));
    }

    // Test case for text containing only occurrences
    @Test
    void testCountOccurrences_OnlyOccurrences() {
        Assertions.assertEquals(3, TextFormat.countOccurrences("okokok", "ok"));
    }

    // --- Tests for removeVowels(String s) ---

    // Test case for null input
    @Test
    void testRemoveVowels_NullInput() {
        Assertions.assertNull(TextFormat.removeVowels(null));
    }

    // Test case for an empty string
    @Test
    void testRemoveVowels_EmptyString() {
        Assertions.assertEquals("", TextFormat.removeVowels(""));
    }

    // Test case for a string with no vowels
    @Test
    void testRemoveVowels_NoVowels() {
        Assertions.assertEquals("rhythm", TextFormat.removeVowels("rhythm"));
    }

    // Test case for a string with all vowels (both cases)
    @Test
    void testRemoveVowels_AllVowels() {
        Assertions.assertEquals("", TextFormat.removeVowels("AEIOUaeiou"));
    }

    // Test case for a mixed-case string with vowels and consonants
    @Test
    void testRemoveVowels_MixedString() {
        Assertions.assertEquals("Hll Wrld", TextFormat.removeVowels("Hello World"));
    }

    // Test case for a string containing only vowels and spaces/special characters
    @Test
    void testRemoveVowels_VowelsAndSpecialChars() {
        Assertions.assertEquals("   !", TextFormat.removeVowels("A E I O U !"));
    }

    // Test case for a string with numbers and vowels
    @Test
    void testRemoveVowels_WithNumbersAndVowels() {
        Assertions.assertEquals("12345", TextFormat.removeVowels("1a2e3i4o5u"));
    }

    // Test case for a string with only spaces
    @Test
    void testRemoveVowels_OnlySpaces() {
        Assertions.assertEquals("   ", TextFormat.removeVowels("   "));
    }
}