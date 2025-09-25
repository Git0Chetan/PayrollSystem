package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// No Mockito imports are needed as the Utility class has no external dependencies to mock.

public class UtilityTest {

    // Test cases for the layer method

    // Test case for two positive integers
    @Test
    void testLayer_PositiveNumbers() {
        assertEquals(5, Utility.layer(2, 3));
    }

    // Test case for two negative integers
    @Test
    void testLayer_NegativeNumbers() {
        assertEquals(-5, Utility.layer(-2, -3));
    }

    // Test case for one positive and one negative integer
    @Test
    void testLayer_MixedSigns() {
        assertEquals(1, Utility.layer(5, -4));
        assertEquals(-1, Utility.layer(-5, 4));
    }

    // Test case for one zero and one positive integer
    @Test
    void testLayer_WithZeroPositive() {
        assertEquals(7, Utility.layer(0, 7));
    }

    // Test case for one zero and one negative integer
    @Test
    void testLayer_WithZeroNegative() {
        assertEquals(-7, Utility.layer(0, -7));
    }

    // Test case for two zeros
    @Test
    void testLayer_TwoZeros() {
        assertEquals(0, Utility.layer(0, 0));
    }

    // Test cases for the joinAndUpper method

    // Test case for two non-null strings
    @Test
    void testJoinAndUpper_TwoNonNullStrings() {
        assertEquals("HELLOWORLD", Utility.joinAndUpper("hello", "world"));
    }

    // Test case for the first string being null
    @Test
    void testJoinAndUpper_FirstStringNull() {
        assertEquals("WORLD", Utility.joinAndUpper(null, "world"));
    }

    // Test case for the second string being null
    @Test
    void testJoinAndUpper_SecondStringNull() {
        assertEquals("HELLO", Utility.joinAndUpper("hello", null));
    }

    // Test case for both strings being null
    @Test
    void testJoinAndUpper_BothStringsNull() {
        assertEquals("", Utility.joinAndUpper(null, null));
    }

    // Test case for empty strings
    @Test
    void testJoinAndUpper_EmptyStrings() {
        assertEquals("", Utility.joinAndUpper("", ""));
        assertEquals("HELLO", Utility.joinAndUpper("hello", ""));
        assertEquals("WORLD", Utility.joinAndUpper("", "world"));
    }

    // Test case for strings with spaces and special characters
    @Test
    void testJoinAndUpper_SpecialCharacters() {
        assertEquals("HELLO WORLD!", Utility.joinAndUpper("hello ", "world!"));
        assertEquals("A B C D", Utility.joinAndUpper("a b", " c d"));
    }

    // Test cases for the reverse method

    // Test case for a non-null, non-empty string
    @Test
    void testReverse_NonNullNonEmptyString() {
        assertEquals("olleh", Utility.reverse("hello"));
    }

    // Test case for an empty string
    @Test
    void testReverse_EmptyString() {
        assertEquals("", Utility.reverse(""));
    }

    // Test case for a null string
    @Test
    void testReverse_NullString() {
        assertNull(Utility.reverse(null));
    }

    // Test case for a single character string
    @Test
    void testReverse_SingleCharacterString() {
        assertEquals("a", Utility.reverse("a"));
    }

    // Test case for a string with spaces
    @Test
    void testReverse_StringWithSpaces() {
        assertEquals("dlrow olleh", Utility.reverse("hello world"));
    }

    // Test case for a palindrome string
    @Test
    void testReverse_PalindromeString() {
        assertEquals("madam", Utility.reverse("madam"));
    }

    // Test cases for the isValuable method (isPrime)

    // Test case for numbers less than or equal to 1
    @Test
    void testIsValuable_NumbersLessThanOrEqualToOne() {
        assertFalse(Utility.isValuable(0));
        assertFalse(Utility.isValuable(1));
        assertFalse(Utility.isValuable(-5));
    }

    // Test case for small prime numbers (2 and 3)
    @Test
    void testIsValuable_SmallPrimes() {
        assertTrue(Utility.isValuable(2));
        assertTrue(Utility.isValuable(3));
    }

    // Test case for small composite numbers
    @Test
    void testIsValuable_SmallComposites() {
        assertFalse(Utility.isValuable(4)); // 2*2
        assertFalse(Utility.isValuable(6)); // 2*3
        assertFalse(Utility.isValuable(9)); // 3*3
    }

