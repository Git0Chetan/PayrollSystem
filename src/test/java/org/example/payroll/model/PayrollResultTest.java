package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

// Assuming PayrollLine is a simple data class with public double fields: gross, tax, net,
// or a constructor that sets them (e.g., new PayrollLine(gross, tax, net)).
// For the purpose of this test, we'll assume a constructor exists:
// public PayrollLine(double gross, double tax, double net) { this.gross = gross; this.tax = tax; this.net = net; }
// A minimal concrete class definition is needed for compilation if not already available in the project.
// If PayrollLine source is not available, this test will assume it exists and can be instantiated as described.

class PayrollResultTest {

    private PayrollResult payrollResult;
    private static final double DELTA = 0.0001; // For double comparisons

    @BeforeEach
    void setUp() {
        // Initialize a new PayrollResult before each test
        payrollResult = new PayrollResult(1, 2023);
    }

    // Helper class to simulate PayrollLine for testing purposes if not provided by the project.
    // This would typically be a real class in the project, but defined here for self-containment if needed.
    // In a real project, you would import the actual PayrollLine class.
    private static class PayrollLine {
        public double gross;
        public double tax;
        public double net;

        public PayrollLine(double gross, double tax, double net) {
            this.gross = gross;
            this.tax = tax;
            this.net = net;
        }

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


    // Test case for adding a single payroll line and verifying initial state and totals.
    @Test
    void testAddLine_singleEntry_totalsUpdatedCorrectly() {
        // Arrange
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);

        // Act
        payrollResult.addLine(line1);

        // Assert
        Assertions.assertEquals(1, payrollResult.lines.size(), "Payroll lines list should contain one entry.");
        Assertions.assertTrue(payrollResult.lines.contains(line1), "Payroll lines list should contain the added line.");
        Assertions.assertEquals(1000.0, payrollResult.totalGross, DELTA, "Total gross should be updated correctly.");
        Assertions.assertEquals(100.0, payrollResult.totalTax, DELTA, "Total tax should be updated correctly.");
        Assertions.assertEquals(900.0, payrollResult.totalNet, DELTA, "Total net should be updated correctly.");
    }

    // Test case for adding multiple payroll lines and verifying accumulated totals.
    @Test
    void testAddLine_multipleEntries_totalsAccumulatedCorrectly() {
        // Arrange
        PayrollLine line1 = new PayrollLine(1000.0, 100.0, 900.0);
        PayrollLine line2 = new PayrollLine(500.0, 50.0, 450.0);
        PayrollLine line3 = new PayrollLine(2000.0, 300.0, 1700.0);

        // Act
        payrollResult.addLine(line1);
        payrollResult.addLine(line2);
        payrollResult.addLine(line3);

        // Assert
        Assertions.assertEquals(3, payrollResult.lines.size(), "Payroll lines list should contain three entries.");
        Assertions.assertTrue(payrollResult.lines.containsAll(List.of(line1, line2, line3)), "Payroll lines list should contain all added lines.");
        Assertions.assertEquals(3500.0, payrollResult.totalGross, DELTA, "Total gross should be accumulated correctly."); // 1000+500+2000
        Assertions.assertEquals(450.0, payrollResult.totalTax, DELTA, "Total tax should be accumulated correctly.");   // 100+50+300
        Assertions.assertEquals(3050.0, payrollResult.totalNet, DELTA, "Total net should be accumulated correctly.");   // 900+450+1700
    }

