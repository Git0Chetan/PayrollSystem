package org.example.payroll.service;

import org.example.payroll.model.Employee;
import org.example.payroll.model.PayrollLine;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private PayrollService payrollService;

    // This setup is handled by @ExtendWith(MockitoExtension.class) and @Mock/@InjectMocks annotations.
    // However, an explicit @BeforeEach for clarity or additional setup is still good practice.
    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); // Not strictly needed with @ExtendWith(MockitoExtension.class)
    }

    // Test case for generating payroll with multiple employees
    @Test
    @DisplayName("generatePayroll should create correct payroll for multiple employees")
    void generatePayroll_multipleEmployees_returnsCorrectPayrollResult() {
        // Arrange
        int month = 1;
        int year = 2023;

        Employee employee1 = mock(Employee.class);
        when(employee1.getId()).thenReturn("E001");
        when(employee1.getName()).thenReturn("Alice");
        when(employee1.getType()).thenReturn("FullTime");
        when(employee1.getTaxRate()).thenReturn(0.10); // 10% tax
        when(employee1.calculateGrossPay(month, year)).thenReturn(5000.00);

        Employee employee2 = mock(Employee.class);
        when(employee2.getId()).thenReturn("E002");
        when(employee2.getName()).thenReturn("Bob");
        when(employee2.getType()).thenReturn("PartTime");
        when(employee2.getTaxRate()).thenReturn(0.05); // 5% tax
        when(employee2.calculateGrossPay(month, year)).thenReturn(2000.00);

        when(repository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        // Act
        PayrollResult result = payrollService.generatePayroll(month, year);

        // Assert
        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(2, result.getLines().size());

        PayrollLine line1 = result.getLines().get(0);
        assertEquals("E001", line1.getEmployeeId());
        assertEquals("Alice", line1.getEmployeeName());
        assertEquals("FullTime", line1.getEmployeeType());
        assertEquals(5000.00, line1.getGrossPay(), 0.001);
        assertEquals(500.00, line1.getTaxAmount(), 0.001); // 5000 * 0.10
        assertEquals(4500.00, line1.getNetPay(), 0.001); // 5000 - 500
        assertEquals(month, line1.getMonth());
        assertEquals(year, line1.getYear());

        PayrollLine line2 = result.getLines().get(1);
        assertEquals("E002", line2.getEmployeeId());
        assertEquals("Bob", line2.getEmployeeName());
        assertEquals("PartTime", line2.getEmployeeType());
        assertEquals(2000.00, line2.getGrossPay(), 0.001);
        assertEquals(100.00, line2.getTaxAmount(), 0.001); // 2000 * 0.05
        assertEquals(1900.00, line2.getNetPay(), 0.001); // 2000 - 100
        assertEquals(month, line2.getMonth());
        assertEquals(year, line2.getYear());

        verify(repository, times(1)).findAll();
        verify(employee1, times(1)).calculateGrossPay(month, year);
        verify(employee2, times(1)).calculateGrossPay(month, year);
    }

    // Test case for generating payroll when there are no employees
    @Test
    @DisplayName("generatePayroll should return empty PayrollResult if no employees exist")
    void generatePayroll_noEmployees_returnsEmptyPayrollResult() {
        // Arrange
        int month = 2;
        int year = 2024;
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        PayrollResult result = payrollService.generatePayroll(month, year);

        // Assert
        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertTrue(result.getLines().isEmpty());

        verify(repository, times(1)).findAll();
    }

    // Test case for an employee with zero gross pay
    @Test
    @DisplayName("generatePayroll should handle employee with zero gross pay correctly")
    void generatePayroll_employeeWithZeroGrossPay_returnsCorrectPayrollLine() {
        // Arrange
        int month = 3;
        int year = 2023;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn("E003");
        when(employee.getName()).thenReturn("Charlie");
        when(employee.getType()).thenReturn("Contractor");
        when(employee.getTaxRate()).thenReturn(0.15);
        when(employee.calculateGrossPay(month, year)).thenReturn(0.00);

        when(repository.findAll()).thenReturn(Collections.singletonList(employee));

        // Act
        PayrollResult result = payrollService.generatePayroll(month, year);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getLines().size());
        PayrollLine line = result.getLines().get(0);
        assertEquals("E003", line.getEmployeeId());
        assertEquals(0.00, line.getGrossPay(), 0.001);
        assertEquals(0.00, line.getTaxAmount(), 0.001);
        assertEquals(0.00, line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(repository, times(1)).findAll();
        verify(employee, times(1)).calculateGrossPay(month, year);
    }

    // Test case for an employee with zero tax rate
    @Test
    @DisplayName("generatePayroll should handle employee with zero tax rate correctly")
    void generatePayroll_employeeWithZeroTaxRate_netPayEqualsGrossPay() {
        // Arrange
        int month = 4;
        int year = 2023;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn("E004");
        when(employee.getName()).thenReturn("Diana");
        when(employee.getType()).thenReturn("Intern");
        when(employee.getTaxRate()).thenReturn(0.0); // No tax
        when(employee.calculateGrossPay(month, year)).thenReturn(1000.00);

        when(repository.findAll()).thenReturn(Collections.singletonList(employee));

        // Act
        PayrollResult result = payrollService.generatePayroll(month, year);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getLines().size());
        PayrollLine line = result.getLines().get(0);
        assertEquals("E004", line.getEmployeeId());
        assertEquals(1000.00, line.getGrossPay(), 0.001);
        assertEquals(0.00, line.getTaxAmount(), 0.001); // 1000 * 0.0
        assertEquals(1000.00, line.getNetPay(), 0.001); // 1000 - 0
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(repository, times(1)).findAll();
        verify(employee, times(1)).calculateGrossPay(month, year);
    }

    // Test case for retrieving all employees when the repository has employees
    @Test
    @DisplayName("getAllEmployees should return a list of all employees")
    void getAllEmployees_repositoryHasEmployees_returnsListOfEmployees() {
        // Arrange
        Employee employee1 = mock(Employee.class);
        Employee employee2 = mock(Employee.class);
        List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);
        when(repository.findAll()).thenReturn(expectedEmployees);

        // Act
        List<Employee> actualEmployees = payrollService.getAllEmployees();

        // Assert
        assertNotNull(actualEmployees);
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        assertTrue(actualEmployees.containsAll(expectedEmployees));
        assertNotSame(expectedEmployees, actualEmployees); // Should return a new list instance

        verify(repository, times(1)).findAll();
    }

    // Test case for retrieving all employees when the repository is empty
    @Test
    @DisplayName("getAllEmployees should return an empty list if no employees exist")
    void getAllEmployees_repositoryIsEmpty_returnsEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Employee> actualEmployees = payrollService.getAllEmployees();

        // Assert
        assertNotNull(actualEmployees);
        assertTrue(actualEmployees.isEmpty());
        assertEquals(0, actualEmployees.size());

        verify(repository, times(1)).findAll();
    }

    // Test case for retrieving all employees when repository returns null (though unlikely with proper repo implementation)
    @Test
    @DisplayName("getAllEmployees should return an empty list if repository.findAll returns null")
    void getAllEmployees_repositoryReturnsNull_returnsEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(null); // Simulate a case where findAll might return null

        // Act
        List<Employee> actualEmployees = payrollService.getAllEmployees();

        // Assert
        assertNotNull(actualEmployees); // new ArrayList<>(null) results in an empty list, not NPE
        assertTrue(actualEmployees.isEmpty());

        verify(repository, times(1)).findAll();
    }
}