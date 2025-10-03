package org.example.payroll.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Objects;

class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;

    // Helper Employee class for testing purposes
    static class Employee {
        private String id;
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

        public double getSalary() {
            return salary;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return Objects.equals(id, employee.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Employee{" +
                   "id='" + id + '\'' +
                   ", name='" + name + '\'' +
                   ", salary=" + salary +
                   '}';
        }
    }

    @BeforeEach
    void setUp() {
        employeeRepository = new EmployeeRepository();
    }

    // Test case for adding a single employee
    @Test
    @DisplayName("Should add a single employee to the repository")
    void add_singleEmployee_shouldBeFindable() {
        Employee employee1 = new Employee("1", "Alice", 50000.0);
        employeeRepository.add(employee1);

        assertNotNull(employeeRepository.findById("1"), "Employee should be found by ID after adding.");
        assertEquals(employee1, employeeRepository.findById("1"), "Found employee should match the added employee.");
        assertEquals(1, employeeRepository.findAll().size(), "Repository size should be 1 after adding one employee.");
    }

    // Test case for adding multiple employees
    @Test
    @DisplayName("Should add multiple employees to the repository")
    void add_multipleEmployees_shouldAllBeFindable() {
        Employee employee1 = new Employee("1", "Alice", 50000.0);
        Employee employee2 = new Employee("2", "Bob", 60000.0);

        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        assertEquals(2, employeeRepository.findAll().size(), "Repository size should be 2 after adding two employees.");
        assertTrue(employeeRepository.findAll().contains(employee1), "Repository should contain employee1.");
        assertTrue(employeeRepository.findAll().contains(employee2), "Repository should contain employee2.");
    }

    // Test case for adding an employee with an existing ID (update scenario)
    @Test
    @DisplayName("Should update an employee if ID already exists")
    void add_existingEmployeeId_shouldUpdateEmployee() {
        Employee employee1 = new Employee("1", "Alice", 50000.0);
        employeeRepository.add(employee1);

        Employee updatedEmployee1 = new Employee("1", "Alicia", 55000.0);
        employeeRepository.add(updatedEmployee1);

        assertEquals(1, employeeRepository.findAll().size(), "Repository size should remain 1 after updating an employee.");
        assertEquals(updatedEmployee1, employeeRepository.findById("1"), "Employee with ID 1 should be updated.");
        assertEquals("Alicia", employeeRepository.findById("1").getName(), "Updated employee name should be 'Alicia'.");
    }

    // Test case for adding an employee with a null ID
    @Test
    @DisplayName("Should handle adding an employee with a null ID")
    void add_employeeWithNullId_shouldBeAddable() {
        // HashMap allows null keys. The current implementation would put a null key if employee.getId() returns null.
        // This test verifies that the system does not crash and the employee is added with a null ID.
        Employee employeeWithNullId = new Employee(null, "Charlie", 40000.0);
        employeeRepository.add(employeeWithNullId);

        assertEquals(1, employeeRepository.findAll().size(), "Repository size should be 1.");
        assertNotNull(employeeRepository.findById(null), "Employee with null ID should be findable by null ID.");
        assertEquals(employeeWithNullId, employeeRepository.findById(null), "Found employee should match the added employee with null ID.");
    }

    // Test case for findAll on an empty repository
    @Test
    @DisplayName("Should return an empty collection when findAll on an empty repository")
    void findAll_emptyRepository_shouldReturnEmptyCollection() {
        Collection<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees, "findAll should not return null.");
        assertTrue(employees.isEmpty(), "Collection should be empty for an empty repository.");
    }

    // Test case for findAll with multiple employees
    @Test
    @DisplayName("Should return all added employees when findAll is called")
    void findAll_withEmployees_shouldReturnAllEmployees() {
        Employee employee1 = new Employee("1", "Alice", 50000.0);
        Employee employee2 = new Employee("2", "Bob", 60000.0);
        employeeRepository.add(employee1);
        employeeRepository.add(employee2);

        Collection<Employee> employees = employeeRepository.findAll();
        assertEquals(2, employees.size(), "Collection size should be 2.");
        assertTrue(employees.contains(employee1), "Collection should contain employee1.");
        assertTrue(employees.contains(employee2), "Collection should contain employee2.");
    }

    // Test case for findById with an existing ID
    @Test
    @DisplayName("Should find an employee by an existing ID")
    void findById_existingId_shouldReturnCorrectEmployee() {
        Employee employee1 = new Employee("101", "Alice", 50000.0);
        employeeRepository.add(employee1);

        Employee foundEmployee = employeeRepository.findById("101");
        assertNotNull(foundEmployee, "Employee should be found.");
        assertEquals(employee1, foundEmployee, "Found employee should match the added one.");
        assertEquals("Alice", foundEmployee.getName(), "Found employee's name should be 'Alice'.");
    }

    // Test case for findById with a non-existing ID
    @Test
    @DisplayName("Should return null when finding by a non-existing ID")
    void findById_nonExistingId_shouldReturnNull() {
        Employee employee1 = new Employee("101", "Alice", 50000.0);
        employeeRepository.add(employee1);

        Employee foundEmployee = employeeRepository.findById("999");
        assertNull(foundEmployee, "Should return null for a non-existing ID.");
    }

    // Test case for findById with a null ID when no employee with null ID exists
    @Test
    @DisplayName("Should return null when finding by a null ID if no such employee exists")
    void findById_nullIdNoEmployee_shouldReturnNull() {
        Employee foundEmployee = employeeRepository.findById(null);
        assertNull(foundEmployee, "Should return null when finding by null ID if no employee with null ID is present.");
    }

    // Test case for findById with a null ID when an employee with null ID exists
    @Test
    @DisplayName("Should return employee when finding by a null ID if such employee exists")
    void findById_nullIdWithEmployee_shouldReturnEmployee() {
        Employee employeeWithNullId = new Employee(null, "Charlie", 40000.0);
        employeeRepository.add(employeeWithNullId);

        Employee foundEmployee = employeeRepository.findById(null);
        assertNotNull(foundEmployee, "Employee with null ID should be found.");
        assertEquals(employeeWithNullId, foundEmployee, "Found employee should match the added employee with null ID.");
    }

    // Test case for clear on an empty repository
    @Test
    @DisplayName("Should clear an empty repository without issues")
    void clear_emptyRepository_shouldRemainEmpty() {
        employeeRepository.clear();
        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should be empty after clearing an empty repository.");
    }

    // Test case for clear on a repository with employees
    @Test
    @DisplayName("Should clear all employees from the repository")
    void clear_withEmployees_shouldMakeRepositoryEmpty() {
        employeeRepository.add(new Employee("1", "Alice", 50000.0));
        employeeRepository.add(new Employee("2", "Bob", 60000.0));
        assertEquals(2, employeeRepository.findAll().size(), "Repository should have 2 employees before clearing.");

        employeeRepository.clear();
        assertTrue(employeeRepository.findAll().isEmpty(), "Repository should be empty after clearing.");
        assertNull(employeeRepository.findById("1"), "Employee '1' should not be found after clearing.");
    }
}