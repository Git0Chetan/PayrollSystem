package org.example.payroll;

import org.example.payroll.model.FullTimeEmployee;
import org.example.payroll.model.PartTimeEmployee;
import org.example.payroll.model.PayrollResult;
import org.example.payroll.model.PayrollLine;
import org.example.payroll.repository.EmployeeRepository;
import org.example.payroll.service.PayrollService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PayrollServiceTest {
    @Test
    void testFullTimeEmployeePayroll() {
        EmployeeRepository repo = new EmployeeRepository();
        repo.add(new FullTimeEmployee("FT-1", "Alice", "Engineering", 5000.0, 0.2));

        PayrollService svc = new PayrollService(repo);
        PayrollResult res = svc.generatePayroll(9, 2025);

        assertEquals(1, res.lines.size());
        PayrollLine line = res.lines.get(0);
        assertEquals("FT-1", line.employeeId);
        assertEquals(5000.0, line.gross, 0.01);
        assertEquals(1000.0, line.tax, 0.01);
        assertEquals(4000.0, line.net, 0.01);

        assertEquals(5000.0, res.totalGross, 0.01);
        assertEquals(1000.0, res.totalTax, 0.01);
        assertEquals(4000.0, res.totalNet, 0.01);
    }

    @Test
    void testPartTimeEmployeePayroll() {
        EmployeeRepository repo = new EmployeeRepository();
        repo.add(new PartTimeEmployee("PT-1", "Bob", "QA", 20.0, 80, 0.12));

        PayrollService svc = new PayrollService(repo);
        PayrollResult res = svc.generatePayroll(9, 2025);

        assertEquals(1, res.lines.size());
        PayrollLine line = res.lines.get(0);
        assertEquals("PT-1", line.employeeId);
        assertEquals(1600.0, line.gross, 0.01);
        assertEquals(192.0, line.tax, 0.01);
        assertEquals(1408.0, line.net, 0.01);
    }
}
