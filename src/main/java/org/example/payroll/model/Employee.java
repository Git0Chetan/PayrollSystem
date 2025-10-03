package org.example.payroll.model;

public abstract class Employee {
    protected String id;
    protected String name;
    protected String department;

    public Employee(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    // Gross pay for the given month/year
    public abstract double calculateGrossPay(int month, int year);

    // Tax rate (as a decimal, e.g., 0.20 for 20%)
    public abstract double getTaxRate();

    // Type label (for display)
    public abstract String getType();

}
