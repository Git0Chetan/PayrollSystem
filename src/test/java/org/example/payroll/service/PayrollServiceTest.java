package org.example.payroll.service;

import org.example.payroll.model.Employee;
import org.example.payroll.model.PayrollLine;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PayrollServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private PayrollService payrollService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case for when no employees are present in the repository
    @Test
    @DisplayName("getAllEmployees returns empty list when repository is empty")
    void getAllEmployees_emptyRepository_returnsEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result, "Result list should not be null");
        assertTrue(result.isEmpty(), "Result list should be empty");
        verify(repository, times(1)).findAll();
    }

    // Test case for when multiple employees are present in the repository
    @Test
    @DisplayName("getAllEmployees returns a list of all employees from the repository")
    void getAllEmployees_withEmployees_returnsAllEmployees() {
        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("E001");
        when(emp1.getName()).thenReturn("John Doe");
        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("E002");
        when(emp2.getName()).thenReturn("Jane Smith");

        List<Employee> mockEmployees = Arrays.asList(emp1, emp2);
        when(repository.findAll()).thenReturn(mockEmployees);

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result, "Result list should not be null");
        assertEquals(2, result.size(), "Result list should contain 2 employees");
        assertTrue(result.contains(emp1), "Result list should contain emp1");
        assertTrue(result.contains(emp2), "Result list should contain emp2");
        assertNotSame(mockEmployees, result, "Should return a new ArrayList, not the repository's internal list");
        verify(repository, times(1)).findAll();
    }

    // Test case for generating payroll with an empty employee repository
    @Test
    @DisplayName("generatePayroll returns empty PayrollResult when no employees exist")
    void generatePayroll_emptyRepository_returnsEmptyPayrollResult() {
        int month = 1;
        int year = 2023;
        when(repository.findAll()).thenReturn(Collections.emptyList());

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result, "PayrollResult should not be null");
        assertEquals(month, result.getMonth(), "Month in PayrollResult should match input");
        assertEquals(year, result.getYear(), "Year in PayrollResult should match input");
        assertTrue(result.getPayrollLines().isEmpty(), "PayrollResult should have no lines for empty repository");
        verify(repository, times(1)).findAll();
    }

    // Test case for generating payroll for a single employee
    @Test
    @DisplayName("generatePayroll correctly calculates for a single employee")
    void generatePayroll_singleEmployee_calculatesCorrectly() {
        int month = 2;
        int year = 2023;
        double grossPay = 5000.0;
        double taxRate = 0.20; // 20%
        double expectedTax = grossPay * taxRate;
        double expectedNet = grossPay - expectedTax;

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("EMP001");
        when(emp.getName()).thenReturn("Alice");
        when(emp.getType()).thenReturn("Full-Time");
        when(emp.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(emp.getTaxRate()).thenReturn(taxRate);

        when(repository.findAll()).thenReturn(Collections.singletonList(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result, "PayrollResult should not be null");
        assertEquals(1, result.getPayrollLines().size(), "PayrollResult should have one line");

        PayrollLine line = result.getPayrollLines().get(0);
        assertEquals("EMP001", line.getEmployeeId());
        assertEquals("Alice", line.getEmployeeName());
        assertEquals("Full-Time", line.getEmployeeType());
        assertEquals(grossPay, line.getGrossPay(), 0.001);
        assertEquals(expectedTax, line.getTaxAmount(), 0.001);
        assertEquals(expectedNet, line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
        verify(emp, times(1)).getTaxRate();
    }

    // Test case for generating payroll for multiple employees with different data
    @Test
    @DisplayName("generatePayroll correctly calculates for multiple employees")
    void generatePayroll_multipleEmployees_calculatesCorrectly() {
        int month = 3;
        int year = 2023;

        // Employee 1
        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("EMP001");
        when(emp1.getName()).thenReturn("Alice");
        when(emp1.getType()).thenReturn("Full-Time");
        when(emp1.calculateGrossPay(month, year)).thenReturn(6000.0);
        when(emp1.getTaxRate()).thenReturn(0.25); // 25%

        // Employee 2
        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("EMP002");
        when(emp2.getName()).thenReturn("Bob");
        when(emp2.getType()).thenReturn("Part-Time");
        when(emp2.calculateGrossPay(month, year)).thenReturn(2000.0);
        when(emp2.getTaxRate()).thenReturn(0.10); // 10%

        List<Employee> employees = Arrays.asList(emp1, emp2);
        when(repository.findAll()).thenReturn(employees);

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result, "PayrollResult should not be null");
        assertEquals(2, result.getPayrollLines().size(), "PayrollResult should have two lines");
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());

        // Verify Employee 1's payroll line
        PayrollLine line1 = result.getPayrollLines().stream()
                .filter(l -> l.getEmployeeId().equals("EMP001"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Payroll line for EMP001 not found"));
        assertEquals(6000.0, line1.getGrossPay(), 0.001);
        assertEquals(6000.0 * 0.25, line1.getTaxAmount(), 0.001);
        assertEquals(6000.0 - (6000.0 * 0.25), line1.getNetPay(), 0.001);

        // Verify Employee 2's payroll line
        PayrollLine line2 = result.getPayrollLines().stream()
                .filter(l -> l.getEmployeeId().equals("EMP002"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Payroll line for EMP002 not found"));
        assertEquals(2000.0, line2.getGrossPay(), 0.001);
        assertEquals(2000.0 * 0.10, line2.getTaxAmount(), 0.001);
        assertEquals(2000.0 - (2000.0 * 0.10), line2.getNetPay(), 0.001);

        verify(repository, times(1)).findAll();
        verify(emp1, times(1)).calculateGrossPay(month, year);
        verify(emp1, times(1)).getTaxRate();
        verify(emp2, times(1)).calculateGrossPay(month, year);
        verify(emp2, times(1)).getTaxRate();
    }

    // Test case for an employee with a zero tax rate
    @Test
    @DisplayName("generatePayroll handles employee with zero tax rate correctly")
    void generatePayroll_zeroTaxRateEmployee_taxIsZeroNetEqualsGross() {
        int month = 4;
        int year = 2023;
        double grossPay = 4500.0;
        double taxRate = 0.0; // 0% tax

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("EMP003");
        when(emp.getName()).thenReturn("Charlie");
        when(emp.getType()).thenReturn("Contractor");
        when(emp.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(emp.getTaxRate()).thenReturn(taxRate);

        when(repository.findAll()).thenReturn(Collections.singletonList(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());

        PayrollLine line = result.getPayrollLines().get(0);
        assertEquals(grossPay, line.getGrossPay(), 0.001);
        assertEquals(0.0, line.getTaxAmount(), 0.001);
        assertEquals(grossPay, line.getNetPay(), 0.001); // Net should equal gross when tax is zero

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
        verify(emp, times(1)).getTaxRate();
    }

    // Test case for an employee with a high tax rate
    @Test
    @DisplayName("generatePayroll handles employee with high tax rate correctly")
    void generatePayroll_highTaxRateEmployee_calculatesCorrectly() {
        int month = 5;
        int year = 2023;
        double grossPay = 10000.0;
        double taxRate = 0.40; // 40% tax
        double expectedTax = grossPay * taxRate;
        double expectedNet = grossPay - expectedTax;

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("EMP004");
        when(emp.getName()).thenReturn("Diana");
        when(emp.getType()).thenReturn("Executive");
        when(emp.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(emp.getTaxRate()).thenReturn(taxRate);

        when(repository.findAll()).thenReturn(Collections.singletonList(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());

        PayrollLine line = result.getPayrollLines().get(0);
        assertEquals(grossPay, line.getGrossPay(), 0.001);
        assertEquals(expectedTax, line.getTaxAmount(), 0.001);
        assertEquals(expectedNet, line.getNetPay(), 0.001);

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
        verify(emp, times(1)).getTaxRate();
    }

    // Test case to ensure calculateGrossPay is called with correct month and year
    @Test
    @DisplayName("generatePayroll passes correct month and year to calculateGrossPay")
    void generatePayroll_passesCorrectMonthAndYearToEmployee() {
        int expectedMonth = 6;
        int expectedYear = 2024;
        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("EMP005");
        when(emp.getName()).thenReturn("Eve");
        when(emp.getType()).thenReturn("Intern");
        when(emp.calculateGrossPay(anyInt(), anyInt())).thenReturn(1000.0); // Return a default value
        when(emp.getTaxRate()).thenReturn(0.10);

        when(repository.findAll()).thenReturn(Collections.singletonList(emp));

        payrollService.generatePayroll(expectedMonth, expectedYear);

        // Verify that calculateGrossPay was called exactly once with the expected month and year
        verify(emp, times(1)).calculateGrossPay(expectedMonth, expectedYear);
        verify(repository, times(1)).findAll();
    }
}