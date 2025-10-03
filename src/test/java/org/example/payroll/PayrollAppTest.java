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
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollAppTest {

    // Store original System.in and System.out
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;

    // Streams for testing System.in and System.out
    private ByteArrayOutputStream outputStreamCaptor;
    private ByteArrayInputStream inputStreamSimulator;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Ensure default locale is US for consistent number formatting in output.
        // The PayrollApp itself sets Locale.US, so we align with that for testing output.
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn); // Restore original System.in
        System.setOut(originalSystemOut); // Restore original System.out
        // Restore default locale to avoid affecting subsequent tests if any outside this class are run.
        Locale.setDefault(Locale.getDefault());
    }

    // Helper to simulate input for System.in
    private void provideInput(String data) {
        inputStreamSimulator = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStreamSimulator);
    }

    // Helper to get and make accessible private static methods via reflection
    private Method getPrivateStaticMethod(String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = PayrollApp.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    // Test for ensureNotNull method

    // Test case for a non-null string
    @Test
    @DisplayName("ensureNotNull: Should not throw exception for non-null string")
    void ensureNotNull_givenNonNullString_shouldNotThrowException() throws Exception {
        Method ensureNotNullMethod = getPrivateStaticMethod("ensureNotNull", String.class);
        assertDoesNotThrow(() -> ensureNotNullMethod.invoke(null, "someString"));
    }

    // Test case for a null string, expecting IllegalArgumentException
    @Test
    @DisplayName("ensureNotNull: Should throw IllegalArgumentException for null string")
    void ensureNotNull_givenNullString_shouldThrowIllegalArgumentException() throws Exception {
        Method ensureNotNullMethod = getPrivateStaticMethod("ensureNotNull", String.class);
        // We expect InvocationTargetException because reflection wraps the actual exception
        InvocationTargetException thrown = assertThrows(InvocationTargetException.class,
                () -> ensureNotNullMethod.invoke(null, (String) null));
        assertTrue(thrown.getCause() instanceof IllegalArgumentException,
                "Expected cause to be IllegalArgumentException");
        assertEquals("Null value not allowed", thrown.getCause().getMessage());
    }

    // Test for listEmployees method

    @Mock
    private EmployeeRepository employeeRepositoryMock; // Mock for listEmployees

    // Test case for listing employees when the repository is empty
    @Test
    @DisplayName("listEmployees: Should print only header when repository is empty")
    void listEmployees_givenEmptyRepository_shouldPrintOnlyHeader() throws Exception {
        when(employeeRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        Method listEmployeesMethod = getPrivateStaticMethod("listEmployees", EmployeeRepository.class);
        listEmployeesMethod.invoke(null, employeeRepositoryMock);

        String expectedOutput = "Employees:\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        verify(employeeRepositoryMock, times(1)).findAll();
    }

    // Test case for listing employees when the repository contains employees
    @Test
    @DisplayName("listEmployees: Should print employee details when repository has employees")
    void listEmployees_givenEmployeesInRepository_shouldPrintEmployeeDetails() throws Exception {
        FullTimeEmployee ftEmployee = new FullTimeEmployee("FT1", "Alice Johnson", "Engineering", 7500.0, 0.20);
        PartTimeEmployee ptEmployee = new PartTimeEmployee("PT1", "Bob Smith", "QA", 25.0, 80, 0.12);
        List<Employee> employees = Arrays.asList(ftEmployee, ptEmployee);

        when(employeeRepositoryMock.findAll()).thenReturn(employees);

        Method listEmployeesMethod = getPrivateStaticMethod("listEmployees", EmployeeRepository.class);
        listEmployeesMethod.invoke(null, employeeRepositoryMock);

        String expectedOutput = "Employees:\n" +
                                "FT1 | Alice Johnson | Engineering | FULL_TIME\n" +
                                "PT1 | Bob Smith | QA | PART_TIME\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        verify(employeeRepositoryMock, times(1)).findAll();
    }

    // Note on lambda$listEmployees$0:
    // This is a synthetic method generated by the compiler for the lambda expression inside `listEmployees`.
    // It is not intended for direct testing as a standalone method. Its behavior is inherently covered
    // by the comprehensive tests for the `listEmployees` method itself, which assert the final output
    // produced by iterating over the employees and formatting each one using this lambda.
    // Direct testing of synthetic methods is generally fragile and an anti-pattern.

    // Test for addEmployeeCLI method

    @Mock
    private EmployeeRepository addEmployeeCliRepoMock; // Separate mock for addEmployeeCLI clarity

    // Test case for adding a FullTimeEmployee successfully
    @Test
    @DisplayName("addEmployeeCLI: Should successfully add a FullTimeEmployee")
    void addEmployeeCLI_addFullTimeEmployee_shouldCallRepoAddAndPrintSuccess() throws Exception {
        String input = "1\nFT2\nCharlie Brown\nMarketing\n6000.0\n0.15\n"; // type, id, name, dept, salary, tax
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        Method addEmployeeCLIMethod = getPrivateStaticMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.invoke(null, scanner, addEmployeeCliRepoMock);

        String expectedOutput = "Add Employee:\n" +
                                "Enter type: 1 = FullTime, 2 = PartTime\n" +
                                "Type: ID: Name: Department: Monthly Salary: Tax Rate (e.g., 0.20 for 20%): Employee added.\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        
        // Verify that add was called with a FullTimeEmployee and capture its details
        ArgumentCaptor<FullTimeEmployee> employeeCaptor = ArgumentCaptor.forClass(FullTimeEmployee.class);
        verify(addEmployeeCliRepoMock, times(1)).add(employeeCaptor.capture());
        FullTimeEmployee capturedEmployee = employeeCaptor.getValue();
        
        assertEquals("FT2", capturedEmployee.getId());
        assertEquals("Charlie Brown", capturedEmployee.getName());
        assertEquals("Marketing", capturedEmployee.getDepartment()); 
        assertEquals(6000.0, capturedEmployee.getMonthlySalary());
        assertEquals(0.15, capturedEmployee.getTaxRate());
    }

    // Test case for adding a PartTimeEmployee successfully
    @Test
    @DisplayName("addEmployeeCLI: Should successfully add a PartTimeEmployee")
    void addEmployeeCLI_addPartTimeEmployee_shouldCallRepoAddAndPrintSuccess() throws Exception {
        String input = "2\nPT2\nDiana Prince\nHR\n30.5\n100\n0.10\n"; // type, id, name, dept, hourlyRate, hours, tax
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        Method addEmployeeCLIMethod = getPrivateStaticMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.invoke(null, scanner, addEmployeeCliRepoMock);

        String expectedOutput = "Add Employee:\n" +
                                "Enter type: 1 = FullTime, 2 = PartTime\n" +
                                "Type: ID: Name: Department: Hourly Rate: Hours Worked (for the month): Tax Rate (e.g., 0.12 for 12%): Employee added.\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        
        // Verify that add was called with a PartTimeEmployee and capture its details
        ArgumentCaptor<PartTimeEmployee> employeeCaptor = ArgumentCaptor.forClass(PartTimeEmployee.class);
        verify(addEmployeeCliRepoMock, times(1)).add(employeeCaptor.capture());
        PartTimeEmployee capturedEmployee = employeeCaptor.getValue();
        
        assertEquals("PT2", capturedEmployee.getId());
        assertEquals("Diana Prince", capturedEmployee.getName());
        assertEquals("HR", capturedEmployee.getDepartment());
        assertEquals(30.5, capturedEmployee.getHourlyRate());
        assertEquals(100, capturedEmployee.getHoursWorked());
        assertEquals(0.10, capturedEmployee.getTaxRate());
    }

    // Test case for invalid number format during input (e.g., salary/rate/hours/tax)
    @Test
    @DisplayName("addEmployeeCLI: Should print error message for invalid number format input")
    void addEmployeeCLI_givenInvalidNumberInput_shouldPrintErrorMessage() throws Exception {
        String input = "1\nFT3\nEve Adams\nFinance\nINVALID_SALARY\n0.20\n"; // Invalid salary
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        Method addEmployeeCLIMethod = getPrivateStaticMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.invoke(null, scanner, addEmployeeCliRepoMock);

        String expectedOutput = "Add Employee:\n" +
                                "Enter type: 1 = FullTime, 2 = PartTime\n" +
                                "Type: ID: Name: Department: Monthly Salary: Invalid number format. Employee not added.\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        verify(addEmployeeCliRepoMock, never()).add(any(Employee.class)); // No employee should be added
    }

    // Test case for a general exception during employee addition (e.g., repository fails)
    @Test
    @DisplayName("addEmployeeCLI: Should print error message if repository throws exception")
    void addEmployeeCLI_givenRepoThrowsException_shouldPrintErrorMessage() throws Exception {
        String input = "1\nFT4\nFrank Green\nOperations\n5000.0\n0.18\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        doThrow(new RuntimeException("Database error")).when(addEmployeeCliRepoMock).add(any(FullTimeEmployee.class));

        Method addEmployeeCLIMethod = getPrivateStaticMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.invoke(null, scanner, addEmployeeCliRepoMock);

        String expectedOutput = "Add Employee:\n" +
                                "Enter type: 1 = FullTime, 2 = PartTime\n" +
                                "Type: ID: Name: Department: Monthly Salary: Tax Rate (e.g., 0.20 for 20%): Error adding employee: Database error\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        verify(addEmployeeCliRepoMock, times(1)).add(any(FullTimeEmployee.class));
    }

    // Test for generatePayrollCLI method

    @Mock
    private PayrollService payrollServiceMock; // Mock for generatePayrollCLI

    // Test case for successful payroll generation
    @Test
    @DisplayName("generatePayrollCLI: Should successfully generate and print payroll details")
    void generatePayrollCLI_successfulGeneration_shouldPrintPayrollDetails() throws Exception {
        String input = "1\n2023\n"; // month, year
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        PayrollLine line1 = new PayrollLine("FT1", "Alice", "FULL_TIME", 7500.0, 1500.0, 6000.0);
        PayrollLine line2 = new PayrollLine("PT1", "Bob", "PART_TIME", 2000.0, 240.0, 1760.0);
        PayrollResult mockResult = new PayrollResult(Arrays.asList(line1, line2));
        mockResult.totalGross = 9500.0;
        mockResult.totalTax = 1740.0;
        mockResult.totalNet = 7760.0;

        when(payrollServiceMock.generatePayroll(1, 2023)).thenReturn(mockResult);

        Method generatePayrollCLIMethod = getPrivateStaticMethod("generatePayrollCLI", Scanner.class, PayrollService.class);
        generatePayrollCLIMethod.invoke(null, scanner, payrollServiceMock);

        String expectedOutput = "Month (1-12): Year (e.g., 2025): Payroll for 1/2023\n" +
                                String.format(Locale.US, "ID=%s, Name=%s, Type=%s, Gross=%.2f, Tax=%.2f, Net=%.2f\n", "FT1", "Alice", "FULL_TIME", 7500.00, 1500.00, 6000.00) +
                                String.format(Locale.US, "ID=%s, Name=%s, Type=%s, Gross=%.2f, Tax=%.2f, Net=%.2f\n", "PT1", "Bob", "PART_TIME", 2000.00, 240.00, 1760.00) +
                                String.format(Locale.US, "Total Gross: %.1f | Total Tax: %.1f | Total Net: %.1f\n", 9500.0, 1740.0, 7760.0);

        // Normalize line endings and leading/trailing whitespace for comparison
        assertEquals(expectedOutput.trim().replace("\r\n", "\n"), outputStreamCaptor.toString().trim().replace("\r\n", "\n"));
        verify(payrollServiceMock, times(1)).generatePayroll(1, 2023);
    }

    // Test case for invalid number format for month/year input
    @Test
    @DisplayName("generatePayrollCLI: Should print error message for invalid number format input for month/year")
    void generatePayrollCLI_givenInvalidNumberInput_shouldPrintErrorMessage() throws Exception {
        String input = "JANUARY\n2023\n"; // Invalid month
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        Method generatePayrollCLIMethod = getPrivateStaticMethod("generatePayrollCLI", Scanner.class, PayrollService.class);
        generatePayrollCLIMethod.invoke(null, scanner, payrollServiceMock);

        String expectedOutput = "Month (1-12): Year (e.g., 2025): Error generating payroll: For input string: \"JANUARY\"\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        verify(payrollServiceMock, never()).generatePayroll(anyInt(), anyInt()); // Service should not be called
    }

    // Test case for an exception thrown by PayrollService during payroll generation
    @Test
    @DisplayName("generatePayrollCLI: Should print error message if PayrollService throws exception")
    void generatePayrollCLI_givenServiceThrowsException_shouldPrintErrorMessage() throws Exception {
        String input = "3\n2024\n";
        provideInput(input);
        Scanner scanner = new Scanner(System.in);

        when(payrollServiceMock.generatePayroll(3, 2024)).thenThrow(new RuntimeException("Service unavailable"));

        Method generatePayrollCLIMethod = getPrivateStaticMethod("generatePayrollCLI", Scanner.class, PayrollService.class);
        generatePayrollCLIMethod.invoke(null, scanner, payrollServiceMock);

        String expectedOutput = "Month (1-12): Year (e.g., 2025): Error generating payroll: Service unavailable\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        verify(payrollServiceMock, times(1)).generatePayroll(3, 2024);
    }

    // Test for main method
    // This test simulates a full application flow through the main method.
    // Since the main method internally creates its own EmployeeRepository and PayrollService,
    // this acts as an integration test for the CLI user experience with real dependencies.
    // Mocks are NOT used for `EmployeeRepository` or `PayrollService` in this test,
    // as PayrollApp.main constructs them internally.

    // Test case for the full main menu flow: Add, List, Generate, Exit
    @Test
    @DisplayName("main: Should execute full application flow (Add, List, Generate, Exit)")
    void main_fullApplicationFlow_shouldExecuteOptionsCorrectly() {
        // Input sequence for main:
        // 1) Add Employee (option 1)
        //    type: 1 (FullTime)
        //    id: FTM001
        //    name: New FullTime
        //    dept: Development
        //    salary: 8000.0
        //    tax: 0.25
        // 2) List Employees (option 2)
        // 3) Generate Payroll (option 3)
        //    month: 1
        //    year: 2023
        // 4) Exit (option 4)

        String simulatedInput = "1\n" + // Choose "Add Employee"
                                "1\n" + // Type: FullTime
                                "FTM001\n" + // ID
                                "New FullTime\n" + // Name
                                "Development\n" + // Department
                                "8000.0\n" + // Monthly Salary
                                "0.25\n" + // Tax Rate
                                "2\n" + // Choose "List Employees"
                                "3\n" + // Choose "Generate Payroll"
                                "1\n" + // Month
                                "2023\n" + // Year
                                "4\n"; // Choose "Exit"

        provideInput(simulatedInput);

        // Call the main method
        PayrollApp.main(new String[]{});

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n").trim();

        // Verify key interactions and expected output patterns
        assertTrue(actualOutput.contains("Payroll System - Main Menu"));
        assertTrue(actualOutput.contains("Employee added."));

        // Verify that initial seeded employees are listed (FT1, PT1)
        assertTrue(actualOutput.contains("FT1 | Alice Johnson | Engineering | FULL_TIME"));
        assertTrue(actualOutput.contains("PT1 | Bob Smith | QA | PART_TIME"));
        // Verify that the newly added employee is listed (FTM001)
        assertTrue(actualOutput.contains("FTM001 | New FullTime | Development | FULL_TIME"));

        // Verify payroll generation output for 1/2023
        assertTrue(actualOutput.contains("Payroll for 1/2023"));

        // Expected payroll lines based on seeded and added data:
        // 1. FT1 (Alice Johnson): 7500.0 salary, 0.20 tax -> Gross: 7500.0, Tax: 1500.0, Net: 6000.0
        // 2. PT1 (Bob Smith): 25.0 rate, 80 hours, 0.12 tax -> Gross: 2000.0, Tax: 240.0, Net: 1760.0
        // 3. FTM001 (New FullTime): 8000.0 salary, 0.25 tax -> Gross: 8000.0, Tax: 2000.0, Net: 6000.0

        // Total Gross: 7500.0 + 2000.0 + 8000.0 = 17500.0
        // Total Tax: 1500.0 + 240.0 + 2000.0 = 3740.0
        // Total Net: 6000.0 + 1760.0 + 6000.0 = 13760.0

        assertTrue(actualOutput.contains(String.format(Locale.US, "ID=%s, Name=%s, Type=%s, Gross=%.2f, Tax=%.2f, Net=%.2f", "FT1", "Alice Johnson", "FULL_TIME", 7500.00, 1500.00, 6000.00)));
        assertTrue(actualOutput.contains(String.format(Locale.US, "ID=%s, Name=%s, Type=%s, Gross=%.2f, Tax=%.2f, Net=%.2f", "PT1", "Bob Smith", "PART_TIME", 2000.00, 240.00, 1760.00)));
        assertTrue(actualOutput.contains(String.format(Locale.US, "ID=%s, Name=%s, Type=%s, Gross=%.2f, Tax=%.2f, Net=%.2f", "FTM001", "New FullTime", "FULL_TIME", 8000.00, 2000.00, 6000.00)));
        assertTrue(actualOutput.contains(String.format(Locale.US, "Total Gross: %.1f | Total Tax: %.1f | Total Net: %.1f", 17500.0, 3740.0, 13760.0)));

        assertTrue(actualOutput.endsWith("Choose an option: ")); // Indicates the loop exited cleanly after '4'
    }

    // Test case for invalid numeric input for menu option in main loop
    @Test
    @DisplayName("main: Should handle invalid menu option number format gracefully")
    void main_invalidMenuOptionInput_shouldPrintErrorMessageAndContinue() {
        String simulatedInput = "invalid\n" + // Invalid input, causes NumberFormatException
                                "4\n";       // Exit
        provideInput(simulatedInput);

        PayrollApp.main(new String[]{});

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n").trim();
        assertTrue(actualOutput.contains("Invalid input. Try again."));
        assertTrue(actualOutput.contains("Payroll System - Main Menu")); // Should display menu again after error
        // Ensure it did not treat "invalid" as a valid option that defaults to "Unknown option."
        assertFalse(actualOutput.contains("Unknown option."));
    }

    // Test case for an unknown menu option number in main loop
    @Test
    @DisplayName("main: Should handle unknown menu option gracefully")
    void main_unknownMenuOption_shouldPrintErrorMessageAndContinue() {
        String simulatedInput = "99\n" + // Unknown option
                                "4\n";    // Exit
        provideInput(simulatedInput);

        PayrollApp.main(new String[]{});

        String actualOutput = outputStreamCaptor.toString().replace("\r\n", "\n").trim();
        assertTrue(actualOutput.contains("Unknown option."));
        assertTrue(actualOutput.contains("Payroll System - Main Menu")); // Should display menu again after error
    }
}