package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TextUtils Utility Class Tests")
class TextUtilsTest {

    // Test cases for reverse method
    @Test
    @DisplayName("reverse: Should return null for null input")
    void testReverse_nullInput() {
        assertNull(TextUtils.reverse(null));
    }

    @Test
    @DisplayName("reverse: Should return empty string for empty input")
    void testReverse_emptyString() {
        assertEquals("", TextUtils.reverse(""));
    }

    @Test
    @DisplayName("reverse: Should return the same string for single character input")
    void testReverse_singleCharacter() {
        assertEquals("a", TextUtils.reverse("a"));
    }

    @Test
    @DisplayName("reverse: Should correctly reverse a simple string")
    void testReverse_simpleString() {
        assertEquals("olleh", TextUtils.reverse("hello"));
    }

    @Test
    @DisplayName("reverse: Should correctly reverse a string with spaces")
    void testReverse_stringWithSpaces() {
        assertEquals("dlrow olleh", TextUtils.reverse("hello world"));
    }

    @Test
    @DisplayName("reverse: Should correctly reverse a string with special characters")
    void testReverse_stringWithSpecialCharacters() {
        assertEquals("!@#cba", TextUtils.reverse("abc!@#"));
    }

    @Test
    @DisplayName("reverse: Should correctly reverse a string with numbers")
    void testReverse_stringWithNumbers() {
        assertEquals("321", TextUtils.reverse("123"));
    }

    // Test cases for isPalindrome method
    @Test
    @DisplayName("isPalindrome: Should return false for null input")
    void testIsPalindrome_nullInput() {
        assertFalse(TextUtils.isPalindrome(null));
    }

    @Test
    @DisplayName("isPalindrome: Should return true for an empty string")
    void testIsPalindrome_emptyString() {
        assertTrue(TextUtils.isPalindrome(""));
    }

    @Test
    @DisplayName("isPalindrome: Should return true for a single character string")
    void testIsPalindrome_singleCharacter() {
        assertTrue(TextUtils.isPalindrome("a"));
    }

    @Test
    @DisplayName("isPalindrome: Should return true for a simple palindrome")
    void testIsPalindrome_simplePalindrome() {
        assertTrue(TextUtils.isPalindrome("madam"));
    }

    @Test
    @DisplayName("isPalindrome: Should return true for a palindrome with mixed case")
    void testIsPalindrome_mixedCasePalindrome() {
        assertTrue(TextUtils.isPalindrome("Madam"));
    }

    @Test
    @DisplayName("isPalindrome: Should return true for a palindrome with spaces and punctuation")
    void testIsPalindrome_palindromeWithSpacesAndPunctuation() {
        assertTrue(TextUtils.isPalindrome("A man, a plan, a canal: Panama"));
    }

    @Test
    @DisplayName("isPalindrome: Should return false for a non-palindrome string")
    void testIsPalindrome_notPalindrome() {
        assertFalse(TextUtils.isPalindrome("hello"));
    }

    @Test
    @DisplayName("isPalindrome: Should return false for a non-palindrome with mixed case and special characters")
    void testIsPalindrome_notPalindromeMixedCaseSpecialChars() {
        assertFalse(TextUtils.isPalindrome("Hello, World!"));
    }

    @Test
    @DisplayName("isPalindrome: Should return true for a number palindrome")
    void testIsPalindrome_numberPalindrome() {
        assertTrue(TextUtils.isPalindrome("121"));
    }

    @Test
    @DisplayName("isPalindrome: Should return true for a palindrome with numbers and letters")
    void testIsPalindrome_alphanumericPalindrome() {
        assertTrue(TextUtils.isPalindrome("Race fast, safe car"));
    }


    // Test cases for countVowels method
    @Test
    @DisplayName("countVowels: Should return 0 for null input")
    void testCountVowels_nullInput() {
        assertEquals(0, TextUtils.countVowels(null));
    }

    @Test
    @DisplayName("countVowels: Should return 0 for an empty string")
    void testCountVowels_emptyString() {
        assertEquals(0, TextUtils.countVowels(""));
    }

    @Test
    @DisplayName("countVowels: Should return 0 for a string with no vowels")
    void testCountVowels_noVowels() {
        assertEquals(0, TextUtils.countVowels("rhythm"));
    }

