package org.example.payroll.service;

import org.example.payroll.model.Employee;
import org.example.payroll.model.EmployeeType;
import org.example.payroll.model.PayrollLine;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    // Test case: getAllEmployees when repository returns an empty list
    @Test
    void getAllEmployees_emptyRepository_returnsEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result, "Result list should not be null");
        assertTrue(result.isEmpty(), "Result list should be empty");
        verify(repository, times(1)).findAll();
    }

    // Test case: getAllEmployees when repository returns a list of employees
    @Test
    void getAllEmployees_repositoryWithEmployees_returnsAllEmployees() {
        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("E001");
        when(emp1.getName()).thenReturn("Alice");
        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("E002");
        when(emp2.getName()).thenReturn("Bob");

        List<Employee> mockEmployees = Arrays.asList(emp1, emp2);
        when(repository.findAll()).thenReturn(mockEmployees);

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result, "Result list should not be null");
        assertEquals(2, result.size(), "Result list should contain 2 employees");
        assertTrue(result.contains(emp1), "Result should contain emp1");
        assertTrue(result.contains(emp2), "Result should contain emp2");
        assertNotSame(mockEmployees, result, "Should return a new ArrayList, not the original");
        verify(repository, times(1)).findAll();
    }

    // Test case: generatePayroll with an empty employee repository
    @Test
    void generatePayroll_emptyRepository_returnsEmptyPayrollResult() {
        int month = 1;
        int year = 2023;
        when(repository.findAll()).thenReturn(Collections.emptyList());

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result, "PayrollResult should not be null");
        assertEquals(month, result.getMonth(), "Month should match input");
        assertEquals(year, result.getYear(), "Year should match input");
        assertTrue(result.getLines().isEmpty(), "Payroll lines should be empty");
        assertEquals(0.0, result.getTotalGrossPay(), 0.001, "Total gross pay should be 0");
        assertEquals(0.0, result.getTotalTax(), 0.001, "Total tax should be 0");
        assertEquals(0.0, result.getTotalNetPay(), 0.001, "Total net pay should be 0");
        verify(repository, times(1)).findAll();
    }

    // Test case: generatePayroll with multiple employees and positive pay
    @Test
    void generatePayroll_multipleEmployees_calculatesCorrectPayroll() {
        int month = 6;
        int year = 2023;

        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("E001");
        when(emp1.getName()).thenReturn("Alice Smith");
        when(emp1.getType()).thenReturn(EmployeeType.FULL_TIME);
        when(emp1.calculateGrossPay(month, year)).thenReturn(5000.0);
        when(emp1.getTaxRate()).thenReturn(0.20); // 20% tax

        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("E002");
        when(emp2.getName()).thenReturn("Bob Johnson");
        when(emp2.getType()).thenReturn(EmployeeType.PART_TIME);
        when(emp2.calculateGrossPay(month, year)).thenReturn(2500.0);
        when(emp2.getTaxRate()).thenReturn(0.10); // 10% tax

        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(2, result.getLines().size());

        // Verify totals
        assertEquals(7500.0, result.getTotalGrossPay(), 0.001); // 5000 + 2500
        assertEquals(1250.0, result.getTotalTax(), 0.001);    // (5000 * 0.20) + (2500 * 0.10) = 1000 + 250
        assertEquals(6250.0, result.getTotalNetPay(), 0.001); // 7500 - 1250

        // Verify individual payroll lines
        PayrollLine line1 = result.getLines().get(0);
        assertEquals("E001", line1.getEmployeeId());
        assertEquals("Alice Smith", line1.getEmployeeName());
        assertEquals(EmployeeType.FULL_TIME, line1.getEmployeeType());
        assertEquals(5000.0, line1.getGrossPay(), 0.001);
        assertEquals(1000.0, line1.getTax(), 0.001);
        assertEquals(4000.0, line1.getNetPay(), 0.001);
        assertEquals(month, line1.getMonth());
        assertEquals(year, line1.getYear());

        PayrollLine line2 = result.getLines().get(1);
        assertEquals("E002", line2.getEmployeeId());
        assertEquals("Bob Johnson", line2.getEmployeeName());
        assertEquals(EmployeeType.PART_TIME, line2.getEmployeeType());
        assertEquals(2500.0, line2.getGrossPay(), 0.001);
        assertEquals(250.0, line2.getTax(), 0.001);
        assertEquals(2250.0, line2.getNetPay(), 0.001);
        assertEquals(month, line2.getMonth());
        assertEquals(year, line2.getYear());

        verify(repository, times(1)).findAll();
        verify(emp1, times(1)).calculateGrossPay(month, year);
        verify(emp1, times(1)).getTaxRate();
        verify(emp2, times(1)).calculateGrossPay(month, year);
        verify(emp2, times(1)).getTaxRate();
    }

    // Test case: generatePayroll with an employee having zero gross pay
    @Test
    void generatePayroll_employeeWithZeroGrossPay_calculatesCorrectPayroll() {
        int month = 3;
        int year = 2024;

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("E003");
        when(emp.getName()).thenReturn("Charlie Brown");
        when(emp.getType()).thenReturn(EmployeeType.CONTRACTOR);
        when(emp.calculateGrossPay(month, year)).thenReturn(0.0); // No pay for this month
        when(emp.getTaxRate()).thenReturn(0.15); // Tax rate exists but won't be applied to zero gross

        when(repository.findAll()).thenReturn(Collections.singletonList(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getLines().size());

        PayrollLine line = result.getLines().get(0);
        assertEquals(0.0, line.getGrossPay(), 0.001);
        assertEquals(0.0, line.getTax(), 0.001);
        assertEquals(0.0, line.getNetPay(), 0.001);

        assertEquals(0.0, result.getTotalGrossPay(), 0.001);
        assertEquals(0.0, result.getTotalTax(), 0.001);
        assertEquals(0.0, result.getTotalNetPay(), 0.001);

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
        verify(emp, times(1)).getTaxRate();
    }

    // Test case: generatePayroll with an employee having a negative tax rate (though unusual, good to cover)
    // Assuming tax rate can be negative, leading to a negative tax deduction (tax credit)
    @Test
    void generatePayroll_employeeWithNegativeTaxRate_calculatesCorrectPayroll() {
        int month = 1;
        int year = 2023;

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("E004");
        when(emp.getName()).thenReturn("Diana Prince");
        when(emp.getType()).thenReturn(EmployeeType.FULL_TIME);
        when(emp.calculateGrossPay(month, year)).thenReturn(3000.0);
        when(emp.getTaxRate()).thenReturn(-0.05); // 5% tax credit

        when(repository.findAll()).thenReturn(Collections.singletonList(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getLines().size());

        PayrollLine line = result.getLines().get(0);
        assertEquals(3000.0, line.getGrossPay(), 0.001);
        assertEquals(-150.0, line.getTax(), 0.001); // 3000 * -0.05
        assertEquals(3150.0, line.getNetPay(), 0.001); // 3000 - (-150) = 3150

        assertEquals(3000.0, result.getTotalGrossPay(), 0.001);
        assertEquals(-150.0, result.getTotalTax(), 0.001);
        assertEquals(3150.0, result.getTotalNetPay(), 0.001);

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
        verify(emp, times(1)).getTaxRate();
    }

    // Test case: generatePayroll with boundary values for month and year
    @Test
    void generatePayroll_boundaryMonthAndYear_processesCorrectly() {
        int month = 12;
        int year = 1999; // An old year to ensure calculation works

        Employee emp = mock(Employee.class);
        when(emp.getId()).thenReturn("E005");
        when(emp.getName()).thenReturn("Eve Adams");
        when(emp.getType()).thenReturn(EmployeeType.FULL_TIME);
        when(emp.calculateGrossPay(month, year)).thenReturn(4000.0);
        when(emp.getTaxRate()).thenReturn(0.25);

        when(repository.findAll()).thenReturn(Collections.singletonList(emp));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(1, result.getLines().size());

        PayrollLine line = result.getLines().get(0);
        assertEquals(4000.0, line.getGrossPay(), 0.001);
        assertEquals(1000.0, line.getTax(), 0.001); // 4000 * 0.25
        assertEquals(3000.0, line.getNetPay(), 0.001); // 4000 - 1000

        verify(repository, times(1)).findAll();
        verify(emp, times(1)).calculateGrossPay(month, year);
        verify(emp, times(1)).getTaxRate();
    }
}