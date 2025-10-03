package org.example.payroll.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FullTimeEmployeeTest {

    // Test case for calculating gross pay with a positive monthly salary
    @Test
    @DisplayName("calculateGrossPay should return the monthly salary for a positive salary")
    void calculateGrossPay_PositiveMonthlySalary_ReturnsMonthlySalary() {
        double monthlySalary = 5000.00;
        FullTimeEmployee employee = new FullTimeEmployee("E001", "John Doe", "Sales", monthlySalary, 0.15);
        double expectedGrossPay = monthlySalary;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), "Gross pay should be equal to the monthly salary.");
    }

    // Test case for calculating gross pay with zero monthly salary
    @Test
    @DisplayName("calculateGrossPay should return zero for a zero monthly salary")
    void calculateGrossPay_ZeroMonthlySalary_ReturnsZero() {
        double monthlySalary = 0.00;
        FullTimeEmployee employee = new FullTimeEmployee("E002", "Jane Smith", "HR", monthlySalary, 0.10);
        double expectedGrossPay = monthlySalary;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(6, 2023), "Gross pay should be zero for zero monthly salary.");
    }

    // Test case for calculating gross pay with a negative monthly salary (edge case, though unlikely in practice)
    @Test
    @DisplayName("calculateGrossPay should return the negative monthly salary for a negative salary")
    void calculateGrossPay_NegativeMonthlySalary_ReturnsNegativeMonthlySalary() {
        double monthlySalary = -1000.00; // Represents a potential deduction or error
        FullTimeEmployee employee = new FullTimeEmployee("E003", "Bob Johnson", "IT", monthlySalary, 0.20);
        double expectedGrossPay = monthlySalary;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(12, 2022), "Gross pay should be equal to the negative monthly salary.");
    }

    // Test case for retrieving a positive tax rate
    @Test
    @DisplayName("getTaxRate should return the correct positive tax rate")
    void getTaxRate_PositiveTaxRate_ReturnsCorrectRate() {
        double taxRate = 0.18;
        FullTimeEmployee employee = new FullTimeEmployee("E004", "Alice Brown", "Marketing", 4500.00, taxRate);
        double expectedTaxRate = taxRate;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should match the initialized value.");
    }

    // Test case for retrieving a zero tax rate
    @Test
    @DisplayName("getTaxRate should return zero for a zero tax rate")
    void getTaxRate_ZeroTaxRate_ReturnsZero() {
        double taxRate = 0.00;
        FullTimeEmployee employee = new FullTimeEmployee("E005", "Charlie Green", "Finance", 6000.00, taxRate);
        double expectedTaxRate = taxRate;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should be zero.");
    }

    // Test case for retrieving a negative tax rate (edge case, though unlikely in practice)
    @Test
    @DisplayName("getTaxRate should return the correct negative tax rate")
    void getTaxRate_NegativeTaxRate_ReturnsCorrectRate() {
        double taxRate = -0.05; // Represents a potential tax credit or reverse tax
        FullTimeEmployee employee = new FullTimeEmployee("E006", "Diana Prince", "Security", 5500.00, taxRate);
        double expectedTaxRate = taxRate;
        assertEquals(expectedTaxRate, employee.getTaxRate(), "Tax rate should match the initialized negative value.");
    }

    // Test case for getting the employee type
    @Test
    @DisplayName("getType should return 'FullTime' for a FullTimeEmployee")
    void getType_FullTimeEmployee_ReturnsFullTime() {
        FullTimeEmployee employee = new FullTimeEmployee("E007", "Bruce Wayne", "Management", 10000.00, 0.30);
        String expectedType = "FullTime";
        assertEquals(expectedType, employee.getType(), "Employee type should be 'FullTime'.");
    }
}