package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartTimeEmployeeTest {

    private static final double DELTA = 0.0001; // For double comparisons

    // Test case for positive hourly rate and hours worked
    @Test
    void calculateGrossPay_positiveValues_shouldReturnCorrectPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT001", "Jane Doe", "Sales", 15.0, 80, 0.15);
        assertEquals(15.0 * 80, employee.calculateGrossPay(1, 2023), DELTA, "Gross pay should be hourlyRate * hoursWorked for positive values.");
    }

    // Test case for zero hourly rate
    @Test
    void calculateGrossPay_zeroHourlyRate_shouldReturnZeroPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT002", "John Smith", "Marketing", 0.0, 100, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(2, 2023), DELTA, "Gross pay should be 0.0 when hourly rate is 0.0.");
    }

    // Test case for zero hours worked
    @Test
    void calculateGrossPay_zeroHoursWorked_shouldReturnZeroPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT003", "Alice Johnson", "HR", 20.0, 0, 0.20);
        assertEquals(0.0, employee.calculateGrossPay(3, 2023), DELTA, "Gross pay should be 0.0 when hours worked is 0.");
    }

    // Test case for negative hourly rate (uncommon, but testing behavior)
    @Test
    void calculateGrossPay_negativeHourlyRate_shouldReturnNegativePay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT004", "Bob Brown", "IT", -10.0, 50, 0.05);
        assertEquals(-10.0 * 50, employee.calculateGrossPay(4, 2023), DELTA, "Gross pay should be negative when hourly rate is negative.");
    }

    // Test case for negative hours worked (uncommon, but testing behavior)
    @Test
    void calculateGrossPay_negativeHoursWorked_shouldReturnNegativePay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT005", "Charlie Davis", "Finance", 25.0, -20, 0.25);
        assertEquals(25.0 * -20, employee.calculateGrossPay(5, 2023), DELTA, "Gross pay should be negative when hours worked is negative.");
    }

    // Test case for both zero hourly rate and hours worked
    @Test
    void calculateGrossPay_zeroHourlyRateAndHoursWorked_shouldReturnZeroPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT006", "Eve White", "Operations", 0.0, 0, 0.12);
        assertEquals(0.0, employee.calculateGrossPay(6, 2023), DELTA, "Gross pay should be 0.0 when both hourly rate and hours worked are 0.");
    }

    // Test case for positive tax rate
    @Test
    void getTaxRate_positiveTaxRate_shouldReturnCorrectRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT007", "Frank Green", "Sales", 18.0, 70, 0.18);
        assertEquals(0.18, employee.getTaxRate(), DELTA, "Should return the correct positive tax rate.");
    }

    // Test case for zero tax rate
    @Test
    void getTaxRate_zeroTaxRate_shouldReturnZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT008", "Grace Hall", "Marketing", 12.0, 60, 0.0);
        assertEquals(0.0, employee.getTaxRate(), DELTA, "Should return 0.0 when tax rate is 0.");
    }

    // Test case for tax rate of 1.0 (100%)
    @Test
    void getTaxRate_fullTaxRate_shouldReturnOne() {
        PartTimeEmployee employee = new PartTimeEmployee("PT009", "Henry King", "HR", 22.0, 90, 1.0);
        assertEquals(1.0, employee.getTaxRate(), DELTA, "Should return 1.0 when tax rate is 1.0.");
    }

    // Test case for a negative tax rate (uncommon, but testing behavior)
    @Test
    void getTaxRate_negativeTaxRate_shouldReturnNegativeRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT010", "Ivy Lee", "IT", 30.0, 40, -0.05);
        assertEquals(-0.05, employee.getTaxRate(), DELTA, "Should return the correct negative tax rate.");
    }

    // Test case for getType method
    @Test
    void getType_shouldReturnPartTime() {
        PartTimeEmployee employee = new PartTimeEmployee("PT011", "Jack Martin", "Finance", 10.0, 30, 0.1);
        assertEquals("PartTime", employee.getType(), "getType should always return 'PartTime'.");
    }
}