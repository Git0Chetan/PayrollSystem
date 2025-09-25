package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    // Note: The Utility class is entirely static and has no external dependencies
    // or internal state that requires mocking. Therefore, Mockito setup is not
    // strictly necessary here, but included to adhere to the instruction regarding
    // the presence of Mockito imports and setup if dependencies existed.
    @BeforeEach
    void setUp() {
        // If Utility had external dependencies (e.g., a service it interacted with),
        // we would initialize mocks here using MockitoAnnotations.openMocks(this);
        // For this specific class, no mocks are needed.
        // MockitoAnnotations.openMocks(this);
    }

    // Test cases for layer(int a, int b)

    // Test case for two positive integers
    @Test
    @DisplayName("layer: Should return correct sum for two positive integers")
    void layer_PositiveNumbers_ReturnsCorrectSum() {
        assertEquals(5, Utility.layer(2, 3));
    }

    // Test case for one positive and one negative integer
    @Test
    @DisplayName("layer: Should return correct sum for positive and negative integers")
    void layer_PositiveAndNegativeNumbers_ReturnsCorrectSum() {
        assertEquals(-1, Utility.layer(2, -3));
        assertEquals(1, Utility.layer(-2, 3));
    }

    // Test case for two negative integers
    @Test
    @DisplayName("layer: Should return correct sum for two negative integers")
    void layer_NegativeNumbers_ReturnsCorrectSum() {
        assertEquals(-5, Utility.layer(-2, -3));
    }

    // Test case for one zero and one positive integer
    @Test
    @DisplayName("layer: Should return the non-zero number when one input is zero")
    void layer_ZeroAndPositive_ReturnsNonZeroNumber() {
        assertEquals(5, Utility.layer(0, 5));
        assertEquals(5, Utility.layer(5, 0));
    }

    // Test case for two zero integers
    @Test
    @DisplayName("layer: Should return zero when both inputs are zero")
    void layer_TwoZeros_ReturnsZero() {
        assertEquals(0, Utility.layer(0, 0));
    }

    // Test cases for joinAndUpper(String a, String b)

    // Test case for two non-null, non-empty strings
    @Test
    @DisplayName("joinAndUpper: Should concatenate and uppercase two non-null strings")
    void joinAndUpper_TwoNonNullStrings_ConcatenatesAndUppercases() {
        assertEquals("HELLOWORLD", Utility.joinAndUpper("hello", "world"));
        assertEquals("JAVATEST", Utility.joinAndUpper("Java", "test"));
    }

    // Test case for the first string being null
    @Test
    @DisplayName("joinAndUpper: Should treat null first string as empty and uppercase the second")
    void joinAndUpper_FirstStringNull_TreatsAsEmptyAndUppercasesSecond() {
        assertEquals("WORLD", Utility.joinAndUpper(null, "world"));
    }

    // Test case for the second string being null
    @Test
    @DisplayName("joinAndUpper: Should treat null second string as empty and uppercase the first")
    void joinAndUpper_SecondStringNull_TreatsAsEmptyAndUppercasesFirst() {
        assertEquals("HELLO", Utility.joinAndUpper("hello", null));
    }

    // Test case for both strings being null
    @Test
    @DisplayName("joinAndUpper: Should return empty string when both inputs are null")
    void joinAndUpper_BothStringsNull_ReturnsEmptyString() {
        assertEquals("", Utility.joinAndUpper(null, null));
    }

    // Test case for empty strings
    @Test
    @DisplayName("joinAndUpper: Should return empty string for two empty strings")
    void joinAndUpper_EmptyStrings_ReturnsEmptyString() {
        assertEquals("", Utility.joinAndUpper("", ""));
    }

    // Test case for one empty string
    @Test
    @DisplayName("joinAndUpper: Should uppercase and return the non-empty string when one is empty")
    void joinAndUpper_OneEmptyString_ReturnsUppercaseNonEmpty() {
        assertEquals("HELLO", Utility.joinAndUpper("hello", ""));
        assertEquals("WORLD", Utility.joinAndUpper("", "world"));
    }

    // Test case with mixed case input
    @Test
    @DisplayName("joinAndUpper: Should correctly uppercase mixed case inputs")
    void joinAndUpper_MixedCaseInputs_ReturnsAllUppercase() {
        assertEquals("JUNIT5TEST", Utility.joinAndUpper("jUnit5", "tEsT"));
    }

    // Test cases for reverse(String s)

    // Test case for a non-null, non-empty string
    @Test
    @DisplayName("reverse: Should correctly reverse a non-empty string")
    void reverse_NonNullString_ReturnsReversedString() {
        assertEquals("olleh", Utility.reverse("hello"));
        assertEquals("dcba", Utility.reverse("abcd"));
        assertEquals("54321", Utility.reverse("12345"));
    }

    // Test case for a null string input
    @Test
    @DisplayName("reverse: Should return null when input string is null")
    void reverse_NullString_ReturnsNull() {
        assertNull(Utility.reverse(null));
    }

    // Test case for an empty string input
    @Test
    @DisplayName("reverse: Should return an empty string when input string is empty")
    void reverse_EmptyString_ReturnsEmptyString() {
        assertEquals("", Utility.reverse(""));
    }

    // Test case for a string with special characters and spaces
    @Test
    @DisplayName("reverse: Should correctly reverse strings with spaces and special characters")
    void reverse_StringWithSpacesAndSpecialChars_ReturnsReversed() {
        assertEquals("!dlrow olleh", Utility.reverse("hello world!"));
        assertEquals("3#@1", Utility.reverse("1@#3"));
    }

    // Test case for a single character string
    @Test
    @DisplayName("reverse: Should return the same string for a single character input")
    void reverse_SingleCharString_ReturnsSameString() {
        assertEquals("a", Utility.reverse("a"));
        assertEquals("Z", Utility.reverse("Z"));
    }

    // Test cases for isValuable(int n) (Primality check)

    // Test case for numbers less than or equal to 1
    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, -5, Integer.MIN_VALUE})
    @DisplayName("isValuable: Should return false for numbers <= 1")
    void isValuable_NumbersLessThanOrEqualToOne_ReturnsFalse(int n) {
        assertFalse(Utility.isValuable(n));
    }

    // Test case for known prime numbers
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 97, 101})
    @DisplayName("isValuable: Should return true for known prime numbers")
    void isValuable_KnownPrimes_ReturnsTrue(int n) {
        assertTrue(Utility.isValuable(n));
    }

    // Test case for known composite numbers
    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 15, 21, 25, 49, 100})
    @DisplayName("isValuable: Should return false for known composite numbers")
    void isValuable_KnownComposites_ReturnsFalse(int n) {
        assertFalse(Utility.isValuable(n));
    }

    // Test case for larger prime number
    @Test
    @DisplayName("isValuable: Should return true for a larger prime number")
    void isValuable_LargePrime_ReturnsTrue() {
        assertTrue(Utility.isValuable(2147483647)); // Largest Mersenne prime (2^31-1)
    }

    // Test case for larger composite number
    @Test
    @DisplayName("isValuable: Should return false for a larger composite number")
    void isValuable_LargeComposite_ReturnsFalse() {
        assertFalse(Utility.isValuable(2147483646)); // Just a large even number
        assertFalse(Utility.isValuable(999999937 * 2)); // Product of two primes
    }


    // Test cases for gcd(int a, int b)

    // Test case for two positive integers
    @ParameterizedTest
    @CsvSource({
            "12, 18, 6",
            "10, 5, 5",
            "7, 11, 1",
            "48, 18, 6",
            "100, 75, 25",
            "1, 100, 1"
    })
    @DisplayName("gcd: Should return correct GCD for two positive integers")
    void gcd_PositiveNumbers_ReturnsCorrectGCD(int a, int b, int expectedGcd) {
        assertEquals(expectedGcd, Utility.gcd(a, b));
    }

    // Test case for one number being zero
    @ParameterizedTest
    @CsvSource({
            "5, 0, 5",
            "0, 10, 10",
            "0, 0, 0"
    })
    @DisplayName("gcd: Should handle zero input correctly")
    void gcd_OneOrBothZeros_ReturnsCorrectGCD(int a, int b, int expectedGcd) {
        assertEquals(expectedGcd, Utility.gcd(a, b));
    }

    // Test case for negative numbers
    @ParameterizedTest
    @CsvSource({
            "-12, 18, 6",
            "12, -18, 6",
            "-12, -18, 6",
            "-7, 0, 7",
            "0, -11, 11"
    })
    @DisplayName("gcd: Should return correct GCD when one or both inputs are negative")
    void gcd_NegativeNumbers_ReturnsCorrectGCD(int a, int b, int expectedGcd) {
        assertEquals(expectedGcd, Utility.gcd(a, b));
    }

    // Test case for large numbers where result is 1
    @Test
    @DisplayName("gcd: Should return 1 for coprime large numbers")
    void gcd_LargeCoprimeNumbers_ReturnsOne() {
        assertEquals(1, Utility.gcd(99991, 100003)); // Two large primes
    }

    // Test cases for Order_Ascending(int[] arr)

    // Test case for a null input array
    @Test
    @DisplayName("Order_Ascending: Should return null when input array is null")
    void orderAscending_NullArray_ReturnsNull() {
        assertNull(Utility.Order_Ascending(null));
    }

    // Test case for an empty input array
    @Test
    @DisplayName("Order_Ascending: Should return an empty array when input array is empty")
    void orderAscending_EmptyArray_ReturnsEmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an array with a single element
    @Test
    @DisplayName("Order_Ascending: Should return the same array for a single-element array")
    void orderAscending_SingleElementArray_ReturnsSameArray() {
        int[] input = {5};
        int[] expected = {5};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an unsorted array of positive integers
    @Test
    @DisplayName("Order_Ascending: Should correctly sort an unsorted array of positive integers")
    void orderAscending_UnsortedPositiveArray_ReturnsSortedArray() {
        int[] input = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] expected = {1, 1, 2, 3, 4, 5, 6, 9};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an array with negative numbers and zero
    @Test
    @DisplayName("Order_Ascending: Should correctly sort an array with negative numbers and zero")
    void orderAscending_ArrayWithNegativesAndZero_ReturnsSortedArray() {
        int[] input = {-5, 0, 3, -1, 10};
        int[] expected = {-5, -1, 0, 3, 10};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case for an already sorted array
    @Test
    @DisplayName("Order_Ascending: Should return the same array if it's already sorted")
    void orderAscending_AlreadySortedArray_ReturnsSameArray() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }

    // Test case to ensure the original array is not modified
    @Test
    @DisplayName("Order_Ascending: Should not modify the original input array")
    void orderAscending_OriginalArrayNotModified() {
        int[] original = {5, 1, 4, 2, 8};
        int[] originalCopy = Arrays.copyOf(original, original.length); // Deep copy for comparison

        Utility.Order_Ascending(original); // Call method

        assertArrayEquals(originalCopy, original, "Original array should not be modified");
    }

    // Test case with duplicate elements
    @Test
    @DisplayName("Order_Ascending: Should handle duplicate elements correctly")
    void orderAscending_ArrayWithDuplicates_ReturnsSortedArray() {
        int[] input = {3, 1, 3, 2, 1, 2};
        int[] expected = {1, 1, 2, 2, 3, 3};
        assertArrayEquals(expected, Utility.Order_Ascending(input));
    }
}