package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    private Calculator calculator;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        // Redirect System.out for main method testing
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Test add method with positive numbers")
    void testAddPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("Test add method with negative numbers")
    void testAddNegativeNumbers() {
        assertEquals(-8, calculator.add(-5, -3));
    }

    @Test
    @DisplayName("Test add method with mixed positive and negative numbers")
    void testAddMixedNumbers() {
        assertEquals(0, calculator.add(-1, 1));
        assertEquals(-2, calculator.add(3, -5));
    }

    @Test
    @DisplayName("Test add method with zeros")
    void testAddZeros() {
        assertEquals(0, calculator.add(0, 0));
        assertEquals(5, calculator.add(5, 0));
    }

    @Test
    @DisplayName("Test subtract method with positive numbers")
    void testSubtractPositiveNumbers() {
        assertEquals(3, calculator.subtract(5, 2));
    }

    @Test
    @DisplayName("Test subtract method with result being negative")
    void testSubtractNegativeResult() {
        assertEquals(-3, calculator.subtract(2, 5));
    }

    @Test
    @DisplayName("Test subtract method with negative numbers")
    void testSubtractNegativeNumbers() {
        assertEquals(-3, calculator.subtract(-5, -2));
        assertEquals(3, calculator.subtract(-2, -5));
    }

    @Test
    @DisplayName("Test subtract method with zeros")
    void testSubtractZeros() {
        assertEquals(0, calculator.subtract(0, 0));
        assertEquals(5, calculator.subtract(5, 0));
        assertEquals(-5, calculator.subtract(0, 5));
    }

    @Test
    @DisplayName("Test multiply method with positive numbers")
    void testMultiplyPositiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    @DisplayName("Test multiply method with one negative number")
    void testMultiplyOneNegativeNumber() {
        assertEquals(-6, calculator.multiply(-2, 3));
        assertEquals(-6, calculator.multiply(2, -3));
    }

    @Test
    @DisplayName("Test multiply method with two negative numbers")
    void testMultiplyTwoNegativeNumbers() {
        assertEquals(6, calculator.multiply(-2, -3));
    }

    @Test
    @DisplayName("Test multiply method with zero")
    void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(0, 5));
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, 0));
    }

    @Test
    @DisplayName("Test divide method with positive numbers")
    void testDividePositiveNumbers() {
        assertEquals(3, calculator.divide(6, 2));
        assertEquals(3, calculator.divide(7, 2)); // Integer division
    }

    @Test
    @DisplayName("Test divide method with negative numbers")
    void testDivideNegativeNumbers() {
        assertEquals(-3, calculator.divide(-6, 2));
        assertEquals(-3, calculator.divide(6, -2));
        assertEquals(3, calculator.divide(-6, -2));
        assertEquals(-3, calculator.divide(-7, 2)); // Integer division
    }

    @Test
    @DisplayName("Test divide method with dividend as zero")
    void testDivideZeroByNonZero() {
        assertEquals(0, calculator.divide(0, 5));
    }

    @Test
    @DisplayName("Test divide method throws IllegalArgumentException for division by zero")
    void testDivideByZeroThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(5, 0);
        });
        assertEquals("Division by zero", thrown.getMessage());
    }

    @Test
    @DisplayName("Test main method output")
    void testMainMethodOutput() {
        // Execute the main method
        Calculator.main(new String[]{});

        // Verify the captured output
        String expectedOutput = "2+3 = 5" + System.lineSeparator() +
                                "6/2 = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}