package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PaymentProcessor {
    // Abstract employee
    static abstract class Employee {
        String id;
        String name;
        String department;

        Employee(String id, String name, String department) {
            this.id = id;
            this.name = name;
            this.department = department;
        }

        abstract double calculateGross(int month, int year);

        abstract double getTaxRate();

        abstract String getType();
    }

    // Full-time employee
    static class FullTimeEmployee extends Employee {
        double monthlySalary;
        double taxRate;

        FullTimeEmployee(String id, String name, String department, double monthlySalary, double taxRate) {
            super(id, name, department);
            this.monthlySalary = monthlySalary;
            this.taxRate = taxRate;
        }

        @Override
        double calculateGross(int month, int year) {
            return monthlySalary;
        }

        @Override
        double getTaxRate() {
            return taxRate;
        }

        @Override
        String getType() {
            return "FullTime";
        }
    }

    // Part-time employee
    static class PartTimeEmployee extends Employee {
        double hourlyRate;
        int hoursWorked;
        double taxRate;

        PartTimeEmployee(String id, String name, String department, double hourlyRate, int hoursWorked, double taxRate) {
            super(id, name, department);
            this.hourlyRate = hourlyRate;
            this.hoursWorked = hoursWorked;
            this.taxRate = taxRate;
        }

        @Override
        double calculateGross(int month, int year) {
            return hourlyRate * hoursWorked;
        }

        @Override
        double getTaxRate() {
            return taxRate;
        }

        @Override
        String getType() {
            return "PartTime";
        }
    }

    // Payroll line for output
    static class PayrollLine {
        String employeeId;
        String employeeName;
        String type;
        double gross;
        double tax;
        double net;
        int month;
        int year;

        PayrollLine(String employeeId, String employeeName, String type,
                    double gross, double tax, double net, int month, int year) {
            this.employeeId = employeeId;
            this.employeeName = employeeName;
            this.type = type;
            this.gross = gross;
            this.tax = tax;
            this.net = net;
            this.month = month;
            this.year = year;
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Employee> employees = new ArrayList<>();

        System.out.println("Simple Payroll Processor (Single-file Java)");
        int n = 0;
        System.out.print("Enter number of employees: ");
        try {
            n = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid number, defaulting to 0.");
            n = 0;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Employee #" + (i + 1));
            System.out.print("Type (1=FullTime, 2=PartTime): ");
            int type = Integer.parseInt(sc.nextLine().trim());
            System.out.print("ID: ");
            String id = sc.nextLine().trim();
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Department: ");
            String dept = sc.nextLine().trim();

            if (type == 1) {
                System.out.print("Monthly Salary: ");
                double salary = Double.parseDouble(sc.nextLine().trim());
                System.out.print("Tax Rate (e.g., 0.20 for 20%): ");
                double tax = Double.parseDouble(sc.nextLine().trim());
                employees.add(new FullTimeEmployee(id, name, dept, salary, tax));
            } else {
                System.out.print("Hourly Rate: ");
                double rate = Double.parseDouble(sc.nextLine().trim());
                System.out.print("Hours Worked: ");
                int hours = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Tax Rate (e.g., 0.12 for 12%): ");
                double tax = Double.parseDouble(sc.nextLine().trim());
                employees.add(new PartTimeEmployee(id, name, dept, rate, hours, tax));
            }
            System.out.println();
        }

        System.out.print("Month (1-12): ");
        int month = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Year (e.g., 2025): ");
        int year = Integer.parseInt(sc.nextLine().trim());

        // Compute payroll
        double totalGross = 0;
        double totalTax = 0;
        double totalNet = 0;
        List<PayrollLine> lines = new ArrayList<>();

        for (Employee e : employees) {
            double gross = e.calculateGross(month, year);
            double tax = gross * e.getTaxRate();
            double net = gross - tax;

            PayrollLine pl = new PayrollLine(e.id, e.name, e.getType(), gross, tax, net, month, year);
            lines.add(pl);

            totalGross += gross;
            totalTax += tax;
            totalNet += net;
        }

        // Output results
        System.out.println("Payroll for " + month + "/" + year);
        for (PayrollLine pl : lines) {
            System.out.printf("ID=%s, Name=%s, Type=%s, Gross=%.2f, Tax=%.2f, Net=%.2f%n",
                    pl.employeeId, pl.employeeName, pl.type, pl.gross, pl.tax, pl.net);
        }
        System.out.println("Total Gross: " + totalGross + " | Total Tax: " + totalTax + " | Total Net: " + totalNet);
    }
}
