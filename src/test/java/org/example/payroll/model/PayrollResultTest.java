package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PayrollResultTest {

    private PayrollResult payrollResult;

    @BeforeEach
    void setUp() {
        payrollResult = new PayrollResult(10, 2023);
    }

    // Test case for adding a single valid PayrollLine and verifying state updates
    @Test
    @DisplayName("Should add a single payroll line and update all totals correctly")
    void addLine_AddsSingleLineAndUpdatesTotals() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list size should be 1");
        assertEquals(line1, payrollResult.lines.get(0), "The added line should be present in the list");
        assertEquals(1000.0, payrollResult.totalGross, 0.001, "totalGross should be updated correctly");
        assertEquals(100.0, payrollResult.totalTax, 0.001, "totalTax should be updated correctly");
        assertEquals(900.0, payrollResult.totalNet, 0.001, "totalNet should be updated correctly");
    }

    // Test case for adding multiple PayrollLine objects and verifying cumulative totals
    @Test
    @DisplayName("Should add multiple payroll lines and accumulate all totals correctly")
    void addLine_AddsMultipleLinesAndAccumulatesTotals() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        PayrollLine line2 = new PayrollLine(2500.0, 300.0, 2200.0);
        PayrollLine line3 = new PayrollLine(500.0, 50.0, 450.0);

        payrollResult.addLine(line1);
        payrollResult.addLine(line2);
        payrollResult.addLine(line3);

        assertEquals(3, payrollResult.lines.size(), "Payroll lines list size should be 3");
        assertEquals(line1, payrollResult.lines.get(0), "First added line should be present");
        assertEquals(line2, payrollResult.lines.get(1), "Second added line should be present");
        assertEquals(line3, payrollResult.lines.get(2), "Third added line should be present");

        assertEquals(1000.0 + 2500.0 + 500.0, payrollResult.totalGross, 0.001, "totalGross should accumulate correctly");
        assertEquals(100.0 + 300.0 + 50.0, payrollResult.totalTax, 0.001, "totalTax should accumulate correctly");
        assertEquals(900.0 + 2200.0 + 450.0, payrollResult.totalNet, 0.001, "totalNet should accumulate correctly");
    }

    // Test case for adding a PayrollLine with zero values
    @Test
    @DisplayName("Should add a payroll line with zero values and update totals correctly")
    void addLine_WithZeroValueLine_UpdatesTotalsCorrectly() {
        PayrollLine line1 = new PayrollLine(0.0, 0.0, 0.0);
        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list size should be 1");
        assertEquals(0.0, payrollResult.totalGross, 0.001, "totalGross should be 0.0");
        assertEquals(0.0, payrollResult.totalTax, 0.001, "totalTax should be 0.0");
        assertEquals(0.0, payrollResult.totalNet, 0.001, "totalNet should be 0.0");
    }

    // Test case for adding a PayrollLine that results in negative totals (e.g., adjustments)
    @Test
    @DisplayName("Should add a payroll line with negative values and update totals correctly")
    void addLine_WithNegativeValues_UpdatesTotalsCorrectly() {
        // First, add a positive line
        payrollResult.addLine(new PayrollLine(1000.0, 100.0, 900.0));

        // Then, add a negative adjustment line
        PayrollLine line2 = new PayrollLine(-200.0, -20.0, -180.0);
        payrollResult.addLine(line2);

        assertEquals(2, payrollResult.lines.size(), "Payroll lines list size should be 2");
        assertEquals(800.0, payrollResult.totalGross, 0.001, "totalGross should reflect negative adjustment");
        assertEquals(80.0, payrollResult.totalTax, 0.001, "totalTax should reflect negative adjustment");
        assertEquals(720.0, payrollResult.totalNet, 0.001, "totalNet should reflect negative adjustment");
    }

    // Test case for attempting to add a null PayrollLine
    @Test
    @DisplayName("Should throw NullPointerException when adding a null payroll line")
    void addLine_WithNullLine_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            payrollResult.addLine(null);
        }, "Adding a null PayrollLine should throw NullPointerException");

        assertEquals(0, payrollResult.lines.size(), "Lines list should remain empty after NullPointerException");
        assertEquals(0.0, payrollResult.totalGross, 0.001, "Totals should remain 0.0 after NullPointerException");
        assertEquals(0.0, payrollResult.totalTax, 0.001, "Totals should remain 0.0 after NullPointerException");
        assertEquals(0.0, payrollResult.totalNet, 0.001, "Totals should remain 0.0 after NullPointerException");
    }

    // A helper PayrollLine class for testing purposes, mimicking the structure inferred by PayrollResult.
    // In a real project, this would be a separate class file.
    static class PayrollLine {
        public double gross;
        public double tax;
        public double net;

        public PayrollLine(double gross, double tax, double net) {
            this.gross = gross;
            this.tax = tax;
            this.net = net;
        }
    }
}