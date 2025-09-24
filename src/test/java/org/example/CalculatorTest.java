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
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("add: two positive numbers should return correct sum")
    void testAdd_PositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("add: positive and negative numbers should return correct sum")
    void testAdd_PositiveAndNegativeNumbers() {
        assertEquals(-1, calculator.add(2, -3));
        assertEquals(1, calculator.add(-2, 3));
    }

    @Test
    @DisplayName("add: zero with a number should return the number")
    void testAdd_Zero() {
        assertEquals(5, calculator.add(5, 0));
        assertEquals(-5, calculator.add(-5, 0));
        assertEquals(0, calculator.add(0, 0));
    }

    @Test
    @DisplayName("add: large numbers near Integer.MAX_VALUE and Integer.MIN_VALUE")
    void testAdd_LargeNumbers() {
        assertEquals(Integer.MAX_VALUE, calculator.add(Integer.MAX_VALUE - 1, 1));
        assertEquals(Integer.MIN_VALUE, calculator.add(Integer.MIN_VALUE + 1, -1));
    }

    @Test
    @DisplayName("subtract: two positive numbers should return correct difference")
    void testSubtract_PositiveNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    @DisplayName("subtract: positive and negative numbers should return correct difference")
    void testSubtract_PositiveAndNegativeNumbers() {
        assertEquals(5, calculator.subtract(2, -3));
        assertEquals(-5, calculator.subtract(-2, 3));
    }

    @Test
    @DisplayName("subtract: zero with a number should return correct difference")
    void testSubtract_Zero() {
        assertEquals(5, calculator.subtract(5, 0));
        assertEquals(-5, calculator.subtract(-5, 0));
        assertEquals(0, calculator.subtract(0, 0));
        assertEquals(-5, calculator.subtract(0, 5));
    }

    @Test
    @DisplayName("subtract: large numbers near Integer.MAX_VALUE and Integer.MIN_VALUE")
    void testSubtract_LargeNumbers() {
        assertEquals(1, calculator.subtract(Integer.MAX_VALUE, Integer.MAX_VALUE - 1));
        assertEquals(-1, calculator.subtract(Integer.MIN_VALUE, Integer.MIN_VALUE + 1));
    }

    @Test
    @DisplayName("multiply: two positive numbers should return correct product")
    void testMultiply_PositiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    @DisplayName("multiply: positive and negative numbers should return correct product")
    void testMultiply_PositiveAndNegativeNumbers() {
        assertEquals(-6, calculator.multiply(2, -3));
        assertEquals(-6, calculator.multiply(-2, 3));
        assertEquals(6, calculator.multiply(-2, -3));
    }

    @Test
    @DisplayName("multiply: by zero should return zero")
    void testMultiply_ByZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, 5));
        assertEquals(0, calculator.multiply(0, 0));
        assertEquals(0, calculator.multiply(-5, 0));
    }

    @Test
    @DisplayName("multiply: by one should return the number")
    void testMultiply_ByOne() {
        assertEquals(5, calculator.multiply(5, 1));
        assertEquals(-5, calculator.multiply(-5, 1));
        assertEquals(5, calculator.multiply(1, 5));
    }

    @Test
    @DisplayName("divide: two positive numbers should return correct quotient")
    void testDivide_PositiveNumbers() {
        assertEquals(3, calculator.divide(6, 2));
    }

    @Test
    @DisplayName("divide: positive and negative numbers should return correct quotient")
    void testDivide_PositiveAndNegativeNumbers() {
        assertEquals(-3, calculator.divide(6, -2));
        assertEquals(-3, calculator.divide(-6, 2));
        assertEquals(3, calculator.divide(-6, -2));
    }

    @Test
    @DisplayName("divide: by one should return the number")
    void testDivide_ByOne() {
        assertEquals(5, calculator.divide(5, 1));
        assertEquals(-5, calculator.divide(-5, 1));
    }

    @Test
    @DisplayName("divide: non-exact division should truncate towards zero")
    void testDivide_Truncation() {
        assertEquals(2, calculator.divide(5, 2));
        assertEquals(-2, calculator.divide(-5, 2));
        assertEquals(-2, calculator.divide(5, -2));
    }

    @Test
    @DisplayName("divide: by zero should throw IllegalArgumentException")
    void testDivide_ByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(10, 0));
        assertEquals("Division by zero", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(0, 0));
        assertEquals("Division by zero", exception.getMessage());
    }

    @Test
    @DisplayName("divide: Integer.MIN_VALUE by -1 should throw ArithmeticException due to overflow")
    void testDivide_MinIntByMinusOne_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(Integer.MIN_VALUE, -1));
    }

    @Test
    @DisplayName("main: should print expected output to console")
    void testMain() {
        String[] args = {};
        Calculator.main(args);
        String expectedOutput = "2+3 = 5" + System.lineSeparator() + "6/2 = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}