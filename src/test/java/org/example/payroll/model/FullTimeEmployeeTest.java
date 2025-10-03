package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FullTimeEmployeeTest {

    // Test case for calculating gross pay for a full-time employee
    @Test
    void testCalculateGrossPay() {
        double monthlySalary = 5000.00;
        double taxRate = 0.15;
        FullTimeEmployee employee = new FullTimeEmployee("FT001", "John Doe", "Sales", monthlySalary, taxRate);

        // For FullTimeEmployee, month and year parameters are ignored, always returns monthlySalary
        assertEquals(monthlySalary, employee.calculateGrossPay(1, 2023), "Gross pay should be the monthly salary.");
        assertEquals(monthlySalary, employee.calculateGrossPay(12, 2024), "Gross pay should be consistent regardless of month/year.");
    }

    // Test case for a zero monthly salary
    @Test
    void testCalculateGrossPay_ZeroSalary() {
        double monthlySalary = 0.00;
        double taxRate = 0.10;
        FullTimeEmployee employee = new FullTimeEmployee("FT002", "Jane Smith", "HR", monthlySalary, taxRate);

        assertEquals(0.00, employee.calculateGrossPay(5, 2023), "Gross pay for zero monthly salary should be zero.");
    }

    // Test case for positive monthly salary
    @Test
    void testCalculateGrossPay_PositiveSalary() {
        double monthlySalary = 7500.50;
        double taxRate = 0.20;
        FullTimeEmployee employee = new FullTimeEmployee("FT003", "Alice Brown", "Marketing", monthlySalary, taxRate);

        assertEquals(monthlySalary, employee.calculateGrossPay(3, 2023), "Gross pay should match the positive monthly salary.");
    }

    // Test case for retrieving the tax rate
    @Test
    void testGetTaxRate() {
        double monthlySalary = 6000.00;
        double taxRate = 0.18; // Specific tax rate for this test
        FullTimeEmployee employee = new FullTimeEmployee("FT004", "Bob White", "Engineering", monthlySalary, taxRate);

        assertEquals(taxRate, employee.getTaxRate(), "The retrieved tax rate should match the one provided during construction.");
    }

    // Test case for retrieving a zero tax rate
    @Test
    void testGetTaxRate_ZeroTaxRate() {
        double monthlySalary = 4000.00;
        double taxRate = 0.00;
        FullTimeEmployee employee = new FullTimeEmployee("FT005", "Charlie Green", "Finance", monthlySalary, taxRate);

        assertEquals(0.00, employee.getTaxRate(), "The retrieved tax rate should be zero if initialized as such.");
    }

    // Test case for retrieving a non-zero tax rate
    @Test
    void testGetTaxRate_NonZeroTaxRate() {
        double monthlySalary = 8000.00;
        double taxRate = 0.25;
        FullTimeEmployee employee = new FullTimeEmployee("FT006", "Diana Prince", "Management", monthlySalary, taxRate);

        assertEquals(0.25, employee.getTaxRate(), "The retrieved tax rate should be 0.25.");
    }

    // Test case for retrieving the employee type
    @Test
    void testGetType() {
        FullTimeEmployee employee = new FullTimeEmployee("FT007", "Eve Black", "Operations", 4500.00, 0.12);

        assertEquals("FullTime", employee.getType(), "The employee type should be 'FullTime'.");
        assertNotNull(employee.getType(), "The employee type should not be null.");
    }
}