    // Test case for adding a payroll line with zero values.
    @Test
    void testAddLine_withZeroValues_totalsRemainUnchanged() {
        // Arrange
        payrollResult.addLine(new PayrollLine(100.0, 10.0, 90.0)); // Add an initial line
        PayrollLine zeroLine = new PayrollLine(0.0, 0.0, 0.0);

        // Act
        payrollResult.addLine(zeroLine);

        // Assert
        Assertions.assertEquals(2, payrollResult.lines.size(), "Payroll lines list should contain two entries.");
        Assertions.assertTrue(payrollResult.lines.contains(zeroLine), "Payroll lines list should contain the zero value line.");
        Assertions.assertEquals(100.0, payrollResult.totalGross, DELTA, "Total gross should remain unchanged with zero line.");
        Assertions.assertEquals(10.0, payrollResult.totalTax, DELTA, "Total tax should remain unchanged with zero line.");
        Assertions.assertEquals(90.0, payrollResult.totalNet, DELTA, "Total net should remain unchanged with zero line.");
    }

    // Test case for adding a payroll line with negative values (e.g., for adjustments or refunds).
    @Test
    void testAddLine_withNegativeValues_totalsAdjustedCorrectly() {
        // Arrange
        payrollResult.addLine(new PayrollLine(1000.0, 100.0, 900.0)); // Initial positive line
        PayrollLine negativeLine = new PayrollLine(-50.0, -5.0, -45.0); // Negative adjustment

        // Act
        payrollResult.addLine(negativeLine);

        // Assert
        Assertions.assertEquals(2, payrollResult.lines.size(), "Payroll lines list should contain two entries.");
        Assertions.assertTrue(payrollResult.lines.contains(negativeLine), "Payroll lines list should contain the negative value line.");
        Assertions.assertEquals(950.0, payrollResult.totalGross, DELTA, "Total gross should be adjusted correctly with negative values."); // 1000 - 50
        Assertions.assertEquals(95.0, payrollResult.totalTax, DELTA, "Total tax should be adjusted correctly with negative values.");     // 100 - 5
        Assertions.assertEquals(855.0, payrollResult.totalNet, DELTA, "Total net should be adjusted correctly with negative values.");     // 900 - 45
    }

    // Test case for adding a null PayrollLine, which should result in a NullPointerException
    // due to accessing fields like `line.gross`.
    @Test
    void testAddLine_nullLine_throwsNullPointerException() {
        // Arrange
        PayrollLine nullLine = null;

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> {
            payrollResult.addLine(nullLine);
        }, "Adding a null PayrollLine should throw NullPointerException.");

        // Verify that no partial updates occurred before the exception (though not strictly necessary for this method)
        Assertions.assertEquals(0, payrollResult.lines.size(), "No lines should be added if NullPointerException occurs.");
        Assertions.assertEquals(0.0, payrollResult.totalGross, DELTA, "Totals should remain 0.0 if NullPointerException occurs.");
        Assertions.assertEquals(0.0, payrollResult.totalTax, DELTA, "Totals should remain 0.0 if NullPointerException occurs.");
        Assertions.assertEquals(0.0, payrollResult.totalNet, DELTA, "Totals should remain 0.0 if NullPointerException occurs.");
    }

    // Test case for adding lines to an initially empty PayrollResult.
    @Test
    void testAddLine_toEmptyResult_initialTotalsSet() {
        // Arrange (payrollResult is already empty from @BeforeEach)
        PayrollLine line1 = new PayrollLine(250.0, 25.0, 225.0);

        // Assert initial state
        Assertions.assertEquals(0, payrollResult.lines.size());
        Assertions.assertEquals(0.0, payrollResult.totalGross, DELTA);
        Assertions.assertEquals(0.0, payrollResult.totalTax, DELTA);
        Assertions.assertEquals(0.0, payrollResult.totalNet, DELTA);

        // Act
        payrollResult.addLine(line1);

        // Assert
        Assertions.assertEquals(1, payrollResult.lines.size(), "Payroll lines list should contain one entry after first add.");
        Assertions.assertEquals(250.0, payrollResult.totalGross, DELTA, "Total gross should be set correctly for the first line.");
        Assertions.assertEquals(25.0, payrollResult.totalTax, DELTA, "Total tax should be set correctly for the first line.");
        Assertions.assertEquals(225.0, payrollResult.totalNet, DELTA, "Total net should be set correctly for the first line.");
    }
}