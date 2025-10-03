package org.example.payroll.repository;

import org.example.payroll.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects; // For defensive programming and robust Employee equals/hashCode assumption

import static org.junit.jupiter.api.Assertions.*;

// This test class assumes the existence of 'org.example.payroll.model.Employee'
// with a constructor like Employee(String id, String name, double salary),
// a public String getId() method, and correctly implemented equals() and hashCode()
// for proper collection comparisons and Map behavior.
class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    // Helper method to create Employee instances for testing
    // This uses the assumed constructor of org.example.payroll.model.Employee
    private Employee createEmployee(String id, String name, double salary) {
        return new Employee(id, name, salary);
    }

    // Test case: Adding a single employee successfully
    @Test
    @DisplayName("Should add a single employee to the repository")
    void testAddSingleEmployee() {
        Employee employee1 = createEmployee("E001", "Alice", 60000.0);
        repository.add(employee1);

        assertEquals(1, repository.findAll().size());
        assertTrue(repository.findAll().contains(employee1));
        assertEquals(employee1, repository.findById("E001"));
    }

    // Test case: Adding multiple employees successfully
    @Test
    @DisplayName("Should add multiple employees to the repository")
    void testAddMultipleEmployees() {
        Employee employee1 = createEmployee("E001", "Alice", 60000.0);
        Employee employee2 = createEmployee("E002", "Bob", 75000.0);

        repository.add(employee1);
        repository.add(employee2);

        assertEquals(2, repository.findAll().size());
        assertTrue(repository.findAll().contains(employee1));
        assertTrue(repository.findAll().contains(employee2));
        assertEquals(employee1, repository.findById("E001"));
        assertEquals(employee2, repository.findById("E002"));
    }

    // Test case: Adding an employee with an ID that already exists should overwrite the previous entry
    @Test
    @DisplayName("Should overwrite an existing employee when adding one with the same ID")
    void testAddEmployeeWithExistingIdOverwrites() {
        Employee employee1 = createEmployee("E001", "Alice", 60000.0);
        repository.add(employee1);

        Employee employee1Updated = createEmployee("E001", "Alicia", 65000.0); // Same ID, different details
        repository.add(employee1Updated);

        assertEquals(1, repository.findAll().size()); // Size should remain 1
        assertEquals(employee1Updated, repository.findById("E001")); // Should return the updated employee
        assertFalse(repository.findAll().contains(employee1)); // The old object should not be there if equals/hashCode work
        assertTrue(repository.findAll().contains(employee1Updated));
    }

    // Test case: Adding a null Employee object should throw NullPointerException
    @Test
    @DisplayName("Should throw NullPointerException when adding a null employee")
    void testAddNullEmployeeThrowsNPE() {
        // The repository's add method calls e.getId() on the passed employee.
        // If employee is null, this will result in a NullPointerException.
        assertThrows(NullPointerException.class, () -> repository.add(null));
    }

    // Test case: Adding an employee with a null ID should be successful
    // HashMap allows null keys, so employee will be stored and retrievable by null.
    @Test
    @DisplayName("Should successfully add and retrieve an employee with a null ID")
    void testAddEmployeeWithNullId() {
        // Create an anonymous inner class extending Employee to simulate an Employee with a null ID.
        // This relies on Employee having a constructor like Employee(String id, String name, double salary).
        Employee employeeWithNullId = new Employee(null, "Charlie", 50000.0) {
            // Override equals/hashCode if the base Employee class doesn't handle null IDs explicitly,
            // though for basic Map operations, this is often fine.
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Employee that = (Employee) o;
                return Double.compare(that.getSalary(), getSalary()) == 0 &&
                       Objects.equals(getId(), that.getId()) &&
                       Objects.equals(getName(), that.getName());
            }

            @Override
            public int hashCode() {
                return Objects.hash(getId(), getName(), getSalary());
            }
        };

        repository.add(employeeWithNullId);

        assertEquals(1, repository.findAll().size());
        assertTrue(repository.findAll().contains(employeeWithNullId));
        assertEquals(employeeWithNullId, repository.findById(null)); // Should be retrievable by null ID
    }


    // Test case: Finding all employees when the repository is empty
    @Test
    @DisplayName("Should return an empty collection when findAll is called on an empty repository")
    void testFindAllEmptyRepository() {
        Collection<Employee> allEmployees = repository.findAll();
        assertNotNull(allEmployees);
        assertTrue(allEmployees.isEmpty());
        assertEquals(0, allEmployees.size());
    }

    // Test case: Finding all employees when the repository contains multiple employees
    @Test
    @DisplayName("Should return all employees when findAll is called on a populated repository")
    void testFindAllWithEmployees() {
        Employee employee1 = createEmployee("E001", "Alice", 60000.0);
        Employee employee2 = createEmployee("E002", "Bob", 75000.0);
        repository.add(employee1);
        repository.add(employee2);

        Collection<Employee> allEmployees = repository.findAll();
        assertEquals(2, allEmployees.size());
        // Using HashSet for comparison to ignore order, assuming Employee equals/hashCode are correct.
        Set<Employee> expectedEmployees = new HashSet<>();
        expectedEmployees.add(employee1);
        expectedEmployees.add(employee2);
        assertEquals(expectedEmployees, new HashSet<>(allEmployees));
    }

    // Test case: Verifying findAll returns a live view of the map's values
    // Changes to the underlying map should be reflected in the obtained collection view.
    @Test
    @DisplayName("Should return a live view of employees, reflecting changes to the underlying map")
    void testFindAllReturnsLiveView() {
        Employee employee1 = createEmployee("E001", "Alice", 60000.0);
        repository.add(employee1);

        Collection<Employee> firstView = repository.findAll();
        assertEquals(1, firstView.size());
        assertTrue(firstView.contains(employee1));

        Employee employee2 = createEmployee("E002", "Bob", 75000.0);
        repository.add(employee2); // Add another employee

        // The 'firstView' should now reflect the change, as it's a live view into the HashMap values.
        assertEquals(2, firstView.size());
        assertTrue(firstView.contains(employee2));
        assertTrue(firstView.contains(employee1));

        repository.clear(); // Clear the repository

        // The 'firstView' should now reflect that the repository is empty.
        assertTrue(firstView.isEmpty());
    }


    // Test case: Finding an existing employee by ID
    @Test
    @DisplayName("Should return the correct employee when found by an existing ID")
    void testFindByIdExistingEmployee() {
        Employee employee1 = createEmployee("E001", "Alice", 60000.0);
        repository.add(employee1);

        Employee foundEmployee = repository.findById("E001");
        assertNotNull(foundEmployee);
        assertEquals(employee1, foundEmployee);
    }

    // Test case: Finding a non-existing employee by ID
    @Test
    @DisplayName("Should return null when employee ID does not exist")
    void testFindByIdNonExistingEmployee() {
        repository.add(createEmployee("E001", "Alice", 60000.0));
        Employee foundEmployee = repository.findById("E002"); // Non-existent ID
        assertNull(foundEmployee);
    }

    // Test case: Finding an employee with a null ID should return null if no such employee was added
    @Test
    @DisplayName("Should return null when searching for a null ID if no such employee exists")
    void testFindByIdNullIdNonExisting() {
        repository.add(createEmployee("E001", "Alice", 60000.0));
        Employee foundEmployee = repository.findById(null);
        assertNull(foundEmployee);
    }

    // Test case: Finding an employee with an empty string ID when such an employee exists
    @Test
    @DisplayName("Should return the correct employee when found by an empty string ID")
    void testFindByIdEmptyStringIdExisting() {
        Employee employeeWithEmptyId = createEmployee("", "Empty ID User", 1000.0);
        repository.add(employeeWithEmptyId);

        Employee foundEmployee = repository.findById("");
        assertNotNull(foundEmployee);
        assertEquals(employeeWithEmptyId, foundEmployee);
    }

    // Test case: Finding an employee with an empty string ID when no such employee exists
    @Test
    @DisplayName("Should return null when searching for an empty string ID that does not exist")
    void testFindByIdEmptyStringIdNonExisting() {
        repository.add(createEmployee("E001", "Alice", 60000.0));
        Employee foundEmployee = repository.findById("");
        assertNull(foundEmployee);
    }


    // Test case: Clearing an empty repository
    @Test
    @DisplayName("Should remain empty when clear is called on an empty repository")
    void testClearEmptyRepository() {
        repository.clear();
        assertTrue(repository.findAll().isEmpty());
        assertEquals(0, repository.findAll().size());
    }

    // Test case: Clearing a repository with employees
    @Test
    @DisplayName("Should clear all employees from a populated repository")
    void testClearRepositoryWithEmployees() {
        repository.add(createEmployee("E001", "Alice", 60000.0));
        repository.add(createEmployee("E002", "Bob", 75000.0));
        assertEquals(2, repository.findAll().size());

        repository.clear();

        assertTrue(repository.findAll().isEmpty());
        assertEquals(0, repository.findAll().size());
        assertNull(repository.findById("E001")); // Verify specific employee is no longer found
        assertNull(repository.findById("E002"));
    }
}