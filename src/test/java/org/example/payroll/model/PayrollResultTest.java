package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayrollResultTest {

    private PayrollResult payrollResult;

    @Mock
    private PayrollLine mockPayrollLine1;
    @Mock
    private PayrollLine mockPayrollLine2;
    @Mock
    private PayrollLine mockPayrollLineZero;

    @BeforeEach
    void setUp() {
        payrollResult = new PayrollResult(1, 2023);
        // MockitoAnnotations.openMocks(this) is handled by @ExtendWith(MockitoExtension.class)
    }

    // Test case for adding a single payroll line with positive values
    @Test
    @DisplayName("Should add a single payroll line and correctly update totals")
    void addLine_singlePositiveLine_updatesTotalsAndList() {
        when(mockPayrollLine1.gross).thenReturn(1000.0);
        when(mockPayrollLine1.tax).thenReturn(100.0);
        when(mockPayrollLine1.net).thenReturn(900.0);

        payrollResult.addLine(mockPayrollLine1);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list size should be 1");
        assertTrue(payrollResult.lines.contains(mockPayrollLine1), "Payroll lines list should contain the added line");
        assertEquals(1000.0, payrollResult.totalGross, "Total gross should be updated correctly");
        assertEquals(100.0, payrollResult.totalTax, "Total tax should be updated correctly");
        assertEquals(900.0, payrollResult.totalNet, "Total net should be updated correctly");
    }

    // Test case for adding multiple payroll lines with positive values
    @Test
    @DisplayName("Should add multiple payroll lines and correctly accumulate totals")
    void addLine_multiplePositiveLines_accumulatesTotalsAndList() {
        // First line
        when(mockPayrollLine1.gross).thenReturn(1000.0);
        when(mockPayrollLine1.tax).thenReturn(100.0);
        when(mockPayrollLine1.net).thenReturn(900.0);

        // Second line
        when(mockPayrollLine2.gross).thenReturn(2000.0);
        when(mockPayrollLine2.tax).thenReturn(250.0);
        when(mockPayrollLine2.net).thenReturn(1750.0);

        payrollResult.addLine(mockPayrollLine1);
        payrollResult.addLine(mockPayrollLine2);

        assertEquals(2, payrollResult.lines.size(), "Payroll lines list size should be 2");
        assertTrue(payrollResult.lines.contains(mockPayrollLine1), "Payroll lines list should contain the first added line");
        assertTrue(payrollResult.lines.contains(mockPayrollLine2), "Payroll lines list should contain the second added line");
        assertEquals(3000.0, payrollResult.totalGross, "Total gross should accumulate correctly");
        assertEquals(350.0, payrollResult.totalTax, "Total tax should accumulate correctly");
        assertEquals(2650.0, payrollResult.totalNet, "Total net should accumulate correctly");
    }

    // Test case for adding a payroll line with zero values
    @Test
    @DisplayName("Should add a payroll line with zero values without changing existing totals")
    void addLine_zeroValueLine_updatesListAndKeepsTotalsSameIfInitial() {
        when(mockPayrollLineZero.gross).thenReturn(0.0);
        when(mockPayrollLineZero.tax).thenReturn(0.0);
        when(mockPayrollLineZero.net).thenReturn(0.0);

        payrollResult.addLine(mockPayrollLineZero);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list size should be 1");
        assertTrue(payrollResult.lines.contains(mockPayrollLineZero), "Payroll lines list should contain the zero value line");
        assertEquals(0.0, payrollResult.totalGross, "Total gross should remain 0.0 with a zero-value line");
        assertEquals(0.0, payrollResult.totalTax, "Total tax should remain 0.0 with a zero-value line");
        assertEquals(0.0, payrollResult.totalNet, "Total net should remain 0.0 with a zero-value line");

        // Add a non-zero line first, then a zero line
        payrollResult = new PayrollResult(1, 2023); // Reset
        when(mockPayrollLine1.gross).thenReturn(500.0);
        when(mockPayrollLine1.tax).thenReturn(50.0);
        when(mockPayrollLine1.net).thenReturn(450.0);
        payrollResult.addLine(mockPayrollLine1);
        payrollResult.addLine(mockPayrollLineZero);

        assertEquals(2, payrollResult.lines.size());
        assertEquals(500.0, payrollResult.totalGross);
        assertEquals(50.0, payrollResult.totalTax);
        assertEquals(450.0, payrollResult.totalNet);
    }

    // Test case for adding a payroll line with negative values (uncommon but valid double arithmetic)
    @Test
    @DisplayName("Should correctly handle adding a payroll line with negative values")
    void addLine_negativeValueLine_updatesTotalsAndList() {
        when(mockPayrollLine1.gross).thenReturn(-100.0);
        when(mockPayrollLine1.tax).thenReturn(-10.0);
        when(mockPayrollLine1.net).thenReturn(-90.0);

        payrollResult.addLine(mockPayrollLine1);

        assertEquals(1, payrollResult.lines.size(), "Payroll lines list size should be 1");
        assertTrue(payrollResult.lines.contains(mockPayrollLine1), "Payroll lines list should contain the added line");
        assertEquals(-100.0, payrollResult.totalGross, "Total gross should be updated correctly with negative value");
        assertEquals(-10.0, payrollResult.totalTax, "Total tax should be updated correctly with negative value");
        assertEquals(-90.0, payrollResult.totalNet, "Total net should be updated correctly with negative value");
    }

    // Test case for adding a null payroll line
    @Test
    @DisplayName("Should throw NullPointerException when adding a null payroll line")
    void addLine_nullLine_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> payrollResult.addLine(null),
                "Adding a null PayrollLine should throw NullPointerException");
    }

    // Test case to ensure initial state is correct before adding lines
    @Test
    @DisplayName("Should have correct initial state before any lines are added")
    void initialState_correctValues() {
        assertEquals(1, payrollResult.month);
        assertEquals(2023, payrollResult.year);
        assertNotNull(payrollResult.lines);
        assertTrue(payrollResult.lines.isEmpty());
        assertEquals(0.0, payrollResult.totalGross);
        assertEquals(0.0, payrollResult.totalTax);
        assertEquals(0.0, payrollResult.totalNet);
    }
}