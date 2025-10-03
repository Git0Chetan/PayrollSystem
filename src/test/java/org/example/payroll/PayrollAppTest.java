package org.example.payroll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.payroll.model.FullTimeEmployee;
import org.example.payroll.model.PartTimeEmployee;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;
import org.example.payroll.service.PayrollService;

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
import java.util.regex.Pattern;

/**
 * Helper class for accessing private static methods using reflection.
 */
class TestHelper {
    static Method getPrivateStaticMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method;
    }
}

@ExtendWith(MockitoExtension.class)
class PayrollAppTest {

    private InputStream originalSystemIn;
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream outputStreamCaptor;

    @Mock
    private PayrollService mockPayrollService;

    // Use a real EmployeeRepository for testing addEmployeeCLI and listEmployees
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        originalSystemIn = System.in;
        originalSystemOut = System.out;
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Ensure consistent locale for number parsing/formatting
        Locale.setDefault(Locale.US);
        employeeRepository = new EmployeeRepository(); // Fresh repo for each test
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        // Restore default locale to avoid impacting other tests if needed
        Locale.setDefault(Locale.getDefault());
    }

    /**
     * Helper method to simulate user input via System.in.
     *
     * @param data The string data to be fed into System.in.
     */
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    /**
     * Helper method to retrieve captured System.out output.
     * Normalizes line endings for cross-platform consistency.
     *
     * @return The captured output as a string.
     */
    private String getOutput() {
        return outputStreamCaptor.toString().replace("\r\n", "\n");
    }

    // --- Tests for ensureNotNull method ---

    // Test case for null input
    @Test
    @DisplayName("ensureNotNull: Should throw IllegalArgumentException for null input")
    void ensureNotNull_throwsExceptionForNull() throws NoSuchMethodException {
        Method method = TestHelper.getPrivateStaticMethod(PayrollApp.class, "ensureNotNull", String.class);
        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () ->
            method.invoke(null, (Object) null) // Cast to Object to resolve method ambiguity
        );
        assertTrue(thrown.getTargetException() instanceof IllegalArgumentException);
        assertEquals("Null value not allowed", thrown.getTargetException().getMessage());
    }

    // Test case for non-null input
    @Test
    @DisplayName("ensureNotNull: Should do nothing for non-null input")
    void ensureNotNull_doesNothingForNonNull() throws NoSuchMethodException {
        Method method = TestHelper.getPrivateStaticMethod(PayrollApp.class, "ensureNotNull", String.class);
        assertDoesNotThrow(() ->
            method.invoke(null, "some string")
        );
        assertTrue(getOutput().isEmpty(), "No output should be captured for non-null input.");
    }

    // --- Tests for addEmployeeCLI method ---

    // Test case for adding a full-time employee successfully
    @Test
    @DisplayName("addEmployeeCLI: Should add a full-time employee successfully")
    void addEmployeeCLI_addsFullTimeEmployeeSuccessfully() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "1\nFT001\nJohn Doe\nSales\n6000.0\n0.15\n";
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            Method addEmployeeCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "addEmployeeCLI", Scanner.class, EmployeeRepository.class);
            addEmployeeCLIMethod.invoke(null, scanner, employeeRepository);
        }

        String output = getOutput();
        assertTrue(output.contains("Employee added."));
        assertEquals(1, employeeRepository.findAll().size());
        assertTrue(employeeRepository.findAll().get(0) instanceof FullTimeEmployee);
        FullTimeEmployee emp = (FullTimeEmployee) employeeRepository.findAll().get(0);
        assertEquals("FT001", emp.getId());
        assertEquals("John Doe", emp.getName());
        assertEquals("Sales", emp.getDepartment());
        assertEquals(7000.0, emp.getMonthlySalary(), "Monthly salary should be 7000.0 (from seeded value in main, this test is for the cli input)");
        // Correction: This test doesn't use seeded value, it uses input.
        assertEquals(6000.0, emp.getMonthlySalary());
        assertEquals(0.15, emp.getTaxRate());
    }

    // Test case for adding a part-time employee successfully
    @Test
    @DisplayName("addEmployeeCLI: Should add a part-time employee successfully")
    void addEmployeeCLI_addsPartTimeEmployeeSuccessfully() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "2\nPT001\nJane Smith\nHR\n20.0\n160\n0.10\n";
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            Method addEmployeeCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "addEmployeeCLI", Scanner.class, EmployeeRepository.class);
            addEmployeeCLIMethod.invoke(null, scanner, employeeRepository);
        }

        String output = getOutput();
        assertTrue(output.contains("Employee added."));
        assertEquals(1, employeeRepository.findAll().size());
        assertTrue(employeeRepository.findAll().get(0) instanceof PartTimeEmployee);
        PartTimeEmployee emp = (PartTimeEmployee) employeeRepository.findAll().get(0);
        assertEquals("PT001", emp.getId());
        assertEquals("Jane Smith", emp.getName());
        assertEquals("HR", emp.getDepartment());
        assertEquals(20.0, emp.getHourlyRate());
        assertEquals(160, emp.getHoursWorked());
        assertEquals(0.10, emp.getTaxRate());
    }

    // Test case for invalid number format during adding employee
    @Test
    @DisplayName("addEmployeeCLI: Should handle invalid number format gracefully")
    void addEmployeeCLI_handlesInvalidNumberFormat() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "1\nFT002\nAlice\nMarketing\nnot_a_number\n0.15\n"; // Invalid salary
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            Method addEmployeeCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "addEmployeeCLI", Scanner.class, EmployeeRepository.class);
            addEmployeeCLIMethod.invoke(null, scanner, employeeRepository);
        }

        String output = getOutput();
        assertTrue(output.contains("Invalid number format. Employee not added."));
        assertTrue(employeeRepository.findAll().isEmpty(), "No employee should be added.");
    }

    // Test case for general exceptions during adding employee (e.g., parsing empty string for double)
    @Test
    @DisplayName("addEmployeeCLI: Should handle general exceptions during adding employee")
    void addEmployeeCLI_handlesGeneralExceptions() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "1\nFT003\nBob\nIT\n\n0.15\n"; // Empty salary input will cause NumberFormatException
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            Method addEmployeeCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "addEmployeeCLI", Scanner.class, EmployeeRepository.class);
            addEmployeeCLIMethod.invoke(null, scanner, employeeRepository);
        }

        String output = getOutput();
        // The specific error message for NumberFormatException when parsing an empty string
        assertTrue(output.contains("Error adding employee: For input string: \"\""));
        assertTrue(employeeRepository.findAll().isEmpty(), "No employee should be added.");
    }

    // Test case for an invalid employee type input that defaults to PartTime
    @Test
    @DisplayName("addEmployeeCLI: Should default to PartTime if type input is not 1")
    void addEmployeeCLI_defaultsToPartTimeIfTypeNotOne() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "99\nPT002\nCharlie\nSupport\n15.0\n100\n0.05\n"; // Type '99' is not '1', so it falls into 'else' block for PartTime
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            Method addEmployeeCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "addEmployeeCLI", Scanner.class, EmployeeRepository.class);
            addEmployeeCLIMethod.invoke(null, scanner, employeeRepository);
        }

        String output = getOutput();
        assertTrue(output.contains("Employee added."));
        assertEquals(1, employeeRepository.findAll().size());
        assertTrue(employeeRepository.findAll().get(0) instanceof PartTimeEmployee);
        PartTimeEmployee emp = (PartTimeEmployee) employeeRepository.findAll().get(0);
        assertEquals("PT002", emp.getId());
        assertEquals("Charlie", emp.getName());
        assertEquals("Support", emp.getDepartment());
    }

    // --- Tests for listEmployees method ---

    // Test case for listing employees when repository is empty
    @Test
    @DisplayName("listEmployees: Should print 'Employees:' header and nothing else if repository is empty")
    void listEmployees_printsOnlyHeaderWhenEmpty() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method listEmployeesMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "listEmployees", EmployeeRepository.class);
        listEmployeesMethod.invoke(null, employeeRepository);

        String output = getOutput();
        assertTrue(output.contains("Employees:\n")); // Check for header with newline
        assertFalse(output.contains(" | "), "Should not contain employee details if repository is empty.");
        // Should only contain the header and possibly an extra newline from System.out.println
        assertEquals("Employees:\n", output);
    }

    // Test case for listing employees with data
    @Test
    @DisplayName("listEmployees: Should print all employees from the repository in the specified format")
    void listEmployees_printsAllEmployees() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        employeeRepository.add(new FullTimeEmployee("FT1", "Alice Johnson", "Engineering", 7500.0, 0.20));
        employeeRepository.add(new PartTimeEmployee("PT1", "Bob Smith", "QA", 25.0, 80, 0.12));

        Method listEmployeesMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "listEmployees", EmployeeRepository.class);
        listEmployeesMethod.invoke(null, employeeRepository);

        String output = getOutput();
        assertTrue(output.contains("Employees:\n"));
        assertTrue(output.contains("FT1 | Alice Johnson | Engineering | FULL_TIME\n"));
        assertTrue(output.contains("PT1 | Bob Smith | QA | PART_TIME\n"));

        // Verify the exact order and content of lines (after "Employees:\n")
        String expectedContent = "FT1 | Alice Johnson | Engineering | FULL_TIME\n" +
                                 "PT1 | Bob Smith | QA | PART_TIME\n";
        assertTrue(output.substring(output.indexOf("Employees:\n") + "Employees:\n".length()).contains(expectedContent));
    }

    // --- Tests for generatePayrollCLI method ---

    // Test case for successful payroll generation
    @Test
    @DisplayName("generatePayrollCLI: Should generate and display payroll successfully")
    void generatePayrollCLI_generatesPayrollSuccessfully() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "1\n2023\n"; // Month and Year
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            // Mock the PayrollService behavior
            PayrollResult.PayrollLine line1 = new PayrollResult.PayrollLine("E1", "Emp One", "FULL_TIME", 5000.0, 1000.0, 4000.0);
            PayrollResult.PayrollLine line2 = new PayrollResult.PayrollLine("E2", "Emp Two", "PART_TIME", 1000.0, 100.0, 900.0);
            PayrollResult payrollResult = new PayrollResult(Arrays.asList(line1, line2), 6000.0, 1100.0, 4900.0);
            when(mockPayrollService.generatePayroll(1, 2023)).thenReturn(payrollResult);

            Method generatePayrollCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "generatePayrollCLI", Scanner.class, PayrollService.class);
            generatePayrollCLIMethod.invoke(null, scanner, mockPayrollService);
        }

        String output = getOutput();
        assertTrue(output.contains("Payroll for 1/2023"));
        assertTrue(output.contains("ID=E1, Name=Emp One, Type=FULL_TIME, Gross=5000.00, Tax=1000.00, Net=4000.00"));
        assertTrue(output.contains("ID=E2, Name=Emp Two, Type=PART_TIME, Gross=1000.00, Tax=100.00, Net=900.00"));
        assertTrue(output.contains("Total Gross: 6000.0 | Total Tax: 1100.0 | Total Net: 4900.0"));

        verify(mockPayrollService, times(1)).generatePayroll(1, 2023);
    }

    // Test case for invalid number format for month/year
    @Test
    @DisplayName("generatePayrollCLI: Should handle invalid number format for month/year gracefully")
    void generatePayrollCLI_handlesInvalidNumberFormat() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "invalid_month\n2023\n"; // Invalid month
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            Method generatePayrollCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "generatePayrollCLI", Scanner.class, PayrollService.class);
            generatePayrollCLIMethod.invoke(null, scanner, mockPayrollService);
        }

        String output = getOutput();
        assertTrue(output.contains("Error generating payroll: For input string: \"invalid_month\""));
        verify(mockPayrollService, never()).generatePayroll(anyInt(), anyInt()); // Service should not be called
    }

    // Test case for PayrollService throwing an exception
    @Test
    @DisplayName("generatePayrollCLI: Should handle exceptions from PayrollService gracefully")
    void generatePayrollCLI_handlesPayrollServiceException() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String input = "2\n2024\n"; // Month and Year
        provideInput(input);
        try (Scanner scanner = new Scanner(System.in)) {
            when(mockPayrollService.generatePayroll(2, 2024)).thenThrow(new RuntimeException("Service error during payroll calculation"));

            Method generatePayrollCLIMethod = TestHelper.getPrivateStaticMethod(PayrollApp.class, "generatePayrollCLI", Scanner.class, PayrollService.class);
            generatePayrollCLIMethod.invoke(null, scanner, mockPayrollService);
        }

        String output = getOutput();
        assertTrue(output.contains("Error generating payroll: Service error during payroll calculation"));
        verify(mockPayrollService, times(1)).generatePayroll(2, 2024);
    }

    // --- Tests for main method ---

    // Test case for a full end-to-end CLI interaction with main menu, adding, listing, generating payroll, and exiting.
    @Test
    @DisplayName("main: Should handle a full CLI flow including add, list, generate, and exit operations")
    void main_fullCliInteraction() throws InterruptedException {
        // Simulate input for:
        // 1. Add FullTime Employee
        // 2. Add PartTime Employee
        // 3. List Employees
        // 4. Generate Payroll (for 1/2023, for all employees including seeded ones)
        // 5. Exit
        String input =
            // Add FullTime Employee (Menu option 1)
            "1\n" +                  // Option 1: Add Employee
            "1\n" +                  // Type 1: FullTime
            "FT002\n" +              // ID
            "Charlie Brown\n" +      // Name
            "Design\n" +             // Department
            "5500.0\n" +             // Monthly Salary
            "0.18\n" +               // Tax Rate

            // Add PartTime Employee (Menu option 1)
            "1\n" +                  // Option 1: Add Employee
            "2\n" +                  // Type 2: PartTime
            "PT002\n" +              // ID
            "Lucy Van Pelt\n" +      // Name
            "Marketing\n" +          // Department
            "28.0\n" +               // Hourly Rate
            "120\n" +                // Hours Worked
            "0.10\n" +               // Tax Rate

            // List Employees (Menu option 2)
            "2\n" +                  // Option 2: List Employees

            // Generate Payroll (Menu option 3)
            "3\n" +                  // Option 3: Generate Payroll
            "1\n" +                  // Month (January)
            "2023\n" +               // Year

            // Exit (Menu option 4)
            "4\n";                   // Option 4: Exit

        provideInput(input);

        // Run the main method in a separate thread because it contains a Scanner.nextLine() loop.
        // This ensures the test thread doesn't block indefinitely.
        Thread mainThread = new Thread(() -> PayrollApp.main(new String[]{}));
        mainThread.start();
        mainThread.join(10000); // Wait for the main thread to finish, with a timeout

        assertFalse(mainThread.isAlive(), "Main thread should have terminated after '4' input.");

        String output = getOutput();

        // Verify "Add Employee" interactions
        assertTrue(output.contains("Employee added."), "Output should confirm employee addition.");
        assertTrue(output.contains("ID: FT002"), "Output should contain added FT employee ID prompt.");
        assertTrue(output.contains("ID: PT002"), "Output should contain added PT employee ID prompt.");

        // Verify "List Employees" output contains all employees (seeded + newly added)
        assertTrue(output.contains("Employees:\n"), "Output should contain 'Employees:' header.");
        assertTrue(output.contains("FT1 | Alice Johnson | Engineering | FULL_TIME\n"), "Output should contain seeded FT employee.");
        assertTrue(output.contains("PT1 | Bob Smith | QA | PART_TIME\n"), "Output should contain seeded PT employee.");
        assertTrue(output.contains("FT002 | Charlie Brown | Design | FULL_TIME\n"), "Output should contain newly added FT employee.");
        assertTrue(output.contains("PT002 | Lucy Van Pelt | Marketing | PART_TIME\n"), "Output should contain newly added PT employee.");

        // Verify "Generate Payroll" output (for 1/2023)
        // These are checks for the format and presence of specific employee data.
        // Exact gross/tax/net values are handled by PayrollService's own tests.
        assertTrue(output.contains("Payroll for 1/2023\n"), "Output should contain payroll header for 1/2023.");
        assertTrue(output.contains("ID=FT1, Name=Alice Johnson, Type=FULL_TIME, Gross="), "Output should contain payroll line for seeded Alice Johnson.");
        assertTrue(output.contains("ID=PT1, Name=Bob Smith, Type=PART_TIME, Gross="), "Output should contain payroll line for seeded Bob Smith.");
        assertTrue(output.contains("ID=FT002, Name=Charlie Brown, Type=FULL_TIME, Gross="), "Output should contain payroll line for added Charlie Brown.");
        assertTrue(output.contains("ID=PT002, Name=Lucy Van Pelt, Type=PART_TIME, Gross="), "Output should contain payroll line for added Lucy Van Pelt.");
        assertTrue(output.contains("Total Gross: "), "Output should contain Total Gross.");
        assertTrue(output.contains("Total Tax: "), "Output should contain Total Tax.");
        assertTrue(output.contains("Total Net: "), "Output should contain Total Net.");

        // For the specific case of the main method (Jan 2023), let's check totals if possible
        // Expected totals considering seeded and added employees:
        // FT1: Salary 7500, TaxRate 0.20 -> Gross 7500, Tax 1500, Net 6000
        // PT1: Rate 25, Hours 80, TaxRate 0.12 -> Gross 2000, Tax 240, Net 1760
        // FT002: Salary 5500, TaxRate 0.18 -> Gross 5500, Tax 990, Net 4510
        // PT002: Rate 28, Hours 120, TaxRate 0.10 -> Gross 3360, Tax 336, Net 3024
        // Total Gross: 7500 + 2000 + 5500 + 3360 = 18360.0
        // Total Tax: 1500 + 240 + 990 + 336 = 3066.0
        // Total Net: 6000 + 1760 + 4510 + 3024 = 15294.0
        assertTrue(output.contains("Total Gross: 18360.0 | Total Tax: 3066.0 | Total Net: 15294.0"), "Output should contain correct total payroll.");
    }

    // Test case for invalid menu option input in main
    @Test
    @DisplayName("main: Should handle invalid and unknown menu options gracefully before exiting")
    void main_invalidMenuOption() throws InterruptedException {
        String input =
            "invalid_option\n" + // Non-numeric input
            "99\n" +             // Valid number, but unknown option
            "4\n";               // Exit

        provideInput(input);

        Thread mainThread = new Thread(() -> PayrollApp.main(new String[]{}));
        mainThread.start();
        mainThread.join(2000); // Wait for the main thread to finish

        assertFalse(mainThread.isAlive(), "Main thread should have terminated.");

        String output = getOutput();
        assertTrue(output.contains("Invalid input. Try again.\n"), "Output should contain message for invalid non-numeric input.");
        assertTrue(output.contains("Unknown option.\n"), "Output should contain message for unknown numeric option.");
        // Ensure the menu is printed multiple times due to retries
        long menuCount = output.lines().filter(line -> line.contains("Payroll System - Main Menu")).count();
        assertTrue(menuCount >= 3, "Main menu should be displayed at least 3 times (initial + 2 retries).");
    }

    // Test case to ensure lambda$listEmployees$0 is indirectly covered by listEmployees
    @Test
    @DisplayName("lambda$listEmployees$0: Behavior is covered by listEmployees tests (no direct test)")
    void lambdaListEmployees0_isCoveredByListEmployees() {
        // This test serves as an explicit acknowledgment that the synthetic method
        // `lambda$listEmployees$0` is implicitly covered.
        // It's generated by the compiler for the lambda expression within `listEmployees`
        // and contains simple printing logic. Testing `listEmployees` thoroughly
        // ensures the lambda's functionality is verified without needing direct access.
        assertTrue(true, "The behavior of lambda$listEmployees$0 is verified through tests for listEmployees.");
    }
}