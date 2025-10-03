package org.example.payroll.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    // A concrete implementation of Employee for testing purposes
    private static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(String id, String name, String department) {
            super(id, name, department);
        }

        @Override
        public double calculateGrossPay(int month, int year) {
            return 0.0; // Not relevant for these getter tests
        }

        @Override
        public double getTaxRate() {
            return 0.0; // Not relevant for these getter tests
        }

        @Override
        public String getType() {
            return "Concrete"; // Not relevant for these getter tests
        }
    }

    // Test case for a valid employee ID
    @Test
    void test_getId_validId_shouldReturnCorrectId() {
        String expectedId = "EMP001";
        ConcreteEmployee employee = new ConcreteEmployee(expectedId, "John Doe", "IT");
        assertEquals(expectedId, employee.getId(), "getId should return the correct ID.");
    }

    // Test case for an employee ID that is an empty string
    @Test
    void test_getId_emptyId_shouldReturnEmptyString() {
        String expectedId = "";
        ConcreteEmployee employee = new ConcreteEmployee(expectedId, "Jane Smith", "HR");
        assertEquals(expectedId, employee.getId(), "getId should return an empty string if ID was empty.");
    }

    // Test case for an employee ID that is null
    @Test
    void test_getId_nullId_shouldReturnNull() {
        String expectedId = null;
        ConcreteEmployee employee = new ConcreteEmployee(expectedId, "Alice Brown", "Finance");
        assertNull(employee.getId(), "getId should return null if ID was null.");
    }

    // Test case for an employee ID with special characters and spaces
    @Test
    void test_getId_idWithSpecialChars_shouldReturnCorrectId() {
        String expectedId = "EMP-007_X";
        ConcreteEmployee employee = new ConcreteEmployee(expectedId, "Bond James", "Secret Service");
        assertEquals(expectedId, employee.getId(), "getId should return the ID correctly even with special characters.");
    }

    // Test case for a valid employee name
    @Test
    void test_getName_validName_shouldReturnCorrectName() {
        String expectedName = "John Doe";
        ConcreteEmployee employee = new ConcreteEmployee("EMP001", expectedName, "IT");
        assertEquals(expectedName, employee.getName(), "getName should return the correct name.");
    }

    // Test case for an employee name that is an empty string
    @Test
    void test_getName_emptyName_shouldReturnEmptyString() {
        String expectedName = "";
        ConcreteEmployee employee = new ConcreteEmployee("EMP002", expectedName, "HR");
        assertEquals(expectedName, employee.getName(), "getName should return an empty string if name was empty.");
    }

    // Test case for an employee name that is null
    @Test
    void test_getName_nullName_shouldReturnNull() {
        String expectedName = null;
        ConcreteEmployee employee = new ConcreteEmployee("EMP003", expectedName, "Finance");
        assertNull(employee.getName(), "getName should return null if name was null.");
    }

    // Test case for an employee name with multiple spaces
    @Test
    void test_getName_nameWithMultipleSpaces_shouldReturnCorrectName() {
        String expectedName = "Dr. Alice M. Brown";
        ConcreteEmployee employee = new ConcreteEmployee("EMP004", expectedName, "Research");
        assertEquals(expectedName, employee.getName(), "getName should return the name correctly with multiple spaces.");
    }

    // Test case for a valid employee department
    @Test
    void test_getDepartment_validDepartment_shouldReturnCorrectDepartment() {
        String expectedDepartment = "IT";
        ConcreteEmployee employee = new ConcreteEmployee("EMP001", "John Doe", expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment(), "getDepartment should return the correct department.");
    }

    // Test case for an employee department that is an empty string
    @Test
    void test_getDepartment_emptyDepartment_shouldReturnEmptyString() {
        String expectedDepartment = "";
        ConcreteEmployee employee = new ConcreteEmployee("EMP002", "Jane Smith", expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment(), "getDepartment should return an empty string if department was empty.");
    }

    // Test case for an employee department that is null
    @Test
    void test_getDepartment_nullDepartment_shouldReturnNull() {
        String expectedDepartment = null;
        ConcreteEmployee employee = new ConcreteEmployee("EMP003", "Alice Brown", expectedDepartment);
        assertNull(employee.getDepartment(), "getDepartment should return null if department was null.");
    }

    // Test case for an employee department with hyphens and mixed case
    @Test
    void test_getDepartment_departmentWithHyphensAndMixedCase_shouldReturnCorrectDepartment() {
        String expectedDepartment = "Sales-Marketing-South";
        ConcreteEmployee employee = new ConcreteEmployee("EMP005", "Bob Johnson", expectedDepartment);
        assertEquals(expectedDepartment, employee.getDepartment(), "getDepartment should return the department correctly with special characters and mixed case.");
    }
}