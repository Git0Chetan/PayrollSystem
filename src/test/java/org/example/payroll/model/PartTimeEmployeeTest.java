package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartTimeEmployeeTest {

    private PartTimeEmployee employee;
    private final String ID = "PT001";
    private final String NAME = "Jane Doe";
    private final String DEPARTMENT = "Sales";
    private final double HOURLY_RATE = 15.0;
    private final int HOURS_WORKED = 160;
    private final double TAX_RATE = 0.15;

    @BeforeEach
    void setUp() {
        employee = new PartTimeEmployee(ID, NAME, DEPARTMENT, HOURLY_RATE, HOURS_WORKED, TAX_RATE);
    }

    // Test case for typical positive hourly rate and hours worked
    @Test
    @DisplayName("calculateGrossPay should return correct gross pay for positive hourly rate and hours worked")
    void calculateGrossPay_positiveValues_returnsCorrectPay() {
        double expectedGrossPay = HOURLY_RATE * HOURS_WORKED; // 15.0 * 160 = 2400.0
        assertEquals(expectedGrossPay, employee.calculateGrossPay(1, 2023), "Gross pay calculation for positive values failed.");
    }

    // Test case for zero hours worked
    @Test
    @DisplayName("calculateGrossPay should return zero when hours worked is zero")
    void calculateGrossPay_zeroHoursWorked_returnsZero() {
        PartTimeEmployee zeroHoursEmployee = new PartTimeEmployee(ID, NAME, DEPARTMENT, HOURLY_RATE, 0, TAX_RATE);
        assertEquals(0.0, zeroHoursEmployee.calculateGrossPay(1, 2023), "Gross pay should be zero when hours worked is zero.");
    }

    // Test case for zero hourly rate
    @Test
    @DisplayName("calculateGrossPay should return zero when hourly rate is zero")
    void calculateGrossPay_zeroHourlyRate_returnsZero() {
        PartTimeEmployee zeroRateEmployee = new PartTimeEmployee(ID, NAME, DEPARTMENT, 0.0, HOURS_WORKED, TAX_RATE);
        assertEquals(0.0, zeroRateEmployee.calculateGrossPay(1, 2023), "Gross pay should be zero when hourly rate is zero.");
    }

    // Test case for negative hourly rate (as allowed by constructor, though atypical)
    @Test
    @DisplayName("calculateGrossPay should return a negative value for negative hourly rate")
    void calculateGrossPay_negativeHourlyRate_returnsNegativePay() {
        PartTimeEmployee negativeRateEmployee = new PartTimeEmployee(ID, NAME, DEPARTMENT, -10.0, HOURS_WORKED, TAX_RATE);
        double expectedGrossPay = -10.0 * HOURS_WORKED; // -1600.0
        assertEquals(expectedGrossPay, negativeRateEmployee.calculateGrossPay(1, 2023), "Gross pay calculation for negative hourly rate failed.");
    }

    // Test case for negative hours worked (as allowed by constructor, though atypical)
    @Test
    @DisplayName("calculateGrossPay should return a negative value for negative hours worked")
    void calculateGrossPay_negativeHoursWorked_returnsNegativePay() {
        PartTimeEmployee negativeHoursEmployee = new PartTimeEmployee(ID, NAME, DEPARTMENT, HOURLY_RATE, -50, TAX_RATE);
        double expectedGrossPay = HOURLY_RATE * -50; // -750.0
        assertEquals(expectedGrossPay, negativeHoursEmployee.calculateGrossPay(1, 2023), "Gross pay calculation for negative hours worked failed.");
    }

    // Test case for both negative hourly rate and negative hours worked
    @Test
    @DisplayName("calculateGrossPay should return a positive value for both negative hourly rate and negative hours worked")
    void calculateGrossPay_bothNegative_returnsPositivePay() {
        PartTimeEmployee bothNegativeEmployee = new PartTimeEmployee(ID, NAME, DEPARTMENT, -10.0, -50, TAX_RATE);
        double expectedGrossPay = (-10.0) * (-50); // 500.0
        assertEquals(expectedGrossPay, bothNegativeEmployee.calculateGrossPay(1, 2023), "Gross pay calculation for both negative values failed.");
    }

    // Test case for a typical positive tax rate
    @Test
    @DisplayName("getTaxRate should return the correct positive tax rate")
    void getTaxRate_positiveRate_returnsCorrectRate() {
        assertEquals(TAX_RATE, employee.getTaxRate(), "Tax rate for positive value failed.");
    }

    // Test case for zero tax rate
    @Test
    @DisplayName("getTaxRate should return zero when tax rate is zero")
    void getTaxRate_zeroRate_returnsZero() {
        PartTimeEmployee zeroTaxEmployee = new PartTimeEmployee(ID, NAME, DEPARTMENT, HOURLY_RATE, HOURS_WORKED, 0.0);
        assertEquals(0.0, zeroTaxEmployee.getTaxRate(), "Tax rate should be zero.");
    }

    // Test case for negative tax rate (as allowed by constructor, though atypical)
    @Test
    @DisplayName("getTaxRate should return the negative tax rate")
    void getTaxRate_negativeRate_returnsNegativeRate() {
        PartTimeEmployee negativeTaxEmployee = new PartTimeEmployee(ID, NAME, DEPARTMENT, HOURLY_RATE, HOURS_WORKED, -0.05);
        assertEquals(-0.05, negativeTaxEmployee.getTaxRate(), "Tax rate for negative value failed.");
    }

    // Test case for verifying the employee type string
    @Test
    @DisplayName("getType should return 'PartTime'")
    void getType_returnsPartTime() {
        assertEquals("PartTime", employee.getType(), "Employee type should be 'PartTime'.");
    }
}