package org.example.payroll.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    // A concrete implementation of Employee for testing purposes.
    // This allows us to instantiate the abstract Employee class for testing its common fields and getters.
    private static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(String id, String name, String department) {
            super(id, name, department);
        }

        @Override
        public double calculateGrossPay(int month, int year) {
            // Not relevant for these getter tests, provide a dummy implementation.
            return 0.0;
        }

        @Override
        public double getTaxRate() {
            // Not relevant for these getter tests, provide a dummy implementation.
            return 0.0;
        }

        @Override
        public String getType() {
            // Not relevant for these getter tests, provide a dummy implementation.
            return "Concrete";
        }
    }

    // Test case for getId with a valid, non-empty ID
    @Test
    @DisplayName("getId should return the correct ID when a valid ID is provided")
    void testGetId_ValidId() {
        String expectedId = "EMP001";
        ConcreteEmployee employee = new ConcreteEmployee(expectedId, "John Doe", "IT");
        assertEquals(expectedId, employee.getId(), "The getId method should return the initialized ID.");
    }

    // Test case for getId with an empty string ID
    @Test
    @DisplayName("getId should return an empty string when an empty ID is provided")
    void testGetId_EmptyId() {
        String expectedId = "";
        ConcreteEmployee employee = new ConcreteEmployee(expectedId, "Jane Smith", "HR");
        assertEquals(expectedId, employee.getId(), "The getId method should return an empty string for an empty ID.");
    }

    // Test case for getId with a null ID
    @Test
    @DisplayName("getId should return null when a null ID is provided")
    void testGetId_NullId() {
        String expectedId = null;
        ConcreteEmployee employee = new ConcreteEmployee(expectedId, "Alice Brown", "Finance");
        assertNull(employee.getId(), "The getId method should return null for a null ID.");
    }

    // Test case for getName with a valid, non-empty name
    @Test
    @DisplayName("getName should return the correct name when a valid name is provided")
    void testGetName_ValidName() {
        String expectedName = "John Doe";
        ConcreteEmployee employee = new ConcreteEmployee("EMP002", expectedName, "IT");
        assertEquals(expectedName, employee.getName(), "The getName method should return the initialized name.");
    }

    // Test case for getName with an empty string name
    @Test
    @DisplayName("getName should return an empty string when an empty name is provided")
    void testGetName_EmptyName() {
        String expectedName = "";
        ConcreteEmployee employee = new ConcreteEmployee("EMP003", expectedName, "HR");
        assertEquals(expectedName, employee.getName(), "The getName method should return an empty string for an empty name.");
    }

    // Test case for getName with a null name
    @Test
    @DisplayName("getName should return null when a null name is provided")
    void testGetName_NullName() {
        String expectedName = null;
        ConcreteEmployee employee = new ConcreteEmployee("EMP004", expectedName, "Finance");
        assertNull(employee.getName(), "The getName method should return null for a null name.");
    }

    // Test case for getDepartment with a valid, non-empty department
    @Test
    @DisplayName("getDepartment should return the correct department when a valid department is provided")
    void testGetDepartment_ValidDepartment() {
        String expectedDepartment = "Engineering";
        ConcreteEmployee employee = new ConcreteEmployee("EMP005", "Alice Green", expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment(), "The getDepartment method should return the initialized department.");
    }

    // Test case for getDepartment with an empty string department
    @Test
    @DisplayName("getDepartment should return an empty string when an empty department is provided")
    void testGetDepartment_EmptyDepartment() {
        String expectedDepartment = "";
        ConcreteEmployee employee = new ConcreteEmployee("EMP006", "Bob White", expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment(), "The getDepartment method should return an empty string for an empty department.");
    }

    // Test case for getDepartment with a null department
    @Test
    @DisplayName("getDepartment should return null when a null department is provided")
    void testGetDepartment_NullDepartment() {
        String expectedDepartment = null;
        ConcreteEmployee employee = new ConcreteEmployee("EMP007", "Charlie Black", expectedDepartment);
        assertNull(employee.getDepartment(), "The getDepartment method should return null for a null department.");
    }
}