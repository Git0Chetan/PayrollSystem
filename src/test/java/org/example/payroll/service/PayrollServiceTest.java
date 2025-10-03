package org.example.payroll.service;

import org.example.payroll.model.Employee;
import org.example.payroll.model.PayrollLine;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

    // --- Tests for generatePayroll method ---

    // Test case for generating payroll with multiple employees, positive pay, and various tax rates.
    @Test
    @DisplayName("should generate payroll correctly for multiple employees with positive gross pay")
    void generatePayroll_multipleEmployees_success() {
        int month = 10;
        int year = 2023;

        // Mock Employee 1
        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("EMP001");
        when(emp1.getName()).thenReturn("Alice");
        when(emp1.getType()).thenReturn("FULL_TIME");
        when(emp1.getTaxRate()).thenReturn(0.10); // 10% tax
        when(emp1.calculateGrossPay(month, year)).thenReturn(5000.0);

        // Mock Employee 2
        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("EMP002");
        when(emp2.getName()).thenReturn("Bob");
        when(emp2.getType()).thenReturn("PART_TIME");
        when(emp2.getTaxRate()).thenReturn(0.05); // 5% tax
        when(emp2.calculateGrossPay(month, year)).thenReturn(2000.0);

        // Mock Employee 3 (zero tax rate)
        Employee emp3 = mock(Employee.class);
        when(emp3.getId()).thenReturn("EMP003");
        when(emp3.getName()).thenReturn("Charlie");
        when(emp3.getType()).thenReturn("CONTRACTOR");
        when(emp3.getTaxRate()).thenReturn(0.0); // 0% tax
        when(emp3.calculateGrossPay(month, year)).thenReturn(3000.0);

        when(repository.findAll()).thenReturn(List.of(emp1, emp2, emp3));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(3, result.getPayrollLines().size());

        // Verify emp1's payroll line
        PayrollLine line1 = result.getPayrollLines().get(0);
        assertEquals("EMP001", line1.getEmployeeId());
        assertEquals("Alice", line1.getEmployeeName());
        assertEquals("FULL_TIME", line1.getEmployeeType());
        assertEquals(5000.0, line1.getGrossPay(), 0.001);
        assertEquals(500.0, line1.getTaxAmount(), 0.001); // 5000 * 0.10
        assertEquals(4500.0, line1.getNetPay(), 0.001);   // 5000 - 500
        assertEquals(month, line1.getMonth());
        assertEquals(year, line1.getYear());

        // Verify emp2's payroll line
        PayrollLine line2 = result.getPayrollLines().get(1);
        assertEquals("EMP002", line2.getEmployeeId());
        assertEquals("Bob", line2.getEmployeeName());
        assertEquals("PART_TIME", line2.getEmployeeType());
        assertEquals(2000.0, line2.getGrossPay(), 0.001);
        assertEquals(100.0, line2.getTaxAmount(), 0.001); // 2000 * 0.05
        assertEquals(1900.0, line2.getNetPay(), 0.001);   // 2000 - 100
        assertEquals(month, line2.getMonth());
        assertEquals(year, line2.getYear());

        // Verify emp3's payroll line (zero tax)
        PayrollLine line3 = result.getPayrollLines().get(2);
        assertEquals("EMP003", line3.getEmployeeId());
        assertEquals("Charlie", line3.getEmployeeName());
        assertEquals("CONTRACTOR", line3.getEmployeeType());
        assertEquals(3000.0, line3.getGrossPay(), 0.001);
        assertEquals(0.0, line3.getTaxAmount(), 0.001); // 3000 * 0.0
        assertEquals(3000.0, line3.getNetPay(), 0.001);   // 3000 - 0
        assertEquals(month, line3.getMonth());
        assertEquals(year, line3.getYear());

        verify(repository, times(1)).findAll();
        verify(emp1, times(1)).calculateGrossPay(month, year);
        verify(emp2, times(1)).calculateGrossPay(month, year);
        verify(emp3, times(1)).calculateGrossPay(month, year);
    }

    // Test case for generating payroll when no employees are found in the repository.
    @Test
    @DisplayName("should generate an empty payroll result when no employees exist")
    void generatePayroll_noEmployees_returnsEmptyResult() {
        int month = 1;
        int year = 2024;

        when(repository.findAll()).thenReturn(Collections.emptyList());

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertTrue(result.getPayrollLines().isEmpty());
        verify(repository, times(1)).findAll();
    }

    // Test case for an employee whose gross pay calculation results in zero.
    @Test
    @DisplayName("should handle employees with zero gross pay correctly")
    void generatePayroll_employeeWithZeroGrossPay_correctlyCalculated() {
        int month = 6;
        int year = 2023;

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("EMP004");
        when(emp.getName()).thenReturn("David");
        when(emp.getType()).thenReturn("FULL_TIME");
        when(emp.getTaxRate()).thenReturn(0.15); // 15% tax
        when(emp.calculateGrossPay(month, year)).thenReturn(0.0); // Zero gross pay

        when(repository.findAll()).thenReturn(List.of(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());
        PayrollLine line = result.getPayrollLines().get(0);

        assertEquals("EMP004", line.getEmployeeId());
        assertEquals(0.0, line.getGrossPay(), 0.001);
        assertEquals(0.0, line.getTaxAmount(), 0.001);
        assertEquals(0.0, line.getNetPay(), 0.001);

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
    }

    // Test case for a single employee with typical pay.
    @Test
    @DisplayName("should generate payroll for a single employee")
    void generatePayroll_singleEmployee_success() {
        int month = 7;
        int year = 2023;

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("SINGLE_EMP");
        when(emp.getName()).thenReturn("Eve");
        when(emp.getType()).thenReturn("SALARIED");
        when(emp.getTaxRate()).thenReturn(0.20);
        when(emp.calculateGrossPay(month, year)).thenReturn(6000.0);

        when(repository.findAll()).thenReturn(List.of(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());
        PayrollLine line = result.getPayrollLines().get(0);

        assertEquals("SINGLE_EMP", line.getEmployeeId());
        assertEquals("Eve", line.getEmployeeName());
        assertEquals("SALARIED", line.getEmployeeType());
        assertEquals(6000.0, line.getGrossPay(), 0.001);
        assertEquals(1200.0, line.getTaxAmount(), 0.001); // 6000 * 0.20
        assertEquals(4800.0, line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
    }

    // --- Tests for getAllEmployees method ---

    // Test case for retrieving all employees when the repository contains multiple employees.
    @Test
    @DisplayName("should return all employees from the repository")
    void getAllEmployees_repositoryHasEmployees_returnsAll() {
        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("E1");
        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("E2");

        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(emp1);
        mockEmployees.add(emp2);

        when(repository.findAll()).thenReturn(mockEmployees);

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(emp1));
        assertTrue(result.contains(emp2));
        verify(repository, times(1)).findAll();
    }

    // Test case for retrieving all employees when the repository is empty.
    @Test
    @DisplayName("should return an empty list when no employees are in the repository")
    void getAllEmployees_repositoryIsEmpty_returnsEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    // Test case to ensure the returned list is a new instance and not the repository's internal list.
    @Test
    @DisplayName("should return a new mutable list instance, not the repository's internal list")
    void getAllEmployees_returnsMutableCopy() {
        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("Original");

        List<Employee> internalRepositoryList = new ArrayList<>();
        internalRepositoryList.add(emp1);

        when(repository.findAll()).thenReturn(internalRepositoryList);

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        // Add a new employee to the returned list
        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("Added");
        result.add(emp2);

        // Assert that the returned list is a distinct object
        assertNotSame(internalRepositoryList, result, "The returned list should be a different instance from the repository's list.");
        // Assert that the original list (which was mocked to be internalRepositoryList) remains unchanged
        // This is implicitly tested by verifying `repository.findAll()` was called with its original return.
        assertEquals(1, internalRepositoryList.size(), "Changes to the returned list should not affect the original list from the repository.");
        assertEquals(2, result.size()); // The 'result' list now has 2 elements.
        assertTrue(result.contains(emp1));
        assertTrue(result.contains(emp2));

        // Re-verify that calling getAllEmployees again would yield the original content
        // This implicitly confirms the copy behavior.
        // We explicitly stub again if we want to ensure the state after the previous call is considered for a new call.
        when(repository.findAll()).thenReturn(internalRepositoryList);
        List<Employee> secondCallResult = payrollService.getAllEmployees();
        assertEquals(1, secondCallResult.size());
        assertTrue(secondCallResult.contains(emp1));
        assertFalse(secondCallResult.contains(emp2)); // emp2 was added to 'result', not 'internalRepositoryList'

        verify(repository, times(2)).findAll(); // Called twice for verification.
    }
}