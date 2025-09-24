package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

// Add any other necessary imports for the class under test here, for example:
// import org.example.Calculator;

public class CalculatorTest {

    @Test
        @DisplayName("Adds two positive integers correctly")
        public void testAdd_twoPositiveNumbers() {
            // Instantiate the class under test
            org.example.Calculator calculator = new org.example.Calculator();
    
            // Define input values
            int a = 5;
            int b = 3;
    
            // Define the expected result
            int expected = 8;
    
            // Invoke the method under test
            int actual = calculator.add(a, b);
    
            // Assert that the actual result matches the expected result
            org.junit.jupiter.api.Assertions.assertEquals(expected, actual, "The sum of " + a + " and " + b + " should be " + expected);
        }

    @Test
        @DisplayName("Should correctly subtract two integers")
        void test_subtract() {
            // Arrange
            Calculator calculator = new Calculator();
    
            // Test cases
            // 1. Positive numbers
            assertEquals(2, calculator.subtract(5, 3), "5 - 3 should be 2");
    
            // 2. Negative numbers
            assertEquals(-2, calculator.subtract(-5, -3), "-5 - -3 should be -2");
    
            // 3. Mixed signs (positive - negative)
            assertEquals(8, calculator.subtract(5, -3), "5 - -3 should be 8");
    
            // 4. Mixed signs (negative - positive)
            assertEquals(-8, calculator.subtract(-5, 3), "-5 - 3 should be -8");
    
            // 5. Subtracting zero
            assertEquals(10, calculator.subtract(10, 0), "10 - 0 should be 10");
            assertEquals(-10, calculator.subtract(0, 10), "0 - 10 should be -10");
    
            // 6. Subtracting same number
            assertEquals(0, calculator.subtract(7, 7), "7 - 7 should be 0");
        }

    @Test
        @DisplayName("Should correctly multiply two positive numbers")
        void testMultiply_positiveNumbers() {
            Calculator calculator = new Calculator();
            int result = calculator.multiply(5, 3);
            assertEquals(15, result, "5 * 3 should be 15");
        }
    
        @Test
        @DisplayName("Should correctly multiply a positive and a negative number")
        void testMultiply_positiveAndNegativeNumber() {
            Calculator calculator = new Calculator();
            int result = calculator.multiply(5, -3);
            assertEquals(-15, result, "5 * -3 should be -15");
        }
    
        @Test
        @DisplayName("Should correctly multiply two negative numbers")
        void testMultiply_negativeNumbers() {
            Calculator calculator = new Calculator();
            int result = calculator.multiply(-5, -3);
            assertEquals(15, result, "-5 * -3 should be 15");
        }
    
        @Test
        @DisplayName("Should return zero when multiplying by zero")
        void testMultiply_byZero() {
            Calculator calculator = new Calculator();
            int result = calculator.multiply(10, 0);
            assertEquals(0, result, "10 * 0 should be 0");
        }
    
        @Test
        @DisplayName("Should return zero when multiplying zero by a number")
        void testMultiply_zeroByNumber() {
            Calculator calculator = new Calculator();
            int result = calculator.multiply(0, 7);
            assertEquals(0, result, "0 * 7 should be 0");
        }
    
        @Test
        @DisplayName("Should handle multiplication with large positive numbers")
        void testMultiply_largePositiveNumbers() {
            Calculator calculator = new Calculator();
            int result = calculator.multiply(Integer.MAX_VALUE / 2, 2);
            // Note: For int multiplication, ensure no overflow for the test case itself.
            // Integer.MAX_VALUE / 2 * 2 will not overflow, but Integer.MAX_VALUE * 2 would.
            assertEquals(Integer.MAX_VALUE - 1, result, "Large positive multiplication"); // (2147483647 / 2) * 2 = 1073741823 * 2 = 2147483646
        }

    @Test
        @DisplayName("Should throw IllegalArgumentException when dividing by zero")
        public void test_divide_byZero_throwsIllegalArgumentException() {
            // Instantiate the class under test
            org.example.Calculator calculator = new org.example.Calculator();
    
            // Invoke the method and assert that an IllegalArgumentException is thrown
            // and verify its message.
            Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(10, 0),
                "Dividing by zero should throw an IllegalArgumentException"
            );
    
            org.junit.jupiter.api.Assertions.assertEquals(
                "Division by zero",
                exception.getMessage(),
                "The exception message should be 'Division by zero'"
            );
        }

}
