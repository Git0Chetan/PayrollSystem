package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

public class PaymentProcessorTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Set default locale to US to ensure consistent number formatting for output assertions
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    /**
     * Helper method to provide input to System.in.
     *
     * @param data The string to be used as input.
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /**
     * Helper method to get the captured output from System.out.
     *
     * @return The captured output as a trimmed string.
     */
    private String getOutput() {
        return outputStreamCaptor.toString().trim();
    }

    // Test case for zero employees, ensuring correct output for no payroll entries
    @Test
    @DisplayName("main method - processes zero employees correctly, showing zero totals")
    void testMain_ZeroEmployees() {
        String input = "0\n" + // Number of employees
                       "1\n" + // Month
                       "2023\n"; // Year
        provideInput(input);

        PaymentProcessor.main(new String[]{});

        String actualOutput = getOutput();
        assertTrue(actualOutput.contains("Simple Payroll Processor"), "Should print welcome message");
        assertTrue(actualOutput.contains("Enter number of employees:"), "Should ask for number of employees");
        assertTrue(actualOutput.contains("Month (1-12):"), "Should ask for month");
        assertTrue(actualOutput.contains("Year (e.g., 2025):"), "Should ask for year");
        assertTrue(actualOutput.contains("Payroll for 1/2023"), "Should print payroll header");
        assertTrue(actualOutput.contains("Total Gross: 0.00 | Total Tax: 0.00 | Total Net: 0.00"), "Should show zero totals");
    }

    // Test case for one full-time employee, verifying individual payroll line and totals
    @Test
    @DisplayName("main method - processes one full-time employee correctly")
    void testMain_OneFullTimeEmployee() {
        String input = "1\n" + // Number of employees
                       "1\n" + // Type: FullTime
                       "FT001\n" + // ID
                       "John Doe\n" + // Name
                       "HR\n" + // Department
                       "5000.00\n" + // Monthly Salary
                       "0.20\n" + // Tax Rate (20%)
                       "6\n" + // Month
                       "2024\n"; // Year
        provideInput(input);

        PaymentProcessor.main(new String[]{});

        // Expected calculations:
        // Gross = 5000.00
        // Tax = 5000.00 * 0.20 = 1000.00
        // Net = 5000.00 - 1000.00 = 4000.00
        String expectedEmployeeLine = "ID=FT001, Name=John Doe, Type=FullTime, Gross=5000.00, Tax=1000.00, Net=4000.00";
        String expectedTotalLine = "Total Gross: 5000.00 | Total Tax: 1000.00 | Total Net: 4000.00";
        String actualOutput = getOutput();

        assertTrue(actualOutput.contains("Payroll for 6/2024"), "Should print payroll header for correct month/year");
        assertTrue(actualOutput.contains(expectedEmployeeLine), "Should contain correct employee payroll line");
        assertTrue(actualOutput.contains(expectedTotalLine), "Should contain correct total payroll line");
    }

    // Test case for one part-time employee, verifying individual payroll line and totals
    @Test
    @DisplayName("main method - processes one part-time employee correctly")
    void testMain_OnePartTimeEmployee() {
        String input = "1\n" + // Number of employees
                       "2\n" + // Type: PartTime
                       "PT001\n" + // ID
                       "Jane Smith\n" + // Name
                       "Sales\n" + // Department
                       "25.50\n" + // Hourly Rate
                       "160\n" + // Hours Worked
                       "0.10\n" + // Tax Rate (10%)
                       "7\n" + // Month
                       "2024\n"; // Year
        provideInput(input);

        PaymentProcessor.main(new String[]{});

        // Expected calculations:
        // Gross = 25.50 * 160 = 4080.00
        // Tax = 4080.00 * 0.10 = 408.00
        // Net = 4080.00 - 408.00 = 3672.00
        String expectedEmployeeLine = "ID=PT001, Name=Jane Smith, Type=PartTime, Gross=4080.00, Tax=408.00, Net=3672.00";
        String expectedTotalLine = "Total Gross: 4080.00 | Total Tax: 408.00 | Total Net: 3672.00";
        String actualOutput = getOutput();

        assertTrue(actualOutput.contains("Payroll for 7/2024"), "Should print payroll header for correct month/year");
        assertTrue(actualOutput.contains(expectedEmployeeLine), "Should contain correct employee payroll line");
        assertTrue(actualOutput.contains(expectedTotalLine), "Should contain correct total payroll line");
    }

    // Test case for multiple employees of mixed types, verifying all individual lines and aggregate totals
    @Test
    @DisplayName("main method - processes mixed full-time and part-time employees correctly")
    void testMain_MixedEmployees() {
        String input = "2\n" + // Number of employees
                       "1\n" + // Type: FullTime (John Doe)
                       "FT002\n" + // ID
                       "John Doe\n" + // Name
                       "Engineering\n" + // Department
                       "6000.00\n" + // Monthly Salary
                       "0.25\n" + // Tax Rate (25%)
                       "2\n" + // Type: PartTime (Alice Green)
                       "PT002\n" + // ID
                       "Alice Green\n" + // Name
                       "Marketing\n" + // Department
                       "30.00\n" + // Hourly Rate
                       "100\n" + // Hours Worked
                       "0.15\n" + // Tax Rate (15%)
                       "8\n" + // Month
                       "2024\n"; // Year
        provideInput(input);

        PaymentProcessor.main(new String[]{});

        // Calculations for John Doe (FullTime):
        // Gross = 6000.00
        // Tax = 6000.00 * 0.25 = 1500.00
        // Net = 6000.00 - 1500.00 = 4500.00

        // Calculations for Alice Green (PartTime):
        // Gross = 30.00 * 100 = 3000.00
        // Tax = 3000.00 * 0.15 = 450.00
        // Net = 3000.00 - 450.00 = 2550.00

        // Totals:
        // Total Gross = 6000.00 + 3000.00 = 9000.00
        // Total Tax = 1500.00 + 450.00 = 1950.00
        // Total Net = 4500.00 + 2550.00 = 7050.00

        String expectedJohnDoeLine = "ID=FT002, Name=John Doe, Type=FullTime, Gross=6000.00, Tax=1500.00, Net=4500.00";
        String expectedAliceGreenLine = "ID=PT002, Name=Alice Green, Type=PartTime, Gross=3000.00, Tax=450.00, Net=2550.00";
        String expectedTotalLine = "Total Gross: 9000.00 | Total Tax: 1950.00 | Total Net: 7050.00";
        String actualOutput = getOutput();

        assertTrue(actualOutput.contains("Payroll for 8/2024"), "Should print payroll header for correct month/year");
        assertTrue(actualOutput.contains(expectedJohnDoeLine), "Should contain correct John Doe payroll line");
        assertTrue(actualOutput.contains(expectedAliceGreenLine), "Should contain correct Alice Green payroll line");
        assertTrue(actualOutput.contains(expectedTotalLine), "Should contain correct total payroll line");
    }

    // Test case for invalid number of employees input (non-integer), which is handled by the main method's try-catch
    @Test
    @DisplayName("main method - handles invalid number of employees input by defaulting to zero")
    void testMain_InvalidNumberOfEmployeesInput() {
        String input = "not a number\n" + // Invalid number of employees
                       "1\n" + // Month
                       "2023\n"; // Year
        provideInput(input);

        PaymentProcessor.main(new String[]{});

        String actualOutput = getOutput();
        assertTrue(actualOutput.contains("Invalid number, defaulting to 0."), "Should print error message for invalid employee count");
        assertTrue(actualOutput.contains("Total Gross: 0.00 | Total Tax: 0.00 | Total Net: 0.00"), "Should show zero totals as it defaulted to 0 employees");
    }

    // Test case for invalid employee type input (non-integer), expecting a NumberFormatException because it's not handled
    @Test
    @DisplayName("main method - throws NumberFormatException for invalid employee type input")
    void testMain_InvalidEmployeeTypeInput_ThrowsException() {
        String input = "1\n" + // Number of employees
                       "invalid_type\n"; // Invalid type (will cause NumberFormatException)
        provideInput(input);

        assertThrows(NumberFormatException.class, () -> PaymentProcessor.main(new String[]{}),
                "Expected NumberFormatException for non-integer employee type input");
    }

    // Test case for invalid monthly salary input (non-numeric), expecting a NumberFormatException
    @Test
    @DisplayName("main method - throws NumberFormatException for invalid monthly salary input for full-time employee")
    void testMain_InvalidMonthlySalaryInput_ThrowsException() {
        String input = "1\n" + // Number of employees
                       "1\n" + // Type: FullTime
                       "FT003\n" + // ID
                       "Bob\n" + // Name
                       "IT\n" + // Department
                       "not a salary\n"; // Invalid Monthly Salary (will cause NumberFormatException)
        provideInput(input);

        assertThrows(NumberFormatException.class, () -> PaymentProcessor.main(new String[]{}),
                "Expected NumberFormatException for non-numeric monthly salary input");
    }

    // Test case for invalid tax rate input (non-numeric) for a full-time employee, expecting a NumberFormatException
    @Test
    @DisplayName("main method - throws NumberFormatException for invalid full-time tax rate input")
    void testMain_InvalidFullTimeTaxRateInput_ThrowsException() {
        String input = "1\n" + // Number of employees
                       "1\n" + // Type: FullTime
                       "FT004\n" + // ID
                       "Charlie\n" + // Name
                       "Finance\n" + // Department
                       "7000.00\n" + // Monthly Salary
                       "not a tax\n"; // Invalid Tax Rate (will cause NumberFormatException)
        provideInput(input);

        assertThrows(NumberFormatException.class, () -> PaymentProcessor.main(new String[]{}),
                "Expected NumberFormatException for non-numeric full-time tax rate input");
    }

    // Test case for invalid hourly rate input (non-numeric) for a part-time employee, expecting a NumberFormatException
    @Test
    @DisplayName("main method - throws NumberFormatException for invalid hourly rate input for part-time employee")
    void testMain_InvalidHourlyRateInput_ThrowsException() {
        String input = "1\n" + // Number of employees
                       "2\n" + // Type: PartTime
                       "PT003\n" + // ID
                       "Diana\n" + // Name
                       "Support\n" + // Department
                       "not a rate\n"; // Invalid Hourly Rate (will cause NumberFormatException)
        provideInput(input);

        assertThrows(NumberFormatException.class, () -> PaymentProcessor.main(new String[]{}),
                "Expected NumberFormatException for non-numeric hourly rate input");
    }

    // Test case for invalid hours worked input (non-integer) for a part-time employee, expecting a NumberFormatException
    @Test
    @DisplayName("main method - throws NumberFormatException for invalid hours worked input for part-time employee")
    void testMain_InvalidHoursWorkedInput_ThrowsException() {
        String input = "1\n" + // Number of employees
                       "2\n" + // Type: PartTime
                       "PT004\n" + // ID
                       "Eve\n" + // Name
                       "Operations\n" + // Department
                       "20.00\n" + // Hourly Rate
                       "not hours\n"; // Invalid Hours Worked (will cause NumberFormatException)
        provideInput(input);

        assertThrows(NumberFormatException.class, () -> PaymentProcessor.main(new String[]{}),
                "Expected NumberFormatException for non-integer hours worked input");
    }

    // Test case for invalid month input (non-integer), expecting a NumberFormatException
    @Test
    @DisplayName("main method - throws NumberFormatException for invalid month input")
    void testMain_InvalidMonthInput_ThrowsException() {
        String input = "0\n" + // Number of employees
                       "not a month\n"; // Invalid Month (will cause NumberFormatException)
        provideInput(input);

        assertThrows(NumberFormatException.class, () -> PaymentProcessor.main(new String[]{}),
                "Expected NumberFormatException for non-integer month input");
    }

    // Test case for invalid year input (non-integer), expecting a NumberFormatException
    @Test
    @DisplayName("main method - throws NumberFormatException for invalid year input")
    void testMain_InvalidYearInput_ThrowsException() {
        String input = "0\n" + // Number of employees
                       "1\n" + // Valid Month
                       "not a year\n"; // Invalid Year (will cause NumberFormatException)
        provideInput(input);

        assertThrows(NumberFormatException.class, () -> PaymentProcessor.main(new String[]{}),
                "Expected NumberFormatException for non-integer year input");
    }

    // Test case to explicitly verify that Locale.US is used for formatting numbers in the output.
    // This ensures decimal points are always '.' and no thousands separators.
    @Test
    @DisplayName("main method - uses US Locale for number formatting in output (decimal points)")
    void testMain_LocaleFormatting() {
        String input = "1\n" + // Number of employees
                       "1\n" + // Type: FullTime
                       "FT005\n" + // ID
                       "Frank\n" + // Name
                       "Sales\n" + // Department
                       "5500.50\n" + // Monthly Salary (with fractional part)
                       "0.18\n" + // Tax Rate
                       "9\n" + // Month
                       "2024\n"; // Year
        provideInput(input);

        PaymentProcessor.main(new String[]{});

        // Expected calculations:
        // Gross = 5500.50
        // Tax = 5500.50 * 0.18 = 990.09
        // Net = 5500.50 - 990.09 = 4510.41
        String expectedEmployeeLine = "ID=FT005, Name=Frank, Type=FullTime, Gross=5500.50, Tax=990.09, Net=4510.41";
        String expectedTotalLine = "Total Gross: 5500.50 | Total Tax: 990.09 | Total Net: 4510.41";
        String actualOutput = getOutput();

        assertTrue(actualOutput.contains(expectedEmployeeLine), "Should use dot for decimals in employee line as per US locale");
        assertTrue(actualOutput.contains(expectedTotalLine), "Should use dot for decimals in total line as per US locale");
    }

    // Test case for an employee type number that is not 1 (e.g., 99), verifying it's treated as PartTime
    @Test
    @DisplayName("main method - treats employee type other than 1 as PartTime")
    void testMain_InvalidEmployeeTypeNumberDefaultsToPartTime() {
        String input = "1\n" + // Number of employees
                       "99\n" + // Type: Not 1, not 2; current code defaults to PartTime (else branch)
                       "PT005\n" + // ID
                       "Grace\n" + // Name
                       "QA\n" + // Department
                       "35.00\n" + // Hourly Rate
                       "150\n" + // Hours Worked
                       "0.12\n" + // Tax Rate
                       "10\n" + // Month
                       "2024\n"; // Year
        provideInput(input);

        PaymentProcessor.main(new String[]{});

        // Expected calculations (as PartTime):
        // Gross = 35.00 * 150 = 5250.00
        // Tax = 5250.00 * 0.12 = 630.00
        // Net = 5250.00 - 630.00 = 4620.00
        String expectedEmployeeLine = "ID=PT005, Name=Grace, Type=PartTime, Gross=5250.00, Tax=630.00, Net=4620.00";
        String expectedTotalLine = "Total Gross: 5250.00 | Total Tax: 630.00 | Total Net: 4620.00";
        String actualOutput = getOutput();

        assertTrue(actualOutput.contains("Payroll for 10/2024"), "Should print payroll header for correct month/year");
        assertTrue(actualOutput.contains(expectedEmployeeLine), "Employee should be processed as PartTime with correct calculations");
        assertTrue(actualOutput.contains(expectedTotalLine), "Totals should reflect PartTime calculations");
    }
}