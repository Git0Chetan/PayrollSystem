package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FullTimeEmployeeTest {

    // Test case for calculateGrossPay with a positive monthly salary
    @Test
    void testCalculateGrossPay_PositiveSalary() {
        double monthlySalary = 5000.00;
        double taxRate = 0.15;
        FullTimeEmployee employee = new FullTimeEmployee("FT001", "John Doe", "Sales", monthlySalary, taxRate);

        double expectedGrossPay = monthlySalary;
        // month and year parameters are ignored by the current implementation
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), "Gross pay should be equal to monthly salary");
    }

    // Test case for calculateGrossPay with a zero monthly salary
    @Test
    void testCalculateGrossPay_ZeroSalary() {
        double monthlySalary = 0.00;
        double taxRate = 0.05;
        FullTimeEmployee employee = new FullTimeEmployee("FT002", "Jane Smith", "HR", monthlySalary, taxRate);

        double expectedGrossPay = monthlySalary;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(6, 2024), "Gross pay should be zero for zero monthly salary");
    }

    // Test case for calculateGrossPay with a negative monthly salary (though unlikely in real scenarios)
    @Test
    void testCalculateGrossPay_NegativeSalary() {
        double monthlySalary = -1000.00; // Represents a debt or overpayment, for example
        double taxRate = 0.10;
        FullTimeEmployee employee = new FullTimeEmployee("FT003", "Peter Jones", "Finance", monthlySalary, taxRate);

        double expectedGrossPay = monthlySalary;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(12, 2022), "Gross pay should reflect the negative monthly salary");
    }

    // Test case for getTaxRate with a positive tax rate
    @Test
    void testGetTaxRate_PositiveRate() {
        double monthlySalary = 6000.00;
        double taxRate = 0.20;
        FullTimeEmployee employee = new FullTimeEmployee("FT004", "Alice Brown", "Marketing", monthlySalary, taxRate);

        double expectedTaxRate = taxRate;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should match the initialized value");
    }

    // Test case for getTaxRate with a zero tax rate
    @Test
    void testGetTaxRate_ZeroRate() {
        double monthlySalary = 4500.00;
        double taxRate = 0.00;
        FullTimeEmployee employee = new FullTimeEmployee("FT005", "Bob White", "IT", monthlySalary, taxRate);

        double expectedTaxRate = taxRate;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should be zero when initialized as zero");
    }

    // Test case for getTaxRate with a negative tax rate (unlikely but possible for testing robustness)
    @Test
    void testGetTaxRate_NegativeRate() {
        double monthlySalary = 7000.00;
        double taxRate = -0.05; // Could represent a tax credit or rebate system
        FullTimeEmployee employee = new FullTimeEmployee("FT006", "Carol Green", "Operations", monthlySalary, taxRate);

        double expectedTaxRate = taxRate;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should reflect the negative initialized value");
    }

    // Test case for getType method
    @Test
    void testGetType() {
        double monthlySalary = 5500.00;
        double taxRate = 0.18;
        FullTimeEmployee employee = new FullTimeEmployee("FT007", "David Black", "R&D", monthlySalary, taxRate);

        String expectedType = "FullTime";
        assertEquals(expectedType, employee.getType(), "getType should return 'FullTime'");
    }
}