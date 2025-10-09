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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollServiceTest {

    @Mock
    private EmployeeRepository mockRepository;

    @InjectMocks
    private PayrollService payrollService;

    // Test case for when no employees are present in the repository
    @Test
    @DisplayName("getAllEmployees should return an empty list when repository is empty")
    void getAllEmployees_emptyRepository_returnsEmptyList() {
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mockRepository, times(1)).findAll();
    }

    // Test case for when multiple employees are present in the repository
    @Test
    @DisplayName("getAllEmployees should return all employees from the repository")
    void getAllEmployees_withEmployees_returnsAllEmployees() {
        Employee emp1 = mock(Employee.class);
        Employee emp2 = mock(Employee.class);

        when(mockRepository.findAll()).thenReturn(List.of(emp1, emp2));

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(emp1));
        assertTrue(result.contains(emp2));
        // Verify that a new list instance is returned, not the repository's internal list
        assertNotSame(mockRepository.findAll(), result);
        verify(mockRepository, times(1)).findAll();
    }

    // Test case for an empty repository
    @Test
    @DisplayName("generatePayroll should return an empty PayrollResult when no employees exist")
    void generatePayroll_emptyRepository_returnsEmptyPayrollResult() {
        int month = 1;
        int year = 2023;
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertTrue(result.getPayrollLines().isEmpty());
        verify(mockRepository, times(1)).findAll();
    }

    // Test case for a single employee with positive gross pay and tax
    @Test
    @DisplayName("generatePayroll should correctly calculate payroll for a single employee")
    void generatePayroll_singleEmployee_calculatesCorrectly() {
        int month = 2;
        int year = 2024;
        String employeeId = "E001";
        String employeeName = "Alice Smith";
        String employeeType = "CONTRACTOR"; // Assuming EmployeeType is a String

        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getId()).thenReturn(employeeId);
        when(mockEmployee.getName()).thenReturn(employeeName);
        when(mockEmployee.getType()).thenReturn(employeeType);
        when(mockEmployee.calculateGrossPay(month, year)).thenReturn(6000.0);
        when(mockEmployee.getTaxRate()).thenReturn(0.15); // 15% tax

        when(mockRepository.findAll()).thenReturn(List.of(mockEmployee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(1, result.getPayrollLines().size());

        PayrollLine line = result.getPayrollLines().get(0);
        assertEquals(employeeId, line.getEmployeeId());
        assertEquals(employeeName, line.getEmployeeName());
        assertEquals(employeeType, line.getEmployeeType());
        assertEquals(6000.0, line.getGrossPay(), 0.001); // Using delta for double comparison
        assertEquals(6000.0 * 0.15, line.getTaxAmount(), 0.001);
        assertEquals(6000.0 - (6000.0 * 0.15), line.getNetPay(), 0.001);
        assertEquals(month, line.getMonth());
        assertEquals(year, line.getYear());

        verify(mockRepository, times(1)).findAll();
        verify(mockEmployee, times(1)).calculateGrossPay(month, year);
        verify(mockEmployee, times(1)).getTaxRate();
    }

    // Test case for multiple employees with varying pay and tax rates
    @Test
    @DisplayName("generatePayroll should correctly calculate payroll for multiple employees")
    void generatePayroll_multipleEmployees_calculatesAllCorrectly() {
        int month = 3;
        int year = 2024;

        Employee emp1 = mock(Employee.class);
        when(emp1.getId()).thenReturn("E001");
        when(emp1.getName()).thenReturn("Alice");
        when(emp1.getType()).thenReturn("FULL_TIME");
        when(emp1.calculateGrossPay(month, year)).thenReturn(5000.0);
        when(emp1.getTaxRate()).thenReturn(0.20); // 20% tax

        Employee emp2 = mock(Employee.class);
        when(emp2.getId()).thenReturn("E002");
        when(emp2.getName()).thenReturn("Bob");
        when(emp2.getType()).thenReturn("PART_TIME");
        when(emp2.calculateGrossPay(month, year)).thenReturn(2000.0);
        when(emp2.getTaxRate()).thenReturn(0.10); // 10% tax

        when(mockRepository.findAll()).thenReturn(List.of(emp1, emp2));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(2, result.getPayrollLines().size());

        // Verify first employee's payroll line
        PayrollLine line1 = result.getPayrollLines().stream()
                .filter(l -> l.getEmployeeId().equals("E001"))
                .findFirst().orElse(null);
        assertNotNull(line1);
        assertEquals("Alice", line1.getEmployeeName());
        assertEquals(5000.0, line1.getGrossPay(), 0.001);
        assertEquals(1000.0, line1.getTaxAmount(), 0.001); // 5000 * 0.20
        assertEquals(4000.0, line1.getNetPay(), 0.001);   // 5000 - 1000

        // Verify second employee's payroll line
        PayrollLine line2 = result.getPayrollLines().stream()
                .filter(l -> l.getEmployeeId().equals("E002"))
                .findFirst().orElse(null);
        assertNotNull(line2);
        assertEquals("Bob", line2.getEmployeeName());
        assertEquals(2000.0, line2.getGrossPay(), 0.001);
        assertEquals(200.0, line2.getTaxAmount(), 0.001); // 2000 * 0.10
        assertEquals(1800.0, line2.getNetPay(), 0.001);   // 2000 - 200

        verify(mockRepository, times(1)).findAll();
        verify(emp1, times(1)).calculateGrossPay(month, year);
        verify(emp1, times(1)).getTaxRate();
        verify(emp2, times(1)).calculateGrossPay(month, year);
        verify(emp2, times(1)).getTaxRate();
    }

    // Test case for an employee with zero gross pay
    @Test
    @DisplayName("generatePayroll should handle employees with zero gross pay")
    void generatePayroll_employeeWithZeroGrossPay_calculatesCorrectly() {
        int month = 4;
        int year = 2024;

        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getId()).thenReturn("E003");
        when(mockEmployee.getName()).thenReturn("Charlie");
        when(mockEmployee.getType()).thenReturn("INTERN");
        when(mockEmployee.calculateGrossPay(month, year)).thenReturn(0.0); // Zero gross pay
        when(mockEmployee.getTaxRate()).thenReturn(0.0); // Zero tax rate, or any rate, should result in zero tax

        when(mockRepository.findAll()).thenReturn(List.of(mockEmployee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());
        PayrollLine line = result.getPayrollLines().get(0);

        assertEquals(0.0, line.getGrossPay(), 0.001);
        assertEquals(0.0, line.getTaxAmount(), 0.001);
        assertEquals(0.0, line.getNetPay(), 0.001);

        verify(mockEmployee, times(1)).calculateGrossPay(month, year);
        // getTaxRate is called even if gross is 0, as per current implementation
        verify(mockEmployee, times(1)).getTaxRate(); 
    }

    // Test case for employee with maximum tax rate (1.0)
    @Test
    @DisplayName("generatePayroll should handle employee with 100% tax rate")
    void generatePayroll_employeeWithOneHundredPercentTaxRate_calculatesCorrectly() {
        int month = 5;
        int year = 2024;
        double grossPay = 1000.0;
        double taxRate = 1.0; // 100% tax

        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getId()).thenReturn("E004");
        when(mockEmployee.getName()).thenReturn("David");
        when(mockEmployee.getType()).thenReturn("FULL_TIME");
        when(mockEmployee.calculateGrossPay(month, year)).thenReturn(grossPay);
        when(mockEmployee.getTaxRate()).thenReturn(taxRate);

        when(mockRepository.findAll()).thenReturn(List.of(mockEmployee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());
        PayrollLine line = result.getPayrollLines().get(0);

        assertEquals(grossPay, line.getGrossPay(), 0.001);
        assertEquals(grossPay * taxRate, line.getTaxAmount(), 0.001);
        assertEquals(0.0, line.getNetPay(), 0.001); // All gross pay goes to tax

        verify(mockEmployee, times(1)).calculateGrossPay(month, year);
        verify(mockEmployee, times(1)).getTaxRate();
    }

    // Test case for verifying month and year passed to calculateGrossPay
    @Test
    @DisplayName("generatePayroll should pass correct month and year to employee's calculateGrossPay")
    void generatePayroll_passesCorrectMonthYearToCalculateGrossPay() {
        int month = 6;
        int year = 2024;

        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getId()).thenReturn("E005");
        when(mockEmployee.getName()).thenReturn("Eve");
        when(mockEmployee.getType()).thenReturn("FREELANCE");
        // We only care about the call, not the return value for this specific test
        when(mockEmployee.calculateGrossPay(anyInt(), anyInt())).thenReturn(100.0); 
        when(mockEmployee.getTaxRate()).thenReturn(0.1);

        when(mockRepository.findAll()).thenReturn(List.of(mockEmployee));

        payrollService.generatePayroll(month, year);

        // Verify that calculateGrossPay was called exactly once with the specified month and year
        verify(mockEmployee, times(1)).calculateGrossPay(month, year);
    }
}