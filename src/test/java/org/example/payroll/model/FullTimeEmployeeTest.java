package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FullTimeEmployeeTest {

    // Test case for a standard full-time employee's gross pay calculation
    @Test
    void testCalculateGrossPay_standardSalary() {
        FullTimeEmployee employee = new FullTimeEmployee("FT001", "John Doe", "Engineering", 5000.00, 0.15);
        // Gross pay should be the monthly salary regardless of month and year
        assertEquals(5000.00, employee.calculateGrossPay(1, 2023), "Gross pay should match monthly salary for standard case");
        assertEquals(5000.00, employee.calculateGrossPay(7, 2024), "Gross pay should match monthly salary for a different month/year");
    }

    // Test case for gross pay when monthly salary is zero
    @Test
    void testCalculateGrossPay_zeroSalary() {
        FullTimeEmployee employee = new FullTimeEmployee("FT002", "Jane Smith", "HR", 0.00, 0.10);
        assertEquals(0.00, employee.calculateGrossPay(3, 2023), "Gross pay should be zero if monthly salary is zero");
    }

    // Test case for gross pay with a negative monthly salary (unlikely but testable input)
    @Test
    void testCalculateGrossPay_negativeSalary() {
        FullTimeEmployee employee = new FullTimeEmployee("FT003", "Bob Johnson", "Sales", -1000.00, 0.05);
        assertEquals(-1000.00, employee.calculateGrossPay(5, 2023), "Gross pay should reflect negative monthly salary if provided");
    }

    // Test case for retrieving a standard tax rate
    @Test
    void testGetTaxRate_standardRate() {
        FullTimeEmployee employee = new FullTimeEmployee("FT004", "Alice Brown", "Marketing", 6000.00, 0.20);
        assertEquals(0.20, employee.getTaxRate(), "Tax rate should match the initialized value");
    }

    // Test case for retrieving a zero tax rate
    @Test
    void testGetTaxRate_zeroRate() {
        FullTimeEmployee employee = new FullTimeEmployee("FT005", "Charlie Green", "Finance", 7000.00, 0.00);
        assertEquals(0.00, employee.getTaxRate(), "Tax rate should be zero if initialized as such");
    }

    // Test case for retrieving a relatively high tax rate
    @Test
    void testGetTaxRate_highRate() {
        FullTimeEmployee employee = new FullTimeEmployee("FT006", "Diana White", "Operations", 4500.00, 0.50);
        assertEquals(0.50, employee.getTaxRate(), "Tax rate should reflect a high value");
    }

    // Test case for retrieving the employee type
    @Test
    void testGetType() {
        FullTimeEmployee employee = new FullTimeEmployee("FT007", "Eve Black", "IT", 8000.00, 0.25);
        assertEquals("FullTime", employee.getType(), "Employee type should be 'FullTime'");
    }
}