package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentProcessorTest {
    @Test
    void testFullTimePayroll() {
        PaymentProcessor.FullTimeEmployee ft =
                new PaymentProcessor.FullTimeEmployee("FT-1", "Alice", "Engineering", 5000.0, 0.20);

        int month = 9;
        int year = 2025;

        double gross = ft.calculateGross(month, year);
        double tax = gross * ft.getTaxRate();
        double net = gross - tax;

        assertEquals(5000.0, gross, 0.0001);
        assertEquals(1000.0, tax, 0.0001);
        assertEquals(4000.0, net, 0.0001);
    }

    @Test
    void testPartTimePayroll() {
        PaymentProcessor.PartTimeEmployee pt =
                new PaymentProcessor.PartTimeEmployee("PT-1", "Bob", "QA", 20.0, 80, 0.12);

        int month = 9;
        int year = 2025;

        double gross = pt.calculateGross(month, year);
        double tax = gross * pt.getTaxRate();
        double net = gross - tax;

        assertEquals(1600.0, gross, 0.01);
        assertEquals(192.0, tax, 0.01);
        assertEquals(1408.0, net, 0.01);
    }
}
