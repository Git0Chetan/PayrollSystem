package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Utils Test Suite")
class UtlityTest {

    // Test cases for factorial method

    // Test case for factorial of 0
    @Test
    @DisplayName("factorial(0) should return 1")
    void testFactorial_Zero() {
        assertEquals(1L, Utlity.factorial(0));
    }

    // Test case for factorial of 1
    @Test
    @DisplayName("factorial(1) should return 1")
    void testFactorial_One() {
        assertEquals(1L, Utlity.factorial(1));
    }

    // Test case for factorial of a small positive number
    @Test
    @DisplayName("factorial(5) should return 120")
    void testFactorial_PositiveSmallNumber() {
        assertEquals(120L, Utlity.factorial(5));
    }

    // Test case for factorial of a larger positive number
    @Test
    @DisplayName("factorial(10) should return 3628800")
    void testFactorial_PositiveLargerNumber() {
        assertEquals(3628800L, Utlity.factorial(10));
    }

    // Test case for factorial of an edge positive number (max for int that fits long)
    @Test
    @DisplayName("factorial(20) should return 2432902008176640000L")
    void testFactorial_MaxIntFitsLong() {
        assertEquals(2432902008176640000L, Utlity.factorial(20));
    }

    // Test case for negative input to factorial (should throw IllegalArgumentException)
    @Test
    @DisplayName("factorial(-1) should throw IllegalArgumentException")
    void testFactorial_NegativeNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Utlity.factorial(-1),
                "n must be non-negative");
    }

    // Test cases for isPrime method

    // Test case for 0 (not prime)
    @Test
    @DisplayName("isPrime(0) should return false")
    void testIsPrime_Zero() {
        assertFalse(Utlity.isPrime(0));
    }

    // Test case for 1 (not prime)
    @Test
    @DisplayName("isPrime(1) should return false")
    void testIsPrime_One() {
        assertFalse(Utlity.isPrime(1));
    }

    // Test case for 2 (smallest prime)
    @Test
    @DisplayName("isPrime(2) should return true")
    void testIsPrime_Two() {
        assertTrue(Utlity.isPrime(2));
    }

    // Test case for 3 (prime)
    @Test
    @DisplayName("isPrime(3) should return true")
    void testIsPrime_Three() {
        assertTrue(Utlity.isPrime(3));
    }

    // Test case for 4 (not prime)
    @Test
    @DisplayName("isPrime(4) should return false")
    void testIsPrime_Four() {
        assertFalse(Utlity.isPrime(4));
    }

    // Test case for a small prime number
    @Test
    @DisplayName("isPrime(7) should return true")
    void testIsPrime_SmallPrime() {
        assertTrue(Utlity.isPrime(7));
    }

    // Test case for a small composite number
    @Test
    @DisplayName("isPrime(9) should return false")
    void testIsPrime_SmallComposite() {
        assertFalse(Utlity.isPrime(9));
    }

    // Test case for a larger prime number
    @Test
    @DisplayName("isPrime(17) should return true")
    void testIsPrime_LargerPrime() {
        assertTrue(Utlity.isPrime(17));
    }

    // Test case for a larger composite number
    @Test
    @DisplayName("isPrime(25) should return false")
    void testIsPrime_LargerComposite() {
        assertFalse(Utlity.isPrime(25));
    }

    // Test case for a prime number that is a multiple of 5 + 2
    @Test
    @DisplayName("isPrime(23) should return true")
    void testIsPrime_AnotherPrime() {
        assertTrue(Utlity.isPrime(23));
    }

    // Test case for a number that fails prime check due to factor i+2
    @Test
    @DisplayName("isPrime(49) should return false")
    void testIsPrime_CompositeFromIPlusTwoFactor() {
        assertFalse(Utlity.isPrime(49)); // 49 = 7 * 7
    }

    // Test case for a larger prime using the loop optimization
    @Test
    @DisplayName("isPrime(97) should return true")
    void testIsPrime_LargePrimeOptimizedLoop() {
        assertTrue(Utlity.isPrime(97));
    }

    // Test case for a larger composite using the loop optimization
    @Test
    @DisplayName("isPrime(121) should return false")
    void testIsPrime_LargeCompositeOptimizedLoop() {
        assertFalse(Utlity.isPrime(121)); // 11 * 11
    }


    // Test cases for gcd method

    // Test case for two positive numbers
    @Test
    @DisplayName("gcd(48, 18) should return 6")
    void testGcd_TwoPositiveNumbers() {
        assertEquals(6, Utlity.gcd(48, 18));
    }

    // Test case where one number is a multiple of the other
    @Test
    @DisplayName("gcd(10, 5) should return 5")
    void testGcd_OneMultipleOfOther() {
        assertEquals(5, Utlity.gcd(10, 5));
    }

    // Test case where numbers are coprime
    @Test
    @DisplayName("gcd(7, 13) should return 1")
    void testGcd_CoprimeNumbers() {
        assertEquals(1, Utlity.gcd(7, 13));
    }

    // Test case with one number as zero
    @Test
    @DisplayName("gcd(0, 5) should return 5")
    void testGcd_OneZero() {
        assertEquals(5, Utlity.gcd(0, 5));
    }

    // Test case with the other number as zero
    @Test
    @DisplayName("gcd(7, 0) should return 7")
    void testGcd_OtherZero() {
        assertEquals(7, Utlity.gcd(7, 0));
    }

    // Test case with both numbers as zero (edge case, GCD is typically undefined, but Euclidean algo returns 0)
    // The implementation returns 0 for gcd(0,0) based on Euclidean algorithm for non-negative integers
    @Test
    @DisplayName("gcd(0, 0) should return 0")
    void testGcd_BothZero() {
        assertEquals(0, Utlity.gcd(0, 0));
    }

    // Test case with negative numbers (implementation takes absolute values)
    @Test
    @DisplayName("gcd(-48, 18) should return 6")
    void testGcd_NegativeAndPositive() {
        assertEquals(6, Utlity.gcd(-48, 18));
    }

    // Test case with both negative numbers
    @Test
    @DisplayName("gcd(-18, -48) should return 6")
    void testGcd_BothNegative() {
        assertEquals(6, Utlity.gcd(-18, -48));
    }

    // Test case with order swapped
    @Test
    @DisplayName("gcd(18, 48) should return 6")
    void testGcd_OrderSwapped() {
        assertEquals(6, Utlity.gcd(18, 48));
    }

    // Test cases for lcm method

    // Test case for two positive numbers
    @Test
    @DisplayName("lcm(4, 6) should return 12")
    void testLcm_TwoPositiveNumbers() {
        assertEquals(12L, Utlity.lcm(4, 6));
    }

    // Test case where numbers are coprime
    @Test
    @DisplayName("lcm(7, 13) should return 91")
    void testLcm_CoprimeNumbers() {
        assertEquals(91L, Utlity.lcm(7, 13));
    }

    // Test case where one number is a multiple of the other
    @Test
    @DisplayName("lcm(10, 5) should return 10")
    void testLcm_OneMultipleOfOther() {
        assertEquals(10L, Utlity.lcm(10, 5));
    }

    // Test case with one number as zero
    @Test
    @DisplayName("lcm(0, 5) should return 0")
    void testLcm_OneZero() {
        assertEquals(0L, Utlity.lcm(0, 5));
    }

    // Test case with the other number as zero
    @Test
    @DisplayName("lcm(7, 0) should return 0")
    void testLcm_OtherZero() {
        assertEquals(0L, Utlity.lcm(7, 0));
    }

    // Test case with both numbers as zero
    @Test
    @DisplayName("lcm(0, 0) should return 0")
    void testLcm_BothZero() {
        assertEquals(0L, Utlity.lcm(0, 0));
    }

    // Test case with negative numbers (implementation takes absolute values)
    @Test
    @DisplayName("lcm(-4, 6) should return 12")
    void testLcm_NegativeAndPositive() {
        assertEquals(12L, Utlity.lcm(-4, 6));
    }

    // Test case with both negative numbers
    @Test
    @DisplayName("lcm(-4, -6) should return 12")
    void testLcm_BothNegative() {
        assertEquals(12L, Utlity.lcm(-4, -6));
    }


    // Test cases for sum method

    // Test case for an array with positive numbers
    @Test
    @DisplayName("sum([1, 2, 3, 4, 5]) should return 15")
    void testSum_PositiveNumbers() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(15, Utlity.sum(arr));
    }

    // Test case for an array with negative numbers
    @Test
    @DisplayName("sum([-1, -2, -3]) should return -6")
    void testSum_NegativeNumbers() {
        int[] arr = {-1, -2, -3};
        assertEquals(-6, Utlity.sum(arr));
    }

    // Test case for an array with mixed positive and negative numbers
    @Test
    @DisplayName("sum([-1, 0, 10, -5, 2]) should return 6")
    void testSum_MixedNumbers() {
        int[] arr = {-1, 0, 10, -5, 2};
        assertEquals(6, Utlity.sum(arr));
    }

    // Test case for an array containing only zero
    @Test
    @DisplayName("sum([0, 0, 0]) should return 0")
    void testSum_OnlyZeros() {
        int[] arr = {0, 0, 0};
        assertEquals(0, Utlity.sum(arr));
    }

    // Test case for an empty array
    @Test
    @DisplayName("sum([]) should return 0")
    void testSum_EmptyArray() {
        int[] arr = {};
        assertEquals(0, Utlity.sum(arr));
    }

    // Test case for a null array input
    @Test
    @DisplayName("sum(null) should return 0")
    void testSum_NullArray() {
        assertEquals(0, Utlity.sum(null));
    }

    // Test case for an array with a single element
    @Test
    @DisplayName("sum([42]) should return 42")
    void testSum_SingleElementArray() {
        int[] arr = {42};
        assertEquals(42, Utlity.sum(arr));
    }
}