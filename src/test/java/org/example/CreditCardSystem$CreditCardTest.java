package org.example;

import org.example.CreditCardSystem.CreditCard;
import org.example.CreditCardSystem.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CreditCardSystem.CreditCard Tests")
class CreditCardSystem$CreditCardTest {

    private CreditCard card;
    private static int initialId;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Reset SEQ to a known value before each test to ensure predictable IDs
        // This is necessary because SEQ is static and increments across all tests.
        Field seqField = CreditCard.class.getDeclaredField("SEQ");
        seqField.setAccessible(true);
        initialId = (int) seqField.get(null);
        seqField.set(null, 1); // Reset to 1 for consistent starting IDs

        // Default card for many tests
        card = new CreditCard("4111111111111111", "Alice Smith", "12/29", 123, "Visa", 5000.00);
        
        // Ensure Locale is set to US for consistent number formatting in toString()
        // The main method sets it, but tests run independently.
        Locale.setDefault(Locale.US);
    }

    // Test case for getting the card ID
    @Test
    void testGetId() {
        assertEquals(1, card.getId(), "Initial card ID should be 1 after SEQ reset.");
        CreditCard anotherCard = new CreditCard("5555555555554444", "Bob Johnson", "11/30", 456, "MasterCard", 3000.00);
        assertEquals(2, anotherCard.getId(), "Second card ID should be 2.");
    }

    // Test case for getting the masked card number
    @Test
    void testGetNumberMasked() {
        assertEquals("4111 **** **** 1111", card.getNumberMasked(), "Masked number should show first and last 4 digits.");
    }

    // Test case for getting the masked card number with short number
    @Test
    void testGetNumberMasked_shortNumber() {
        CreditCard shortCard = new CreditCard("1234567", "Shorty", "12/25", 111, "Unknown", 1000.0);
        assertEquals("****", shortCard.getNumberMasked(), "Masked number should be '****' for numbers less than 8 digits.");
    }

    // Test case for getting the masked card number with spaces in original number
    @Test
    void testGetNumberMasked_withSpaces() {
        CreditCard spacedCard = new CreditCard("4111 2222 3333 4444", "Spaced Holder", "12/29", 123, "Visa", 5000.00);
        assertEquals("4111 **** **** 4444", spacedCard.getNumberMasked(), "Masked number should be correct even if original had spaces.");
    }

    // Test case for getting the full card number
    @Test
    void testGetNumber() {
        assertEquals("4111111111111111", card.getNumber(), "Should return the full, unmasked card number without spaces.");
    }

    // Test case for getting the holder name
    @Test
    void testGetHolderName() {
        assertEquals("Alice Smith", card.getHolderName(), "Should return the correct holder name.");
    }

    // Test case for getting the expiry date
    @Test
    void testGetExpiry() {
        assertEquals(YearMonth.of(2029, 12), card.getExpiry(), "Should return the correct YearMonth expiry.");
    }

    // Test case for getting the CVV
    @Test
    void testGetCvv() {
        assertEquals(123, card.getCvv(), "Should return the correct CVV.");
    }

    // Test case for getting the card type
    @Test
    void testGetType() {
        assertEquals("Visa", card.getType(), "Should return the correct card type.");
    }

    // Test case for getting the credit limit
    @Test
    void testGetCreditLimit() {
        assertEquals(5000.00, card.getCreditLimit(), 0.001, "Should return the correct credit limit.");
    }

    // Test case for getting the initial balance used
    @Test
    void testGetBalanceUsed_initial() {
        assertEquals(0.00, card.getBalanceUsed(), 0.001, "Initial balance used should be 0.");
    }

    // Test case for getting the balance used after a charge
    @Test
    void testGetBalanceUsed_afterCharge() {
        card.charge(100.00);
        assertEquals(100.00, card.getBalanceUsed(), 0.001, "Balance used should update after a charge.");
    }

    // Test case for getting the balance used after a payment
    @Test
    void testGetBalanceUsed_afterPayment() {
        card.charge(500.00);
        card.payment(200.00);
        assertEquals(300.00, card.getBalanceUsed(), 0.001, "Balance used should update after a payment.");
    }

    // Test case for getting the initial available credit
    @Test
    void testGetAvailableCredit_initial() {
        assertEquals(5000.00, card.getAvailableCredit(), 0.001, "Initial available credit should be equal to credit limit.");
    }

    // Test case for getting available credit after a charge
    @Test
    void testGetAvailableCredit_afterCharge() {
        card.charge(1000.00);
        assertEquals(4000.00, card.getAvailableCredit(), 0.001, "Available credit should decrease after a charge.");
    }

    // Test case for getting available credit after a payment
    @Test
    void testGetAvailableCredit_afterPayment() {
        card.charge(1000.00);
        card.payment(500.00);
        assertEquals(4500.00, card.getAvailableCredit(), 0.001, "Available credit should increase after a payment.");
    }

    // Test case for a valid card number (Luhn check)
    @Test
    void testIsValidNumber_validLuhn() {
        CreditCard validCard = new CreditCard("49927398716", "Luhn Test", "12/25", 111, "Test", 1000.0); // A valid Luhn number
        assertTrue(validCard.isValidNumber(), "Should return true for a valid Luhn number.");
    }

    // Test case for an invalid card number (Luhn check)
    @Test
    void testIsValidNumber_invalidLuhn() {
        CreditCard invalidCard = new CreditCard("49927398717", "Luhn Test", "12/25", 111, "Test", 1000.0); // Invalid Luhn number
        assertFalse(invalidCard.isValidNumber(), "Should return false for an invalid Luhn number.");
    }

    // Test case for an invalid card number with non-digits
    @Test
    void testIsValidNumber_nonDigits() {
        CreditCard invalidCard = new CreditCard("4992739871A", "Luhn Test", "12/25", 111, "Test", 1000.0);
        assertFalse(invalidCard.isValidNumber(), "Should return false if number contains non-digit characters.");
    }

    // Test case for `isExpired()` with a future expiry date
    @Test
    void testIsExpired_futureDate() {
        YearMonth futureExpiry = YearMonth.now().plusMonths(1);
        CreditCard futureCard = new CreditCard("1234", "Future", futureExpiry.format(java.time.format.DateTimeFormatter.ofPattern("MM/yy")), 111, "Test", 100.0);
        assertFalse(futureCard.isExpired(), "Card with future expiry should not be expired.");
    }

    // Test case for `isExpired()` with a past expiry date
    @Test
    void testIsExpired_pastDate() {
        YearMonth pastExpiry = YearMonth.now().minusMonths(1);
        CreditCard pastCard = new CreditCard("1234", "Past", pastExpiry.format(java.time.format.DateTimeFormatter.ofPattern("MM/yy")), 111, "Test", 100.0);
        assertTrue(pastCard.isExpired(), "Card with past expiry should be expired.");
    }

    // Test case for `isExpired()` with the current month and year
    @Test
    void testIsExpired_currentMonthYear() {
        YearMonth currentExpiry = YearMonth.now();
        CreditCard currentCard = new CreditCard("1234", "Current", currentExpiry.format(java.time.format.DateTimeFormatter.ofPattern("MM/yy")), 111, "Test", 100.0);
        assertFalse(currentCard.isExpired(), "Card expiring in the current month/year should not be considered expired.");
    }

    // Test case for `canCharge()` with a valid amount and sufficient credit on an unexpired card
    @Test
    void testCanCharge_valid() {
        assertTrue(card.canCharge(100.00), "Should be able to charge a valid amount on an unexpired card with sufficient credit.");
    }

    // Test case for `canCharge()` with a zero amount
    @Test
    void testCanCharge_zeroAmount() {
        assertFalse(card.canCharge(0.00), "Should not be able to charge a zero amount.");
    }

    // Test case for `canCharge()` with a negative amount
    @Test
    void testCanCharge_negativeAmount() {
        assertFalse(card.canCharge(-50.00), "Should not be able to charge a negative amount.");
    }

    // Test case for `canCharge()` with an expired card
    @Test
    void testCanCharge_expiredCard() {
        YearMonth pastExpiry = YearMonth.now().minusMonths(1);
        CreditCard expiredCard = new CreditCard("1234", "Expired", pastExpiry.format(java.time.format.DateTimeFormatter.ofPattern("MM/yy")), 111, "Test", 100.0);
        assertFalse(expiredCard.canCharge(10.00), "Should not be able to charge an expired card.");
    }

    // Test case for `canCharge()` with insufficient credit
    @Test
    void testCanCharge_insufficientCredit() {
        card.charge(4900.00); // Use up most of the credit
        assertFalse(card.canCharge(200.00), "Should not be able to charge if credit is insufficient.");
    }

    // Test case for `canCharge()` with an amount exactly equal to available credit
    @Test
    void testCanCharge_exactAvailableCredit() {
        card.charge(4000.00);
        assertTrue(card.canCharge(1000.00), "Should be able to charge an amount exactly equal to available credit.");
    }

    // Test case for `charge()` a valid amount
    @Test
    void testCharge_success() {
        double initialBalance = card.getBalanceUsed();
        double chargeAmount = 100.00;
        Transaction transaction = card.charge(chargeAmount);

        assertTrue(transaction.success, "Charge should be successful.");
        assertEquals(initialBalance + chargeAmount, card.getBalanceUsed(), 0.001, "Balance used should increase by charge amount.");
        assertEquals("Charge successful", transaction.note, "Transaction note should indicate success.");
        assertEquals(chargeAmount, transaction.amount, 0.001);
        assertEquals(Transaction.Type.CHARGE, transaction.type);
    }

    // Test case for `charge()` a zero amount
    @Test
    void testCharge_zeroAmount() {
        double initialBalance = card.getBalanceUsed();
        Transaction transaction = card.charge(0.00);

        assertFalse(transaction.success, "Charge should fail for zero amount.");
        assertEquals(initialBalance, card.getBalanceUsed(), 0.001, "Balance used should not change for zero amount.");
        assertEquals("Invalid amount", transaction.note, "Transaction note should indicate invalid amount.");
    }

    // Test case for `charge()` a negative amount
    @Test
    void testCharge_negativeAmount() {
        double initialBalance = card.getBalanceUsed();
        Transaction transaction = card.charge(-50.00);

        assertFalse(transaction.success, "Charge should fail for negative amount.");
        assertEquals(initialBalance, card.getBalanceUsed(), 0.001, "Balance used should not change for negative amount.");
        assertEquals("Invalid amount", transaction.note, "Transaction note should indicate invalid amount.");
    }

    // Test case for `charge()` on an expired card
    @Test
    void testCharge_expiredCard() {
        YearMonth pastExpiry = YearMonth.now().minusMonths(1);
        CreditCard expiredCard = new CreditCard("1234", "Expired", pastExpiry.format(java.time.format.DateTimeFormatter.ofPattern("MM/yy")), 111, "Test", 100.0);
        double initialBalance = expiredCard.getBalanceUsed();
        Transaction transaction = expiredCard.charge(10.00);

        assertFalse(transaction.success, "Charge should fail for an expired card.");
        assertEquals(initialBalance, expiredCard.getBalanceUsed(), 0.001, "Balance used should not change for expired card.");
        assertEquals("Card expired", transaction.note, "Transaction note should indicate expired card.");
    }

    // Test case for `charge()` with insufficient credit
    @Test
    void testCharge_insufficientCredit() {
        card.charge(4900.00);
        double initialBalance = card.getBalanceUsed();
        Transaction transaction = card.charge(200.00); // 100 remaining, trying to charge 200

        assertFalse(transaction.success, "Charge should fail for insufficient credit.");
        assertEquals(initialBalance, card.getBalanceUsed(), 0.001, "Balance used should not change for insufficient credit.");
        assertEquals("Insufficient credit", transaction.note, "Transaction note should indicate insufficient credit.");
    }

    // Test case for `payment()` a valid amount
    @Test
    void testPayment_success() {
        card.charge(500.00);
        double initialBalance = card.getBalanceUsed();
        double paymentAmount = 200.00;
        Transaction transaction = card.payment(paymentAmount);

        assertTrue(transaction.success, "Payment should be successful.");
        assertEquals(initialBalance - paymentAmount, card.getBalanceUsed(), 0.001, "Balance used should decrease by payment amount.");
        assertEquals("Payment successful", transaction.note, "Transaction note should indicate success.");
        assertEquals(paymentAmount, transaction.amount, 0.001);
        assertEquals(Transaction.Type.PAYMENT, transaction.type);
    }

    // Test case for `payment()` a zero amount
    @Test
    void testPayment_zeroAmount() {
        card.charge(100.00);
        double initialBalance = card.getBalanceUsed();
        Transaction transaction = card.payment(0.00);

        assertFalse(transaction.success, "Payment should fail for zero amount.");
        assertEquals(initialBalance, card.getBalanceUsed(), 0.001, "Balance used should not change for zero amount.");
        assertEquals("Invalid amount", transaction.note, "Transaction note should indicate invalid amount.");
    }

    // Test case for `payment()` a negative amount
    @Test
    void testPayment_negativeAmount() {
        card.charge(100.00);
        double initialBalance = card.getBalanceUsed();
        Transaction transaction = card.payment(-50.00);

        assertFalse(transaction.success, "Payment should fail for negative amount.");
        assertEquals(initialBalance, card.getBalanceUsed(), 0.001, "Balance used should not change for negative amount.");
        assertEquals("Invalid amount", transaction.note, "Transaction note should indicate invalid amount.");
    }

    // Test case for `payment()` exceeding balance (overpayment)
    @Test
    void testPayment_overpayment() {
        card.charge(100.00);
        double initialBalance = card.getBalanceUsed();
        Transaction transaction = card.payment(200.00); // Balance is 100, trying to pay 200

        assertFalse(transaction.success, "Payment should fail for overpayment.");
        assertEquals(initialBalance, card.getBalanceUsed(), 0.001, "Balance used should not change for overpayment.");
        assertEquals("Payment exceeds balance", transaction.note, "Transaction note should indicate overpayment.");
    }

    // Test case for `payment()` exact balance
    @Test
    void testPayment_exactBalance() {
        card.charge(100.00);
        Transaction transaction = card.payment(100.00);

        assertTrue(transaction.success, "Payment should be successful for exact balance.");
        assertEquals(0.00, card.getBalanceUsed(), 0.001, "Balance used should be 0 after exact payment.");
        assertEquals("Payment successful", transaction.note);
    }

    // Test case for `parseExpiry()` with MM/YY format
    @Test
    void testParseExpiry_MMYY() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseExpiryMethod = CreditCard.class.getDeclaredMethod("parseExpiry", String.class);
        parseExpiryMethod.setAccessible(true);

        YearMonth result = (YearMonth) parseExpiryMethod.invoke(null, "10/25");
        assertEquals(YearMonth.of(2025, 10), result, "Should correctly parse MM/YY format.");
    }

    // Test case for `parseExpiry()` with MM/YYYY format
    @Test
    void testParseExpiry_MMYYYY() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseExpiryMethod = CreditCard.class.getDeclaredMethod("parseExpiry", String.class);
        parseExpiryMethod.setAccessible(true);

        YearMonth result = (YearMonth) parseExpiryMethod.invoke(null, "03/2030");
        assertEquals(YearMonth.of(2030, 3), result, "Should correctly parse MM/YYYY format.");
    }

    // Test case for `parseExpiry()` with spaces
    @Test
    void testParseExpiry_withSpaces() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseExpiryMethod = CreditCard.class.getDeclaredMethod("parseExpiry", String.class);
        parseExpiryMethod.setAccessible(true);

        YearMonth result = (YearMonth) parseExpiryMethod.invoke(null, " 06 / 28 ");
        assertEquals(YearMonth.of(2028, 6), result, "Should correctly parse with leading/trailing/internal spaces.");
    }
    
    // Test case for `parseExpiry()` with only month (defaults to current year)
    @Test
    void testParseExpiry_onlyMonth() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseExpiryMethod = CreditCard.class.getDeclaredMethod("parseExpiry", String.class);
        parseExpiryMethod.setAccessible(true);

        YearMonth result = (YearMonth) parseExpiryMethod.invoke(null, "07");
        assertEquals(YearMonth.of(YearMonth.now().getYear(), 7), result, "Should default to current year if only month is provided.");
    }

    // Test case for `parseExpiry()` with null input
    @Test
    void testParseExpiry_nullInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseExpiryMethod = CreditCard.class.getDeclaredMethod("parseExpiry", String.class);
        parseExpiryMethod.setAccessible(true);

        YearMonth result = (YearMonth) parseExpiryMethod.invoke(null, (String) null);
        assertEquals(YearMonth.of(2100, 1), result, "Should return default expiry (2100-01) for null input.");
    }

    // Test case for `parseExpiry()` with invalid month (e.g., "13/25")
    @Test
    void testParseExpiry_invalidMonth() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseExpiryMethod = CreditCard.class.getDeclaredMethod("parseExpiry", String.class);
        parseExpiryMethod.setAccessible(true);

        InvocationTargetException e = assertThrows(InvocationTargetException.class, () ->
                parseExpiryMethod.invoke(null, "13/25"));
        assertTrue(e.getTargetException() instanceof DateTimeException, "Should throw DateTimeException for invalid month.");
    }

    // Test case for `parseExpiry()` with non-numeric input for month/year
    @ParameterizedTest
    @ValueSource(strings = {"AA/25", "10/BB", "XX/YY"})
    void testParseExpiry_nonNumeric(String expiryStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseExpiryMethod = CreditCard.class.getDeclaredMethod("parseExpiry", String.class);
        parseExpiryMethod.setAccessible(true);

        InvocationTargetException e = assertThrows(InvocationTargetException.class, () ->
                parseExpiryMethod.invoke(null, expiryStr));
        assertTrue(e.getTargetException() instanceof NumberFormatException, "Should throw NumberFormatException for non-numeric month/year.");
    }

    // Parameterized test for `luhnCheck()` with various valid and invalid numbers
    @ParameterizedTest
    @CsvSource({
            "49927398716, true", // Valid Luhn number
            "49927398717, false", // Invalid Luhn number (last digit changed)
            "4111111111111111, true", // Visa test card
            "5555555555554444, true", // MasterCard test card
            "1234567890123452, true", // Another valid Luhn
            "1234567890123451, false", // Invalid Luhn
            "1, false", // Too short
            "1234567890123456789012345678901234567890, false" // Too long (but still checked)
    })
    void testLuhnCheck_validAndInvalid(String cardNumber, boolean expected) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method luhnCheckMethod = CreditCard.class.getDeclaredMethod("luhnCheck", String.class);
        luhnCheckMethod.setAccessible(true);

        boolean result = (boolean) luhnCheckMethod.invoke(null, cardNumber);
        assertEquals(expected, result, "Luhn check for " + cardNumber + " should be " + expected);
    }

    // Test case for `luhnCheck()` with null or empty string
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  \t  "})
    void testLuhnCheck_nullEmptyOrBlank(String cardNumber) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method luhnCheckMethod = CreditCard.class.getDeclaredMethod("luhnCheck", String.class);
        luhnCheckMethod.setAccessible(true);

        boolean result = (boolean) luhnCheckMethod.invoke(null, cardNumber);
        assertFalse(result, "Luhn check should return false for null, empty, or blank strings.");
    }

    // Test case for `luhnCheck()` with non-digit characters
    @ParameterizedTest
    @ValueSource(strings = {"123A456", "abcde", "123-456", "123 456"})
    void testLuhnCheck_nonDigits(String cardNumber) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method luhnCheckMethod = CreditCard.class.getDeclaredMethod("luhnCheck", String.class);
        luhnCheckMethod.setAccessible(true);

        boolean result = (boolean) luhnCheckMethod.invoke(null, cardNumber);
        assertFalse(result, "Luhn check should return false if string contains non-digit characters.");
    }

    // Test case for `luhnCheck()` with spaces that are removed
    @Test
    void testLuhnCheck_withSpaces() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method luhnCheckMethod = CreditCard.class.getDeclaredMethod("luhnCheck", String.class);
        luhnCheckMethod.setAccessible(true);

        // A valid Luhn number with spaces
        boolean result = (boolean) luhnCheckMethod.invoke(null, "4992 7398 716");
        assertTrue(result, "Luhn check should handle spaces in the number by removing them.");
    }

    // Test case for `toString()` method with initial state
    @Test
    void testToString_initialState() {
        String expected = String.format("Card{id=%d, number=4111 **** **** 1111, holder=Alice Smith, expiry=2029-12, balanceUsed=0.00, limit=5000.00}", card.getId());
        assertEquals(expected, card.toString(), "ToString should match expected format for initial card state.");
    }

    // Test case for `toString()` method after a charge
    @Test
    void testToString_afterCharge() {
        card.charge(150.75);
        String expected = String.format("Card{id=%d, number=4111 **** **** 1111, holder=Alice Smith, expiry=2029-12, balanceUsed=150.75, limit=5000.00}", card.getId());
        assertEquals(expected, card.toString(), "ToString should reflect updated balanceUsed after charge.");
    }

    // Test case for `toString()` method after a payment
    @Test
    void testToString_afterPayment() {
        card.charge(300.00);
        card.payment(100.50);
        String expected = String.format("Card{id=%d, number=4111 **** **** 1111, holder=Alice Smith, expiry=2029-12, balanceUsed=199.50, limit=5000.00}", card.getId());
        assertEquals(expected, card.toString(), "ToString should reflect updated balanceUsed after payment.");
    }
}