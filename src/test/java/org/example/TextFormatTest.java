package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextFormatTest {

    // Test cases for reverse method

    // Test case for null input
    @Test
    @DisplayName("reverse: Should return null when input is null")
    void reverse_nullInput_returnsNull() {
        assertNull(TextFormat.reverse(null), "Should return null for null input");
    }

    // Test case for empty string input
    @Test
    @DisplayName("reverse: Should return empty string when input is empty")
    void reverse_emptyString_returnsEmptyString() {
        assertEquals("", TextFormat.reverse(""), "Should return empty string for empty input");
    }

    // Test case for a single character string
    @Test
    @DisplayName("reverse: Should return same string for single character input")
    void reverse_singleCharacterString_returnsSameString() {
        assertEquals("a", TextFormat.reverse("a"), "Should return 'a' for 'a'");
    }

    // Test case for a simple word
    @Test
    @DisplayName("reverse: Should correctly reverse a simple word")
    void reverse_simpleWord_returnsReversedWord() {
        assertEquals("olleh", TextFormat.reverse("hello"), "Should reverse 'hello' to 'olleh'");
    }

    // Test case for a phrase with spaces
    @Test
    @DisplayName("reverse: Should correctly reverse a phrase with spaces")
    void reverse_phraseWithSpaces_returnsReversedPhrase() {
        assertEquals("dlrow olleh", TextFormat.reverse("hello world"), "Should reverse 'hello world' to 'dlrow olleh'");
    }

    // Test case for a phrase with special characters and numbers
    @Test
    @DisplayName("reverse: Should correctly reverse a string with special characters and numbers")
    void reverse_specialCharactersAndNumbers_returnsReversedString() {
        assertEquals("123!@#cba", TextFormat.reverse("abc#!@321"), "Should reverse 'abc#!@321'");
    }

    // Test cases for isPalindrome method

    // Test case for null input
    @Test
    @DisplayName("isPalindrome: Should return false when input is null")
    void isPalindrome_nullInput_returnsFalse() {
        assertFalse(TextFormat.isPalindrome(null), "Should return false for null input");
    }

    // Test case for empty string input
    @Test
    @DisplayName("isPalindrome: Should return true for empty string")
    void isPalindrome_emptyString_returnsTrue() {
        assertTrue(TextFormat.isPalindrome(""), "Empty string should be considered a palindrome");
    }

    // Test case for a single character string
    @Test
    @DisplayName("isPalindrome: Should return true for single character string")
    void isPalindrome_singleCharacterString_returnsTrue() {
        assertTrue(TextFormat.isPalindrome("a"), "Single character 'a' should be a palindrome");
    }

    // Test case for a simple palindrome (even length)
    @Test
    @DisplayName("isPalindrome: Should return true for an even length palindrome")
    void isPalindrome_evenLengthPalindrome_returnsTrue() {
        assertTrue(TextFormat.isPalindrome("madam"), "madam should be a palindrome");
    }

    // Test case for a simple palindrome (odd length)
    @Test
    @DisplayName("isPalindrome: Should return true for an odd length palindrome")
    void isPalindrome_oddLengthPalindrome_returnsTrue() {
        assertTrue(TextFormat.isPalindrome("level"), "level should be a palindrome");
    }

    // Test case for a palindrome with mixed case
    @Test
    @DisplayName("isPalindrome: Should return true for a mixed case palindrome")
    void isPalindrome_mixedCasePalindrome_returnsTrue() {
        assertTrue(TextFormat.isPalindrome("Racecar"), "Racecar should be a palindrome (case insensitive)");
    }

    // Test case for a palindrome with spaces and special characters
    @Test
    @DisplayName("isPalindrome: Should return true for a complex palindrome with spaces and punctuation")
    void isPalindrome_complexPalindrome_returnsTrue() {
        assertTrue(TextFormat.isPalindrome("A man, a plan, a canal: Panama"), "A man, a plan, a canal: Panama should be a palindrome");
    }

    // Test case for a non-palindrome
    @Test
    @DisplayName("isPalindrome: Should return false for a non-palindrome")
    void isPalindrome_notPalindrome_returnsFalse() {
        assertFalse(TextFormat.isPalindrome("hello"), "hello should not be a palindrome");
    }

    // Test case for a non-palindrome with mixed case and spaces
    @Test
    @DisplayName("isPalindrome: Should return false for a non-palindrome with mixed case and spaces")
    void isPalindrome_notPalindromeMixedCaseAndSpaces_returnsFalse() {
        assertFalse(TextFormat.isPalindrome("Hello World"), "Hello World should not be a palindrome");
    }

    // Test case for a string that is almost a palindrome but not quite
    @Test
    @DisplayName("isPalindrome: Should return false for an almost palindrome")
    void isPalindrome_almostPalindrome_returnsFalse() {
        assertFalse(TextFormat.isPalindrome("abacabaX"), "abacabaX should not be a palindrome");
    }

    // Test cases for toUpperCase method

    // Test case for null input
    @Test
    @DisplayName("toUpperCase: Should return null when input is null")
    void toUpperCase_nullInput_returnsNull() {
        assertNull(TextFormat.toUpperCase(null), "Should return null for null input");
    }

    // Test case for empty string input
    @Test
    @DisplayName("toUpperCase: Should return empty string when input is empty")
    void toUpperCase_emptyString_returnsEmptyString() {
        assertEquals("", TextFormat.toUpperCase(""), "Should return empty string for empty input");
    }

    // Test case for an all lowercase string
    @Test
    @DisplayName("toUpperCase: Should convert an all lowercase string to uppercase")
    void toUpperCase_allLowerCase_returnsAllUpperCase() {
        assertEquals("HELLO", TextFormat.toUpperCase("hello"), "Should convert 'hello' to 'HELLO'");
    }

    // Test case for an all uppercase string
    @Test
    @DisplayName("toUpperCase: Should return same string for an all uppercase string")
    void toUpperCase_allUpperCase_returnsSameString() {
        assertEquals("WORLD", TextFormat.toUpperCase("WORLD"), "Should return 'WORLD' for 'WORLD'");
    }

    // Test case for a mixed case string
    @Test
    @DisplayName("toUpperCase: Should convert a mixed case string to all uppercase")
    void toUpperCase_mixedCase_returnsAllUpperCase() {
        assertEquals("JAVATEST", TextFormat.toUpperCase("JavaTest"), "Should convert 'JavaTest' to 'JAVATEST'");
    }

    // Test case for a string with numbers and special characters
    @Test
    @DisplayName("toUpperCase: Should keep numbers and special characters unchanged while uppercasing letters")
    void toUpperCase_withNumbersAndSpecialChars_convertsLettersOnly() {
        assertEquals("123!@#ABC", TextFormat.toUpperCase("123!@#abc"), "Should convert '123!@#abc' to '123!@#ABC'");
    }

    // Test cases for countOccurrences method

    // Test case for null text
    @Test
    @DisplayName("countOccurrences: Should return 0 when text is null")
    void countOccurrences_nullText_returnsZero() {
        assertEquals(0, TextFormat.countOccurrences(null, "sub"), "Should return 0 when text is null");
    }

    // Test case for null sub
    @Test
    @DisplayName("countOccurrences: Should return 0 when sub is null")
    void countOccurrences_nullSub_returnsZero() {
        assertEquals(0, TextFormat.countOccurrences("text", null), "Should return 0 when sub is null");
    }

    // Test case for empty sub string
    @Test
    @DisplayName("countOccurrences: Should return 0 when sub is empty")
    void countOccurrences_emptySub_returnsZero() {
        assertEquals(0, TextFormat.countOccurrences("text", ""), "Should return 0 when sub is empty");
    }

    // Test case for no occurrences
    @Test
    @DisplayName("countOccurrences: Should return 0 when sub is not found")
    void countOccurrences_noOccurrences_returnsZero() {
        assertEquals(0, TextFormat.countOccurrences("hello world", "java"), "Should return 0 when 'java' is not found");
    }

    // Test case for a single occurrence
    @Test
    @DisplayName("countOccurrences: Should return 1 for a single occurrence")
    void countOccurrences_singleOccurrence_returnsOne() {
        assertEquals(1, TextFormat.countOccurrences("hello world", "world"), "Should return 1 for 'world'");
    }

    // Test case for multiple non-overlapping occurrences
    @Test
    @DisplayName("countOccurrences: Should return correct count for multiple non-overlapping occurrences")
    void countOccurrences_multipleNonOverlapping_returnsCorrectCount() {
        assertEquals(2, TextFormat.countOccurrences("ababab", "ab"), "Should return 2 for 'ab' in 'ababab'");
    }

    // Test case for multiple non-overlapping occurrences with spacing
    @Test
    @DisplayName("countOccurrences: Should return correct count for multiple non-overlapping occurrences with spacing")
    void countOccurrences_multipleNonOverlappingWithSpace_returnsCorrectCount() {
        assertEquals(2, TextFormat.countOccurrences("banana banana", "ana"), "Should return 2 for 'ana' in 'banana banana'");
    }

    // Test case for overlapping occurrences (expect non-overlapping count)
    @Test
    @DisplayName("countOccurrences: Should handle overlapping occurrences by counting non-overlapping only")
    void countOccurrences_overlappingOccurrences_returnsNonOverlappingCount() {
        assertEquals(2, TextFormat.countOccurrences("aaaaa", "aa"), "Should return 2 for 'aa' in 'aaaaa' (non-overlapping)");
    }

    // Test case for sub string at the beginning
    @Test
    @DisplayName("countOccurrences: Should count sub at the beginning of the text")
    void countOccurrences_subAtBeginning_returnsCorrectCount() {
        assertEquals(1, TextFormat.countOccurrences("startsWith", "starts"), "Should count 'starts' at the beginning");
    }

    // Test case for sub string at the end
    @Test
    @DisplayName("countOccurrences: Should count sub at the end of the text")
    void countOccurrences_subAtEnd_returnsCorrectCount() {
        assertEquals(1, TextFormat.countOccurrences("endsWith", "Ends"), "Should count 'Ends' at the end");
    }

    // Test case for case sensitivity
    @Test
    @DisplayName("countOccurrences: Should be case sensitive")
    void countOccurrences_caseSensitivity_returnsZeroIfCaseMismatch() {
        assertEquals(0, TextFormat.countOccurrences("Hello World", "hello"), "Should return 0 due to case sensitivity");
    }

    // Test case where text is shorter than sub
    @Test
    @DisplayName("countOccurrences: Should return 0 when text is shorter than sub")
    void countOccurrences_textShorterThanSub_returnsZero() {
        assertEquals(0, TextFormat.countOccurrences("hi", "hello"), "Should return 0 when text is shorter than sub");
    }

    // Test cases for removeVowels method

    // Test case for null input
    @Test
    @DisplayName("removeVowels: Should return null when input is null")
    void removeVowels_nullInput_returnsNull() {
        assertNull(TextFormat.removeVowels(null), "Should return null for null input");
    }

    // Test case for empty string input
    @Test
    @DisplayName("removeVowels: Should return empty string when input is empty")
    void removeVowels_emptyString_returnsEmptyString() {
        assertEquals("", TextFormat.removeVowels(""), "Should return empty string for empty input");
    }

    // Test case for a string with no vowels
    @Test
    @DisplayName("removeVowels: Should return the same string if it contains no vowels")
    void removeVowels_noVowels_returnsSameString() {
        assertEquals("rhythm", TextFormat.removeVowels("rhythm"), "Should return 'rhythm' for 'rhythm'");
    }

    // Test case for a string with only vowels (lowercase)
    @Test
    @DisplayName("removeVowels: Should return empty string for all lowercase vowels")
    void removeVowels_onlyLowerCaseVowels_returnsEmptyString() {
        assertEquals("", TextFormat.removeVowels("aeiou"), "Should return empty for 'aeiou'");
    }

    // Test case for a string with only vowels (uppercase)
    @Test
    @DisplayName("removeVowels: Should return empty string for all uppercase vowels")
    void removeVowels_onlyUpperCaseVowels_returnsEmptyString() {
        assertEquals("", TextFormat.removeVowels("AEIOU"), "Should return empty for 'AEIOU'");
    }

    // Test case for a string with mixed case vowels
    @Test
    @DisplayName("removeVowels: Should remove mixed case vowels")
    void removeVowels_mixedCaseVowels_returnsEmptyString() {
        assertEquals("", TextFormat.removeVowels("AaEeIiOoUu"), "Should remove all mixed case vowels");
    }

    // Test case for a typical string with mixed vowels and consonants
    @Test
    @DisplayName("removeVowels: Should correctly remove vowels from a standard sentence")
    void removeVowels_standardString_removesVowels() {
        assertEquals("Hll Wrld", TextFormat.removeVowels("Hello World"), "Should remove vowels from 'Hello World'");
    }

    // Test case for a string with numbers and special characters
    @Test
    @DisplayName("removeVowels: Should remove vowels and keep numbers/special characters")
    void removeVowels_withNumbersAndSpecialChars_removesVowelsOnly() {
        assertEquals("123!@#bc", TextFormat.removeVowels("123!@#abc"), "Should remove vowels from '123!@#abc'");
    }

    // Test case for a long string
    @Test
    @DisplayName("removeVowels: Should handle long strings correctly")
    void removeVowels_longString_removesVowels() {
        String longString = "The quick brown fox jumps over the lazy dog";
        String expected = "Th qck brwn fx jmps vr th lzy dg";
        assertEquals(expected, TextFormat.removeVowels(longString), "Should remove vowels from a long string");
    }
}