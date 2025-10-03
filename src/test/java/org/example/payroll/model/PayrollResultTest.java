package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class PayrollResultTest {

    // A simple dummy PayrollLine class for testing purposes, mirroring the structure
    // implied by PayrollResult's addLine method.
    static class PayrollLine {
        public double gross;
        public double tax;
        public double net;

        public PayrollLine(double gross, double tax, double net) {
            this.gross = gross;
            this.tax = tax;
            this.net = net;
        }

        // For assertion equals comparisons, if needed (though direct field comparison is used here)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PayrollLine that = (PayrollLine) o;
            return Double.compare(that.gross, gross) == 0 &&
                    Double.compare(that.tax, tax) == 0 &&
                    Double.compare(that.net, net) == 0;
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(gross, tax, net);
        }
    }

    // Test case for adding a single payroll line with positive values.
    @Test
    void addLine_singlePositiveLine() {
        PayrollResult payrollResult = new PayrollResult(1, 2023);
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);

        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size());
        assertTrue(payrollResult.lines.contains(line1));
        assertEquals(1000.0, payrollResult.totalGross, 0.001);
        assertEquals(100.0, payrollResult.totalTax, 0.001);
        assertEquals(900.0, payrollResult.totalNet, 0.001);
    }

    // Test case for adding multiple payroll lines with positive values.
    @Test
    void addLine_multiplePositiveLines() {
        PayrollResult payrollResult = new PayrollResult(2, 2024);
        PayrollLine line1 = new PayrollLine(2000.0, 200.0, 1800.0);
        PayrollLine line2 = new PayrollLine(500.0, 50.0, 450.0);
        PayrollLine line3 = new PayrollLine(1500.0, 150.0, 1350.0);

        payrollResult.addLine(line1);
        payrollResult.addLine(line2);
        payrollResult.addLine(line3);

        assertEquals(3, payrollResult.lines.size());
        assertTrue(payrollResult.lines.contains(line1));
        assertTrue(payrollResult.lines.contains(line2));
        assertTrue(payrollResult.lines.contains(line3));
        assertEquals(2000.0 + 500.0 + 1500.0, payrollResult.totalGross, 0.001); // 4000.0
        assertEquals(200.0 + 50.0 + 150.0, payrollResult.totalTax, 0.001);     // 400.0
        assertEquals(1800.0 + 450.0 + 1350.0, payrollResult.totalNet, 0.001);   // 3600.0
    }

    // Test case for adding a payroll line with zero values.
    @Test
    void addLine_zeroValueLine() {
        PayrollResult payrollResult = new PayrollResult(3, 2023);
        PayrollLine line1 = new PayrollLine(0.0, 0.0, 0.0);

        payrollResult.addLine(line1);

        assertEquals(1, payrollResult.lines.size());
        assertTrue(payrollResult.lines.contains(line1));
        assertEquals(0.0, payrollResult.totalGross, 0.001);
        assertEquals(0.0, payrollResult.totalTax, 0.001);
        assertEquals(0.0, payrollResult.totalNet, 0.001);
    }

    // Test case for adding a payroll line with mixed positive and negative values.
    // Example: a negative tax could represent a refund or correction.
    @Test
    void addLine_mixedValuesLine() {
        PayrollResult payrollResult = new PayrollResult(4, 2023);
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        PayrollLine line2 = new PayrollLine(500.0, -50.0, 550.0); // Gross 500, Tax -50, Net 550

        payrollResult.addLine(line1);
        payrollResult.addLine(line2);

        assertEquals(2, payrollResult.lines.size());
        assertEquals(1500.0, payrollResult.totalGross, 0.001);
        assertEquals(50.0, payrollResult.totalTax, 0.001);
        assertEquals(1450.0, payrollResult.totalNet, 0.001);
    }

    // Test case for adding a null payroll line, expecting a NullPointerException.
    @Test
    void addLine_nullLine() {
        PayrollResult payrollResult = new PayrollResult(5, 2023);

        assertThrows(NullPointerException.class, () -> {
            payrollResult.addLine(null);
        }, "Adding a null PayrollLine should throw NullPointerException");

        // Verify that no state changes occurred if exception is thrown
        assertEquals(0, payrollResult.lines.size());
        assertEquals(0.0, payrollResult.totalGross, 0.001);
        assertEquals(0.0, payrollResult.totalTax, 0.001);
        assertEquals(0.0, payrollResult.totalNet, 0.001);
    }

    // Test case to ensure initial state after constructor call.
    @Test
    void constructor_initialState() {
        PayrollResult payrollResult = new PayrollResult(6, 2023);

        assertEquals(6, payrollResult.month);
        assertEquals(2023, payrollResult.year);
        assertNotNull(payrollResult.lines);
        assertTrue(payrollResult.lines.isEmpty());
        assertEquals(0.0, payrollResult.totalGross, 0.001);
        assertEquals(0.0, payrollResult.totalTax, 0.001);
        assertEquals(0.0, payrollResult.totalNet, 0.001);
    }
}