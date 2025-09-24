package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

// Add any other necessary imports for the class under test here, for example:
// import org.example.Calculator;

public class CalculatorTest {

    @Test
    @DisplayName("Should correctly add two positive integers")
    void testAdd_positiveNumbers() {
        Calculator calculator = new Calculator();
        int expected = 5;
        int actual = calculator.add(2, 3);
        assertEquals(expected, actual, "2 + 3 should be 5");
    }
    
    @Test
    @DisplayName("Should correctly add a positive and a negative integer")
    void testAdd_positiveAndNegativeNumbers() {
        Calculator calculator = new Calculator();
        int expected = -1;
        int actual = calculator.add(2, -3);
        assertEquals(expected, actual, "2 + (-3) should be -1");
    }
    
    @Test
    @DisplayName("Should correctly add two negative integers")
    void testAdd_negativeNumbers() {
        Calculator calculator = new Calculator();
        int expected = -5;
        int actual = calculator.add(-2, -3);
        assertEquals(expected, actual, "(-2) + (-3) should be -5");
    }
    
    @Test
    @DisplayName("Should return the other number when one is zero")
    void testAdd_withZero() {
        Calculator calculator = new Calculator();
        assertEquals(5, calculator.add(5, 0), "5 + 0 should be 5");
        assertEquals(-5, calculator.add(0, -5), "0 + (-5) should be -5");
    }
    
    @Test
    @DisplayName("Should handle addition resulting in max int value without overflow (within int limits)")
    void testAdd_largeNumbersWithinLimits() {
        Calculator calculator = new Calculator();
        int a = Integer.MAX_VALUE - 1;
        int b = 1;
        long expectedLong = (long) Integer.MAX_VALUE; // Cast to long for comparison if needed, but result is int
        int actual = calculator.add(a, b);
        assertEquals(Integer.MAX_VALUE, actual, "Adding to MAX_VALUE - 1 should result in MAX_VALUE");
    }
    
    @Test
    @DisplayName("Should handle addition resulting in min int value without underflow (within int limits)")
    void testAdd_smallNumbersWithinLimits() {
        Calculator calculator = new Calculator();
        int a = Integer.MIN_VALUE + 1;
        int b = -1;
        long expectedLong = (long) Integer.MIN_VALUE; // Cast to long for comparison if needed, but result is int
        int actual = calculator.add(a, b);
        assertEquals(Integer.MIN_VALUE, actual, "Adding to MIN_VALUE + 1 should result in MIN_VALUE");
    }

    import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.DisplayName;
        import static org.junit.jupiter.api.Assertions.assertEquals;
    
        public void test_subtract() {
            // Instantiate the class under test
            org.example.Calculator calculator = new org.example.Calculator();
    
            // Test Case 1: Positive numbers
            int result1 = calculator.subtract(10, 5);
            assertEquals(5, result1, "10 - 5 should be 5");
    
            // Test Case 2: Negative numbers
            int result2 = calculator.subtract(-10, -5);
            assertEquals(-5, result2, "-10 - (-5) should be -5");
    
            // Test Case 3: Mixed numbers (positive - negative)
            int result3 = calculator.subtract(10, -5);
            assertEquals(15, result3, "10 - (-5) should be 15");
    
            // Test Case 4: Mixed numbers (negative - positive)
            int result4 = calculator.subtract(-10, 5);
            assertEquals(-15, result4, "-10 - 5 should be -15");
    
            // Test Case 5: Zero subtraction
            int result5 = calculator.subtract(5, 0);
            assertEquals(5, result5, "5 - 0 should be 5");
    
            // Test Case 6: Subtracting from zero
            int result6 = calculator.subtract(0, 5);
            assertEquals(-5, result6, "0 - 5 should be -5");
    
            // Test Case 7: Subtracting same numbers
            int result7 = calculator.subtract(7, 7);
            assertEquals(0, result7, "7 - 7 should be 0");
        }

    @Test
        @DisplayName("Should multiply two positive numbers correctly")
        public void testMultiply_positiveNumbers_shouldReturnCorrectProduct() {
            // Arrange
            Calculator calculator = new Calculator();
            int a = 5;
            int b = 3;
            int expectedProduct = 15;
    
            // Act
            int actualProduct = calculator.multiply(a, b);
    
            // Assert
            org.junit.jupiter.api.Assertions.assertEquals(expectedProduct, actualProduct, "The product of 5 and 3 should be 15");
        }
    
        @Test
        @DisplayName("Should multiply a positive number by zero correctly")
        public void testMultiply_byZero_shouldReturnZero() {
            // Arrange
            Calculator calculator = new Calculator();
            int a = 7;
            int b = 0;
            int expectedProduct = 0;
    
            // Act
            int actualProduct = calculator.multiply(a, b);
    
            // Assert
            org.junit.jupiter.api.Assertions.assertEquals(expectedProduct, actualProduct, "The product of any number and zero should be zero");
        }
    
        @Test
        @DisplayName("Should multiply two negative numbers correctly")
        public void testMultiply_negativeNumbers_shouldReturnPositiveProduct() {
            // Arrange
            Calculator calculator = new Calculator();
            int a = -4;
            int b = -2;
            int expectedProduct = 8;
    
            // Act
            int actualProduct = calculator.multiply(a, b);
    
            // Assert
            org.junit.jupiter.api.Assertions.assertEquals(expectedProduct, actualProduct, "The product of -4 and -2 should be 8");
        }
    
        @Test
        @DisplayName("Should multiply a positive and a negative number correctly")
        public void testMultiply_positiveAndNegative_shouldReturnNegativeProduct() {
            // Arrange
            Calculator calculator = new Calculator();
            int a = 6;
            int b = -3;
            int expectedProduct = -18;
    
            // Act
            int actualProduct = calculator.multiply(a, b);
    
            // Assert
            org.junit.jupiter.api.Assertions.assertEquals(expectedProduct, actualProduct, "The product of 6 and -3 should be -18");
        }

    @Test
        void test_divide() {
            // Instantiate the class under test
            org.example.Calculator calculator = new org.example.Calculator();
    
            // Test case 1: Positive numbers, successful division
            int result1 = calculator.divide(10, 2);
            org.junit.jupiter.api.Assertions.assertEquals(5, result1, "10 divided by 2 should be 5");
    
            // Test case 2: Negative numerator
            int result2 = calculator.divide(-10, 2);
            org.junit.jupiter.api.Assertions.assertEquals(-5, result2, "-10 divided by 2 should be -5");
    
            // Test case 3: Negative denominator
            int result3 = calculator.divide(10, -2);
            org.junit.jupiter.api.Assertions.assertEquals(-5, result3, "10 divided by -2 should be -5");
    
            // Test case 4: Both negative numbers
            int result4 = calculator.divide(-10, -2);
            org.junit.jupiter.api.Assertions.assertEquals(5, result4, "-10 divided by -2 should be 5");
    
            // Test case 5: Zero numerator
            int result5 = calculator.divide(0, 5);
            org.junit.jupiter.api.Assertions.assertEquals(0, result5, "0 divided by 5 should be 0");
    
            // Test case 6: Division by zero - expecting IllegalArgumentException
            org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
                calculator.divide(10, 0);
            }, "Division by zero should throw IllegalArgumentException");
    
            // Verify the message of the exception for division by zero
            IllegalArgumentException thrown = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
                calculator.divide(5, 0);
            });
            org.junit.jupiter.api.Assertions.assertEquals("Division by zero", thrown.getMessage(), "Exception message should be 'Division by zero'");
        }

}
