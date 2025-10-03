package org.example.payroll.repository;

import org.example.payroll.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository = new EmployeeRepository();
    }

    // Test cases for add method

    // Test case for adding a single employee
    @Test
    @DisplayName("Should add a single employee successfully")
    void testAdd_singleEmployee_success() {
        Employee employee = new Employee("EMP001", "Alice Smith");
        employeeRepository.add(employee);

        assertNotNull(employeeRepository.findById("EMP001"), "Employee should be found by ID after adding.");
        assertEquals(employee, employeeRepository.findById("EMP001"), "Found employee should be the same as the added one.");
        assertEquals(1, employeeRepository.findAll().size(), "Repository should contain exactly one employee.");
        assertTrue(employeeRepository.findAll().contains(employee), "The added employee should be present in the collection.");
    }

    // Test case for adding multiple unique employees
    @Test
    @DisplayName("Should add multiple unique employees successfully")
    void testAdd_multipleUniqueEmployees_success() {
        Employee employee1 = new Employee("EMP001", "Alice Smith");
        Employee employee2 = new Employee("EMP002", "Bob Johnson");

        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        assertEquals(2, employeeRepository.findAll().size(), "Repository should contain two employees.");
        assertTrue(employeeRepository.findAll().contains(employee1), "Employee 1 should be present.");
        assertTrue(employeeRepository.findAll().contains(employee2), "Employee 2 should be present.");
    }

    // Test case for adding an employee with an existing ID (update scenario)
    @Test
    @DisplayName("Should update an existing employee when adding an employee with the same ID")
    void testAdd_existingId_updatesEmployee() {
        Employee originalEmployee = new Employee("EMP001", "Alice Smith");
        Employee updatedEmployee = new Employee("EMP001", "Alice Williams"); // Same ID, different name

        employeeRepository.add(originalEmployee);
        assertEquals(1, employeeRepository.findAll().size(), "Repository should initially have one employee.");
        assertEquals(originalEmployee, employeeRepository.findById("EMP001"), "Original employee should be found.");

        employeeRepository.add(updatedEmployee); // Add with same ID
        assertEquals(1, employeeRepository.findAll().size(), "Repository size should remain one after update.");
        assertNotNull(employeeRepository.findById("EMP001"), "Employee should still be found by ID after update.");
        assertEquals(updatedEmployee, employeeRepository.findById("EMP001"), "The found employee should be the updated one.");
        assertNotEquals(originalEmployee, employeeRepository.findById("EMP001"), "The found employee should not be the original object.");
    }

    // Test case for adding an employee with a null ID (expecting NullPointerException as per HashMap behavior)
    // Note: If Employee.getId() handles null IDs gracefully, this test might need adjustment.
    // Assuming a standard Employee class where getId() on a well-formed employee is non-null.
    // If `e` itself is null, it would cause NPE. If `e.getId()` returns null, HashMap allows null keys.
    // The current implementation of `add` (`employees.put(e.getId(), e);`) means `e.getId()` must not be null.
    // If `e` itself is null, `add(null)` would throw NPE, which is a valid test case for robustness.
    @Test
    @DisplayName("Should throw NullPointerException when adding a null employee object")
    void testAdd_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> employeeRepository.add(null),
                "Adding a null employee object should throw NullPointerException.");
    }


    // Test cases for findAll method

    // Test case for retrieving all employees from an empty repository
    @Test
    @DisplayName("Should return an empty collection when findAll is called on an empty repository")
    void testFindAll_emptyRepository_returnsEmptyCollection() {
        Collection<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees, "findAll should not return null, even if empty.");
        assertTrue(employees.isEmpty(), "The collection of employees should be empty.");
        assertEquals(0, employees.size(), "The size of the collection should be 0.");
    }

    // Test case for retrieving all employees from a populated repository
    @Test
    @DisplayName("Should return all employees from a populated repository")
    void testFindAll_populatedRepository_returnsAllEmployees() {
        Employee employee1 = new Employee("EMP001", "Alice Smith");
        Employee employee2 = new Employee("EMP002", "Bob Johnson");
        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        Collection<Employee> allEmployees = employeeRepository.findAll();
        assertEquals(2, allEmployees.size(), "The collection should contain two employees.");
        assertTrue(allEmployees.contains(employee1), "Collection should contain employee1.");
        assertTrue(allEmployees.contains(employee2), "Collection should contain employee2.");
    }

    // Test case to ensure the returned collection is a view, not a deep copy (standard HashMap behavior)
    // Modifying the returned collection should reflect in the repository's state for HashMap.values()
    @Test
    @DisplayName("Should reflect changes in repository if returned collection from findAll is modified (HashMap view)")
    void testFindAll_returnedCollectionIsView() {
        Employee employee = new Employee("EMP001", "Alice Smith");
        employeeRepository.add(employee);

        Collection<Employee> allEmployees = employeeRepository.findAll();
        assertEquals(1, allEmployees.size());

        // Attempt to clear the returned collection
        allEmployees.clear(); // This will clear the underlying map

        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should be empty after clearing the returned view.");
        assertNull(employeeRepository.findById("EMP001"), "Employee should not be found after clearing the returned view.");
    }


    // Test cases for findById method

    // Test case for finding an existing employee by ID
    @Test
    @DisplayName("Should return the correct employee when finding by an existing ID")
    void testFindById_existingEmployee_returnsEmployee() {
        Employee employee = new Employee("EMP001", "Alice Smith");
        employeeRepository.add(employee);

        Employee foundEmployee = employeeRepository.findById("EMP001");
        assertNotNull(foundEmployee, "An employee should be found.");
        assertEquals(employee, foundEmployee, "The found employee should match the added one.");
    }

    // Test case for finding a non-existent employee by ID
    @Test
    @DisplayName("Should return null when finding by a non-existent ID")
    void testFindById_nonExistentId_returnsNull() {
        Employee employee = new Employee("EMP001", "Alice Smith");
        employeeRepository.add(employee); // Add one employee to ensure repository is not empty

        Employee foundEmployee = employeeRepository.findById("NONEXISTENT_ID");
        assertNull(foundEmployee, "No employee should be found for a non-existent ID.");
    }

    // Test case for finding an employee with a null ID
    @Test
    @DisplayName("Should return null when finding by a null ID")
    void testFindById_nullId_returnsNull() {
        Employee employee = new Employee("EMP001", "Alice Smith");
        employeeRepository.add(employee);

        // HashMap.get(null) returns null if null key is not present.
        // If a null key was added (which is possible with HashMap), it would return that value.
        // Assuming Employee.getId() does not return null for valid Employee objects being added.
        Employee foundEmployee = employeeRepository.findById(null);
        assertNull(foundEmployee, "Finding by a null ID should return null if no employee has a null ID.");
    }


    // Test cases for clear method

    // Test case for clearing an empty repository
    @Test
    @DisplayName("Should clear an empty repository without issues")
    void testClear_emptyRepository_remainsEmpty() {
        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should be empty initially.");
        employeeRepository.clear();
        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should still be empty after clearing an empty one.");
        assertEquals(0, employeeRepository.findAll().size(), "Size should be 0 after clear.");
    }

    // Test case for clearing a populated repository
    @Test
    @DisplayName("Should clear a populated repository, making it empty")
    void testClear_populatedRepository_becomesEmpty() {
        Employee employee1 = new Employee("EMP001", "Alice Smith");
        Employee employee2 = new Employee("EMP002", "Bob Johnson");
        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        assertEquals(2, employeeRepository.findAll().size(), "Repository should be populated initially.");

        employeeRepository.clear();

        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should be empty after clear.");
        assertEquals(0, employeeRepository.findAll().size(), "Size should be 0 after clear.");
        assertNull(employeeRepository.findById("EMP001"), "Employee 1 should no longer be found after clear.");
        assertNull(employeeRepository.findById("EMP002"), "Employee 2 should no longer be found after clear.");
    }
}