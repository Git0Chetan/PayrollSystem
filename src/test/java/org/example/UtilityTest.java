package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations; // Included as per instructions, even if no mocks are actually used.

import java.util.Arrays;

public class UtilityTest {

    // The Utility class contains only static methods and no external dependencies,
    // so no actual mocking is required. However, the @BeforeEach setup
    // is included to adhere to the prompt's instruction regarding Mockito.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- Tests for layer method ---

    // Test case for two positive numbers.
    @Test
    @DisplayName("layer: Should return correct sum for two positive integers")
    void testLayer_PositiveNumbers() {
        assertEquals(5, Utility.layer(2, 3));
        assertEquals(100, Utility.layer(50, 50));
    }

    // Test case for two negative numbers.
    @Test
    @DisplayName("layer: Should return correct sum for two negative integers")
    void testLayer_NegativeNumbers() {
        assertEquals(-5, Utility.layer(-2, -3));
        assertEquals(-100, Utility.layer(-50, -50));
    }

    // Test case for a positive and a negative number.
    @Test
    @DisplayName("layer: Should return correct sum for mixed positive and negative integers")
    void testLayer_MixedNumbers() {
        assertEquals(1, Utility.layer(3, -2));
        assertEquals(-1, Utility.layer(-3, 2));
        assertEquals(0, Utility.layer(5, -5));
    }

    // Test case involving zero.
    @Test
    @DisplayName("layer: Should handle zero correctly in addition")
    void testLayer_WithZero() {
        assertEquals(5, Utility.layer(5, 0));
        assertEquals(-5, Utility.layer(-5, 0));
        assertEquals(0, Utility.layer(0, 0));
    }

    // --- Tests for joinAndUpper method ---

    // Test case for two non-null, non-empty strings.
    @Test
    @DisplayName("joinAndUpper: Should concatenate and uppercase two non-null strings")
    void testJoinAndUpper_NonNullStrings() {
        assertEquals("HELLOWORLD", Utility.joinAndUpper("hello", "world"));
        assertEquals("JAVATEST", Utility.joinAndUpper("Java", "Test"));
    }

    // Test case for the first string being null.
    @Test
    @DisplayName("joinAndUpper: Should treat first null string as empty")
    void testJoinAndUpper_FirstStringNull() {
        assertEquals("WORLD", Utility.joinAndUpper(null, "world"));
        assertEquals("", Utility.joinAndUpper(null, null));
    }

    // Test case for the second string being null.
    @Test
    @DisplayName("joinAndUpper: Should treat second null string as empty")
    void testJoinAndUpper_SecondStringNull() {
        assertEquals("HELLO", Utility.joinAndUpper("hello", null));
        assertEquals("", Utility.joinAndUpper(null, null));
    }

    // Test case for both strings being null.
    @Test
    @DisplayName("joinAndUpper: Should return empty string when both inputs are null")
    void testJoinAndUpper_BothStringsNull() {
        assertEquals("", Utility.joinAndUpper(null, null));
    }

    // Test case for empty strings.
    @Test
    @DisplayName("joinAndUpper: Should handle empty strings correctly")
    void testJoinAndUpper_EmptyStrings() {
        assertEquals("HELLO", Utility.joinAndUpper("hello", ""));
        assertEquals("WORLD", Utility.joinAndUpper("", "world"));
        assertEquals("", Utility.joinAndUpper("", ""));
    }

    // Test case for strings with mixed case input.
    @Test
    @DisplayName("joinAndUpper: Should convert mixed case input to uppercase")
    void testJoinAndUpper_MixedCaseInput() {
        assertEquals("HELLOJAVA", Utility.joinAndUpper("hElLo", "jAvA"));
        assertEquals("UPPERCASE", Utility.joinAndUpper("uPpEr", "cAsE"));
    }

    // --- Tests for reverse method ---

    // Test case for a non-null, non-empty string.
    @Test
    @DisplayName("reverse: Should reverse a valid string")
    void testReverse_ValidString() {
        assertEquals("olleh", Utility.reverse("hello"));
        assertEquals("54321", Utility.reverse("12345"));
        assertEquals("!DLROW", Utility.reverse("WORLD!"));
        assertEquals("a", Utility.reverse("a"));
    }

