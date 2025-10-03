package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartTimeEmployeeTest {

    // Test case for a standard scenario with positive hourly rate and hours worked
    @Test
    void calculateGrossPay_standardRatesAndHours_returnsCorrectPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT001", "Jane Doe", "Sales", 20.0, 40, 0.15);
        double expectedGrossPay = 20.0 * 40; // 800.0
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001, "Gross pay should be hourlyRate * hoursWorked");
    }

    // Test case for zero hourly rate
    @Test
    void calculateGrossPay_zeroHourlyRate_returnsZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT002", "John Smith", "Marketing", 0.0, 30, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(2, 2023), 0.001, "Gross pay should be zero when hourly rate is zero");
    }

    // Test case for zero hours worked
    @Test
    void calculateGrossPay_zeroHoursWorked_returnsZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT003", "Alice Brown", "HR", 25.0, 0, 0.20);
        assertEquals(0.0, employee.calculateGrossPay(3, 2023), 0.001, "Gross pay should be zero when hours worked are zero");
    }

    // Test case for decimal hourly rate
    @Test
    void calculateGrossPay_decimalHourlyRate_returnsCorrectPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT004", "Bob White", "IT", 15.75, 25, 0.12);
        double expectedGrossPay = 15.75 * 25; // 393.75
        assertEquals(expectedGrossPay, employee.calculateGrossPay(4, 2023), 0.001, "Gross pay should be correct with decimal hourly rate");
    }

    // Test case for relatively large values to ensure calculation accuracy
    @Test
    void calculateGrossPay_largeValues_returnsCorrectPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT005", "Charlie Green", "Finance", 1000.50, 1000, 0.25);
        double expectedGrossPay = 1000.50 * 1000; // 1000500.0
        assertEquals(expectedGrossPay, employee.calculateGrossPay(5, 2023), 0.001, "Gross pay should be correct for large values");
    }

    // Test case for a positive tax rate
    @Test
    void getTaxRate_positiveTaxRate_returnsCorrectRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT006", "David Black", "Operations", 18.0, 35, 0.18);
        assertEquals(0.18, employee.getTaxRate(), 0.001, "Should return the correct positive tax rate");
    }

    // Test case for zero tax rate
    @Test
    void getTaxRate_zeroTaxRate_returnsZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT007", "Eve Blue", "Admin", 22.0, 20, 0.0);
        assertEquals(0.0, employee.getTaxRate(), 0.001, "Should return zero for a zero tax rate");
    }

    // Test case for a decimal tax rate
    @Test
    void getTaxRate_decimalTaxRate_returnsCorrectRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT008", "Frank Red", "Support", 14.50, 40, 0.075);
        assertEquals(0.075, employee.getTaxRate(), 0.001, "Should return the correct decimal tax rate");
    }

    // Test case to ensure getType always returns "PartTime"
    @Test
    void getType_always_returnsPartTime() {
        PartTimeEmployee employee = new PartTimeEmployee("PT009", "Grace Yellow", "Logistics", 19.0, 30, 0.14);
        assertEquals("PartTime", employee.getType(), "getType() should always return 'PartTime'");
    }
}