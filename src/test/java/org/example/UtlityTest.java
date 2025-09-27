package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtlityTest {

    // Test case for factorial of zero
    @Test
    void factorial_ShouldReturnOneForZero() {
        assertEquals(1L, Utlity.factorial(0));
    }

    // Test case for factorial of one
    @Test
    void factorial_ShouldReturnOneForOne() {
        assertEquals(1L, Utlity.factorial(1));
    }

    // Test case for factorial of a positive number
    @Test
    void factorial_ShouldReturnCorrectResultForPositiveNumber() {
        assertEquals(120L, Utlity.factorial(5));
        assertEquals(720L, Utlity.factorial(6));
    }

    // Test case for factorial of a larger number within long limits
    @Test
    void factorial_ShouldReturnCorrectResultForLargerNumber() {
        assertEquals(2432902008176640000L, Utlity.factorial(20));
    }

    // Test case for factorial of a negative number
    @Test
    void factorial_ShouldThrowExceptionForNegativeNumber() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Utlity.factorial(-1);
        });
        assertEquals("n must be non-negative", thrown.getMessage());
    }

    // Test case for a known prime number (2)
    @Test
    void isPrime_ShouldReturnTrueForTwo() {
        assertTrue(Utlity.isPrime(2));
    }

    // Test case for a known prime number (3)
    @Test
    void isPrime_ShouldReturnTrueForThree() {
        assertTrue(Utlity.isPrime(3));
    }

    // Test case for a known prime number (large)
    @Test
    void isPrime_ShouldReturnTrueForPrimeNumber() {
        assertTrue(Utlity.isPrime(17));
        assertTrue(Utlity.isPrime(29));
        assertTrue(Utlity.isPrime(97));
    }

    // Test case for zero
    @Test
    void isPrime_ShouldReturnFalseForZero() {
        assertFalse(Utlity.isPrime(0));
    }

    // Test case for one
    @Test
    void isPrime_ShouldReturnFalseForOne() {
        assertFalse(Utlity.isPrime(1));
    }

    // Test case for negative numbers
    @Test
    void isPrime_ShouldReturnFalseForNegativeNumber() {
        assertFalse(Utlity.isPrime(-5));
        assertFalse(Utlity.isPrime(-1));
    }

    // Test case for an even composite number (not 2)
    @Test
    void isPrime_ShouldReturnFalseForEvenCompositeNumber() {
        assertFalse(Utlity.isPrime(4));
        assertFalse(Utlity.isPrime(10));
    }

    // Test case for an odd composite number (multiple of 3, not 3)
    @Test
    void isPrime_ShouldReturnFalseForOddCompositeNumberMultipleOfThree() {
        assertFalse(Utlity.isPrime(9));
        assertFalse(Utlity.isPrime(15));
    }

    // Test case for an odd composite number (other factors)
    @Test
    void isPrime_ShouldReturnFalseForOtherCompositeNumber() {
        assertFalse(Utlity.isPrime(25));
        assertFalse(Utlity.isPrime(33));
        assertFalse(Utlity.isPrime(49));
    }

    // Test case for GCD of two positive numbers
    @Test
    void gcd_ShouldReturnCorrectResultForPositiveNumbers() {
        assertEquals(5, Utlity.gcd(10, 15));
        assertEquals(6, Utlity.gcd(18, 24));
    }

    // Test case for GCD where one number is a multiple of the other
    @Test
    void gcd_ShouldReturnTheSmallerNumberIfOneIsMultipleOfOther() {
        assertEquals(6, Utlity.gcd(6, 12));
        assertEquals(7, Utlity.gcd(49, 7));
    }

    // Test case for GCD with relatively prime numbers
    @Test
    void gcd_ShouldReturnOneForRelativelyPrimeNumbers() {
        assertEquals(1, Utlity.gcd(7, 11));
        assertEquals(1, Utlity.gcd(17, 23));
    }

    // Test case for GCD involving zero
    @Test
    void gcd_ShouldReturnOtherNumberWhenOneIsZero() {
        assertEquals(5, Utlity.gcd(0, 5));
        assertEquals(5, Utlity.gcd(5, 0));
    }

    // Test case for GCD of zero and zero
    @Test
    void gcd_ShouldReturnZeroForZeroAndZero() {
        assertEquals(0, Utlity.gcd(0, 0));
    }

    // Test case for GCD with negative numbers
    @Test
    void gcd_ShouldHandleNegativeNumbersCorrectly() {
        assertEquals(5, Utlity.gcd(-10, 15));
        assertEquals(5, Utlity.gcd(10, -15));
        assertEquals(5, Utlity.gcd(-10, -15));
        assertEquals(6, Utlity.gcd(-18, -24));
    }

    // Test case for LCM of two positive numbers
    @Test
    void lcm_ShouldReturnCorrectResultForPositiveNumbers() {
        assertEquals(30L, Utlity.lcm(10, 15));
        assertEquals(72L, Utlity.lcm(8, 9));
    }

    // Test case for LCM where one number is a multiple of the other
    @Test
    void lcm_ShouldReturnTheLargerNumberIfOneIsMultipleOfOther() {
        assertEquals(12L, Utlity.lcm(6, 12));
        assertEquals(49L, Utlity.lcm(7, 49));
    }

    // Test case for LCM with relatively prime numbers
    @Test
    void lcm_ShouldReturnProductForRelativelyPrimeNumbers() {
        assertEquals(77L, Utlity.lcm(7, 11));
        assertEquals(391L, Utlity.lcm(17, 23));
    }

    // Test case for LCM involving zero
    @Test
    void lcm_ShouldReturnZeroWhenOneNumberIsZero() {
        assertEquals(0L, Utlity.lcm(0, 5));
        assertEquals(0L, Utlity.lcm(5, 0));
    }

    // Test case for LCM of zero and zero
    @Test
    void lcm_ShouldReturnZeroForZeroAndZero() {
        assertEquals(0L, Utlity.lcm(0, 0));
    }

    // Test case for LCM with negative numbers
    @Test
    void lcm_ShouldHandleNegativeNumbersCorrectlyReturningPositiveLcm() {
        assertEquals(30L, Utlity.lcm(-10, 15));
        assertEquals(30L, Utlity.lcm(10, -15));
        assertEquals(30L, Utlity.lcm(-10, -15));
    }

    // Test case for LCM resulting in a large value fitting in long
    @Test
    void lcm_ShouldHandleLargeLcmValues() {
        assertEquals(144900L, Utlity.lcm(2100, 1035)); // Example with larger numbers
    }

    // Test case for sum of an array with positive numbers
    @Test
    void sum_ShouldReturnCorrectSumForPositiveNumbers() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(15, Utlity.sum(arr));
    }

    // Test case for sum of an array with negative numbers
    @Test
    void sum_ShouldReturnCorrectSumForNegativeNumbers() {
        int[] arr = {-1, -2, -3, -4, -5};
        assertEquals(-15, Utlity.sum(arr));
    }

    // Test case for sum of an array with mixed positive and negative numbers
    @Test
    void sum_ShouldReturnCorrectSumForMixedNumbers() {
        int[] arr = {-1, 2, -3, 4, -5};
        assertEquals(-3, Utlity.sum(arr));
    }

    // Test case for sum of an empty array
    @Test
    void sum_ShouldReturnZeroForEmptyArray() {
        int[] arr = {};
        assertEquals(0, Utlity.sum(arr));
    }

    // Test case for sum of a null array
    @Test
    void sum_ShouldReturnZeroForNullArray() {
        assertNull(null); // Just to ensure it doesn't fail, though the method returns 0 for null.
        assertEquals(0, Utlity.sum(null));
    }

    // Test case for sum of an array with a single element
    @Test
    void sum_ShouldReturnTheElementForSingleElementArray() {
        int[] arr = {42};
        assertEquals(42, Utlity.sum(arr));
    }

    // Test case for sum resulting in zero
    @Test
    void sum_ShouldReturnZeroWhenNumbersCancelOut() {
        int[] arr = {-5, 5};
        assertEquals(0, Utlity.sum(arr));
        int[] arr2 = {1, -1, 2, -2};
        assertEquals(0, Utlity.sum(arr2));
    }
}