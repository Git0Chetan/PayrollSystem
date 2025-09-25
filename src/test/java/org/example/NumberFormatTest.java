package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberFormatTest {

    // factorial tests

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

    // Test case for a positive integer
    @Test
    @DisplayName("factorial(5) should return 120")
    void testFactorialPositive() {
        assertEquals(120, NumberFormat.factorial(5));
    }

    // Test case for a larger positive integer
    @Test
    @DisplayName("factorial(10) should return 3628800")
    void testFactorialLargerPositive() {
        assertEquals(3628800, NumberFormat.factorial(10));
    }

    // Test case for max value that fits in long
    @Test
    @DisplayName("factorial(20) should return 2432902008176640000L")
    void testFactorialTwenty() {
        assertEquals(2432902008176640000L, NumberFormat.factorial(20));
    }

    // Test case for negative input, expecting IllegalArgumentException
    @Test
    @DisplayName("factorial(-1) should throw IllegalArgumentException")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> NumberFormat.factorial(-1));
    }

    // gcd tests

    // Test case for two positive numbers with common divisor
    @Test
    @DisplayName("gcd(48, 18) should return 6")
    void testGcdPositiveNumbers() {
        assertEquals(6, NumberFormat.gcd(48, 18));
    }

    // Test case for coprime numbers
    @Test
    @DisplayName("gcd(17, 13) should return 1 for coprime numbers")
    void testGcdCoprimeNumbers() {
        assertEquals(1, NumberFormat.gcd(17, 13));
    }

    // Test case with one number as zero
    @Test
    @DisplayName("gcd(0, 5) should return 5")
    void testGcdOneZero() {
        assertEquals(5, NumberFormat.gcd(0, 5));
        assertEquals(5, NumberFormat.gcd(5, 0));
    }

    // Test case with both numbers as zero
    @Test
    @DisplayName("gcd(0, 0) should return 0")
    void testGcdBothZero() {
        assertEquals(0, NumberFormat.gcd(0, 0));
    }

    // Test case with negative numbers
    @Test
    @DisplayName("gcd(-48, 18) should return 6 with negative input")
    void testGcdNegativeNumbers() {
        assertEquals(6, NumberFormat.gcd(-48, 18));
        assertEquals(6, NumberFormat.gcd(48, -18));
        assertEquals(6, NumberFormat.gcd(-48, -18));
    }

    // Test case with numbers that are multiples
    @Test
    @DisplayName("gcd(10, 5) should return 5 when one is a multiple of the other")
    void testGcdMultiples() {
        assertEquals(5, NumberFormat.gcd(10, 5));
    }

    // Test case with same numbers
    @Test
    @DisplayName("gcd(7, 7) should return 7")
    void testGcdSameNumbers() {
        assertEquals(7, NumberFormat.gcd(7, 7));
    }

    // lcm tests

    // Test case for two positive numbers
    @Test
    @DisplayName("lcm(4, 6) should return 12")
    void testLcmPositiveNumbers() {
        assertEquals(12, NumberFormat.lcm(4, 6));
    }

    // Test case for coprime numbers
    @Test
    @DisplayName("lcm(7, 5) should return 35 for coprime numbers")
    void testLcmCoprimeNumbers() {
        assertEquals(35, NumberFormat.lcm(7, 5));
    }

    // Test case with one number as zero
    @Test
    @DisplayName("lcm(0, 5) should return 0")
    void testLcmOneZero() {
        assertEquals(0, NumberFormat.lcm(0, 5));
        assertEquals(0, NumberFormat.lcm(5, 0));
    }

    // Test case with both numbers as zero
    @Test
    @DisplayName("lcm(0, 0) should return 0")
    void testLcmBothZero() {
        assertEquals(0, NumberFormat.lcm(0, 0));
    }

    // Test case with negative numbers
    @Test
    @DisplayName("lcm(-4, 6) should return 12 with negative input")
    void testLcmNegativeNumbers() {
        assertEquals(12, NumberFormat.lcm(-4, 6));
        assertEquals(12, NumberFormat.lcm(4, -6));
        assertEquals(12, NumberFormat.lcm(-4, -6));
    }

    // Test case with numbers that are multiples
    @Test
    @DisplayName("lcm(2, 4) should return 4 when one is a multiple of the other")
    void testLcmMultiples() {
        assertEquals(4, NumberFormat.lcm(2, 4));
    }

    // Test case with same numbers
    @Test
    @DisplayName("lcm(7, 7) should return 7")
    void testLcmSameNumbers() {
        assertEquals(7, NumberFormat.lcm(7, 7));
    }

    // isPrime tests

    // Test case for numbers less than or equal to 1
    @Test
    @DisplayName("isPrime for n <= 1 should return false")
    void testIsPrimeLessThanTwo() {
        assertFalse(NumberFormat.isPrime(0));
        assertFalse(NumberFormat.isPrime(1));
        assertFalse(NumberFormat.isPrime(-5));
    }

    // Test case for 2 (first prime)
    @Test
    @DisplayName("isPrime(2) should return true")
    void testIsPrimeTwo() {
        assertTrue(NumberFormat.isPrime(2));
    }

    // Test case for 3 (second prime)
    @Test
    @DisplayName("isPrime(3) should return true")
    void testIsPrimeThree() {
        assertTrue(NumberFormat.isPrime(3));
    }

    // Test case for an even composite number (4)
    @Test
    @DisplayName("isPrime(4) should return false")
    void testIsPrimeFour() {
        assertFalse(NumberFormat.isPrime(4));
    }

    // Test case for an odd composite number (9)
    @Test
    @DisplayName("isPrime(9) should return false")
    void testIsPrimeNine() {
        assertFalse(NumberFormat.isPrime(9));
    }

    // Test case for a prime number
    @Test
    @DisplayName("isPrime(5) should return true")
    void testIsPrimeFive() {
        assertTrue(NumberFormat.isPrime(5));
    }

    // Test case for a larger prime number
    @Test
    @DisplayName("isPrime(29) should return true")
    void testIsPrimeLargerPrime() {
        assertTrue(NumberFormat.isPrime(29));
    }

    // Test case for a larger composite number (121 = 11*11)
    @Test
    @DisplayName("isPrime(121) should return false")
    void testIsPrimeLargerComposite() {
        assertFalse(NumberFormat.isPrime(121));
    }

    // Test case for another larger prime number (using the 6k +/- 1 optimization)
    @Test
    @DisplayName("isPrime(97) should return true")
    void testIsPrimeNinetySeven() {
        assertTrue(NumberFormat.isPrime(97));
    }

    // binomial tests

    // Test case for k < 0
    @Test
    @DisplayName("binomial(5, -1) should return 0 for k < 0")
    void testBinomialKLessThanZero() {
        assertEquals(0, NumberFormat.binomial(5, -1));
    }

    // Test case for k > n
    @Test
    @DisplayName("binomial(5, 6) should return 0 for k > n")
    void testBinomialKGreaterThanN() {
        assertEquals(0, NumberFormat.binomial(5, 6));
    }

    // Test case for k = 0
    @Test
    @DisplayName("binomial(5, 0) should return 1")
    void testBinomialKEqualsZero() {
        assertEquals(1, NumberFormat.binomial(5, 0));
    }

    // Test case for k = n
    @Test
    @DisplayName("binomial(5, 5) should return 1")
    void testBinomialKEqualsN() {
        assertEquals(1, NumberFormat.binomial(5, 5));
    }

    // Test case for k = 1
    @Test
    @DisplayName("binomial(5, 1) should return 5")
    void testBinomialKEqualsOne() {
        assertEquals(5, NumberFormat.binomial(5, 1));
    }

    // Test case for k = n-1
    @Test
    @DisplayName("binomial(5, 4) should return 5")
    void testBinomialKEqualsNMinusOne() {
        assertEquals(5, NumberFormat.binomial(5, 4));
    }

    // Test case for typical positive n and k
    @Test
    @DisplayName("binomial(5, 2) should return 10")
    void testBinomialTypicalCase() {
        assertEquals(10, NumberFormat.binomial(5, 2));
    }

    // Test case for another typical case
    @Test
    @DisplayName("binomial(6, 3) should return 20")
    void testBinomialAnotherTypicalCase() {
        assertEquals(20, NumberFormat.binomial(6, 3));
    }

    // Test case illustrating symmetry C(n,k) = C(n, n-k)
    @Test
    @DisplayName("binomial(10, 3) should be equal to binomial(10, 7)")
    void testBinomialSymmetry() {
        assertEquals(120, NumberFormat.binomial(10, 3));
        assertEquals(120, NumberFormat.binomial(10, 7));
    }

    // Test case for a larger binomial coefficient
    @Test
    @DisplayName("binomial(10, 5) should return 252")
    void testBinomialLargerCoefficient() {
        assertEquals(252, NumberFormat.binomial(10, 5));
    }

    // Test case for C(0,0)
    @Test
    @DisplayName("binomial(0, 0) should return 1")
    void testBinomialZeroZero() {
        assertEquals(1, NumberFormat.binomial(0, 0));
    }
}