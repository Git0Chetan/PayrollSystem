package org.example.payroll;

import org.example.payroll.model.Employee;
import org.example.payroll.model.FullTimeEmployee;
import org.example.payroll.model.PartTimeEmployee;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;
import org.example.payroll.service.PayrollService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollAppTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @Mock
    private EmployeeRepository mockEmployeeRepository;
    @Mock
    private PayrollService mockPayrollService;

    // Helper for invoking private static methods using reflection
    private Object invokePrivateStaticMethod(String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
        Method method = PayrollApp.class.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true); // Make private method accessible
        try {
            return method.invoke(null, args); // Invoke static method with null object
        } catch (InvocationTargetException e) {
            // Unwrap the real exception thrown by the invoked method
            throw (Exception) e.getTargetException();
        }
    }

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Ensure Locale is set for consistent number formatting in output
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    // Helper to simulate user input
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    // --- Tests for main method ---

    // Test case for exiting the application from the main menu
    @Test
    @DisplayName("main method should exit cleanly when option 4 is selected")
    void main_shouldExitWhenOption4Selected() {
        provideInput("4\n"); // Input: 4 (Exit)

        PayrollApp.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Payroll System - Main Menu"));
        assertTrue(output.endsWith("Choose an option: ")); // The prompt for the first interaction should be the last output before exit
    }

    // Test case for main method handling invalid menu input (non-numeric)
    @Test
    @DisplayName("main method should handle invalid menu input gracefully")
    void main_shouldHandleInvalidMenuInput() {
        provideInput("invalid\n4\n"); // Input: "invalid" (bad option), then "4" (exit)

        PayrollApp.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Invalid input. Try again."));
        assertTrue(output.contains("Payroll System - Main Menu")); // Menu should reappear after invalid input
    }

    // Test case for main method handling unknown menu option (numeric but not 1-4)
    @Test
    @DisplayName("main method should handle unknown menu option gracefully")
    void main_shouldHandleUnknownMenuOption() {
        provideInput("99\n4\n"); // Input: "99" (unknown option), then "4" (exit)

        PayrollApp.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Unknown option."));
        assertTrue(output.contains("Payroll System - Main Menu")); // Menu should reappear after unknown option
    }

    // Test case for main method flow: add an employee, list all employees (including seeded), then exit
    @Test
    @DisplayName("main method should allow adding and listing employees before exiting")
    void main_shouldAddAndListEmployees() {
        String input = "1\n" + // Add Employee
                "1\n" + // FullTime
                "FT_NEW\n" + // ID
                "New Employee\n" + // Name
                "New Department\n" + // Department
                "8000.0\n" + // Monthly Salary
                "0.18\n" + // Tax Rate
                "2\n" + // List Employees
                "4\n"; // Exit

        provideInput(input);

        PayrollApp.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Employee added.")); // Verify employee addition message
        assertTrue(output.contains("Employees:")); // Verify list header
        assertTrue(output.contains("FT1 | Alice Johnson | Engineering | FULL_TIME")); // Verify seeded full-time employee
        assertTrue(output.contains("PT1 | Bob Smith | QA | PART_TIME")); // Verify seeded part-time employee
        assertTrue(output.contains("FT_NEW | New Employee | New Department | FULL_TIME")); // Verify newly added employee
    }

    // Test case for main method flow: generate payroll for seeded employees, then exit
    @Test
    @DisplayName("main method should allow generating payroll for seeded employees before exiting")
    void main_shouldGeneratePayroll() {
        String input = "3\n" + // Generate Payroll
                "1\n" + // Month (e.g., January)
                "2023\n" + // Year
                "4\n"; // Exit

        provideInput(input);

        PayrollApp.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Payroll for 1/2023"));
        // Expected gross for Alice Johnson (7500.0 * 1): 7500.00, Tax (7500.0 * 0.20): 1500.00, Net: 6000.00
        assertTrue(output.contains("ID=FT1, Name=Alice Johnson, Type=FULL_TIME, Gross=7500.00, Tax=1500.00, Net=6000.00"));
        // Expected gross for Bob Smith (25.0 * 80): 2000.00, Tax (2000.0 * 0.12): 240.00, Net: 1760.00
        assertTrue(output.contains("ID=PT1, Name=Bob Smith, Type=PART_TIME, Gross=2000.00, Tax=240.00, Net=1760.00"));
        // Expected Totals
        assertTrue(output.contains("Total Gross: 9500.0 | Total Tax: 1740.0 | Total Net: 7760.0"));
    }


    // --- Tests for addEmployeeCLI method ---

    // Test case for adding a FullTime employee successfully
    @Test
    @DisplayName("addEmployeeCLI should successfully add a FullTime employee")
    void addEmployeeCLI_shouldAddFullTimeEmployee() throws Exception {
        // Simulate input for a FullTime employee
        String input = "1\nFT001\nJohn Doe\nIT\n5000.0\n0.15\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        invokePrivateStaticMethod("addEmployeeCLI", new Class[]{Scanner.class, EmployeeRepository.class}, scanner, mockEmployeeRepository);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(mockEmployeeRepository, times(1)).add(employeeCaptor.capture());

        Employee addedEmployee = employeeCaptor.getValue();
        assertInstanceOf(FullTimeEmployee.class, addedEmployee);
        FullTimeEmployee ftEmployee = (FullTimeEmployee) addedEmployee;
        assertEquals("FT001", ftEmployee.getId());
        assertEquals("John Doe", ftEmployee.getName());
        assertEquals("IT", ftEmployee.getDepartment());
        assertEquals(5000.0, ftEmployee.getMonthlySalary(), 0.001);
        assertEquals(0.15, ftEmployee.getTaxRate(), 0.001);

        assertTrue(outputStreamCaptor.toString().contains("Employee added."));
    }

    // Test case for adding a PartTime employee successfully
    @Test
    @DisplayName("addEmployeeCLI should successfully add a PartTime employee")
    void addEmployeeCLI_shouldAddPartTimeEmployee() throws Exception {
        // Simulate input for a PartTime employee
        String input = "2\nPT001\nJane Doe\nHR\n20.0\n100\n0.10\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        invokePrivateStaticMethod("addEmployeeCLI", new Class[]{Scanner.class, EmployeeRepository.class}, scanner, mockEmployeeRepository);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(mockEmployeeRepository, times(1)).add(employeeCaptor.capture());

        Employee addedEmployee = employeeCaptor.getValue();
        assertInstanceOf(PartTimeEmployee.class, addedEmployee);
        PartTimeEmployee ptEmployee = (PartTimeEmployee) addedEmployee;
        assertEquals("PT001", ptEmployee.getId());
        assertEquals("Jane Doe", ptEmployee.getName());
        assertEquals("HR", ptEmployee.getDepartment());
        assertEquals(20.0, ptEmployee.getHourlyRate(), 0.001);
        assertEquals(100, ptEmployee.getHoursWorked());
        assertEquals(0.10, ptEmployee.getTaxRate(), 0.001);

        assertTrue(outputStreamCaptor.toString().contains("Employee added."));
    }

    // Test case for invalid number format when adding an employee (salary/rate/hours/tax)
    @Test
    @DisplayName("addEmployeeCLI should handle invalid number format gracefully")
    void addEmployeeCLI_shouldHandleInvalidNumberFormat() throws Exception {
        // Simulate input with invalid salary for FullTime employee
        String input = "1\nFT002\nBad Salary\nOps\ninvalid_number\n0.15\n"; // 0.15 won't be read
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        invokePrivateStaticMethod("addEmployeeCLI", new Class[]{Scanner.class, EmployeeRepository.class}, scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any()); // No employee should be added
        assertTrue(outputStreamCaptor.toString().contains("Invalid number format. Employee not added."));
    }

    // Test case for invalid employee type input (non-numeric)
    @Test
    @DisplayName("addEmployeeCLI should handle invalid employee type format")
    void addEmployeeCLI_shouldHandleInvalidTypeFormat() throws Exception {
        // Simulate input with invalid type
        String input = "not_a_number\n"; // No further input will be processed
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        invokePrivateStaticMethod("addEmployeeCLI", new Class[]{Scanner.class, EmployeeRepository.class}, scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any()); // No employee should be added
        assertTrue(outputStreamCaptor.toString().contains("Invalid number format. Employee not added."));
    }

    // Test case for repository throwing a general exception during add
    @Test
    @DisplayName("addEmployeeCLI should catch and report general exceptions from repository")
    void addEmployeeCLI_shouldHandleRepositoryException() throws Exception {
        // Simulate input for a FullTime employee
        String input = "1\nFT003\nError Employee\nDept\n6000.0\n0.20\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        doThrow(new RuntimeException("Repo save error")).when(mockEmployeeRepository).add(any(Employee.class));

        invokePrivateStaticMethod("addEmployeeCLI", new Class[]{Scanner.class, EmployeeRepository.class}, scanner, mockEmployeeRepository);

        assertTrue(outputStreamCaptor.toString().contains("Error adding employee: Repo save error"));
    }


    // --- Tests for listEmployees method ---

    // Test case for listing employees when repository has employees
    @Test
    @DisplayName("listEmployees should print all employees from the repository")
    void listEmployees_shouldPrintEmployees() throws Exception {
        FullTimeEmployee ft = new FullTimeEmployee("FT1", "Alice Johnson", "Engineering", 7500.0, 0.20);
        PartTimeEmployee pt = new PartTimeEmployee("PT1", "Bob Smith", "QA", 25.0, 80, 0.12);
        when(mockEmployeeRepository.findAll()).thenReturn(Arrays.asList(ft, pt));

        invokePrivateStaticMethod("listEmployees", new Class[]{EmployeeRepository.class}, mockEmployeeRepository);

        String expectedOutput = "Employees:" + System.lineSeparator() +
                "FT1 | Alice Johnson | Engineering | FULL_TIME" + System.lineSeparator() +
                "PT1 | Bob Smith | QA | PART_TIME" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for listing employees when repository is empty
    @Test
    @DisplayName("listEmployees should print header and nothing else if repository is empty")
    void listEmployees_shouldPrintNothingIfNoEmployees() throws Exception {
        when(mockEmployeeRepository.findAll()).thenReturn(Collections.emptyList());

        invokePrivateStaticMethod("listEmployees", new Class[]{EmployeeRepository.class}, mockEmployeeRepository);

        String expectedOutput = "Employees:" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }


    // --- Tests for generatePayrollCLI method ---

    // Test case for generating payroll successfully
    @Test
    @DisplayName("generatePayrollCLI should successfully generate and print payroll")
    void generatePayrollCLI_shouldGenerateAndPrintPayroll() throws Exception {
        // Simulate input: Month 6, Year 2023
        String input = "6\n2023\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        PayrollResult.PayrollLine line1 = new PayrollResult.PayrollLine(
                "FT1", "Alice", "FULL_TIME", 7500.0, 1500.0, 6000.0
        );
        PayrollResult.PayrollLine line2 = new PayrollResult.PayrollLine(
                "PT1", "Bob", "PART_TIME", 2000.0, 240.0, 1760.0
        );
        PayrollResult mockResult = new PayrollResult(
                Arrays.asList(line1, line2), 9500.0, 1740.0, 7760.0
        );

        when(mockPayrollService.generatePayroll(6, 2023)).thenReturn(mockResult);

        invokePrivateStaticMethod("generatePayrollCLI", new Class[]{Scanner.class, PayrollService.class}, scanner, mockPayrollService);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Payroll for 6/2023"));
        assertTrue(actualOutput.contains("ID=FT1, Name=Alice, Type=FULL_TIME, Gross=7500.00, Tax=1500.00, Net=6000.00"));
        assertTrue(actualOutput.contains("ID=PT1, Name=Bob, Type=PART_TIME, Gross=2000.00, Tax=240.00, Net=1760.00"));
        assertTrue(actualOutput.contains("Total Gross: 9500.0 | Total Tax: 1740.0 | Total Net: 7760.0"));

        verify(mockPayrollService, times(1)).generatePayroll(6, 2023);
    }

    // Test case for invalid number format in month/year input
    @Test
    @DisplayName("generatePayrollCLI should handle invalid number format for month/year")
    void generatePayrollCLI_shouldHandleInvalidNumberFormat() throws Exception {
        // Simulate input: invalid month, then valid year (year won't be read)
        String input = "invalid_month\n2023\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        invokePrivateStaticMethod("generatePayrollCLI", new Class[]{Scanner.class, PayrollService.class}, scanner, mockPayrollService);

        verify(mockPayrollService, never()).generatePayroll(anyInt(), anyInt()); // PayrollService should not be called
        assertTrue(outputStreamCaptor.toString().contains("Error generating payroll: For input string: \"invalid_month\""));
    }

    // Test case for PayrollService throwing an exception during payroll generation
    @Test
    @DisplayName("generatePayrollCLI should catch and report exceptions from PayrollService")
    void generatePayrollCLI_shouldHandlePayrollServiceException() throws Exception {
        // Simulate input: Month 1, Year 2024
        String input = "1\n2024\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        doThrow(new RuntimeException("Service error")).when(mockPayrollService).generatePayroll(1, 2024);

        invokePrivateStaticMethod("generatePayrollCLI", new Class[]{Scanner.class, PayrollService.class}, scanner, mockPayrollService);

        assertTrue(outputStreamCaptor.toString().contains("Error generating payroll: Service error"));
    }


    // --- Tests for ensureNotNull method ---

    // Test case for ensureNotNull with a non-null string
    @Test
    @DisplayName("ensureNotNull should do nothing for a non-null string")
    void ensureNotNull_shouldPassForNonNullString() {
        String testString = "hello";
        assertDoesNotThrow(() -> invokePrivateStaticMethod("ensureNotNull", new Class[]{String.class}, testString));
    }

    // Test case for ensureNotNull with a null string
    @Test
    @DisplayName("ensureNotNull should throw IllegalArgumentException for a null string")
    void ensureNotNull_shouldThrowExceptionForNullString() {
        String testString = null;
        Throwable thrown = assertThrows(Exception.class, // invokePrivateStaticMethod wraps the real exception
                () -> invokePrivateStaticMethod("ensureNotNull", new Class[]{String.class}, testString));
        assertInstanceOf(IllegalArgumentException.class, thrown);
        assertEquals("Null value not allowed", thrown.getMessage());
    }

    // --- Tests for lambda$listEmployees$0 ---
    // This is a synthetic method generated by the compiler for the lambda expression
    // within the `listEmployees` method. Direct testing of synthetic methods is
    // generally not recommended or meaningfully possible without highly specialized
    // bytecode manipulation. Its behavior (formatting and printing employee details)
    // is implicitly and thoroughly tested by the `listEmployees_shouldPrintEmployees` test,
    // which verifies the output produced by this lambda.
    @Test
    @DisplayName("lambda$listEmployees$0 is implicitly tested by listEmployees method tests")
    void lambda_listEmployees_0_implicitlyTested() throws Exception {
        // This test serves as an acknowledgment that the lambda's functionality
        // is covered by the integration test of the `listEmployees` method.
        // We will call the method that uses this lambda to demonstrate coverage.
        listEmployees_shouldPrintEmployees();
        assertTrue(true, "The lambda's behavior is verified through the listEmployees test.");
    }
}