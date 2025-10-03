package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// A simple stub for PayrollLine, assuming it has public fields for testing.
// In a real scenario, this would be a separate, properly defined class.
class PayrollLine {
    public double gross;
    public double tax;
    public double net;

    public PayrollLine(double gross, double tax, double net) {
        this.gross = gross;
        this.tax = tax;
        this.net = net;
    }
}

public class PayrollResultTest {

    private PayrollResult payrollResult;
    private static final double DELTA = 0.0001; // For double comparisons

    @BeforeEach
    void setUp() {
        payrollResult = new PayrollResult(1, 2023);
    }

    // Test case for adding a single payroll line successfully and verifying totals and list size.
    @Test
    void addLine_singleLine_updatesTotalsAndList() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list should contain one item.");
        assertEquals(1000.0, payrollResult.totalGross, DELTA, "Total gross should be updated correctly.");
        assertEquals(100.0, payrollResult.totalTax, DELTA, "Total tax should be updated correctly.");
        assertEquals(900.0, payrollResult.totalNet, DELTA, "Total net should be updated correctly.");
        assertEquals(line1, payrollResult.lines.get(0), "The added line should be in the list.");
    }

    // Test case for adding multiple payroll lines and verifying cumulative totals and list size.
    @Test
    void addLine_multipleLines_updatesCumulativeTotalsAndList() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        PayrollLine line2 = new PayrollLine(2500.0, 500.0, 2000.0);
        PayrollLine line3 = new PayrollLine(500.0, 50.0, 450.0);

        payrollResult.addLine(line1);
        payrollResult.addLine(line2);
        payrollResult.addLine(line3);

        assertEquals(3, payrollResult.lines.size(), "Payroll lines list should contain three items.");
        assertEquals(4000.0, payrollResult.totalGross, DELTA, "Total gross should be cumulative.");
        assertEquals(650.0, payrollResult.totalTax, DELTA, "Total tax should be cumulative.");
        assertEquals(3350.0, payrollResult.totalNet, DELTA, "Total net should be cumulative.");
        assertTrue(payrollResult.lines.contains(line1), "List should contain line1.");
        assertTrue(payrollResult.lines.contains(line2), "List should contain line2.");
        assertTrue(payrollResult.lines.contains(line3), "List should contain line3.");
    }

    // Test case for adding a payroll line with zero values and verifying totals are updated correctly.
    @Test
    void addLine_lineWithZeroValues_updatesTotalsCorrectly() {
        PayrollLine line1 = new PayrollLine(0.0, 0.0, 0.0);
        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list should contain one item.");
        assertEquals(0.0, payrollResult.totalGross, DELTA, "Total gross should be zero.");
        assertEquals(0.0, payrollResult.totalTax, DELTA, "Total tax should be zero.");
        assertEquals(0.0, payrollResult.totalNet, DELTA, "Total net should be zero.");
    }

    // Test case for adding a payroll line with mixed positive and zero values.
    @Test
    void addLine_lineWithMixedValues_updatesTotalsCorrectly() {
        PayrollLine line1 = new PayrollLine(100.0, 0.0, 100.0);
        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list should contain one item.");
        assertEquals(100.0, payrollResult.totalGross, DELTA, "Total gross should be 100.0.");
        assertEquals(0.0, payrollResult.totalTax, DELTA, "Total tax should be 0.0.");
        assertEquals(100.0, payrollResult.totalNet, DELTA, "Total net should be 100.0.");
    }

    // Test case for adding a payroll line with negative values (e.g., deductions, adjustments).
    @Test
    void addLine_lineWithNegativeValues_updatesTotalsCorrectly() {
        payrollResult.addLine(new PayrollLine(1000.0, 100.0, 900.0)); // Initial positive line

        PayrollLine negativeLine = new PayrollLine(-200.0, -20.0, -180.0);
        payrollResult.addLine(negativeLine);

        assertEquals(2, payrollResult.lines.size(), "Payroll lines list should contain two items.");
        assertEquals(800.0, payrollResult.totalGross, DELTA, "Total gross should be updated with negative value.");
        assertEquals(80.0, payrollResult.totalTax, DELTA, "Total tax should be updated with negative value.");
        assertEquals(720.0, payrollResult.totalNet, DELTA, "Total net should be updated with negative value.");
    }

    // Test case for adding a null PayrollLine, which should result in a NullPointerException.
    @Test
    void addLine_nullLine_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> payrollResult.addLine(null),
                "Adding a null PayrollLine should throw NullPointerException.");
        
        // Verify that state hasn't changed if exception is thrown before any modification
        // In this case, `lines.add(line)` might execute before the exception if line is null,
        // but subsequently accessing `line.gross` will fail.
        // The current implementation attempts to access fields of 'line' directly, so
        // the NPE will occur on `line.gross`. The `lines` list would not have been modified
        // if `lines.add(null)` was the first operation. However, `lines.add(null)` is allowed by ArrayList.
        // It's `line.gross` that causes the NPE.
        
        // Let's ensure the list and totals remain unchanged from the initial state
        assertEquals(0, payrollResult.lines.size(), "Payroll lines list should remain empty after NPE.");
        assertEquals(0.0, payrollResult.totalGross, DELTA, "Total gross should remain zero after NPE.");
        assertEquals(0.0, payrollResult.totalTax, DELTA, "Total tax should remain zero after NPE.");
        assertEquals(0.0, payrollResult.totalNet, DELTA, "Total net should remain zero after NPE.");
    }
}