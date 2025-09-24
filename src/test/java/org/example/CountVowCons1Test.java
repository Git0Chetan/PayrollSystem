package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountVowCons1Test {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return outputStreamCaptor.toString();
    }

    @Test
    void testMain_MixedVowelsAndConsonants() {
        provideInput("Hello World");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  3"));
        assertTrue(output.contains("Number of Consonant Characters =  7"));
    }

    @Test
    void testMain_OnlyVowels() {
        provideInput("aeiouAEIOU");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  10"));
        assertTrue(output.contains("Number of Consonant Characters =  0"));
    }

    @Test
    void testMain_OnlyConsonants() {
        provideInput("bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  0"));
        assertTrue(output.contains("Number of Consonant Characters =  42"));
    }

    @Test
    void testMain_EmptyString() {
        provideInput("");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  0"));
        assertTrue(output.contains("Number of Consonant Characters =  0"));
    }

    @Test
    void testMain_StringWithSpacesAndSpecialChars() {
        provideInput("A1 B2 C!@#$%");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  1"));
        assertTrue(output.contains("Number of Consonant Characters =  2"));
    }

    @Test
    void testMain_StringWithNumbers() {
        provideInput("abc123DEF456");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  2"));
        assertTrue(output.contains("Number of Consonant Characters =  4"));
    }

    @Test
    void testMain_MixedCaseInput() {
        provideInput("Java iS fUn");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        // J, v, s, f, n -> 5 consonants
        // a, a, i, U -> 4 vowels
        assertTrue(output.contains("Number of Vowel Characters     =  4"));
        assertTrue(output.contains("Number of Consonant Characters =  5"));
    }

    @Test
    void testMain_AllNonAlphabetic() {
        provideInput("12345!@#$ %^&*()");
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("Number of Vowel Characters     =  0"));
        assertTrue(output.contains("Number of Consonant Characters =  0"));
    }

    @Test
    void testMain_LongComplexString() {
        String longInput = "The quick brown fox jumps over the lazy dog. A sentence that uses every letter of the alphabet.";
        provideInput(longInput);
        CountVowCons1.main(new String[]{});
        String output = getOutput();
        // Vowels: 28
        // Consonants: 49
        assertTrue(output.contains("Number of Vowel Characters     =  28"));
        assertTrue(output.contains("Number of Consonant Characters =  49"));
    }
}