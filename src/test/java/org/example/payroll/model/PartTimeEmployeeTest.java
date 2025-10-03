package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartTimeEmployeeTest {

    private static final double DELTA = 0.0001; // For double comparisons

    // Test case for standard positive hourly rate and hours worked
    @Test
    void calculateGrossPay_standardPositiveValues_shouldReturnCorrectPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT001", "Jane Doe", "Sales", 15.0, 80, 0.10);
        assertEquals(1200.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for zero hourly rate
    @Test
    void calculateGrossPay_zeroHourlyRate_shouldReturnZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT002", "John Smith", "Marketing", 0.0, 100, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for zero hours worked
    @Test
    void calculateGrossPay_zeroHoursWorked_shouldReturnZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT003", "Alice Johnson", "HR", 20.0, 0, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for both zero hourly rate and zero hours worked
    @Test
    void calculateGrossPay_bothZero_shouldReturnZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT004", "Bob Williams", "IT", 0.0, 0, 0.10);
        assertEquals(0.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for negative hourly rate (unusual, but mathematically possible)
    @Test
    void calculateGrossPay_negativeHourlyRate_shouldReturnNegativePay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT005", "Charlie Brown", "Admin", -10.0, 50, 0.10);
        assertEquals(-500.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for negative hours worked (unusual, but mathematically possible)
    @Test
    void calculateGrossPay_negativeHoursWorked_shouldReturnNegativePay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT006", "Diana Prince", "Security", 25.0, -20, 0.10);
        assertEquals(-500.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for fractional hourly rate
    @Test
    void calculateGrossPay_fractionalHourlyRate_shouldCalculateCorrectly() {
        PartTimeEmployee employee = new PartTimeEmployee("PT007", "Eve Green", "Finance", 12.75, 40, 0.10);
        assertEquals(510.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for large numbers to ensure double precision
    @Test
    void calculateGrossPay_largeNumbers_shouldCalculateCorrectly() {
        PartTimeEmployee employee = new PartTimeEmployee("PT008", "Frank White", "Operations", 1000.0, 5000, 0.10);
        assertEquals(5_000_000.0, employee.calculateGrossPay(1, 2023), DELTA);
    }

    // Test case for a standard positive tax rate
    @Test
    void getTaxRate_positiveTaxRate_shouldReturnCorrectRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT009", "Grace Kelly", "R&D", 20.0, 160, 0.15);
        assertEquals(0.15, employee.getTaxRate(), DELTA);
    }

    // Test case for a zero tax rate
    @Test
    void getTaxRate_zeroTaxRate_shouldReturnZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT010", "Harry Potter", "Magic", 10.0, 40, 0.0);
        assertEquals(0.0, employee.getTaxRate(), DELTA);
    }

    // Test case for a high tax rate close to 1.0 (100%)
    @Test
    void getTaxRate_highTaxRate_shouldReturnCorrectRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT011", "Ivy Queen", "Botany", 18.0, 120, 0.99);
        assertEquals(0.99, employee.getTaxRate(), DELTA);
    }

    // Test case for a tax rate of exactly 1.0 (100%)
    @Test
    void getTaxRate_oneHundredPercentTaxRate_shouldReturnOne() {
        PartTimeEmployee employee = new PartTimeEmployee("PT012", "Jack Frost", "Weather", 22.0, 100, 1.0);
        assertEquals(1.0, employee.getTaxRate(), DELTA);
    }

    // Test case for a negative tax rate (unusual, but the field allows it)
    @Test
    void getTaxRate_negativeTaxRate_shouldReturnNegativeRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT013", "Karen Miller", "Support", 14.0, 60, -0.05);
        assertEquals(-0.05, employee.getTaxRate(), DELTA);
    }

    // Test case for the getType method
    @Test
    void getType_shouldReturnPartTime() {
        PartTimeEmployee employee = new PartTimeEmployee("PT014", "Liam Neeson", "Acting", 30.0, 20, 0.20);
        assertEquals("PartTime", employee.getType());
    }

    // Test case to ensure getType always returns "PartTime" regardless of other parameters
    @Test
    void getType_alwaysReturnsPartTime_evenWithZeroValues() {
        PartTimeEmployee employee = new PartTimeEmployee("PT015", "Mia Khalifa", "Internet", 0.0, 0, 0.0);
        assertEquals("PartTime", employee.getType());
    }
}