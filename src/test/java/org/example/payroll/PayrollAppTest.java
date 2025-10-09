package org.example.payroll;

import org.example.payroll.model.Employee;
import org.example.payroll.model.FullTimeEmployee;
import org.example.payroll.model.PartTimeEmployee;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.model.PayrollResult.PayrollLine;
import org.example.payroll.repository.EmployeeRepository;
import org.example.payroll.service.PayrollService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PayrollAppTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    // Mocks for methods that take dependencies as arguments
    @Mock
    private PayrollService mockPayrollService;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Ensure consistent locale for output formatting in tests
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    /**
     * Helper method to set System.in for simulating user input.
     * Each call replaces the current System.in.
     * @param data The string to provide as input.
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    // Test case for exiting the main application loop
    @Test
    void main_shouldExitWhenOption4IsChosen() {
        // Test scenario: User chooses option 4 to exit the application.
        // Provide "4\n" to select the "Exit" option.
        String simulatedInput = "4\n";
        provideInput(simulatedInput);

        // Run the main method
        PayrollApp.main(new String[]{});

        // Verify that the main menu is displayed and the application terminates without further input.
        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Payroll System - Main Menu"));
        // The absence of subsequent menu prints indicates successful exit after '4' is entered.
    }

    // Test case for handling invalid numeric and unknown menu options
    @Test
    void main_shouldHandleInvalidMenuInput() {
        // Test scenario: User enters non-numeric input, then an unknown option (e.g., 5), then exits.
        String simulatedInput = "abc\n5\n4\n"; // Invalid input, Unknown option, then Exit
        provideInput(simulatedInput);

        PayrollApp.main(new String[]{});

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid input. Try again."));
        assertTrue(actualOutput.contains("Unknown option."));
        assertTrue(actualOutput.contains("Payroll System - Main Menu")); // Menu should reappear after invalid input
    }

    // Test case for verifying initial seeded employees are displayed
    @Test
    void main_shouldSeedInitialEmployeesAndAllowListing() {
        // Test scenario: The application starts, and the seeded employees can be listed.
        // Input sequence: "2" (List Employees), then "4" (Exit).
        String simulatedInput = "2\n4\n";
        provideInput(simulatedInput);

        PayrollApp.main(new String[]{});

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Employees:"));
        assertTrue(actualOutput.contains("FT1 | Alice Johnson | Engineering | FULL_TIME"));
        assertTrue(actualOutput.contains("PT1 | Bob Smith | QA | PART_TIME"));
    }

    // Test case for successfully adding a full-time employee via CLI
    @Test
    void addEmployeeCLI_shouldAddFullTimeEmployeeSuccessfully() {
        // Test scenario: Provide valid inputs to add a full-time employee.
        // Inputs: type=1, ID, Name, Department, Monthly Salary, Tax Rate.
        Scanner scanner = new Scanner("1\nFT2\nCharlie Brown\nHR\n6000.0\n0.15\n");
        // Use a real repository to check the actual state after addition.
        EmployeeRepository localRepo = new EmployeeRepository();
        // Add existing employees to simulate the app's initial state
        localRepo.add(new FullTimeEmployee("FT1", "Alice Johnson", "Engineering", 7500.0, 0.20));
        localRepo.add(new PartTimeEmployee("PT1", "Bob Smith", "QA", 25.0, 80, 0.12));

        PayrollApp.addEmployeeCLI(scanner, localRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Employee added."));
        assertEquals(3, localRepo.findAll().size()); // Two seeded + one new
        Employee addedEmployee = localRepo.findById("FT2");
        assertNotNull(addedEmployee);
        assertTrue(addedEmployee instanceof FullTimeEmployee);
        assertEquals("Charlie Brown", addedEmployee.getName());
        assertEquals("HR", addedEmployee.getDepartment());
        assertEquals(6000.0, ((FullTimeEmployee) addedEmployee).getMonthlySalary());
    }

    // Test case for successfully adding a part-time employee via CLI
    @Test
    void addEmployeeCLI_shouldAddPartTimeEmployeeSuccessfully() {
        // Test scenario: Provide valid inputs to add a part-time employee.
        // Inputs: type=2, ID, Name, Department, Hourly Rate, Hours Worked, Tax Rate.
        Scanner scanner = new Scanner("2\nPT2\nDiana Prince\nMarketing\n30.0\n100\n0.10\n");
        EmployeeRepository localRepo = new EmployeeRepository();
        // Add existing employees to simulate the app's initial state
        localRepo.add(new FullTimeEmployee("FT1", "Alice Johnson", "Engineering", 7500.0, 0.20));
        localRepo.add(new PartTimeEmployee("PT1", "Bob Smith", "QA", 25.0, 80, 0.12));

        PayrollApp.addEmployeeCLI(scanner, localRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Employee added."));
        assertEquals(3, localRepo.findAll().size());
        Employee addedEmployee = localRepo.findById("PT2");
        assertNotNull(addedEmployee);
        assertTrue(addedEmployee instanceof PartTimeEmployee);
        assertEquals("Diana Prince", addedEmployee.getName());
        assertEquals("Marketing", addedEmployee.getDepartment());
        assertEquals(30.0, ((PartTimeEmployee) addedEmployee).getHourlyRate());
        assertEquals(100, ((PartTimeEmployee) addedEmployee).getHoursWorked());
    }

    // Test case for handling invalid number format when adding a full-time employee
    @Test
    void addEmployeeCLI_shouldHandleInvalidNumberFormatForFullTime() {
        // Test scenario: Input 'abc' for monthly salary, expecting NumberFormatException.
        Scanner scanner = new Scanner("1\nFT3\nEve Green\nFinance\nabc\n0.18\n");
        EmployeeRepository localRepo = new EmployeeRepository();

        PayrollApp.addEmployeeCLI(scanner, localRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid number format. Employee not added."));
        assertNull(localRepo.findById("FT3")); // Employee should not be added
    }

    // Test case for handling invalid number format when adding a part-time employee
    @Test
    void addEmployeeCLI_shouldHandleInvalidNumberFormatForPartTime() {
        // Test scenario: Input 'def' for hours worked, expecting NumberFormatException.
        Scanner scanner = new Scanner("2\nPT3\nFrank White\nOperations\n20.0\ndef\n0.08\n");
        EmployeeRepository localRepo = new EmployeeRepository();

        PayrollApp.addEmployeeCLI(scanner, localRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid number format. Employee not added."));
        assertNull(localRepo.findById("PT3")); // Employee should not be added
    }

    // Test case for handling other general exceptions during employee addition
    @Test
    void addEmployeeCLI_shouldHandleOtherExceptionsDuringInputParsing() {
        // Test scenario: Provide malformed input (e.g., non-double for tax rate) to trigger a general exception.
        Scanner errorScanner = new Scanner("1\nFTX\nNameX\nDeptX\n6000.0\nmalformedTax\n");
        EmployeeRepository localRepo = new EmployeeRepository();

        PayrollApp.addEmployeeCLI(errorScanner, localRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Error adding employee: ")); // General error message
        assertTrue(actualOutput.contains("For input string: \"malformedTax\"")); // Specific NFE message
        assertNull(localRepo.findById("FTX")); // Employee should not be added
    }

    // Test case for displaying no employees when the repository is empty
    @Test
    void listEmployees_shouldDisplayNoEmployeesWhenRepositoryIsEmpty() {
        // Test scenario: The repository contains no employees.
        EmployeeRepository emptyRepo = new EmployeeRepository();

        PayrollApp.listEmployees(emptyRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Employees:"));
        // Assert that no employee-specific lines (containing " | ") are printed.
        assertFalse(actualOutput.contains(" | "));
    }

    // Test case for displaying a single employee correctly
    @Test
    void listEmployees_shouldDisplayOneEmployeeCorrectly() {
        // Test scenario: The repository contains one full-time employee.
        EmployeeRepository singleRepo = new EmployeeRepository();
        singleRepo.add(new FullTimeEmployee("F1", "Test User F", "IT", 5000.0, 0.10));

        PayrollApp.listEmployees(singleRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Employees:"));
        assertTrue(actualOutput.contains("F1 | Test User F | IT | FULL_TIME"));
    }

    // Test case for displaying multiple employees (full-time and part-time) correctly
    @Test
    void listEmployees_shouldDisplayMultipleEmployeesCorrectly() {
        // Test scenario: The repository contains both full-time and part-time employees.
        EmployeeRepository multipleRepo = new EmployeeRepository();
        multipleRepo.add(new FullTimeEmployee("F1", "Test User F", "IT", 5000.0, 0.10));
        multipleRepo.add(new PartTimeEmployee("P1", "Test User P", "Support", 20.0, 160, 0.05));

        PayrollApp.listEmployees(multipleRepo);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Employees:"));
        assertTrue(actualOutput.contains("F1 | Test User F | IT | FULL_TIME"));
        assertTrue(actualOutput.contains("P1 | Test User P | Support | PART_TIME"));
    }

    // Test case for successfully generating and displaying payroll results
    @Test
    void generatePayrollCLI_shouldDisplayPayrollSuccessfully() {
        // Test scenario: PayrollService returns a valid PayrollResult, which is then displayed.
        // Create mock PayrollLines and a PayrollResult.
        PayrollLine line1 = new PayrollLine("E1", "John Doe", "FULL_TIME", 5000.0, 1000.0, 4000.0);
        PayrollLine line2 = new PayrollLine("E2", "Jane Doe", "PART_TIME", 2000.0, 200.0, 1800.0);
        PayrollResult mockResult = new PayrollResult(List.of(line1, line2), 7000.0, 1200.0, 5800.0);

        // Configure the mock PayrollService to return the mock result for any month/year.
        when(mockPayrollService.generatePayroll(anyInt(), anyInt())).thenReturn(mockResult);

        // Simulate user input for month and year.
        Scanner scanner = new Scanner("10\n2023\n");

        PayrollApp.generatePayrollCLI(scanner, mockPayrollService);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Payroll for 10/2023"));
        assertTrue(actualOutput.contains("ID=E1, Name=John Doe, Type=FULL_TIME, Gross=5000.00, Tax=1000.00, Net=4000.00"));
        assertTrue(actualOutput.contains("ID=E2, Name=Jane Doe, Type=PART_TIME, Gross=2000.00, Tax=200.00, Net=1800.00"));
        assertTrue(actualOutput.contains("Total Gross: 7000.0 | Total Tax: 1200.0 | Total Net: 5800.0"));

        // Verify that the generatePayroll method was called with the correct arguments.
        verify(mockPayrollService).generatePayroll(10, 2023);
    }

    // Test case for handling invalid number format for month input during payroll generation
    @Test
    void generatePayrollCLI_shouldHandleInvalidNumberFormatForMonth() {
        // Test scenario: User inputs non-numeric value for month.
        Scanner scanner = new Scanner("invalid\n2023\n");

        PayrollApp.generatePayrollCLI(scanner, mockPayrollService);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Error generating payroll: For input string: \"invalid\""));
        // Verify that the PayrollService was never called due to input error.
        verifyNoInteractions(mockPayrollService);
    }

    // Test case for handling exceptions thrown by the PayrollService
    @Test
    void generatePayrollCLI_shouldHandleExceptionFromService() {
        // Test scenario: The PayrollService's generatePayroll method throws a RuntimeException.
        when(mockPayrollService.generatePayroll(anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Payroll calculation failed unexpectedly"));

        Scanner scanner = new Scanner("11\n2024\n");

        PayrollApp.generatePayrollCLI(scanner, mockPayrollService);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Error generating payroll: Payroll calculation failed unexpectedly"));
        // Verify that the PayrollService's method was called.
        verify(mockPayrollService).generatePayroll(11, 2024);
    }

    // Test case for ensureNotNull method with a non-null string
    @Test
    void ensureNotNull_shouldDoNothingForNonNullString() {
        // Test scenario: Pass a valid non-null string.
        assertDoesNotThrow(() -> PayrollApp.ensureNotNull("some string"));
    }

    // Test case for ensureNotNull method with a null string
    @Test
    void ensureNotNull_shouldThrowIllegalArgumentExceptionForNullString() {
        // Test scenario: Pass a null string, expecting an IllegalArgumentException.
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> PayrollApp.ensureNotNull(null));
        assertEquals("Null value not allowed", thrown.getMessage());
    }

    // Test for lambda$listEmployees$0
    // The lambda expression `e -> { System.out.println(...) }` within `listEmployees` is a compiler-generated
    // synthetic method. It's not directly callable from tests. Its functionality is to format and print
    // employee details.
    // The tests `listEmployees_shouldDisplayOneEmployeeCorrectly` and `listEmployees_shouldDisplayMultipleEmployeesCorrectly`
    // verify the correctness of the output format produced by this lambda. Therefore, the lambda's
    // behavior is sufficiently covered by the `listEmployees` method's tests.
}