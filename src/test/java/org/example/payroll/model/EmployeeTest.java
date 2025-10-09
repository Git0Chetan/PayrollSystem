package org.example.payroll.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    // A concrete implementation of the abstract Employee class for testing purposes
    private static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(String id, String name, String department) {
            super(id, name, department);
        }

        @Override
        public double calculateGrossPay(int month, int year) {
            return 1000.0; // Dummy implementation
        }

        @Override
        public double getTaxRate() {
            return 0.10; // Dummy implementation
        }

        @Override
        public String getType() {
            return "Concrete"; // Dummy implementation
        }
    }

    private ConcreteEmployee employee;

    @BeforeEach
    void setUp() {
        employee = new ConcreteEmployee("E101", "John Doe", "Sales");
    }

    // Test case for retrieving a valid ID
    @Test
    @DisplayName("getId should return the correct ID")
    void getId_shouldReturnCorrectId() {
        assertEquals("E101", employee.getId(), "The retrieved ID should match the initialized ID.");
    }

    // Test case for retrieving ID when it was initialized as null
    @Test
    @DisplayName("getId should return null when ID was initialized as null")
    void getId_shouldReturnNull_whenIdIsNull() {
        ConcreteEmployee nullIdEmployee = new ConcreteEmployee(null, "Jane Doe", "HR");
        assertNull(nullIdEmployee.getId(), "The retrieved ID should be null when initialized as null.");
    }

    // Test case for retrieving ID when it was initialized as an empty string
    @Test
    @DisplayName("getId should return empty string when ID was initialized as empty")
    void getId_shouldReturnEmpty_whenIdIsEmpty() {
        ConcreteEmployee emptyIdEmployee = new ConcreteEmployee("", "Alice", "Marketing");
        assertEquals("", emptyIdEmployee.getId(), "The retrieved ID should be an empty string when initialized as empty.");
    }

    // Test case for retrieving a valid name
    @Test
    @DisplayName("getName should return the correct name")
    void getName_shouldReturnCorrectName() {
        assertEquals("John Doe", employee.getName(), "The retrieved name should match the initialized name.");
    }

    // Test case for retrieving name when it was initialized as null
    @Test
    @DisplayName("getName should return null when name was initialized as null")
    void getName_shouldReturnNull_whenNameIsNull() {
        ConcreteEmployee nullNameEmployee = new ConcreteEmployee("E102", null, "HR");
        assertNull(nullNameEmployee.getName(), "The retrieved name should be null when initialized as null.");
    }

    // Test case for retrieving name when it was initialized as an empty string
    @Test
    @DisplayName("getName should return empty string when name was initialized as empty")
    void getName_shouldReturnEmpty_whenNameIsEmpty() {
        ConcreteEmployee emptyNameEmployee = new ConcreteEmployee("E103", "", "Marketing");
        assertEquals("", emptyNameEmployee.getName(), "The retrieved name should be an empty string when initialized as empty.");
    }

    // Test case for retrieving a valid department
    @Test
    @DisplayName("getDepartment should return the correct department")
    void getDepartment_shouldReturnCorrectDepartment() {
        assertEquals("Sales", employee.getDepartment(), "The retrieved department should match the initialized department.");
    }

    // Test case for retrieving department when it was initialized as null
    @Test
    @DisplayName("getDepartment should return null when department was initialized as null")
    void getDepartment_shouldReturnNull_whenDepartmentIsNull() {
        ConcreteEmployee nullDeptEmployee = new ConcreteEmployee("E104", "Bob", null);
        assertNull(nullDeptEmployee.getDepartment(), "The retrieved department should be null when initialized as null.");
    }

    // Test case for retrieving department when it was initialized as an empty string
    @Test
    @DisplayName("getDepartment should return empty string when department was initialized as empty")
    void getDepartment_shouldReturnEmpty_whenDepartmentIsEmpty() {
        ConcreteEmployee emptyDeptEmployee = new ConcreteEmployee("E105", "Charlie", "");
        assertEquals("", emptyDeptEmployee.getDepartment(), "The retrieved department should be an empty string when initialized as empty.");
    }
}