package org.example.payroll.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FullTimeEmployeeTest {

    // Test case for calculateGrossPay with a standard monthly salary
    @Test
    @DisplayName("calculateGrossPay should return the monthly salary for full-time employees")
    void calculateGrossPay_standardSalary_returnsMonthlySalary() {
        double monthlySalary = 5000.00;
        FullTimeEmployee employee = new FullTimeEmployee("FTE001", "John Doe", "Sales", monthlySalary, 0.15);
        
        // Month and year are ignored by this specific implementation
        double grossPay = employee.calculateGrossPay(1, 2023);
        
        assertEquals(monthlySalary, grossPay, "Gross pay should be equal to the monthly salary.");
    }

    // Test case for calculateGrossPay with a zero monthly salary
    @Test
    @DisplayName("calculateGrossPay should return zero if monthly salary is zero")
    void calculateGrossPay_zeroSalary_returnsZero() {
        double monthlySalary = 0.00;
        FullTimeEmployee employee = new FullTimeEmployee("FTE002", "Jane Smith", "HR", monthlySalary, 0.10);
        
        double grossPay = employee.calculateGrossPay(7, 2024);
        
        assertEquals(0.00, grossPay, "Gross pay should be zero for a zero monthly salary.");
    }

    // Test case for calculateGrossPay with a negative monthly salary (edge case, though unlikely in real-world)
    @Test
    @DisplayName("calculateGrossPay should return the negative monthly salary if monthly salary is negative")
    void calculateGrossPay_negativeSalary_returnsNegativeValue() {
        double monthlySalary = -1000.00; // Represents a potential error state or unique scenario
        FullTimeEmployee employee = new FullTimeEmployee("FTE003", "Bob Johnson", "Finance", monthlySalary, 0.20);

        double grossPay = employee.calculateGrossPay(12, 2023);

        assertEquals(monthlySalary, grossPay, "Gross pay should reflect the negative monthly salary.");
    }

    // Test case for getTaxRate with a positive tax rate
    @Test
    @DisplayName("getTaxRate should return the correct positive tax rate")
    void getTaxRate_positiveTaxRate_returnsCorrectRate() {
        double taxRate = 0.15;
        FullTimeEmployee employee = new FullTimeEmployee("FTE004", "Alice Brown", "Marketing", 6000.00, taxRate);
        
        double actualTaxRate = employee.getTaxRate();
        
        assertEquals(taxRate, actualTaxRate, "Tax rate should match the initialized value.");
    }

    // Test case for getTaxRate with a zero tax rate
    @Test
    @DisplayName("getTaxRate should return zero when tax rate is zero")
    void getTaxRate_zeroTaxRate_returnsZero() {
        double taxRate = 0.00;
        FullTimeEmployee employee = new FullTimeEmployee("FTE005", "Charlie Green", "IT", 7000.00, taxRate);
        
        double actualTaxRate = employee.getTaxRate();
        
        assertEquals(0.00, actualTaxRate, "Tax rate should be zero.");
    }

    // Test case for getTaxRate with a negative tax rate (edge case, though unlikely in real-world)
    @Test
    @DisplayName("getTaxRate should return the negative tax rate if initialized with one")
    void getTaxRate_negativeTaxRate_returnsNegativeValue() {
        double taxRate = -0.05; // Represents a potential error state or unique scenario
        FullTimeEmployee employee = new FullTimeEmployee("FTE006", "Diana White", "HR", 4500.00, taxRate);

        double actualTaxRate = employee.getTaxRate();

        assertEquals(taxRate, actualTaxRate, "Tax rate should reflect the negative initialized value.");
    }

    // Test case for getType
    @Test
    @DisplayName("getType should return 'FullTime'")
    void getType_always_returnsFullTime() {
        FullTimeEmployee employee = new FullTimeEmployee("FTE007", "Eve Black", "Operations", 5500.00, 0.12);
        
        String type = employee.getType();
        
        assertEquals("FullTime", type, "Employee type should always be 'FullTime'.");
    }
}