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

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @Mock
    private EmployeeRepository mockEmployeeRepository;
    @Mock
    private PayrollService mockPayrollService;
    @Mock
    private Scanner mockScanner; // Used for mocking Scanner inputs for CLI methods directly

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Reset locale for consistent output in tests, matching the application's initial setting
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        Locale.setDefault(Locale.ROOT); // Reset to a known default after each test
    }

    /**
     * Helper to invoke private static methods using reflection.
     * @param methodName The name of the private static method.
     * @param parameterTypes The parameter types of the method.
     * @param args The arguments to pass to the method.
     */
    private void invokePrivateStaticMethod(String methodName, Class<?>[] parameterTypes, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = PayrollApp.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        method.invoke(null, args); // null for static methods
    }

    // --- Tests for main method ---

    // Test case for exiting the main application gracefully
    @Test
    void testMain_ExitOption() {
        String input = "4\n"; // Choose exit option
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        PayrollApp.main(new String[]{});
        // Verify that the application printed the menu and exited.
        String expectedOutputFragment = "\nPayroll System - Main Menu\n" +
                "1) Add Employee\n" +
                "2) List Employees\n" +
                "3) Generate Payroll\n" +
                "4) Exit\n" +
                "Choose an option: ";
        assertTrue(outputStreamCaptor.toString().contains(expectedOutputFragment),
                "Expected output to contain menu and prompt before exit.");
    }

    // Test case for invalid menu input (non-numeric)
    @Test
    void testMain_InvalidMenuInput() {
        String input = "invalid\n4\n"; // Invalid input, then exit
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        PayrollApp.main(new String[]{});
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Invalid input. Try again."), "Expected error message for invalid input.");
        assertTrue(output.contains("Choose an option: "), "Expected to re-prompt for option.");
        assertTrue(output.contains("Payroll System - Main Menu"), "Expected menu to be displayed again.");
    }

    // Test case for unknown menu option (valid number, but not 1-4)
    @Test
    void testMain_UnknownMenuOption() {
        String input = "5\n4\n"; // Unknown option, then exit
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        PayrollApp.main(new String[]{});
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Unknown option."), "Expected message for unknown option.");
        assertTrue(output.contains("Choose an option: "), "Expected to re-prompt for option.");
        assertTrue(output.contains("Payroll System - Main Menu"), "Expected menu to be displayed again.");
    }

    // --- Tests for addEmployeeCLI method ---

    // Test case for adding a FullTimeEmployee successfully
    @Test
    void testAddEmployeeCLI_FullTime_Success() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("1") // Type: FullTime
                .thenReturn("FT001") // ID
                .thenReturn("John Doe") // Name
                .thenReturn("IT") // Department
                .thenReturn("5000.0") // Monthly Salary
                .thenReturn("0.15"); // Tax Rate

        invokePrivateStaticMethod("addEmployeeCLI", new Class<?>[]{Scanner.class, EmployeeRepository.class}, mockScanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).add(any(FullTimeEmployee.class));
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Add Employee:"));
        assertTrue(output.contains("Employee added."));
    }

    // Test case for adding a PartTimeEmployee successfully
    @Test
    void testAddEmployeeCLI_PartTime_Success() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("2") // Type: PartTime
                .thenReturn("PT001") // ID
                .thenReturn("Jane Smith") // Name
                .thenReturn("HR") // Department
                .thenReturn("20.0") // Hourly Rate
                .thenReturn("160") // Hours Worked
                .thenReturn("0.10"); // Tax Rate

        invokePrivateStaticMethod("addEmployeeCLI", new Class<?>[]{Scanner.class, EmployeeRepository.class}, mockScanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, times(1)).add(any(PartTimeEmployee.class));
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Add Employee:"));
        assertTrue(output.contains("Employee added."));
    }

    // Test case for invalid employee type input (non-numeric)
    @Test
    void testAddEmployeeCLI_InvalidTypeInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("invalid_type"); // Invalid type input

        invokePrivateStaticMethod("addEmployeeCLI", new Class<?>[]{Scanner.class, EmployeeRepository.class}, mockScanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any(Employee.class));
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Invalid number format. Employee not added."),
                "Expected error for invalid type input.");
    }

    // Test case for invalid numeric input for FullTimeEmployee (e.g., salary)
    @Test
    void testAddEmployeeCLI_FullTime_InvalidNumericInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("1") // Type: FullTime
                .thenReturn("FT002") // ID
                .thenReturn("Alice") // Name
                .thenReturn("Sales") // Department
                .thenReturn("not_a_number"); // Invalid Monthly Salary

        invokePrivateStaticMethod("addEmployeeCLI", new Class<?>[]{Scanner.class, EmployeeRepository.class}, mockScanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any(Employee.class));
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Invalid number format. Employee not added."),
                "Expected error for invalid numeric input (salary).");
    }

    // Test case for invalid numeric input for PartTimeEmployee (e.g., hours)
    @Test
    void testAddEmployeeCLI_PartTime_InvalidNumericInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("2") // Type: PartTime
                .thenReturn("PT002") // ID
                .thenReturn("Bob") // Name
                .thenReturn("Support") // Department
                .thenReturn("15.0") // Hourly Rate
                .thenReturn("not_an_integer"); // Invalid Hours Worked

        invokePrivateStaticMethod("addEmployeeCLI", new Class<?>[]{Scanner.class, EmployeeRepository.class}, mockScanner, mockEmployeeRepository);

        verify(mockEmployeeRepository, never()).add(any(Employee.class));
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Invalid number format. Employee not added."),
                "Expected error for invalid numeric input (hours).");
    }

    // Test case for an unexpected error during add (e.g., repository fails)
    @Test
    void testAddEmployeeCLI_RepositoryError() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("1") // Type: FullTime
                .thenReturn("FT003") // ID
                .thenReturn("Charlie") // Name
                .thenReturn("Marketing") // Department
                .thenReturn("6000.0") // Monthly Salary
                .thenReturn("0.20"); // Tax Rate
        doThrow(new RuntimeException("Repo save failed")).when(mockEmployeeRepository).add(any(FullTimeEmployee.class));

        invokePrivateStaticMethod("addEmployeeCLI", new Class<?>[]{Scanner.class, EmployeeRepository.class}, mockScanner, mockEmployeeRepository);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Error adding employee: Repo save failed"),
                "Expected error message from repository failure.");
    }

    // --- Tests for listEmployees method ---

    // Test case for listing employees when the repository is empty
    @Test
    void testListEmployees_EmptyRepository() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockEmployeeRepository.findAll()).thenReturn(Collections.emptyList());

        invokePrivateStaticMethod("listEmployees", new Class<?>[]{EmployeeRepository.class}, mockEmployeeRepository);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Employees:"), "Expected 'Employees:' header.");
        assertFalse(output.contains("|"), "Expected no employee details when repository is empty.");
    }

    // Test case for listing employees with full-time and part-time employees
    @Test
    void testListEmployees_WithEmployees() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FullTimeEmployee ft = new FullTimeEmployee("FT1", "Alice", "IT", 5000.0, 0.2);
        PartTimeEmployee pt = new PartTimeEmployee("PT1", "Bob", "HR", 25.0, 160, 0.1);
        when(mockEmployeeRepository.findAll()).thenReturn(Arrays.asList(ft, pt));

        invokePrivateStaticMethod("listEmployees", new Class<?>[]{EmployeeRepository.class}, mockEmployeeRepository);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Employees:"), "Expected 'Employees:' header.");
        assertTrue(output.contains("FT1 | Alice | IT | FULL_TIME"), "Expected full-time employee details.");
        assertTrue(output.contains("PT1 | Bob | HR | PART_TIME"), "Expected part-time employee details.");
    }

    // --- Tests for generatePayrollCLI method ---

    // Test case for successful payroll generation and display
    @Test
    void testGeneratePayrollCLI_Success() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        PayrollLine line1 = new PayrollLine("E001", "Emp One", "FULL_TIME", 5000.0, 1000.0, 4000.0);
        PayrollLine line2 = new PayrollLine("E002", "Emp Two", "PART_TIME", 2000.0, 200.0, 1800.0);
        PayrollResult result = new PayrollResult(Arrays.asList(line1, line2), 7000.0, 1200.0, 5800.0);

        when(mockScanner.nextLine())
                .thenReturn("1") // Month
                .thenReturn("2023"); // Year
        when(mockPayrollService.generatePayroll(anyInt(), anyInt())).thenReturn(result);

        invokePrivateStaticMethod("generatePayrollCLI", new Class<?>[]{Scanner.class, PayrollService.class}, mockScanner, mockPayrollService);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Payroll for 1/2023"), "Expected payroll header.");
        assertTrue(output.contains("ID=E001, Name=Emp One, Type=FULL_TIME, Gross=5000.00, Tax=1000.00, Net=4000.00"),
                "Expected full-time payroll line.");
        assertTrue(output.contains("ID=E002, Name=Emp Two, Type=PART_TIME, Gross=2000.00, Tax=200.00, Net=1800.00"),
                "Expected part-time payroll line.");
        assertTrue(output.contains("Total Gross: 7000.0 | Total Tax: 1200.0 | Total Net: 5800.0"),
                "Expected total payroll summary.");
    }

    // Test case for invalid month input (non-numeric)
    @Test
    void testGeneratePayrollCLI_InvalidMonthInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("january"); // Invalid month input

        invokePrivateStaticMethod("generatePayrollCLI", new Class<?>[]{Scanner.class, PayrollService.class}, mockScanner, mockPayrollService);

        verify(mockPayrollService, never()).generatePayroll(anyInt(), anyInt());
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Error generating payroll: For input string: \"january\""),
                "Expected error for invalid month input.");
    }

    // Test case for invalid year input (non-numeric)
    @Test
    void testGeneratePayrollCLI_InvalidYearInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("1") // Valid month
                .thenReturn("two thousand twenty three"); // Invalid year

        invokePrivateStaticMethod("generatePayrollCLI", new Class<?>[]{Scanner.class, PayrollService.class}, mockScanner, mockPayrollService);

        verify(mockPayrollService, never()).generatePayroll(anyInt(), anyInt());
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Error generating payroll: For input string: \"two thousand twenty three\""),
                "Expected error for invalid year input.");
    }

    // Test case when PayrollService throws an exception
    @Test
    void testGeneratePayrollCLI_ServiceThrowsException() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(mockScanner.nextLine())
                .thenReturn("2") // Month
                .thenReturn("2024"); // Year
        doThrow(new RuntimeException("Payroll calculation failed")).when(mockPayrollService).generatePayroll(anyInt(), anyInt());

        invokePrivateStaticMethod("generatePayrollCLI", new Class<?>[]{Scanner.class, PayrollService.class}, mockScanner, mockPayrollService);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Error generating payroll: Payroll calculation failed"),
                "Expected error message from payroll service failure.");
    }

    // --- Tests for ensureNotNull method ---

    // Test case for a null string input, expecting an IllegalArgumentException
    @Test
    void testEnsureNotNull_NullString() throws NoSuchMethodException {
        assertThrows(IllegalArgumentException.class, () ->
            invokePrivateStaticMethod("ensureNotNull", new Class<?>[]{String.class}, (String) null),
            "Expected IllegalArgumentException for null string input."
        );
    }

    // Test case for a non-null string input, expecting no exception
    @Test
    void testEnsureNotNull_NonNullString() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertDoesNotThrow(() ->
            invokePrivateStaticMethod("ensureNotNull", new Class<?>[]{String.class}, "hello"),
            "Expected no exception for non-null string input."
        );
    }

    // --- Tests for lambda$listEmployees$0 ---
    // The lambda$listEmployees$0 method is a synthetic method generated by the compiler
    // for the lambda expression used within the `listEmployees` method.
    // Direct unit testing of synthetic methods is generally not practical or good practice,
    // as they are implementation details of the Java compiler and JVM.
    // The functionality of this lambda (formatting and printing employee details to System.out)
    // is thoroughly covered by the tests for the `listEmployees` method, specifically
    // `testListEmployees_WithEmployees`. This test asserts the exact output format
    // produced by the `listEmployees` method, which directly relies on this lambda.
    // Therefore, no separate, isolated test method is provided for `lambda$listEmployees$0`.
}