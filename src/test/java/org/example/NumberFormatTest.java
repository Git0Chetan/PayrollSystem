package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberFormatTest {

    // --- Tests for factorial(int n) ---

    // Test case for factorial of 0
    @Test
    @DisplayName("factorial(0) should return 1")
    void testFactorial_Zero() {
        assertEquals(1L, NumberFormat.factorial(0));
    }

    // Test case for factorial of 1
    @Test
    @DisplayName("factorial(1) should return 1")
    void testFactorial_One() {
        assertEquals(1L, NumberFormat.factorial(1));
    }

    // Test case for positive factorial values
    @Test
    @DisplayName("factorial of positive numbers")
    void testFactorial_PositiveNumbers() {
        assertEquals(2L, NumberFormat.factorial(2));
        assertEquals(6L, NumberFormat.factorial(3));
        assertEquals(120L, NumberFormat.factorial(5));
        assertEquals(3628800L, NumberFormat.factorial(10));
    }

    // Test case for factorial of a number that results in a large long value
    @Test
    @DisplayName("factorial of large numbers within long range")
    void testFactorial_LargeNumbers() {
        assertEquals(2432902008176640000L, NumberFormat.factorial(20)); // 20!
    }

    // Test case for negative input, expecting IllegalArgumentException
    @Test
    @DisplayName("factorial of negative number should throw IllegalArgumentException")
    void testFactorial_NegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> NumberFormat.factorial(-1),
                "n must be non-negative");
        assertThrows(IllegalArgumentException.class, () -> NumberFormat.factorial(-5),
                "n must be non-negative");
    }

    // --- Tests for gcd(int a, int b) ---

    // Test case for GCD of positive numbers
    @Test
    @DisplayName("gcd of two positive numbers")
    void testGcd_PositiveNumbers() {
        assertEquals(5, NumberFormat.gcd(10, 5));
        assertEquals(6, NumberFormat.gcd(48, 18));
        assertEquals(1, NumberFormat.gcd(7, 13)); // Co-prime
        assertEquals(12, NumberFormat.gcd(60, 36));
    }

    // Test case for GCD when one number is zero
    @Test
    @DisplayName("gcd when one number is zero")
    void testGcd_OneZero() {
        assertEquals(5, NumberFormat.gcd(0, 5));
        assertEquals(7, NumberFormat.gcd(7, 0));
    }

    // Test case for GCD when both numbers are zero
    @Test
    @DisplayName("gcd when both numbers are zero")
    void testGcd_BothZero() {
        assertEquals(0, NumberFormat.gcd(0, 0));
    }

    // Test case for GCD with negative numbers
    @Test
    @DisplayName("gcd with negative numbers")
    void testGcd_NegativeNumbers() {
        assertEquals(5, NumberFormat.gcd(-10, 5));
        assertEquals(5, NumberFormat.gcd(10, -5));
        assertEquals(5, NumberFormat.gcd(-10, -5));
        assertEquals(6, NumberFormat.gcd(-48, 18));
        assertEquals(6, NumberFormat.gcd(48, -18));
        assertEquals(6, NumberFormat.gcd(-48, -18));
    }

    // Test case for GCD with large numbers
    @Test
    @DisplayName("gcd with large numbers")
    void testGcd_LargeNumbers() {
        assertEquals(25, NumberFormat.gcd(125, 25));
        assertEquals(1, NumberFormat.gcd(Integer.MAX_VALUE, Integer.MAX_VALUE - 1));
    }

    // --- Tests for lcm(int a, int b) ---

    // Test case for LCM of positive numbers
    @Test
    @DisplayName("lcm of two positive numbers")
    void testLcm_PositiveNumbers() {
        assertEquals(12L, NumberFormat.lcm(4, 6));
        assertEquals(21L, NumberFormat.lcm(7, 3)); // Co-prime
        assertEquals(10L, NumberFormat.lcm(10, 5));
        assertEquals(180L, NumberFormat.lcm(36, 60));
    }

    // Test case for LCM when one number is zero
    @Test
    @DisplayName("lcm when one number is zero")
    void testLcm_OneZero() {
        assertEquals(0L, NumberFormat.lcm(0, 5));
        assertEquals(0L, NumberFormat.lcm(7, 0));
    }

    // Test case for LCM when both numbers are zero
    @Test
    @DisplayName("lcm when both numbers are zero")
    void testLcm_BothZero() {
        assertEquals(0L, NumberFormat.lcm(0, 0));
    }

    // Test case for LCM with negative numbers
    @Test
    @DisplayName("lcm with negative numbers")
    void testLcm_NegativeNumbers() {
        assertEquals(12L, NumberFormat.lcm(-4, 6));
        assertEquals(12L, NumberFormat.lcm(4, -6));
        assertEquals(12L, NumberFormat.lcm(-4, -6));
        assertEquals(180L, NumberFormat.lcm(-36, 60));
    }

    // Test case for LCM that results in a large long value (potential overflow if not careful)
    @Test
    @DisplayName("lcm of large numbers within long range")
    void testLcm_LargeResult() {
        assertEquals(2147483646L, NumberFormat.lcm(2, Integer.MAX_VALUE -1)); // 2 * (2^31 - 2)
        assertEquals(2147483647L * 2L, NumberFormat.lcm(2147483647, 2)); // MAX_INT * 2 (prime * 2)
        assertEquals(11605791361200L, NumberFormat.lcm(123456, 94012)); // Example of two large numbers
    }

    // --- Tests for isPrime(int n) ---

    // Test cases for numbers less than or equal to 1
    @Test
    @DisplayName("isPrime for numbers <= 1 should be false")
    void testIsPrime_LessThanOrEqualToOne() {
        assertFalse(NumberFormat.isPrime(0));
        assertFalse(NumberFormat.isPrime(1));
        assertFalse(NumberFormat.isPrime(-1));
        assertFalse(NumberFormat.isPrime(-10));
    }

    // Test cases for small prime numbers
    @Test
    @DisplayName("isPrime for small prime numbers")
    void testIsPrime_SmallPrimes() {
        assertTrue(NumberFormat.isPrime(2));
        assertTrue(NumberFormat.isPrime(3));
        assertTrue(NumberFormat.isPrime(5));
        assertTrue(NumberFormat.isPrime(7));
    }

    // Test cases for small non-prime numbers
    @Test
    @DisplayName("isPrime for small non-prime numbers")
    void testIsPrime_SmallNonPrimes() {
        assertFalse(NumberFormat.isPrime(4));
        assertFalse(NumberFormat.isPrime(6));
        assertFalse(NumberFormat.isPrime(8));
        assertFalse(NumberFormat.isPrime(9));
        assertFalse(NumberFormat.isPrime(10));
    }

    // Test cases for larger prime numbers
    @Test
    @DisplayName("isPrime for larger prime numbers")
    void testIsPrime_LargerPrimes() {
        assertTrue(NumberFormat.isPrime(11));
        assertTrue(NumberFormat.isPrime(13));
        assertTrue(NumberFormat.isPrime(17));
        assertTrue(NumberFormat.isPrime(19));
        assertTrue(NumberFormat.isPrime(23));
        assertTrue(NumberFormat.isPrime(29));
        assertTrue(NumberFormat.isPrime(31));
        assertTrue(NumberFormat.isPrime(97));
        assertTrue(NumberFormat.isPrime(101));
        assertTrue(NumberFormat.isPrime(Integer.MAX_VALUE)); // A known large prime
    }

    // Test cases for larger non-prime numbers
    @Test
    @DisplayName("isPrime for larger non-prime numbers")
    void testIsPrime_LargerNonPrimes() {
        assertFalse(NumberFormat.isPrime(25)); // 5*5
        assertFalse(NumberFormat.isPrime(33)); // 3*11
        assertFalse(NumberFormat.isPrime(49)); // 7*7
        assertFalse(NumberFormat.isPrime(100)); // Even
        assertFalse(NumberFormat.isPrime(121)); // 11*11
        assertFalse(NumberFormat.isPrime(169)); // 13*13
    }

    // Test cases for even numbers greater than 2
    @Test
    @DisplayName("isPrime for even numbers greater than 2 should be false")
    void testIsPrime_EvenNumbersGreaterThanTwo() {
        assertFalse(NumberFormat.isPrime(4));
        assertFalse(NumberFormat.isPrime(100));
        assertFalse(NumberFormat.isPrime(2000));
    }

    // Test cases for multiples of 3 greater than 3
    @Test
    @DisplayName("isPrime for multiples of 3 greater than 3 should be false")
    void testIsPrime_MultiplesOfThreeGreaterThanThree() {
        assertFalse(NumberFormat.isPrime(9));
        assertFalse(NumberFormat.isPrime(15));
        assertFalse(NumberFormat.isPrime(33));
    }

    // --- Tests for binomial(int n, int k) ---

    // Test case for C(n, 0)
    @Test
    @DisplayName("binomial(n, 0) should return 1")
    void testBinomial_KIsZero() {
        assertEquals(1L, NumberFormat.binomial(0, 0));
        assertEquals(1L, NumberFormat.binomial(5, 0));
        assertEquals(1L, NumberFormat.binomial(100, 0));
    }

    // Test case for C(n, n)
    @Test
    @DisplayName("binomial(n, n) should return 1")
    void testBinomial_KEqualsN() {
        assertEquals(1L, NumberFormat.binomial(5, 5));
        assertEquals(1L, NumberFormat.binomial(100, 100));
    }

    // Test case for C(n, 1)
    @Test
    @DisplayName("binomial(n, 1) should return n")
    void testBinomial_KIsOne() {
        assertEquals(5L, NumberFormat.binomial(5, 1));
        assertEquals(100L, NumberFormat.binomial(100, 1));
    }

    // Test case for C(n, k) where k < 0 or k > n
    @Test
    @DisplayName("binomial(n, k) where k < 0 or k > n should return 0")
    void testBinomial_InvalidK() {
        assertEquals(0L, NumberFormat.binomial(5, -1));
        assertEquals(0L, NumberFormat.binomial(5, 6));
        assertEquals(0L, NumberFormat.binomial(0, 1));
    }

    // Test cases for standard binomial coefficients
    @Test
    @DisplayName("binomial for standard positive values")
    void testBinomial_StandardValues() {
        assertEquals(10L, NumberFormat.binomial(5, 2)); // 5! / (2! * 3!) = 10
        assertEquals(10L, NumberFormat.binomial(5, 3)); // Symmetry C(n, k) = C(n, n-k)
        assertEquals(20L, NumberFormat.binomial(6, 3));
        assertEquals(35L, NumberFormat.binomial(7, 3));
        assertEquals(792L, NumberFormat.binomial(12, 5));
    }

    // Test cases for larger binomial coefficients (within long range)
    @Test
    @DisplayName("binomial for larger values within long range")
    void testBinomial_LargeValues() {
        assertEquals(1140L, NumberFormat.binomial(20, 3));
        assertEquals(184756L, NumberFormat.binomial(20, 6));
        assertEquals(137947150531289L, NumberFormat.binomial(45, 10));
    }

    // Test case for symmetry C(n, k) = C(n, n-k)
    @Test
    @DisplayName("binomial should respect symmetry C(n, k) = C(n, n-k)")
    void testBinomial_Symmetry() {
        assertEquals(NumberFormat.binomial(10, 3), NumberFormat.binomial(10, 7));
        assertEquals(NumberFormat.binomial(25, 5), NumberFormat.binomial(25, 20));
        assertEquals(NumberFormat.binomial(30, 1), NumberFormat.binomial(30, 29));
    }
}