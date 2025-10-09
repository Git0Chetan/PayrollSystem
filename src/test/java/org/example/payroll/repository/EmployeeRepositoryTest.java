package org.example.payroll.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// A minimal Employee class for testing purposes, as the actual Employee class source was not provided.
// It must have an `getId()` method for the repository to function.
class Employee {
    private final String id;
    private String name;
    private double salary;

    public Employee(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Employee{id='" + id + "', name='" + name + "', salary=" + salary + '}';
    }
}


@DisplayName("EmployeeRepository Tests")
class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    // Test case for adding a new employee successfully
    @Test
    void add_shouldAddEmployee() {
        Employee emp1 = new Employee("E001", "Alice", 50000.0);
        repository.add(emp1);

        assertNotNull(repository.findById("E001"), "Employee should be found after adding.");
        assertEquals(emp1, repository.findById("E001"), "The found employee should be the one added.");
        assertEquals(1, repository.findAll().size(), "Repository size should be 1 after adding one employee.");
    }

    // Test case for adding an employee with an existing ID, overwriting the old one
    @Test
    void add_shouldOverwriteExistingEmployee() {
        Employee emp1 = new Employee("E001", "Alice", 50000.0);
        repository.add(emp1);

        Employee emp1Updated = new Employee("E001", "Alicia", 55000.0);
        repository.add(emp1Updated);

        assertEquals(1, repository.findAll().size(), "Repository size should still be 1 after overwriting.");
        assertEquals(emp1Updated, repository.findById("E001"), "The employee should be updated with the new details.");
        assertNotEquals(emp1, repository.findById("E001"), "The old employee object should not be the one found anymore.");
        assertEquals("Alicia", repository.findById("E001").getName());
        assertEquals(55000.0, repository.findById("E001").getSalary());
    }

    // Test case for adding a null employee
    @Test
    void add_shouldThrowNullPointerException_whenEmployeeIsNull() {
        assertThrows(NullPointerException.class, () -> repository.add(null),
                "Adding a null Employee should throw NullPointerException.");
    }

    // Test case for finding all employees when the repository is empty
    @Test
    void findAll_shouldReturnEmptyCollection_whenRepositoryIsEmpty() {
        Collection<Employee> employees = repository.findAll();
        assertNotNull(employees, "findAll should not return null.");
        assertTrue(employees.isEmpty(), "Collection should be empty when no employees are added.");
        assertEquals(0, employees.size(), "Size of collection should be 0.");
    }

    // Test case for finding all employees when there are multiple employees
    @Test
    void findAll_shouldReturnAllEmployees() {
        Employee emp1 = new Employee("E001", "Alice", 50000.0);
        Employee emp2 = new Employee("E002", "Bob", 60000.0);
        Employee emp3 = new Employee("E003", "Charlie", 70000.0);

        repository.add(emp1);
        repository.add(emp2);
        repository.add(emp3);

        Collection<Employee> allEmployees = repository.findAll();
        assertNotNull(allEmployees, "findAll should not return null.");
        assertEquals(3, allEmployees.size(), "Collection should contain all 3 added employees.");

        Set<Employee> expectedEmployees = new HashSet<>();
        expectedEmployees.add(emp1);
        expectedEmployees.add(emp2);
        expectedEmployees.add(emp3);

        assertTrue(allEmployees.containsAll(expectedEmployees) && expectedEmployees.containsAll(allEmployees),
                "The returned collection should contain exactly the added employees.");
    }

    // Test case for finding an existing employee by ID
    @Test
    void findById_shouldReturnEmployee_whenIdExists() {
        Employee emp1 = new Employee("E001", "Alice", 50000.0);
        repository.add(emp1);

        Employee foundEmployee = repository.findById("E001");
        assertNotNull(foundEmployee, "Employee should be found by its ID.");
        assertEquals(emp1, foundEmployee, "The found employee should match the added employee.");
    }

    // Test case for finding an employee by ID that does not exist
    @Test
    void findById_shouldReturnNull_whenIdDoesNotExist() {
        Employee emp1 = new Employee("E001", "Alice", 50000.0);
        repository.add(emp1);

        Employee foundEmployee = repository.findById("NON_EXISTENT_ID");
        assertNull(foundEmployee, "findById should return null for a non-existent ID.");
    }

    // Test case for finding an employee with a null ID
    @Test
    void findById_shouldReturnNull_whenIdIsNull() {
        Employee emp1 = new Employee("E001", "Alice", 50000.0);
        repository.add(emp1);

        Employee foundEmployee = repository.findById(null);
        assertNull(foundEmployee, "findById should return null when searching with a null ID.");
    }

    // Test case for clearing a non-empty repository
    @Test
    void clear_shouldRemoveAllEmployees() {
        repository.add(new Employee("E001", "Alice", 50000.0));
        repository.add(new Employee("E002", "Bob", 60000.0));

        assertFalse(repository.findAll().isEmpty(), "Repository should not be empty before clear.");
        assertEquals(2, repository.findAll().size(), "Repository should have 2 employees before clear.");

        repository.clear();

        assertTrue(repository.findAll().isEmpty(), "Repository should be empty after clear.");
        assertEquals(0, repository.findAll().size(), "Size of repository should be 0 after clear.");
        assertNull(repository.findById("E001"), "Employee should not be found after clear.");
    }

    // Test case for clearing an already empty repository
    @Test
    void clear_shouldDoNothing_whenRepositoryIsEmpty() {
        assertTrue(repository.findAll().isEmpty(), "Repository should be empty initially.");

        repository.clear();

        assertTrue(repository.findAll().isEmpty(), "Repository should remain empty after clearing an empty repository.");
        assertEquals(0, repository.findAll().size(), "Size of repository should be 0 after clear on empty.");
    }
}