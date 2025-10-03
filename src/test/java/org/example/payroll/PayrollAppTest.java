package org.example.payroll;

import org.example.payroll.model.FullTimeEmployee;
import org.example.payroll.model.PartTimeEmployee;
import org.example.payroll.model.Employee;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.model.PayrollResult.PayrollLine;
import org.example.payroll.repository.EmployeeRepository;
import org.example.payroll.service.PayrollService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollAppTest {

    // Store original System.in and System.out
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;

    // Output stream to capture System.out
    private ByteArrayOutputStream outputStreamCaptor;

    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @Mock
    private PayrollService mockPayrollService;

    private Scanner scanner; // Scanner for testing CLI methods

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // The PayrollApp's main method sets Locale.US, but ensuring it for all tests can prevent locale-specific issues.
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        if (scanner != null) {
            scanner.close();
        }
        // Reset default locale in case any test changed it, or for consistency
        Locale.setDefault(Locale.getDefault()); // Or a specific default if needed
    }

    // Helper method to simulate user input
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        // Ensure the scanner is created *after* System.in is redirected
        scanner = new Scanner(System.in);
    }

    // Helper method to retrieve captured output
    private String getOutput() {
        return outputStreamCaptor.toString().trim();
    }

    // --- Tests for main method ---
    // The main method is static and creates its own dependencies (EmployeeRepository, PayrollService).
    // For these tests, we let main use its real internal dependencies to test the overall CLI flow.
    // More isolated unit tests for CLI sub-features are handled by calling the private methods directly with mocks.

    // Test case for exiting the application immediately.
    @Test
    @DisplayName("main method should exit cleanly when option 4 is chosen")
    void main_exitApplication() {
        provideInput("4\n"); // Choose option 4 to exit

        PayrollApp.main(new String[]{});

        String output = getOutput();
        assertTrue(output.contains("Payroll System - Main Menu"), "Should display main menu");
        assertTrue(output.contains("Choose an option:"), "Should prompt for option");
        // No explicit exit message, just loop termination. The absence of further output implies exit.
    }

    // Test case for an invalid menu option.
    @Test
    @DisplayName("main method should handle invalid menu option and prompt again")
    void main_invalidOption() {
        provideInput("99\n4\n"); // Invalid option, then exit

        PayrollApp.main(new String[]{});

        String output = getOutput();
        assertTrue(output.contains("Unknown option."), "Should show unknown option message");
        // Ensure it prompts for option again after invalid input
        long mainMenuPrompts = Arrays.stream(output.split("\n"))
                                     .filter(line -> line.contains("Payroll System - Main Menu"))
                                     .count();
        assertTrue(mainMenuPrompts >= 2, "Should display main menu at least twice (after invalid input and before exit)");
    }

    // Test case for a non-numeric menu option.
    @Test
    @DisplayName("main method should handle non-numeric menu option and prompt again")
    void main_nonNumericOption() {
        provideInput("abc\n4\n"); // Non-numeric input, then exit

        PayrollApp.main(new String[]{});

        String output = getOutput();
        assertTrue(output.contains("Invalid input. Try again."), "Should show invalid input message");
        // Ensure it prompts for option again after invalid input
        long mainMenuPrompts = Arrays.stream(output.split("\n"))
                                     .filter(line -> line.contains("Payroll System - Main Menu"))
                                     .count();
        assertTrue(mainMenuPrompts >= 2, "Should display main menu at least twice (after invalid input and before exit)");
    }

    // Test case for adding an employee and then exiting.
    @Test
    @DisplayName("main method should allow adding a full-time employee and then exiting")
    void main_addFullTimeEmployeeThenExit() {
        String input = "1\n" + // Choose Add Employee
                       "1\n" + // FullTime
                       "FT001\n" +
                       "Test Alice\n" +
                       "IT\n" +
                       "8000.0\n" +
                       "0.25\n" +
                       "4\n"; // Exit
        provideInput(input);

        PayrollApp.main(new String[]{});

        String output = getOutput();
        assertTrue(output.contains("Add Employee:"), "Should show Add Employee menu");
        assertTrue(output.contains("Employee added."), "Should confirm employee added");
        assertTrue(output.contains("Payroll System - Main Menu"), "Should return to main menu");
    }

    // Test case for adding a part-time employee and then exiting.
    @Test
    @DisplayName("main method should allow adding a part-time employee and then exiting")
    void main_addPartTimeEmployeeThenExit() {
        String input = "1\n" + // Choose Add Employee
                       "2\n" + // PartTime
                       "PT002\n" +
                       "Test Bob\n" +
                       "Support\n" +
                       "30.0\n" +
                       "150\n" +
                       "0.10\n" +
                       "4\n"; // Exit
        provideInput(input);

        PayrollApp.main(new String[]{});

        String output = getOutput();
        assertTrue(output.contains("Add Employee:"), "Should show Add Employee menu");
        assertTrue(output.contains("Employee added."), "Should confirm employee added");
        assertTrue(output.contains("Payroll System - Main Menu"), "Should return to main menu");
    }

    // --- Tests for addEmployeeCLI method ---

    // Test case for adding a full-time employee successfully.
    @Test
    @DisplayName("addEmployeeCLI should add a full-time employee successfully")
    void addEmployeeCLI_addFullTimeEmployee_success() throws Exception {
        // Use reflection to access the private static method
        Method addEmployeeCLIMethod = PayrollApp.class.getDeclaredMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.setAccessible(true);

        String input = "1\n" + // Type: FullTime
                       "FT001\n" +
                       "John Doe\n" +
                       "Development\n" +
                       "5000.0\n" +
                       "0.15\n";
        provideInput(input);

        addEmployeeCLIMethod.invoke(null, scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).add(any(FullTimeEmployee.class));
        String output = getOutput();
        assertTrue(output.contains("Employee added."));
        assertTrue(output.contains("Add Employee:"));
    }

    // Test case for adding a part-time employee successfully.
    @Test
    @DisplayName("addEmployeeCLI should add a part-time employee successfully")
    void addEmployeeCLI_addPartTimeEmployee_success() throws Exception {
        Method addEmployeeCLIMethod = PayrollApp.class.getDeclaredMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.setAccessible(true);

        String input = "2\n" + // Type: PartTime
                       "PT001\n" +
                       "Jane Smith\n" +
                       "Support\n" +
                       "20.50\n" +
                       "160\n" +
                       "0.10\n";
        provideInput(input);

        addEmployeeCLIMethod.invoke(null, scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).add(any(PartTimeEmployee.class));
        String output = getOutput();
        assertTrue(output.contains("Employee added."));
        assertTrue(output.contains("Add Employee:"));
    }

    // Test case for invalid number format when adding full-time employee.
    @Test
    @DisplayName("addEmployeeCLI should handle invalid number format for full-time employee salary")
    void addEmployeeCLI_invalidNumberFormat_fullTimeSalary() throws Exception {
        Method addEmployeeCLIMethod = PayrollApp.class.getDeclaredMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.setAccessible(true);

        String input = "1\n" + // Type: FullTime
                       "FT002\n" +
                       "Invalid Salary Emp\n" +
                       "HR\n" +
                       "abc\n"; // Invalid salary input
        provideInput(input);

        addEmployeeCLIMethod.invoke(null, scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any(Employee.class));
        String output = getOutput();
        assertTrue(output.contains("Invalid number format. Employee not added."));
    }

    // Test case for invalid number format when adding part-time employee hours.
    @Test
    @DisplayName("addEmployeeCLI should handle invalid number format for part-time employee hours")
    void addEmployeeCLI_invalidNumberFormat_partTimeHours() throws Exception {
        Method addEmployeeCLIMethod = PayrollApp.class.getDeclaredMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.setAccessible(true);

        String input = "2\n" + // Type: PartTime
                       "PT002\n" +
                       "Invalid Hours Emp\n" +
                       "Sales\n" +
                       "20.0\n" +
                       "xyz\n"; // Invalid hours input
        provideInput(input);

        addEmployeeCLIMethod.invoke(null, scanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any(Employee.class));
        String output = getOutput();
        assertTrue(output.contains("Invalid number format. Employee not added."));
    }

    // Test case for general exception during addEmployeeCLI, simulated by the repository.
    @Test
    @DisplayName("addEmployeeCLI should handle general exceptions gracefully")
    void addEmployeeCLI_generalException() throws Exception {
        Method addEmployeeCLIMethod = PayrollApp.class.getDeclaredMethod("addEmployeeCLI", Scanner.class, EmployeeRepository.class);
        addEmployeeCLIMethod.setAccessible(true);

        String input = "1\n" + // Type: FullTime
                       "FT003\n" +
                       "Error Employee\n" +
                       "IT\n" +
                       "6000.0\n" +
                       "0.20\n";
        provideInput(input);

        // Simulate an exception when the repository's add method is called
        doThrow(new RuntimeException("Simulated repo error")).when(mockEmployeeRepository).add(any(FullTimeEmployee.class));

        addEmployeeCLIMethod.invoke(null, scanner, mockEmployeeRepository);

        String output = getOutput();
        assertTrue(output.contains("Error adding employee: Simulated repo error"));
    }

    // --- Tests for listEmployees method ---

    // Test case for listing employees when the repository is empty.
    @Test
    @DisplayName("listEmployees should display 'Employees:' and nothing else if repository is empty")
    void listEmployees_emptyRepository() throws Exception {
        Method listEmployeesMethod = PayrollApp.class.getDeclaredMethod("listEmployees", EmployeeRepository.class);
        listEmployeesMethod.setAccessible(true);

        when(mockEmployeeRepository.findAll()).thenReturn(Collections.emptyList());

        listEmployeesMethod.invoke(null, mockEmployeeRepository);

        String output = getOutput();
        assertTrue(output.contains("Employees:"));
        assertFalse(output.contains("|"), "No employee details should be printed if repository is empty");
        verify(mockEmployeeRepository, times(1)).findAll();
    }

    // Test case for listing employees with multiple full-time and part-time employees.
    @Test
    @DisplayName("listEmployees should display all employees with correct format")
    void listEmployees_withEmployees() throws Exception {
        Method listEmployeesMethod = PayrollApp.class.getDeclaredMethod("listEmployees", EmployeeRepository.class);
        listEmployeesMethod.setAccessible(true);

        FullTimeEmployee ft = new FullTimeEmployee("FT001", "Alice", "HR", 5000.0, 0.15);
        PartTimeEmployee pt = new PartTimeEmployee("PT001", "Bob", "Sales", 25.0, 100, 0.10);
        when(mockEmployeeRepository.findAll()).thenReturn(Arrays.asList(ft, pt));

        listEmployeesMethod.invoke(null, mockEmployeeRepository);

        String output = getOutput();
        assertTrue(output.contains("Employees:"));
        assertTrue(output.contains("FT001 | Alice | HR | FULL_TIME"));
        assertTrue(output.contains("PT001 | Bob | Sales | PART_TIME"));
        verify(mockEmployeeRepository, times(1)).findAll();
    }

    // --- Tests for generatePayrollCLI method ---

    // Test case for successful payroll generation.
    @Test
    @DisplayName("generatePayrollCLI should generate and display payroll successfully")
    void generatePayrollCLI_success() throws Exception {
        Method generatePayrollCLIMethod = PayrollApp.class.getDeclaredMethod("generatePayrollCLI", Scanner.class, PayrollService.class);
        generatePayrollCLIMethod.setAccessible(true);

        String input = "1\n" + // Month
                       "2023\n"; // Year
        provideInput(input);

        PayrollResult.PayrollLine line1 = new PayrollResult.PayrollLine("FT001", "Alice", "FULL_TIME", 5000.0, 1000.0, 4000.0);
        PayrollResult.PayrollLine line2 = new PayrollResult.PayrollLine("PT001", "Bob", "PART_TIME", 2000.0, 200.0, 1800.0);
        PayrollResult payrollResult = new PayrollResult(Arrays.asList(line1, line2), 7000.0, 1200.0, 5800.0);

        when(mockPayrollService.generatePayroll(1, 2023)).thenReturn(payrollResult);

        generatePayrollCLIMethod.invoke(null, scanner, mockPayrollService);

        String output = getOutput();
        assertTrue(output.contains("Payroll for 1/2023"));
        assertTrue(output.contains("ID=FT001, Name=Alice, Type=FULL_TIME, Gross=5000.00, Tax=1000.00, Net=4000.00"));
        assertTrue(output.contains("ID=PT001, Name=Bob, Type=PART_TIME, Gross=2000.00, Tax=200.00, Net=1800.00"));
        assertTrue(output.contains("Total Gross: 7000.0 | Total Tax: 1200.0 | Total Net: 5800.0"));

        verify(mockPayrollService, times(1)).generatePayroll(1, 2023);
    }

    // Test case for invalid number format for month in generatePayrollCLI.
    @Test
    @DisplayName("generatePayrollCLI should handle invalid number format for month")
    void generatePayrollCLI_invalidNumberFormat_month() throws Exception {
        Method generatePayrollCLIMethod = PayrollApp.class.getDeclaredMethod("generatePayrollCLI", Scanner.class, PayrollService.class);
        generatePayrollCLIMethod.setAccessible(true);

        String input = "abc\n" + // Invalid month
                       "2023\n"; // This line won't be fully read due to exception
        provideInput(input);

        generatePayrollCLIMethod.invoke(null, scanner, mockPayrollService);

        String output = getOutput();
        assertTrue(output.contains("Error generating payroll: For input string: \"abc\""));
        verify(mockPayrollService, never()).generatePayroll(anyInt(), anyInt());
    }

    // Test case for invalid number format for year in generatePayrollCLI.
    @Test
    @DisplayName("generatePayrollCLI should handle invalid number format for year")
    void generatePayrollCLI_invalidNumberFormat_year() throws Exception {
        Method generatePayrollCLIMethod = PayrollApp.class.getDeclaredMethod("generatePayrollCLI", Scanner.class, PayrollService.class);
        generatePayrollCLIMethod.setAccessible(true);

        String input = "1\n" + // Valid month
                       "year_invalid\n"; // Invalid year
        provideInput(input);

        generatePayrollCLIMethod.invoke(null, scanner, mockPayrollService);

        String output = getOutput();
        assertTrue(output.contains("Error generating payroll: For input string: \"year_invalid\""));
        verify(mockPayrollService, never()).generatePayroll(anyInt(), anyInt());
    }

    // Test case for an exception during payroll generation from PayrollService.
    @Test
    @DisplayName("generatePayrollCLI should handle exceptions from PayrollService gracefully")
    void generatePayrollCLI_serviceException() throws Exception {
        Method generatePayrollCLIMethod = PayrollApp.class.getDeclaredMethod("generatePayrollCLI", Scanner.class, PayrollService.class);
        generatePayrollCLIMethod.setAccessible(true);

        String input = "1\n" + // Month
                       "2023\n"; // Year
        provideInput(input);

        when(mockPayrollService.generatePayroll(1, 2023)).thenThrow(new RuntimeException("Payroll calculation error"));

        generatePayrollCLIMethod.invoke(null, scanner, mockPayrollService);

        String output = getOutput();
        assertTrue(output.contains("Error generating payroll: Payroll calculation error"));
        verify(mockPayrollService, times(1)).generatePayroll(1, 2023);
    }

    // --- Tests for ensureNotNull method ---

    // Test case for ensureNotNull with a non-null string.
    @Test
    @DisplayName("ensureNotNull should do nothing for a non-null string")
    void ensureNotNull_nonNullString() throws Exception {
        // Access the private static method using reflection
        Method ensureNotNullMethod = PayrollApp.class.getDeclaredMethod("ensureNotNull", String.class);
        ensureNotNullMethod.setAccessible(true);

        assertDoesNotThrow(() -> ensureNotNullMethod.invoke(null, "some string"));
    }

    // Test case for ensureNotNull with a null string.
    @Test
    @DisplayName("ensureNotNull should throw IllegalArgumentException for a null string")
    void ensureNotNull_nullString() throws Exception {
        Method ensureNotNullMethod = PayrollApp.class.getDeclaredMethod("ensureNotNull", String.class);
        ensureNotNullMethod.setAccessible(true);

        // When invoking a private method via reflection, exceptions thrown by the method
        // are wrapped in an InvocationTargetException.
        Exception exception = assertThrows(java.lang.reflect.InvocationTargetException.class,
                () -> ensureNotNullMethod.invoke(null, (String) null));

        Throwable cause = exception.getCause();
        assertNotNull(cause, "InvocationTargetException should have a cause");
        assertTrue(cause instanceof IllegalArgumentException, "Cause should be IllegalArgumentException");
        assertEquals("Null value not allowed", cause.getMessage(), "Exception message should match");
    }

    // --- Testing lambda$listEmployees$0 ---
    // The method `lambda$listEmployees$0` is a private synthetic method generated by the compiler
    // for the lambda expression used within `listEmployees`.
    // It is an internal implementation detail and not meant for direct testing.
    // Its functionality is implicitly tested and verified through the behavior of the `listEmployees` method.
    // The `listEmployees_withEmployees` test already verifies the correct formatting and output of
    // employee details, which is the direct responsibility of this lambda.
    // Therefore, no separate, direct test method is provided for `lambda$listEmployees$0`.
}