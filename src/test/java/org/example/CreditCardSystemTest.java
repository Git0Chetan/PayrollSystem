package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreditCardSystemTest {

    @Test
    void testLuhnValidNumber() {
        CreditCardSystem.CreditCard card =
                new CreditCardSystem.CreditCard("4111111111111111", "Alice", "12/29", 123, "Visa", 1000.0);

        assertTrue(card.isValidNumber(), "Expected a valid Luhn number");
    }

    @Test
    void testLuhnInvalidNumber() {
        CreditCardSystem.CreditCard card =
                new CreditCardSystem.CreditCard("4111111111111112", "Alice", "12/29", 123, "Visa", 1000.0);

        assertFalse(card.isValidNumber(), "Expected an invalid Luhn number");
    }

    @Test
    void testChargeWithinLimit() {
        CreditCardSystem.CreditCard card =
                new CreditCardSystem.CreditCard("4111111111111111", "Alice", "12/29", 123, "Visa", 1000.0);

        CreditCardSystem.Transaction t = card.charge(200.0);
        assertTrue(t.success);
        assertEquals(200.0, card.getBalanceUsed(), 0.001);
    }

    @Test
    void testChargeOverLimit() {
        CreditCardSystem.CreditCard card =
                new CreditCardSystem.CreditCard("4111111111111111", "Alice", "12/29", 123, "Visa", 500.0);

        CreditCardSystem.Transaction t = card.charge(600.0);
        assertFalse(t.success);
    }

    @Test
    void testPayment() {
        CreditCardSystem.CreditCard card =
                new CreditCardSystem.CreditCard("4111111111111111", "Alice", "12/29", 123, "Visa", 1000.0);

        card.charge(400.0);
        CreditCardSystem.Transaction p = card.payment(150.0);
        assertTrue(p.success);
        assertEquals(250.0, card.getBalanceUsed(), 0.001);
    }

    @Test
    void testAvailableCredit() {
        CreditCardSystem.CreditCard card =
                new CreditCardSystem.CreditCard("4111111111111111", "Alice", "12/29", 123, "Visa", 1000.0);

        card.charge(200.0);
        assertEquals(800.0, card.getAvailableCredit(), 0.001);
    }
}