package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    // Test cases for reverse method

    // Test case for null input
    @Test
    void testReverse_NullInput() {
        assertNull(TextUtils.reverse(null), "Should return null for null input");
    }

    // Test case for an empty string
    @Test
    void testReverse_EmptyString() {
        assertEquals("", TextUtils.reverse(""), "Should return an empty string for empty input");
    }

    // Test case for a single character string
    @Test
    void testReverse_SingleCharacterString() {
        assertEquals("a", TextUtils.reverse("a"), "Should return the same character for single character input");
    }

    // Test case for a simple even-length string
    @Test
    void testReverse_EvenLengthString() {
        assertEquals("dcba", TextUtils.reverse("abcd"), "Should correctly reverse an even-length string");
    }

    // Test case for a simple odd-length string
    @Test
    void testReverse_OddLengthString() {
        assertEquals("edcba", TextUtils.reverse("abcde"), "Should correctly reverse an odd-length string");
    }

    // Test case for a string with spaces
    @Test
    void testReverse_StringWithSpaces() {
        assertEquals("dlrow olleh", TextUtils.reverse("hello world"), "Should reverse string including spaces");
    }

    // Test case for a string with special characters
    @Test
    void testReverse_StringWithSpecialCharacters() {
        assertEquals("!@#$", TextUtils.reverse("$#@!"), "Should reverse string including special characters");
    }

    // Test case for a long string
    @Test
    void testReverse_LongString() {
        String longString = "This is a relatively long string to test the reverse method.";
        String reversedLongString = ".dohtem esrever eht tset ot gnirts gnol ylevitaler a si sihT";
        assertEquals(reversedLongString, TextUtils.reverse(longString), "Should correctly reverse a long string");
    }

    // Test cases for isPalindrome method

    // Test case for null input
    @Test
    void testIsPalindrome_NullInput() {
        assertFalse(TextUtils.isPalindrome(null), "Should return false for null input");
    }

    // Test case for an empty string
    @Test
    void testIsPalindrome_EmptyString() {
        assertTrue(TextUtils.isPalindrome(""), "Should consider an empty string as a palindrome");
    }

    // Test case for a single character string
    @Test
    void testIsPalindrome_SingleCharacter() {
        assertTrue(TextUtils.isPalindrome("a"), "Should consider a single character string as a palindrome");
    }

    // Test case for an even-length palindrome
    @Test
    void testIsPalindrome_EvenLengthPalindrome() {
        assertTrue(TextUtils.isPalindrome("abba"), "Should identify 'abba' as a palindrome");
    }

    // Test case for an odd-length palindrome
    @Test
    void testIsPalindrome_OddLengthPalindrome() {
        assertTrue(TextUtils.isPalindrome("madam"), "Should identify 'madam' as a palindrome");
    }

    // Test case for a non-palindrome string
    @Test
    void testIsPalindrome_NonPalindrome() {
        assertFalse(TextUtils.isPalindrome("hello"), "Should identify 'hello' as a non-palindrome");
    }

    // Test case for a palindrome with mixed case, spaces, and punctuation
    @Test
    void testIsPalindrome_ComplexPalindrome() {
        assertTrue(TextUtils.isPalindrome("A man, a plan, a canal: Panama"), "Should handle complex palindromes ignoring case and non-alphanumeric chars");
    }

    // Test case for a non-palindrome with mixed case, spaces, and punctuation
    @Test
    void testIsPalindrome_ComplexNonPalindrome() {
        assertFalse(TextUtils.isPalindrome("Hello, World!"), "Should correctly identify a complex non-palindrome");
    }

    // Test case for a palindrome with numbers
    @Test
    void testIsPalindrome_NumericPalindrome() {
        assertTrue(TextUtils.isPalindrome("12321"), "Should handle numeric palindromes");
    }

    // Test case for a palindrome with only non-alphanumeric characters (should become empty)
    @Test
    void testIsPalindrome_OnlySpecialCharacters() {
        assertTrue(TextUtils.isPalindrome("!@#$%^&*"), "Should be true if only special characters are present (cleaned string is empty)");
    }

    // Test cases for countVowels method

    // Test case for null input
    @Test
    void testCountVowels_NullInput() {
        assertEquals(0, TextUtils.countVowels(null), "Should return 0 for null input");
    }

    // Test case for an empty string
    @Test
    void testCountVowels_EmptyString() {
        assertEquals(0, TextUtils.countVowels(""), "Should return 0 for an empty string");
    }

    // Test case for a string with no vowels
    @Test
    void testCountVowels_NoVowels() {
        assertEquals(0, TextUtils.countVowels("rhythm"), "Should return 0 for a string with no vowels");
    }

    // Test case for a string with only lowercase vowels
    @Test
    void testCountVowels_OnlyLowercaseVowels() {
        assertEquals(5, TextUtils.countVowels("aeiou"), "Should count all lowercase vowels");
    }

    // Test case for a string with only uppercase vowels
    @Test
    void testCountVowels_OnlyUppercaseVowels() {
        assertEquals(5, TextUtils.countVowels("AEIOU"), "Should count all uppercase vowels");
    }

    // Test case for a string with mixed case vowels
    @Test
    void testCountVowels_MixedCaseVowels() {
        assertEquals(5, TextUtils.countVowels("AeiOu"), "Should count mixed case vowels");
    }

    // Test case for a string with mixed vowels and consonants
    @Test
    void testCountVowels_MixedVowelsAndConsonants() {
        assertEquals(3, TextUtils.countVowels("Hello World"), "Should count vowels in a mixed string (e, o, o)");
    }

    // Test case for a string with special characters and numbers
    @Test
    void testCountVowels_WithSpecialCharsAndNumbers() {
        assertEquals(5, TextUtils.countVowels("a!e@i#o$u123"), "Should count vowels ignoring special characters and numbers");
    }

    // Test case for a sentence
    @Test
    void testCountVowels_Sentence() {
        // The quick brown fox jumps over the lazy dog.
        // e, ui, o, o, u, o, e, e, a, o
        assertEquals(12, TextUtils.countVowels("The quick brown fox jumps over the lazy dog."), "Should count vowels in a sentence");
    }

    // Test cases for toUpperCase method

    // Test case for null input
    @Test
    void testToUpperCase_NullInput() {
        assertNull(TextUtils.toUpperCase(null), "Should return null for null input");
    }

    // Test case for an empty string
    @Test
    void testToUpperCase_EmptyString() {
        assertEquals("", TextUtils.toUpperCase(""), "Should return an empty string for empty input");
    }

    // Test case for an all lowercase string
    @Test
    void testToUpperCase_AllLowercase() {
        assertEquals("HELLO", TextUtils.toUpperCase("hello"), "Should convert all lowercase to uppercase");
    }

    // Test case for an all uppercase string
    @Test
    void testToUpperCase_AllUppercase() {
        assertEquals("HELLO", TextUtils.toUpperCase("HELLO"), "Should return the same string if already uppercase");
    }

    // Test case for a mixed case string
    @Test
    void testToUpperCase_MixedCase() {
        assertEquals("HELLO WORLD", TextUtils.toUpperCase("Hello World"), "Should convert mixed case to all uppercase");
    }

    // Test case for a string with numbers and special characters
    @Test
    void testToUpperCase_WithNumbersAndSpecialChars() {
        assertEquals("123!@#ABC", TextUtils.toUpperCase("123!@#abc"), "Should not change numbers or special characters, only letters");
    }

    // Test cases for removeVowels method

    // Test case for null input
    @Test
    void testRemoveVowels_NullInput() {
        assertNull(TextUtils.removeVowels(null), "Should return null for null input");
    }

    // Test case for an empty string
    @Test
    void testRemoveVowels_EmptyString() {
        assertEquals("", TextUtils.removeVowels(""), "Should return an empty string for empty input");
    }

    // Test case for a string with only vowels
    @Test
    void testRemoveVowels_OnlyVowels() {
        assertEquals("", TextUtils.removeVowels("aeiouAEIOU"), "Should return an empty string if all characters are vowels");
    }

    // Test case for a string with no vowels
    @Test
    void testRemoveVowels_NoVowels() {
        assertEquals("rhythm", TextUtils.removeVowels("rhythm"), "Should return the same string if it contains no vowels");
    }

    // Test case for a string with mixed vowels and consonants
    @Test
    void testRemoveVowels_MixedVowelsAndConsonants() {
        assertEquals("Hll Wrld", TextUtils.removeVowels("Hello World"), "Should remove all vowels from a mixed string");
    }

    // Test case for a string with leading/trailing vowels
    @Test
    void testRemoveVowels_LeadingTrailingVowels() {
        assertEquals("ppl", TextUtils.removeVowels("apple"), "Should remove leading/trailing vowels");
    }

    // Test case for a string with special characters and numbers
    @Test
    void testRemoveVowels_WithSpecialCharsAndNumbers() {
        assertEquals("!@#$", TextUtils.removeVowels("a!e@i#o$u"), "Should remove vowels while keeping special characters and numbers");
    }

    // Test case for a sentence
    @Test
    void testRemoveVowels_Sentence() {
        assertEquals("Th qck brwn fx jmps vr th lzy dg.", TextUtils.removeVowels("The quick brown fox jumps over the lazy dog."), "Should correctly remove vowels from a sentence");
    }
}