package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // Capture System.out to verify console output
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Ensure US locale for consistent number formatting in output
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        // Restore original System.in and System.out after each test
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    // Helper method to simulate user input and execute the main method
    private void provideInputAndRunMain(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        PaymentProcessor.main(new String[]{});
    }

    // Test case for zero employees, ensuring correct initial prompts and an empty payroll
    @Test
    void testMain_ZeroEmployees() {
        String input = """
                0
                1
                2023
                """;
        provideInputAndRunMain(input);

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n");

        Assertions.assertTrue(actualOutput.contains("Simple Payroll Processor (Single-file Java)"));
        Assertions.assertTrue(actualOutput.contains("Enter number of employees: "));
        Assertions.assertTrue(actualOutput.contains("Month (1-12): "));
        Assertions.assertTrue(actualOutput.contains("Year (e.g., 2025): "));
        Assertions.assertTrue(actualOutput.contains("Payroll for 1/2023"));
        Assertions.assertTrue(actualOutput.contains("Total Gross: 0.0 | Total Tax: 0.0 | Total Net: 0.0")); // Note: 0.0 for doubles
    }

    // Test case for one Full-Time employee with valid inputs, verifying calculations and output
    @Test
    void testMain_OneFullTimeEmployee() {
        String input = """
                1
                1
                FT001
                John Doe
                Sales
                5000.00
                0.15
                6
                2024
                """;
        provideInputAndRunMain(input);

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n");

        Assertions.assertTrue(actualOutput.contains("Employee #1"));
        Assertions.assertTrue(actualOutput.contains("ID: FT001"));
        Assertions.assertTrue(actualOutput.contains("Name: John Doe"));
        Assertions.assertTrue(actualOutput.contains("Department: Sales"));
        Assertions.assertTrue(actualOutput.contains("Monthly Salary: "));
        Assertions.assertTrue(actualOutput.contains("Tax Rate (e.g., 0.20 for 20%): "));
        Assertions.assertTrue(actualOutput.contains("Month (1-12): "));
        Assertions.assertTrue(actualOutput.contains("Year (e.g., 2025): "));

        String expectedPayrollLine = "ID=FT001, Name=John Doe, Type=FullTime, Gross=5000.00, Tax=750.00, Net=4250.00";
        Assertions.assertTrue(actualOutput.contains(expectedPayrollLine));
        Assertions.assertTrue(actualOutput.contains("Total Gross: 5000.0 | Total Tax: 750.0 | Total Net: 4250.0"));
    }

    // Test case for one Part-Time employee with valid inputs, verifying calculations and output
    @Test
    void testMain_OnePartTimeEmployee() {
        String input = """
                1
                2
                PT001
                Jane Smith
                Marketing
                25.50
                160
                0.10
                7
                2024
                """;
        provideInputAndRunMain(input);

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n");

        Assertions.assertTrue(actualOutput.contains("Employee #1"));
        Assertions.assertTrue(actualOutput.contains("Type (1=FullTime, 2=PartTime): "));
        Assertions.assertTrue(actualOutput.contains("ID: PT001"));
        Assertions.assertTrue(actualOutput.contains("Name: Jane Smith"));
        Assertions.assertTrue(actualOutput.contains("Department: Marketing"));
        Assertions.assertTrue(actualOutput.contains("Hourly Rate: "));
        Assertions.assertTrue(actualOutput.contains("Hours Worked: "));
        Assertions.assertTrue(actualOutput.contains("Tax Rate (e.g., 0.12 for 12%): "));

        // Gross = 25.50 * 160 = 4080.00
        // Tax = 4080.00 * 0.10 = 408.00
        // Net = 4080.00 - 408.00 = 3672.00
        String expectedPayrollLine = "ID=PT001, Name=Jane Smith, Type=PartTime, Gross=4080.00, Tax=408.00, Net=3672.00";
        Assertions.assertTrue(actualOutput.contains(expectedPayrollLine));
        Assertions.assertTrue(actualOutput.contains("Total Gross: 4080.0 | Total Tax: 408.0 | Total Net: 3672.0"));
    }

    // Test case for a mix of Full-Time and Part-Time employees, verifying individual and total calculations
    @Test
    void testMain_MixedEmployees() {
        String input = """
                2
                1
                FT002
                Alice Brown
                HR
                6000.00
                0.20
                2
                PT002
                Bob White
                Finance
                30.00
                100
                0.12
                8
                2024
                """;
        provideInputAndRunMain(input);

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n");

        // FT002: Gross=6000.00, Tax=1200.00, Net=4800.00
        Assertions.assertTrue(actualOutput.contains("ID=FT002, Name=Alice Brown, Type=FullTime, Gross=6000.00, Tax=1200.00, Net=4800.00"));

        // PT002: Gross=30.00 * 100 = 3000.00, Tax=3000.00 * 0.12 = 360.00, Net=2640.00
        Assertions.assertTrue(actualOutput.contains("ID=PT002, Name=Bob White, Type=PartTime, Gross=3000.00, Tax=360.00, Net=2640.00"));

        // Total Gross = 6000 + 3000 = 9000.0
        // Total Tax = 1200 + 360 = 1560.0
        // Total Net = 4800 + 2640 = 7440.0
        Assertions.assertTrue(actualOutput.contains("Total Gross: 9000.0 | Total Tax: 1560.0 | Total Net: 7440.0"));
    }

    // Test case for invalid number of employees input (non-numeric), expecting the default behavior
    @Test
    void testMain_InvalidNumEmployeesInput_DefaultsToZero() {
        String input = """
                not_a_number
                1
                2023
                """;
        provideInputAndRunMain(input);

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n");
        Assertions.assertTrue(actualOutput.contains("Invalid number, defaulting to 0."));
        Assertions.assertTrue(actualOutput.contains("Payroll for 1/2023"));
        Assertions.assertTrue(actualOutput.contains("Total Gross: 0.0 | Total Tax: 0.0 | Total Net: 0.0"));
    }

    // Test case for empty input for number of employees, expecting the default behavior
    @Test
    void testMain_EmptyNumEmployeesInput_DefaultsToZero() {
        String input = """

                1
                2023
                """; // Empty string for N results in NumberFormatException caught by main, defaulting N to 0.
        provideInputAndRunMain(input);
        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n");
        Assertions.assertTrue(actualOutput.contains("Invalid number, defaulting to 0."));
        Assertions.assertTrue(actualOutput.contains("Payroll for 1/2023"));
        Assertions.assertTrue(actualOutput.contains("Total Gross: 0.0 | Total Tax: 0.0 | Total Net: 0.0"));
    }

    // Test case for invalid employee type input (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidEmployeeTypeInput_ThrowsNumberFormatException() {
        String input = """
                1
                not_a_type
                ID001
                Name
                Dept
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for invalid monthly salary input for Full-Time employee (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidFullTimeSalaryInput_ThrowsNumberFormatException() {
        String input = """
                1
                1
                FT003
                Chris Green
                IT
                invalid_salary
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for invalid hourly rate input for Part-Time employee (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidPartTimeHourlyRateInput_ThrowsNumberFormatException() {
        String input = """
                1
                2
                PT003
                Diana Blue
                HR
                invalid_rate
                100
                0.10
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for invalid hours worked input for Part-Time employee (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidPartTimeHoursInput_ThrowsNumberFormatException() {
        String input = """
                1
                2
                PT004
                Eve Black
                Ops
                20.00
                invalid_hours
                0.10
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for invalid tax rate input for Full-Time employee (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidFullTimeTaxRateInput_ThrowsNumberFormatException() {
        String input = """
                1
                1
                FT004
                Frank White
                Admin
                4000.00
                invalid_tax
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for invalid tax rate input for Part-Time employee (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidPartTimeTaxRateInput_ThrowsNumberFormatException() {
        String input = """
                1
                2
                PT005
                Grace Green
                Support
                15.00
                80
                invalid_tax
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for invalid month input (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidMonthInput_ThrowsNumberFormatException() {
        String input = """
                0
                not_a_month
                2023
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for invalid year input (non-numeric), expecting NumberFormatException
    @Test
    void testMain_InvalidYearInput_ThrowsNumberFormatException() {
        String input = """
                0
                1
                not_a_year
                """;
        
        Assertions.assertThrows(NumberFormatException.class, () -> provideInputAndRunMain(input));
    }

    // Test case for multiple employees with various types, checking overall totals and individual lines
    @Test
    void testMain_MultipleMixedEmployees() {
        String input = """
                3
                1
                FT001
                John Doe
                Sales
                5000.00
                0.15
                2
                PT001
                Jane Smith
                Marketing
                25.00
                160
                0.10
                1
                FT002
                Alice Brown
                HR
                6000.00
                0.20
                6
                2024
                """;
        provideInputAndRunMain(input);

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n");

        // FT001: Gross=5000.00, Tax=750.00, Net=4250.00
        Assertions.assertTrue(actualOutput.contains("ID=FT001, Name=John Doe, Type=FullTime, Gross=5000.00, Tax=750.00, Net=4250.00"));

        // PT001: Gross=25.00 * 160 = 4000.00, Tax=4000.00 * 0.10 = 400.00, Net=3600.00
        Assertions.assertTrue(actualOutput.contains("ID=PT001, Name=Jane Smith, Type=PartTime, Gross=4000.00, Tax=400.00, Net=3600.00"));

        // FT002: Gross=6000.00, Tax=1200.00, Net=4800.00
        Assertions.assertTrue(actualOutput.contains("ID=FT002, Name=Alice Brown, Type=FullTime, Gross=6000.00, Tax=1200.00, Net=4800.00"));

        // Total Gross = 5000 + 4000 + 6000 = 15000.0
        // Total Tax = 750 + 400 + 1200 = 2350.0
        // Total Net = 4250 + 3600 + 4800 = 12650.0
        Assertions.assertTrue(actualOutput.contains("Total Gross: 15000.0 | Total Tax: 2350.0 | Total Net: 12650.0"));
    }
}