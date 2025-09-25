package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberFormatTest {

    // Test cases for factorial method

    // Test case for factorial of 0
    @Test
    @DisplayName("factorial(0) should return 1")
    void testFactorialZero() {
        assertEquals(1, NumberFormat.factorial(0));
    }

    // Test case for factorial of 1
    @Test
    @DisplayName("factorial(1) should return 1")
    void testFactorialOne() {
        assertEquals(1, NumberFormat.factorial(1));
    }

    // Test case for factorial of a positive number
    @Test
    @DisplayName("factorial(5) should return 120")
    void testFactorialPositive() {
        assertEquals(120, NumberFormat.factorial(5));
    }

    // Test case for factorial of a larger positive number
    @Test
    @DisplayName("factorial(10) should return 3628800")
    void testFactorialLargerPositive() {
        assertEquals(3628800L, NumberFormat.factorial(10));
    }

    // Test case for factorial of the largest number before overflow for long
    @Test
    @DisplayName("factorial(20) should return correct large value")
    void testFactorialMaxLongValue() {
        assertEquals(2432902008176640000L, NumberFormat.factorial(20));
    }

    // Test case for factorial with negative input
    @Test
    @DisplayName("factorial(-1) should throw IllegalArgumentException")
    void testFactorialNegativeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NumberFormat.factorial(-1));
    }

    // Test cases for gcd method

    // Test case for GCD of two positive numbers
    @Test
    @DisplayName("gcd(10, 15) should return 5")
    void testGcdPositiveNumbers() {
        assertEquals(5, NumberFormat.gcd(10, 15));
    }

    // Test case for GCD of prime numbers
    @Test
    @DisplayName("gcd(7, 5) should return 1")
    void testGcdPrimeNumbers() {
        assertEquals(1, NumberFormat.gcd(7, 5));
    }

    // Test case for GCD where one number is zero
    @Test
    @DisplayName("gcd(0, 5) should return 5")
    void testGcdOneNumberZero() {
        assertEquals(5, NumberFormat.gcd(0, 5));
        assertEquals(5, NumberFormat.gcd(5, 0));
    }

    // Test case for GCD where both numbers are zero
    @Test
    @DisplayName("gcd(0, 0) should return 0")
    void testGcdBothNumbersZero() {
        assertEquals(0, NumberFormat.gcd(0, 0));
    }

    // Test case for GCD with negative numbers
    @Test
    @DisplayName("gcd(-10, 15) should return 5")
    void testGcdNegativeNumbers() {
        assertEquals(5, NumberFormat.gcd(-10, 15));
        assertEquals(5, NumberFormat.gcd(10, -15));
        assertEquals(5, NumberFormat.gcd(-10, -15));
    }

    // Test case for GCD of same numbers
    @Test
    @DisplayName("gcd(7, 7) should return 7")
    void testGcdSameNumbers() {
        assertEquals(7, NumberFormat.gcd(7, 7));
    }

    // Test cases for lcm method

    // Test case for LCM of two positive numbers
    @Test
    @DisplayName("lcm(4, 6) should return 12")
    void testLcmPositiveNumbers() {
        assertEquals(12, NumberFormat.lcm(4, 6));
    }

    // Test case for LCM of prime numbers
    @Test
    @DisplayName("lcm(7, 5) should return 35")
    void testLcmPrimeNumbers() {
        assertEquals(35, NumberFormat.lcm(7, 5));
    }

    // Test case for LCM where one number is zero
    @Test
    @DisplayName("lcm(0, 5) should return 0")
    void testLcmOneNumberZero() {
        assertEquals(0, NumberFormat.lcm(0, 5));
        assertEquals(0, NumberFormat.lcm(5, 0));
    }

    // Test case for LCM where both numbers are zero
    @Test
    @DisplayName("lcm(0, 0) should return 0")
    void testLcmBothNumbersZero() {
        assertEquals(0, NumberFormat.lcm(0, 0));
    }

    // Test case for LCM with negative numbers
    @Test
    @DisplayName("lcm(-4, 6) should return 12 (positive)")
    void testLcmNegativeNumbers() {
        assertEquals(12, NumberFormat.lcm(-4, 6));
        assertEquals(12, NumberFormat.lcm(4, -6));
        assertEquals(12, NumberFormat.lcm(-4, -6));
    }

    // Test case for LCM where one number is a multiple of the other
    @Test
    @DisplayName("lcm(3, 9) should return 9")
    void testLcmOneMultipleOfOther() {
        assertEquals(9, NumberFormat.lcm(3, 9));
        assertEquals(9, NumberFormat.lcm(9, 3));
    }

    // Test cases for isPrime method

    // Test case for n <= 1 (non-prime)
    @Test
    @DisplayName("isPrime(0) should return false")
    void testIsPrimeZero() {
        assertFalse(NumberFormat.isPrime(0));
    }

    // Test case for n <= 1 (non-prime)
    @Test
    @DisplayName("isPrime(1) should return false")
    void testIsPrimeOne() {
        assertFalse(NumberFormat.isPrime(1));
    }

    // Test case for n=2 (prime)
    @Test
    @DisplayName("isPrime(2) should return true")
    void testIsPrimeTwo() {
        assertTrue(NumberFormat.isPrime(2));
    }

    // Test case for n=3 (prime)
    @Test
    @DisplayName("isPrime(3) should return true")
    void testIsPrimeThree() {
        assertTrue(NumberFormat.isPrime(3));
    }

    // Test case for n=4 (non-prime)
    @Test
    @DisplayName("isPrime(4) should return false")
    void testIsPrimeFour() {
        assertFalse(NumberFormat.isPrime(4));
    }

    // Test case for a small prime number
    @Test
    @DisplayName("isPrime(5) should return true")
    void testIsPrimeSmallPrime() {
        assertTrue(NumberFormat.isPrime(5));
    }

    // Test case for a small non-prime number
    @Test
    @DisplayName("isPrime(6) should return false")
    void testIsPrimeSmallNonPrime() {
        assertFalse(NumberFormat.isPrime(6));
    }

    // Test case for a larger prime number
    @Test
    @DisplayName("isPrime(17) should return true")
    void testIsPrimeLargerPrime() {
        assertTrue(NumberFormat.isPrime(17));
    }

    // Test case for a larger non-prime number (odd multiple of 3)
    @Test
    @DisplayName("isPrime(21) should return false")
    void testIsPrimeLargerNonPrimeOddMultipleOfThree() {
        assertFalse(NumberFormat.isPrime(21));
    }

    // Test case for a larger non-prime number (odd multiple of 5)
    @Test
    @DisplayName("isPrime(25) should return false")
    void testIsPrimeLargerNonPrimeOddMultipleOfFive() {
        assertFalse(NumberFormat.isPrime(25));
    }

    // Test case for a large prime number
    @Test
    @DisplayName("isPrime(97) should return true")
    void testIsPrimeLargePrime() {
        assertTrue(NumberFormat.isPrime(97));
    }

    // Test case for a large non-prime number
    @Test
    @DisplayName("isPrime(91) should return false (7*13)")
    void testIsPrimeLargeNonPrime() {
        assertFalse(NumberFormat.isPrime(91));
    }

    // Test case for negative number
    @Test
    @DisplayName("isPrime(-5) should return false")
    void testIsPrimeNegativeNumber() {
        assertFalse(NumberFormat.isPrime(-5));
    }

    // Test cases for binomial method

    // Test case for C(n, k) with normal positive values
    @Test
    @DisplayName("binomial(5, 2) should return 10")
    void testBinomialNormalValues() {
        assertEquals(10, NumberFormat.binomial(5, 2));
    }

    // Test case for C(n, 0)
    @Test
    @DisplayName("binomial(5, 0) should return 1")
    void testBinomialKIsZero() {
        assertEquals(1, NumberFormat.binomial(5, 0));
    }

    // Test case for C(n, n)
    @Test
    @DisplayName("binomial(5, 5) should return 1")
    void testBinomialKIsN() {
        assertEquals(1, NumberFormat.binomial(5, 5));
    }

    // Test case for C(n, 1)
    @Test
    @DisplayName("binomial(5, 1) should return 5")
    void testBinomialKIsOne() {
        assertEquals(5, NumberFormat.binomial(5, 1));
    }

    // Test case for C(n, k) where k > n
    @Test
    @DisplayName("binomial(5, 7) should return 0")
    void testBinomialKGreaterThanN() {
        assertEquals(0, NumberFormat.binomial(5, 7));
    }

    // Test case for C(n, k) where k < 0
    @Test
    @DisplayName("binomial(5, -1) should return 0")
    void testBinomialKLessThanZero() {
        assertEquals(0, NumberFormat.binomial(5, -1));
    }

    // Test case for C(0, 0)
    @Test
    @DisplayName("binomial(0, 0) should return 1")
    void testBinomialZeroZero() {
        assertEquals(1, NumberFormat.binomial(0, 0));
    }

    // Test case for larger binomial coefficient (max value that fits long approx C(60, 30))
    @Test
    @DisplayName("binomial(20, 10) should return 184756")
    void testBinomialLargerValue() {
        assertEquals(184756L, NumberFormat.binomial(20, 10));
    }

    // Test case for symmetry property C(n, k) = C(n, n-k)
    @Test
    @DisplayName("binomial(10, 3) should equal binomial(10, 7)")
    void testBinomialSymmetry() {
        assertEquals(NumberFormat.binomial(10, 3), NumberFormat.binomial(10, 7));
    }

    // Test case for a value where n-k is smaller than k, ensuring optimization path
    @Test
    @DisplayName("binomial(10, 8) should be correctly calculated via optimization")
    void testBinomialOptimizationPath() {
        assertEquals(45, NumberFormat.binomial(10, 8)); // C(10, 8) = C(10, 2) = (10*9)/2 = 45
    }
}