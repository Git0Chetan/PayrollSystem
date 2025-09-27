package org.example.payroll.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FullTimeEmployeeTest {

    // Test cases for calculateGrossPay method

    // Test case for a standard positive monthly salary.
    @Test
    @DisplayName("calculateGrossPay returns the correct positive monthly salary")
    void calculateGrossPay_PositiveMonthlySalary_ReturnsMonthlySalary() {
        double monthlySalary = 5000.00;
        double taxRate = 0.15;
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("FT001", "John Doe", "Sales", monthlySalary, taxRate);

        double expectedGrossPay = monthlySalary;
        // The month and year parameters are ignored by the current implementation
        double actualGrossPay = fullTimeEmployee.calculateGrossPay(1, 2023);
        assertEquals(expectedGrossPay, actualGrossPay, "Gross pay should exactly match the monthly salary for a full-time employee.");
    }

    // Test case for zero monthly salary.
    @Test
    @DisplayName("calculateGrossPay returns zero for zero monthly salary")
    void calculateGrossPay_ZeroMonthlySalary_ReturnsZero() {
        double monthlySalary = 0.00;
        double taxRate = 0.05;
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("FT002", "Jane Smith", "HR", monthlySalary, taxRate);

        double expectedGrossPay = 0.00;
        double actualGrossPay = fullTimeEmployee.calculateGrossPay(6, 2023);
        assertEquals(expectedGrossPay, actualGrossPay, "Gross pay should be zero when the monthly salary is zero.");
    }

    // Test case for a negative monthly salary (edge case).
    @Test
    @DisplayName("calculateGrossPay returns negative for negative monthly salary")
    void calculateGrossPay_NegativeMonthlySalary_ReturnsNegativeSalary() {
        double monthlySalary = -1000.00; // Represents a potential deduction or advance in some systems
        double taxRate = 0.10;
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("FT003", "Peter Jones", "Marketing", monthlySalary, taxRate);

        double expectedGrossPay = monthlySalary;
        double actualGrossPay = fullTimeEmployee.calculateGrossPay(12, 2023);
        assertEquals(expectedGrossPay, actualGrossPay, "Gross pay should reflect the negative monthly salary if provided.");
    }

    // Test cases for getTaxRate method

    // Test case for a standard positive tax rate.
    @Test
    @DisplayName("getTaxRate returns the correct positive tax rate")
    void getTaxRate_PositiveRate_ReturnsCorrectRate() {
        double monthlySalary = 4500.00;
        double taxRate = 0.20;
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("FT004", "Alice Brown", "Finance", monthlySalary, taxRate);

        double expectedTaxRate = taxRate;
        double actualTaxRate = fullTimeEmployee.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate, "Tax rate should be correct for a positive value.");
    }

    // Test case for zero tax rate.
    @Test
    @DisplayName("getTaxRate returns zero for zero tax rate")
    void getTaxRate_ZeroRate_ReturnsZero() {
        double monthlySalary = 3000.00;
        double taxRate = 0.00;
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("FT005", "Bob White", "IT", monthlySalary, taxRate);

        double expectedTaxRate = 0.00;
        double actualTaxRate = fullTimeEmployee.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate, "Tax rate should be zero when initialized as zero.");
    }

    // Test case for a negative tax rate (edge case).
    @Test
    @DisplayName("getTaxRate returns negative for negative tax rate")
    void getTaxRate_NegativeRate_ReturnsNegativeRate() {
        double monthlySalary = 6000.00;
        double taxRate = -0.05; // Represents a tax credit or rebate in some systems
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("FT006", "Charlie Green", "HR", monthlySalary, taxRate);

        double expectedTaxRate = taxRate;
        double actualTaxRate = fullTimeEmployee.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate, "Tax rate should be negative if initialized as negative.");
    }

    // Test cases for getType method

    // Test case for verifying the employee type string.
    @Test
    @DisplayName("getType returns 'FullTime' as expected")
    void getType_ReturnsFullTime() {
        double monthlySalary = 4000.00;
        double taxRate = 0.12;
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("FT007", "Diana Prince", "Security", monthlySalary, taxRate);

        String expectedType = "FullTime";
        String actualType = fullTimeEmployee.getType();
        assertEquals(expectedType, actualType, "The employee type should be 'FullTime'.");
    }
}