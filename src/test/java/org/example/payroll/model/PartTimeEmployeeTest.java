package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartTimeEmployeeTest {

    // Test case for calculateGrossPay with positive hourly rate and hours worked.
    @Test
    void calculateGrossPay_positiveValues_returnsCorrectGrossPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT001", "Jane Doe", "HR", 20.0, 80, 0.10);
        double expectedGrossPay = 20.0 * 80;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), 0.001);
    }

    // Test case for calculateGrossPay with zero hourly rate.
    @Test
    void calculateGrossPay_zeroHourlyRate_returnsZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT002", "John Smith", "IT", 0.0, 100, 0.10);
        double expectedGrossPay = 0.0 * 100;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(2, 2023), 0.001);
    }

    // Test case for calculateGrossPay with zero hours worked.
    @Test
    void calculateGrossPay_zeroHoursWorked_returnsZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT003", "Alice Johnson", "Finance", 25.0, 0, 0.10);
        double expectedGrossPay = 25.0 * 0;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(3, 2023), 0.001);
    }

    // Test case for calculateGrossPay with both zero hourly rate and hours worked.
    @Test
    void calculateGrossPay_zeroRateAndHours_returnsZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT004", "Bob Williams", "Marketing", 0.0, 0, 0.10);
        double expectedGrossPay = 0.0 * 0;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(4, 2023), 0.001);
    }

    // Test case for calculateGrossPay with a negative hourly rate (edge case for input validity).
    @Test
    void calculateGrossPay_negativeHourlyRate_returnsNegativeGrossPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT005", "Charlie Brown", "Sales", -15.0, 50, 0.10);
        double expectedGrossPay = -15.0 * 50;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(5, 2023), 0.001);
    }

    // Test case for calculateGrossPay with negative hours worked (edge case for input validity).
    @Test
    void calculateGrossPay_negativeHoursWorked_returnsNegativeGrossPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT006", "Diana Prince", "Security", 30.0, -40, 0.10);
        double expectedGrossPay = 30.0 * -40;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(6, 2023), 0.001);
    }

    // Test case for calculateGrossPay with large values to check double precision.
    @Test
    void calculateGrossPay_largeValues_returnsCorrectGrossPay() {
        PartTimeEmployee employee = new PartTimeEmployee("PT007", "Clark Kent", "Operations", 1000.0, 2000, 0.10);
        double expectedGrossPay = 1000.0 * 2000;
        assertEquals(expectedGrossPay, employee.calculateGrossPay(7, 2023), 0.001);
    }

    // Test case for getTaxRate with a positive tax rate.
    @Test
    void getTaxRate_positiveRate_returnsCorrectTaxRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT008", "Bruce Wayne", "Management", 50.0, 160, 0.15);
        assertEquals(0.15, employee.getTaxRate(), 0.001);
    }

    // Test case for getTaxRate with a zero tax rate.
    @Test
    void getTaxRate_zeroRate_returnsZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT009", "Selina Kyle", "Logistics", 40.0, 120, 0.0);
        assertEquals(0.0, employee.getTaxRate(), 0.001);
    }

    // Test case for getTaxRate with a negative tax rate (edge case for input validity).
    @Test
    void getTaxRate_negativeRate_returnsNegativeRate() {
        PartTimeEmployee employee = new PartTimeEmployee("PT010", "Arthur Curry", "Maintenance", 35.0, 100, -0.05);
        assertEquals(-0.05, employee.getTaxRate(), 0.001);
    }

    // Test case for getTaxRate with a 100% tax rate.
    @Test
    void getTaxRate_oneHundredPercentRate_returnsOnePointZero() {
        PartTimeEmployee employee = new PartTimeEmployee("PT011", "Barry Allen", "R&D", 60.0, 150, 1.0);
        assertEquals(1.0, employee.getTaxRate(), 0.001);
    }

    // Test case for getType to ensure it returns "PartTime".
    @Test
    void getType_returnsPartTime() {
        PartTimeEmployee employee = new PartTimeEmployee("PT012", "Hal Jordan", "Engineering", 45.0, 130, 0.08);
        assertEquals("PartTime", employee.getType());
    }
}