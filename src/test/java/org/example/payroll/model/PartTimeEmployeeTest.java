package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartTimeEmployeeTest {

    private PartTimeEmployee employee;

    @BeforeEach
    void setUp() {
        // Common setup for tests, if needed. For PartTimeEmployee,
        // we'll often instantiate it directly within the test method for specific scenarios.
    }

    // Test case for a standard part-time employee with positive hourly rate and hours.
    @Test
    @DisplayName("calculateGrossPay returns correct pay for positive hourly rate and hours")
    void calculateGrossPay_positiveValues_returnsCorrectPay() {
        employee = new PartTimeEmployee("PT001", "Jane Doe", "Sales", 15.0, 80, 0.10);
        assertEquals(15.0 * 80, employee.calculateGrossPay(1, 2024), 0.001);
    }

    // Test case for zero hourly rate.
    @Test
    @DisplayName("calculateGrossPay returns zero when hourly rate is zero")
    void calculateGrossPay_zeroHourlyRate_returnsZero() {
        employee = new PartTimeEmployee("PT002", "John Smith", "Marketing", 0.0, 100, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(1, 2024), 0.001);
    }

    // Test case for zero hours worked.
    @Test
    @DisplayName("calculateGrossPay returns zero when hours worked are zero")
    void calculateGrossPay_zeroHoursWorked_returnsZero() {
        employee = new PartTimeEmployee("PT003", "Alice Brown", "HR", 20.0, 0, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(1, 2024), 0.001);
    }

    // Test case for both hourly rate and hours worked being zero.
    @Test
    @DisplayName("calculateGrossPay returns zero when both hourly rate and hours worked are zero")
    void calculateGrossPay_zeroHourlyRateAndHoursWorked_returnsZero() {
        employee = new PartTimeEmployee("PT004", "Bob White", "IT", 0.0, 0, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(1, 2024), 0.001);
    }

    // Test case for negative hourly rate.
    @Test
    @DisplayName("calculateGrossPay returns correct pay for negative hourly rate (unlikely but possible)")
    void calculateGrossPay_negativeHourlyRate_returnsNegativePay() {
        employee = new PartTimeEmployee("PT005", "Charlie Green", "Finance", -10.0, 50, 0.10);
        assertEquals(-500.0, employee.calculateGrossPay(1, 2024), 0.001);
    }

    // Test case for negative hours worked.
    @Test
    @DisplayName("calculateGrossPay returns correct pay for negative hours worked (unlikely but possible)")
    void calculateGrossPay_negativeHoursWorked_returnsNegativePay() {
        employee = new PartTimeEmployee("PT006", "Diana Blue", "Operations", 25.0, -20, 0.10);
        assertEquals(-500.0, employee.calculateGrossPay(1, 2024), 0.001);
    }

    // Test case for a standard positive tax rate.
    @Test
    @DisplayName("getTaxRate returns the correct positive tax rate")
    void getTaxRate_positiveTaxRate_returnsCorrectRate() {
        employee = new PartTimeEmployee("PT007", "Eve Black", "Management", 30.0, 60, 0.15);
        assertEquals(0.15, employee.getTaxRate(), 0.001);
    }

    // Test case for zero tax rate.
    @Test
    @DisplayName("getTaxRate returns zero when tax rate is zero")
    void getTaxRate_zeroTaxRate_returnsZero() {
        employee = new PartTimeEmployee("PT008", "Frank Gray", "Development", 40.0, 70, 0.0);
        assertEquals(0.0, employee.getTaxRate(), 0.001);
    }

    // Test case for a negative tax rate (unlikely but to ensure direct return).
    @Test
    @DisplayName("getTaxRate returns the negative tax rate when set (unlikely but valid behavior)")
    void getTaxRate_negativeTaxRate_returnsNegativeRate() {
        employee = new PartTimeEmployee("PT009", "Grace Red", "Support", 12.0, 90, -0.05);
        assertEquals(-0.05, employee.getTaxRate(), 0.001);
    }

    // Test case for a tax rate of 1.0 (100%).
    @Test
    @DisplayName("getTaxRate returns 1.0 when tax rate is 100%")
    void getTaxRate_fullTaxRate_returnsOnePointZero() {
        employee = new PartTimeEmployee("PT010", "Henry Yellow", "QA", 18.0, 75, 1.0);
        assertEquals(1.0, employee.getTaxRate(), 0.001);
    }

    // Test case for getType method, expecting "PartTime".
    @Test
    @DisplayName("getType returns 'PartTime'")
    void getType_returnsPartTime() {
        employee = new PartTimeEmployee("PT011", "Ivy Orange", "Design", 22.0, 65, 0.12);
        assertEquals("PartTime", employee.getType());
    }
}