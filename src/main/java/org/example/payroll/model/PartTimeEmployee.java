package org.example.payroll.model;

public class PartTimeEmployee extends Employee  {
    private double hourlyRate;
    private int hoursWorked;
    private double taxRate;

    public PartTimeEmployee(String id, String name, String department,
                            double hourlyRate, int hoursWorked, double taxRate) {
        super(id, name, department);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.taxRate = taxRate;
    }

    @Override
    public double calculateGrossPay(int month, int year) {
        return hourlyRate * hoursWorked;
    }

    @Override
    public double getTaxRate() {
        return taxRate;
    }

    @Override
    public String getType() {
        return "PartTime";
    }
}
