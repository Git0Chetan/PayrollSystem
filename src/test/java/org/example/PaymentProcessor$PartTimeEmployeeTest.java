package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessor$PartTimeEmployeeTest {

    private static final double DELTA = 0.001; // For double comparisons

    // Test case for calculateGross with positive hourly rate and hours worked
    @Test
    void calculateGross_positiveValues_shouldReturnCorrectGross() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT001", "Jane Doe", "Sales", 20.0, 80, 0.10);
        double expectedGross = 20.0 * 80; // 1600.0
        assertEquals(expectedGross, employee.calculateGross(1, 2023), DELTA);
    }

    // Test case for calculateGross with zero hourly rate
    @Test
    void calculateGross_zeroHourlyRate_shouldReturnZero() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT002", "John Smith", "Marketing", 0.0, 100, 0.15);
        double expectedGross = 0.0 * 100; // 0.0
        assertEquals(expectedGross, employee.calculateGross(2, 2023), DELTA);
    }

    // Test case for calculateGross with zero hours worked
    @Test
    void calculateGross_zeroHoursWorked_shouldReturnZero() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT003", "Alice Brown", "HR", 25.0, 0, 0.05);
        double expectedGross = 25.0 * 0; // 0.0
        assertEquals(expectedGross, employee.calculateGross(3, 2023), DELTA);
    }

    // Test case for calculateGross with both zero hourly rate and hours worked
    @Test
    void calculateGross_zeroHourlyRateAndHoursWorked_shouldReturnZero() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT004", "Bob White", "IT", 0.0, 0, 0.08);
        double expectedGross = 0.0 * 0; // 0.0
        assertEquals(expectedGross, employee.calculateGross(4, 2023), DELTA);
    }

    // Test case for calculateGross with a negative hourly rate (unusual, but current implementation allows)
    @Test
    void calculateGross_negativeHourlyRate_shouldReturnNegativeGross() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT005", "Charlie Green", "Finance", -10.0, 50, 0.12);
        double expectedGross = -10.0 * 50; // -500.0
        assertEquals(expectedGross, employee.calculateGross(5, 2023), DELTA);
    }

    // Test case for calculateGross with negative hours worked (unusual, but current implementation allows)
    @Test
    void calculateGross_negativeHoursWorked_shouldReturnNegativeGross() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT006", "Diana Blue", "Operations", 30.0, -20, 0.18);
        double expectedGross = 30.0 * -20; // -600.0
        assertEquals(expectedGross, employee.calculateGross(6, 2023), DELTA);
    }

    // Test case for calculateGross with large numbers to ensure double precision
    @Test
    void calculateGross_largeValues_shouldHandlePrecision() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT007", "Eve Red", "Admin", 12345.67, 160, 0.20);
        double expectedGross = 12345.67 * 160; // 1975307.2
        assertEquals(expectedGross, employee.calculateGross(7, 2023), DELTA);
    }

    // Test case for getTaxRate with a positive tax rate
    @Test
    void getTaxRate_positiveTaxRate_shouldReturnCorrectRate() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT008", "Frank Black", "HR", 15.0, 60, 0.12);
        assertEquals(0.12, employee.getTaxRate(), DELTA);
    }

    // Test case for getTaxRate with a zero tax rate
    @Test
    void getTaxRate_zeroTaxRate_shouldReturnZero() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT009", "Grace White", "Sales", 18.0, 70, 0.0);
        assertEquals(0.0, employee.getTaxRate(), DELTA);
    }

    // Test case for getTaxRate with a negative tax rate (unusual, but current implementation allows)
    @Test
    void getTaxRate_negativeTaxRate_shouldReturnNegativeRate() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT010", "Harry Green", "Marketing", 22.0, 90, -0.05);
        assertEquals(-0.05, employee.getTaxRate(), DELTA);
    }

    // Test case for getType method
    @Test
    void getType_shouldReturnPartTime() {
        PaymentProcessor.PartTimeEmployee employee = new PaymentProcessor.PartTimeEmployee(
                "PT011", "Ivy Yellow", "IT", 30.0, 100, 0.25);
        assertEquals("PartTime", employee.getType());
    }
}