package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// The test class for org.example.PaymentProcessor.FullTimeEmployee.
// The class name adheres to Java naming conventions while clearly indicating the tested inner class.
@DisplayName("PaymentProcessor.FullTimeEmployee Methods Test Suite")
class PaymentProcessorFullTimeEmployeeTest {

    // Test cases for calculateGross method

    // Test case for a standard positive monthly salary.
    @Test
    @DisplayName("calculateGross: Should return the monthly salary for a standard positive salary.")
    void calculateGross_standardPositiveSalary_returnsMonthlySalary() {
        // Given
        double monthlySalary = 5000.0;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT001", "John Doe", "Sales", monthlySalary, 0.15);

        // When
        // The month and year parameters are ignored by FullTimeEmployee's calculateGross implementation.
        double gross = employee.calculateGross(1, 2023);

        // Then
        assertEquals(monthlySalary, gross, "Gross salary should be equal to the initialized monthly salary.");
    }

    // Test case for zero monthly salary.
    @Test
    @DisplayName("calculateGross: Should return 0.0 for an employee with zero monthly salary.")
    void calculateGross_zeroSalary_returnsZero() {
        // Given
        double monthlySalary = 0.0;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT002", "Jane Smith", "HR", monthlySalary, 0.10);

        // When
        double gross = employee.calculateGross(7, 2024);

        // Then
        assertEquals(0.0, gross, "Gross salary should be 0.0 when monthly salary is 0.0.");
    }

    // Test case for a negative monthly salary (edge case, though unrealistic for real salaries).
    @Test
    @DisplayName("calculateGross: Should return the negative monthly salary if initialized with a negative value.")
    void calculateGross_negativeSalary_returnsNegative() {
        // Given
        double monthlySalary = -1000.0;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT003", "Alice Wonderland", "Finance", monthlySalary, 0.20);

        // When
        double gross = employee.calculateGross(12, 2023);

        // Then
        assertEquals(-1000.0, gross, "Gross salary should reflect the negative monthly salary.");
    }

    // Test case to ensure month and year parameters are ignored by the calculation.
    @Test
    @DisplayName("calculateGross: Month and year parameters should be ignored by the calculation.")
    void calculateGross_parametersIgnored_alwaysReturnsMonthlySalary() {
        // Given
        double monthlySalary = 7500.0;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT004", "Bob The Builder", "IT", monthlySalary, 0.25);

        // When & Then
        assertEquals(monthlySalary, employee.calculateGross(1, 2023), "Gross should be consistent regardless of valid month/year.");
        assertEquals(monthlySalary, employee.calculateGross(12, 2024), "Gross should be consistent regardless of valid month/year.");
        assertEquals(monthlySalary, employee.calculateGross(99, -100), "Gross should be consistent regardless of invalid month/year.");
    }

    // Test cases for getTaxRate method

    // Test case for a standard positive tax rate.
    @Test
    @DisplayName("getTaxRate: Should return the initialized positive tax rate.")
    void getTaxRate_positiveRate_returnsCorrectRate() {
        // Given
        double expectedTaxRate = 0.18;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT005", "Charlie Chaplin", "Marketing", 4000.0, expectedTaxRate);

        // When
        double actualTaxRate = employee.getTaxRate();

        // Then
        assertEquals(expectedTaxRate, actualTaxRate, "The returned tax rate should match the initialized value.");
    }

    // Test case for zero tax rate.
    @Test
    @DisplayName("getTaxRate: Should return 0.0 for a zero tax rate.")
    void getTaxRate_zeroRate_returnsZero() {
        // Given
        double expectedTaxRate = 0.0;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT006", "David Copperfield", "Support", 3500.0, expectedTaxRate);

        // When
        double actualTaxRate = employee.getTaxRate();

        // Then
        assertEquals(0.0, actualTaxRate, "The tax rate should be 0.0 when initialized as 0.0.");
    }

    // Test case for a 100% tax rate (1.0).
    @Test
    @DisplayName("getTaxRate: Should return 1.0 for a 100% tax rate.")
    void getTaxRate_oneHundredPercentRate_returnsOne() {
        // Given
        double expectedTaxRate = 1.0;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT007", "Eve Green", "Logistics", 5000.0, expectedTaxRate);

        // When
        double actualTaxRate = employee.getTaxRate();

        // Then
        assertEquals(1.0, actualTaxRate, "The tax rate should be 1.0 when initialized as 1.0.");
    }

    // Test case for a negative tax rate (edge case, though unrealistic).
    @Test
    @DisplayName("getTaxRate: Should return the negative tax rate if initialized with a negative value.")
    void getTaxRate_negativeRate_returnsNegative() {
        // Given
        double expectedTaxRate = -0.05;
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT008", "Frankenstein", "Operations", 4500.0, expectedTaxRate);

        // When
        double actualTaxRate = employee.getTaxRate();

        // Then
        assertEquals(expectedTaxRate, actualTaxRate, "The tax rate should reflect the negative initialized value.");
    }

    // Test cases for getType method

    // Test case to verify the employee type is "FullTime".
    @Test
    @DisplayName("getType: Should always return the string 'FullTime'.")
    void getType_returnsFullTime() {
        // Given
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT009", "Grace Hopper", "R&D", 8000.0, 0.30);

        // When
        String type = employee.getType();

        // Then
        assertEquals("FullTime", type, "The employee type string must be 'FullTime'.");
    }

    // Test case to ensure getType returns "FullTime" regardless of salary or tax rate.
    @Test
    @DisplayName("getType: Should return 'FullTime' even for employees with unusual salary or tax rate values.")
    void getType_withVariousEmployeeStates_returnsFullTime() {
        // Given
        PaymentProcessor.FullTimeEmployee emp1 = new PaymentProcessor.FullTimeEmployee("FT010", "Heidi Klum", "Design", 0.0, 0.0);
        PaymentProcessor.FullTimeEmployee emp2 = new PaymentProcessor.FullTimeEmployee("FT011", "Ivan Drago", "Security", -200.0, -0.1);

        // When & Then
        assertEquals("FullTime", emp1.getType(), "Type should be 'FullTime' for zero salary and tax.");
        assertEquals("FullTime", emp2.getType(), "Type should be 'FullTime' for negative salary and tax.");
    }
}