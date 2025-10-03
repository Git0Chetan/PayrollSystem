package org.example.payroll.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    // A concrete implementation of the abstract Employee class for testing purposes.
    // This allows instantiation of Employee to test its non-abstract methods.
    private static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(String id, String name, String department) {
            super(id, name, department);
        }

        @Override
        public double calculateGrossPay(int month, int year) {
            return 0.0; // Dummy implementation, not relevant for these tests
        }

        @Override
        public double getTaxRate() {
            return 0.0; // Dummy implementation, not relevant for these tests
        }

        @Override
        public String getType() {
            return "Test Employee"; // Dummy implementation, not relevant for these tests
        }
    }

    // Test case for getting a valid employee ID
    @Test
    @DisplayName("Should return the correct ID when a valid ID is provided during construction")
    void testGetId_validId() {
        ConcreteEmployee employee = new ConcreteEmployee("E123", "Alice", "HR");
        assertEquals("E123", employee.getId(), "The getId() method should return the ID set in the constructor.");
    }

    // Test case for getting an empty employee ID
    @Test
    @DisplayName("Should return an empty string when an empty ID is provided during construction")
    void testGetId_emptyId() {
        ConcreteEmployee employee = new ConcreteEmployee("", "Bob", "IT");
        assertEquals("", employee.getId(), "The getId() method should return an empty string if the ID was set as empty.");
    }

    // Test case for getting a null employee ID
    @Test
    @DisplayName("Should return null when a null ID is provided during construction")
    void testGetId_nullId() {
        ConcreteEmployee employee = new ConcreteEmployee(null, "Charlie", "Finance");
        assertNull(employee.getId(), "The getId() method should return null if the ID was set as null.");
    }

    // Test case for getting a valid employee name
    @Test
    @DisplayName("Should return the correct name when a valid name is provided during construction")
    void testGetName_validName() {
        ConcreteEmployee employee = new ConcreteEmployee("E124", "David", "Sales");
        assertEquals("David", employee.getName(), "The getName() method should return the name set in the constructor.");
    }

    // Test case for getting an empty employee name
    @Test
    @DisplayName("Should return an empty string when an empty name is provided during construction")
    void testGetName_emptyName() {
        ConcreteEmployee employee = new ConcreteEmployee("E125", "", "Marketing");
        assertEquals("", employee.getName(), "The getName() method should return an empty string if the name was set as empty.");
    }

    // Test case for getting a null employee name
    @Test
    @DisplayName("Should return null when a null name is provided during construction")
    void testGetName_nullName() {
        ConcreteEmployee employee = new ConcreteEmployee("E126", null, "Operations");
        assertNull(employee.getName(), "The getName() method should return null if the name was set as null.");
    }

    // Test case for getting a valid employee department
    @Test
    @DisplayName("Should return the correct department when a valid department is provided during construction")
    void testGetDepartment_validDepartment() {
        ConcreteEmployee employee = new ConcreteEmployee("E127", "Eve", "Engineering");
        assertEquals("Engineering", employee.getDepartment(), "The getDepartment() method should return the department set in the constructor.");
    }

    // Test case for getting an empty employee department
    @Test
    @DisplayName("Should return an empty string when an empty department is provided during construction")
    void testGetDepartment_emptyDepartment() {
        ConcreteEmployee employee = new ConcreteEmployee("E128", "Frank", "");
        assertEquals("", employee.getDepartment(), "The getDepartment() method should return an empty string if the department was set as empty.");
    }

    // Test case for getting a null employee department
    @Test
    @DisplayName("Should return null when a null department is provided during construction")
    void testGetDepartment_nullDepartment() {
        ConcreteEmployee employee = new ConcreteEmployee("E129", "Grace", null);
        assertNull(employee.getDepartment(), "The getDepartment() method should return null if the department was set as null.");
    }
}