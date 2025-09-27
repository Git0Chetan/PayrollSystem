package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtlityTest {

    // Test case for factorial of 0
    @Test
    void factorial_ShouldReturnOneForZero() {
        assertEquals(1L, Utlity.factorial(0));
    }

    // Test case for factorial of 1
    @Test
    void factorial_ShouldReturnOneForOne() {
        assertEquals(1L, Utlity.factorial(1));
    }

    // Test case for factorial of a positive integer
    @Test
    void factorial_ShouldReturnCorrectValueForPositiveNumber() {
        assertEquals(120L, Utlity.factorial(5));
        assertEquals(362880L, Utlity.factorial(9));
    }

    // Test case for factorial of a negative integer
    @Test
    void factorial_ShouldThrowExceptionForNegativeNumber() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Utlity.factorial(-1);
        });
        assertEquals("n must be non-negative", thrown.getMessage());
    }

    // Test case for factorial of a larger number within long range
    @Test
    void factorial_ShouldReturnCorrectValueForLargerNumber() {
        assertEquals(2432902008176640000L, Utlity.factorial(20)); // 20!
    }

    // Test case for isPrime with numbers less than or equal to 1
    @Test
    void isPrime_ShouldReturnFalseForNumbersLessThanOrEqualToOne() {
        assertFalse(Utlity.isPrime(0));
        assertFalse(Utlity.isPrime(1));
        assertFalse(Utlity.isPrime(-5));
    }

    // Test case for isPrime with 2 and 3
    @Test
    void isPrime_ShouldReturnTrueForTwoAndThree() {
        assertTrue(Utlity.isPrime(2));
        assertTrue(Utlity.isPrime(3));
    }

    // Test case for isPrime with even numbers greater than 2
    @Test
    void isPrime_ShouldReturnFalseForEvenNumbersGreaterThanTwo() {
        assertFalse(Utlity.isPrime(4));
        assertFalse(Utlity.isPrime(10));
        assertFalse(Utlity.isPrime(100));
    }

    // Test case for isPrime with multiples of 3 greater than 3
    @Test
    void isPrime_ShouldReturnFalseForMultiplesOfThreeGreaterThanThree() {
        assertFalse(Utlity.isPrime(9));
        assertFalse(Utlity.isPrime(15));
    }

    // Test case for isPrime with known prime numbers
    @Test
    void isPrime_ShouldReturnTrueForPrimeNumbers() {
        assertTrue(Utlity.isPrime(5));
        assertTrue(Utlity.isPrime(7));
        assertTrue(Utlity.isPrime(13));
        assertTrue(Utlity.isPrime(101));
        assertTrue(Utlity.isPrime(997));
    }

    // Test case for isPrime with known composite numbers (not multiples of 2 or 3)
    @Test
    void isPrime_ShouldReturnFalseForCompositeNumbers() {
        assertFalse(Utlity.isPrime(25)); // 5*5
        assertFalse(Utlity.isPrime(49)); // 7*7
        assertFalse(Utlity.isPrime(121)); // 11*11
        assertFalse(Utlity.isPrime(169)); // 13*13
    }

    // Test case for gcd with positive co-prime numbers
    @Test
    void gcd_ShouldReturnOneForCoPrimeNumbers() {
        assertEquals(1, Utlity.gcd(7, 13));
        assertEquals(1, Utlity.gcd(17, 23));
    }

    // Test case for gcd with positive numbers having a common divisor
    @Test
    void gcd_ShouldReturnCorrectValueForPositiveNumbers() {
        assertEquals(5, Utlity.gcd(10, 15));
        assertEquals(12, Utlity.gcd(36, 48));
        assertEquals(7, Utlity.gcd(7, 0)); // gcd(a,0) = |a|
        assertEquals(7, Utlity.gcd(0, 7)); // gcd(0,a) = |a|
        assertEquals(0, Utlity.gcd(0, 0)); // gcd(0,0) = 0
    }

    // Test case for gcd with one number being a multiple of the other
    @Test
    void gcd_ShouldReturnSmallerNumberWhenOneIsMultipleOfTheOther() {
        assertEquals(4, Utlity.gcd(12, 4));
        assertEquals(10, Utlity.gcd(10, 100));
    }

    // Test case for gcd with negative numbers
    @Test
    void gcd_ShouldReturnAbsoluteValueForNegativeNumbers() {
        assertEquals(5, Utlity.gcd(-10, 15));
        assertEquals(5, Utlity.gcd(10, -15));
        assertEquals(5, Utlity.gcd(-10, -15));
        assertEquals(7, Utlity.gcd(-7, 0));
        assertEquals(7, Utlity.gcd(0, -7));
    }

    // Test case for lcm with positive numbers
    @Test
    void lcm_ShouldReturnCorrectValueForPositiveNumbers() {
        assertEquals(12L, Utlity.lcm(4, 6));
        assertEquals(60L, Utlity.lcm(12, 15));
        assertEquals(70L, Utlity.lcm(10, 7)); // co-prime
    }

    // Test case for lcm with one number being a multiple of the other
    @Test
    void lcm_ShouldReturnLargerNumberWhenOneIsMultipleOfTheOther() {
        assertEquals(12L, Utlity.lcm(12, 4));
        assertEquals(100L, Utlity.lcm(10, 100));
    }

    // Test case for lcm involving zero
    @Test
    void lcm_ShouldReturnZeroWhenAnyNumberIsZero() {
        assertEquals(0L, Utlity.lcm(0, 5));
        assertEquals(0L, Utlity.lcm(5, 0));
        assertEquals(0L, Utlity.lcm(0, 0));
    }

    // Test case for lcm with negative numbers
    @Test
    void lcm_ShouldReturnAbsoluteValueForNegativeNumbers() {
        assertEquals(12L, Utlity.lcm(-4, 6));
        assertEquals(12L, Utlity.lcm(4, -6));
        assertEquals(12L, Utlity.lcm(-4, -6));
    }

    // Test case for sum with a null array
    @Test
    void sum_ShouldReturnZeroForNullArray() {
        assertEquals(0, Utlity.sum(null));
    }

    // Test case for sum with an empty array
    @Test
    void sum_ShouldReturnZeroForEmptyArray() {
        assertEquals(0, Utlity.sum(new int[]{}));
    }

    // Test case for sum with an array of positive numbers
    @Test
    void sum_ShouldReturnCorrectSumForPositiveNumbers() {
        assertEquals(15, Utlity.sum(new int[]{1, 2, 3, 4, 5}));
    }

    // Test case for sum with an array of negative numbers
    @Test
    void sum_ShouldReturnCorrectSumForNegativeNumbers() {
        assertEquals(-15, Utlity.sum(new int[]{-1, -2, -3, -4, -5}));
    }

    // Test case for sum with a mixed array of positive and negative numbers
    @Test
    void sum_ShouldReturnCorrectSumForMixedNumbers() {
        assertEquals(0, Utlity.sum(new int[]{-1, 0, 1}));
        assertEquals(5, Utlity.sum(new int[]{10, -5, 2, -7, 5}));
    }

    // Test case for sum with an array containing only zero
    @Test
    void sum_ShouldReturnZeroForArrayOfZeros() {
        assertEquals(0, Utlity.sum(new int[]{0, 0, 0}));
    }

    // Test case for sum with a single element array
    @Test
    void sum_ShouldReturnSingleElementForSingleElementArray() {
        assertEquals(42, Utlity.sum(new int[]{42}));
        assertEquals(-10, Utlity.sum(new int[]{-10}));
    }

    // Test case for sum with large numbers (potential for int overflow if not careful)
    @Test
    void sum_ShouldHandleLargeNumbersWithinIntLimits() {
        assertEquals(Integer.MAX_VALUE, Utlity.sum(new int[]{Integer.MAX_VALUE}));
        assertEquals(Integer.MIN_VALUE, Utlity.sum(new int[]{Integer.MIN_VALUE}));
        // Test a sum that does not overflow int, but individual numbers might be large
        assertEquals(1000000000 + 500000000, Utlity.sum(new int[]{1000000000, 500000000}));
    }
}