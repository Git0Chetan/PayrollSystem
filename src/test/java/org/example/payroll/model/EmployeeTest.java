package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    // Helper concrete class for testing the abstract Employee
    private static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(String id, String name, String department) {
            super(id, name, department);
        }

        @Override
        public double calculateGrossPay(int month, int year) {
            return 0.0;
        }

        @Override
        public double getTaxRate() {
            return 0.0;
        }

        @Override
        public String getType() {
            return "Concrete";
        }
    }

    // Test case for getId() method with a valid ID
    @Test
    void testGetId_validId() {
        String expectedId = "EMP001";
        Employee employee = new ConcreteEmployee(expectedId, "John Doe", "Sales");
        assertEquals(expectedId, employee.getId(), "getId should return the correct ID.");
    }

    // Test case for getId() method with a null ID
    @Test
    void testGetId_nullId() {
        String expectedId = null;
        Employee employee = new ConcreteEmployee(expectedId, "Jane Doe", "Marketing");
        assertNull(employee.getId(), "getId should return null when the ID is null.");
    }

    // Test case for getId() method with an empty ID
    @Test
    void testGetId_emptyId() {
        String expectedId = "";
        Employee employee = new ConcreteEmployee(expectedId, "Peter Pan", "HR");
        assertEquals(expectedId, employee.getId(), "getId should return an empty string when the ID is empty.");
    }

    // Test case for getName() method with a valid name
    @Test
    void testGetName_validName() {
        String expectedName = "John Doe";
        Employee employee = new ConcreteEmployee("EMP001", expectedName, "Sales");
        assertEquals(expectedName, employee.getName(), "getName should return the correct name.");
    }

    // Test case for getName() method with a null name
    @Test
    void testGetName_nullName() {
        String expectedName = null;
        Employee employee = new ConcreteEmployee("EMP002", expectedName, "Marketing");
        assertNull(employee.getName(), "getName should return null when the name is null.");
    }

    // Test case for getName() method with an empty name
    @Test
    void testGetName_emptyName() {
        String expectedName = "";
        Employee employee = new ConcreteEmployee("EMP003", expectedName, "HR");
        assertEquals(expectedName, employee.getName(), "getName should return an empty string when the name is empty.");
    }

    // Test case for getDepartment() method with a valid department
    @Test
    void testGetDepartment_validDepartment() {
        String expectedDepartment = "Sales";
        Employee employee = new ConcreteEmployee("EMP001", "John Doe", expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment(), "getDepartment should return the correct department.");
    }

    // Test case for getDepartment() method with a null department
    @Test
    void testGetDepartment_nullDepartment() {
        String expectedDepartment = null;
        Employee employee = new ConcreteEmployee("EMP002", "Jane Doe", expectedDepartment);
        assertNull(employee.getDepartment(), "getDepartment should return null when the department is null.");
    }

    // Test case for getDepartment() method with an empty department
    @Test
    void testGetDepartment_emptyDepartment() {
        String expectedDepartment = "";
        Employee employee = new ConcreteEmployee("EMP003", "Peter Pan", expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment(), "getDepartment should return an empty string when the department is empty.");
    }
}