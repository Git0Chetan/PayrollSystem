package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NumberFormat Utility Tests")
final class NumberFormatTest {

    // --- factorial() tests ---

    // Test case for a negative input, expecting an IllegalArgumentException
    @Test
    @DisplayName("factorial() should throw IllegalArgumentException for negative input")
    void factorial_NegativeInput_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            NumberFormat.factorial(-1);
        }, "factorial(-1) should throw IllegalArgumentException");
        assertEquals("n must be non-negative", thrown.getMessage());
    }

    // Test case for factorial of 0
    @Test
    @DisplayName("factorial() should return 1 for 0")
    void factorial_Zero_ReturnsOne() {
        assertEquals(1, NumberFormat.factorial(0), "factorial(0) should be 1");
    }

    // Test case for factorial of 1
    @Test
    @DisplayName("factorial() should return 1 for 1")
    void factorial_One_ReturnsOne() {
        assertEquals(1, NumberFormat.factorial(1), "factorial(1) should be 1");
    }

    // Test case for a small positive integer
    @Test
    @DisplayName("factorial() should return correct value for small positive integer")
    void factorial_SmallPositive_ReturnsCorrectValue() {
        assertEquals(120, NumberFormat.factorial(5), "factorial(5) should be 120");
    }

    // Test case for a larger positive integer
    @Test
    @DisplayName("factorial() should return correct value for larger positive integer")
    void factorial_LargerPositive_ReturnsCorrectValue() {
        assertEquals(3628800L, NumberFormat.factorial(10), "factorial(10) should be 3628800");
    }

    // Test case for a value that pushes the long limit, ensuring no overflow within long range
    @Test
    @DisplayName("factorial() should return correct value for large N fitting in long")
    void factorial_LargeN_ReturnsCorrectValue() {
        // 20! is the largest factorial that fits in a signed 64-bit integer (long)
        assertEquals(2432902008176640000L, NumberFormat.factorial(20), "factorial(20) should be 2432902008176640000L");
    }

    // --- gcd() tests ---

    // Test case for two positive numbers
    @Test
    @DisplayName("gcd() should return correct GCD for two positive numbers")
    void gcd_TwoPositiveNumbers_ReturnsCorrectGCD() {
        assertEquals(6, NumberFormat.gcd(48, 18), "gcd(48, 18) should be 6");
        assertEquals(1, NumberFormat.gcd(17, 13), "gcd(17, 13) should be 1 (coprime)");
        assertEquals(10, NumberFormat.gcd(10, 20), "gcd(10, 20) should be 10");
    }

    // Test case for one number being zero
    @Test
    @DisplayName("gcd() should return the absolute value of the non-zero number when one is zero")
    void gcd_OneNumberIsZero_ReturnsOtherNumber() {
        assertEquals(5, NumberFormat.gcd(0, 5), "gcd(0, 5) should be 5");
        assertEquals(7, NumberFormat.gcd(7, 0), "gcd(7, 0) should be 7");
    }

    // Test case for both numbers being zero
    @Test
    @DisplayName("gcd() should return 0 when both numbers are zero")
    void gcd_BothNumbersAreZero_ReturnsZero() {
        assertEquals(0, NumberFormat.gcd(0, 0), "gcd(0, 0) should be 0");
    }

    // Test case for negative inputs
    @Test
    @DisplayName("gcd() should return correct GCD for negative numbers (absolute value logic)")
    void gcd_NegativeNumbers_ReturnsCorrectGCD() {
        assertEquals(6, NumberFormat.gcd(-48, 18), "gcd(-48, 18) should be 6");
        assertEquals(6, NumberFormat.gcd(48, -18), "gcd(48, -18) should be 6");
        assertEquals(6, NumberFormat.gcd(-48, -18), "gcd(-48, -18) should be 6");
        assertEquals(1, NumberFormat.gcd(-17, 13), "gcd(-17, 13) should be 1");
    }

    // Test case where one number is a multiple of the other
    @Test
    @DisplayName("gcd() should return the smaller number when one is a multiple of the other")
    void gcd_OneIsMultipleOfOther_ReturnsSmaller() {
        assertEquals(10, NumberFormat.gcd(10, 100), "gcd(10, 100) should be 10");
        assertEquals(10, NumberFormat.gcd(100, 10), "gcd(100, 10) should be 10");
    }

    // --- lcm() tests ---

    // Test case for two positive numbers
    @Test
    @DisplayName("lcm() should return correct LCM for two positive numbers")
    void lcm_TwoPositiveNumbers_ReturnsCorrectLCM() {
        assertEquals(36, NumberFormat.lcm(12, 18), "lcm(12, 18) should be 36");
        assertEquals(35, NumberFormat.lcm(7, 5), "lcm(7, 5) should be 35");
        assertEquals(100, NumberFormat.lcm(10, 20), "lcm(10, 20) should be 100");
    }

    // Test case for one number being zero
    @Test
    @DisplayName("lcm() should return 0 when one number is zero")
    void lcm_OneNumberIsZero_ReturnsZero() {
        assertEquals(0, NumberFormat.lcm(0, 5), "lcm(0, 5) should be 0");
        assertEquals(0, NumberFormat.lcm(7, 0), "lcm(7, 0) should be 0");
    }

    // Test case for both numbers being zero
    @Test
    @DisplayName("lcm() should return 0 when both numbers are zero")
    void lcm_BothNumbersAreZero_ReturnsZero() {
        assertEquals(0, NumberFormat.lcm(0, 0), "lcm(0, 0) should be 0");
    }

    // Test case for negative inputs, expecting positive LCM
    @Test
    @DisplayName("lcm() should return positive LCM for negative numbers")
    void lcm_NegativeNumbers_ReturnsPositiveLCM() {
        assertEquals(36, NumberFormat.lcm(-12, 18), "lcm(-12, 18) should be 36");
        assertEquals(36, NumberFormat.lcm(12, -18), "lcm(12, -18) should be 36");
        assertEquals(36, NumberFormat.lcm(-12, -18), "lcm(-12, -18) should be 36");
    }

    // Test case for maximum integer values that could lead to overflow if not handled carefully (though method uses long)
    @Test
    @DisplayName("lcm() should handle large prime numbers without overflow (within long limits)")
    void lcm_LargePrimes_ReturnsCorrectLCM() {
        // Two large primes whose LCM fits in long
        // 999999937 and 999999929 are large primes
        // LCM(a, b) = |a * b| / GCD(a, b)
        // Since they are primes, GCD is 1, LCM is their product
        // 999999937L * 999999929L = 999999866000008773L
        assertEquals(999999866000008773L, NumberFormat.lcm(999999937, 999999929), "LCM of large primes");
    }

    // --- isPrime() tests ---

    // Test case for numbers less than or equal to 1
    @Test
    @DisplayName("isPrime() should return false for numbers <= 1")
    void isPrime_LessThanOrEqualToOne_ReturnsFalse() {
        assertFalse(NumberFormat.isPrime(0), "0 should not be prime");
        assertFalse(NumberFormat.isPrime(1), "1 should not be prime");
        assertFalse(NumberFormat.isPrime(-5), "-5 should not be prime");
    }

    // Test case for the first prime number (2)
    @Test
    @DisplayName("isPrime() should return true for 2")
    void isPrime_Two_ReturnsTrue() {
        assertTrue(NumberFormat.isPrime(2), "2 should be prime");
    }

    // Test case for the first odd prime number (3)
    @Test
    @DisplayName("isPrime() should return true for 3")
    void isPrime_Three_ReturnsTrue() {
        assertTrue(NumberFormat.isPrime(3), "3 should be prime");
    }

    // Test case for an even composite number
    @Test
    @DisplayName("isPrime() should return false for even composite numbers")
    void isPrime_EvenComposite_ReturnsFalse() {
        assertFalse(NumberFormat.isPrime(4), "4 should not be prime");
        assertFalse(NumberFormat.isPrime(6), "6 should not be prime");
        assertFalse(NumberFormat.isPrime(100), "100 should not be prime");
    }

    // Test case for odd composite numbers
    @Test
    @DisplayName("isPrime() should return false for odd composite numbers")
    void isPrime_OddComposite_ReturnsFalse() {
        assertFalse(NumberFormat.isPrime(9), "9 should not be prime");
        assertFalse(NumberFormat.isPrime(15), "15 should not be prime");
        assertFalse(NumberFormat.isPrime(25), "25 should not be prime");
        assertFalse(NumberFormat.isPrime(49), "49 should not be prime");
    }

    // Test case for various prime numbers
    @Test
    @DisplayName("isPrime() should return true for various prime numbers")
    void isPrime_VariousPrimes_ReturnsTrue() {
        assertTrue(NumberFormat.isPrime(5), "5 should be prime");
        assertTrue(NumberFormat.isPrime(7), "7 should be prime");
        assertTrue(NumberFormat.isPrime(11), "11 should be prime");
        assertTrue(NumberFormat.isPrime(13), "13 should be prime");
        assertTrue(NumberFormat.isPrime(17), "17 should be prime");
        assertTrue(NumberFormat.isPrime(97), "97 should be prime");
    }

    // Test case for a larger prime number
    @Test
    @DisplayName("isPrime() should return true for a larger prime number")
    void isPrime_LargePrime_ReturnsTrue() {
        assertTrue(NumberFormat.isPrime(101), "101 should be prime");
        assertTrue(NumberFormat.isPrime(997), "997 should be prime");
    }

    // Test case for a larger composite number
    @Test
    @DisplayName("isPrime() should return false for a larger composite number")
    void isPrime_LargeComposite_ReturnsFalse() {
        assertFalse(NumberFormat.isPrime(1000), "1000 should not be prime");
        assertFalse(NumberFormat.isPrime(121), "121 (11*11) should not be prime");
    }

    // --- binomial() tests ---

    // Test case for k < 0
    @Test
    @DisplayName("binomial() should return 0 when k is negative")
    void binomial_KIsNegative_ReturnsZero() {
        assertEquals(0, NumberFormat.binomial(5, -1), "binomial(5, -1) should be 0");
    }

    // Test case for k > n
    @Test
    @DisplayName("binomial() should return 0 when k is greater than n")
    void binomial_KGreaterThanN_ReturnsZero() {
        assertEquals(0, NumberFormat.binomial(3, 5), "binomial(3, 5) should be 0");
    }

    // Test case for C(n, 0)
    @Test
    @DisplayName("binomial() should return 1 for C(n, 0)")
    void binomial_KIsZero_ReturnsOne() {
        assertEquals(1, NumberFormat.binomial(5, 0), "binomial(5, 0) should be 1");
        assertEquals(1, NumberFormat.binomial(0, 0), "binomial(0, 0) should be 1");
    }

    // Test case for C(n, n)
    @Test
    @DisplayName("binomial() should return 1 for C(n, n)")
    void binomial_KIsN_ReturnsOne() {
        assertEquals(1, NumberFormat.binomial(5, 5), "binomial(5, 5) should be 1");
    }

    // Test case for C(n, 1)
    @Test
    @DisplayName("binomial() should return n for C(n, 1)")
    void binomial_KIsOne_ReturnsN() {
        assertEquals(5, NumberFormat.binomial(5, 1), "binomial(5, 1) should be 5");
    }

    // Test case for a typical combination C(n, k)
    @Test
    @DisplayName("binomial() should return correct value for typical C(n, k)")
    void binomial_TypicalCase_ReturnsCorrectValue() {
        assertEquals(10, NumberFormat.binomial(5, 2), "binomial(5, 2) should be 10");
        assertEquals(6, NumberFormat.binomial(4, 2), "binomial(4, 2) should be 6");
        assertEquals(120, NumberFormat.binomial(10, 3), "binomial(10, 3) should be 120");
    }

    // Test case for symmetry C(n, k) = C(n, n-k)
    @Test
    @DisplayName("binomial() should demonstrate symmetry C(n, k) = C(n, n-k)")
    void binomial_Symmetry_ReturnsSameValue() {
        assertEquals(NumberFormat.binomial(5, 2), NumberFormat.binomial(5, 3), "C(5, 2) should equal C(5, 3)");
        assertEquals(NumberFormat.binomial(10, 7), NumberFormat.binomial(10, 3), "C(10, 7) should equal C(10, 3)");
    }

    // Test case for larger values that fit into long
    @Test
    @DisplayName("binomial() should return correct value for larger inputs fitting in long")
    void binomial_LargerInputs_ReturnsCorrectValue() {
        assertEquals(184756L, NumberFormat.binomial(20, 10), "C(20, 10) should be 184756");
        assertEquals(155117520L, NumberFormat.binomial(30, 15), "C(30, 15) should be 155117520");
        assertEquals(60088523780L, NumberFormat.binomial(40, 20), "C(40, 20) should be 60088523780");
        // Maximum C(n, k) for int n, k, that fits in long: C(67, 33) = 6271923485078519200L
        assertEquals(6271923485078519200L, NumberFormat.binomial(67, 33), "C(67, 33) should be 6271923485078519200");
    }
}