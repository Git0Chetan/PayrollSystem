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
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        // Redirect System.out to capture console output for main method tests
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        // Restore System.out to its original stream
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should add two positive numbers correctly")
    void testAddPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(100, calculator.add(70, 30));
    }

    @Test
    @DisplayName("Should add positive and negative numbers correctly")
    void testAddPositiveAndNegativeNumber() {
        assertEquals(-1, calculator.add(2, -3));
        assertEquals(1, calculator.add(-2, 3));
    }

    @Test
    @DisplayName("Should add two negative numbers correctly")
    void testAddNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
        assertEquals(-100, calculator.add(-70, -30));
    }

    @Test
    @DisplayName("Should add with zero correctly")
    void testAddWithZero() {
        assertEquals(5, calculator.add(5, 0));
        assertEquals(-5, calculator.add(-5, 0));
        assertEquals(0, calculator.add(0, 0));
    }

    @Test
    @DisplayName("Should subtract two positive numbers correctly")
    void testSubtractPositiveNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
        assertEquals(-1, calculator.subtract(2, 3));
        assertEquals(70, calculator.subtract(100, 30));
    }

    @Test
    @DisplayName("Should subtract positive and negative numbers correctly")
    void testSubtractPositiveAndNegativeNumber() {
        assertEquals(5, calculator.subtract(2, -3)); // 2 - (-3) = 5
        assertEquals(-5, calculator.subtract(-2, 3)); // -2 - 3 = -5
    }

    @Test
    @DisplayName("Should subtract two negative numbers correctly")
    void testSubtractNegativeNumbers() {
        assertEquals(1, calculator.subtract(-2, -3)); // -2 - (-3) = 1
        assertEquals(-1, calculator.subtract(-3, -2)); // -3 - (-2) = -1
    }

    @Test
    @DisplayName("Should subtract with zero correctly")
    void testSubtractWithZero() {
        assertEquals(5, calculator.subtract(5, 0));
        assertEquals(-5, calculator.subtract(-5, 0));
        assertEquals(0, calculator.subtract(0, 0));
    }

    @Test
    @DisplayName("Should multiply two positive numbers correctly")
    void testMultiplyPositiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
        assertEquals(100, calculator.multiply(10, 10));
    }

    @Test
    @DisplayName("Should multiply positive and negative numbers correctly")
    void testMultiplyPositiveAndNegativeNumber() {
        assertEquals(-6, calculator.multiply(2, -3));
        assertEquals(-6, calculator.multiply(-2, 3));
    }

    @Test
    @DisplayName("Should multiply two negative numbers correctly")
    void testMultiplyNegativeNumbers() {
        assertEquals(6, calculator.multiply(-2, -3));
        assertEquals(100, calculator.multiply(-10, -10));
    }

    @Test
    @DisplayName("Should multiply by zero correctly")
    void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(-5, 0));
        assertEquals(0, calculator.multiply(0, 0));
    }

    @Test
    @DisplayName("Should multiply by one correctly")
    void testMultiplyByOne() {
        assertEquals(5, calculator.multiply(5, 1));
        assertEquals(-5, calculator.multiply(-5, 1));
    }

    @Test
    @DisplayName("Should divide two positive numbers correctly with integer division")
    void testDividePositiveNumbers() {
        assertEquals(2, calculator.divide(6, 3));
        assertEquals(1, calculator.divide(5, 3)); // Integer division result
    }

    @Test
    @DisplayName("Should divide positive by negative number correctly with integer division")
    void testDividePositiveByNegative() {
        assertEquals(-2, calculator.divide(6, -3));
        assertEquals(-1, calculator.divide(5, -3)); // Integer division result
    }

    @Test
    @DisplayName("Should divide negative by positive number correctly with integer division")
    void testDivideNegativeByPositive() {
        assertEquals(-2, calculator.divide(-6, 3));
        assertEquals(-1, calculator.divide(-5, 3)); // Integer division result
    }

    @Test
    @DisplayName("Should divide negative by negative number correctly with integer division")
    void testDivideNegativeByNegative() {
        assertEquals(2, calculator.divide(-6, -3));
        assertEquals(1, calculator.divide(-5, -3)); // Integer division result
    }

    @Test
    @DisplayName("Should handle division where numerator is zero")
    void testDivideZeroByNumber() {
        assertEquals(0, calculator.divide(0, 5));
        assertEquals(0, calculator.divide(0, -5));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when dividing by zero")
    void testDivideByZeroThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        }, "Division by zero should throw IllegalArgumentException");
        assertEquals("Division by zero", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(0, 0);
        }, "Division by zero should throw IllegalArgumentException even if numerator is zero");
        assertEquals("Division by zero", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw ArithmeticException for Integer.MIN_VALUE divided by -1 due to overflow")
    void testDivideIntegerMinValueByNegativeOneThrowsArithmeticException() {
        // In Java, Integer.MIN_VALUE / -1 results in an overflow that throws ArithmeticException.
        // The Calculator method does not explicitly check for this, so Java's default behavior is expected.
        ArithmeticException thrown = assertThrows(ArithmeticException.class, () -> {
            calculator.divide(Integer.MIN_VALUE, -1);
        }, "Integer.MIN_VALUE / -1 should throw ArithmeticException due to overflow");
        assertEquals("/ by zero", thrown.getMessage()); // This is the specific message from JVM for this overflow case
    }

    @Test
    @DisplayName("Should print correct output to console when main method is executed")
    void testMainMethodOutput() {
        // The setUp method already redirects System.out to outContent
        Calculator.main(new String[]{}); // Execute the static main method

        // Verify the captured output
        String expectedOutput = "2+3 = 5" + System.lineSeparator() + "6/2 = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}