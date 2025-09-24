package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

// Add any other necessary imports for the class under test here, for example:
// import org.example.Calculator;

public class CalculatorTest {

    @Test
        @DisplayName("add method should correctly sum two integers including positive, negative, and zero values")
        public void test_add() {
            // Instantiate the class under test
            org.example.Calculator calculator = new org.example.Calculator();
    
            // Test case 1: Adding two positive numbers
            assertEquals(5, calculator.add(2, 3), "Adding 2 and 3 should result in 5");
    
            // Test case 2: Adding two negative numbers
            assertEquals(-5, calculator.add(-2, -3), "Adding -2 and -3 should result in -5");
    
            // Test case 3: Adding a positive and a negative number
            assertEquals(2, calculator.add(5, -3), "Adding 5 and -3 should result in 2");
            assertEquals(-2, calculator.add(-5, 3), "Adding -5 and 3 should result in -2");
    
            // Test case 4: Adding with zero
            assertEquals(0, calculator.add(0, 0), "Adding 0 and 0 should result in 0");
            assertEquals(7, calculator.add(7, 0), "Adding 7 and 0 should result in 7");
            assertEquals(-7, calculator.add(0, -7), "Adding 0 and -7 should result in -7");
    
            // Test case 5: Large numbers (within int limits)
            assertEquals(2_000_000_000, calculator.add(1_000_000_000, 1_000_000_000), "Adding two large positive numbers");
            assertEquals(-2_000_000_000, calculator.add(-1_000_000_000, -1_000_000_000), "Adding two large negative numbers");
    
            // Note: The add method directly uses `a + b`, which handles integer overflow
            // according to Java's specification (truncation). No special handling is
            // expected or tested beyond standard `int` addition behavior.
        }

    import org.example.Calculator;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    
    import static org.junit.jupiter.api.Assertions.assertEquals;
    
    public void test_subtract_variousScenarios() {
        // Arrange
        Calculator calculator = new Calculator();
    
        // Act & Assert
    
        // Test case 1: Positive - Positive = Positive
        assertEquals(5, calculator.subtract(10, 5), "10 - 5 should be 5");
    
        // Test case 2: Positive - Positive = Negative
        assertEquals(-5, calculator.subtract(5, 10), "5 - 10 should be -5");
    
        // Test case 3: Positive - Zero
        assertEquals(10, calculator.subtract(10, 0), "10 - 0 should be 10");
    
        // Test case 4: Zero - Positive
        assertEquals(-10, calculator.subtract(0, 10), "0 - 10 should be -10");
    
        // Test case 5: Zero - Zero
        assertEquals(0, calculator.subtract(0, 0), "0 - 0 should be 0");
    
        // Test case 6: Positive - Negative (e.g., 10 - (-5) = 15)
        assertEquals(15, calculator.subtract(10, -5), "10 - (-5) should be 15");
    
        // Test case 7: Negative - Positive (e.g., -10 - 5 = -15)
        assertEquals(-15, calculator.subtract(-10, 5), "-10 - 5 should be -15");
    
        // Test case 8: Negative - Negative (e.g., -10 - (-5) = -5)
        assertEquals(-5, calculator.subtract(-10, -5), "-10 - (-5) should be -5");
    
        // Test case 9: Negative - Zero
        assertEquals(-10, calculator.subtract(-10, 0), "-10 - 0 should be -10");
    
        // Test case 10: Edge case with Integer.MAX_VALUE and Integer.MIN_VALUE (overflow handling)
        // Integer.MAX_VALUE - Integer.MIN_VALUE = 2147483647 - (-2147483648) = 2147483647 + 2147483648 = 4294967295
        // In Java's 2's complement int arithmetic, this wraps around to -1.
        assertEquals(-1, calculator.subtract(Integer.MAX_VALUE, Integer.MIN_VALUE),
                "Integer.MAX_VALUE - Integer.MIN_VALUE should correctly wrap to -1");
    
        // Test case 11: Another edge case with Integer.MIN_VALUE and Integer.MAX_VALUE (overflow handling)
        // Integer.MIN_VALUE - Integer.MAX_VALUE = -2147483648 - 2147483647 = -4294967295
        // In Java's 2's complement int arithmetic, this wraps around to 1.
        assertEquals(1, calculator.subtract(Integer.MIN_VALUE, Integer.MAX_VALUE),
                "Integer.MIN_VALUE - Integer.MAX_VALUE should correctly wrap to 1");
    }

    @Test
        @DisplayName("Should correctly multiply two integers for various positive, negative, and zero inputs")
        public void testMultiply() {
            org.example.Calculator calculator = new org.example.Calculator();
    
            // Test case 1: Positive numbers
            assertEquals(10, calculator.multiply(2, 5), "2 * 5 should be 10");
    
            // Test case 2: Negative numbers
            assertEquals(15, calculator.multiply(-3, -5), "-3 * -5 should be 15");
    
            // Test case 3: Positive and negative numbers
            assertEquals(-12, calculator.multiply(4, -3), "4 * -3 should be -12");
            assertEquals(-12, calculator.multiply(-4, 3), "-4 * 3 should be -12");
    
            // Test case 4: Multiplication by zero
            assertEquals(0, calculator.multiply(0, 7), "0 * 7 should be 0");
            assertEquals(0, calculator.multiply(7, 0), "7 * 0 should be 0");
            assertEquals(0, calculator.multiply(0, -5), "0 * -5 should be 0");
            assertEquals(0, calculator.multiply(-5, 0), "-5 * 0 should be 0");
    
            // Test case 5: Multiplication by one
            assertEquals(8, calculator.multiply(8, 1), "8 * 1 should be 8");
            assertEquals(-9, calculator.multiply(-9, 1), "-9 * 1 should be -9");
            assertEquals(1, calculator.multiply(1, 1), "1 * 1 should be 1");
    
            // Test case 6: Large numbers (within int limits, checking for no unexpected overflow behavior)
            // Note: Java's int multiplication handles overflow by wrapping around,
            // which is standard behavior for primitive types. We test for the expected wrapped result.
            assertEquals(Integer.MAX_VALUE, calculator.multiply(Integer.MAX_VALUE, 1), "Integer.MAX_VALUE * 1");
            assertEquals(Integer.MIN_VALUE, calculator.multiply(Integer.MIN_VALUE, 1), "Integer.MIN_VALUE * 1");
            
            // Example of overflow result for verification (this is the expected behavior for `int` in Java)
            assertEquals(-2, calculator.multiply(Integer.MAX_VALUE, 2), "Integer.MAX_VALUE * 2 should overflow to -2");
        }

    @Test
        @DisplayName("Should throw IllegalArgumentException when dividing by zero")
        public void testDivide_byZero_throwsIllegalArgumentException() {
            Calculator calculator = new Calculator();
            IllegalArgumentException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(10, 0),
                "Expected divide(10, 0) to throw IllegalArgumentException, but it didn't"
            );
            org.junit.jupiter.api.Assertions.assertEquals("Division by zero", thrown.getMessage(), "Incorrect exception message");
        }

}
