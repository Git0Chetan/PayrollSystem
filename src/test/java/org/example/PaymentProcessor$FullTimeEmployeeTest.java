package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessor$FullTimeEmployeeTest {

    private static final double DELTA = 0.001; // Tolerance for double comparisons

    // No @BeforeEach needed as employee objects are simple and instantiated per test.

    @Test
    @DisplayName("calculateGross: Should return the correct positive monthly salary")
    void testCalculateGross_positiveMonthlySalary() {
        // Test case for a typical positive monthly salary
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT001", "John Doe", "Sales", 5000.0, 0.15);
        assertEquals(5000.0, employee.calculateGross(1, 2023), DELTA, "Gross salary should match the monthly salary for a positive value.");
    }

    @Test
    @DisplayName("calculateGross: Should return zero for zero monthly salary")
    void testCalculateGross_zeroMonthlySalary() {
        // Test case for a zero monthly salary
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT002", "Jane Smith", "HR", 0.0, 0.10);
        assertEquals(0.0, employee.calculateGross(6, 2024), DELTA, "Gross salary should be zero for zero monthly salary.");
    }

    @Test
    @DisplayName("calculateGross: Should return the monthly salary even if negative (unlikely but possible input)")
    void testCalculateGross_negativeMonthlySalary() {
        // Test case for a negative monthly salary, though unrealistic in practice, verifies method behavior.
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT003", "Bob Johnson", "IT", -100.0, 0.20);
        assertEquals(-100.0, employee.calculateGross(12, 2022), DELTA, "Gross salary should return the negative monthly salary if provided.");
    }

    @Test
    @DisplayName("calculateGross: Should return monthly salary regardless of month and year parameters")
    void testCalculateGross_monthYearParametersIgnored() {
        // Test case to ensure month and year parameters do not affect the result
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT004", "Alice Brown", "Marketing", 3500.0, 0.18);
        assertEquals(3500.0, employee.calculateGross(1, 2023), DELTA, "Gross salary should be correct for Jan 2023.");
        assertEquals(3500.0, employee.calculateGross(7, 2025), DELTA, "Gross salary should be correct for Jul 2025.");
        assertEquals(3500.0, employee.calculateGross(12, 1999), DELTA, "Gross salary should be correct for Dec 1999.");
    }

    @Test
    @DisplayName("getTaxRate: Should return the correct positive tax rate")
    void testGetTaxRate_positiveRate() {
        // Test case for a typical positive tax rate
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT005", "Charlie Green", "Finance", 6000.0, 0.25);
        assertEquals(0.25, employee.getTaxRate(), DELTA, "Tax rate should return the correct positive value.");
    }

    @Test
    @DisplayName("getTaxRate: Should return zero for a zero tax rate")
    void testGetTaxRate_zeroRate() {
        // Test case for a zero tax rate
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT006", "Diana White", "Operations", 4000.0, 0.0);
        assertEquals(0.0, employee.getTaxRate(), DELTA, "Tax rate should be zero if initialized to zero.");
    }

    @Test
    @DisplayName("getTaxRate: Should return 1.0 for a 100% tax rate")
    void testGetTaxRate_oneHundredPercentRate() {
        // Test case for a 100% tax rate
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT007", "Eve Black", "R&D", 7000.0, 1.0);
        assertEquals(1.0, employee.getTaxRate(), DELTA, "Tax rate should be 1.0 if initialized to 1.0.");
    }

    @Test
    @DisplayName("getTaxRate: Should return a negative rate if initialized with one (unlikely but reflects direct return)")
    void testGetTaxRate_negativeRate() {
        // Test case for a negative tax rate, verifying direct return of the stored value.
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT008", "Frank Red", "IT", 4500.0, -0.05);
        assertEquals(-0.05, employee.getTaxRate(), DELTA, "Tax rate should return a negative value if initialized with one.");
    }

    @Test
    @DisplayName("getType: Should consistently return 'FullTime'")
    void testGetType_returnsFullTime() {
        // Test case to verify the type string is always "FullTime"
        PaymentProcessor.FullTimeEmployee employee = new PaymentProcessor.FullTimeEmployee("FT009", "Grace Blue", "Admin", 3000.0, 0.10);
        assertEquals("FullTime", employee.getType(), "getType() should always return 'FullTime' for FullTimeEmployee.");
    }
}