package org.example.payroll;

import org.example.payroll.model.Employee;
import org.example.payroll.model.FullTimeEmployee;
import org.example.payroll.model.PartTimeEmployee;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;
import org.example.payroll.service.PayrollService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayrollAppTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @Mock
    private EmployeeRepository mockEmployeeRepository;
    @Mock
    private PayrollService mockPayrollService;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Ensure Locale is set to US for consistent number formatting in output,
        // mirroring the application's behavior in main().
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    // Helper method to simulate user input for System.in
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    // Test for ensureNotNull method

    // Test case for a non-null string
    @Test
    void ensureNotNull_WithNonNullString_DoesNothing() {
        assertDoesNotThrow(() -> PayrollApp.ensureNotNull("test"));
    }

    // Test case for a null string
    @Test
    void ensureNotNull_WithNullString_ThrowsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> PayrollApp.ensureNotNull(null));
        assertEquals("Null value not allowed", thrown.getMessage());
    }

    // Test for addEmployeeCLI method

    // Test case for adding a FullTime employee successfully
    @Test
    void addEmployeeCLI_AddFullTimeEmployee_Success() {
        String input = "1\nFT101\nJohn Doe\nSales\n5000.0\n0.15\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PayrollApp.addEmployeeCLI(scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).add(any(FullTimeEmployee.class));
        String expectedOutput = "Add Employee:\n" +
                                "Enter type: 1 = FullTime, 2 = PartTime\n" +
                                "Type: ID: Name: Department: Monthly Salary: Tax Rate (e.g., 0.20 for 20%): Employee added.\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for adding a PartTime employee successfully
    @Test
    void addEmployeeCLI_AddPartTimeEmployee_Success() {
        String input = "2\nPT202\nJane Doe\nMarketing\n20.0\n160\n0.10\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PayrollApp.addEmployeeCLI(scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).add(any(PartTimeEmployee.class));
        String expectedOutput = "Add Employee:\n" +
                                "Enter type: 1 = FullTime, 2 = PartTime\n" +
                                "Type: ID: Name: Department: Hourly Rate: Hours Worked (for the month): Tax Rate (e.g., 0.12 for 12%): Employee added.\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for invalid number format when entering salary/rate/hours/tax
    @Test
    void addEmployeeCLI_InvalidNumberFormat_PrintsErrorMessage() {
        String input = "1\nFT101\nJohn Doe\nSales\nABC\n"; // Invalid salary
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PayrollApp.addEmployeeCLI(scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any(Employee.class));
        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid number format. Employee not added.\n"));
        // Ensure no employee was added to the repository
        verifyNoMoreInteractions(mockEmployeeRepository);
    }

    // Test case for a generic exception during employee addition (e.g., if repository fails)
    @Test
    void addEmployeeCLI_RepositoryThrowsException_PrintsErrorMessage() {
        String input = "1\nFT101\nJohn Doe\nSales\n5000.0\n0.15\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        doThrow(new RuntimeException("Repository add failed")).when(mockEmployeeRepository).add(any(Employee.class));

        PayrollApp.addEmployeeCLI(scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).add(any(FullTimeEmployee.class));
        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Error adding employee: Repository add failed\n"));
    }

    // Test for listEmployees method (implicitly covers lambda$listEmployees$0)

    // Test case for listing employees when repository is empty
    @Test
    void listEmployees_EmptyRepository_PrintsHeaderOnly() {
        when(mockEmployeeRepository.findAll()).thenReturn(Collections.emptyList());

        PayrollApp.listEmployees(mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).findAll();
        String expectedOutput = "Employees:\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for listing employees with multiple employees (covers lambda$listEmployees$0 functionality)
    @Test
    void listEmployees_WithEmployees_PrintsEmployeeDetails() {
        Employee ft = new FullTimeEmployee("FT001", "Alice", "HR", 6000.0, 0.20);
        Employee pt = new PartTimeEmployee("PT002", "Bob", "IT", 30.0, 100, 0.10);
        when(mockEmployeeRepository.findAll()).thenReturn(Arrays.asList(ft, pt));

        PayrollApp.listEmployees(mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).findAll();
        String expectedOutput = "Employees:\n" +
                                "FT001 | Alice | HR | FULL_TIME\n" +
                                "PT002 | Bob | IT | PART_TIME\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test for generatePayrollCLI method

    // Test case for successful payroll generation
    @Test
    void generatePayrollCLI_SuccessfulGeneration_PrintsPayrollDetails() throws Exception {
        String input = "6\n2023\n"; // Month, Year
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PayrollResult.Line line1 = new PayrollResult.Line("E001", "Emp1", "FULL_TIME", 5000.0, 1000.0, 4000.0);
        PayrollResult.Line line2 = new PayrollResult.Line("E002", "Emp2", "PART_TIME", 2000.0, 200.0, 1800.0);
        PayrollResult payrollResult = new PayrollResult(Arrays.asList(line1, line2), 7000.0, 1200.0, 5800.0);

        when(mockPayrollService.generatePayroll(6, 2023)).thenReturn(payrollResult);

        PayrollApp.generatePayrollCLI(scanner, mockPayrollService);

        verify(mockPayrollService, times(1)).generatePayroll(6, 2023);
        String expectedOutput = "Month (1-12): Year (e.g., 2025): Payroll for 6/2023\n" +
                                "ID=E001, Name=Emp1, Type=FULL_TIME, Gross=5000.00, Tax=1000.00, Net=4000.00\n" +
                                "ID=E002, Name=Emp2, Type=PART_TIME, Gross=2000.00, Tax=200.00, Net=1800.00\n" +
                                "Total Gross: 7000.0 | Total Tax: 1200.0 | Total Net: 5800.0\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for invalid number format for month/year input
    @Test
    void generatePayrollCLI_InvalidNumberFormat_PrintsErrorMessage() throws Exception {
        String input = "ABC\n2023\n"; // Invalid month
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        PayrollApp.generatePayrollCLI(scanner, mockPayrollService);

        verify(mockPayrollService, never()).generatePayroll(anyInt(), anyInt());
        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Error generating payroll: For input string: \"ABC\"\n"));
    }

    // Test case when PayrollService throws an exception during generation
    @Test
    void generatePayrollCLI_ServiceThrowsException_PrintsErrorMessage() throws Exception {
        String input = "6\n2023\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        when(mockPayrollService.generatePayroll(anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Service failure"));

        PayrollApp.generatePayrollCLI(scanner, mockPayrollService);

        verify(mockPayrollService, times(1)).generatePayroll(6, 2023);
        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Error generating payroll: Service failure\n"));
    }

    // Test for main method

    // Test case for a comprehensive run through the main menu: Add, List, Generate, Exit
    @Test
    void main_AddListGenerateExitSequence_ExecutesCorrectly() {
        // Use MockedConstruction to intercept the creation of EmployeeRepository and PayrollService
        // instances within the main method.
        try (MockedConstruction<EmployeeRepository> mockedRepo = Mockito.mockConstruction(EmployeeRepository.class);
             MockedConstruction<PayrollService> mockedService = Mockito.mockConstruction(PayrollService.class)) {

            // Retrieve the instances created by the main method
            EmployeeRepository repoInstance = mockedRepo.constructed().get(0); // The one created early in main
            PayrollService serviceInstance = mockedService.constructed().get(0); // The one created early in main

            // Mock initial employees added by main's seeding
            Employee seededFt = new FullTimeEmployee("FT1", "Alice Johnson", "Engineering", 7500.0, 0.20);
            Employee seededPt = new PartTimeEmployee("PT1", "Bob Smith", "QA", 25.0, 80, 0.12);
            List<Employee> initialSeededEmployees = Arrays.asList(seededFt, seededPt);

            // Configure mock repo to return initial employees when findAll is called for listing
            when(repoInstance.findAll()).thenReturn(initialSeededEmployees);

            // Configure mock service for generatePayroll call
            PayrollResult.Line payrollLine = new PayrollResult.Line("FT1", "Alice Johnson", "FULL_TIME", 7500.0, 1500.0, 6000.0);
            PayrollResult mockPayrollResult = new PayrollResult(Collections.singletonList(payrollLine), 7500.0, 1500.0, 6000.0);
            when(serviceInstance.generatePayroll(1, 2024)).thenReturn(mockPayrollResult);

            // Simulate user input for the main menu flow
            String input = "1\n" + // Choose Add Employee
                           "1\n" + // FullTime type
                           "FT999\n" + "Test User FT\n" + "Dev\n" + "6000.0\n" + "0.25\n" +
                           "2\n" + // Choose List Employees
                           "3\n" + // Choose Generate Payroll
                           "1\n" + // Month 1
                           "2024\n" + // Year 2024
                           "4\n"; // Choose Exit

            provideInput(input);

            // Execute the main method
            PayrollApp.main(new String[]{});

            // Verify interactions on the mocked instances
            // 2 adds from seeding, 1 add from CLI
            verify(repoInstance, times(3)).add(any(Employee.class));
            // At least once for the initial list (if any), and once for explicit listEmployees call
            verify(repoInstance, atLeastOnce()).findAll();
            verify(serviceInstance, times(1)).generatePayroll(1, 2024);

            String actualOutput = outputStreamCaptor.toString();

            // Assertions to check the flow and content of the output
            assertTrue(actualOutput.contains("Payroll System - Main Menu"));
            assertTrue(actualOutput.contains("Employee added.")); // After adding FT999
            assertTrue(actualOutput.contains("FT1 | Alice Johnson | Engineering | FULL_TIME")); // Seeded employee
            assertTrue(actualOutput.contains("PT1 | Bob Smith | QA | PART_TIME")); // Seeded employee
            assertTrue(actualOutput.contains("FT999 | Test User FT | Dev | FULL_TIME")); // Employee added via CLI
            assertTrue(actualOutput.contains("Payroll for 1/2024"));
            assertTrue(actualOutput.contains("ID=FT1, Name=Alice Johnson, Type=FULL_TIME, Gross=7500.00, Tax=1500.00, Net=6000.00"));
            assertTrue(actualOutput.contains("Total Gross: 7500.0 | Total Tax: 1500.0 | Total Net: 6000.0"));
            assertTrue(actualOutput.contains("Choose an option: ")); // Indicates the loop re-prompted at least once, before exit.
            assertFalse(actualOutput.contains("Unknown option."));
            assertFalse(actualOutput.contains("Invalid input. Try again."));
        }
    }

    // Test case for main menu with invalid non-numeric input
    @Test
    void main_InvalidMenuOptionNonNumeric_PrintsErrorMessageAndContinues() {
        String input = "abc\n4\n"; // Invalid input (non-numeric), then exit
        provideInput(input);

        // Use MockedConstruction to prevent main from failing on real repo/service creation
        try (MockedConstruction<EmployeeRepository> mockedRepo = Mockito.mockConstruction(EmployeeRepository.class);
             MockedConstruction<PayrollService> mockedService = Mockito.mockConstruction(PayrollService.class)) {

            PayrollApp.main(new String[]{});

            String actualOutput = outputStreamCaptor.toString();
            assertTrue(actualOutput.contains("Invalid input. Try again."));
            assertTrue(actualOutput.contains("Choose an option: ")); // Should re-prompt after error
            assertFalse(actualOutput.contains("Unknown option.")); // Should not hit default case for NFE
        }
    }

    // Test case for main menu with unknown option (numeric out of range)
    @Test
    void main_UnknownMenuOptionNumeric_PrintsErrorMessageAndContinues() {
        String input = "99\n4\n"; // Unknown option (numeric out of range), then exit
        provideInput(input);

        try (MockedConstruction<EmployeeRepository> mockedRepo = Mockito.mockConstruction(EmployeeRepository.class);
             MockedConstruction<PayrollService> mockedService = Mockito.mockConstruction(PayrollService.class)) {

            PayrollApp.main(new String[]{});

            String actualOutput = outputStreamCaptor.toString();
            assertTrue(actualOutput.contains("Unknown option."));
            assertTrue(actualOutput.contains("Choose an option: ")); // Should re-prompt after error
            assertFalse(actualOutput.contains("Invalid input.")); // Should not hit NFE for valid integer
        }
    }
}