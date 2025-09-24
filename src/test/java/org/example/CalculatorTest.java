package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class CalculatorTest {

    private Calculator calculator;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testAddPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testAddNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
    }

    @Test
    void testAddMixedNumbers() {
        assertEquals(1, calculator.add(3, -2));
        assertEquals(-1, calculator.add(-3, 2));
    }

    @Test
    void testAddWithZero() {
        assertEquals(5, calculator.add(5, 0));
        assertEquals(-5, calculator.add(-5, 0));
        assertEquals(0, calculator.add(0, 0));
    }

    @Test
    void testAddMaxIntValue() {
        assertEquals(Integer.MAX_VALUE, calculator.add(Integer.MAX_VALUE - 1, 1));
        assertEquals(Integer.MIN_VALUE, calculator.add(Integer.MAX_VALUE, 1)); // Overflow wraps around
    }

    @Test
    void testAddMinIntValue() {
        assertEquals(Integer.MIN_VALUE, calculator.add(Integer.MIN_VALUE + 1, -1));
        assertEquals(Integer.MAX_VALUE, calculator.add(Integer.MIN_VALUE, -1)); // Overflow wraps around
    }

    @Test
    void testSubtractPositiveNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    void testSubtractNegativeNumbers() {
        assertEquals(-1, calculator.subtract(-2, -1));
    }

    @Test
    void testSubtractMixedNumbers() {
        assertEquals(5, calculator.subtract(3, -2));
        assertEquals(-5, calculator.subtract(-3, 2));
    }

    @Test
    void testSubtractWithZero() {
        assertEquals(5, calculator.subtract(5, 0));
        assertEquals(-5, calculator.subtract(-5, 0));
        assertEquals(0, calculator.subtract(0, 0));
    }

    @Test
    void testSubtractSameNumbers() {
        assertEquals(0, calculator.subtract(7, 7));
    }

    @Test
    void testMultiplyPositiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    void testMultiplyNegativeNumbers() {
        assertEquals(6, calculator.multiply(-2, -3));
    }

    @Test
    void testMultiplyMixedNumbers() {
        assertEquals(-6, calculator.multiply(2, -3));
        assertEquals(-6, calculator.multiply(-2, 3));
    }

    @Test
    void testMultiplyByZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(-5, 0));
        assertEquals(0, calculator.multiply(0, 0));
    }

    @Test
    void testMultiplyByOne() {
        assertEquals(5, calculator.multiply(5, 1));
        assertEquals(-5, calculator.multiply(-5, 1));
    }

    @Test
    void testMultiplyMaxIntValue() {
        assertEquals(2147483646, calculator.multiply(1073741823, 2)); // MAX_VALUE - 1
        assertEquals(-2147483648, calculator.multiply(1073741824, 2)); // Overflow, wraps around to MIN_VALUE
    }

    @Test
    void testDividePositiveNumbers() {
        assertEquals(3, calculator.divide(6, 2));
        assertEquals(2, calculator.divide(7, 3)); // Integer division
    }

    @Test
    void testDivideNegativeNumbers() {
        assertEquals(3, calculator.divide(-6, -2));
        assertEquals(2, calculator.divide(-7, -3)); // Integer division
    }

    @Test
    void testDivideMixedNumbers() {
        assertEquals(-3, calculator.divide(-6, 2));
        assertEquals(-3, calculator.divide(6, -2));
        assertEquals(-2, calculator.divide(-7, 3)); // Integer division
    }

    @Test
    void testDivideByOne() {
        assertEquals(5, calculator.divide(5, 1));
        assertEquals(-5, calculator.divide(-5, 1));
    }

    @Test
    void testDivideBySelf() {
        assertEquals(1, calculator.divide(5, 5));
    }

    @Test
    void testDivideByZeroThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
        assertEquals("Division by zero", thrown.getMessage());
    }

    @Test
    void testDivideMinIntValueByMinusOne() {
        // Integer.MIN_VALUE / -1 results in Integer.MIN_VALUE due to overflow
        // for int types in Java, not an ArithmeticException
        assertEquals(Integer.MIN_VALUE, calculator.divide(Integer.MIN_VALUE, -1));
    }

    @Test
    void testMainMethodOutput() {
        System.setOut(new PrintStream(outputStreamCaptor, true, StandardCharsets.UTF_8));

        Calculator.main(new String[]{});

        String expectedOutput = "2+3 = 5" + System.lineSeparator() +
                                "6/2 = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString(StandardCharsets.UTF_8));

        System.setOut(originalSystemOut); // Restore original System.out
    }
}