    // Test case for an empty string.
    @Test
    @DisplayName("reverse: Should return an empty string for an empty input")
    void testReverse_EmptyString() {
        assertEquals("", Utility.reverse(""));
    }

    // Test case for a null string input.
    @Test
    @DisplayName("reverse: Should return null for a null input")
    void testReverse_NullString() {
        assertNull(Utility.reverse(null));
    }

    // Test case for a palindrome string.
    @Test
    @DisplayName("reverse: Should return the same string for a palindrome")
    void testReverse_PalindromeString() {
        assertEquals("madam", Utility.reverse("madam"));
        assertEquals("aba", Utility.reverse("aba"));
    }

    // Test case for a string with special characters and numbers.
    @Test
    @DisplayName("reverse: Should correctly reverse strings with special characters and numbers")
    void testReverse_SpecialCharactersAndNumbers() {
        assertEquals("!@#$54321", Utility.reverse("12345$#@!"));
    }

    // --- Tests for isValuable method (primality check) ---

    // Test case for numbers less than or equal to 1.
    @Test
    @DisplayName("isValuable: Should return false for numbers less than or equal to 1")
    void testIsValuable_NumbersLessThanOrEqualToOne() {
        assertFalse(Utility.isValuable(0));
        assertFalse(Utility.isValuable(1));
        assertFalse(Utility.isValuable(-5));
        assertFalse(Utility.isValuable(Integer.MIN_VALUE));
    }

    // Test case for small prime numbers (2, 3).
    @Test
    @DisplayName("isValuable: Should return true for 2 and 3")
    void testIsValuable_SmallPrimes() {
        assertTrue(Utility.isValuable(2));
        assertTrue(Utility.isValuable(3));
    }

    // Test case for small composite numbers.
    @Test
    @DisplayName("isValuable: Should return false for small composite numbers")
    void testIsValuable_SmallComposites() {
        assertFalse(Utility.isValuable(4)); // 2*2
        assertFalse(Utility.isValuable(6)); // 2*3
        assertFalse(Utility.isValuable(9)); // 3*3
    }

    // Test case for known prime numbers.
    @Test
    @DisplayName("isValuable: Should return true for known prime numbers")
    void testIsValuable_KnownPrimes() {
        assertTrue(Utility.isValuable(5));
        assertTrue(Utility.isValuable(7));
        assertTrue(Utility.isValuable(11));
        assertTrue(Utility.isValuable(13));
        assertTrue(Utility.isValuable(17));
        assertTrue(Utility.isValuable(19));
        assertTrue(Utility.isValuable(23));
        assertTrue(Utility.isValuable(97)); // A larger prime
    }

    // Test case for known composite numbers.
    @Test
    @DisplayName("isValuable: Should return false for known composite numbers")
    void testIsValuable_KnownComposites() {
        assertFalse(Utility.isValuable(10)); // 2*5
        assertFalse(Utility.isValuable(15)); // 3*5
        assertFalse(Utility.isValuable(25)); // 5*5
        assertFalse(Utility.isValuable(99)); // 9*11
    }

    // Test case for a larger prime number.
    @Test
    @DisplayName("isValuable: Should correctly identify a larger prime number")
    void testIsValuable_LargePrime() {
        assertTrue(Utility.isValuable(101));
        assertTrue(Utility.isValuable(881)); // Another large prime
    }

    // Test case for a larger composite number.
    @Test
    @DisplayName("isValuable: Should correctly identify a larger composite number")
    void testIsValuable_LargeComposite() {
        assertFalse(Utility.isValuable(121)); // 11*11
        assertFalse(Utility.isValuable(143)); // 11*13
    }


    // --- Tests for gcd method ---

    // Test case for two positive numbers.
    @Test
    @DisplayName("gcd: Should return correct GCD for two positive integers")
    void testGcd_PositiveNumbers() {
        assertEquals(6, Utility.gcd(48, 18)); // GCD(48, 18) = 6
        assertEquals(1, Utility.gcd(7, 5));   // Co-prime
        assertEquals(12, Utility.gcd(12, 60)); // One is multiple of other
        assertEquals(1, Utility.gcd(1, 1));
        assertEquals(1, Utility.gcd(1, 100));
    }

