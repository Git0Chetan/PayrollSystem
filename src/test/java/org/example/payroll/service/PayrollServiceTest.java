package org.example.payroll.service;

import org.example.payroll.model.Employee;
import org.example.payroll.model.EmployeeType;
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

    // This is typically handled by @ExtendWith(MockitoExtension.class) for JUnit 5,
    // but explicit call can be kept for clarity or older patterns if desired.
    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); // Not strictly needed with @ExtendWith(MockitoExtension.class)
    }

    // Test case for generating payroll with multiple employees, happy path
    @Test
    @DisplayName("should generate payroll correctly for multiple employees")
    void generatePayroll_multipleEmployees_success() {
        int month = 1;
        int year = 2023;

        Employee employee1 = mock(Employee.class);
        when(employee1.getId()).thenReturn("E001");
        when(employee1.getName()).thenReturn("Alice Smith");
        when(employee1.getType()).thenReturn(EmployeeType.SALARIED);
        when(employee1.calculateGrossPay(month, year)).thenReturn(5000.0);
        when(employee1.getTaxRate()).thenReturn(0.20); // 20% tax

        Employee employee2 = mock(Employee.class);
        when(employee2.getId()).thenReturn("E002");
        when(employee2.getName()).thenReturn("Bob Johnson");
        when(employee2.getType()).thenReturn(EmployeeType.HOURLY);
        when(employee2.calculateGrossPay(month, year)).thenReturn(3000.0);
        when(employee2.getTaxRate()).thenReturn(0.15); // 15% tax

        when(mockRepository.findAll()).thenReturn(List.of(employee1, employee2));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertEquals(2, result.getPayrollLines().size());

        PayrollLine line1 = result.getPayrollLines().get(0);
        assertEquals("E001", line1.getEmployeeId());
        assertEquals("Alice Smith", line1.getEmployeeName());
        assertEquals(EmployeeType.SALARIED, line1.getEmployeeType());
        assertEquals(5000.0, line1.getGrossPay(), 0.001);
        assertEquals(1000.0, line1.getTaxAmount(), 0.001); // 5000 * 0.20
        assertEquals(4000.0, line1.getNetPay(), 0.001); // 5000 - 1000
        assertEquals(month, line1.getMonth());
        assertEquals(year, line1.getYear());


        PayrollLine line2 = result.getPayrollLines().get(1);
        assertEquals("E002", line2.getEmployeeId());
        assertEquals("Bob Johnson", line2.getEmployeeName());
        assertEquals(EmployeeType.HOURLY, line2.getEmployeeType());
        assertEquals(3000.0, line2.getGrossPay(), 0.001);
        assertEquals(450.0, line2.getTaxAmount(), 0.001); // 3000 * 0.15
        assertEquals(2550.0, line2.getNetPay(), 0.001); // 3000 - 450
        assertEquals(month, line2.getMonth());
        assertEquals(year, line2.getYear());

        verify(mockRepository, times(1)).findAll();
        verify(employee1, times(1)).calculateGrossPay(month, year);
        verify(employee1, times(1)).getTaxRate();
        verify(employee2, times(1)).calculateGrossPay(month, year);
        verify(employee2, times(1)).getTaxRate();
    }

    // Test case for generating payroll with no employees in the repository
    @Test
    @DisplayName("should generate an empty payroll result when no employees exist")
    void generatePayroll_noEmployees_emptyResult() {
        int month = 2;
        int year = 2023;

        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(month, result.getMonth());
        assertEquals(year, result.getYear());
        assertTrue(result.getPayrollLines().isEmpty());

        verify(mockRepository, times(1)).findAll();
    }

    // Test case for an employee with zero gross pay
    @Test
    @DisplayName("should handle employee with zero gross pay correctly")
    void generatePayroll_employeeWithZeroGrossPay_correctCalculations() {
        int month = 3;
        int year = 2023;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn("E003");
        when(employee.getName()).thenReturn("Charlie");
        when(employee.getType()).thenReturn(EmployeeType.CONTRACTOR);
        when(employee.calculateGrossPay(month, year)).thenReturn(0.0);
        when(employee.getTaxRate()).thenReturn(0.10);

        when(mockRepository.findAll()).thenReturn(List.of(employee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());

        PayrollLine line = result.getPayrollLines().get(0);
        assertEquals("E003", line.getEmployeeId());
        assertEquals(0.0, line.getGrossPay(), 0.001);
        assertEquals(0.0, line.getTaxAmount(), 0.001);
        assertEquals(0.0, line.getNetPay(), 0.001);

        verify(mockRepository, times(1)).findAll();
        verify(employee, times(1)).calculateGrossPay(month, year);
        verify(employee, times(1)).getTaxRate();
    }

    // Test case for an employee with zero tax rate
    @Test
    @DisplayName("should handle employee with zero tax rate correctly")
    void generatePayroll_employeeWithZeroTaxRate_correctCalculations() {
        int month = 4;
        int year = 2023;

        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn("E004");
        when(employee.getName()).thenReturn("Diana");
        when(employee.getType()).thenReturn(EmployeeType.SALARIED);
        when(employee.calculateGrossPay(month, year)).thenReturn(6000.0);
        when(employee.getTaxRate()).thenReturn(0.0); // No tax

        when(mockRepository.findAll()).thenReturn(List.of(employee));

        PayrollResult result = payrollService.generatePayroll(month, year);

        assertNotNull(result);
        assertEquals(1, result.getPayrollLines().size());

        PayrollLine line = result.getPayrollLines().get(0);
        assertEquals("E004", line.getEmployeeId());
        assertEquals(6000.0, line.getGrossPay(), 0.001);
        assertEquals(0.0, line.getTaxAmount(), 0.001);
        assertEquals(6000.0, line.getNetPay(), 0.001);

        verify(mockRepository, times(1)).findAll();
        verify(employee, times(1)).calculateGrossPay(month, year);
        verify(employee, times(1)).getTaxRate();
    }

    // Test case for `getAllEmployees` with multiple employees
    @Test
    @DisplayName("should return all employees from the repository")
    void getAllEmployees_multipleEmployees_returnsAll() {
        Employee employee1 = mock(Employee.class);
        when(employee1.getId()).thenReturn("E001");
        Employee employee2 = mock(Employee.class);
        when(employee2.getId()).thenReturn("E002");

        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(employee1);
        mockEmployees.add(employee2);

        when(mockRepository.findAll()).thenReturn(mockEmployees);

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(employee1));
        assertTrue(result.contains(employee2));
        assertNotSame(mockEmployees, result, "Should return a new ArrayList, not the original");

        verify(mockRepository, times(1)).findAll();
    }

    // Test case for `getAllEmployees` when the repository is empty
    @Test
    @DisplayName("should return an empty list when no employees exist")
    void getAllEmployees_noEmployees_returnsEmptyList() {
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = payrollService.getAllEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

        verify(mockRepository, times(1)).findAll();
    }

    // Test case for `getAllEmployees` ensuring the returned list is modifiable and distinct
    @Test
    @DisplayName("should return a modifiable list distinct from repository's internal list")
    void getAllEmployees_returnsModifiableDistinctList() {
        Employee employee1 = mock(Employee.class);
        when(employee1.getId()).thenReturn("E001");

        List<Employee> originalRepositoryList = new ArrayList<>();
        originalRepositoryList.add(employee1);

        when(mockRepository.findAll()).thenReturn(originalRepositoryList);

        List<Employee> returnedList = payrollService.getAllEmployees();

        assertNotNull(returnedList);
        assertEquals(1, returnedList.size());
        assertTrue(returnedList.contains(employee1));

        // Verify it's a new list instance
        assertNotSame(originalRepositoryList, returnedList);

        // Verify it's modifiable
        Employee newEmployee = mock(Employee.class);
        when(newEmployee.getId()).thenReturn("E005");
        assertDoesNotThrow(() -> returnedList.add(newEmployee));
        assertEquals(2, returnedList.size());
        assertTrue(returnedList.contains(newEmployee));

        // Original list should remain unchanged
        assertEquals(1, originalRepositoryList.size());
        assertFalse(originalRepositoryList.contains(newEmployee));

        verify(mockRepository, times(1)).findAll();
    }
}