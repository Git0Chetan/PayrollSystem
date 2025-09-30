package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.YearMonth;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardSystemTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        // Ensure consistent locale for number parsing and output
        Locale.setDefault(Locale.US);
        // Reset SEQ for deterministic card IDs, though main method creates cards internally
        // CreditCardSystem.CreditCard.SEQ = 1; // This field is private static, so cannot be directly reset here
        // If SEQ affects logic that needs to be tested across multiple runs of main,
        // it would require reflection or a modification to the SUT.
        // For testing the main method, the cards are instantiated fresh each run, so SEQ's global state isn't a major issue.
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        Locale.setDefault(Locale.getDefault()); // Reset to default locale after tests
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    private String getOutput() {
        return outputStreamCaptor.toString().replace("\r\n", "\n").trim();
    }

    // Test case for exiting the application
    @Test
    @DisplayName("main method should exit gracefully when '5' is chosen")
    void testMain_ExitApplication() {
        provideInput("5\n"); // Choose exit
        CreditCardSystem.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Goodbye!"), "Output should contain 'Goodbye!' on exit.");
        assertFalse(output.contains("Unknown option."), "Should not show 'Unknown option.'");
    }

    // Test case for listing cards
    @Test
    @DisplayName("main method should list seeded cards when '1' is chosen")
    void testMain_ListCards() {
        provideInput("1\n5\n"); // Choose list cards, then exit
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Simple In-Memory Credit Card System (Single File)"), "Output should contain welcome message");
        assertTrue(output.contains("Menu:"), "Output should contain menu options");
        assertTrue(output.contains("Card{id=1, number=4111 **** **** 1111, holder=Alice Smith, expiry=2029-12, balanceUsed=0.00, limit=5000.00}"), "Output should contain Alice's card");
        assertTrue(output.contains("Card{id=2, number=5555 **** **** 4444, holder=Bob Johnson, expiry=2030-11, balanceUsed=0.00, limit=3000.00}"), "Output should contain Bob's card");
    }

    // Test case for charging a card successfully
    @Test
    @DisplayName("main method should successfully charge a card when '2' is chosen with valid input")
    void testMain_ChargeCard_Success() {
        // Input: 2 (charge) -> card number -> amount -> 5 (exit)
        String input = "2\n4111111111111111\n100.50\n5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Note=Charge successful"), "Charge should be successful");
        assertTrue(output.contains("Amount=100.50"), "Transaction amount should be correct");
        // Verify balance change by listing cards
        provideInput("1\n5\n"); // List cards again to check balance, then exit
        CreditCardSystem.main(new String[]{});
        String balanceOutput = getOutput();
        assertTrue(balanceOutput.contains("balanceUsed=100.50"), "Alice's card balance should be updated");
    }

    // Test case for charging a card with insufficient credit
    @Test
    @DisplayName("main method should handle insufficient credit when charging")
    void testMain_ChargeCard_InsufficientCredit() {
        // Input: 2 (charge) -> card number -> amount (exceeding limit) -> 5 (exit)
        String input = "2\n4111111111111111\n5000.01\n5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Note=Insufficient credit"), "Charge should fail due to insufficient credit");
        assertFalse(output.contains("Charge successful"), "Charge should not be successful");

        // Verify balance did not change
        provideInput("1\n5\n");
        CreditCardSystem.main(new String[]{});
        String balanceOutput = getOutput();
        assertTrue(balanceOutput.contains("balanceUsed=0.00"), "Alice's card balance should remain 0.00");
    }

    // Test case for charging a card that is not found
    @Test
    @DisplayName("main method should report 'Card not found' when charging a non-existent card")
    void testMain_ChargeCard_NotFound() {
        // Input: 2 (charge) -> non-existent card number -> amount -> 5 (exit)
        String input = "2\n9999888877776666\n100.00\n5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Card not found."), "Output should indicate card not found.");
        assertFalse(output.contains("Charge successful"), "No charge should occur.");
    }

    // Test case for making a payment successfully
    @Test
    @DisplayName("main method should successfully make a payment when '3' is chosen with valid input")
    void testMain_MakePayment_Success() {
        // First, charge the card
        String chargeInput = "2\n4111111111111111\n200.00\n"; // Charge 200
        // Then, pay part of it
        String payInput = "3\n4111111111111111\n50.00\n"; // Pay 50
        String exitInput = "5\n"; // Exit

        provideInput(chargeInput + payInput + exitInput);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Note=Charge successful"), "Initial charge should be successful");
        assertTrue(output.contains("Note=Payment successful"), "Payment should be successful");
        assertTrue(output.contains("Amount=50.00"), "Payment transaction amount should be correct");

        // Verify balance change
        provideInput("1\n5\n"); // List cards again to check balance, then exit
        CreditCardSystem.main(new String[]{});
        String balanceOutput = getOutput();
        assertTrue(balanceOutput.contains("balanceUsed=150.00"), "Alice's card balance should be updated (200 - 50 = 150)");
    }

    // Test case for making a payment exceeding balance
    @Test
    @DisplayName("main method should handle payment exceeding balance")
    void testMain_MakePayment_ExceedsBalance() {
        // First, charge the card
        String chargeInput = "2\n4111111111111111\n100.00\n"; // Charge 100
        // Then, attempt to overpay
        String payInput = "3\n4111111111111111\n150.00\n"; // Attempt to pay 150
        String exitInput = "5\n"; // Exit

        provideInput(chargeInput + payInput + exitInput);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Note=Charge successful"), "Initial charge should be successful");
        assertTrue(output.contains("Note=Payment exceeds balance"), "Payment should fail due to exceeding balance");
        assertFalse(output.contains("Payment successful"), "Payment should not be successful");

        // Verify balance did not change after failed payment
        provideInput("1\n5\n");
        CreditCardSystem.main(new String[]{});
        String balanceOutput = getOutput();
        assertTrue(balanceOutput.contains("balanceUsed=100.00"), "Alice's card balance should remain 100.00");
    }

    // Test case for making a payment to a card that is not found
    @Test
    @DisplayName("main method should report 'Card not found' when making payment to a non-existent card")
    void testMain_MakePayment_NotFound() {
        // Input: 3 (payment) -> non-existent card number -> amount -> 5 (exit)
        String input = "3\n9999888877776666\n50.00\n5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Card not found."), "Output should indicate card not found.");
        assertFalse(output.contains("Payment successful"), "No payment should occur.");
    }

    // Test case for showing card details
    @Test
    @DisplayName("main method should show card details when '4' is chosen with valid card number")
    void testMain_ShowCardDetails_Success() {
        // Input: 4 (show details) -> card number -> 5 (exit)
        String input = "4\n5555555555554444\n5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Card details:"), "Output should contain card details header");
        assertTrue(output.contains("ID: 2"), "Output should contain correct card ID");
        assertTrue(output.contains("Number: 5555555555554444"), "Output should contain full card number");
        assertTrue(output.contains("Holder: Bob Johnson"), "Output should contain holder name");
        assertTrue(output.contains("Type: MasterCard"), "Output should contain card type");
        assertTrue(output.contains("Expiry: 2030-11"), "Output should contain expiry date");
        assertTrue(output.contains("Credit Limit: 3000.0"), "Output should contain credit limit");
        assertTrue(output.contains("Balance Used: 0.0"), "Output should contain balance used");
        assertTrue(output.contains("Available: 3000.0"), "Output should contain available credit");
    }

    // Test case for showing card details for a non-existent card
    @Test
    @DisplayName("main method should report 'Card not found' when showing details for a non-existent card")
    void testMain_ShowCardDetails_NotFound() {
        // Input: 4 (show details) -> non-existent card number -> 5 (exit)
        String input = "4\n1111222233334444\n5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Card not found."), "Output should indicate card not found.");
        assertFalse(output.contains("Card details:"), "No card details should be shown.");
    }

    // Test case for an unknown menu option
    @Test
    @DisplayName("main method should handle unknown menu option gracefully")
    void testMain_UnknownOption() {
        // Input: 9 (unknown) -> 5 (exit)
        String input = "9\n5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Unknown option."), "Output should contain 'Unknown option.'");
        assertTrue(output.contains("Menu:"), "Menu should be displayed again after unknown option.");
    }

    // Test case for entering non-numeric amount causing NumberFormatException
    // The current main method does not handle NumberFormatException, so this test expects a failure
    @Test
    @DisplayName("main method should throw NumberFormatException when charging with non-numeric amount")
    void testMain_ChargeCard_InvalidAmountInput() {
        // Input: 2 (charge) -> card number -> "abc" (invalid amount)
        String input = "2\n4111111111111111\nabc\n";
        provideInput(input);

        // Expect NumberFormatException because Double.parseDouble is called directly
        assertThrows(NumberFormatException.class, () -> CreditCardSystem.main(new String[]{}));
    }

    @Test
    @DisplayName("main method should throw NumberFormatException when paying with non-numeric amount")
    void testMain_Payment_InvalidAmountInput() {
        // Input: 3 (payment) -> card number -> "xyz" (invalid amount)
        String input = "3\n4111111111111111\nxyz\n";
        provideInput(input);

        // Expect NumberFormatException because Double.parseDouble is called directly
        assertThrows(NumberFormatException.class, () -> CreditCardSystem.main(new String[]{}));
    }

    // Test case for a sequence of operations: charge, payment, list, exit
    @Test
    @DisplayName("main method should handle a sequence of charge, payment, and list operations")
    void testMain_SequenceOfOperations() {
        // Input sequence:
        // 2 (charge) -> Alice -> 100.00
        // 3 (payment) -> Alice -> 25.00
        // 1 (list)
        // 5 (exit)
        String input = "2\n4111111111111111\n100.00\n" +
                       "3\n4111111111111111\n25.00\n" +
                       "1\n" +
                       "5\n";
        provideInput(input);
        CreditCardSystem.main(new String[]{});
        String output = getOutput();

        assertTrue(output.contains("Note=Charge successful"), "Charge should be successful");
        assertTrue(output.contains("Note=Payment successful"), "Payment should be successful");
        assertTrue(output.contains("balanceUsed=75.00"), "Alice's card balance should be 75.00 after sequence");
        assertTrue(output.contains("Card{id=1, number=4111 **** **** 1111, holder=Alice Smith, expiry=2029-12, balanceUsed=75.00, limit=5000.00}"), "List should show updated balance for Alice's card");
        assertTrue(output.contains("Goodbye!"), "Application should exit gracefully");
    }

    // Test case for charging with zero or negative amount
    @Test
    @DisplayName("main method should handle charging with zero or negative amount")
    void testMain_ChargeCard_InvalidAmount() {
        // Input: 2 (charge) -> card number -> 0.00 -> 5 (exit)
        String inputZero = "2\n4111111111111111\n0.00\n5\n";
        provideInput(inputZero);
        CreditCardSystem.main(new String[]{});
        String outputZero = getOutput();
        assertTrue(outputZero.contains("Note=Invalid amount"), "Charge with 0.00 should be invalid");

        setUp(); // Reset streams for next test segment
        // Input: 2 (charge) -> card number -> -50.00 -> 5 (exit)
        String inputNegative = "2\n4111111111111111\n-50.00\n5\n";
        provideInput(inputNegative);
        CreditCardSystem.main(new String[]{});
        String outputNegative = getOutput();
        assertTrue(outputNegative.contains("Note=Invalid amount"), "Charge with negative amount should be invalid");
    }

    // Test case for paying with zero or negative amount
    @Test
    @DisplayName("main method should handle paying with zero or negative amount")
    void testMain_Payment_InvalidAmount() {
        // First, charge a card
        String chargeInput = "2\n4111111111111111\n100.00\n";
        // Then, attempt payment with zero
        String payZeroInput = "3\n4111111111111111\n0.00\n5\n";
        provideInput(chargeInput + payZeroInput);
        CreditCardSystem.main(new String[]{});
        String outputZero = getOutput();
        assertTrue(outputZero.contains("Note=Invalid amount"), "Payment with 0.00 should be invalid");

        setUp(); // Reset streams
        chargeInput = "2\n4111111111111111\n100.00\n";
        // Then, attempt payment with negative
        String payNegativeInput = "3\n4111111111111111\n-50.00\n5\n";
        provideInput(chargeInput + payNegativeInput);
        CreditCardSystem.main(new String[]{});
        String outputNegative = getOutput();
        assertTrue(outputNegative.contains("Note=Invalid amount"), "Payment with negative amount should be invalid");
    }
}