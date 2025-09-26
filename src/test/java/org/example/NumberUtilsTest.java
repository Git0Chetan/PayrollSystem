package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsTest {

    // Test cases for factorial method

    // Test case for factorial of zero
    @Test
    @DisplayName("factorial(0) should return 1")
    void factorial_zero_returnsOne() {
        assertEquals(1, NumberUtils.factorial(0));
    }

    // Test case for factorial of one
    @Test
    @DisplayName("factorial(1) should return 1")
    void factorial_one_returnsOne() {
        assertEquals(1, NumberUtils.factorial(1));
    }

    // Test case for factorial of a positive number
    @Test
    @DisplayName("factorial(5) should return 120")
    void factorial_positiveNumber_returnsCorrectResult() {
        assertEquals(120, NumberUtils.factorial(5));
    }

    // Test case for factorial of a larger positive number
    @Test
    @DisplayName("factorial(10) should return 3628800")
    void factorial_largerPositiveNumber_returnsCorrectResult() {
        assertEquals(3628800L, NumberUtils.factorial(10));
    }

    // Test case for factorial of 20, which is the largest factorial that fits into long
    @Test
    @DisplayName("factorial(20) should return 2432902008176640000")
    void factorial_maxLongValue_returnsCorrectResult() {
        assertEquals(2432902008176640000L, NumberUtils.factorial(20));
    }

    // Test case for factorial of a negative number
    @Test
    @DisplayName("factorial(-1) should throw IllegalArgumentException")
    void factorial_negativeNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.factorial(-1));
    }

    // Test cases for isPrime method

    // Test case for number 1, which is not prime
    @Test
    @DisplayName("isPrime(1) should return false")
    void isPrime_one_returnsFalse() {
        assertFalse(NumberUtils.isPrime(1));
    }

    // Test case for number 2, which is prime
    @Test
    @DisplayName("isPrime(2) should return true")
    void isPrime_two_returnsTrue() {
        assertTrue(NumberUtils.isPrime(2));
    }

    // Test case for number 3, which is prime
    @Test
    @DisplayName("isPrime(3) should return true")
    void isPrime_three_returnsTrue() {
        assertTrue(NumberUtils.isPrime(3));
    }

    // Test case for an even number greater than 2, which is not prime
    @Test
    @DisplayName("isPrime(4) should return false")
    void isPrime_evenNumber_returnsFalse() {
        assertFalse(NumberUtils.isPrime(4));
    }

    // Test case for a prime number
    @Test
    @DisplayName("isPrime(7) should return true")
    void isPrime_primeNumber_returnsTrue() {
        assertTrue(NumberUtils.isPrime(7));
    }

    // Test case for a composite number (multiple of 3)
    @Test
    @DisplayName("isPrime(9) should return false")
    void isPrime_compositeNumberMultipleOfThree_returnsFalse() {
        assertFalse(NumberUtils.isPrime(9));
    }

    // Test case for a composite number using i*i check (e.g., 25)
    @Test
    @DisplayName("isPrime(25) should return false")
    void isPrime_compositeNumberSquare_returnsFalse() {
        assertFalse(NumberUtils.isPrime(25));
    }

    // Test case for a larger prime number
    @Test
    @DisplayName("isPrime(47) should return true")
    void isPrime_largerPrimeNumber_returnsTrue() {
        assertTrue(NumberUtils.isPrime(47));
    }

    // Test case for a larger composite number
    @Test
    @DisplayName("isPrime(49) should return false")
    void isPrime_largerCompositeNumber_returnsFalse() {
        assertFalse(NumberUtils.isPrime(49));
    }

    // Test case for a negative number
    @Test
    @DisplayName("isPrime(-5) should return false")
    void isPrime_negativeNumber_returnsFalse() {
        assertFalse(NumberUtils.isPrime(-5));
    }

    // Test case for 0
    @Test
    @DisplayName("isPrime(0) should return false")
    void isPrime_zero_returnsFalse() {
        assertFalse(NumberUtils.isPrime(0));
    }

    // Test cases for gcd method

    // Test case for positive numbers
    @Test
    @DisplayName("gcd(48, 18) should return 6")
    void gcd_positiveNumbers_returnsCorrectResult() {
        assertEquals(6, NumberUtils.gcd(48, 18));
    }

    // Test case for one number being zero
    @Test
    @DisplayName("gcd(10, 0) should return 10")
    void gcd_oneNumberZero_returnsOtherNumber() {
        assertEquals(10, NumberUtils.gcd(10, 0));
    }

    // Test case for the other number being zero
    @Test
    @DisplayName("gcd(0, 15) should return 15")
    void gcd_otherNumberZero_returnsFirstNumber() {
        assertEquals(15, NumberUtils.gcd(0, 15));
    }

    // Test case for both numbers being zero
    @Test
    @DisplayName("gcd(0, 0) should return 0")
    void gcd_bothNumbersZero_returnsZero() {
        assertEquals(0, NumberUtils.gcd(0, 0));
    }

    // Test case for negative numbers
    @Test
    @DisplayName("gcd(-48, -18) should return 6")
    void gcd_negativeNumbers_returnsPositiveResult() {
        assertEquals(6, NumberUtils.gcd(-48, -18));
    }

    // Test case for mixed positive and negative numbers
    @Test
    @DisplayName("gcd(-48, 18) should return 6")
    void gcd_mixedSignNumbers_returnsPositiveResult() {
        assertEquals(6, NumberUtils.gcd(-48, 18));
    }

    // Test case for coprime numbers
    @Test
    @DisplayName("gcd(7, 11) should return 1")
    void gcd_coprimeNumbers_returnsOne() {
        assertEquals(1, NumberUtils.gcd(7, 11));
    }

    // Test case for one number being a multiple of the other
    @Test
    @DisplayName("gcd(20, 5) should return 5")
    void gcd_oneNumberMultipleOfTheOther_returnsSmallerNumber() {
        assertEquals(5, NumberUtils.gcd(20, 5));
    }

    // Test cases for lcm method

    // Test case for positive numbers
    @Test
    @DisplayName("lcm(4, 6) should return 12")
    void lcm_positiveNumbers_returnsCorrectResult() {
        assertEquals(12L, NumberUtils.lcm(4, 6));
    }

    // Test case for one number being zero
    @Test
    @DisplayName("lcm(10, 0) should return 0")
    void lcm_oneNumberZero_returnsZero() {
        assertEquals(0L, NumberUtils.lcm(10, 0));
    }

    // Test case for both numbers being zero
    @Test
    @DisplayName("lcm(0, 0) should return 0")
    void lcm_bothNumbersZero_returnsZero() {
        assertEquals(0L, NumberUtils.lcm(0, 0));
    }

    // Test case for negative numbers
    @Test
    @DisplayName("lcm(-4, -6) should return 12")
    void lcm_negativeNumbers_returnsPositiveResult() {
        assertEquals(12L, NumberUtils.lcm(-4, -6));
    }

    // Test case for mixed positive and negative numbers
    @Test
    @DisplayName("lcm(-4, 6) should return 12")
    void lcm_mixedSignNumbers_returnsPositiveResult() {
        assertEquals(12L, NumberUtils.lcm(-4, 6));
    }

    // Test case for coprime numbers
    @Test
    @DisplayName("lcm(7, 11) should return 77")
    void lcm_coprimeNumbers_returnsProduct() {
        assertEquals(77L, NumberUtils.lcm(7, 11));
    }

    // Test case for one number being a multiple of the other
    @Test
    @DisplayName("lcm(20, 5) should return 20")
    void lcm_oneNumberMultipleOfTheOther_returnsLargerNumber() {
        assertEquals(20L, NumberUtils.lcm(20, 5));
    }

    // Test case for large numbers that fit in long
    @Test
    @DisplayName("lcm(2000000000, 1500000000) should return 6000000000L")
    void lcm_largeNumbers_returnsCorrectResult() {
        assertEquals(6000000000L, NumberUtils.lcm(2000000000, 1500000000));
    }

    // Test cases for sum method

    // Test case for an array of positive numbers
    @Test
    @DisplayName("sum({1, 2, 3, 4, 5}) should return 15")
    void sum_positiveNumbersArray_returnsCorrectSum() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(15, NumberUtils.sum(arr));
    }

    // Test case for an array of negative numbers
    @Test
    @DisplayName("sum({-1, -2, -3}) should return -6")
    void sum_negativeNumbersArray_returnsCorrectSum() {
        int[] arr = {-1, -2, -3};
        assertEquals(-6, NumberUtils.sum(arr));
    }

    // Test case for an array of mixed positive, negative, and zero numbers
    @Test
    @DisplayName("sum({1, -2, 3, 0, -5}) should return -3")
    void sum_mixedNumbersArray_returnsCorrectSum() {
        int[] arr = {1, -2, 3, 0, -5};
        assertEquals(-3, NumberUtils.sum(arr));
    }

    // Test case for an empty array
    @Test
    @DisplayName("sum({}) should return 0")
    void sum_emptyArray_returnsZero() {
        int[] arr = {};
        assertEquals(0, NumberUtils.sum(arr));
    }

    // Test case for a null array
    @Test
    @DisplayName("sum(null) should return 0")
    void sum_nullArray_returnsZero() {
        int[] arr = null;
        assertEquals(0, NumberUtils.sum(arr));
    }

    // Test case for an array with a single element
    @Test
    @DisplayName("sum({7}) should return 7")
    void sum_singleElementArray_returnsElementValue() {
        int[] arr = {7};
        assertEquals(7, NumberUtils.sum(arr));
    }

    // Test case for an array with all zeros
    @Test
    @DisplayName("sum({0, 0, 0}) should return 0")
    void sum_allZerosArray_returnsZero() {
        int[] arr = {0, 0, 0};
        assertEquals(0, NumberUtils.sum(arr));
    }

    // Test case for array with large sum
    @Test
    @DisplayName("sum({Integer.MAX_VALUE, 1}) should handle overflow and return expected sum in Java's int arithmetic")
    void sum_largeNumbers_returnsCorrectSum() {
        // In Java, int overflow wraps around.
        int[] arr = {Integer.MAX_VALUE, 1};
        assertEquals(Integer.MIN_VALUE, NumberUtils.sum(arr));
    }
}