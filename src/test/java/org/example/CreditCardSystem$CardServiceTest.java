package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.CreditCardSystem.CardService;
import org.example.CreditCardSystem.CreditCard;

class CreditCardSystem$CardServiceTest {

    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardService = new CardService();
        // Note: CreditCard.SEQ is a static counter. Its incrementing nature
        // across tests is generally fine as long as test assertions
        // don't rely on specific ID values but rather on object equality
        // or list sizes. For these tests, object identity is used.
    }

    // Test case for successfully adding a valid credit card
    @Test
    void addCard_ValidCard_AddsSuccessfully() {
        CreditCard validCard = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        cardService.addCard(validCard);
        List<CreditCard> cards = cardService.listCards();
        assertNotNull(cards);
        assertEquals(1, cards.size());
        assertEquals(validCard, cards.get(0));
    }

    // Test case for adding a card with an invalid number (Luhn check fails)
    @Test
    void addCard_InvalidCardNumber_ThrowsIllegalArgumentException() {
        // A number that fails Luhn check (e.g., changed last digit from '4' to '0' for a Visa-like number)
        CreditCard invalidCard = new CreditCard("4111222233334440", "Jane Doe", "12/25", 123, "Visa", 1000.0);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cardService.addCard(invalidCard);
        });
        assertTrue(thrown.getMessage().contains("Invalid card number (Luhn check failed)."));
        assertEquals(0, cardService.listCards().size()); // Ensure no card was added
    }

    // Test case for adding multiple valid cards
    @Test
    void addCard_MultipleValidCards_AddsAllSuccessfully() {
        CreditCard card1 = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        CreditCard card2 = new CreditCard("5400111122223333", "Jane Smith", "01/26", 456, "MasterCard", 2000.0);

        cardService.addCard(card1);
        cardService.addCard(card2);

        List<CreditCard> cards = cardService.listCards();
        assertEquals(2, cards.size());
        assertTrue(cards.contains(card1));
        assertTrue(cards.contains(card2));
    }

    // Test case for adding a null card object
    @Test
    void addCard_NullCard_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            cardService.addCard(null);
        });
    }

    // Test case for listing cards when no cards have been added
    @Test
    void listCards_NoCardsAdded_ReturnsEmptyList() {
        List<CreditCard> cards = cardService.listCards();
        assertNotNull(cards);
        assertTrue(cards.isEmpty());
        assertEquals(0, cards.size());
    }

    // Test case for listing cards after adding one card
    @Test
    void listCards_OneCardAdded_ReturnsListWithOneCard() {
        CreditCard card = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        cardService.addCard(card);

        List<CreditCard> cards = cardService.listCards();
        assertNotNull(cards);
        assertEquals(1, cards.size());
        assertEquals(card, cards.get(0));
    }

    // Test case for listing cards after adding multiple cards
    @Test
    void listCards_MultipleCardsAdded_ReturnsListWithAllCards() {
        CreditCard card1 = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        CreditCard card2 = new CreditCard("5400111122223333", "Jane Smith", "01/26", 456, "MasterCard", 2000.0);

        cardService.addCard(card1);
        cardService.addCard(card2);

        List<CreditCard> cards = cardService.listCards();
        assertNotNull(cards);
        assertEquals(2, cards.size());
        assertTrue(cards.contains(card1));
        assertTrue(cards.contains(card2));
    }

    // Test case for finding a card by its full unmasked number
    @Test
    void findCard_ByFullNumber_CardFound() {
        CreditCard card = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        cardService.addCard(card);

        CreditCard foundCard = cardService.findCard("4111222233334444");
        assertNotNull(foundCard);
        assertEquals(card, foundCard);
    }

    // Test case for finding a card by its masked number (standard 16-digit)
    @Test
    void findCard_ByMaskedNumber_StandardCard_CardFound() {
        CreditCard card = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        cardService.addCard(card);

        CreditCard foundCard = cardService.findCard("4111 **** **** 4444");
        assertNotNull(foundCard);
        assertEquals(card, foundCard);
    }

    // Test case for finding a card by its masked number for a shorter valid card (e.g., 10-digit)
    @Test
    void findCard_ByMaskedNumber_ShorterValidCard_CardFound() {
        // A 10-digit number that passes Luhn check
        CreditCard shortCard = new CreditCard("7992739871", "Short Card Holder", "10/24", 111, "Discover", 500.0);
        cardService.addCard(shortCard);

        // The expected masked format for a 10-digit card from getNumberMasked() is "first 4 **** **** last 4"
        CreditCard foundCard = cardService.findCard("7992 **** **** 9871");
        assertNotNull(foundCard);
        assertEquals(shortCard, foundCard);
    }

    // Test case for finding a card that does not exist
    @Test
    void findCard_NonExistentCard_ReturnsNull() {
        CreditCard card = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        cardService.addCard(card);

        CreditCard foundCard = cardService.findCard("9999888877776666"); // Non-existent
        assertNull(foundCard);
    }

    // Test case for finding a card when the service is empty
    @Test
    void findCard_EmptyService_ReturnsNull() {
        CreditCard foundCard = cardService.findCard("4111222233334444");
        assertNull(foundCard);
    }

    // Test case for finding a card using a null search string
    @Test
    void findCard_NullSearchString_ReturnsNull() {
        CreditCard card = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        cardService.addCard(card);

        CreditCard foundCard = cardService.findCard(null);
        assertNull(foundCard);
    }

    // Test case for finding a card with an empty search string
    @Test
    void findCard_EmptySearchString_ReturnsNull() {
        CreditCard card = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        cardService.addCard(card);

        CreditCard foundCard = cardService.findCard("");
        assertNull(foundCard);
    }

    // Test case for finding a specific card among multiple cards
    @Test
    void findCard_AmongMultipleCards_ReturnsCorrectCard() {
        CreditCard card1 = new CreditCard("4111222233334444", "John Doe", "12/25", 123, "Visa", 1000.0);
        CreditCard card2 = new CreditCard("5400111122223333", "Jane Smith", "01/26", 456, "MasterCard", 2000.0);
        // Amex typically 15 digits. Example: 371234567890123. Let's make one that passes Luhn.
        CreditCard card3 = new CreditCard("370011112222333", "Bob Apple", "03/27", 789, "Amex", 3000.0);

        cardService.addCard(card1);
        cardService.addCard(card2);
        cardService.addCard(card3);

        // Find by full number
        CreditCard foundCard = cardService.findCard("5400111122223333");
        assertNotNull(foundCard);
        assertEquals(card2, foundCard);

        // Find by masked number (Amex 15-digit: 3700 **** ****** 333)
        foundCard = cardService.findCard("3700 **** ****** 333");
        assertNotNull(foundCard);
        assertEquals(card3, foundCard);

        // Find another by masked number
        foundCard = cardService.findCard("4111 **** **** 4444");
        assertNotNull(foundCard);
        assertEquals(card1, foundCard);
    }
}