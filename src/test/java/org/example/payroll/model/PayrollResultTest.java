package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class PayrollResultTest {

    private PayrollResult payrollResult;

    // Helper class to represent PayrollLine for testing purposes,
    // assuming it has public fields as accessed by PayrollResult.
    private static class PayrollLine {
        public double gross;
        public double tax;
        public double net;

        public PayrollLine(double gross, double tax, double net) {
            this.gross = gross;
            this.tax = tax;
            this.net = net;
        }
    }

    @BeforeEach
    void setUp() {
        payrollResult = new PayrollResult(1, 2023);
    }

    // Test case for adding a single PayrollLine with positive values
    @Test
    void addLine_singlePositiveLine_totalsAndUpdateCorrectly() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size());
        assertEquals(line1, payrollResult.lines.get(0));
        assertEquals(1000.0, payrollResult.totalGross, 0.001);
        assertEquals(100.0, payrollResult.totalTax, 0.001);
        assertEquals(900.0, payrollResult.totalNet, 0.001);
    }

    // Test case for adding multiple PayrollLines with positive values
    @Test
    void addLine_multiplePositiveLines_cumulativeTotalsUpdateCorrectly() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        PayrollLine line2 = new PayrollLine(500.0, 50.0, 450.0);
        PayrollLine line3 = new PayrollLine(200.0, 20.0, 180.0);

        payrollResult.addLine(line1);
        payrollResult.addLine(line2);
        payrollResult.addLine(line3);

        assertEquals(3, payrollResult.lines.size());
        assertEquals(line1, payrollResult.lines.get(0));
        assertEquals(line2, payrollResult.lines.get(1));
        assertEquals(line3, payrollResult.lines.get(2));

        assertEquals(1000.0 + 500.0 + 200.0, payrollResult.totalGross, 0.001);
        assertEquals(100.0 + 50.0 + 20.0, payrollResult.totalTax, 0.001);
        assertEquals(900.0 + 450.0 + 180.0, payrollResult.totalNet, 0.001);
    }

    // Test case for adding a PayrollLine with zero values
    @Test
    void addLine_zeroValueLine_totalsUpdateCorrectly() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line1);

        PayrollLine lineZero = new PayrollLine(0.0, 0.0, 0.0);
        payrollResult.addLine(lineZero);

        assertEquals(2, payrollResult.lines.size());
        assertEquals(lineZero, payrollResult.lines.get(1));
        assertEquals(1000.0, payrollResult.totalGross, 0.001);
        assertEquals(100.0, payrollResult.totalTax, 0.001);
        assertEquals(900.0, payrollResult.totalNet, 0.001);
    }

    // Test case for adding a PayrollLine with negative values (e.g., for adjustments)
    @Test
    void addLine_negativeValueLine_totalsAdjustCorrectly() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line1);

        PayrollLine lineAdjustment = new PayrollLine(-50.0, -5.0, -45.0);
        payrollResult.addLine(lineAdjustment);

        assertEquals(2, payrollResult.lines.size());
        assertEquals(lineAdjustment, payrollResult.lines.get(1));
        assertEquals(1000.0 - 50.0, payrollResult.totalGross, 0.001);
        assertEquals(100.0 - 5.0, payrollResult.totalTax, 0.001);
        assertEquals(900.0 - 45.0, payrollResult.totalNet, 0.001);
    }

    // Test case for adding a null PayrollLine
    @Test
    void addLine_nullLine_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> payrollResult.addLine(null));
    }

    // Test case for initial state before any lines are added
    @Test
    void addLine_initialState_totalsAreZero() {
        assertEquals(0, payrollResult.lines.size());
        assertEquals(0.0, payrollResult.totalGross, 0.001);
        assertEquals(0.0, payrollResult.totalTax, 0.001);
        assertEquals(0.0, payrollResult.totalNet, 0.001);
    }
}