    // Test case for a prime number
    @Test
    void testIsValuable_PrimeNumber() {
        assertTrue(Utility.isValuable(5));
        assertTrue(Utility.isValuable(7));
        assertTrue(Utility.isValuable(11));
        assertTrue(Utility.isValuable(13));
        assertTrue(Utility.isValuable(17));
        assertTrue(Utility.isValuable(19));
        assertTrue(Utility.isValuable(23));
        assertTrue(Utility.isValuable(29));
    }

    // Test case for a composite number
    @Test
    void testIsValuable_CompositeNumber() {
        assertFalse(Utility.isValuable(10));
        assertFalse(Utility.isValuable(15));
        assertFalse(Utility.isValuable(21));
        assertFalse(Utility.isValuable(25));
        assertFalse(Utility.isValuable(33));
        assertFalse(Utility.isValuable(100));
    }

    // Test case for a larger prime number
    @Test
    void testIsValuable_LargerPrimeNumber() {
        assertTrue(Utility.isValuable(97));
        assertTrue(Utility.isValuable(101));
    }

    // Test cases for the gcd method

    // Test case for two positive integers
    @Test
    void testGcd_TwoPositiveNumbers() {
        assertEquals(6, Utility.gcd(48, 18)); // GCD(48, 18) = 6
        assertEquals(1, Utility.gcd(17, 23)); // Relatively prime
        assertEquals(12, Utility.gcd(12, 36)); // One is a multiple of the other
    }

    // Test case for one number being zero
    @Test
    void testGcd_OneNumberZero() {
        assertEquals(5, Utility.gcd(0, 5));
        assertEquals(5, Utility.gcd(5, 0));
        assertEquals(10, Utility.gcd(0, 10));
    }

    // Test case for both numbers being zero
    @Test
    void testGcd_BothNumbersZero() {
        assertEquals(0, Utility.gcd(0, 0)); // Standard behavior for GCD(0,0) is 0
    }

    // Test case for negative numbers
    @Test
    void testGcd_NegativeNumbers() {
        assertEquals(6, Utility.gcd(-48, 18));
        assertEquals(6, Utility.gcd(48, -18));
        assertEquals(6, Utility.gcd(-48, -18));
        assertEquals(1, Utility.gcd(-17, 23));
    }

    // Test case for large numbers
    @Test
    void testGcd_LargeNumbers() {
        assertEquals(10, Utility.gcd(100, 250));
        assertEquals(1, Utility.gcd(99991, 99989)); // Two large primes or relatively prime
    }

    // Test cases for the Order_Ascending method

    // Test case for a typical array
    @Test
    void testOrderAscending_TypicalArray() {
        int[] input = {5, 2, 8, 1, 9};
        int[] expected = {1, 2, 5, 8, 9};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
        // Verify original array is not modified
        assertArrayEquals(new int[]{5, 2, 8, 1, 9}, input);
    }

    // Test case for an array that is already sorted
    @Test
    void testOrderAscending_AlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, input);
    }

    // Test case for a reverse sorted array
    @Test
    void testOrderAscending_ReverseSortedArray() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, input);
    }

    // Test case for an array with duplicate elements
    @Test
    void testOrderAscending_ArrayWithDuplicates() {
        int[] input = {3, 1, 4, 1, 5, 9, 2, 6, 5};
        int[] expected = {1, 1, 2, 3, 4, 5, 5, 6, 9};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
        assertArrayEquals(new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5}, input);
    }

    // Test case for an empty array
    @Test
    void testOrderAscending_EmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
        assertArrayEquals(new int[]{}, input);
    }

    // Test case for a null array
    @Test
    void testOrderAscending_NullArray() {
        assertNull(Utility.Order_Ascending(null));
    }

    // Test case for an array with a single element
    @Test
    void testOrderAscending_SingleElementArray() {
        int[] input = {42};
        int[] expected = {42};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
        assertArrayEquals(new int[]{42}, input);
    }

    // Test case for array with negative numbers
    @Test
    void testOrderAscending_NegativeNumbers() {
        int[] input = {-5, 0, -2, 3, -1};
        int[] expected = {-5, -2, -1, 0, 3};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
        assertArrayEquals(new int[]{-5, 0, -2, 3, -1}, input);
    }
}