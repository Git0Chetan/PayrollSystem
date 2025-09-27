package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtlityTest {

    // Test cases for factorial method

    // Test case for factorial of 0
    @Test
    void testFactorial_Zero() {
        assertEquals(1L, Utlity.factorial(0));
    }

    // Test case for factorial of 1
    @Test
    void testFactorial_One() {
        assertEquals(1L, Utlity.factorial(1));
    }

    // Test case for factorial of a positive number
    @Test
    void testFactorial_PositiveNumber() {
        assertEquals(120L, Utlity.factorial(5)); // 5! = 120
    }

    // Test case for factorial of another positive number
    @Test
    void testFactorial_AnotherPositiveNumber() {
        assertEquals(720L, Utlity.factorial(6)); // 6! = 720
    }

    // Test case for factorial of a larger positive number fitting in long
    @Test
    void testFactorial_LargePositiveNumber() {
        assertEquals(2432902008176640000L, Utlity.factorial(20)); // 20!
    }

    // Test case for negative input, expecting IllegalArgumentException
    @Test
    void testFactorial_NegativeNumber_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> Utlity.factorial(-1),
                "Expected factorial(-1) to throw IllegalArgumentException, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("n must be non-negative"));
    }

    // Test cases for isPrime method

    // Test case for n <= 1
    @Test
    void testIsPrime_LessThanOrEqualToOne() {
        assertFalse(Utlity.isPrime(0));
        assertFalse(Utlity.isPrime(1));
        assertFalse(Utlity.isPrime(-5));
    }

    // Test case for prime numbers 2 and 3
    @Test
    void testIsPrime_TwoAndThree() {
        assertTrue(Utlity.isPrime(2));
        assertTrue(Utlity.isPrime(3));
    }

    // Test case for even numbers (except 2)
    @Test
    void testIsPrime_EvenNumberExceptTwo() {
        assertFalse(Utlity.isPrime(4));
        assertFalse(Utlity.isPrime(6));
        assertFalse(Utlity.isPrime(100));
    }

    // Test case for multiples of 3 (except 3)
    @Test
    void testIsPrime_MultipleOfThreeExceptThree() {
        assertFalse(Utlity.isPrime(9));
        assertFalse(Utlity.isPrime(15));
    }

    // Test case for small prime numbers
    @Test
    void testIsPrime_SmallPrimes() {
        assertTrue(Utlity.isPrime(5));
        assertTrue(Utlity.isPrime(7));
        assertTrue(Utlity.isPrime(11));
        assertTrue(Utlity.isPrime(13));
    }

    // Test case for small composite numbers
    @Test
    void testIsPrime_SmallComposites() {
        assertFalse(Utlity.isPrime(8));
        assertFalse(Utlity.isPrime(25));
        assertFalse(Utlity.isPrime(33));
    }

    // Test case for larger prime numbers
    @Test
    void testIsPrime_LargerPrime() {
        assertTrue(Utlity.isPrime(97));
        assertTrue(Utlity.isPrime(101));
    }

    // Test case for larger composite numbers
    @Test
    void testIsPrime_LargerComposite() {
        assertFalse(Utlity.isPrime(91)); // 7 * 13
        assertFalse(Utlity.isPrime(121)); // 11 * 11
    }

    // Test cases for gcd method

    // Test case for positive numbers
    @Test
    void testGcd_PositiveNumbers() {
        assertEquals(5, Utlity.gcd(10, 15));
        assertEquals(6, Utlity.gcd(48, 18));
        assertEquals(1, Utlity.gcd(7, 11)); // Coprime
        assertEquals(12, Utlity.gcd(36, 48));
    }

    // Test case where one number is zero
    @Test
    void testGcd_OneNumberZero() {
        assertEquals(5, Utlity.gcd(0, 5));
        assertEquals(5, Utlity.gcd(5, 0));
        assertEquals(0, Utlity.gcd(0, 0)); // Both zero
    }

    // Test case with negative numbers
    @Test
    void testGcd_NegativeNumbers() {
        assertEquals(5, Utlity.gcd(-10, 15));
        assertEquals(5, Utlity.gcd(10, -15));
        assertEquals(5, Utlity.gcd(-10, -15));
        assertEquals(6, Utlity.gcd(-48, -18));
    }

    // Test case where one number divides another
    @Test
    void testGcd_OneDividesAnother() {
        assertEquals(6, Utlity.gcd(6, 12));
        assertEquals(7, Utlity.gcd(49, 7));
    }

    // Test cases for lcm method

    // Test case for positive numbers
    @Test
    void testLcm_PositiveNumbers() {
        assertEquals(12L, Utlity.lcm(4, 6));
        assertEquals(30L, Utlity.lcm(10, 15));
        assertEquals(77L, Utlity.lcm(7, 11)); // Coprime
        assertEquals(144L, Utlity.lcm(36, 48));
    }

    // Test case where one number is zero
    @Test
    void testLcm_OneNumberZero() {
        assertEquals(0L, Utlity.lcm(0, 5));
        assertEquals(0L, Utlity.lcm(5, 0));
        assertEquals(0L, Utlity.lcm(0, 0)); // Both zero
    }

    // Test case with negative numbers
    @Test
    void testLcm_NegativeNumbers() {
        assertEquals(12L, Utlity.lcm(-4, 6));
        assertEquals(12L, Utlity.lcm(4, -6));
        assertEquals(12L, Utlity.lcm(-4, -6));
        assertEquals(144L, Utlity.lcm(-36, -48));
    }

    // Test case where one number divides another
    @Test
    void testLcm_OneDividesAnother() {
        assertEquals(12L, Utlity.lcm(6, 12));
        assertEquals(49L, Utlity.lcm(49, 7));
    }

    // Test case with numbers that might overflow int before casting to long for product
    @Test
    void testLcm_LargeNumbersWithinLongCapacity() {
        // 60000 * 70000 = 4,200,000,000, which fits in long
        assertEquals(420000000L, Utlity.lcm(60000, 70000));
        assertEquals(100000000000L, Utlity.lcm(1000000, 100000)); // lcm(10^6, 10^5) = 10^6
        assertEquals(2147483640L, Utlity.lcm(10, 214748364)); // 214748364 is Integer.MAX_VALUE / 10 approximately
    }


    // Test cases for sum method

    // Test case for a normal array of positive integers
    @Test
    void testSum_PositiveNumbers() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(15, Utlity.sum(arr));
    }

    // Test case for an empty array
    @Test
    void testSum_EmptyArray() {
        int[] arr = {};
        assertEquals(0, Utlity.sum(arr));
    }

    // Test case for a null array
    @Test
    void testSum_NullArray() {
        assertEquals(0, Utlity.sum(null));
    }

    // Test case for an array with a single element
    @Test
    void testSum_SingleElementArray() {
        int[] arr = {100};
        assertEquals(100, Utlity.sum(arr));
    }

    // Test case for an array with negative numbers
    @Test
    void testSum_NegativeNumbers() {
        int[] arr = {-1, -2, -3};
        assertEquals(-6, Utlity.sum(arr));
    }

    // Test case for an array with mixed positive and negative numbers
    @Test
    void testSum_MixedNumbers() {
        int[] arr = {-1, 2, -3, 4, 0};
        assertEquals(2, Utlity.sum(arr));
    }

    // Test case for an array containing zero
    @Test
    void testSum_ArrayWithZero() {
        int[] arr = {0, 0, 0};
        assertEquals(0, Utlity.sum(arr));
    }

    // Test case for an array that sums up to Integer.MAX_VALUE / 2 (no overflow)
    @Test
    void testSum_LargeNumbersNoOverflow() {
        int[] arr = {Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2};
        assertEquals(Integer.MAX_VALUE - 1, Utlity.sum(arr));
    }

    // The sum method returns int, so an overflow would manifest as an incorrect int result.
    // This test demonstrates the overflow behavior for sum.
    // The expected behavior depends on whether the method is expected to handle overflow (it doesn't).
    // Here, we verify the Java integer overflow behavior.
    @Test
    void testSum_IntegerOverflowBehavior() {
        int[] arr = {Integer.MAX_VALUE, 1};
        // Expecting overflow, result should wrap around to MIN_VALUE
        assertEquals(Integer.MIN_VALUE, Utlity.sum(arr));
    }
}