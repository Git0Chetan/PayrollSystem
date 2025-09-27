package org.example.payroll.model;

public class PayrollLine {
    public String employeeId;
    public String employeeName;
    public String type;
    public double gross;
    public double tax;
    public double net;
    public int month;
    public int year;

    public PayrollLine(String employeeId, String employeeName, String type,
                       double gross, double tax, double net,
                       int month, int year) {
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
