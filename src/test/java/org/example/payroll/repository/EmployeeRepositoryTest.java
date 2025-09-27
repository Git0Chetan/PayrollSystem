package org.example.payroll.repository;

import org.example.payroll.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository = new EmployeeRepository();
    }

    // Test case: Add a single employee and verify it can be found
    @Test
    @DisplayName("should add a single employee and make it retrievable by ID")
    void add_addOneEmployee_shouldStoreAndBeFindable() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        Collection<Employee> allEmployees = employeeRepository.findAll();
        assertEquals(1, allEmployees.size());
        assertTrue(allEmployees.contains(employee1));
        assertEquals(employee1, employeeRepository.findById("E001"));
    }

    // Test case: Add multiple distinct employees and verify all are stored
    @Test
    @DisplayName("should add multiple distinct employees and make all retrievable")
    void add_addMultipleEmployees_shouldStoreAllAndBeFindable() {
        Employee employee1 = new Employee("E001", "Alice");
        Employee employee2 = new Employee("E002", "Bob");

        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        Collection<Employee> allEmployees = employeeRepository.findAll();
        assertEquals(2, allEmployees.size());
        assertTrue(allEmployees.contains(employee1));
        assertTrue(allEmployees.contains(employee2));
        assertEquals(employee1, employeeRepository.findById("E001"));
        assertEquals(employee2, employeeRepository.findById("E002"));
    }

    // Test case: Add an employee with an ID that already exists, verifying it updates the existing entry
    @Test
    @DisplayName("should update an employee if an employee with the same ID already exists")
    void add_addEmployeeWithExistingId_shouldUpdateExistingEmployee() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        Employee employee1Updated = new Employee("E001", "Alicia"); // Same ID, different name
        employeeRepository.add(employee1Updated);

        assertEquals(1, employeeRepository.findAll().size(), "Repository should still contain only one employee with ID E001");
        assertEquals(employee1Updated, employeeRepository.findById("E001"), "findById should return the updated employee");
        assertNotEquals(employee1, employeeRepository.findById("E001"), "findById should not return the original employee object");
    }

    // Test case: findAll on an empty repository should return an empty collection
    @Test
    @DisplayName("should return an empty collection when the repository is empty")
    void findAll_onEmptyRepository_shouldReturnEmptyCollection() {
        Collection<Employee> allEmployees = employeeRepository.findAll();
        assertTrue(allEmployees.isEmpty());
        assertEquals(0, allEmployees.size());
    }

    // Test case: findAll after adding employees should return all employees
    @Test
    @DisplayName("should return all added employees when the repository is not empty")
    void findAll_afterAddingEmployees_shouldReturnAllEmployees() {
        Employee employee1 = new Employee("E001", "Alice");
        Employee employee2 = new Employee("E002", "Bob");
        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        Collection<Employee> allEmployees = employeeRepository.findAll();
        assertEquals(2, allEmployees.size());
        assertTrue(allEmployees.contains(employee1));
        assertTrue(allEmployees.contains(employee2));
    }

    // Test case: Verify that the collection returned by findAll is a view and reflects changes in the repository
    @Test
    @DisplayName("returned collection should be a live view and reflect internal changes")
    void findAll_returnedCollectionShouldBeLiveView() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        Collection<Employee> allEmployeesView = employeeRepository.findAll();
        assertEquals(1, allEmployeesView.size());
        assertTrue(allEmployeesView.contains(employee1));

        Employee employee2 = new Employee("E002", "Bob");
        employeeRepository.add(employee2); // Add another employee to the repository

        // The view should reflect this change
        assertEquals(2, allEmployeesView.size(), "The view collection should reflect the added employee");
        assertTrue(allEmployeesView.contains(employee2), "The view collection should contain the newly added employee");
    }

    // Test case: Verify that clearing the collection returned by findAll affects the repository
    @Test
    @DisplayName("clearing the returned collection should clear the repository")
    void findAll_clearingReturnedCollection_shouldClearRepository() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        Collection<Employee> allEmployeesView = employeeRepository.findAll();
        assertFalse(allEmployeesView.isEmpty());

        allEmployeesView.clear(); // This should clear the underlying map

        assertTrue(employeeRepository.findAll().isEmpty(), "The repository should be empty after clearing its view");
        assertTrue(allEmployeesView.isEmpty(), "The view itself should also be empty");
    }

    // Test case: Attempting to add to the collection returned by findAll should throw UnsupportedOperationException
    @Test
    @DisplayName("should throw UnsupportedOperationException when attempting to add to the returned collection")
    void findAll_returnedCollection_addShouldThrowUnsupportedOperationException() {
        Collection<Employee> allEmployees = employeeRepository.findAll();
        Employee newEmployee = new Employee("E003", "Charlie");

        assertThrows(UnsupportedOperationException.class, () -> allEmployees.add(newEmployee),
                "Adding to the collection returned by findAll should throw UnsupportedOperationException");
    }

    // Test case: Find an existing employee by ID
    @Test
    @DisplayName("should return the correct employee for an existing ID")
    void findById_withExistingId_shouldReturnCorrectEmployee() {
        Employee employee1 = new Employee("E001", "Alice");
        Employee employee2 = new Employee("E002", "Bob");
        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        assertEquals(employee1, employeeRepository.findById("E001"));
        assertEquals(employee2, employeeRepository.findById("E002"));
    }

    // Test case: Find an employee with a non-existent ID
    @Test
    @DisplayName("should return null for a non-existent ID")
    void findById_withNonExistingId_shouldReturnNull() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        assertNull(employeeRepository.findById("E003"));
    }

    // Test case: Find an employee with a null ID
    @Test
    @DisplayName("should return null when searching with a null ID")
    void findById_withNullId_shouldReturnNull() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        // HashMap.get(null) returns null and does not throw NPE for a null key lookup
        assertNull(employeeRepository.findById(null));
    }

    // Test case: Find an employee with an empty string ID, if it exists
    @Test
    @DisplayName("should return the employee if found with an empty string ID")
    void findById_withEmptyStringId_shouldReturnEmployeeIfFound() {
        Employee employeeEmptyId = new Employee("", "NoID Employee");
        employeeRepository.add(employeeEmptyId);

        assertEquals(employeeEmptyId, employeeRepository.findById(""));
    }

    // Test case: Find an employee with an empty string ID, if it does not exist
    @Test
    @DisplayName("should return null if not found with an empty string ID")
    void findById_withEmptyStringId_shouldReturnNullIfNotFound() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        assertNull(employeeRepository.findById(""));
    }

    // Test case: Clear a non-empty repository
    @Test
    @DisplayName("should clear all employees from a non-empty repository")
    void clear_nonEmptyRepository_shouldMakeItEmpty() {
        Employee employee1 = new Employee("E001", "Alice");
        Employee employee2 = new Employee("E002", "Bob");
        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        assertFalse(employeeRepository.findAll().isEmpty(), "Repository should not be empty before clear");
        assertEquals(2, employeeRepository.findAll().size());

        employeeRepository.clear();

        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should be empty after clear");
        assertNull(employeeRepository.findById("E001"), "Previously added employee should not be found after clear");
    }

    // Test case: Clear an already empty repository
    @Test
    @DisplayName("should do nothing when clearing an already empty repository")
    void clear_emptyRepository_shouldRemainEmpty() {
        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should be empty initially");

        employeeRepository.clear();

        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should still be empty after clearing an empty repository");
    }

    // Test case: After clearing, previously retrievable employees should no longer be found
    @Test
    @DisplayName("after clearing, previously retrievable employees should no longer be found by ID")
    void clear_afterClearing_findByIdShouldReturnNull() {
        Employee employee1 = new Employee("E001", "Alice");
        employeeRepository.add(employee1);

        assertNotNull(employeeRepository.findById("E001"), "Employee should be found before clear");

        employeeRepository.clear();

        assertNull(employeeRepository.findById("E001"), "Employee should not be found after clear");
    }
}