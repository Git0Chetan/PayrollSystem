package org.example.payroll.repository;

import org.example.payroll.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmployeeRepository Tests")
class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    // Test case for adding a single employee to an empty repository.
    @Test
    void add_ShouldAddSingleEmployee() {
        Employee employee = new Employee("E001", "Alice", 60000.0);
        repository.add(employee);

        assertEquals(1, repository.findAll().size());
        assertEquals(employee, repository.findById("E001"));
    }

    // Test case for adding multiple employees to the repository.
    @Test
    void add_ShouldAddMultipleEmployees() {
        Employee emp1 = new Employee("E001", "Alice", 60000.0);
        Employee emp2 = new Employee("E002", "Bob", 70000.0);
        repository.add(emp1);
        repository.add(emp2);

        assertEquals(2, repository.findAll().size());
        assertTrue(repository.findAll().contains(emp1));
        assertTrue(repository.findAll().contains(emp2));
    }

    // Test case for adding an employee with an ID that already exists, which should overwrite the old one.
    @Test
    void add_ShouldOverwriteExistingEmployee_WhenSameId() {
        Employee emp1 = new Employee("E001", "Alice", 60000.0);
        Employee emp2 = new Employee("E001", "Alicia", 65000.0); // Same ID, different name/salary

        repository.add(emp1);
        assertEquals(1, repository.findAll().size());
        assertEquals("Alice", repository.findById("E001").getName());

        repository.add(emp2); // Overwrite
        assertEquals(1, repository.findAll().size());
        assertEquals("Alicia", repository.findById("E001").getName());
        assertEquals(emp2, repository.findById("E001"));
    }

    // Test case for attempting to add a null employee.
    @Test
    void add_ShouldThrowNullPointerException_WhenEmployeeIsNull() {
        assertThrows(NullPointerException.class, () -> repository.add(null));
    }

    // Test case for adding an employee with a null ID.
    @Test
    void add_ShouldHandleEmployeeWithNullId() {
        Employee empWithNullId = new Employee(null, "Charlie", 55000.0);
        repository.add(empWithNullId);

        assertEquals(1, repository.findAll().size());
        assertEquals(empWithNullId, repository.findById(null));
    }


    // Test case for retrieving all employees when the repository is empty.
    @Test
    void findAll_ShouldReturnEmptyCollection_WhenRepositoryIsEmpty() {
        Collection<Employee> employees = repository.findAll();
        assertNotNull(employees);
        assertTrue(employees.isEmpty());
        assertEquals(0, employees.size());
    }

    // Test case for retrieving all employees when the repository contains multiple employees.
    @Test
    void findAll_ShouldReturnAllEmployees_WhenRepositoryHasEmployees() {
        Employee emp1 = new Employee("E001", "Alice", 60000.0);
        Employee emp2 = new Employee("E002", "Bob", 70000.0);
        repository.add(emp1);
        repository.add(emp2);

        Collection<Employee> allEmployees = repository.findAll();
        assertEquals(2, allEmployees.size());
        assertTrue(allEmployees.containsAll(Arrays.asList(emp1, emp2)));
    }

    // Test case to verify that findAll returns a live view, not a copy.
    @Test
    void findAll_ShouldReturnLiveViewOfEmployees() {
        Employee emp1 = new Employee("E001", "Alice", 60000.0);
        repository.add(emp1);

        Collection<Employee> employeesView = repository.findAll();
        assertEquals(1, employeesView.size());

        Employee emp2 = new Employee("E002", "Bob", 70000.0);
        repository.add(emp2); // Add another employee directly to the repository

        // The view should reflect the change immediately
        assertEquals(2, employeesView.size());
        assertTrue(employeesView.contains(emp2));

        // Ensure the returned collection cannot be modified directly (optional, HashMap.values() might or might not throw UOE)
        assertThrows(UnsupportedOperationException.class, () -> employeesView.add(new Employee("E003", "Eve", 80000.0)));
    }


    // Test case for finding an existing employee by ID.
    @Test
    void findById_ShouldReturnEmployee_WhenIdExists() {
        Employee emp = new Employee("E001", "Alice", 60000.0);
        repository.add(emp);

        Employee foundEmployee = repository.findById("E001");
        assertNotNull(foundEmployee);
        assertEquals(emp, foundEmployee);
    }

    // Test case for finding an employee with an ID that does not exist.
    @Test
    void findById_ShouldReturnNull_WhenIdDoesNotExist() {
        repository.add(new Employee("E001", "Alice", 60000.0));

        Employee foundEmployee = repository.findById("NonExistentId");
        assertNull(foundEmployee);
    }

    // Test case for finding an employee by a null ID.
    @Test
    void findById_ShouldReturnNull_WhenIdIsNull() {
        repository.add(new Employee("E001", "Alice", 60000.0)); // Add some employee
        repository.add(new Employee(null, "Charlie", 55000.0)); // Add an employee with null ID

        Employee foundEmployee = repository.findById(null);
        assertNotNull(foundEmployee);
        assertEquals("Charlie", foundEmployee.getName()); // Verify it finds the null-keyed employee
    }

    // Test case for finding an employee by ID when the repository is empty.
    @Test
    void findById_ShouldReturnNull_WhenRepositoryIsEmpty() {
        Employee foundEmployee = repository.findById("E001");
        assertNull(foundEmployee);
    }


    // Test case for clearing a repository that contains employees.
    @Test
    void clear_ShouldEmptyRepository_WhenItContainsEmployees() {
        repository.add(new Employee("E001", "Alice", 60000.0));
        repository.add(new Employee("E002", "Bob", 70000.0));
        assertEquals(2, repository.findAll().size());

        repository.clear();
        assertTrue(repository.findAll().isEmpty());
        assertEquals(0, repository.findAll().size());
        assertNull(repository.findById("E001")); // Verify specific employee is gone
    }

    // Test case for clearing an already empty repository.
    @Test
    void clear_ShouldDoNothing_WhenRepositoryIsEmpty() {
        assertTrue(repository.findAll().isEmpty());

        repository.clear();
        assertTrue(repository.findAll().isEmpty());
        assertEquals(0, repository.findAll().size());
    }

    // Test case to ensure subsequent finds after clear return empty.
    @Test
    void clear_ShouldEnsureFindAllReturnsEmpty() {
        repository.add(new Employee("E001", "Alice", 60000.0));
        repository.clear();
        assertTrue(repository.findAll().isEmpty());

        // Try adding again after clear
        repository.add(new Employee("E003", "David", 75000.0));
        assertEquals(1, repository.findAll().size());
        assertNotNull(repository.findById("E003"));
    }
}