    @Test
    @DisplayName("countVowels: Should count all lowercase vowels correctly")
    void testCountVowels_allLowercaseVowels() {
        assertEquals(5, TextUtils.countVowels("aeiou"));
    }

    @Test
    @DisplayName("countVowels: Should count all uppercase vowels correctly")
    void testCountVowels_allUppercaseVowels() {
        assertEquals(5, TextUtils.countVowels("AEIOU"));
    }

    @Test
    @DisplayName("countVowels: Should count mixed case vowels correctly")
    void testCountVowels_mixedCaseVowels() {
        assertEquals(10, TextUtils.countVowels("AEIOUaeiou"));
    }

    @Test
    @DisplayName("countVowels: Should count vowels in a typical sentence")
    void testCountVowels_typicalSentence() {
        assertEquals(3, TextUtils.countVowels("Hello World")); // e, o, o
    }

    @Test
    @DisplayName("countVowels: Should count vowels amidst special characters and numbers")
    void testCountVowels_withSpecialCharsAndNumbers() {
        assertEquals(5, TextUtils.countVowels("a1!e2@i3#o4$u5%")); // a, e, i, o, u
    }

    @Test
    @DisplayName("countVowels: Should count vowels in a long string")
    void testCountVowels_longString() {
        assertEquals(9, TextUtils.countVowels("The quick brown fox jumps over the lazy dog.")); // e, u, i, o, o, u, o, e, a, o
    }


    // Test cases for toUpperCase method
    @Test
    @DisplayName("toUpperCase: Should return null for null input")
    void testToUpperCase_nullInput() {
        assertNull(TextUtils.toUpperCase(null));
    }

    @Test
    @DisplayName("toUpperCase: Should return empty string for empty input")
    void testToUpperCase_emptyString() {
        assertEquals("", TextUtils.toUpperCase(""));
    }

    @Test
    @DisplayName("toUpperCase: Should convert all lowercase characters to uppercase")
    void testToUpperCase_allLowercase() {
        assertEquals("HELLO", TextUtils.toUpperCase("hello"));
    }

    @Test
    @DisplayName("toUpperCase: Should convert mixed case characters to uppercase")
    void testToUpperCase_mixedCase() {
        assertEquals("HELLO WORLD", TextUtils.toUpperCase("Hello World"));
    }

    @Test
    @DisplayName("toUpperCase: Should return the same string for all uppercase input")
    void testToUpperCase_allUppercase() {
        assertEquals("HELLO", TextUtils.toUpperCase("HELLO"));
    }

    @Test
    @DisplayName("toUpperCase: Should handle strings with numbers and special characters correctly")
    void testToUpperCase_withNumbersAndSpecialCharacters() {
        assertEquals("123!@#ABC", TextUtils.toUpperCase("123!@#abc"));
    }

    // Test cases for removeVowels method
    @Test
    @DisplayName("removeVowels: Should return null for null input")
    void testRemoveVowels_nullInput() {
        assertNull(TextUtils.removeVowels(null));
    }

    @Test
    @DisplayName("removeVowels: Should return empty string for empty input")
    void testRemoveVowels_emptyString() {
        assertEquals("", TextUtils.removeVowels(""));
    }

    @Test
    @DisplayName("removeVowels: Should return the same string if it contains no vowels")
    void testRemoveVowels_noVowels() {
        assertEquals("rhythm", TextUtils.removeVowels("rhythm"));
    }

    @Test
    @DisplayName("removeVowels: Should remove all vowels from a string with only vowels")
    void testRemoveVowels_allVowels() {
        assertEquals("", TextUtils.removeVowels("aeiouAEIOU"));
    }

    @Test
    @DisplayName("removeVowels: Should correctly remove vowels from a typical string")
    void testRemoveVowels_typicalString() {
        assertEquals("Hll Wrld", TextUtils.removeVowels("Hello World"));
    }

    @Test
    @DisplayName("removeVowels: Should handle strings with mixed case vowels")
    void testRemoveVowels_mixedCaseVowels() {
        assertEquals("Tht qck brwn fx jmps vr th lzy dg.", TextUtils.removeVowels("The quick brown fox jumps over the lazy dog."));
    }

    @Test
    @DisplayName("removeVowels: Should handle strings with special characters and numbers")
    void testRemoveVowels_withSpecialCharsAndNumbers() {
        assertEquals("1!2@3#4$5%", TextUtils.removeVowels("a1!e2@i3#o4$u5%"));
    }
}