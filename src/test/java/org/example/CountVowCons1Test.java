package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class CountVowCons1Test {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    private void runMainAndAssert(String input, int expectedVowels, int expectedConsonants) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        CountVowCons1.main(new String[]{});

        String actualOutput = outputStreamCaptor.toString().trim();
        String[] lines = actualOutput.split("\\r?\\n");

        String vowelLine = null;
        String consonantLine = null;

        for (String line : lines) {
            if (line.startsWith("Number of Vowel Characters")) {
                vowelLine = line;
            } else if (line.startsWith("Number of Consonant Characters")) {
                consonantLine = line;
            }
        }

        assertNotNull(vowelLine, "Vowel count line not found in output for input: \"" + input + "\"");
        assertNotNull(consonantLine, "Consonant count line not found in output for input: \"" + input + "\"");

        int actualVowels = Integer.parseInt(vowelLine.substring(vowelLine.lastIndexOf('=') + 1).trim());
        int actualConsonants = Integer.parseInt(consonantLine.substring(consonantLine.lastIndexOf('=') + 1).trim());

        assertEquals(expectedVowels, actualVowels, "Incorrect vowel count for input: \"" + input + "\"");
        assertEquals(expectedConsonants, actualConsonants, "Incorrect consonant count for input: \"" + input + "\"");
    }

    @Test
    void testMain_emptyString() {
        runMainAndAssert("", 0, 0);
    }

    @Test
    void testMain_onlyVowelsLowerCase() {
        runMainAndAssert("aeiou", 5, 0);
    }

    @Test
    void testMain_onlyVowelsUpperCase() {
        runMainAndAssert("AEIOU", 5, 0);
    }

    @Test
    void testMain_mixedCaseVowels() {
        runMainAndAssert("AaEeIiOoUu", 10, 0);
    }

    @Test
    void testMain_onlyConsonantsLowerCase() {
        runMainAndAssert("bcdfgh", 0, 6);
    }

    @Test
    void testMain_onlyConsonantsUpperCase() {
        runMainAndAssert("BCDFGH", 0, 6);
    }

    @Test
    void testMain_mixedCaseConsonants() {
        runMainAndAssert("BbCcDdFfGgHh", 0, 12);
    }

    @Test
    void testMain_mixedVowelsAndConsonants() {
        runMainAndAssert("Hello World", 3, 7); // H,l,l,W,r,l,d are consonants; e,o,o are vowels. Space ignored.
    }

    @Test
    void testMain_stringWithSpaces() {
        runMainAndAssert("Java Programming", 5, 10); // J,v,P,r,g,r,m,m,n,g are consonants; a,a,o,a,i are vowels. Space ignored.
    }

    @Test
    void testMain_withNumbersAndSymbols() {
        runMainAndAssert("123!@#$abcDEf", 3, 3); // a,e are vowels. b,c,D,f are consonants. 1,2,3,!@#$ are ignored.
    }

    @Test
    void testMain_longString() {
        String longInput = "The quick brown fox jumps over the lazy dog";
        // Vowels: e,u,i,o,o,u,o,e,e,a,o (11)
        // Consonants: T,h,q,c,k,b,r,w,n,f,x,j,m,p,s,v,r,t,h,l,z,y,d,g (24)
        runMainAndAssert(longInput, 11, 24);
    }

    @Test
    void testMain_allSpaces() {
        runMainAndAssert("   ", 0, 0);
    }

    @Test
    void testMain_specialCharactersOnly() {
        runMainAndAssert("!@#$%^&*()", 0, 0);
    }

    @Test
    void testMain_numbersOnly() {
        runMainAndAssert("1234567890", 0, 0);
    }
}