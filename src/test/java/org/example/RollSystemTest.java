package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RollSystemTest {

    private RollSystem rollSystem;

    @BeforeEach
    void setUp() {
        rollSystem = new RollSystem();
    }

    // Test cases for roll method

    // Test case for positive numbers
    @Test
    @DisplayName("roll: Should return the sum of two positive numbers")
    void testRoll_positiveNumbers() {
        assertEquals(5, rollSystem.roll(2, 3));
    }

    // Test case for negative numbers
    @Test
    @DisplayName("roll: Should return the sum of two negative numbers")
    void testRoll_negativeNumbers() {
        assertEquals(-5, rollSystem.roll(-2, -3));
    }

    // Test case for a positive and a negative number
    @Test
    @DisplayName("roll: Should return the sum of a positive and a negative number")
    void testRoll_positiveAndNegative() {
        assertEquals(1, rollSystem.roll(3, -2));
        assertEquals(-1, rollSystem.roll(-3, 2));
    }

    // Test case for zero with a positive number
    @Test
    @DisplayName("roll: Should return the positive number when one operand is zero")
    void testRoll_zeroAndPositive() {
        assertEquals(5, rollSystem.roll(0, 5));
        assertEquals(5, rollSystem.roll(5, 0));
    }

    // Test case for zero with a negative number
    @Test
    @DisplayName("roll: Should return the negative number when one operand is zero")
    void testRoll_zeroAndNegative() {
        assertEquals(-5, rollSystem.roll(0, -5));
        assertEquals(-5, rollSystem.roll(-5, 0));
    }

    // Test case for two zeros
    @Test
    @DisplayName("roll: Should return zero when both operands are zero")
    void testRoll_twoZeros() {
        assertEquals(0, rollSystem.roll(0, 0));
    }

    // Test case for large numbers within int range
    @Test
    @DisplayName("roll: Should handle large numbers correctly without overflow for standard sum")
    void testRoll_largeNumbers() {
        assertEquals(2000000000, rollSystem.roll(1000000000, 1000000000));
        assertEquals(-2000000000, rollSystem.roll(-1000000000, -1000000000));
        assertEquals(Integer.MAX_VALUE, rollSystem.roll(Integer.MAX_VALUE - 1, 1));
        assertEquals(Integer.MIN_VALUE, rollSystem.roll(Integer.MIN_VALUE + 1, -1));
    }


    // Test cases for min method (subtraction)

    // Test case for two positive numbers (a > b)
    @Test
    @DisplayName("min: Should return the difference of two positive numbers (a > b)")
    void testMin_positive_aGreaterThanB() {
        assertEquals(2, rollSystem.min(5, 3));
    }

    // Test case for two positive numbers (a < b)
    @Test
    @DisplayName("min: Should return the difference of two positive numbers (a < b)")
    void testMin_positive_aLessThanB() {
        assertEquals(-2, rollSystem.min(3, 5));
    }

    // Test case for two positive numbers (a == b)
    @Test
    @DisplayName("min: Should return zero when two positive numbers are equal")
    void testMin_positive_aEqualsB() {
        assertEquals(0, rollSystem.min(5, 5));
    }

    // Test case for negative numbers (a > b)
    @Test
    @DisplayName("min: Should return the difference of two negative numbers (a > b)")
    void testMin_negative_aGreaterThanB() {
        assertEquals(2, rollSystem.min(-3, -5)); // -3 - (-5) = -3 + 5 = 2
    }

    // Test case for negative numbers (a < b)
    @Test
    @DisplayName("min: Should return the difference of two negative numbers (a < b)")
    void testMin_negative_aLessThanB() {
        assertEquals(-2, rollSystem.min(-5, -3)); // -5 - (-3) = -5 + 3 = -2
    }

    // Test case for negative numbers (a == b)
    @Test
    @DisplayName("min: Should return zero when two negative numbers are equal")
    void testMin_negative_aEqualsB() {
        assertEquals(0, rollSystem.min(-5, -5));
    }

    // Test case for zero with a positive number
    @Test
    @DisplayName("min: Should handle zero as first operand with a positive number")
    void testMin_zeroAndPositive_aIsZero() {
        assertEquals(-5, rollSystem.min(0, 5));
    }

    // Test case for zero with a positive number
    @Test
    @DisplayName("min: Should handle zero as second operand with a positive number")
    void testMin_positiveAndZero_bIsZero() {
        assertEquals(5, rollSystem.min(5, 0));
    }

    // Test case for zero with a negative number
    @Test
    @DisplayName("min: Should handle zero as first operand with a negative number")
    void testMin_zeroAndNegative_aIsZero() {
        assertEquals(5, rollSystem.min(0, -5)); // 0 - (-5) = 5
    }

    // Test case for zero with a negative number
    @Test
    @DisplayName("min: Should handle zero as second operand with a negative number")
    void testMin_negativeAndZero_bIsZero() {
        assertEquals(-5, rollSystem.min(-5, 0));
    }

    // Test case for two zeros
    @Test
    @DisplayName("min: Should return zero when both operands are zero")
    void testMin_twoZeros() {
        assertEquals(0, rollSystem.min(0, 0));
    }

    // Test case for large numbers within int range
    @Test
    @DisplayName("min: Should handle large numbers correctly")
    void testMin_largeNumbers() {
        assertEquals(1000000000, rollSystem.min(2000000000, 1000000000));
        assertEquals(-1000000000, rollSystem.min(1000000000, 2000000000));
        assertEquals(Integer.MAX_VALUE, rollSystem.min(Integer.MAX_VALUE, 0));
        assertEquals(Integer.MIN_VALUE, rollSystem.min(Integer.MIN_VALUE, 0));
    }


    // Test cases for combination method (multiplication)

    // Test case for two positive numbers
    @Test
    @DisplayName("combination: Should return the product of two positive numbers")
    void testCombination_positiveNumbers() {
        assertEquals(6, rollSystem.combination(2, 3));
    }

    // Test case for a positive and a negative number
    @Test
    @DisplayName("combination: Should return the product of a positive and a negative number")
    void testCombination_positiveAndNegative() {
        assertEquals(-6, rollSystem.combination(2, -3));
        assertEquals(-6, rollSystem.combination(-2, 3));
    }

    // Test case for two negative numbers
    @Test
    @DisplayName("combination: Should return the product of two negative numbers")
    void testCombination_negativeNumbers() {
        assertEquals(6, rollSystem.combination(-2, -3));
    }

    // Test case for multiplication by zero
    @Test
    @DisplayName("combination: Should return zero when one operand is zero")
    void testCombination_multiplicationByZero() {
        assertEquals(0, rollSystem.combination(5, 0));
        assertEquals(0, rollSystem.combination(0, 5));
        assertEquals(0, rollSystem.combination(0, 0));
    }

    // Test case for multiplication by one
    @Test
    @DisplayName("combination: Should return the number itself when multiplied by one")
    void testCombination_multiplicationByOne() {
        assertEquals(5, rollSystem.combination(5, 1));
        assertEquals(-5, rollSystem.combination(-5, 1));
        assertEquals(5, rollSystem.combination(1, 5));
    }

    // Test case for large numbers within int limits (no overflow handled by method)
    @Test
    @DisplayName("combination: Should handle large numbers correctly within int limits")
    void testCombination_largeNumbers() {
        assertEquals(1000000000, rollSystem.combination(1000, 1000000));
        assertEquals(-1000000000, rollSystem.combination(-1000, 1000000));
        // Demonstrating an overflow scenario, Java int multiplication truncates.
        // It's not a bug in the method, but how int multiplication works.
        assertEquals(2000000000, rollSystem.combination(100000, 20000));
    }


    // Test cases for minimize method (division)

    // Test case for positive division (exact)
    @Test
    @DisplayName("minimize: Should perform exact positive integer division")
    void testMinimize_positiveExact() {
        assertEquals(3, rollSystem.minimize(6, 2));
    }

    // Test case for positive division (with remainder)
    @Test
    @DisplayName("minimize: Should perform positive integer division with remainder truncation")
    void testMinimize_positiveRemainder() {
        assertEquals(2, rollSystem.minimize(7, 3)); // 7 / 3 = 2 (integer division)
    }

    // Test case for negative division (numerator negative)
    @Test
    @DisplayName("minimize: Should perform negative integer division when numerator is negative")
    void testMinimize_negativeNumerator() {
        assertEquals(-3, rollSystem.minimize(-6, 2));
        assertEquals(-2, rollSystem.minimize(-7, 3)); // -7 / 3 = -2
    }

    // Test case for negative division (denominator negative)
    @Test
    @DisplayName("minimize: Should perform negative integer division when denominator is negative")
    void testMinimize_negativeDenominator() {
        assertEquals(-3, rollSystem.minimize(6, -2));
        assertEquals(-2, rollSystem.minimize(7, -3)); // 7 / -3 = -2
    }

    // Test case for negative division (both negative)
    @Test
    @DisplayName("minimize: Should perform positive integer division when both are negative")
    void testMinimize_bothNegative() {
        assertEquals(3, rollSystem.minimize(-6, -2));
        assertEquals(2, rollSystem.minimize(-7, -3)); // -7 / -3 = 2
    }

    // Test case for zero numerator
    @Test
    @DisplayName("minimize: Should return zero when numerator is zero")
    void testMinimize_zeroNumerator() {
        assertEquals(0, rollSystem.minimize(0, 5));
        assertEquals(0, rollSystem.minimize(0, -5));
    }

    // Test case for division by one
    @Test
    @DisplayName("minimize: Should return the number itself when divided by one")
    void testMinimize_divisionByOne() {
        assertEquals(5, rollSystem.minimize(5, 1));
        assertEquals(-5, rollSystem.minimize(-5, 1));
    }

    // Test case for division by zero (expected exception)
    @Test
    @DisplayName("minimize: Should throw IllegalArgumentException for division by zero")
    void testMinimize_divisionByZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            rollSystem.minimize(10, 0);
        }, "minimize() should throw IllegalArgumentException when dividing by zero");
        assertEquals("Division by zero", thrown.getMessage());
    }

    // Test case for large numbers within int range
    @Test
    @DisplayName("minimize: Should handle large numbers correctly")
    void testMinimize_largeNumbers() {
        assertEquals(Integer.MAX_VALUE / 2, rollSystem.minimize(Integer.MAX_VALUE, 2));
        assertEquals(Integer.MIN_VALUE / 2, rollSystem.minimize(Integer.MIN_VALUE, 2));
    }


    // Test cases for main method

    // Test case for main method output
    @Test
    @DisplayName("main: Should print the correct calculations to standard output")
    void testMain() {
        // Capture standard output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Execute the main method
            RollSystem.main(new String[]{});

            // Verify the output
            String expectedOutput = "2+3 = 5" + System.lineSeparator() + "6/2 = 3" + System.lineSeparator();
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            // Restore original standard output
            System.setOut(originalOut);
        }
    }
}