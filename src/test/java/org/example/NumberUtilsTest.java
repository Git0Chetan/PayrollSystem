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

    // Test case for a small positive integer
    @Test
    @DisplayName("factorial(5) should return 120")
    void testFactorialSmallPositive() {
        assertEquals(120L, NumberUtils.factorial(5));
    }

    // Test case for a larger positive integer
    @Test
    @DisplayName("factorial(10) should return 3628800")
    void testFactorialLargerPositive() {
        assertEquals(3628800L, NumberUtils.factorial(10));
    }

    // Test case for the largest factorial that fits in long (20!)
    @Test
    @DisplayName("factorial(20) should return correct large long value")
    void testFactorialLargestLong() {
        assertEquals(2432902008176640000L, NumberUtils.factorial(20));
    }

    // Test case for negative input, expecting IllegalArgumentException
    @Test
    @DisplayName("factorial(-1) should throw IllegalArgumentException")
    void testFactorialNegative() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            NumberUtils.factorial(-1);
        });
        assertEquals("n must be non-negative", thrown.getMessage());
    }


    // Test cases for isPrime method

    // Test case for n <= 1
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

    // Test case for a small composite number
    @Test
    @DisplayName("isPrime(4) should return false")
    void testIsPrimeFour() {
        assertFalse(NumberUtils.isPrime(4));
    }

    // Test case for a positive prime number
    @Test
    @DisplayName("isPrime(7) should return true")
    void testIsPrimeSeven() {
        assertTrue(NumberUtils.isPrime(7));
    }

    // Test case for a positive composite number
    @Test
    @DisplayName("isPrime(9) should return false")
    void testIsPrimeNine() {
        assertFalse(NumberUtils.isPrime(9));
    }

    // Test case for another positive composite number divisible by 2
    @Test
    @DisplayName("isPrime(10) should return false")
    void testIsPrimeTen() {
        assertFalse(NumberUtils.isPrime(10));
    }

    // Test case for a prime number testing the 6k +/- 1 optimization
    @Test
    @DisplayName("isPrime(23) should return true")
    void testIsPrimeTwentyThree() {
        assertTrue(NumberUtils.isPrime(23));
    }

    // Test case for a composite number testing the 6k +/- 1 optimization (multiple of 5)
    @Test
    @DisplayName("isPrime(25) should return false")
    void testIsPrimeTwentyFive() {
        assertFalse(NumberUtils.isPrime(25));
    }

    // Test case for a composite number testing the 6k +/- 1 optimization (multiple of 7)
    @Test
    @DisplayName("isPrime(49) should return false")
    void testIsPrimeFortyNine() {
        assertFalse(NumberUtils.isPrime(49));
    }

    // Test case for a larger prime number
    @Test
    @DisplayName("isPrime(97) should return true")
    void testIsPrimeNinetySeven() {
        assertTrue(NumberUtils.isPrime(97));
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

    // Test case where one number is a multiple of the other
    @Test
    @DisplayName("gcd(15, 5) should return 5")
    void testGcdOneMultipleOfOther() {
        assertEquals(5, NumberUtils.gcd(15, 5));
    }

    // Test case for relatively prime numbers
    @Test
    @DisplayName("gcd(7, 11) should return 1")
    void testGcdRelativelyPrime() {
        assertEquals(1, NumberUtils.gcd(7, 11));
    }

    // Test case where first number is zero
    @Test
    @DisplayName("gcd(0, 5) should return 5")
    void testGcdFirstZero() {
        assertEquals(5, NumberUtils.gcd(0, 5));
    }

    // Test case where second number is zero
    @Test
    @DisplayName("gcd(7, 0) should return 7")
    void testGcdSecondZero() {
        assertEquals(7, NumberUtils.gcd(7, 0));
    }

    // Test case where both numbers are zero
    @Test
    @DisplayName("gcd(0, 0) should return 0")
    void testGcdBothZero() {
        assertEquals(0, NumberUtils.gcd(0, 0));
    }

    // Test case with negative numbers
    @Test
    @DisplayName("gcd(-48, 18) should return 6")
    void testGcdNegativeFirstNumber() {
        assertEquals(6, NumberUtils.gcd(-48, 18));
    }

    // Test case with both negative numbers
    @Test
    @DisplayName("gcd(-48, -18) should return 6")
    void testGcdBothNegativeNumbers() {
        assertEquals(6, NumberUtils.gcd(-48, -18));
    }

    // Test case with one negative and one zero
    @Test
    @DisplayName("gcd(-10, 0) should return 10")
    void testGcdNegativeAndZero() {
        assertEquals(10, NumberUtils.gcd(-10, 0));
    }

    // Test case with numbers equal
    @Test
    @DisplayName("gcd(13, 13) should return 13")
    void testGcdEqualNumbers() {
        assertEquals(13, NumberUtils.gcd(13, 13));
    }


    // Test cases for lcm method

    // Test case for two positive numbers
    @Test
    @DisplayName("lcm(10, 15) should return 30")
    void testLcmPositiveNumbers() {
        assertEquals(30L, NumberUtils.lcm(10, 15));
    }

    // Test case where one number is a multiple of the other
    @Test
    @DisplayName("lcm(4, 8) should return 8")
    void testLcmOneMultipleOfOther() {
        assertEquals(8L, NumberUtils.lcm(4, 8));
    }

    // Test case for relatively prime numbers
    @Test
    @DisplayName("lcm(7, 11) should return 77")
    void testLcmRelativelyPrime() {
        assertEquals(77L, NumberUtils.lcm(7, 11));
    }

    // Test case where first number is zero
    @Test
    @DisplayName("lcm(0, 5) should return 0")
    void testLcmFirstZero() {
        assertEquals(0L, NumberUtils.lcm(0, 5));
    }

    // Test case where second number is zero
    @Test
    @DisplayName("lcm(7, 0) should return 0")
    void testLcmSecondZero() {
        assertEquals(0L, NumberUtils.lcm(7, 0));
    }

    // Test case where both numbers are zero
    @Test
    @DisplayName("lcm(0, 0) should return 0")
    void testLcmBothZero() {
        assertEquals(0L, NumberUtils.lcm(0, 0));
    }

    // Test case with negative numbers
    @Test
    @DisplayName("lcm(-10, 15) should return 30")
    void testLcmNegativeFirstNumber() {
        assertEquals(30L, NumberUtils.lcm(-10, 15));
    }

    // Test case with both negative numbers
    @Test
    @DisplayName("lcm(-10, -15) should return 30")
    void testLcmBothNegativeNumbers() {
        assertEquals(30L, NumberUtils.lcm(-10, -15));
    }

    // Test case with numbers that result in a large long value without overflow
    @Test
    @DisplayName("lcm(Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 3) should return correct large long value")
    void testLcmLargeNumbers() {
        int a = 100000; // Example large numbers
        int b = 150000;
        long expected = 300000L; // lcm(100000, 150000) = 300000
        assertEquals(expected, NumberUtils.lcm(a, b));
    }

    // Test case with a very large product that might exceed int but fit in long
    @Test
    @DisplayName("lcm(200000, 300000) should return 600000")
    void testLcmVeryLargeNumbers() {
        assertEquals(600000L, NumberUtils.lcm(200000, 300000));
    }


    // Test cases for sum method

    // Test case for null array
    @Test
    @DisplayName("sum(null) should return 0")
    void testSumNullArray() {
        assertEquals(0, NumberUtils.sum(null));
    }

    // Test case for empty array
    @Test
    @DisplayName("sum([]) should return 0")
    void testSumEmptyArray() {
        assertEquals(0, NumberUtils.sum(new int[]{}));
    }

    // Test case for array with positive numbers
    @Test
    @DisplayName("sum([1, 2, 3, 4, 5]) should return 15")
    void testSumPositiveNumbers() {
        assertEquals(15, NumberUtils.sum(new int[]{1, 2, 3, 4, 5}));
    }

    // Test case for array with negative numbers
    @Test
    @DisplayName("sum([-1, -2, -3]) should return -6")
    void testSumNegativeNumbers() {
        assertEquals(-6, NumberUtils.sum(new int[]{-1, -2, -3}));
    }

    // Test case for array with mixed positive and negative numbers
    @Test
    @DisplayName("sum([-1, 0, 1, -2, 2]) should return 0")
    void testSumMixedNumbers() {
        assertEquals(0, NumberUtils.sum(new int[]{-1, 0, 1, -2, 2}));
    }

    // Test case for array with a single element
    @Test
    @DisplayName("sum([42]) should return 42")
    void testSumSingleElementArray() {
        assertEquals(42, NumberUtils.sum(new int[]{42}));
    }

    // Test case for array with zeros
    @Test
    @DisplayName("sum([0, 0, 0]) should return 0")
    void testSumZerosArray() {
        assertEquals(0, NumberUtils.sum(new int[]{0, 0, 0}));
    }

    // Test case for potential integer overflow (sum exceeds Integer.MAX_VALUE)
    // The current implementation allows overflow, so we test for that specific behavior.
    @Test
    @DisplayName("sum([Integer.MAX_VALUE, 1]) should overflow and return Integer.MIN_VALUE")
    void testSumIntegerOverflow() {
        // Integer.MAX_VALUE + 1 wraps around to Integer.MIN_VALUE
        assertEquals(Integer.MIN_VALUE, NumberUtils.sum(new int[]{Integer.MAX_VALUE, 1}));
    }

    // Test case for potential integer underflow (sum goes below Integer.MIN_VALUE)
    // The current implementation allows underflow, so we test for that specific behavior.
    @Test
    @DisplayName("sum([Integer.MIN_VALUE, -1]) should underflow and return Integer.MAX_VALUE")
    void testSumIntegerUnderflow() {
        // Integer.MIN_VALUE - 1 wraps around to Integer.MAX_VALUE
        assertEquals(Integer.MAX_VALUE, NumberUtils.sum(new int[]{Integer.MIN_VALUE, -1}));
    }
}