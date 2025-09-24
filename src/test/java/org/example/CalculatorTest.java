package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testAddPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(100, calculator.add(70, 30));
    }

    @Test
    void testAddNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
        assertEquals(-100, calculator.add(-70, -30));
    }

    @Test
    void testAddZero() {
        assertEquals(3, calculator.add(3, 0));
        assertEquals(-5, calculator.add(-5, 0));
        assertEquals(0, calculator.add(0, 0));
    }

    @Test
    void testAddPositiveAndNegative() {
        assertEquals(1, calculator.add(3, -2));
        assertEquals(-1, calculator.add(-3, 2));
        assertEquals(0, calculator.add(5, -5));
    }

    @Test
    void testSubtractPositiveNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
        assertEquals(-1, calculator.subtract(2, 3));
        assertEquals(40, calculator.subtract(70, 30));
    }

    @Test
    void testSubtractNegativeNumbers() {
        assertEquals(1, calculator.subtract(-2, -3)); // -2 - (-3) = 1
        assertEquals(-1, calculator.subtract(-3, -2)); // -3 - (-2) = -1
    }

    @Test
    void testSubtractZero() {
        assertEquals(3, calculator.subtract(3, 0));
        assertEquals(-5, calculator.subtract(-5, 0));
        assertEquals(0, calculator.subtract(0, 0));
    }

    @Test
    void testSubtractPositiveAndNegative() {
        assertEquals(5, calculator.subtract(3, -2)); // 3 - (-2) = 5
        assertEquals(-5, calculator.subtract(-3, 2)); // -3 - 2 = -5
    }

    @Test
    void testMultiplyPositiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
        assertEquals(2100, calculator.multiply(70, 30));
    }

    @Test
    void testMultiplyNegativeNumbers() {
        assertEquals(6, calculator.multiply(-2, -3));
        assertEquals(-2100, calculator.multiply(-70, 30));
    }

    @Test
    void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(-5, 0));
        assertEquals(0, calculator.multiply(0, 0));
    }

    @Test
    void testDividePositiveNumbers() {
        assertEquals(3, calculator.divide(6, 2));
        assertEquals(10, calculator.divide(100, 10));
    }

    @Test
    void testDivideNegativeNumbers() {
        assertEquals(3, calculator.divide(-6, -2));
        assertEquals(-3, calculator.divide(6, -2));
        assertEquals(-3, calculator.divide(-6, 2));
    }

    @Test
    void testDivideByOne() {
        assertEquals(5, calculator.divide(5, 1));
        assertEquals(-5, calculator.divide(-5, 1));
    }

    @Test
    void testDivideZeroByPositive() {
        assertEquals(0, calculator.divide(0, 5));
    }

    @Test
    void testDivideThrowsExceptionWhenDividingByZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
        assertEquals("Division by zero", exception.getMessage());
    }

    @Test
    void testMainMethodOutput() {
        // Store original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Set System.out to the custom stream
        System.setOut(new PrintStream(bos));

        try {
            // Call the main method
            Calculator.main(new String[]{});

            // Get the output
            String expectedOutput = "2+3 = 5" + System.lineSeparator() +
                                    "6/2 = 3" + System.lineSeparator();
            assertEquals(expectedOutput, bos.toString());
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}