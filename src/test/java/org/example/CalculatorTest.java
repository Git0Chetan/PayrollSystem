package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // Test case for adding two positive numbers
    @Test
    @DisplayName("Should correctly add two positive integers")
    void testAdd_PositiveNumbers() {
        assertEquals(5, calculator.add(2, 3), "2 + 3 should be 5");
    }

    // Test case for adding a positive and a negative number
    @Test
    @DisplayName("Should correctly add a positive and a negative integer")
    void testAdd_PositiveAndNegativeNumbers() {
        assertEquals(-1, calculator.add(2, -3), "2 + (-3) should be -1");
        assertEquals(1, calculator.add(-2, 3), "-2 + 3 should be 1");
    }

    // Test case for adding two negative numbers
    @Test
    @DisplayName("Should correctly add two negative integers")
    void testAdd_NegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3), "-2 + (-3) should be -5");
    }

    // Test case for adding with zero
    @Test
    @DisplayName("Should correctly add with zero")
    void testAdd_WithZero() {
        assertEquals(5, calculator.add(5, 0), "5 + 0 should be 5");
        assertEquals(-5, calculator.add(-5, 0), "-5 + 0 should be -5");
        assertEquals(0, calculator.add(0, 0), "0 + 0 should be 0");
    }

    // Test case for subtracting two positive numbers
    @Test
    @DisplayName("Should correctly subtract two positive integers")
    void testSubtract_PositiveNumbers() {
        assertEquals(1, calculator.subtract(3, 2), "3 - 2 should be 1");
    }

    // Test case for subtracting a smaller positive from a larger positive
    @Test
    @DisplayName("Should correctly subtract a smaller positive from a larger positive")
    void testSubtract_PositiveNumbers_ResultPositive() {
        assertEquals(5, calculator.subtract(10, 5), "10 - 5 should be 5");
    }

    // Test case for subtracting a larger positive from a smaller positive
    @Test
    @DisplayName("Should correctly subtract a larger positive from a smaller positive")
    void testSubtract_PositiveNumbers_ResultNegative() {
        assertEquals(-5, calculator.subtract(5, 10), "5 - 10 should be -5");
    }

    // Test case for subtracting negative numbers
    @Test
    @DisplayName("Should correctly subtract negative numbers")
    void testSubtract_NegativeNumbers() {
        assertEquals(1, calculator.subtract(-2, -3), "-2 - (-3) should be 1");
        assertEquals(-1, calculator.subtract(-3, -2), "-3 - (-2) should be -1");
    }

    // Test case for subtracting with zero
    @Test
    @DisplayName("Should correctly subtract with zero")
    void testSubtract_WithZero() {
        assertEquals(5, calculator.subtract(5, 0), "5 - 0 should be 5");
        assertEquals(-5, calculator.subtract(-5, 0), "-5 - 0 should be -5");
        assertEquals(0, calculator.subtract(0, 0), "0 - 0 should be 0");
    }

    // Test case for multiplying two positive numbers
    @Test
    @DisplayName("Should correctly multiply two positive integers")
    void testMultiply_PositiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3), "2 * 3 should be 6");
    }

    // Test case for multiplying a positive and a negative number
    @Test
    @DisplayName("Should correctly multiply a positive and a negative integer")
    void testMultiply_PositiveAndNegativeNumbers() {
        assertEquals(-6, calculator.multiply(2, -3), "2 * (-3) should be -6");
        assertEquals(-6, calculator.multiply(-2, 3), "-2 * 3 should be -6");
    }

    // Test case for multiplying two negative numbers
    @Test
    @DisplayName("Should correctly multiply two negative integers")
    void testMultiply_NegativeNumbers() {
        assertEquals(6, calculator.multiply(-2, -3), "-2 * (-3) should be 6");
    }

    // Test case for multiplying with zero
    @Test
    @DisplayName("Should correctly multiply with zero")
    void testMultiply_WithZero() {
        assertEquals(0, calculator.multiply(5, 0), "5 * 0 should be 0");
        assertEquals(0, calculator.multiply(0, 5), "0 * 5 should be 0");
        assertEquals(0, calculator.multiply(0, 0), "0 * 0 should be 0");
        assertEquals(0, calculator.multiply(-5, 0), "-5 * 0 should be 0");
    }

    // Test case for multiplying by one
    @Test
    @DisplayName("Should correctly multiply by one")
    void testMultiply_ByOne() {
        assertEquals(5, calculator.multiply(5, 1), "5 * 1 should be 5");
        assertEquals(-5, calculator.multiply(-5, 1), "-5 * 1 should be -5");
    }

    // Test case for dividing two positive numbers
    @Test
    @DisplayName("Should correctly divide two positive integers")
    void testDivide_PositiveNumbers() {
        assertEquals(3, calculator.divide(6, 2), "6 / 2 should be 3");
    }

    // Test case for dividing with negative numbers
    @Test
    @DisplayName("Should correctly divide with negative integers")
    void testDivide_NegativeNumbers() {
        assertEquals(-3, calculator.divide(6, -2), "6 / (-2) should be -3");
        assertEquals(-3, calculator.divide(-6, 2), "-6 / 2 should be -3");
        assertEquals(3, calculator.divide(-6, -2), "-6 / (-2) should be 3");
    }

    // Test case for division by one
    @Test
    @DisplayName("Should correctly divide by one")
    void testDivide_ByOne() {
        assertEquals(5, calculator.divide(5, 1), "5 / 1 should be 5");
        assertEquals(-5, calculator.divide(-5, 1), "-5 / 1 should be -5");
    }

    // Test case for division resulting in zero
    @Test
    @DisplayName("Should correctly divide resulting in zero")
    void testDivide_ResultZero() {
        assertEquals(0, calculator.divide(0, 5), "0 / 5 should be 0");
        assertEquals(0, calculator.divide(0, -5), "0 / -5 should be 0");
    }

    // Test case for division by zero (exception handling)
    @Test
    @DisplayName("Should throw IllegalArgumentException when dividing by zero")
    void testDivide_ByZero_ThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        }, "Dividing by zero should throw IllegalArgumentException");
        assertEquals("Division by zero", thrown.getMessage(), "Exception message should be 'Division by zero'");
    }

    // Test case for the main method's console output
    @Test
    @DisplayName("Should print correct output to console in main method")
    void testMain_PrintsCorrectOutput() {
        // Redirect System.out to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Execute the main method
            Calculator.main(new String[]{});

            // Verify the output
            String expectedOutput = "2+3 = 5" + System.lineSeparator() +
                                    "6/2 = 3" + System.lineSeparator();
            assertEquals(expectedOutput, outContent.toString(), "Main method output should match expected string");
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}