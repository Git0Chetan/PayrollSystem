package org.example.payroll.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Employee Getter Tests")
class EmployeeTest {

    // A concrete implementation of Employee for testing purposes
    static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(String id, String name, String department) {
            super(id, name, department);
        }

        @Override
        public double calculateGrossPay(int month, int year) {
            return 0.0; // Not relevant for getter tests
        }

        @Override
        public double getTaxRate() {
            return 0.0; // Not relevant for getter tests
        }

        @Override
        public String getType() {
            return "Concrete"; // Not relevant for getter tests
        }
    }

    // Test case for getId() with a valid ID
    @Test
    @DisplayName("getId should return the correct ID")
    void getId_shouldReturnCorrectId() {
        Employee employee = new ConcreteEmployee("E123", "John Doe", "IT");
        assertEquals("E123", employee.getId(), "getId should return 'E123'");
    }

    // Test case for getId() with an empty ID
    @Test
    @DisplayName("getId should return an empty string when ID is empty")
    void getId_shouldReturnEmptyString_whenIdIsEmpty() {
        Employee employee = new ConcreteEmployee("", "Jane Doe", "HR");
        assertEquals("", employee.getId(), "getId should return an empty string");
    }

    // Test case for getId() with a null ID
    @Test
    @DisplayName("getId should return null when ID is null")
    void getId_shouldReturnNull_whenIdIsNull() {
        Employee employee = new ConcreteEmployee(null, "Alice Smith", "Finance");
        assertNull(employee.getId(), "getId should return null");
    }

    // Test case for getName() with a valid name
    @Test
    @DisplayName("getName should return the correct name")
    void getName_shouldReturnCorrectName() {
        Employee employee = new ConcreteEmployee("E123", "John Doe", "IT");
        assertEquals("John Doe", employee.getName(), "getName should return 'John Doe'");
    }

    // Test case for getName() with an empty name
    @Test
    @DisplayName("getName should return an empty string when name is empty")
    void getName_shouldReturnEmptyString_whenNameIsEmpty() {
        Employee employee = new ConcreteEmployee("E456", "", "HR");
        assertEquals("", employee.getName(), "getName should return an empty string");
    }

    // Test case for getName() with a null name
    @Test
    @DisplayName("getName should return null when name is null")
    void getName_shouldReturnNull_whenNameIsNull() {
        Employee employee = new ConcreteEmployee("E789", null, "Finance");
        assertNull(employee.getName(), "getName should return null");
    }

    // Test case for getDepartment() with a valid department
    @Test
    @DisplayName("getDepartment should return the correct department")
    void getDepartment_shouldReturnCorrectDepartment() {
        Employee employee = new ConcreteEmployee("E123", "John Doe", "IT");
        assertEquals("IT", employee.getDepartment(), "getDepartment should return 'IT'");
    }

    // Test case for getDepartment() with an empty department
    @Test
    @DisplayName("getDepartment should return an empty string when department is empty")
    void getDepartment_shouldReturnEmptyString_whenDepartmentIsEmpty() {
        Employee employee = new ConcreteEmployee("E456", "Jane Doe", "");
        assertEquals("", employee.getDepartment(), "getDepartment should return an empty string");
    }

    // Test case for getDepartment() with a null department
    @Test
    @DisplayName("getDepartment should return null when department is null")
    void getDepartment_shouldReturnNull_whenDepartmentIsNull() {
        Employee employee = new ConcreteEmployee("E789", "Alice Smith", null);
        assertNull(employee.getDepartment(), "getDepartment should return null");
    }
}