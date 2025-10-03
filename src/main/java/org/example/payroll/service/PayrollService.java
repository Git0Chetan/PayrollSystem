package org.example.payroll.service;

import org.example.payroll.model.Employee;
import org.example.payroll.model.PayrollLine;
import org.example.payroll.model.PayrollResult;

import org.example.payroll.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class PayrollService {
    private final EmployeeRepository repository;

    public PayrollService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public PayrollResult generatePayroll(int month, int year) {
        PayrollResult result = new PayrollResult(month, year);
        for (Employee e : repository.findAll()) {
            double gross = e.calculateGrossPay(month, year);
            double tax = gross * e.getTaxRate();
            double net = gross - tax;
            PayrollLine line = new PayrollLine(
                    e.getId(), e.getName(), e.getType(),
                    gross, tax, net, month, year);
            result.addLine(line);
        }
        return result;
    }

    // Simple helper for tests or UI
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(repository.findAll());
    }

}
