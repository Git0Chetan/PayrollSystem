package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextFormatTest {

    // Test cases for reverse method

    // Test case for null input
    @Test
    void testReverse_NullInput() {
        assertNull(TextFormat.reverse(null), "Should return null for null input");
    }

    // Test case for empty string input
    @Test
    void testReverse_EmptyString() {
        assertEquals("", TextFormat.reverse(""), "Should return an empty string for an empty input");
    }

    // Test case for a single character string
    @Test
    void testReverse_SingleCharacter() {
        assertEquals("a", TextFormat.reverse("a"), "Should return the same character for single character input");
    }

    // Test case for a common string
    @Test
    void testReverse_StandardString() {
        assertEquals("olleh", TextFormat.reverse("hello"), "Should correctly reverse a standard string");
    }

    // Test case for a string with spaces and numbers
    @Test
    void testReverse_StringWithSpacesAndNumbers() {
        assertEquals("54321 dlrow olleh", TextFormat.reverse("hello world 12345"), "Should reverse strings including spaces and numbers");
    }

    // Test cases for isPalindrome method

    // Test case for null input
    @Test
    void testIsPalindrome_NullInput() {
        assertFalse(TextFormat.isPalindrome(null), "Should return false for null input");
    }

    // Test case for empty string input
    @Test
    void testIsPalindrome_EmptyString() {
        assertTrue(TextFormat.isPalindrome(""), "Should return true for an empty string");
    }

    // Test case for a single character string
    @Test
    void testIsPalindrome_SingleCharacter() {
        assertTrue(TextFormat.isPalindrome("a"), "Should return true for a single character string");
    }

    // Test case for a simple palindrome
    @Test
    void testIsPalindrome_SimplePalindrome() {
        assertTrue(TextFormat.isPalindrome("madam"), "Should return true for a simple palindrome");
    }

    // Test case for a simple non-palindrome
    @Test
    void testIsPalindrome_SimpleNonPalindrome() {
        assertFalse(TextFormat.isPalindrome("hello"), "Should return false for a simple non-palindrome");
    }

    // Test case for a palindrome with mixed case
    @Test
    void testIsPalindrome_MixedCasePalindrome() {
        assertTrue(TextFormat.isPalindrome("Madam"), "Should return true for a mixed-case palindrome");
    }

    // Test case for a palindrome with spaces and punctuation
    @Test
    void testIsPalindrome_ComplexPalindrome() {
        assertTrue(TextFormat.isPalindrome("A man, a plan, a canal: Panama"), "Should return true for a complex palindrome with spaces and punctuation");
    }

    // Test case for a non-palindrome with spaces and punctuation
    @Test
    void testIsPalindrome_ComplexNonPalindrome() {
        assertFalse(TextFormat.isPalindrome("Hello, world!"), "Should return false for a complex non-palindrome");
    }

    // Test case for a numeric palindrome
    @Test
    void testIsPalindrome_NumericPalindrome() {
        assertTrue(TextFormat.isPalindrome("12321"), "Should return true for a numeric palindrome");
    }

    // Test cases for toUpperCase method

    // Test case for null input
    @Test
    void testToUpperCase_NullInput() {
        assertNull(TextFormat.toUpperCase(null), "Should return null for null input");
    }

    // Test case for empty string input
    @Test
    void testToUpperCase_EmptyString() {
        assertEquals("", TextFormat.toUpperCase(""), "Should return an empty string for an empty input");
    }

    // Test case for an all lowercase string
    @Test
    void testToUpperCase_AllLowerCase() {
        assertEquals("HELLO", TextFormat.toUpperCase("hello"), "Should convert all lowercase to uppercase");
    }

    // Test case for an all uppercase string
    @Test
    void testToUpperCase_AllUpperCase() {
        assertEquals("WORLD", TextFormat.toUpperCase("WORLD"), "Should return the same string if already uppercase");
    }

    // Test case for a mixed case string
    @Test
    void testToUpperCase_MixedCase() {
        assertEquals("JAVA PROGRAMMING", TextFormat.toUpperCase("Java Programming"), "Should convert mixed case to all uppercase");
    }

    // Test case for a string with numbers and special characters
    @Test
    void testToUpperCase_NumbersAndSpecialChars() {
        assertEquals("123!@# ABC", TextFormat.toUpperCase("123!@# abc"), "Should preserve numbers and special characters, convert letters");
    }

    // Test cases for countOccurrences method

    // Test case for null text input
    @Test
    void testCountOccurrences_NullText() {
        assertEquals(0, TextFormat.countOccurrences(null, "a"), "Should return 0 if text is null");
    }

    // Test case for null sub input
    @Test
    void testCountOccurrences_NullSub() {
        assertEquals(0, TextFormat.countOccurrences("abc", null), "Should return 0 if sub is null");
    }

    // Test case for empty sub input
    @Test
    void testCountOccurrences_EmptySub() {
        assertEquals(0, TextFormat.countOccurrences("abc", ""), "Should return 0 if sub is empty");
    }

    // Test case for empty text input
    @Test
    void testCountOccurrences_EmptyText() {
        assertEquals(0, TextFormat.countOccurrences("", "a"), "Should return 0 if text is empty");
    }

    // Test case where sub is not found
    @Test
    void testCountOccurrences_NotFound() {
        assertEquals(0, TextFormat.countOccurrences("hello", "z"), "Should return 0 if substring is not found");
    }

    // Test case for single occurrence
    @Test
    void testCountOccurrences_SingleOccurrence() {
        assertEquals(1, TextFormat.countOccurrences("hello world", "world"), "Should return 1 for a single occurrence");
    }

    // Test case for multiple non-overlapping occurrences
    @Test
    void testCountOccurrences_MultipleNonOverlapping() {
        assertEquals(2, TextFormat.countOccurrences("banana", "ana"), "Should count non-overlapping occurrences correctly");
    }

    // Test case for multiple simple occurrences
    @Test
    void testCountOccurrences_MultipleSimple() {
        assertEquals(3, TextFormat.countOccurrences("test test test", "test"), "Should count simple multiple occurrences");
    }

    // Test case with overlapping potential, but only non-overlapping counted
    @Test
    void testCountOccurrences_OverlappingPotential() {
        assertEquals(2, TextFormat.countOccurrences("aaaaa", "aa"), "Should count only non-overlapping 'aa' occurrences in 'aaaaa'");
    }

    // Test case where sub is longer than text
    @Test
    void testCountOccurrences_SubLongerThanText() {
        assertEquals(0, TextFormat.countOccurrences("abc", "abcd"), "Should return 0 if sub is longer than text");
    }

    // Test case for identical text and sub
    @Test
    void testCountOccurrences_TextEqualsSub() {
        assertEquals(1, TextFormat.countOccurrences("abc", "abc"), "Should return 1 if text and sub are identical");
    }

    // Test cases for removeVowels method

    // Test case for null input
    @Test
    void testRemoveVowels_NullInput() {
        assertNull(TextFormat.removeVowels(null), "Should return null for null input");
    }

    // Test case for empty string input
    @Test
    void testRemoveVowels_EmptyString() {
        assertEquals("", TextFormat.removeVowels(""), "Should return an empty string for an empty input");
    }

    // Test case for a string with all vowels
    @Test
    void testRemoveVowels_AllVowels() {
        assertEquals("", TextFormat.removeVowels("AEIOUaeiou"), "Should remove all vowels, leaving an empty string");
    }

    // Test case for a string with no vowels
    @Test
    void testRemoveVowels_NoVowels() {
        assertEquals("rhythm", TextFormat.removeVowels("rhythm"), "Should return the same string if no vowels are present");
    }

    // Test case for a common string with mixed case vowels
    @Test
    void testRemoveVowels_MixedCaseVowels() {
        assertEquals("hll", TextFormat.removeVowels("hello"), "Should remove both uppercase and lowercase vowels");
        assertEquals("WRLD", TextFormat.removeVowels("WORLD"), "Should remove uppercase vowels");
    }

    // Test case for a string with numbers and special characters
    @Test
    void testRemoveVowels_NumbersAndSpecialChars() {
        assertEquals("123!@#", TextFormat.removeVowels("123!@#"), "Should preserve numbers and special characters");
    }

    // Test case for a phrase
    @Test
    void testRemoveVowels_Phrase() {
        assertEquals("Jv Prgrmmng", TextFormat.removeVowels("Java Programming"), "Should remove vowels from a phrase");
    }

    // Test case for a string with only consonants
    @Test
    void testRemoveVowels_OnlyConsonants() {
        assertEquals("str", TextFormat.removeVowels("str"), "Should return string with only consonants unchanged");
    }
}