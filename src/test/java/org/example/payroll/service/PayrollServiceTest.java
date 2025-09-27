package org.example.payroll.service;

import org.example.payroll.model.Employee;
import org.example.payroll.model.PayrollLine;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects; // Needed for Objects.equals if PayrollLine had it

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PayrollServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private PayrollService payrollService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for getAllEmployees()

    // Test case for retrieving all employees when the repository is empty
    @Test
    void getAllEmployees_returnsEmptyList_whenNoEmployeesExist() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> employees = payrollService.getAllEmployees();

        assertNotNull(employees);
        assertTrue(employees.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }

    // Test case for retrieving all employees when multiple employees exist
    @Test
    void getAllEmployees_returnsAllEmployees_whenEmployeesExist() {
        Employee emp1 = mock(Employee.class);
        Employee emp2 = mock(Employee.class);
        List<Employee> mockEmployees = Arrays.asList(emp1, emp2);

        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        List<Employee> employees = payrollService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertTrue(employees.contains(emp1));
        assertTrue(employees.contains(emp2));
        // Verify that the returned list is a new ArrayList instance, as per the service implementation
        assertNotSame(mockEmployees, employees);
        // Verify content equality
        assertEquals(mockEmployees, employees);

        verify(employeeRepository, times(1)).findAll();
    }


    // Test cases for generatePayroll(int month, int year)

    // Test case for generating payroll with no employees in the repository
    @Test
    void generatePayroll_returnsEmptyPayrollResult_whenNoEmployeesExist() {
        int month = 1;
        int year = 2023;
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertTrue(result.getLines().isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }

    // Test case for generating payroll with a single employee (happy path)
    @Test
    void generatePayroll_calculatesCorrectly_forSingleEmployee() {
        int month = 3;
        int year = 2024;
        String empId = "E001";
        String empName = "John Doe";
        String empType = "FULL_TIME";
        double grossPay = 5000.0;
        double taxRate = 0.20;
        double expectedTax = grossPay * taxRate;
        double expectedNet = grossPay - expectedTax;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(empId);
        when(employee.getName()).thenReturn(empName);
        when(employee.getType()).thenReturn(empType);
        when(employee.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(employee.getTaxRate()).thenReturn(taxRate);

        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(1, result.getLines().size());

        PayrollLine line = result.getLines().get(0);
        assertEquals(empId, line.getEmployeeId());
        assertEquals(empName, line.getEmployeeName());
        assertEquals(empType, line.getEmployeeType());
        assertEquals(grossPay, line.getGrossPay(), 0.001); // Use delta for double comparisons
        assertEquals(expectedTax, line.getTaxAmount(), 0.001);
        assertEquals(expectedNet, line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(employeeRepository, times(1)).findAll();
        verify(employee, times(1)).getId();
        verify(employee, times(1)).getName();
        verify(employee, times(1)).getType();
        verify(employee, times(1)).calculateGrossPay(month, year);
        verify(employee, times(1)).getTaxRate();
    }

    // Test case for generating payroll with multiple employees
    @Test
    void generatePayroll_calculatesCorrectly_forMultipleEmployees() {
        int month = 4;
        int year = 2023;

        // Employee 1 setup
        String empId1 = "E001";
        String empName1 = "Alice Smith";
        String empType1 = "FULL_TIME";
        double grossPay1 = 6000.0;
        double taxRate1 = 0.25;
        double expectedTax1 = grossPay1 * taxRate1;
        double expectedNet1 = grossPay1 - expectedTax1;

        Employee employee1 = mock(Employee.class);
        when(employee1.getId()).thenReturn(empId1);
        when(employee1.getName()).thenReturn(empName1);
        when(employee1.getType()).thenReturn(empType1);
        when(employee1.calculateGrossPay(month, year)).thenReturn(grossPay1);
        when(employee1.getTaxRate()).thenReturn(taxRate1);

        // Employee 2 setup
        String empId2 = "E002";
        String empName2 = "Bob Johnson";
        String empType2 = "PART_TIME";
        double grossPay2 = 2500.0;
        double taxRate2 = 0.10;
        double expectedTax2 = grossPay2 * taxRate2;
        double expectedNet2 = grossPay2 - expectedTax2;

        Employee employee2 = mock(Employee.class);
        when(employee2.getId()).thenReturn(empId2);
        when(employee2.getName()).thenReturn(empName2);
        when(employee2.getType()).thenReturn(empType2);
        when(employee2.calculateGrossPay(month, year)).thenReturn(grossPay2);
        when(employee2.getTaxRate()).thenReturn(taxRate2);

        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(2, result.getLines().size());

        // Verify line for Employee 1
        PayrollLine line1 = result.getLines().stream()
                .filter(l -> l.getEmployeeId().equals(empId1))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Payroll line for E001 not found"));
        assertEquals(empName1, line1.getEmployeeName());
        assertEquals(empType1, line1.getEmployeeType());
        assertEquals(grossPay1, line1.getGrossPay(), 0.001);
        assertEquals(expectedTax1, line1.getTaxAmount(), 0.001);
        assertEquals(expectedNet1, line1.getNetPay(), 0.001);
        assertEquals(month, line1.getMonth());
        assertEquals(year, line1.getYear());

        // Verify line for Employee 2
        PayrollLine line2 = result.getLines().stream()
                .filter(l -> l.getEmployeeId().equals(empId2))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Payroll line for E002 not found"));
        assertEquals(empName2, line2.getEmployeeName());
        assertEquals(empType2, line2.getEmployeeType());
        assertEquals(grossPay2, line2.getGrossPay(), 0.001);
        assertEquals(expectedTax2, line2.getTaxAmount(), 0.001);
        assertEquals(expectedNet2, line2.getNetPay(), 0.001);
        assertEquals(month, line2.getMonth());
        assertEquals(year, line2.getYear());

        verify(employeeRepository, times(1)).findAll();
        verify(employee1, times(1)).getId();
        verify(employee1, times(1)).getName();
        verify(employee1, times(1)).getType();
        verify(employee1, times(1)).calculateGrossPay(month, year);
        verify(employee1, times(1)).getTaxRate();
        verify(employee2, times(1)).getId();
        verify(employee2, times(1)).getName();
        verify(employee2, times(1)).getType();
        verify(employee2, times(1)).calculateGrossPay(month, year);
        verify(employee2, times(1)).getTaxRate();
    }

    // Test case for generating payroll when an employee has zero gross pay
    @Test
    void generatePayroll_handlesZeroGrossPayCorrectly() {
        int month = 5;
        int year = 2024;
        String empId = "E003";
        String empName = "Jane Doe";
        String empType = "CONTRACTOR";
        double grossPay = 0.0; // Employee had no pay this month
        double taxRate = 0.15; // Tax rate doesn't matter if gross is 0
        double expectedTax = 0.0;
        double expectedNet = 0.0;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(empId);
        when(employee.getName()).thenReturn(empName);
        when(employee.getType()).thenReturn(empType);
        when(employee.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(employee.getTaxRate()).thenReturn(taxRate);

        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getLines().size());
        PayrollLine line = result.getLines().get(0);

        assertEquals(empId, line.getEmployeeId());
        assertEquals(grossPay, line.getGrossPay(), 0.001);
        assertEquals(expectedTax, line.getTaxAmount(), 0.001);
        assertEquals(expectedNet, line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(employeeRepository, times(1)).findAll();
        verify(employee, times(1)).getId();
        verify(employee, times(1)).getName();
        verify(employee, times(1)).getType();
        verify(employee, times(1)).calculateGrossPay(month, year);
        verify(employee, times(1)).getTaxRate();
    }

    // Test case for generating payroll when an employee has zero tax rate
    @Test
    void generatePayroll_handlesZeroTaxRateCorrectly() {
        int month = 6;
        int year = 2024;
        String empId = "E004";
        String empName = "Peter Pan";
        String empType = "INTERN";
        double grossPay = 1000.0;
        double taxRate = 0.0; // No tax
        double expectedTax = 0.0;
        double expectedNet = grossPay;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(empId);
        when(employee.getName()).thenReturn(empName);
        when(employee.getType()).thenReturn(empType);
        when(employee.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(employee.getTaxRate()).thenReturn(taxRate);

        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getLines().size());
        PayrollLine line = result.getLines().get(0);

        assertEquals(empId, line.getEmployeeId());
        assertEquals(grossPay, line.getGrossPay(), 0.001);
        assertEquals(expectedTax, line.getTaxAmount(), 0.001);
        assertEquals(expectedNet, line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(employeeRepository, times(1)).findAll();
        verify(employee, times(1)).getId();
        verify(employee, times(1)).getName();
        verify(employee, times(1)).getType();
        verify(employee, times(1)).calculateGrossPay(month, year);
        verify(employee, times(1)).getTaxRate();
    }

    // Test case for generating payroll with a very high tax rate
    @Test
    void generatePayroll_handlesHighTaxRate() {
        int month = 8;
        int year = 2024;
        String empId = "E006";
        String empName = "High Taxer";
        String empType = "EXECUTIVE";
        double grossPay = 10000.0;
        double taxRate = 0.90; // Extremely high tax
        double expectedTax = grossPay * taxRate;
        double expectedNet = grossPay - expectedTax;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(empId);
        when(employee.getName()).thenReturn(empName);
        when(employee.getType()).thenReturn(empType);
        when(employee.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(employee.getTaxRate()).thenReturn(taxRate);

        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getLines().size());
        PayrollLine line = result.getLines().get(0);

        assertEquals(empId, line.getEmployeeId());
        assertEquals(grossPay, line.getGrossPay(), 0.001);
        assertEquals(expectedTax, line.getTaxAmount(), 0.001);
        assertEquals(expectedNet, line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(employeeRepository, times(1)).findAll();
        verify(employee, times(1)).getId();
        verify(employee, times(1)).getName();
        verify(employee, times(1)).getType();
        verify(employee, times(1)).calculateGrossPay(month, year);
        verify(employee, times(1)).getTaxRate();
    }
}