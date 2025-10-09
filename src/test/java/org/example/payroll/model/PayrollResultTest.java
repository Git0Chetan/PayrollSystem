package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PayrollResultTest {

    // Define a minimal PayrollLine class for testing purposes,
    // as it's a dependency of PayrollResult but not provided in the prompt.
    // It mirrors how PayrollResult accesses its fields directly.
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

    private PayrollResult payrollResult;
    private static final double DELTA = 0.001; // Tolerance for double comparisons

    @BeforeEach
    void setUp() {
        payrollResult = new PayrollResult(1, 2023);
    }

    // Test case for initial state of PayrollResult
    @Test
    @DisplayName("Should initialize with empty lines and zero totals")
    void testInitialState() {
        assertNotNull(payrollResult.lines, "Lines list should not be null");
        assertTrue(payrollResult.lines.isEmpty(), "Lines list should be empty initially");
        assertEquals(0.0, payrollResult.totalGross, DELTA, "Initial total gross should be 0.0");
        assertEquals(0.0, payrollResult.totalTax, DELTA, "Initial total tax should be 0.0");
        assertEquals(0.0, payrollResult.totalNet, DELTA, "Initial total net should be 0.0");
        assertEquals(1, payrollResult.month, "Month should be set correctly");
        assertEquals(2023, payrollResult.year, "Year should be set correctly");
    }

    // Test case for adding a single valid payroll line
    @Test
    @DisplayName("Should correctly add a single payroll line and update totals")
    void testAddLine_singleLine() {
        PayrollLine line = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line);

        assertEquals(1, payrollResult.lines.size(), "Lines list should contain one line");
        assertTrue(payrollResult.lines.contains(line), "Lines list should contain the added line");
        assertEquals(1000.0, payrollResult.totalGross, DELTA, "Total gross should be updated correctly");
        assertEquals(100.0, payrollResult.totalTax, DELTA, "Total tax should be updated correctly");
        assertEquals(900.0, payrollResult.totalNet, DELTA, "Total net should be updated correctly");
    }

    // Test case for adding multiple payroll lines
    @Test
    @DisplayName("Should correctly add multiple payroll lines and accumulate totals")
    void testAddLine_multipleLines() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        PayrollLine line2 = new PayrollLine(2000.0, 250.0, 1750.0);
        PayrollLine line3 = new PayrollLine(500.0, 50.0, 450.0);

        payrollResult.addLine(line1);
        payrollResult.addLine(line2);
        payrollResult.addLine(line3);

        assertEquals(3, payrollResult.lines.size(), "Lines list should contain three lines");
        assertTrue(payrollResult.lines.contains(line1), "Lines list should contain line1");
        assertTrue(payrollResult.lines.contains(line2), "Lines list should contain line2");
        assertTrue(payrollResult.lines.contains(line3), "Lines list should contain line3");

        assertEquals(1000.0 + 2000.0 + 500.0, payrollResult.totalGross, DELTA, "Total gross should be accumulated correctly");
        assertEquals(100.0 + 250.0 + 50.0, payrollResult.totalTax, DELTA, "Total tax should be accumulated correctly");
        assertEquals(900.0 + 1750.0 + 450.0, payrollResult.totalNet, DELTA, "Total net should be accumulated correctly");
    }

    // Test case for adding a payroll line with zero values
    @Test
    @DisplayName("Should correctly handle adding a payroll line with zero values")
    void testAddLine_zeroValues() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line1); // Add an initial line to ensure accumulation works from non-zero state

        PayrollLine lineZero = new PayrollLine(0.0, 0.0, 0.0);
        payrollResult.addLine(lineZero);

        assertEquals(2, payrollResult.lines.size(), "Lines list should contain two lines");
        assertTrue(payrollResult.lines.contains(lineZero), "Lines list should contain the zero-value line");

        assertEquals(1000.0, payrollResult.totalGross, DELTA, "Total gross should remain unchanged after adding zero-value line");
        assertEquals(100.0, payrollResult.totalTax, DELTA, "Total tax should remain unchanged after adding zero-value line");
        assertEquals(900.0, payrollResult.totalNet, DELTA, "Total net should remain unchanged after adding zero-value line");
    }

    // Test case for adding a payroll line with negative values
    // This tests the behavior for potentially erroneous or credit entries.
    @Test
    @DisplayName("Should correctly handle adding a payroll line with negative values")
    void testAddLine_negativeValues() {
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        payrollResult.addLine(line1);

        // Simulate a correction or negative entry
        PayrollLine lineNegative = new PayrollLine(-200.0, -20.0, -180.0);
        payrollResult.addLine(lineNegative);

        assertEquals(2, payrollResult.lines.size(), "Lines list should contain two lines");
        assertTrue(payrollResult.lines.contains(lineNegative), "Lines list should contain the negative-value line");

        assertEquals(1000.0 - 200.0, payrollResult.totalGross, DELTA, "Total gross should be updated with negative value");
        assertEquals(100.0 - 20.0, payrollResult.totalTax, DELTA, "Total tax should be updated with negative value");
        assertEquals(900.0 - 180.0, payrollResult.totalNet, DELTA, "Total net should be updated with negative value");
    }

    // Test case for adding a null payroll line (if the method doesn't explicitly handle it)
    @Test
    @DisplayName("Should throw NullPointerException when adding a null payroll line")
    void testAddLine_nullLine() {
        // According to the source code, `lines.add(line)` will accept null,
        // but `line.gross` etc. will cause NullPointerException.
        assertThrows(NullPointerException.class, () -> payrollResult.addLine(null),
                "Adding a null line should throw NullPointerException when accessing its fields");

        assertEquals(0, payrollResult.lines.size(), "Lines list should remain unchanged after NullPointerException");
        assertEquals(0.0, payrollResult.totalGross, DELTA, "Totals should remain unchanged after NullPointerException");
    }
}