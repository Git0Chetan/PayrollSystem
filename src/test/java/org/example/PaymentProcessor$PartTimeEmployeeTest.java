package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessor$PartTimeEmployeeTest {

    private PaymentProcessor.PartTimeEmployee partTimeEmployee;
    private static final double DELTA = 0.0001; // For double comparisons

    // Re-initialize for each test to ensure isolation
    @BeforeEach
    void setUp() {
        // Default valid constructor parameters for an employee
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT001", "Jane Doe", "Sales", 25.0, 160, 0.15);
    }

    // Test case for valid positive hourly rate and hours worked
    @Test
    @DisplayName("calculateGross: Should return correct gross for positive hourly rate and hours worked")
    void calculateGross_PositiveValues_ReturnsCorrectGross() {
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT001", "Jane Doe", "Sales", 25.0, 160, 0.15);
        double expectedGross = 25.0 * 160; // 4000.0
        assertEquals(expectedGross, partTimeEmployee.calculateGross(1, 2023), DELTA,
                "Gross calculation should be correct for positive values.");
    }

    // Test case for zero hourly rate
    @Test
    @DisplayName("calculateGross: Should return zero gross for zero hourly rate")
    void calculateGross_ZeroHourlyRate_ReturnsZero() {
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT002", "John Smith", "IT", 0.0, 100, 0.10);
        double expectedGross = 0.0 * 100; // 0.0
        assertEquals(expectedGross, partTimeEmployee.calculateGross(2, 2023), DELTA,
                "Gross calculation should be zero when hourly rate is zero.");
    }

    // Test case for zero hours worked
    @Test
    @DisplayName("calculateGross: Should return zero gross for zero hours worked")
    void calculateGross_ZeroHoursWorked_ReturnsZero() {
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT003", "Alice Johnson", "Marketing", 30.0, 0, 0.12);
        double expectedGross = 30.0 * 0; // 0.0
        assertEquals(expectedGross, partTimeEmployee.calculateGross(3, 2023), DELTA,
                "Gross calculation should be zero when hours worked is zero.");
    }

    // Test case for both zero hourly rate and hours worked
    @Test
    @DisplayName("calculateGross: Should return zero gross for zero hourly rate and zero hours worked")
    void calculateGross_ZeroHourlyRateAndHoursWorked_ReturnsZero() {
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT004", "Bob Brown", "HR", 0.0, 0, 0.05);
        double expectedGross = 0.0 * 0; // 0.0
        assertEquals(expectedGross, partTimeEmployee.calculateGross(4, 2023), DELTA,
                "Gross calculation should be zero when both hourly rate and hours worked are zero.");
    }

    // Test case for negative hourly rate (unlikely but possible via constructor)
    @Test
    @DisplayName("calculateGross: Should return negative gross for negative hourly rate")
    void calculateGross_NegativeHourlyRate_ReturnsNegativeGross() {
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT005", "Charlie Green", "Operations", -10.0, 50, 0.10);
        double expectedGross = -10.0 * 50; // -500.0
        assertEquals(expectedGross, partTimeEmployee.calculateGross(5, 2023), DELTA,
                "Gross calculation should be negative if hourly rate is negative.");
    }

    // Test case for negative hours worked (unlikely but possible via constructor)
    @Test
    @DisplayName("calculateGross: Should return negative gross for negative hours worked")
    void calculateGross_NegativeHoursWorked_ReturnsNegativeGross() {
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT006", "David Blue", "Finance", 20.0, -10, 0.18);
        double expectedGross = 20.0 * -10; // -200.0
        assertEquals(expectedGross, partTimeEmployee.calculateGross(6, 2023), DELTA,
                "Gross calculation should be negative if hours worked is negative.");
    }

    // Test case for negative hourly rate and negative hours worked (should result in positive gross)
    @Test
    @DisplayName("calculateGross: Should return positive gross for negative hourly rate and negative hours worked")
    void calculateGross_NegativeHourlyRateAndHoursWorked_ReturnsPositiveGross() {
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT007", "Eve White", "IT", -15.0, -80, 0.08);
        double expectedGross = -15.0 * -80; // 1200.0
        assertEquals(expectedGross, partTimeEmployee.calculateGross(7, 2023), DELTA,
                "Gross calculation should be positive if both hourly rate and hours worked are negative.");
    }

    // Test case for positive tax rate
    @Test
    @DisplayName("getTaxRate: Should return the correct positive tax rate")
    void getTaxRate_PositiveTaxRate_ReturnsCorrectRate() {
        double taxRate = 0.15;
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT001", "Jane Doe", "Sales", 25.0, 160, taxRate);
        assertEquals(taxRate, partTimeEmployee.getTaxRate(), DELTA,
                "getTaxRate should return the initialized positive tax rate.");
    }

    // Test case for zero tax rate
    @Test
    @DisplayName("getTaxRate: Should return zero for a zero tax rate")
    void getTaxRate_ZeroTaxRate_ReturnsZero() {
        double taxRate = 0.0;
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT008", "Frank Black", "Development", 40.0, 100, taxRate);
        assertEquals(taxRate, partTimeEmployee.getTaxRate(), DELTA,
                "getTaxRate should return zero for a zero tax rate.");
    }

    // Test case for a negative tax rate (unlikely but possible via constructor)
    @Test
    @DisplayName("getTaxRate: Should return negative for a negative tax rate")
    void getTaxRate_NegativeTaxRate_ReturnsNegative() {
        double taxRate = -0.05;
        partTimeEmployee = new PaymentProcessor.PartTimeEmployee(
                "PT009", "Grace Hall", "Support", 18.0, 120, taxRate);
        assertEquals(taxRate, partTimeEmployee.getTaxRate(), DELTA,
                "getTaxRate should return a negative value if initialized with one.");
    }

    // Test case for getType method
    @Test
    @DisplayName("getType: Should return 'PartTime'")
    void getType_ReturnsPartTime() {
        assertEquals("PartTime", partTimeEmployee.getType(),
                "getType should return 'PartTime' for a PartTimeEmployee.");
    }
}