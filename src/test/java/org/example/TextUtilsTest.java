package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    // Test cases for the reverse method

    // Test case for null input
    @Test
    @DisplayName("reverse: Should return null for null input")
    void testReverse_NullInput() {
        assertNull(TextUtils.reverse(null), "Null input should result in null output.");
    }

    // Test case for an empty string
    @Test
    @DisplayName("reverse: Should return an empty string for empty input")
    void testReverse_EmptyString() {
        assertEquals("", TextUtils.reverse(""), "Empty string should remain empty.");
    }

    // Test case for a single character string
    @Test
    @DisplayName("reverse: Should return the same character for a single character string")
    void testReverse_SingleCharString() {
        assertEquals("a", TextUtils.reverse("a"), "Single character string should remain unchanged.");
    }

    // Test case for a common word
    @Test
    @DisplayName("reverse: Should correctly reverse a typical word")
    void testReverse_TypicalWord() {
        assertEquals("olleh", TextUtils.reverse("hello"), "Should reverse 'hello' to 'olleh'.");
    }

    // Test case for a palindrome string
    @Test
    @DisplayName("reverse: Should correctly reverse a palindrome string")
    void testReverse_PalindromeString() {
        assertEquals("racecar", TextUtils.reverse("racecar"), "Should reverse 'racecar' to 'racecar'.");
    }

    // Test case for a string with spaces and special characters
    @Test
    @DisplayName("reverse: Should correctly reverse a string with spaces and special characters")
    void testReverse_WithSpacesAndSpecialChars() {
        assertEquals("!dlroW olleH", TextUtils.reverse("Hello World!"), "Should handle spaces and special characters.");
    }

    // Test cases for the isPalindrome method

    // Test case for null input
    @Test
    @DisplayName("isPalindrome: Should return false for null input")
    void testIsPalindrome_NullInput() {
        assertFalse(TextUtils.isPalindrome(null), "Null input should not be considered a palindrome.");
    }

    // Test case for an empty string
    @Test
    @DisplayName("isPalindrome: Should return true for an empty string")
    void testIsPalindrome_EmptyString() {
        assertTrue(TextUtils.isPalindrome(""), "Empty string should be considered a palindrome.");
    }

    // Test case for a single character string
    @Test
    @DisplayName("isPalindrome: Should return true for a single character string")
    void testIsPalindrome_SingleCharString() {
        assertTrue(TextUtils.isPalindrome("a"), "Single character string should be a palindrome.");
    }

    // Test case for a known palindrome
    @Test
    @DisplayName("isPalindrome: Should return true for a classic palindrome (e.g., racecar)")
    void testIsPalindrome_TruePalindrome() {
        assertTrue(TextUtils.isPalindrome("racecar"), "Racecar should be a palindrome.");
    }

    // Test case for a non-palindrome
    @Test
    @DisplayName("isPalindrome: Should return false for a non-palindrome (e.g., hello)")
    void testIsPalindrome_NonPalindrome() {
        assertFalse(TextUtils.isPalindrome("hello"), "Hello should not be a palindrome.");
    }

    // Test case for palindrome with mixed case
    @Test
    @DisplayName("isPalindrome: Should handle mixed case palindromes correctly")
    void testIsPalindrome_MixedCasePalindrome() {
        assertTrue(TextUtils.isPalindrome("Racecar"), "Racecar (mixed case) should be a palindrome.");
    }

    // Test case for palindrome with spaces and punctuation
    @Test
    @DisplayName("isPalindrome: Should ignore spaces and punctuation for palindromes")
    void testIsPalindrome_WithSpacesAndPunctuation() {
        assertTrue(TextUtils.isPalindrome("A man, a plan, a canal: Panama"), "Complex palindrome should be recognized.");
    }

    // Test case for a non-palindrome with spaces and punctuation
    @Test
    @DisplayName("isPalindrome: Should return false for a non-palindrome with spaces and punctuation")
    void testIsPalindrome_NonPalindromeWithSpacesAndPunctuation() {
        assertFalse(TextUtils.isPalindrome("Hello, World!"), "Non-palindrome with special chars should be recognized as such.");
    }

    // Test case for a numeric palindrome
    @Test
    @DisplayName("isPalindrome: Should recognize numeric palindromes")
    void testIsPalindrome_NumericPalindrome() {
        assertTrue(TextUtils.isPalindrome("12321"), "Numeric string 12321 should be a palindrome.");
    }

    // Test case for an alphanumeric palindrome
    @Test
    @DisplayName("isPalindrome: Should recognize alphanumeric palindromes")
    void testIsPalindrome_AlphanumericPalindrome() {
        assertTrue(TextUtils.isPalindrome("Madam, I'm Adam"), "Alphanumeric palindrome should be recognized.");
    }

    // Test cases for the countVowels method

    // Test case for null input
    @Test
    @DisplayName("countVowels: Should return 0 for null input")
    void testCountVowels_NullInput() {
        assertEquals(0, TextUtils.countVowels(null), "Null input should result in 0 vowels.");
    }

    // Test case for an empty string
    @Test
    @DisplayName("countVowels: Should return 0 for an empty string")
    void testCountVowels_EmptyString() {
        assertEquals(0, TextUtils.countVowels(""), "Empty string should have 0 vowels.");
    }

    // Test case for a string with no vowels
    @Test
    @DisplayName("countVowels: Should return 0 for a string with no vowels")
    void testCountVowels_NoVowels() {
        assertEquals(0, TextUtils.countVowels("rhythm"), "String 'rhythm' should have 0 vowels.");
        assertEquals(0, TextUtils.countVowels("bcdfghjkl"), "String with only consonants should have 0 vowels.");
    }

    // Test case for a string with all lowercase vowels
    @Test
    @DisplayName("countVowels: Should correctly count all lowercase vowels")
    void testCountVowels_AllLowercaseVowels() {
        assertEquals(5, TextUtils.countVowels("aeiou"), "String 'aeiou' should have 5 vowels.");
    }

    // Test case for a string with all uppercase vowels
    @Test
    @DisplayName("countVowels: Should correctly count all uppercase vowels")
    void testCountVowels_AllUppercaseVowels() {
        assertEquals(5, TextUtils.countVowels("AEIOU"), "String 'AEIOU' should have 5 vowels.");
    }

    // Test case for a string with mixed case vowels and consonants
    @Test
    @DisplayName("countVowels: Should correctly count mixed case vowels in a typical sentence")
    void testCountVowels_MixedCaseSentence() {
        assertEquals(4, TextUtils.countVowels("Java is fun"), "String 'Java is fun' should have 4 vowels.");
    }

    // Test case for a string with repeated vowels
    @Test
    @DisplayName("countVowels: Should count repeated vowels")
    void testCountVowels_RepeatedVowels() {
        assertEquals(3, TextUtils.countVowels("banana"), "String 'banana' should have 3 vowels.");
    }

    // Test case for a string with special characters and numbers
    @Test
    @DisplayName("countVowels: Should ignore special characters and numbers when counting vowels")
    void testCountVowels_WithSpecialCharsAndNumbers() {
        assertEquals(2, TextUtils.countVowels("h3llo world!"), "String 'h3ll0 w0rld!' should have 2 vowels.");
    }

    // Test cases for the toUpperCase method

    // Test case for null input
    @Test
    @DisplayName("toUpperCase: Should return null for null input")
    void testToUpperCase_NullInput() {
        assertNull(TextUtils.toUpperCase(null), "Null input should result in null output.");
    }

    // Test case for an empty string
    @Test
    @DisplayName("toUpperCase: Should return an empty string for empty input")
    void testToUpperCase_EmptyString() {
        assertEquals("", TextUtils.toUpperCase(""), "Empty string should remain empty.");
    }

    // Test case for an all lowercase string
    @Test
    @DisplayName("toUpperCase: Should convert an all lowercase string to uppercase")
    void testToUpperCase_AllLowercase() {
        assertEquals("HELLO", TextUtils.toUpperCase("hello"), "Should convert 'hello' to 'HELLO'.");
    }

    // Test case for an all uppercase string
    @Test
    @DisplayName("toUpperCase: Should return an all uppercase string unchanged")
    void testToUpperCase_AllUppercase() {
        assertEquals("WORLD", TextUtils.toUpperCase("WORLD"), "All uppercase string should remain unchanged.");
    }

    // Test case for a mixed case string
    @Test
    @DisplayName("toUpperCase: Should convert a mixed case string to all uppercase")
    void testToUpperCase_MixedCase() {
        assertEquals("HELLO WORLD", TextUtils.toUpperCase("HeLlO WoRlD"), "Should convert 'HeLlO WoRlD' to 'HELLO WORLD'.");
    }

    // Test case for a string with numbers and special characters
    @Test
    @DisplayName("toUpperCase: Should not alter numbers or special characters")
    void testToUpperCase_WithNumbersAndSpecialChars() {
        assertEquals("123!@# ABC", TextUtils.toUpperCase("123!@# Abc"), "Numbers and special characters should not change.");
    }

    // Test cases for the removeVowels method

    // Test case for null input
    @Test
    @DisplayName("removeVowels: Should return null for null input")
    void testRemoveVowels_NullInput() {
        assertNull(TextUtils.removeVowels(null), "Null input should result in null output.");
    }

    // Test case for an empty string
    @Test
    @DisplayName("removeVowels: Should return an empty string for empty input")
    void testRemoveVowels_EmptyString() {
        assertEquals("", TextUtils.removeVowels(""), "Empty string should remain empty.");
    }

    // Test case for a string with all vowels
    @Test
    @DisplayName("removeVowels: Should remove all vowels from a string consisting only of vowels")
    void testRemoveVowels_AllVowels() {
        assertEquals("", TextUtils.removeVowels("AEIOUaeiou"), "String with all vowels should become empty.");
    }

    // Test case for a string with no vowels
    @Test
    @DisplayName("removeVowels: Should return the same string for input with no vowels")
    void testRemoveVowels_NoVowels() {
        assertEquals("rhythm", TextUtils.removeVowels("rhythm"), "String 'rhythm' should remain unchanged.");
    }

    // Test case for a common word
    @Test
    @DisplayName("removeVowels: Should correctly remove vowels from a typical word")
    void testRemoveVowels_TypicalWord() {
        assertEquals("hll", TextUtils.removeVowels("hello"), "Should remove vowels from 'hello' to get 'hll'.");
    }

    // Test case for a sentence with mixed case vowels
    @Test
    @DisplayName("removeVowels: Should remove both lowercase and uppercase vowels from a sentence")
    void testRemoveVowels_MixedCaseSentence() {
        assertEquals("Jv s fn", TextUtils.removeVowels("Java is fun"), "Should remove vowels from 'Java is fun'.");
    }

    // Test case for a string with special characters and numbers
    @Test
    @DisplayName("removeVowels: Should ignore special characters and numbers when removing vowels")
    void testRemoveVowels_WithSpecialCharsAndNumbers() {
        assertEquals("h3ll0 w0rld!", TextUtils.removeVowels("h3ll0 w0rld!"), "Numbers and special characters should be preserved.");
        assertEquals("Tst Strng Wth N Vwls @nd Nmbrs 123", TextUtils.removeVowels("Test String With No Vowels @and Numbers 123"), "Should remove vowels from a complex string.");
    }
}