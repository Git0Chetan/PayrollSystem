package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FullTimeEmployeeTest {

    // Test case for calculating gross pay with a positive monthly salary
    @Test
    void testCalculateGrossPay_PositiveSalary() {
        FullTimeEmployee employee = new FullTimeEmployee("FT001", "Alice Smith", "HR", 5000.00, 0.15);
        double expectedGrossPay = 5000.00;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), "Gross pay should be equal to the monthly salary for a positive salary.");
    }

    // Test case for calculating gross pay with a zero monthly salary
    @Test
    void testCalculateGrossPay_ZeroSalary() {
        FullTimeEmployee employee = new FullTimeEmployee("FT002", "Bob Johnson", "IT", 0.00, 0.10);
        double expectedGrossPay = 0.00;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(2, 2023), "Gross pay should be 0.00 for a zero monthly salary.");
    }

    // Test case for calculating gross pay with a negative monthly salary (unusual but allowed by model)
    @Test
    void testCalculateGrossPay_NegativeSalary() {
        FullTimeEmployee employee = new FullTimeEmployee("FT003", "Charlie Brown", "Finance", -1000.00, 0.20);
        double expectedGrossPay = -1000.00;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(3, 2023), "Gross pay should be the negative monthly salary if specified.");
    }

    // Test case for getting a positive tax rate
    @Test
    void testGetTaxRate_PositiveRate() {
        FullTimeEmployee employee = new FullTimeEmployee("FT001", "Alice Smith", "HR", 5000.00, 0.15);
        double expectedTaxRate = 0.15;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should be the value set in the constructor.");
    }

    // Test case for getting a zero tax rate
    @Test
    void testGetTaxRate_ZeroRate() {
        FullTimeEmployee employee = new FullTimeEmployee("FT004", "David Lee", "Marketing", 4000.00, 0.00);
        double expectedTaxRate = 0.00;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should be 0.00 if specified.");
    }

    // Test case for getting a negative tax rate (unusual but allowed by model)
    @Test
    void testGetTaxRate_NegativeRate() {
        FullTimeEmployee employee = new FullTimeEmployee("FT005", "Eve Davis", "Sales", 6000.00, -0.05);
        double expectedTaxRate = -0.05;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should be the negative value if specified.");
    }

    // Test case for getting the employee type
    @Test
    void testGetType() {
        FullTimeEmployee employee = new FullTimeEmployee("FT001", "Alice Smith", "HR", 5000.00, 0.15);
        String expectedType = "FullTime";
        assertEquals(expectedType, employee.getType(), "Employee type should be 'FullTime'.");
    }
}