package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // region: Add tests
    @Test
    void testAdd_positiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testAdd_negativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
    }

    @Test
    void testAdd_mixedNumbers() {
        assertEquals(1, calculator.add(3, -2));
        assertEquals(-1, calculator.add(-3, 2));
    }

    @Test
    void testAdd_withZero() {
        assertEquals(5, calculator.add(5, 0));
        assertEquals(-5, calculator.add(0, -5));
        assertEquals(0, calculator.add(0, 0));
    }
    // endregion

    // region: Subtract tests
    @Test
    void testSubtract_positiveNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    void testSubtract_negativeNumbers() {
        assertEquals(1, calculator.subtract(-2, -3)); // -2 - (-3) = 1
    }

    @Test
    void testSubtract_mixedNumbers() {
        assertEquals(5, calculator.subtract(3, -2)); // 3 - (-2) = 5
        assertEquals(-5, calculator.subtract(-3, 2)); // -3 - 2 = -5
    }

    @Test
    void testSubtract_withZero() {
        assertEquals(5, calculator.subtract(5, 0));
        assertEquals(-5, calculator.subtract(0, 5));
        assertEquals(0, calculator.subtract(0, 0));
    }
    // endregion

    // region: Multiply tests
    @Test
    void testMultiply_positiveNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    void testMultiply_negativeNumbers() {
        assertEquals(6, calculator.multiply(-2, -3));
    }

    @Test
    void testMultiply_mixedNumbers() {
        assertEquals(-6, calculator.multiply(2, -3));
        assertEquals(-6, calculator.multiply(-2, 3));
    }

    @Test
    void testMultiply_withZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, -5));
        assertEquals(0, calculator.multiply(0, 0));
    }
    // endregion

    // region: Divide tests
    @Test
    void testDivide_positiveNumbers() {
        assertEquals(3, calculator.divide(6, 2));
    }

    @Test
    void testDivide_negativeNumbers() {
        assertEquals(3, calculator.divide(-6, -2));
    }

    @Test
    void testDivide_mixedNumbers() {
        assertEquals(-3, calculator.divide(-6, 2));
        assertEquals(-3, calculator.divide(6, -2));
    }

    @Test
    void testDivide_integerDivision() {
        assertEquals(3, calculator.divide(7, 2)); // 7 / 2 = 3 (integer division)
        assertEquals(-3, calculator.divide(-7, 2)); // -7 / 2 = -3 (integer division)
    }

    @Test
    void testDivide_byZero_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(5, 0);
        });
        assertEquals("Division by zero", thrown.getMessage());
    }
    // endregion

    // region: Main method tests
    @Test
    void testMainMethod() {
        // Capture standard output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            Calculator.main(new String[]{});

            String expectedOutput = "2+3 = 5" + System.lineSeparator() +
                                    "6/2 = 3" + System.lineSeparator();

            assertEquals(expectedOutput, outContent.toString());
        } finally {
            // Restore original standard output
            System.setOut(originalOut);
        }
    }
    // endregion
}