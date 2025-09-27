package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NumberUtils Test Suite")
class NumberUtilsTest {

    // Test cases for factorial method

    // Test case for n = 0
    @Test
    @DisplayName("factorial(0) should return 1")
    void testFactorialZero() {
        assertEquals(1L, NumberUtils.factorial(0));
    }

    // Test case for n = 1
    @Test
    @DisplayName("factorial(1) should return 1")
    void testFactorialOne() {
        assertEquals(1L, NumberUtils.factorial(1));
    }

    // Test case for a small positive number
    @Test
    @DisplayName("factorial(5) should return 120")
    void testFactorialPositiveSmall() {
        assertEquals(120L, NumberUtils.factorial(5));
    }

    // Test case for a larger positive number
    @Test
    @DisplayName("factorial(10) should return 3628800")
    void testFactorialPositiveLarge() {
        assertEquals(3628800L, NumberUtils.factorial(10));
    }

    // Test case for the largest n whose factorial fits in long
    @Test
    @DisplayName("factorial(20) should return correct value")
    void testFactorialMaxLong() {
        assertEquals(2432902008176640000L, NumberUtils.factorial(20));
    }

    // Test case for negative input, expecting an IllegalArgumentException
    @Test
    @DisplayName("factorial(-1) should throw IllegalArgumentException")
    void testFactorialNegativeThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> NumberUtils.factorial(-1),
                "Expected factorial(-1) to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("n must be non-negative"));
    }

    // Test cases for isPrime method

    // Test case for n = 0
    @Test
    @DisplayName("isPrime(0) should return false")
    void testIsPrimeZero() {
        assertFalse(NumberUtils.isPrime(0));
    }

    // Test case for n = 1
    @Test
    @DisplayName("isPrime(1) should return false")
    void testIsPrimeOne() {
        assertFalse(NumberUtils.isPrime(1));
    }

    // Test case for n = 2 (smallest prime)
    @Test
    @DisplayName("isPrime(2) should return true")
    void testIsPrimeTwo() {
        assertTrue(NumberUtils.isPrime(2));
    }

    // Test case for n = 3 (prime)
    @Test
    @DisplayName("isPrime(3) should return true")
    void testIsPrimeThree() {
        assertTrue(NumberUtils.isPrime(3));
    }

    // Test case for n = 4 (smallest composite)
    @Test
    @DisplayName("isPrime(4) should return false")
    void testIsPrimeFour() {
        assertFalse(NumberUtils.isPrime(4));
    }

    // Test case for a small prime number
    @Test
    @DisplayName("isPrime(5) should return true")
    void testIsPrimeFive() {
        assertTrue(NumberUtils.isPrime(5));
    }

    // Test case for a small composite number
    @Test
    @DisplayName("isPrime(6) should return false")
    void testIsPrimeSix() {
        assertFalse(NumberUtils.isPrime(6));
    }

    // Test case for a larger prime number
    @Test
    @DisplayName("isPrime(97) should return true")
    void testIsPrimeLargePrime() {
        assertTrue(NumberUtils.isPrime(97));
    }

    // Test case for a larger composite number
    @Test
    @DisplayName("isPrime(100) should return false")
    void testIsPrimeLargeComposite() {
        assertFalse(NumberUtils.isPrime(100));
    }

    // Test case for a negative number
    @Test
    @DisplayName("isPrime(-5) should return false")
    void testIsPrimeNegative() {
        assertFalse(NumberUtils.isPrime(-5));
    }

    // Test cases for gcd method

    // Test case for two positive numbers
    @Test
    @DisplayName("gcd(48, 18) should return 6")
    void testGcdPositiveNumbers() {
        assertEquals(6, NumberUtils.gcd(48, 18));
    }

    // Test case for one number being zero
    @Test
    @DisplayName("gcd(0, 5) should return 5")
    void testGcdOneZero() {
        assertEquals(5, NumberUtils.gcd(0, 5));
        assertEquals(5, NumberUtils.gcd(5, 0)); // Symmetric check
    }

    // Test case for both numbers being zero
    @Test
    @DisplayName("gcd(0, 0) should return 0")
    void testGcdBothZero() {
        assertEquals(0, NumberUtils.gcd(0, 0));
    }

    // Test case for one negative number
    @Test
    @DisplayName("gcd(-48, 18) should return 6")
    void testGcdOneNegative() {
        assertEquals(6, NumberUtils.gcd(-48, 18));
        assertEquals(6, NumberUtils.gcd(48, -18)); // Symmetric check
    }

    // Test case for both negative numbers
    @Test
    @DisplayName("gcd(-48, -18) should return 6")
    void testGcdBothNegative() {
        assertEquals(6, NumberUtils.gcd(-48, -18));
    }

    // Test case for relatively prime numbers
    @Test
    @DisplayName("gcd(7, 13) should return 1")
    void testGcdRelativelyPrime() {
        assertEquals(1, NumberUtils.gcd(7, 13));
    }

    // Test case for identical numbers
    @Test
    @DisplayName("gcd(7, 7) should return 7")
    void testGcdIdenticalNumbers() {
        assertEquals(7, NumberUtils.gcd(7, 7));
    }

    // Test case where one number is a multiple of the other
    @Test
    @DisplayName("gcd(10, 5) should return 5")
    void testGcdMultiple() {
        assertEquals(5, NumberUtils.gcd(10, 5));
        assertEquals(5, NumberUtils.gcd(5, 10));
    }

    // Test cases for lcm method

    // Test case for two positive numbers
    @Test
    @DisplayName("lcm(4, 6) should return 12")
    void testLcmPositiveNumbers() {
        assertEquals(12L, NumberUtils.lcm(4, 6));
    }

    // Test case for one number being zero
    @Test
    @DisplayName("lcm(0, 5) should return 0")
    void testLcmOneZero() {
        assertEquals(0L, NumberUtils.lcm(0, 5));
        assertEquals(0L, NumberUtils.lcm(5, 0)); // Symmetric check
    }

    // Test case for both numbers being zero
    @Test
    @DisplayName("lcm(0, 0) should return 0")
    void testLcmBothZero() {
        assertEquals(0L, NumberUtils.lcm(0, 0));
    }

    // Test case for one negative number
    @Test
    @DisplayName("lcm(-4, 6) should return 12")
    void testLcmOneNegative() {
        assertEquals(12L, NumberUtils.lcm(-4, 6));
        assertEquals(12L, NumberUtils.lcm(4, -6)); // Symmetric check
    }

    // Test case for both negative numbers
    @Test
    @DisplayName("lcm(-4, -6) should return 12")
    void testLcmBothNegative() {
        assertEquals(12L, NumberUtils.lcm(-4, -6));
    }

    // Test case for relatively prime numbers
    @Test
    @DisplayName("lcm(7, 13) should return 91")
    void testLcmRelativelyPrime() {
        assertEquals(91L, NumberUtils.lcm(7, 13));
    }

    // Test case for identical numbers
    @Test
    @DisplayName("lcm(7, 7) should return 7")
    void testLcmIdenticalNumbers() {
        assertEquals(7L, NumberUtils.lcm(7, 7));
    }

    // Test case for numbers where one is a multiple of the other
    @Test
    @DisplayName("lcm(10, 5) should return 10")
    void testLcmMultiple() {
        assertEquals(10L, NumberUtils.lcm(10, 5));
        assertEquals(10L, NumberUtils.lcm(5, 10));
    }

    // Test case for large numbers that might overflow int but fit in long
    @Test
    @DisplayName("lcm(Integer.MAX_VALUE - 1, Integer.MAX_VALUE) should return correct large value")
    void testLcmLargeNumbers() {
        // Example: 2 and Integer.MAX_VALUE
        assertEquals((long)2 * Integer.MAX_VALUE, NumberUtils.lcm(2, Integer.MAX_VALUE));
        // Example: a slightly larger pair, e.g., 100, 150 -> 300
        assertEquals(300L, NumberUtils.lcm(100, 150));
    }

    // Test cases for sum method

    // Test case for a null array
    @Test
    @DisplayName("sum(null) should return 0")
    void testSumNullArray() {
        assertEquals(0, NumberUtils.sum(null));
    }

    // Test case for an empty array
    @Test
    @DisplayName("sum([]) should return 0")
    void testSumEmptyArray() {
        assertEquals(0, NumberUtils.sum(new int[]{}));
    }

    // Test case for an array with positive numbers
    @Test
    @DisplayName("sum([1, 2, 3, 4, 5]) should return 15")
    void testSumPositiveNumbers() {
        assertEquals(15, NumberUtils.sum(new int[]{1, 2, 3, 4, 5}));
    }

    // Test case for an array with negative numbers
    @Test
    @DisplayName("sum([-1, -2, -3]) should return -6")
    void testSumNegativeNumbers() {
        assertEquals(-6, NumberUtils.sum(new int[]{-1, -2, -3}));
    }

    // Test case for an array with mixed positive and negative numbers
    @Test
    @DisplayName("sum([-1, 2, -3, 4, -5]) should return -3")
    void testSumMixedNumbers() {
        assertEquals(-3, NumberUtils.sum(new int[]{-1, 2, -3, 4, -5}));
    }

    // Test case for an array with zero
    @Test
    @DisplayName("sum([0, 0, 0]) should return 0")
    void testSumZeroOnly() {
        assertEquals(0, NumberUtils.sum(new int[]{0, 0, 0}));
    }

    // Test case for an array with a single element
    @Test
    @DisplayName("sum([42]) should return 42")
    void testSumSingleElement() {
        assertEquals(42, NumberUtils.sum(new int[]{42}));
    }

    // Test case for array with large numbers potentially leading to overflow (sum itself is int)
    @Test
    @DisplayName("sum([Integer.MAX_VALUE, 1]) should handle overflow (expected to overflow if s was not int)")
    void testSumOverflow() {
        // The sum method returns int, so an overflow is expected if the sum exceeds Integer.MAX_VALUE.
        // This test ensures the method behaves as per standard Java integer addition overflow.
        assertEquals(Integer.MIN_VALUE, NumberUtils.sum(new int[]{Integer.MAX_VALUE, 1}));
        assertEquals(Integer.MAX_VALUE, NumberUtils.sum(new int[]{Integer.MAX_VALUE - 1, 1}));
    }
}