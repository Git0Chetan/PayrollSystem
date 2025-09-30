package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class CreditCardSystem$TransactionTest {

    // Define a fixed timestamp and formatter for deterministic tests
    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2023, 10, 26, 14, 30, 0);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Test case for a successful CHARGE transaction
    @Test
    void testToString_successfulCharge() {
        int cardId = 1;
        String cardNumber = "1234567890123456";
        double amount = 100.50;
        CreditCardSystem.Transaction.Type type = CreditCardSystem.Transaction.Type.CHARGE;
        boolean success = true;
        String note = "Charge successful";

        CreditCardSystem.Transaction transaction = new CreditCardSystem.Transaction(
                cardId, cardNumber, amount, type, FIXED_TIMESTAMP, success, note);

        String expectedTimestamp = FORMATTER.format(FIXED_TIMESTAMP);
        String expected = String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=%s",
                expectedTimestamp, cardNumber, type.name(), amount, success, note);

        assertEquals(expected, transaction.toString());
    }

    // Test case for a failed CHARGE transaction due to insufficient credit
    @Test
    void testToString_failedChargeInsufficientCredit() {
        int cardId = 2;
        String cardNumber = "9876543210987654";
        double amount = 5000.00;
        CreditCardSystem.Transaction.Type type = CreditCardSystem.Transaction.Type.CHARGE;
        boolean success = false;
        String note = "Insufficient credit";

        CreditCardSystem.Transaction transaction = new CreditCardSystem.Transaction(
                cardId, cardNumber, amount, type, FIXED_TIMESTAMP, success, note);

        String expectedTimestamp = FORMATTER.format(FIXED_TIMESTAMP);
        String expected = String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=%s",
                expectedTimestamp, cardNumber, type.name(), amount, success, note);

        assertEquals(expected, transaction.toString());
    }

    // Test case for a successful PAYMENT transaction
    @Test
    void testToString_successfulPayment() {
        int cardId = 3;
        String cardNumber = "1111222233334444";
        double amount = 75.25;
        CreditCardSystem.Transaction.Type type = CreditCardSystem.Transaction.Type.PAYMENT;
        boolean success = true;
        String note = "Payment successful";

        CreditCardSystem.Transaction transaction = new CreditCardSystem.Transaction(
                cardId, cardNumber, amount, type, FIXED_TIMESTAMP, success, note);

        String expectedTimestamp = FORMATTER.format(FIXED_TIMESTAMP);
        String expected = String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=%s",
                expectedTimestamp, cardNumber, type.name(), amount, success, note);

        assertEquals(expected, transaction.toString());
    }

    // Test case for a failed PAYMENT transaction due to overpayment
    @Test
    void testToString_failedPaymentOverpayment() {
        int cardId = 4;
        String cardNumber = "5555666677778888";
        double amount = 200.00;
        CreditCardSystem.Transaction.Type type = CreditCardSystem.Transaction.Type.PAYMENT;
        boolean success = false;
        String note = "Payment exceeds balance";

        CreditCardSystem.Transaction transaction = new CreditCardSystem.Transaction(
                cardId, cardNumber, amount, type, FIXED_TIMESTAMP, success, note);

        String expectedTimestamp = FORMATTER.format(FIXED_TIMESTAMP);
        String expected = String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=%s",
                expectedTimestamp, cardNumber, type.name(), amount, success, note);

        assertEquals(expected, transaction.toString());
    }

    // Test case for a transaction with zero amount
    @Test
    void testToString_zeroAmountTransaction() {
        int cardId = 5;
        String cardNumber = "0000000000000000";
        double amount = 0.00;
        CreditCardSystem.Transaction.Type type = CreditCardSystem.Transaction.Type.CHARGE;
        boolean success = false; // Usually 0 amount charge would be false
        String note = "Invalid amount";

        CreditCardSystem.Transaction transaction = new CreditCardSystem.Transaction(
                cardId, cardNumber, amount, type, FIXED_TIMESTAMP, success, note);

        String expectedTimestamp = FORMATTER.format(FIXED_TIMESTAMP);
        String expected = String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=%s",
                expectedTimestamp, cardNumber, type.name(), amount, success, note);

        assertEquals(expected, transaction.toString());
    }

    // Test case for a transaction with null note
    @Test
    void testToString_nullNote() {
        int cardId = 6;
        String cardNumber = "1234123412341234";
        double amount = 50.00;
        CreditCardSystem.Transaction.Type type = CreditCardSystem.Transaction.Type.CHARGE;
        boolean success = true;
        String note = null; // Note can technically be null if not handled upstream

        CreditCardSystem.Transaction transaction = new CreditCardSystem.Transaction(
                cardId, cardNumber, amount, type, FIXED_TIMESTAMP, success, note);

        String expectedTimestamp = FORMATTER.format(FIXED_TIMESTAMP);
        // The String.format will convert null to "null"
        String expected = String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=null",
                expectedTimestamp, cardNumber, type.name(), amount, success);

        assertEquals(expected, transaction.toString());
    }

    // Test case for a transaction with a very long card number (though not typical)
    @Test
    void testToString_longCardNumber() {
        int cardId = 7;
        String cardNumber = "12345678901234567890123456789012"; // Longer than standard
        double amount = 1.99;
        CreditCardSystem.Transaction.Type type = CreditCardSystem.Transaction.Type.PAYMENT;
        boolean success = true;
        String note = "Small payment";

        CreditCardSystem.Transaction transaction = new CreditCardSystem.Transaction(
                cardId, cardNumber, amount, type, FIXED_TIMESTAMP, success, note);

        String expectedTimestamp = FORMATTER.format(FIXED_TIMESTAMP);
        String expected = String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=%s",
                expectedTimestamp, cardNumber, type.name(), amount, success, note);

        assertEquals(expected, transaction.toString());
    }
}