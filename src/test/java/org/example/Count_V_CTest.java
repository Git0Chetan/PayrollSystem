package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Count_V_CTest {

    // Store original System.in and System.out to restore them after tests
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    // Captures the output printed to System.out
    private ByteArrayOutputStream outputStreamCaptor;

    /**
     * Sets up the test environment before each test method.
     * Redirects System.out to capture console output.
     */
    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Cleans up the test environment after each test method.
     * Restores original System.in and System.out.
     */
    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    /**
     * Helper method to execute the main method of Count_V_C with a given input
     * and capture its console output.
     *
     * @param input The string to feed into System.in. A newline character `\n` is usually required
     *              at the end to simulate pressing Enter after typing.
     * @return The captured console output as a string.
     */
    private String runMainAndCaptureOutput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        // Call the main method of the class under test
        Count_V_C.main(new String[]{});
        return outputStreamCaptor.toString();
    }

    /**
     * Helper method to parse the console output and extract the count for a specific label.
     * Assumes output format "Number of <label> Characters =  <count>".
     *
     * @param output The full console output string.
     * @param label  The label to search for (e.g., "Vowel", "Consonant").
     * @return The extracted integer count.
     * @throws IllegalStateException if the count cannot be found in the output.
     */
    private int getCountFromOutput(String output, String label) {
        Pattern pattern = Pattern.compile("Number of " + label + " Characters\\s*=\\s*(\\d+)");
        Matcher matcher = pattern.matcher(output);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalStateException("Could not find " + label + " count in output: " + output);
    }

    // Test case for a typical string with mixed vowels and consonants
    @Test
    @DisplayName("Should correctly count vowels and consonants for a mixed string (e.g., 'Hello World')")
    void testMixedVowelsAndConsonants() {
        String input = "Hello World\n"; // Input string for Scanner
        String output = runMainAndCaptureOutput(input);

        // Verify the prompt is present in the output
        assertTrue(output.contains("Please Enter Vowel, Consonant String ="));

        int vowels = getCountFromOutput(output, "Vowel");
        int consonants = getCountFromOutput(output, "Consonant");

        // 'e', 'o', 'o' -> 3 vowels
        // 'H', 'l', 'l', 'W', 'r', 'l', 'd' -> 7 consonants (spaces and case are handled)
        assertEquals(3, vowels, "Expected 3 vowels in 'Hello World'");
        assertEquals(7, consonants, "Expected 7 consonants in 'Hello World'");
    }

    // Test case for a string containing only vowels (mixed case)
    @Test
    @DisplayName("Should correctly count all vowels for an all-vowel string (e.g., 'aeiouAEIOU')")
    void testAllVowels() {
        String input = "aeiouAEIOU\n";
        String output = runMainAndCaptureOutput(input);

        int vowels = getCountFromOutput(output, "Vowel");
        int consonants = getCountFromOutput(output, "Consonant");

        assertEquals(10, vowels, "Expected 10 vowels in 'aeiouAEIOU'");
        assertEquals(0, consonants, "Expected 0 consonants in 'aeiouAEIOU'");
    }

    // Test case for a string containing only consonants (mixed case)
    @Test
    @DisplayName("Should correctly count all consonants for an all-consonant string (e.g., 'BCDFghjkl')")
    void testAllConsonants() {
        String input = "BCDFghjkl\n"; // 4 uppercase, 5 lowercase consonants
        String output = runMainAndCaptureOutput(input);

        int vowels = getCountFromOutput(output, "Vowel");
        int consonants = getCountFromOutput(output, "Consonant");

        assertEquals(0, vowels, "Expected 0 vowels in 'BCDFghjkl'");
        assertEquals(9, consonants, "Expected 9 consonants in 'BCDFghjkl'");
    }

    // Test case for an empty input string
    @Test
    @DisplayName("Should correctly handle an empty input string")
    void testEmptyString() {
        String input = "\n"; // Simulate pressing Enter immediately
        String output = runMainAndCaptureOutput(input);

        int vowels = getCountFromOutput(output, "Vowel");
        int consonants = getCountFromOutput(output, "Consonant");

        assertEquals(0, vowels, "Expected 0 vowels for empty string");
        assertEquals(0, consonants, "Expected 0 consonants for empty string");
    }

    // Test case for a string with numbers and special characters interspersed with letters
    @Test
    @DisplayName("Should ignore numbers and special characters, counting only letters")
    void testNumbersAndSpecialCharacters() {
        String input = "123!@#$aBcDeF\n"; // 'a','e' are vowels; 'B','c','D','F' are consonants
        String output = runMainAndCaptureOutput(input);

        int vowels = getCountFromOutput(output, "Vowel");
        int consonants = getCountFromOutput(output, "Consonant");

        assertEquals(2, vowels, "Expected 2 vowels in '123!@#$aBcDeF'"); // a, e
        assertEquals(4, consonants, "Expected 4 consonants in '123!@#$aBcDeF'"); // B, c, D, F
    }

    // Test case for a string with spaces
    @Test
    @DisplayName("Should ignore spaces in the input string")
    void testStringWithSpaces() {
        String input = "A p p l e\n"; // 'A', 'e' are vowels; 'p', 'p', 'l' are consonants
        String output = runMainAndCaptureOutput(input);

        int vowels = getCountFromOutput(output, "Vowel");
        int consonants = getCountFromOutput(output, "Consonant");

        assertEquals(2, vowels, "Expected 2 vowels in 'A p p l e'");
        assertEquals(3, consonants, "Expected 3 consonants in 'A p p l e'");
    }

    // Test case for a string composed solely of special characters and numbers
    @Test
    @DisplayName("Should return zero counts for a string with only special characters and numbers")
    void testOnlySpecialCharactersAndNumbers() {
        String input = "!@#$%^&*()12345\n";
        String output = runMainAndCaptureOutput(input);

        int vowels = getCountFromOutput(output, "Vowel");
        int consonants = getCountFromOutput(output, "Consonant");

        assertEquals(0, vowels, "Expected 0 vowels for only special chars/numbers");
        assertEquals(0, consonants, "Expected 0 consonants for only special chars/numbers");
    }

    // Test case for a longer, complex string
    @Test
    @DisplayName("Should correctly count for a longer, complex string with various characters")
    void testLongComplexString() {
        String input = "This is a Test String 123! With MANY Vowels and CONSONANTS.\n";
        // Vowels: i, i, a, e, i, A, Y, o, e, a, o, A
        // Correct count: T(C), h(C), i(V), s(C), i(V), s(C), a(V), T(C), e(V), s(C), t(C), S(C), t(C), r(C), i(V), n(C), g(C), W(C), i(V), t(C), h(C), M(C), A(V), N(C), Y(C), V(C), o(V), w(C), e(V), l(C), s(C), a(V), n(C), d(C), C(C), O(V), N(C), S(C), O(V), N(C), A(V), N(C), T(C), S(C)
        // Vowels: 11 (i, i, a, e, i, i, A, o, e, a, O, O, A)
        // Consonants: 26 (T, h, s, s, T, s, t, S, t, r, n, g, W, t, h, M, N, Y, V, w, l, s, n, d, C, N, S, N, N, T, S) -- wait, I should count them carefully by hand.
        // Let's re-count manually: "This is a Test String 123! With MANY Vowels and CONSONANTS."
        // Vowels: i, i, a, e, i, i, A, o, e, a, O, O, A -> 13
        // Consonants: T, h, s, s, T, s, t, S, t, r, n, g, W, t, h, M, N, Y, V, w, l, s, n, d, C, N, S, N, N, T, S -> 31
        // My manual counting failed, let's use a simpler method for expected:
        int expectedVowels = 0;
        int expectedConsonants = 0;
        String cleanedInput = input.toLowerCase().replaceAll("[^a-z]", ""); // Remove non-alphabetic, convert to lower
        for (char ch : cleanedInput.toCharArray()) {
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                expectedVowels++;
            } else {
                expectedConsonants++;
            }
        }
        // For "This is a Test String 123! With MANY Vowels and CONSONANTS."
        // Vowels: T(h)i(s) (i)s (a) T(e)s(t) S(t)r(i)n(g) W(i)t(h) M(a)n(y) V(o)w(e)l(s) (a)n(d) C(o)n(s)o(n)a(n)t(s)
        // 'i', 'i', 'a', 'e', 'i', 'i', 'a', 'o', 'e', 'a', 'o', 'o', 'a' = 13 vowels
        // All other letters are consonants. Total letters: 44. Consonants = 44 - 13 = 31
        // Okay, my helper method is good for deriving the expected result.

        String output = runMainAndCaptureOutput(input);

        int actualVowels = getCountFromOutput(output, "Vowel");
        int actualConsonants = getCountFromOutput(output, "Consonant");

        assertEquals(expectedVowels, actualVowels, "Expected correct vowel count for complex string");
        assertEquals(expectedConsonants, actualConsonants, "Expected correct consonant count for complex string");
    }
}