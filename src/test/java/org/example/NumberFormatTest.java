package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberFormatTest {

    // --- Tests for factorial method ---

    // Test case for factorial of zero
    @Test
    void testFactorialZero() {
        assertEquals(1, NumberFormat.factorial(0));
    }

    // Test case for factorial of one
    @Test
    void testFactorialOne() {
        assertEquals(1, NumberFormat.factorial(1));
    }

    // Test case for a small positive integer
    @Test
    void testFactorialSmallPositive() {
        assertEquals(120, NumberFormat.factorial(5));
    }

    // Test case for a larger positive integer
    @Test
    void testFactorialLargerPositive() {
        assertEquals(3628800, NumberFormat.factorial(10));
    }

    // Test case for the largest factorial that fits in a long
    // Note: 20! is 2,432,902,008,176,640,000 which fits in long. 21! overflows.
    @Test
    void testFactorialMaxLongValue() {
        assertEquals(2432902008176640000L, NumberFormat.factorial(20));
    }

    // Test case for negative input, expecting IllegalArgumentException
    @Test
    void testFactorialNegativeInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NumberFormat.factorial(-1));
        assertThrows(IllegalArgumentException.class, () -> NumberFormat.factorial(-5));
    }

    // --- Tests for gcd method ---

    // Test case for positive numbers
    @Test
    void testGcdPositiveNumbers() {
        assertEquals(6, NumberFormat.gcd(12, 18));
        assertEquals(1, NumberFormat.gcd(17, 5)); // Coprime
        assertEquals(12, NumberFormat.gcd(48, 180));
    }

    // Test case for one of the numbers being zero
    @Test
    void testGcdWithZero() {
        assertEquals(5, NumberFormat.gcd(5, 0));
        assertEquals(5, NumberFormat.gcd(0, 5));
    }

    // Test case for both numbers being zero
    @Test
    void testGcdBothZero() {
        assertEquals(0, NumberFormat.gcd(0, 0));
    }

    // Test case for negative numbers
    @Test
    void testGcdNegativeNumbers() {
        assertEquals(6, NumberFormat.gcd(-12, 18));
        assertEquals(6, NumberFormat.gcd(12, -18));
        assertEquals(6, NumberFormat.gcd(-12, -18));
    }

    // Test case for identical numbers
    @Test
    void testGcdIdenticalNumbers() {
        assertEquals(7, NumberFormat.gcd(7, 7));
    }

    // Test case for large numbers that are multiples of each other
    @Test
    void testGcdLargeMultiples() {
        assertEquals(100, NumberFormat.gcd(100, 1000));
    }

    // Test case for one and MAX_VALUE
    @Test
    void testGcdOneAndMax() {
        assertEquals(1, NumberFormat.gcd(Integer.MAX_VALUE, 1));
    }

    // --- Tests for lcm method ---

    // Test case for positive numbers
    @Test
    void testLcmPositiveNumbers() {
        assertEquals(12, NumberFormat.lcm(4, 6));
        assertEquals(35, NumberFormat.lcm(7, 5)); // Coprime
        assertEquals(36, NumberFormat.lcm(12, 18));
    }

    // Test case for one or both numbers being zero
    @Test
    void testLcmWithZero() {
        assertEquals(0, NumberFormat.lcm(0, 5));
        assertEquals(0, NumberFormat.lcm(5, 0));
        assertEquals(0, NumberFormat.lcm(0, 0));
    }

    // Test case for negative numbers (should return positive LCM)
    @Test
    void testLcmNegativeNumbers() {
        assertEquals(12, NumberFormat.lcm(-4, 6));
        assertEquals(12, NumberFormat.lcm(4, -6));
        assertEquals(12, NumberFormat.lcm(-4, -6));
    }

    // Test case for identical numbers
    @Test
    void testLcmIdenticalNumbers() {
        assertEquals(7, NumberFormat.lcm(7, 7));
    }

    // Test case for one and MAX_VALUE
    @Test
    void testLcmOneAndMax() {
        assertEquals(Integer.MAX_VALUE, NumberFormat.lcm(Integer.MAX_VALUE, 1));
    }

    // Test case for large numbers that result in a large LCM (within long limits)
    @Test
    void testLcmLargeNumbers() {
        assertEquals(4200000000L, NumberFormat.lcm(60000, 70000));
    }

    // --- Tests for isPrime method ---

    // Test case for numbers less than or equal to 1
    @Test
    void testIsPrimeEdgeCasesBelowTwo() {
        assertFalse(NumberFormat.isPrime(0));
        assertFalse(NumberFormat.isPrime(1));
        assertFalse(NumberFormat.isPrime(-5));
    }

    // Test case for small prime numbers
    @Test
    void testIsPrimeSmallPrimes() {
        assertTrue(NumberFormat.isPrime(2));
        assertTrue(NumberFormat.isPrime(3));
        assertTrue(NumberFormat.isPrime(5));
        assertTrue(NumberFormat.isPrime(7));
    }

    // Test case for small composite numbers
    @Test
    void testIsPrimeSmallComposites() {
        assertFalse(NumberFormat.isPrime(4));
        assertFalse(NumberFormat.isPrime(6));
        assertFalse(NumberFormat.isPrime(9));
        assertFalse(NumberFormat.isPrime(10));
    }

    // Test case for larger prime numbers
    @Test
    void testIsPrimeLargerPrimes() {
        assertTrue(NumberFormat.isPrime(13));
        assertTrue(NumberFormat.isPrime(17));
        assertTrue(NumberFormat.isPrime(101));
        assertTrue(NumberFormat.isPrime(199));
    }

    // Test case for larger composite numbers
    @Test
    void testIsPrimeLargerComposites() {
        assertFalse(NumberFormat.isPrime(121)); // 11*11
        assertFalse(NumberFormat.isPrime(100));
        assertFalse(NumberFormat.isPrime(200));
    }

    // Test case for a known large prime number
    @Test
    void testIsPrimeLargeKnownPrime() {
        assertTrue(NumberFormat.isPrime(15485863)); // A prime number
    }

    // Test case for a large composite number
    @Test
    void testIsPrimeLargeComposite() {
        assertFalse(NumberFormat.isPrime(15485864)); // Even number
        assertFalse(NumberFormat.isPrime(15485865)); // Divisible by 5
    }

    // --- Tests for binomial method ---

    // Test case for C(n, 0) which should always be 1
    @Test
    void testBinomialKIsZero() {
        assertEquals(1, NumberFormat.binomial(0, 0));
        assertEquals(1, NumberFormat.binomial(5, 0));
        assertEquals(1, NumberFormat.binomial(100, 0));
    }

    // Test case for C(n, n) which should always be 1
    @Test
    void testBinomialKIsN() {
        assertEquals(1, NumberFormat.binomial(0, 0)); // Also C(0,0)
        assertEquals(1, NumberFormat.binomial(5, 5));
        assertEquals(1, NumberFormat.binomial(100, 100));
    }

    // Test case for C(n, 1) which should always be n
    @Test
    void testBinomialKIsOne() {
        assertEquals(5, NumberFormat.binomial(5, 1));
        assertEquals(100, NumberFormat.binomial(100, 1));
    }

    // Test case for C(n, k) where k is between 0 and n
    @Test
    void testBinomialGeneralCases() {
        assertEquals(10, NumberFormat.binomial(5, 2));
        assertEquals(10, NumberFormat.binomial(5, 3)); // Symmetry C(n, k) = C(n, n-k)
        assertEquals(20, NumberFormat.binomial(6, 3));
        assertEquals(252, NumberFormat.binomial(10, 5));
    }

    // Test case for k < 0, should return 0
    @Test
    void testBinomialKLessThanZero() {
        assertEquals(0, NumberFormat.binomial(5, -1));
        assertEquals(0, NumberFormat.binomial(10, -5));
    }

    // Test case for k > n, should return 0
    @Test
    void testBinomialKGreaterThanN() {
        assertEquals(0, NumberFormat.binomial(5, 6));
        assertEquals(0, NumberFormat.binomial(10, 11));
    }

    // Test case for binomial coefficients resulting in large numbers
    @Test
    void testBinomialLargeNumbers() {
        // C(20, 10) = 184756
        assertEquals(184756, NumberFormat.binomial(20, 10));
        // C(30, 15) = 155,117,520
        assertEquals(155117520L, NumberFormat.binomial(30, 15));
        // C(60, 30) = 1,182,645,815,648,614,400 (fits in long)
        assertEquals(1182645815648614400L, NumberFormat.binomial(60, 30));
    }
}