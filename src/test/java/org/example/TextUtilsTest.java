package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    // Test cases for reverse method

    // Test case for a standard string
    @Test
    void testReverse_standardString() {
        assertEquals("olleh", TextUtils.reverse("hello"));
    }

    // Test case for an empty string
    @Test
    void testReverse_emptyString() {
        assertEquals("", TextUtils.reverse(""));
    }

    // Test case for a null string
    @Test
    void testReverse_nullString() {
        assertNull(TextUtils.reverse(null));
    }

    // Test case for a single character string
    @Test
    void testReverse_singleCharacterString() {
        assertEquals("a", TextUtils.reverse("a"));
    }

    // Test case for a string with special characters and numbers
    @Test
    void testReverse_stringWithSpecialCharsAndNumbers() {
        assertEquals("!321bcA", TextUtils.reverse("Acb123!"));
    }

    // Test cases for isPalindrome method

    // Test case for a simple palindrome
    @Test
    void testIsPalindrome_simplePalindrome() {
        assertTrue(TextUtils.isPalindrome("madam"));
    }

    // Test case for a non-palindrome
    @Test
    void testIsPalindrome_notPalindrome() {
        assertFalse(TextUtils.isPalindrome("hello"));
    }

    // Test case for a palindrome with mixed case and spaces
    @Test
    void testIsPalindrome_mixedCaseAndSpacesPalindrome() {
        assertTrue(TextUtils.isPalindrome("Race Car"));
    }

    // Test case for a palindrome with punctuation and mixed case
    @Test
    void testIsPalindrome_complexPalindrome() {
        assertTrue(TextUtils.isPalindrome("A man, a plan, a canal: Panama"));
    }

    // Test case for an empty string (considered a palindrome)
    @Test
    void testIsPalindrome_emptyString() {
        assertTrue(TextUtils.isPalindrome(""));
    }

    // Test case for a null string
    @Test
    void testIsPalindrome_nullString() {
        assertFalse(TextUtils.isPalindrome(null));
    }

    // Test case for a single character string
    @Test
    void testIsPalindrome_singleCharacterString() {
        assertTrue(TextUtils.isPalindrome("a"));
    }

    // Test case for a string that becomes a palindrome after cleaning
    @Test
    void testIsPalindrome_stringBecomesPalindromeAfterCleaning() {
        assertTrue(TextUtils.isPalindrome("No lemon, no melon"));
    }

    // Test case for a non-palindrome with mixed chars
    @Test
    void testIsPalindrome_complexNotPalindrome() {
        assertFalse(TextUtils.isPalindrome("Hello, world!"));
    }


    // Test cases for countVowels method

    // Test case for a string with multiple vowels
    @Test
    void testCountVowels_standardString() {
        assertEquals(2, TextUtils.countVowels("hello"));
    }

    // Test case for a string with no vowels
    @Test
    void testCountVowels_noVowels() {
        assertEquals(0, TextUtils.countVowels("rhythm"));
    }

    // Test case for a string with mixed case vowels
    @Test
    void testCountVowels_mixedCaseVowels() {
        assertEquals(5, TextUtils.countVowels("AEIOUaeiou"));
    }

    // Test case for an empty string
    @Test
    void testCountVowels_emptyString() {
        assertEquals(0, TextUtils.countVowels(""));
    }

    // Test case for a null string
    @Test
    void testCountVowels_nullString() {
        assertEquals(0, TextUtils.countVowels(null));
    }

    // Test case for a string with only vowels
    @Test
    void testCountVowels_onlyVowels() {
        assertEquals(3, TextUtils.countVowels("aei"));
    }

    // Test case for a string with numbers and special characters but no vowels
    @Test
    void testCountVowels_numbersAndSpecialCharsNoVowels() {
        assertEquals(0, TextUtils.countVowels("123!@#$"));
    }

    // Test case for a string with numbers, special characters and vowels
    @Test
    void testCountVowels_numbersSpecialCharsAndVowels() {
        assertEquals(3, TextUtils.countVowels("a1e2i!@"));
    }


    // Test cases for toUpperCase method

    // Test case for a lowercase string
    @Test
    void testToUpperCase_lowercaseString() {
        assertEquals("HELLO", TextUtils.toUpperCase("hello"));
    }

    // Test case for an uppercase string
    @Test
    void testToUpperCase_uppercaseString() {
        assertEquals("HELLO", TextUtils.toUpperCase("HELLO"));
    }

    // Test case for a mixed case string
    @Test
    void testToUpperCase_mixedCaseString() {
        assertEquals("HELLO WORLD", TextUtils.toUpperCase("Hello World"));
    }

    // Test case for an empty string
    @Test
    void testToUpperCase_emptyString() {
        assertEquals("", TextUtils.toUpperCase(""));
    }

    // Test case for a null string
    @Test
    void testToUpperCase_nullString() {
        assertNull(TextUtils.toUpperCase(null));
    }

    // Test case for a string with numbers and special characters
    @Test
    void testToUpperCase_withNumbersAndSpecialChars() {
        assertEquals("HELLO123!@", TextUtils.toUpperCase("Hello123!@"));
    }


    // Test cases for removeVowels method

    // Test case for a standard string with vowels
    @Test
    void testRemoveVowels_standardString() {
        assertEquals("hll", TextUtils.removeVowels("hello"));
    }

    // Test case for a string with no vowels
    @Test
    void testRemoveVowels_noVowels() {
        assertEquals("rhythm", TextUtils.removeVowels("rhythm"));
    }

    // Test case for a string with only vowels
    @Test
    void testRemoveVowels_onlyVowels() {
        assertEquals("", TextUtils.removeVowels("AEIOUaeiou"));
    }

    // Test case for an empty string
    @Test
    void testRemoveVowels_emptyString() {
        assertEquals("", TextUtils.removeVowels(""));
    }

    // Test case for a null string
    @Test
    void testRemoveVowels_nullString() {
        assertNull(TextUtils.removeVowels(null));
    }

    // Test case for a string with mixed case vowels and other characters
    @Test
    void testRemoveVowels_mixedCaseVowelsAndOtherChars() {
        assertEquals("Ths s tst.", TextUtils.removeVowels("This is a Test."));
    }

    // Test case for a string with numbers and special characters
    @Test
    void testRemoveVowels_numbersAndSpecialChars() {
        assertEquals("123!@#$", TextUtils.removeVowels("123!@#$"));
    }

    // Test case for a string with vowels, numbers, and special characters
    @Test
    void testRemoveVowels_withAllTypes() {
        assertEquals("bcdfgh123!", TextUtils.removeVowels("abcdefghi123!"));
    }
}