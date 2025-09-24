package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CalculatorTest {

    private Calculator calculator;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Should correctly add two positive numbers")
    void testAddPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("Should correctly add a positive and a negative number")
    void testAddPositiveAndNegativeNumbers() {
        assertEquals(-1, calculator.add(2, -3));
        assertEquals(1, calculator.add(-2, 3));
    }

    @Test
    @DisplayName("Should correctly add two negative numbers")
    void testAddNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
    }

    @Test
    @DisplayName("Should correctly add with zero")
    void testAddWithZero() {
        assertEquals(5, calculator.add(5, 0));
        assertEquals(-5, calculator.add(-5, 0));
        assertEquals(0, calculator.add(0, 0));
    }

    @Test
    @DisplayName("Should correctly subtract two positive numbers")
    void testSubtractPositiveNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
        assertEquals(-1, calculator.subtract(2, 3));
    }

    @Test
    @DisplayName("Should correctly subtract a positive and a negative number")
    void testSubtractPositiveAndNegativeNumbers() {
        assertEquals(5, calculator.subtract(2, -3));
        assertEquals(-5, calculator.subtract(-2, 3));
    }

    @Test
    @DisplayName("Should correctly subtract two negative numbers")
    void testSubtractNegativeNumbers() {
        assertEquals(1, calculator.subtract(-2, -3));
        assertEquals(-1, calculator.subtract(-3, -2));
    }

    @Test
    @DisplayName("Should correctly subtract with zero")
    void testSubtractWithZero() {
        assertEquals(5, calculator.subtract(5, 0));
        assertEquals(-5, calculator.subtract(-5, 0));
        assertEquals(0, calculator.subtract(0, 0));
    }

    @Test
    @DisplayName("Should correctly multiply two positive numbers")
    void testMultiplyPositiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    @DisplayName("Should correctly multiply a positive and a negative number")
    void testMultiplyPositiveAndNegativeNumbers() {
        assertEquals(-6, calculator.multiply(2, -3));
        assertEquals(-6, calculator.multiply(-2, 3));
    }

    @Test
    @DisplayName("Should correctly multiply two negative numbers")
    void testMultiplyNegativeNumbers() {
        assertEquals(6, calculator.multiply(-2, -3));
    }

    @Test
    @DisplayName("Should correctly multiply by zero")
    void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, 5));
        assertEquals(0, calculator.multiply(-5, 0));
        assertEquals(0, calculator.multiply(0, 0));
    }

    @Test
    @DisplayName("Should correctly multiply by one")
    void testMultiplyByOne() {
        assertEquals(5, calculator.multiply(5, 1));
        assertEquals(-5, calculator.multiply(-5, 1));
    }

    @Test
    @DisplayName("Should correctly divide two positive numbers")
    void testDividePositiveNumbers() {
        assertEquals(3, calculator.divide(6, 2));
    }

    @Test
    @DisplayName("Should correctly divide a positive by a negative number")
    void testDividePositiveByNegative() {
        assertEquals(-3, calculator.divide(6, -2));
    }

    @Test
    @DisplayName("Should correctly divide a negative by a positive number")
    void testDivideNegativeByPositive() {
        assertEquals(-3, calculator.divide(-6, 2));
    }

    @Test
    @DisplayName("Should correctly divide two negative numbers")
    void testDivideNegativeNumbers() {
        assertEquals(3, calculator.divide(-6, -2));
    }

    @Test
    @DisplayName("Should correctly handle integer division (truncation)")
    void testDivideIntegerTruncation() {
        assertEquals(2, calculator.divide(7, 3));
        assertEquals(-2, calculator.divide(-7, 3));
        assertEquals(-2, calculator.divide(7, -3));
    }

    @Test
    @DisplayName("Should correctly divide zero by a non-zero number")
    void testDivideZeroByNonZero() {
        assertEquals(0, calculator.divide(0, 5));
        assertEquals(0, calculator.divide(0, -5));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when dividing by zero")
    void testDivideByZeroThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
        assertEquals("Division by zero", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(-10, 0);
        });
        assertEquals("Division by zero", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(0, 0);
        });
        assertEquals("Division by zero", thrown.getMessage());
    }

    @Test
    @DisplayName("Should print correct output for main method")
    void testMainMethodOutput() {
        Calculator.main(new String[]{});
        String expectedOutput = "2+3 = 5" + System.lineSeparator() + "6/2 = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}