    // Test case for one number being zero.
    @Test
    @DisplayName("gcd: Should return the absolute value of the other number when one input is zero")
    void testGcd_OneZero() {
        assertEquals(5, Utility.gcd(0, 5));
        assertEquals(5, Utility.gcd(5, 0));
        assertEquals(5, Utility.gcd(0, -5));
        assertEquals(5, Utility.gcd(-5, 0));
    }

    // Test case for both numbers being zero.
    @Test
    @DisplayName("gcd: Should return 0 when both inputs are zero (per implementation convention)")
    void testGcd_BothZeros() {
        assertEquals(0, Utility.gcd(0, 0)); // Conventionally, GCD(0,0) is 0 or undefined. This implementation returns 0.
    }

    // Test case for negative numbers.
    @Test
    @DisplayName("gcd: Should handle negative numbers by taking their absolute value")
    void testGcd_NegativeNumbers() {
        assertEquals(6, Utility.gcd(-48, 18));
        assertEquals(6, Utility.gcd(48, -18));
        assertEquals(6, Utility.gcd(-48, -18));
        assertEquals(1, Utility.gcd(-7, -5));
    }

    // Test case for co-prime numbers.
    @Test
    @DisplayName("gcd: Should return 1 for co-prime numbers")
    void testGcd_CoPrime() {
        assertEquals(1, Utility.gcd(17, 13));
        assertEquals(1, Utility.gcd(2, 3));
        assertEquals(1, Utility.gcd(8, 15));
    }

    // Test case for identical numbers.
    @Test
    @DisplayName("gcd: Should return the number itself if inputs are identical")
    void testGcd_IdenticalNumbers() {
        assertEquals(7, Utility.gcd(7, 7));
        assertEquals(100, Utility.gcd(100, 100));
        assertEquals(7, Utility.gcd(-7, -7));
    }

    // --- Tests for Order_Ascending method ---

    // Test case for a non-null, unsorted array.
    @Test
    @DisplayName("Order_Ascending: Should sort an unsorted array in ascending order")
    void testOrderAscending_UnsortedArray() {
        int[] input = {5, 2, 8, 1, 9};
        int[] expected = {1, 2, 5, 8, 9};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an empty array.
    @Test
    @DisplayName("Order_Ascending: Should return an empty array for an empty input")
    void testOrderAscending_EmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for a null array input.
    @Test
    @DisplayName("Order_Ascending: Should return null for a null input")
    void testOrderAscending_NullArray() {
        assertNull(Utility.Order_Ascending(null));
    }

    // Test case for an already sorted array.
    @Test
    @DisplayName("Order_Ascending: Should return a sorted array for an already sorted input")
    void testOrderAscending_AlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an array with duplicate numbers.
    @Test
    @DisplayName("Order_Ascending: Should handle duplicate numbers correctly")
    void testOrderAscending_DuplicateNumbers() {
        int[] input = {5, 2, 8, 1, 5, 2};
        int[] expected = {1, 2, 2, 5, 5, 8};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an array with negative numbers.
    @Test
    @DisplayName("Order_Ascending: Should sort arrays with negative numbers and zero")
    void testOrderAscending_NegativeNumbers() {
        int[] input = {-5, 2, -8, 1, 0};
        int[] expected = {-8, -5, 0, 1, 2};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an array with a single element.
    @Test
    @DisplayName("Order_Ascending: Should return the same array for a single-element input")
    void testOrderAscending_SingleElementArray() {
        int[] input = {42};
        int[] expected = {42};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case to ensure the original array is not modified.
    @Test
    @DisplayName("Order_Ascending: Should not modify the original input array")
    void testOrderAscending_OriginalArrayNotModified() {
        int[] original = {5, 2, 8, 1, 9};
        int[] inputCopy = Arrays.copyOf(original, original.length); // Create a copy to compare against

        Utility.Order_Ascending(original); // Call the method

        // Assert that the original array remains unchanged
        assertArrayEquals(inputCopy, original, "The original array should not have been modified.");
    }
}