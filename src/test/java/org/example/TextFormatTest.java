package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextFormatTest {

    // --- Tests for reverse method ---

    // Test case for null input
    @Test
    void testReverse_NullInput() {
        assertNull(TextFormat.reverse(null), "reverse should return null for null input");
    }

    // Test case for an empty string
    @Test
    void testReverse_EmptyString() {
        assertEquals("", TextFormat.reverse(""), "reverse should return an empty string for empty input");
    }

    // Test case for a single character string
    @Test
    void testReverse_SingleCharacterString() {
        assertEquals("a", TextFormat.reverse("a"), "reverse should return the same character for a single character string");
    }

    // Test case for a regular string
    @Test
    void testReverse_RegularString() {
        assertEquals("olleh", TextFormat.reverse("hello"), "reverse should correctly reverse a standard string");
    }

    // Test case for a string with spaces
    @Test
    void testReverse_StringWithSpaces() {
        assertEquals("dlrow olleH", TextFormat.reverse("Hello world"), "reverse should handle spaces correctly");
    }

    // Test case for a string with special characters
    @Test
    void testReverse_StringWithSpecialCharacters() {
        assertEquals("!@#$", TextFormat.reverse("$#@!"), "reverse should handle special characters correctly");
    }

    // Test case for a long string
    @Test
    void testReverse_LongString() {
        String longString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String expected = "9876543210ZYXWVUTSRQPONMLKJIHGFEDCBAzyxwvutsrqponmlkjihgfedcba";
        assertEquals(expected, TextFormat.reverse(longString), "reverse should handle long strings");
    }

    // --- Tests for isPalindrome method ---

    // Test case for null input
    @Test
    void testIsPalindrome_NullInput() {
        assertFalse(TextFormat.isPalindrome(null), "isPalindrome should return false for null input");
    }

    // Test case for an empty string
    @Test
    void testIsPalindrome_EmptyString() {
        assertTrue(TextFormat.isPalindrome(""), "isPalindrome should return true for an empty string");
    }

    // Test case for a single character string
    @Test
    void testIsPalindrome_SingleCharacterString() {
        assertTrue(TextFormat.isPalindrome("a"), "isPalindrome should return true for a single character string");
        assertTrue(TextFormat.isPalindrome("Z"), "isPalindrome should return true for a single uppercase character string");
        assertTrue(TextFormat.isPalindrome("1"), "isPalindrome should return true for a single digit string");
    }

    // Test case for a standard palindrome
    @Test
    void testIsPalindrome_StandardPalindrome() {
        assertTrue(TextFormat.isPalindrome("madam"), "isPalindrome should return true for 'madam'");
    }

    // Test case for a palindrome with mixed case
    @Test
    void testIsPalindrome_MixedCasePalindrome() {
        assertTrue(TextFormat.isPalindrome("Racecar"), "isPalindrome should return true for 'Racecar' (case-insensitive)");
    }

    // Test case for a palindrome with spaces and special characters
    @Test
    void testIsPalindrome_ComplexPalindrome() {
        assertTrue(TextFormat.isPalindrome("A man, a plan, a canal: Panama"), "isPalindrome should handle spaces and special characters");
    }

    // Test case for a non-palindrome
    @Test
    void testIsPalindrome_NonPalindrome() {
        assertFalse(TextFormat.isPalindrome("hello"), "isPalindrome should return false for 'hello'");
    }

    // Test case for a non-palindrome with spaces and special characters
    @Test
    void testIsPalindrome_ComplexNonPalindrome() {
        assertFalse(TextFormat.isPalindrome("Hello World"), "isPalindrome should return false for 'Hello World'");
    }

    // Test case for a string with only non-alphanumeric characters
    @Test
    void testIsPalindrome_OnlySpecialCharacters() {
        assertTrue(TextFormat.isPalindrome("!@#$%^&*()"), "isPalindrome should return true for string with only special characters");
    }

    // Test case for a palindrome with numbers
    @Test
    void testIsPalindrome_NumericPalindrome() {
        assertTrue(TextFormat.isPalindrome("12321"), "isPalindrome should return true for a numeric palindrome");
        assertTrue(TextFormat.isPalindrome("1-2-3-2-1"), "isPalindrome should handle numbers and separators");
    }

    // --- Tests for toUpperCase method ---

    // Test case for null input
    @Test
    void testToUpperCase_NullInput() {
        assertNull(TextFormat.toUpperCase(null), "toUpperCase should return null for null input");
    }

    // Test case for an empty string
    @Test
    void testToUpperCase_EmptyString() {
        assertEquals("", TextFormat.toUpperCase(""), "toUpperCase should return an empty string for empty input");
    }

    // Test case for a string with all lowercase characters
    @Test
    void testToUpperCase_AllLowerCase() {
        assertEquals("HELLO WORLD", TextFormat.toUpperCase("hello world"), "toUpperCase should convert all lowercase to uppercase");
    }

    // Test case for a string with mixed case characters
    @Test
    void testToUpperCase_MixedCase() {
        assertEquals("HELLO WORLD", TextFormat.toUpperCase("Hello World"), "toUpperCase should convert mixed case to uppercase");
    }

    // Test case for a string already in uppercase
    @Test
    void testToUpperCase_AllUpperCase() {
        assertEquals("HELLO WORLD", TextFormat.toUpperCase("HELLO WORLD"), "toUpperCase should return the same string if already uppercase");
    }

    // Test case for a string with numbers and special characters
    @Test
    void testToUpperCase_NumbersAndSpecialCharacters() {
        assertEquals("123!@# ABC", TextFormat.toUpperCase("123!@# abc"), "toUpperCase should leave numbers and special characters unchanged");
    }

    // --- Tests for countOccurrences method ---

    // Test case for null text
    @Test
    void testCountOccurrences_NullText() {
        assertEquals(0, TextFormat.countOccurrences(null, "a"), "countOccurrences should return 0 for null text");
    }

    // Test case for null substring
    @Test
    void testCountOccurrences_NullSub() {
        assertEquals(0, TextFormat.countOccurrences("test", null), "countOccurrences should return 0 for null substring");
    }

    // Test case for empty substring
    @Test
    void testCountOccurrences_EmptySub() {
        assertEquals(0, TextFormat.countOccurrences("test", ""), "countOccurrences should return 0 for empty substring");
    }

    // Test case where no occurrence is found
    @Test
    void testCountOccurrences_NoOccurrence() {
        assertEquals(0, TextFormat.countOccurrences("hello", "x"), "countOccurrences should return 0 when substring is not found");
    }

    // Test case for a single occurrence
    @Test
    void testCountOccurrences_SingleOccurrence() {
        assertEquals(1, TextFormat.countOccurrences("banana", "ana"), "countOccurrences should count a single occurrence");
    }

    // Test case for multiple non-overlapping occurrences
    @Test
    void testCountOccurrences_MultipleNonOverlapping() {
        assertEquals(3, TextFormat.countOccurrences("banana", "a"), "countOccurrences should count multiple non-overlapping occurrences");
    }

    // Test case for overlapping occurrences (should not count them overlapping)
    @Test
    void testCountOccurrences_NonOverlappingMechanism() {
        assertEquals(2, TextFormat.countOccurrences("aaaaa", "aa"), "countOccurrences should correctly handle non-overlapping 'aa' in 'aaaaa'");
        assertEquals(2, TextFormat.countOccurrences("ababab", "aba"), "countOccurrences should correctly handle non-overlapping 'aba' in 'ababab'");
    }

    // Test case for substring at the beginning
    @Test
    void testCountOccurrences_AtBeginning() {
        assertEquals(1, TextFormat.countOccurrences("apple", "app"), "countOccurrences should count substring at the beginning");
    }

    // Test case for substring at the end
    @Test
    void testCountOccurrences_AtEnd() {
        assertEquals(1, TextFormat.countOccurrences("apple", "ple"), "countOccurrences should count substring at the end");
    }

    // Test case where text is shorter than substring
    @Test
    void testCountOccurrences_TextShorterThanSub() {
        assertEquals(0, TextFormat.countOccurrences("a", "abc"), "countOccurrences should return 0 if text is shorter than substring");
    }

    // Test case for case sensitivity
    @Test
    void testCountOccurrences_CaseSensitive() {
        assertEquals(0, TextFormat.countOccurrences("Hello World", "hello"), "countOccurrences should be case-sensitive");
        assertEquals(1, TextFormat.countOccurrences("Hello World", "Hello"), "countOccurrences should find case-matching occurrences");
    }

    // Test case with numbers and special characters in text and sub
    @Test
    void testCountOccurrences_WithNumbersAndSpecialChars() {
        assertEquals(2, TextFormat.countOccurrences("123abc123def123", "123"), "countOccurrences should handle numbers");
        assertEquals(1, TextFormat.countOccurrences("!@#$!@#$", "!@#$"), "countOccurrences should handle special characters");
    }

    // Test case with empty text (and non-empty sub)
    @Test
    void testCountOccurrences_EmptyText() {
        assertEquals(0, TextFormat.countOccurrences("", "a"), "countOccurrences should return 0 for empty text");
    }

    // --- Tests for removeVowels method ---

    // Test case for null input
    @Test
    void testRemoveVowels_NullInput() {
        assertNull(TextFormat.removeVowels(null), "removeVowels should return null for null input");
    }

    // Test case for an empty string
    @Test
    void testRemoveVowels_EmptyString() {
        assertEquals("", TextFormat.removeVowels(""), "removeVowels should return an empty string for empty input");
    }

    // Test case for a string with all vowels
    @Test
    void testRemoveVowels_AllVowels() {
        assertEquals("", TextFormat.removeVowels("aeiouAEIOU"), "removeVowels should remove all vowels (lowercase and uppercase)");
    }

    // Test case for a string with no vowels
    @Test
    void testRemoveVowels_NoVowels() {
        assertEquals("Rhythm", TextFormat.removeVowels("Rhythm"), "removeVowels should return the same string if no vowels are present");
        assertEquals("BCDfgH", TextFormat.removeVowels("BCDfgh"), "removeVowels should handle all consonants");
    }

    // Test case for a regular string with mixed vowels and consonants
    @Test
    void testRemoveVowels_MixedString() {
        assertEquals("Hll Wrld", TextFormat.removeVowels("Hello World"), "removeVowels should correctly remove vowels from a mixed string");
        assertEquals("Th qck brwn fx jmps vr th lzy dg", TextFormat.removeVowels("The quick brown fox jumps over the lazy dog"), "removeVowels should handle a sentence");
    }

    // Test case for a string with numbers and special characters
    @Test
    void testRemoveVowels_NumbersAndSpecialCharacters() {
        assertEquals("123!@#", TextFormat.removeVowels("123!@#"), "removeVowels should leave numbers and special characters unchanged");
    }

    // Test case for a string with only some vowels
    @Test
    void testRemoveVowels_SomeVowels() {
        assertEquals("ppl", TextFormat.removeVowels("Apple"), "removeVowels should remove some vowels correctly");
        assertEquals("rng", TextFormat.removeVowels("Orange"), "removeVowels should remove specific vowels");
    }

    // Test case for leading/trailing vowels
    @Test
    void testRemoveVowels_LeadingTrailingVowels() {
        assertEquals("pple", TextFormat.removeVowels("Apple"), "removeVowels should remove leading vowels");
        assertEquals("bnn", TextFormat.removeVowels("Banana"), "removeVowels should remove trailing vowels");
    }
}