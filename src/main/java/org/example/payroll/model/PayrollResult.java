package org.example.payroll.model;

import java.util.ArrayList;
import java.util.List;

public class PayrollResult {
    public int month;
    public int year;
    public List<PayrollLine> lines = new ArrayList<>();
    public double totalGross;
    public double totalTax;
    public double totalNet;

    public PayrollResult(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public void addLine(PayrollLine line) {
        lines.add(line);
        totalGross += line.gross;
        totalTax += line.tax;
        totalNet += line.net;
    }

}
