package org.example.payroll.repository;

import org.example.payroll.model.Employee;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmployeeRepository {
    private final Map<String, Employee> employees = new HashMap<>();

    public void add(Employee e) {
        employees.put(e.getId(), e);
    }

    public Collection<Employee> findAll() {
        return employees.values();
    }

    public Employee findById(String id) {
        return employees.get(id);
    }

    public void clear() {
        employees.clear();
    }
}
