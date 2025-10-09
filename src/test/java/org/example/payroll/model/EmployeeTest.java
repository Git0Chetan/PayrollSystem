package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Employee Tests")
class EmployeeTest {

    // A concrete implementation of the abstract Employee class for testing purposes
    private static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(String id, String name, String department) {
            super(id, name, department);
        }

        @Override
        public double calculateGrossPay(int month, int year) {
            return 0.0; // Not relevant for getId, getName, getDepartment tests
        }

        @Override
        public double getTaxRate() {
            return 0.0; // Not relevant for getId, getName, getDepartment tests
        }

        @Override
        public String getType() {
            return "Concrete"; // Not relevant for getId, getName, getDepartment tests
        }
    }

    private ConcreteEmployee employee;

    // Test case for retrieving a valid ID
    @Test
    @DisplayName("getId should return the correct ID when valid ID is provided")
    void getId_shouldReturnCorrectId() {
        employee = new ConcreteEmployee("EMP001", "Alice Smith", "Engineering");
        assertEquals("EMP001", employee.getId(), "getId should return the ID 'EMP001'");
    }

    // Test case for retrieving a null ID
    @Test
    @DisplayName("getId should return null when ID is null")
    void getId_shouldReturnNullForNullId() {
        employee = new ConcreteEmployee(null, "Bob Johnson", "HR");
        assertNull(employee.getId(), "getId should return null for a null ID");
    }

    // Test case for retrieving an empty ID
    @Test
    @DisplayName("getId should return an empty string when ID is empty")
    void getId_shouldReturnEmptyForEmptyId() {
        employee = new ConcreteEmployee("", "Charlie Brown", "Finance");
        assertEquals("", employee.getId(), "getId should return an empty string for an empty ID");
    }

    // Test case for retrieving a valid name
    @Test
    @DisplayName("getName should return the correct name when a valid name is provided")
    void getName_shouldReturnCorrectName() {
        employee = new ConcreteEmployee("EMP002", "David Lee", "Marketing");
        assertEquals("David Lee", employee.getName(), "getName should return the name 'David Lee'");
    }

    // Test case for retrieving a null name
    @Test
    @DisplayName("getName should return null when name is null")
    void getName_shouldReturnNullForNullName() {
        employee = new ConcreteEmployee("EMP003", null, "Sales");
        assertNull(employee.getName(), "getName should return null for a null name");
    }

    // Test case for retrieving an empty name
    @Test
    @DisplayName("getName should return an empty string when name is empty")
    void getName_shouldReturnEmptyForEmptyName() {
        employee = new ConcreteEmployee("EMP004", "", "IT");
        assertEquals("", employee.getName(), "getName should return an empty string for an empty name");
    }

    // Test case for retrieving a valid department
    @Test
    @DisplayName("getDepartment should return the correct department when a valid department is provided")
    void getDepartment_shouldReturnCorrectDepartment() {
        employee = new ConcreteEmployee("EMP005", "Eve White", "Operations");
        assertEquals("Operations", employee.getDepartment(), "getDepartment should return the department 'Operations'");
    }

    // Test case for retrieving a null department
    @Test
    @DisplayName("getDepartment should return null when department is null")
    void getDepartment_shouldReturnNullForNullDepartment() {
        employee = new ConcreteEmployee("EMP006", "Frank Black", null);
        assertNull(employee.getDepartment(), "getDepartment should return null for a null department");
    }

    // Test case for retrieving an empty department
    @Test
    @DisplayName("getDepartment should return an empty string when department is empty")
    void getDepartment_shouldReturnEmptyForEmptyDepartment() {
        employee = new ConcreteEmployee("EMP007", "Grace Green", "");
        assertEquals("", employee.getDepartment(), "getDepartment should return an empty string for an empty department");
    }
}