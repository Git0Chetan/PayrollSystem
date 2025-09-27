package org.example.payroll;

import org.example.payroll.model.FullTimeEmployee;
import org.example.payroll.model.PartTimeEmployee;
import org.example.payroll.model.Employee;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.repository.EmployeeRepository;
import org.example.payroll.service.PayrollService;

import java.util.Locale;
import java.util.Scanner;

public class PayrollApp {
    private static final Locale LOCALE = Locale.US;
    public static void main(String[] args) {
        Locale.setDefault(LOCALE);

        EmployeeRepository repo = new EmployeeRepository();
        PayrollService service = new PayrollService(repo);

        // Seed with a couple of sample employees for quick testing
        repo.add(new FullTimeEmployee("FT1", "Alice Johnson", "Engineering", 7500.0, 0.20));
        repo.add(new PartTimeEmployee("PT1", "Bob Smith", "QA",
                25.0, 80, 0.12));

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                System.out.println("\nPayroll System - Main Menu");
                System.out.println("1) Add Employee");
                System.out.println("2) List Employees");
                System.out.println("3) Generate Payroll");
                System.out.println("4) Exit");
                System.out.print("Choose an option: ");
                String line = scanner.nextLine();
                int option;
                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }

                switch (option) {
                    case 1:
                        addEmployeeCLI(scanner, repo);
                        break;
                    case 2:
                        listEmployees(repo);
                        break;
                    case 3:
                        generatePayrollCLI(scanner, service);
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown option.");
                }
            }
        }
    }

    private static void addEmployeeCLI(Scanner scanner, EmployeeRepository repo) {
        System.out.println("Add Employee:");
        System.out.println("Enter type: 1 = FullTime, 2 = PartTime");
        System.out.print("Type: ");
        String typeInput = scanner.nextLine();
        try {
            int type = Integer.parseInt(typeInput);
            System.out.print("ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Department: ");
            String dept = scanner.nextLine().trim();

            if (type == 1) { // FullTime
                System.out.print("Monthly Salary: ");
                double salary = Double.parseDouble(scanner.nextLine());
                System.out.print("Tax Rate (e.g., 0.20 for 20%): ");
                double tax = Double.parseDouble(scanner.nextLine());
                repo.add(new FullTimeEmployee(id, name, dept, salary, tax));
            } else { // PartTime
                System.out.print("Hourly Rate: ");
                double rate = Double.parseDouble(scanner.nextLine());
                System.out.print("Hours Worked (for the month): ");
                int hours = Integer.parseInt(scanner.nextLine());
                System.out.print("Tax Rate (e.g., 0.12 for 12%): ");
                double tax = Double.parseDouble(scanner.nextLine());
                repo.add(new PartTimeEmployee(id, name, dept, rate, hours, tax));
            }
            System.out.println("Employee added.");
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number format. Employee not added.");
        } catch (Exception ex) {
            System.out.println("Error adding employee: " + ex.getMessage());
        }
    }

    private static void listEmployees(EmployeeRepository repo) {
        System.out.println("Employees:");
        repo.findAll().forEach(e -> {
            System.out.println(String.format("%s | %s | %s | %s",
                    e.getId(), e.getName(), e.getDepartment(), e.getType()));
        });
    }

    private static void generatePayrollCLI(Scanner scanner, PayrollService service) {
        try {
            System.out.print("Month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.print("Year (e.g., 2025): ");
            int year = Integer.parseInt(scanner.nextLine());

            PayrollResult result = service.generatePayroll(month, year);
            System.out.println("Payroll for " + month + "/" + year);
            for (var line : result.lines) {
                System.out.println(String.format(
                        "ID=%s, Name=%s, Type=%s, Gross=%.2f, Tax=%.2f, Net=%.2f",
                        line.employeeId, line.employeeName, line.type,
                        line.gross, line.tax, line.net));
            }
            System.out.println("Total Gross: " + result.totalGross
                    + " | Total Tax: " + result.totalTax
                    + " | Total Net: " + result.totalNet);
        } catch (Exception ex) {
            System.out.println("Error generating payroll: " + ex.getMessage());
        }
    }

    private static void ensureNotNull(String s) {
        if (s == null) throw new IllegalArgumentException("Null value not allowed");
    }
}
