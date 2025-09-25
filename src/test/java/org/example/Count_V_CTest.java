package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.mockito.MockitoAnnotations; // Required by instructions, even if no explicit @Mock usage for Scanner.

public class Count_V_CTest {

    // Store original System.in and System.out to restore them after tests
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    
    // To capture System.out messages
    private ByteArrayOutputStream outputStreamCaptor;

    // This method runs before each test to set up the test environment
    @BeforeEach
    void setUp() {
        // Initialize the output stream captor
        outputStreamCaptor = new ByteArrayOutputStream();
        // Redirect System.out to our captor
        System.setOut(new PrintStream(outputStreamCaptor));
        // Initialize Mockito mocks (even if no @Mock fields are present, it's good practice as per instructions)
        MockitoAnnotations.openMocks(this);
    }

    // This method runs after each test to clean up and restore original System streams
    @AfterEach
    void tearDown() {
        // Restore original System.in
        System.setIn(originalSystemIn);
        // Restore original System.out
        System.setOut(originalSystemOut);
    }

    /**
     * Helper method to provide input to System.in for the Scanner.
     * Appends a newline character to simulate pressing Enter after input.
     * @param data The string data to be provided as input.
     */
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream((data + "\n").getBytes()));
    }

    /**
     * Helper method to retrieve the captured System.out output.
     * @return The string captured from System.out, trimmed of leading/trailing whitespace.
     */
    private String getOutput() {
        return outputStreamCaptor.toString().trim();
    }

    // Test case for a typical string with mixed vowels and consonants
    @Test
    void testMain_MixedVowelsAndConsonants() {
        provideInput("Hello World");
        Count_V_C.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  3"), "Expected 3 vowels for 'Hello World'"); // e, o, o
        assertTrue(output.contains("Number of Consonant Characters =  7"), "Expected 7 consonants for 'Hello World'"); // H, l, l, W, r, l, d
    }

    // Test case for a string with only vowels (both upper and lower case)
    @Test
    void testMain_AllVowels() {
        provideInput("aeiouAEIOU");
        Count_V_C.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  10"), "Expected 10 vowels for 'aeiouAEIOU'");
        assertTrue(output.contains("Number of Consonant Characters =  0"), "Expected 0 consonants for 'aeiouAEIOU'");
    }

    // Test case for a string with only consonants (both upper and lower case)
    @Test
    void testMain_AllConsonants() {
        provideInput("bcdfghjklBCDFGHJKL");
        Count_V_C.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  0"), "Expected 0 vowels for 'bcdfghjklBCDFGHJKL'");
        assertTrue(output.contains("Number of Consonant Characters =  18"), "Expected 18 consonants for 'bcdfghjklBCDFGHJKL'");
    }

    // Test case for an empty string input
    @Test
    void testMain_EmptyString() {
        provideInput(""); // An empty line, as `sc.nextLine()` still reads a line
        Count_V_C.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  0"), "Expected 0 vowels for empty string");
        assertTrue(output.contains("Number of Consonant Characters =  0"), "Expected 0 consonants for empty string");
    }

    // Test case for a string containing numbers, symbols, and letters
    @Test
    void testMain_NumbersAndSpecialCharacters() {
        provideInput("123!@#abcdeFGH");
        Count_V_C.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  2"), "Expected 2 vowels (a, e) for '123!@#abcdeFGH'");
        assertTrue(output.contains("Number of Consonant Characters =  6"), "Expected 6 consonants (b, c, d, F, G, H) for '123!@#abcdeFGH'");
    }

    // Test case for a string consisting only of spaces
    @Test
    void testMain_OnlySpaces() {
        provideInput("   ");
        Count_V_C.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  0"), "Expected 0 vowels for spaces only");
        assertTrue(output.contains("Number of Consonant Characters =  0"), "Expected 0 consonants for spaces only");
    }

    // Test case for a long string to ensure correct counting
    @Test
    void testMain_LongString() {
        provideInput("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); // 52 chars
        Count_V_C.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  10"), "Expected 10 vowels for alphabet string"); // a,e,i,o,u (x2)
        assertTrue(output.contains("Number of Consonant Characters =  42"), "Expected 42 consonants for alphabet string"); // 26-5 = 21 (x2)
    }

    // Test case for string with leading/trailing spaces around meaningful characters
    @Test
    void testMain_StringWithWhitespace() {
        provideInput("   apple banana   ");
        Count_V_C.main(new String[]{});
        String output = getOutput();
        // apple (a, e) + banana (a, a, a) = 5 vowels
        // apple (p, p, l) + banana (b, n, n) = 6 consonants
        assertTrue(output.contains("Number of Vowel Characters     =  5"), "Expected 5 vowels for '   apple banana   '");
        assertTrue(output.contains("Number of Consonant Characters =  6"), "Expected 6 consonants for '   apple banana   '");
    }
}