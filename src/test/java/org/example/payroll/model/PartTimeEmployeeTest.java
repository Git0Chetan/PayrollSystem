package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartTimeEmployeeTest {

    // Test case for calculating gross pay with positive hourly rate and hours worked
    @Test
    void testCalculateGrossPay_PositiveValues() {
        PartTimeEmployee employee = new PartTimeEmployee("PT001", "Alice", "Sales",
                25.0, 80, 0.15);
        double expectedGrossPay = 25.0 * 80;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for calculating gross pay with zero hours worked
    @Test
    void testCalculateGrossPay_ZeroHoursWorked() {
        PartTimeEmployee employee = new PartTimeEmployee("PT002", "Bob", "Marketing",
                30.0, 0, 0.10);
        double expectedGrossPay = 30.0 * 0;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for calculating gross pay with zero hourly rate
    @Test
    void testCalculateGrossPay_ZeroHourlyRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT003", "Charlie", "HR",
                0.0, 100, 0.05);
        double expectedGrossPay = 0.0 * 100;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for calculating gross pay with both hourly rate and hours worked as zero
    @Test
    void testCalculateGrossPay_ZeroHourlyRateAndHoursWorked() {
        PartTimeEmployee employee = new PartTimeEmployee("PT004", "Diana", "IT",
                0.0, 0, 0.0);
        double expectedGrossPay = 0.0 * 0;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for calculating gross pay with negative hourly rate (unusual, but tests direct calculation)
    @Test
    void testCalculateGrossPay_NegativeHourlyRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT005", "Eve", "Finance",
                -15.0, 50, 0.20);
        double expectedGrossPay = -15.0 * 50;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for calculating gross pay with negative hours worked (unusual, but tests direct calculation)
    @Test
    void testCalculateGrossPay_NegativeHoursWorked() {
        PartTimeEmployee employee = new PartTimeEmployee("PT006", "Frank", "Operations",
                20.0, -20, 0.12);
        double expectedGrossPay = 20.0 * -20;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for calculating gross pay with large values
    @Test
    void testCalculateGrossPay_LargeValues() {
        PartTimeEmployee employee = new PartTimeEmployee("PT007", "Grace", "R&D",
                1500.0, 160, 0.25);
        double expectedGrossPay = 1500.0 * 160;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for retrieving a positive tax rate
    @Test
    void testGetTaxRate_PositiveTaxRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT008", "Heidi", "Admin",
                20.0, 100, 0.18);
        double expectedTaxRate = 0.18;
        assertEquals(expectedTaxRate, employee.getTaxRate(), 0.001);
    }

    // Test case for retrieving a zero tax rate
    @Test
    void testGetTaxRate_ZeroTaxRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT009", "Ivan", "Support",
                18.0, 120, 0.0);
        double expectedTaxRate = 0.0;
        assertEquals(expectedTaxRate, employee.getTaxRate(), 0.001);
    }

    // Test case for retrieving a negative tax rate (unusual, but tests direct getter behavior)
    @Test
    void testGetTaxRate_NegativeTaxRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT010", "Judy", "Logistics",
                22.0, 90, -0.05);
        double expectedTaxRate = -0.05;
        assertEquals(expectedTaxRate, employee.getTaxRate(), 0.001);
    }

    // Test case for verifying the employee type is "PartTime"
    @Test
    void testGetType_ReturnsPartTime() {
        PartTimeEmployee employee = new PartTimeEmployee("PT011", "Karl", "Security",
                15.0, 60, 0.10);
        String expectedType = "PartTime";
        assertEquals(expectedType, employee.getType());
    }
}