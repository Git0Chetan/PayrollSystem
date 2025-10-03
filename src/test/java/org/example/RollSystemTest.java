package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class RollSystemTest {

    private RollSystem rollSystem;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        rollSystem = new RollSystem();
        // Setup for main method test to capture System.out
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Restore original System.out after each test
        System.setOut(originalOut);
    }

    // Test case for roll method with two positive numbers
    @Test
    void testRoll_PositiveNumbers() {
        assertEquals(5, rollSystem.roll(2, 3));
    }

    // Test case for roll method with a positive and a negative number
    @Test
    void testRoll_PositiveAndNegativeNumbers() {
        assertEquals(-1, rollSystem.roll(2, -3));
        assertEquals(1, rollSystem.roll(-2, 3));
    }

    // Test case for roll method with two negative numbers
    @Test
    void testRoll_NegativeNumbers() {
        assertEquals(-5, rollSystem.roll(-2, -3));
    }

    // Test case for roll method with zero
    @Test
    void testRoll_WithZero() {
        assertEquals(3, rollSystem.roll(0, 3));
        assertEquals(2, rollSystem.roll(2, 0));
        assertEquals(0, rollSystem.roll(0, 0));
    }

    // Test case for min method with two positive numbers where a > b
    @Test
    void testMin_PositiveNumbers_ResultPositive() {
        assertEquals(1, rollSystem.min(3, 2));
    }

    // Test case for min method with two positive numbers where a < b
    @Test
    void testMin_PositiveNumbers_ResultNegative() {
        assertEquals(-1, rollSystem.min(2, 3));
    }

    // Test case for min method with two negative numbers
    @Test
    void testMin_NegativeNumbers() {
        assertEquals(1, rollSystem.min(-2, -3)); // -2 - (-3) = -2 + 3 = 1
        assertEquals(-1, rollSystem.min(-3, -2)); // -3 - (-2) = -3 + 2 = -1
    }

    // Test case for min method with zero
    @Test
    void testMin_WithZero() {
        assertEquals(-3, rollSystem.min(0, 3));
        assertEquals(2, rollSystem.min(2, 0));
        assertEquals(0, rollSystem.min(0, 0));
    }

    // Test case for min method with same numbers
    @Test
    void testMin_SameNumbers() {
        assertEquals(0, rollSystem.min(5, 5));
    }

    // Test case for combination method with two positive numbers
    @Test
    void testCombination_PositiveNumbers() {
        assertEquals(6, rollSystem.combination(2, 3));
    }

    // Test case for combination method with a positive and a negative number
    @Test
    void testCombination_PositiveAndNegativeNumbers() {
        assertEquals(-6, rollSystem.combination(2, -3));
        assertEquals(-6, rollSystem.combination(-2, 3));
    }

    // Test case for combination method with two negative numbers
    @Test
    void testCombination_NegativeNumbers() {
        assertEquals(6, rollSystem.combination(-2, -3));
    }

    // Test case for combination method with zero
    @Test
    void testCombination_WithZero() {
        assertEquals(0, rollSystem.combination(0, 3));
        assertEquals(0, rollSystem.combination(2, 0));
        assertEquals(0, rollSystem.combination(0, 0));
    }

    // Test case for combination method with one
    @Test
    void testCombination_WithOne() {
        assertEquals(5, rollSystem.combination(5, 1));
        assertEquals(-5, rollSystem.combination(-5, 1));
        assertEquals(5, rollSystem.combination(-5, -1));
    }

    // Test case for minimize method with positive division
    @Test
    void testMinimize_PositiveDivision() {
        assertEquals(3, rollSystem.minimize(6, 2));
        assertEquals(2, rollSystem.minimize(7, 3)); // Integer division
    }

    // Test case for minimize method with negative division
    @Test
    void testMinimize_NegativeDivision() {
        assertEquals(-3, rollSystem.minimize(6, -2));
        assertEquals(-3, rollSystem.minimize(-6, 2));
        assertEquals(3, rollSystem.minimize(-6, -2));
    }

    // Test case for minimize method when numerator is zero
    @Test
    void testMinimize_NumeratorZero() {
        assertEquals(0, rollSystem.minimize(0, 5));
    }

    // Test case for minimize method when denominator is one
    @Test
    void testMinimize_DenominatorOne() {
        assertEquals(5, rollSystem.minimize(5, 1));
        assertEquals(-5, rollSystem.minimize(-5, 1));
    }

    // Test case for minimize method throwing IllegalArgumentException for division by zero
    @Test
    void testMinimize_DivisionByZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            rollSystem.minimize(10, 0);
        });
        assertEquals("Division by zero", thrown.getMessage());
    }

    // Test case for main method to check console output
    @Test
    void testMain() {
        // Since main is static, we call it directly
        RollSystem.main(new String[]{});

        String expectedOutput = "2+3 = 5" + System.lineSeparator() +
                                "6/2 = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}