package org.example.payroll.repository;

import org.example.payroll.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    // Test case for adding a new employee successfully
    @Test
    @DisplayName("should add a new employee successfully")
    void add_addNewEmployee_success() {
        Employee employee1 = new Employee("E001", "Alice");
        repository.add(employee1);

        assertNotNull(repository.findById("E001"), "Employee should be found by ID after adding.");
        assertEquals(1, repository.findAll().size(), "Repository size should be 1 after adding one employee.");
        assertTrue(repository.findAll().contains(employee1), "Repository should contain the added employee.");
    }

    // Test case for adding an employee with an existing ID (update scenario)
    @Test
    @DisplayName("should update employee when adding one with existing ID")
    void add_addEmployeeWithExistingId_updatesEmployee() {
        Employee employee1 = new Employee("E001", "Alice");
        repository.add(employee1);

        Employee employee1Updated = new Employee("E001", "Alicia"); // Same ID, different name
        repository.add(employee1Updated);

        assertEquals(1, repository.findAll().size(), "Repository size should still be 1.");
        Employee foundEmployee = repository.findById("E001");
        assertNotNull(foundEmployee);
        assertEquals("Alicia", foundEmployee.getName(), "Employee name should be updated.");
        assertSame(employee1Updated, foundEmployee, "The employee object should be the updated one.");
    }

    // Test case for adding a null employee
    @Test
    @DisplayName("should throw NullPointerException when adding a null employee")
    void add_addNullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> repository.add(null),
                "Adding a null employee should throw NullPointerException.");
    }

    // Test case for adding an employee with a null ID
    @Test
    @DisplayName("should throw NullPointerException when adding an employee with a null ID")
    void add_addEmployeeWithNullId_throwsNullPointerException() {
        Employee employeeWithNullId = new Employee(null, "Bob");
        assertThrows(NullPointerException.class, () -> repository.add(employeeWithNullId),
                "Adding an employee with a null ID should throw NullPointerException.");
    }

    // Test case for finding all employees in an empty repository
    @Test
    @DisplayName("should return an empty collection when repository is empty")
    void findAll_emptyRepository_returnsEmptyCollection() {
        Collection<Employee> employees = repository.findAll();
        assertNotNull(employees, "findAll should not return null.");
        assertTrue(employees.isEmpty(), "Collection should be empty for an empty repository.");
        assertEquals(0, employees.size(), "Size should be 0 for an empty repository.");
    }

    // Test case for finding all employees in a repository with multiple employees
    @Test
    @DisplayName("should return all employees when repository has multiple employees")
    void findAll_repositoryWithEmployees_returnsAllEmployees() {
        Employee emp1 = new Employee("E001", "Alice");
        Employee emp2 = new Employee("E002", "Bob");
        Employee emp3 = new Employee("E003", "Charlie");

        repository.add(emp1);
        repository.add(emp2);
        repository.add(emp3);

        Collection<Employee> allEmployees = repository.findAll();
        assertNotNull(allEmployees);
        assertEquals(3, allEmployees.size(), "Should return 3 employees.");
        assertTrue(allEmployees.contains(emp1), "Collection should contain emp1.");
        assertTrue(allEmployees.contains(emp2), "Collection should contain emp2.");
        assertTrue(allEmployees.contains(emp3), "Collection should contain emp3.");
    }

    // Test case for verifying the immutability of the returned collection (structural, not content)
    @Test
    @DisplayName("returned collection should be a view and not allow direct modification")
    void findAll_returnedCollectionModification_shouldNotModifyRepository() {
        Employee emp1 = new Employee("E001", "Alice");
        repository.add(emp1);

        Collection<Employee> allEmployees = repository.findAll();
        assertEquals(1, allEmployees.size());

        assertThrows(UnsupportedOperationException.class, () -> allEmployees.clear(),
                "Clearing the returned collection should throw UnsupportedOperationException.");
        assertThrows(UnsupportedOperationException.class, () -> allEmployees.remove(emp1),
                "Removing from the returned collection should throw UnsupportedOperationException.");
        assertThrows(UnsupportedOperationException.class, () -> allEmployees.add(new Employee("E002", "Bob")),
                "Adding to the returned collection should throw UnsupportedOperationException.");

        // Verify the original repository is unaffected
        assertEquals(1, repository.findAll().size(), "Repository size should remain 1.");
        assertTrue(repository.findAll().contains(emp1), "Repository should still contain emp1.");
    }

    // Test case for finding an existing employee by ID
    @Test
    @DisplayName("should return the correct employee when ID exists")
    void findById_existingId_returnsEmployee() {
        Employee employee = new Employee("E001", "Alice");
        repository.add(employee);

        Employee foundEmployee = repository.findById("E001");
        assertNotNull(foundEmployee, "Employee should be found.");
        assertEquals("E001", foundEmployee.getId(), "Found employee ID should match.");
        assertEquals("Alice", foundEmployee.getName(), "Found employee name should match.");
        assertSame(employee, foundEmployee, "Should return the exact same Employee object.");
    }

    // Test case for finding an employee by a non-existent ID
    @Test
    @DisplayName("should return null when ID does not exist")
    void findById_nonExistingId_returnsNull() {
        repository.add(new Employee("E001", "Alice"));
        Employee foundEmployee = repository.findById("E002");
        assertNull(foundEmployee, "Should return null for a non-existent ID.");
    }

    // Test case for finding an employee with a null ID
    @Test
    @DisplayName("should return null when searching with a null ID")
    void findById_nullId_returnsNull() {
        repository.add(new Employee("E001", "Alice"));
        Employee foundEmployee = repository.findById(null);
        assertNull(foundEmployee, "Should return null when searching with a null ID.");
    }

    // Test case for finding an employee with an empty string ID if no such employee exists
    @Test
    @DisplayName("should return null when searching with an empty string ID if no such employee exists")
    void findById_emptyStringId_returnsNullIfNoMatch() {
        repository.add(new Employee("E001", "Alice"));
        Employee foundEmployee = repository.findById("");
        assertNull(foundEmployee, "Should return null for an empty string ID if no match.");
    }

    // Test case for finding an employee with an empty string ID if such employee exists
    @Test
    @DisplayName("should return employee when searching with an empty string ID if such employee exists")
    void findById_emptyStringId_returnsEmployeeIfMatch() {
        Employee emptyIdEmployee = new Employee("", "NoName");
        repository.add(emptyIdEmployee);
        Employee foundEmployee = repository.findById("");
        assertNotNull(foundEmployee, "Should find employee with empty string ID.");
        assertSame(emptyIdEmployee, foundEmployee, "Should return the correct employee.");
    }

    // Test case for clearing a repository with employees
    @Test
    @DisplayName("should clear all employees from the repository")
    void clear_repositoryWithEmployees_clearsAllEmployees() {
        repository.add(new Employee("E001", "Alice"));
        repository.add(new Employee("E002", "Bob"));
        assertEquals(2, repository.findAll().size(), "Repository should have 2 employees before clearing.");

        repository.clear();
        assertTrue(repository.findAll().isEmpty(), "Repository should be empty after clearing.");
        assertEquals(0, repository.findAll().size(), "Repository size should be 0 after clearing.");
        assertNull(repository.findById("E001"), "Employee E001 should not be found after clearing.");
    }

    // Test case for clearing an already empty repository
    @Test
    @DisplayName("should do nothing when clearing an already empty repository")
    void clear_emptyRepository_remainsEmpty() {
        assertTrue(repository.findAll().isEmpty(), "Repository should be empty initially.");
        repository.clear();
        assertTrue(repository.findAll().isEmpty(), "Repository should remain empty after clearing an empty repository.");
    }
}