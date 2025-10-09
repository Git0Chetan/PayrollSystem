package org.example.payroll.model;

public class FullTimeEmployee extends Employee{
    private double monthlySalary;
    private double taxRate;

    public FullTimeEmployee(String id, String name, String department,
                            double monthlySalary, double taxRate) {
        super(id, name, department);
        this.monthlySalary = monthlySalary;
        this.taxRate = taxRate;
    }

    @Override
    public double calculateGrossPay(int month, int year) {
        // For simplicity, assume full monthly salary is paid every month
        return monthlySalary;
    }

    @Override
    public double getTaxRate() {
        return taxRate;
    }

    @Override
    public String getType() {
        return "FullTime";
    }
}

//Modification
