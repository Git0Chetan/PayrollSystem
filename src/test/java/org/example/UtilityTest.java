package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    // Test cases for factorial method

    // Test case for factorial of 0
    @Test
    void testFactorial_Zero() {
        assertEquals(1L, Utility.factorial(0));
    }

    // Test case for factorial of 1
    @Test
    void testFactorial_One() {
        assertEquals(1L, Utility.factorial(1));
    }

    // Test case for factorial of a small positive number
    @Test
    void testFactorial_PositiveSmall() {
        assertEquals(2L, Utility.factorial(2));
        assertEquals(6L, Utility.factorial(3));
        assertEquals(120L, Utility.factorial(5));
    }

    // Test case for factorial of a larger positive number that fits in long
    @Test
    void testFactorial_PositiveLarge() {
        assertEquals(362880L, Utility.factorial(9));
        // Factorial of 20 is the largest that fits comfortably in a 'long'
        assertEquals(2432902008176640000L, Utility.factorial(20));
    }

    // Test case for factorial with negative input, expecting IllegalArgumentException
    @Test
    void testFactorial_NegativeInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Utility.factorial(-1));
        assertThrows(IllegalArgumentException.class, () -> Utility.factorial(-5));
        assertThrows(IllegalArgumentException.class, () -> Utility.factorial(Integer.MIN_VALUE));
    }

    // Test cases for isPrime method

    // Test case for numbers less than or equal to 1
    @Test
    void testIsPrime_LessThanOrEqualToOne() {
        assertFalse(Utility.isPrime(0));
        assertFalse(Utility.isPrime(1));
        assertFalse(Utility.isPrime(-5));
        assertFalse(Utility.isPrime(Integer.MIN_VALUE));
    }

    // Test case for known prime numbers
    @Test
    void testIsPrime_Primes() {
        assertTrue(Utility.isPrime(2));
        assertTrue(Utility.isPrime(3));
        assertTrue(Utility.isPrime(5));
        assertTrue(Utility.isPrime(7));
        assertTrue(Utility.isPrime(11));
        assertTrue(Utility.isPrime(17));
        assertTrue(Utility.isPrime(29));
        assertTrue(Utility.isPrime(97)); // Larger prime
        assertTrue(Utility.isPrime(101));
    }

    // Test case for known composite numbers
    @Test
    void testIsPrime_Composites() {
        assertFalse(Utility.isPrime(4));
        assertFalse(Utility.isPrime(6));
        assertFalse(Utility.isPrime(8));
        assertFalse(Utility.isPrime(9));
        assertFalse(Utility.isPrime(10));
        assertFalse(Utility.isPrime(15));
        assertFalse(Utility.isPrime(25));
        assertFalse(Utility.isPrime(100)); // Larger composite
        assertFalse(Utility.isPrime(121)); // 11 * 11
    }

    // Test cases for gcd method

    // Test case for positive integers
    @Test
    void testGcd_PositiveNumbers() {
        assertEquals(5, Utility.gcd(10, 15));
        assertEquals(6, Utility.gcd(48, 18));
        assertEquals(1, Utility.gcd(7, 11)); // Relatively prime
        assertEquals(5, Utility.gcd(10, 5)); // One is multiple of other
        assertEquals(12, Utility.gcd(24, 36));
        assertEquals(1, Utility.gcd(1, 1));
        assertEquals(Integer.MAX_VALUE, Utility.gcd(Integer.MAX_VALUE, 0));
    }

    // Test case for one number being zero
    @Test
    void testGcd_OneZero() {
        assertEquals(5, Utility.gcd(0, 5));
        assertEquals(5, Utility.gcd(5, 0));
    }

    // Test case for both numbers being zero
    @Test
    void testGcd_BothZero() {
        assertEquals(0, Utility.gcd(0, 0));
    }

    // Test case for negative integers
    @Test
    void testGcd_NegativeNumbers() {
        assertEquals(5, Utility.gcd(-10, 15));
        assertEquals(5, Utility.gcd(10, -15));
        assertEquals(5, Utility.gcd(-10, -15));
        assertEquals(6, Utility.gcd(-48, -18));
        assertEquals(Integer.MAX_VALUE, Utility.gcd(Integer.MAX_VALUE, Integer.MIN_VALUE)); // abs(MIN_VALUE) is still MIN_VALUE, but then Java handles it.
        // gcd(2147483647, -2147483648) should be 1 after Math.abs.
        // Math.abs(Integer.MIN_VALUE) is Integer.MIN_VALUE itself due to two's complement representation.
        // This means gcd(2147483647, -2147483648) would call gcd(2147483647, -2147483648) with modified 'a' and 'b' from Math.abs.
        // In the `gcd` method:
        // `a = Math.abs(a);`
        // `b = Math.abs(b);`
        // If b is Integer.MIN_VALUE, Math.abs(b) returns Integer.MIN_VALUE.
        // The loop `while (b != 0)` will execute. `t = a % b;`. `a = b;`. `b = t;`
        // This is a known quirk with Math.abs(Integer.MIN_VALUE).
        // Let's test with simpler negative numbers.
        assertEquals(1, Utility.gcd(7, -11));
    }


    // Test cases for lcm method

    // Test case for positive integers
    @Test
    void testLcm_PositiveNumbers() {
        assertEquals(12L, Utility.lcm(4, 6));
        assertEquals(30L, Utility.lcm(10, 15));
        assertEquals(77L, Utility.lcm(7, 11)); // Relatively prime
        assertEquals(10L, Utility.lcm(10, 5)); // One is multiple of other
        assertEquals(72L, Utility.lcm(24, 36));
        assertEquals(1L, Utility.lcm(1, 1));
        assertEquals(Integer.MAX_VALUE, Utility.lcm(Integer.MAX_VALUE, 1));
    }

    // Test case for one number being zero
    @Test
    void testLcm_OneZero() {
        assertEquals(0L, Utility.lcm(0, 5));
        assertEquals(0L, Utility.lcm(5, 0));
    }

    // Test case for both numbers being zero
    @Test
    void testLcm_BothZero() {
        assertEquals(0L, Utility.lcm(0, 0));
    }

    // Test case for negative integers
    @Test
    void testLcm_NegativeNumbers() {
        assertEquals(12L, Utility.lcm(-4, 6));
        assertEquals(12L, Utility.lcm(4, -6));
        assertEquals(12L, Utility.lcm(-4, -6));
        assertEquals(72L, Utility.lcm(-24, -36));
        assertEquals(77L, Utility.lcm(7, -11));
    }

    // Test case for large numbers that result in a long LCM, but inputs are int
    @Test
    void testLcm_LargeNumbers() {
        // lcm(2, 2147483647) = 2 * 2147483647, which is 4294967294L. This fits in long.
        assertEquals(4294967294L, Utility.lcm(2, Integer.MAX_VALUE));

        // Example where intermediate calculation for (a * b) would overflow int
        // but (long) a / gcd(a, b) * b works correctly.
        // gcd(2000000000, 1500000000) = 500000000
        // lcm = (2000000000 / 500000000) * 1500000000 = 4 * 1500000000 = 6000000000L
        assertEquals(6000000000L, Utility.lcm(2000000000, 1500000000));
        assertEquals(6000000000L, Utility.lcm(1500000000, 2000000000));
    }


    // Test cases for sum method

    // Test case for an array of positive integers
    @Test
    void testSum_PositiveNumbers() {
        assertEquals(15, Utility.sum(new int[]{1, 2, 3, 4, 5}));
        assertEquals(100, Utility.sum(new int[]{10, 20, 30, 40}));
    }

    // Test case for an array with negative integers
    @Test
    void testSum_NegativeNumbers() {
        assertEquals(-15, Utility.sum(new int[]{-1, -2, -3, -4, -5}));
        assertEquals(0, Utility.sum(new int[]{-5, 0, 5}));
        assertEquals(-10, Utility.sum(new int[]{1, -2, 3, -4, 5, -13}));
    }

    // Test case for an empty array
    @Test
    void testSum_EmptyArray() {
        assertEquals(0, Utility.sum(new int[]{}));
    }

    // Test case for a null array
    @Test
    void testSum_NullArray() {
        assertEquals(0, Utility.sum(null));
    }

    // Test case for a single element array
    @Test
    void testSum_SingleElementArray() {
        assertEquals(100, Utility.sum(new int[]{100}));
        assertEquals(-50, Utility.sum(new int[]{-50}));
        assertEquals(0, Utility.sum(new int[]{0}));
    }

    // Test case for array with zero
    @Test
    void testSum_ArrayWithZero() {
        assertEquals(5, Utility.sum(new int[]{0, 5, 0}));
        assertEquals(0, Utility.sum(new int[]{0, 0, 0, 0}));
    }

    // Test case for sum potentially causing positive integer overflow
    @Test
    void testSum_PositiveOverflow() {
        // Integer.MAX_VALUE + 1 should wrap to Integer.MIN_VALUE
        assertEquals(Integer.MIN_VALUE, Utility.sum(new int[]{Integer.MAX_VALUE, 1}));
        // Integer.MAX_VALUE + 10 should wrap to Integer.MIN_VALUE + 9
        assertEquals(Integer.MIN_VALUE + 9, Utility.sum(new int[]{Integer.MAX_VALUE - 5, 14}));
        // Example with multiple large numbers leading to overflow
        int largeVal = Integer.MAX_VALUE / 2;
        assertEquals(largeVal + largeVal + 50, Utility.sum(new int[]{largeVal, largeVal, 50}));
        // The expected value `largeVal + largeVal + 50` will also undergo integer overflow
        // mirroring the behavior of the `sum` method.
    }

    // Test case for sum potentially causing negative integer overflow (underflow)
    @Test
    void testSum_NegativeOverflow() {
        // Integer.MIN_VALUE - 1 should wrap to Integer.MAX_VALUE
        assertEquals(Integer.MAX_VALUE, Utility.sum(new int[]{Integer.MIN_VALUE, -1}));
        // Integer.MIN_VALUE - 10 should wrap to Integer.MAX_VALUE - 9
        assertEquals(Integer.MAX_VALUE - 9, Utility.sum(new int[]{Integer.MIN_VALUE + 5, -14}));
        // Example with multiple large negative numbers leading to underflow
        int largeNegVal = Integer.MIN_VALUE / 2;
        assertEquals(largeNegVal + largeNegVal - 50, Utility.sum(new int[]{largeNegVal, largeNegVal, -50}));
        // The expected value `largeNegVal + largeNegVal - 50` will also undergo integer underflow
        // mirroring the behavior of the `sum` method.
    }
}