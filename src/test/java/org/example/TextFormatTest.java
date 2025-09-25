package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextFormatTest {

    // Test cases for the reverse method

    // Test case for null input
    @Test
    void testReverse_NullInput() {
        assertNull(TextFormat.reverse(null), "Should return null for null input.");
    }

    // Test case for an empty string
    @Test
    void testReverse_EmptyString() {
        assertEquals("", TextFormat.reverse(""), "Should return an empty string for empty input.");
    }

    // Test case for a single character string
    @Test
    void testReverse_SingleCharacterString() {
        assertEquals("a", TextFormat.reverse("a"), "Should return the same string for a single character.");
    }

    // Test case for a typical word
    @Test
    void testReverse_TypicalWord() {
        assertEquals("olleh", TextFormat.reverse("hello"), "Should correctly reverse a typical word.");
    }

    // Test case for a phrase with spaces and special characters
    @Test
    void testReverse_PhraseWithSpacesAndSpecialChars() {
        assertEquals("!dlrow olleh", TextFormat.reverse("hello world!"), "Should reverse phrase with spaces and special characters.");
    }

    // Test case for a string with numbers
    @Test
    void testReverse_StringWithNumbers() {
        assertEquals("321cba", TextFormat.reverse("abc123"), "Should reverse string containing numbers.");
    }

    // Test cases for the isPalindrome method

    // Test case for null input
    @Test
    void testIsPalindrome_NullInput() {
        assertFalse(TextFormat.isPalindrome(null), "Should return false for null input.");
    }

    // Test case for an empty string
    @Test
    void testIsPalindrome_EmptyString() {
        assertTrue(TextFormat.isPalindrome(""), "Should return true for an empty string after cleaning.");
    }

    // Test case for a single character string
    @Test
    void testIsPalindrome_SingleCharacterString() {
        assertTrue(TextFormat.isPalindrome("a"), "Should return true for a single character string.");
    }

    // Test case for a simple palindrome
    @Test
    void testIsPalindrome_SimplePalindrome() {
        assertTrue(TextFormat.isPalindrome("madam"), "Should return true for 'madam'.");
    }

    // Test case for a palindrome with mixed case
    @Test
    void testIsPalindrome_MixedCasePalindrome() {
        assertTrue(TextFormat.isPalindrome("Racecar"), "Should return true for 'Racecar' (case-insensitive).");
    }

    // Test case for a palindrome with spaces and special characters
    @Test
    void testIsPalindrome_ComplexPalindrome() {
        assertTrue(TextFormat.isPalindrome("A man, a plan, a canal: Panama"), "Should return true for a complex palindrome (ignoring non-letters).");
    }

    // Test case for a palindrome with numbers and special characters
    @Test
    void testIsPalindrome_PalindromeWithNumbersAndSpecialChars() {
        assertTrue(TextFormat.isPalindrome("Eva, can I stab a crab in a cave?"), "Should return true for 'Eva, can I stab a crab in a cave?'.");
    }

    // Test case for a non-palindrome
    @Test
    void testIsPalindrome_NonPalindrome() {
        assertFalse(TextFormat.isPalindrome("hello"), "Should return false for a non-palindrome 'hello'.");
    }

    // Test case for a non-palindrome with mixed case and spaces
    @Test
    void testIsPalindrome_NonPalindromeMixedCase() {
        assertFalse(TextFormat.isPalindrome("Java test"), "Should return false for 'Java test'.");
    }

    // Test cases for the toUpperCase method

    // Test case for null input
    @Test
    void testToUpperCase_NullInput() {
        assertNull(TextFormat.toUpperCase(null), "Should return null for null input.");
    }

    // Test case for an empty string
    @Test
    void testToUpperCase_EmptyString() {
        assertEquals("", TextFormat.toUpperCase(""), "Should return an empty string for empty input.");
    }

    // Test case for an already uppercase string
    @Test
    void testToUpperCase_AlreadyUpperCase() {
        assertEquals("HELLO", TextFormat.toUpperCase("HELLO"), "Should return the same string if already uppercase.");
    }

    // Test case for a lowercase string
    @Test
    void testToUpperCase_LowerCaseString() {
        assertEquals("HELLO", TextFormat.toUpperCase("hello"), "Should convert lowercase to uppercase.");
    }

    // Test case for a mixed case string
    @Test
    void testToUpperCase_MixedCaseString() {
        assertEquals("HELLO WORLD", TextFormat.toUpperCase("HeLlO wOrLd"), "Should convert mixed case to uppercase.");
    }

    // Test case for string with numbers and special characters
    @Test
    void testToUpperCase_StringWithNumbersAndSpecialChars() {
        assertEquals("JAVA 123!@#", TextFormat.toUpperCase("java 123!@#"), "Should convert letters to uppercase, leave others unchanged.");
    }

    // Test cases for the countOccurrences method

    // Test case for null text input
    @Test
    void testCountOccurrences_NullTextInput() {
        assertEquals(0, TextFormat.countOccurrences(null, "sub"), "Should return 0 for null text input.");
    }

    // Test case for null sub input
    @Test
    void testCountOccurrences_NullSubInput() {
        assertEquals(0, TextFormat.countOccurrences("text", null), "Should return 0 for null sub input.");
    }

    // Test case for empty sub string
    @Test
    void testCountOccurrences_EmptySubString() {
        assertEquals(0, TextFormat.countOccurrences("text", ""), "Should return 0 for an empty sub string.");
    }

    // Test case where sub is not found
    @Test
    void testCountOccurrences_SubNotFound() {
        assertEquals(0, TextFormat.countOccurrences("hello world", "xyz"), "Should return 0 when sub is not found.");
    }

    // Test case where sub is found once
    @Test
    void testCountOccurrences_SubFoundOnce() {
        assertEquals(1, TextFormat.countOccurrences("hello world", "world"), "Should return 1 when sub is found once.");
    }

    // Test case where sub is found multiple times (non-overlapping)
    @Test
    void testCountOccurrences_SubFoundMultipleTimesNonOverlapping() {
        assertEquals(2, TextFormat.countOccurrences("banana", "ana"), "Should count non-overlapping occurrences correctly.");
    }

    // Test case where sub is found multiple times (overlapping, handled non-overlapping by implementation)
    @Test
    void testCountOccurrences_SubFoundMultipleTimesOverlapping() {
        assertEquals(3, TextFormat.countOccurrences("aaaaa", "aa"), "Should count non-overlapping occurrences for overlapping subs.");
    }

    // Test case for case-sensitive matching
    @Test
    void testCountOccurrences_CaseSensitive() {
        assertEquals(0, TextFormat.countOccurrences("Hello World", "hello"), "Should be case-sensitive, no match for different case.");
        assertEquals(1, TextFormat.countOccurrences("Hello World", "World"), "Should find case-sensitive match.");
    }

    // Test case for sub at the beginning
    @Test
    void testCountOccurrences_SubAtBeginning() {
        assertEquals(1, TextFormat.countOccurrences("apple pie", "apple"), "Should find sub at the beginning.");
    }

    // Test case for sub at the end
    @Test
    void testCountOccurrences_SubAtEnd() {
        assertEquals(1, TextFormat.countOccurrences("apple pie", "pie"), "Should find sub at the end.");
    }

    // Test case for empty text
    @Test
    void testCountOccurrences_EmptyText() {
        assertEquals(0, TextFormat.countOccurrences("", "a"), "Should return 0 for empty text.");
    }

    // Test case for sub longer than text
    @Test
    void testCountOccurrences_SubLongerThanText() {
        assertEquals(0, TextFormat.countOccurrences("short", "longer_sub"), "Should return 0 if sub is longer than text.");
    }

    // Test cases for the removeVowels method

    // Test case for null input
    @Test
    void testRemoveVowels_NullInput() {
        assertNull(TextFormat.removeVowels(null), "Should return null for null input.");
    }

    // Test case for an empty string
    @Test
    void testRemoveVowels_EmptyString() {
        assertEquals("", TextFormat.removeVowels(""), "Should return an empty string for empty input.");
    }

    // Test case for a string with no vowels
    @Test
    void testRemoveVowels_NoVowels() {
        assertEquals("rhythm", TextFormat.removeVowels("rhythm"), "Should return the same string if no vowels are present.");
    }

    // Test case for a string with only vowels
    @Test
    void testRemoveVowels_OnlyVowels() {
        assertEquals("", TextFormat.removeVowels("AEIOUaeiou"), "Should return an empty string if only vowels are present.");
    }

    // Test case for a typical string with vowels
    @Test
    void testRemoveVowels_TypicalString() {
        assertEquals("Hll Wrld", TextFormat.removeVowels("Hello World"), "Should remove all vowels from a typical string.");
    }

    // Test case for mixed case vowels
    @Test
    void testRemoveVowels_MixedCaseVowels() {
        assertEquals("Xmpl", TextFormat.removeVowels("Example"), "Should remove mixed case vowels.");
    }

    // Test case for string with numbers and special characters
    @Test
    void testRemoveVowels_StringWithNumbersAndSpecialChars() {
        assertEquals("Jv 123!@", TextFormat.removeVowels("Java 123!@"), "Should remove vowels, leave other characters untouched.");
    }

    // Test case for string starting and ending with vowels
    @Test
    void testRemoveVowels_StartsAndEndsWithVowel() {
        assertEquals("pll", TextFormat.removeVowels("Apple"), "Should handle words starting and ending with vowels.");
    }
}