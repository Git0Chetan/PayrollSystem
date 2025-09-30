package org.example;

import java.time.YearMonth;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CreditCardSystem {

    // ----------------- Models -----------------

    public static class CreditCard {
        private static int SEQ = 1;
        private final int id;
        private final String number;
        private final String holderName;
        private final YearMonth expiry; // e.g., 2029-12
        private final int cvv;
        private final String type; // e.g., Visa, MasterCard
        private final double creditLimit; // maximum allowed outstanding balance
        private double balanceUsed; // current outstanding amount

        public CreditCard(String number, String holderName, String expiryStr, int cvv, String type,
                          double creditLimit) {
            this.id = SEQ++;
            this.number = number != null ? number.replaceAll("\\s+", "") : "";
            this.holderName = holderName;
            this.expiry = parseExpiry(expiryStr);
            this.cvv = cvv;
            this.type = type;
            this.creditLimit = creditLimit;
            this.balanceUsed = 0.0;
        }

        public int getId() { return id; }
        public String getNumberMasked() {
            // Return a masked number for display: show first 4 and last 4
            if (number.length() < 8) return "****";
            String first = number.substring(0, 4);
            String last  = number.substring(number.length() - 4);
            return first + " **** **** " + last;
        }

        public String getNumber() { return number; }
        public String getHolderName() { return holderName; }
        public YearMonth getExpiry() { return expiry; }
        public int getCvv() { return cvv; }
        public String getType() { return type; }
        public double getCreditLimit() { return creditLimit; }
        public double getBalanceUsed() { return balanceUsed; }
        public double getAvailableCredit() { return creditLimit - balanceUsed; }

        // Luhn check
        public boolean isValidNumber() {
            return luhnCheck(number);
        }

        public boolean isExpired() {
            YearMonth now = YearMonth.now();
            return expiry.isBefore(now) || expiry.equals(now) == false && expiry.isBefore(now);
        }

        public boolean canCharge(double amount) {
            return amount > 0 && !isExpired() && (balanceUsed + amount) <= creditLimit;
        }

        // Charges the card; returns a Transaction
        public Transaction charge(double amount) {
            boolean success = false;
            String note = "";
            if (amount <= 0) {
                note = "Invalid amount";
            } else if (isExpired()) {
                note = "Card expired";
            } else if (balanceUsed + amount > creditLimit) {
                note = "Insufficient credit";
            } else {
                balanceUsed += amount;
                success = true;
                note = "Charge successful";
            }
            return new Transaction(id, number, amount, Transaction.Type.CHARGE, LocalDateTime.now(), success, note);
        }

        // Pays back (reduces balance)
        public Transaction payment(double amount) {
            boolean success = false;
            String note = "";
            if (amount <= 0) {
                note = "Invalid amount";
            } else if (amount > balanceUsed) {
                // overpayment not allowed in this simple model
                note = "Payment exceeds balance";
            } else {
                balanceUsed -= amount;
                success = true;
                note = "Payment successful";
            }
            return new Transaction(id, number, amount, Transaction.Type.PAYMENT, LocalDateTime.now(), success, note);
        }

        private static YearMonth parseExpiry(String expiryStr) {
            // Accept MM/YY or MM/YYYY
            if (expiryStr == null) return YearMonth.of(2100, 1);
            expiryStr = expiryStr.trim();
            String[] parts = expiryStr.split("/");
            int month = Integer.parseInt(parts[0]);
            int year;
            if (parts.length > 1) {
                String y = parts[1];
                if (y.length() == 2) {
                    year = Integer.parseInt(y) + 2000;
                } else {
                    year = Integer.parseInt(y);
                }
            } else {
                year = YearMonth.now().getYear();
            }
            int yyy = year;
            return YearMonth.of(yyy, month);
        }

        private static boolean luhnCheck(String number) {
            if (number == null || number.isEmpty()) return false;
            String s = number.replaceAll("\\s+", "");
            int sum = 0;
            boolean alternate = false;
            for (int i = s.length() - 1; i >= 0; i--) {
                char c = s.charAt(i);
                if (!Character.isDigit(c)) return false;
                int n = c - '0';
                if (alternate) {
                    n *= 2;
                    if (n > 9) n -= 9;
                }
                sum += n;
                alternate = !alternate;
            }
            return (sum % 10 == 0);
        }

        @Override
        public String toString() {
            return String.format("Card{id=%d, number=%s, holder=%s, expiry=%s, balanceUsed=%.2f, limit=%.2f}",
                    id, getNumberMasked(), holderName, expiry, balanceUsed, creditLimit);
        }
    }

    public static class Transaction {
        public enum Type { CHARGE, PAYMENT }

        private final int cardId;
        private final String cardNumber;
        private final double amount;
        private final Type type;
        private final LocalDateTime timestamp;
        public final boolean success;
        private final String note;

        public Transaction(int cardId, String cardNumber, double amount, Type type,
                           LocalDateTime timestamp, boolean success, String note) {
            this.cardId = cardId;
            this.cardNumber = cardNumber;
            this.amount = amount;
            this.type = type;
            this.timestamp = timestamp;
            this.success = success;
            this.note = note;
        }

        @Override
        public String toString() {
            String t = type.name();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return String.format("[%s] Card=%s, Type=%s, Amount=%.2f, Success=%s, Note=%s",
                    fmt.format(timestamp), cardNumber, t, amount, success, note);
        }
    }

    // --------------- Demo/Service --------------

    public static class CardService {
        private final List<CreditCard> cards = new ArrayList<>();

        public void addCard(CreditCard c) {
            if (c.isValidNumber()) {
                cards.add(c);
            } else {
                throw new IllegalArgumentException("Invalid card number (Luhn check failed).");
            }
        }

        public List<CreditCard> listCards() {
            return cards;
        }

        public CreditCard findCard(String numberOrMask) {
            for (CreditCard c : cards) {
                if (c.getNumber().equals(numberOrMask) || c.getNumberMasked().equals(numberOrMask)) {
                    return c;
                }
            }
            return null;
        }
    }

    // --------------- Main (CLI) ---------------

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        CardService service = new CardService();

        // Seed with two sample cards (one Visa-like, one MasterCard-like)
        CreditCard c1 = new CreditCard("4111111111111111", "Alice Smith", "12/29", 123, "Visa", 5000.00);
        CreditCard c2 = new CreditCard("5555555555554444", "Bob Johnson", "11/30", 456, "MasterCard", 3000.00);

        // Add cards (will validate with Luhn)
        service.addCard(c1);
        service.addCard(c2);

        // Simple CLI
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Simple In-Memory Credit Card System (Single File)");
        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1) List cards");
            System.out.println("2) Charge a card");
            System.out.println("3) Make a payment");
            System.out.println("4) Show card details");
            System.out.println("5) Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    for (CreditCard cc : service.listCards()) {
                        System.out.println(cc);
                    }
                    break;
                case "2": {
                    System.out.print("Enter card number (full): ");
                    String num = sc.nextLine().trim().replaceAll("\\s+", "");
                    CreditCard cc = null;
                    for (CreditCard c : service.listCards()) {
                        if (c.getNumber().equals(num)) { cc = c; break; }
                    }
                    if (cc == null) {
                        System.out.println("Card not found.");
                        break;
                    }
                    System.out.print("Amount to charge: ");
                    double amt = Double.parseDouble(sc.nextLine().trim());
                    Transaction t = cc.charge(amt);
                    System.out.println(t);
                    break;
                }
                case "3": {
                    System.out.print("Enter card number (full): ");
                    String num = sc.nextLine().trim().replaceAll("\\s+", "");
                    CreditCard cc = null;
                    for (CreditCard c : service.listCards()) {
                        if (c.getNumber().equals(num)) { cc = c; break; }
                    }
                    if (cc == null) {
                        System.out.println("Card not found.");
                        break;
                    }
                    System.out.print("Payment amount: ");
                    double amt = Double.parseDouble(sc.nextLine().trim());
                    Transaction t = cc.payment(amt);
                    System.out.println(t);
                    break;
                }
                case "4": {
                    System.out.print("Enter card number (full): ");
                    String num = sc.nextLine().trim().replaceAll("\\s+", "");
                    CreditCard cc = null;
                    for (CreditCard c : service.listCards()) {
                        if (c.getNumber().equals(num)) { cc = c; break; }
                    }
                    if (cc == null) {
                        System.out.println("Card not found.");
                        break;
                    }
                    System.out.println("Card details:");
                    System.out.println("  ID: " + cc.getId());
                    System.out.println("  Number: " + cc.getNumber());
                    System.out.println("  Holder: " + cc.getHolderName());
                    System.out.println("  Type: " + cc.getType());
                    System.out.println("  Expiry: " + cc.getExpiry());
                    System.out.println("  Credit Limit: " + cc.getCreditLimit());
                    System.out.println("  Balance Used: " + cc.getBalanceUsed());
                    System.out.println("  Available: " + cc.getAvailableCredit());
                    break;
                }
                case "5":
                    exit = true;
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }

        sc.close();
        System.out.println("Goodbye!");
    }
}