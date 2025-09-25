package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Count_V_CTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // No mocks needed for this specific class, but good practice if external dependencies were present.
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    // Test case for a normal string with mixed case vowels and consonants
    @Test
    @DisplayName("Should correctly count vowels and consonants in a mixed case string")
    void testMain_MixedCaseString() {
        String input = "Hello World";
        provideInput(input);
        Count_V_C.main(new String[]{});

        String expectedOutput1 = "Number of Vowel Characters     =  3"; // e, o, o
        String expectedOutput2 = "Number of Consonant Characters =  7"; // H, l, l, W, r, l, d

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(expectedOutput1), "Output should contain: " + expectedOutput1 + "\nActual output: " + actualOutput);
        assertTrue(actualOutput.contains(expectedOutput2), "Output should contain: " + expectedOutput2 + "\nActual output: " + actualOutput);
    }

    // Test case for a string containing only vowels
    @Test
    @DisplayName("Should correctly count vowels when input contains only vowels")
    void testMain_OnlyVowels() {
        String input = "AEIOUaeiou";
        provideInput(input);
        Count_V_C.main(new String[]{});

        String expectedOutput1 = "Number of Vowel Characters     =  10";
        String expectedOutput2 = "Number of Consonant Characters =  0";

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(expectedOutput1), "Output should contain: " + expectedOutput1 + "\nActual output: " + actualOutput);
        assertTrue(actualOutput.contains(expectedOutput2), "Output should contain: " + expectedOutput2 + "\nActual output: " + actualOutput);
    }

    // Test case for a string containing only consonants
    @Test
    @DisplayName("Should correctly count consonants when input contains only consonants")
    void testMain_OnlyConsonants() {
        String input = "BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz";
        provideInput(input);
        Count_V_C.main(new String[]{});

        String expectedOutput1 = "Number of Vowel Characters     =  0";
        String expectedOutput2 = "Number of Consonant Characters =  42";

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(expectedOutput1), "Output should contain: " + expectedOutput1 + "\nActual output: " + actualOutput);
        assertTrue(actualOutput.contains(expectedOutput2), "Output should contain: " + expectedOutput2 + "\nActual output: " + actualOutput);
    }

    // Test case for an empty string
    @Test
    @DisplayName("Should correctly handle an empty string input")
    void testMain_EmptyString() {
        String input = "";
        provideInput(input);
        Count_V_C.main(new String[]{});

        String expectedOutput1 = "Number of Vowel Characters     =  0";
        String expectedOutput2 = "Number of Consonant Characters =  0";

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(expectedOutput1), "Output should contain: " + expectedOutput1 + "\nActual output: " + actualOutput);
        assertTrue(actualOutput.contains(expectedOutput2), "Output should contain: " + expectedOutput2 + "\nActual output: " + actualOutput);
    }

    // Test case for a string with numbers and special characters
    @Test
    @DisplayName("Should ignore numbers and special characters")
    void testMain_NumbersAndSpecialCharacters() {
        String input = "123!@#$ Aeiou BcdFg";
        provideInput(input);
        Count_V_C.main(new String[]{});

        String expectedOutput1 = "Number of Vowel Characters     =  5"; // A, e, i, o, u
        String expectedOutput2 = "Number of Consonant Characters =  5"; // B, c, d, F, g

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(expectedOutput1), "Output should contain: " + expectedOutput1 + "\nActual output: " + actualOutput);
        assertTrue(actualOutput.contains(expectedOutput2), "Output should contain: " + expectedOutput2 + "\nActual output: " + actualOutput);
    }

    // Test case for a string with spaces only
    @Test
    @DisplayName("Should correctly handle a string with only spaces")
    void testMain_OnlySpaces() {
        String input = "     ";
        provideInput(input);
        Count_V_C.main(new String[]{});

        String expectedOutput1 = "Number of Vowel Characters     =  0";
        String expectedOutput2 = "Number of Consonant Characters =  0";

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(expectedOutput1), "Output should contain: " + expectedOutput1 + "\nActual output: " + actualOutput);
        assertTrue(actualOutput.contains(expectedOutput2), "Output should contain: " + expectedOutput2 + "\nActual output: " + actualOutput);
    }

    // Test case for a long string with various characters
    @Test
    @DisplayName("Should correctly count for a long and complex string")
    void testMain_LongComplexString() {
        String input = "This is a very long string with Many different chARacters and numb3rs!@#";
        provideInput(input);
        Count_V_C.main(new String[]{});

        // Count manually:
        // Vowels: i, i, a, e, y, o, i, A, e, e, a, a, i, e, e
        // (15 vowels: T(h)i(s) (i)s (a) (v)e(r)y (l)o(n)g (s)t(r)i(n)g (w)i(t)h (M)(a)(n)y (d)i(f)f(e)(r)(e)n(t) (c)h(A)(R)(a)(c)(t)(e)(r)s (a)(n)d (n)u(m)b3(r)s!@# )
        // T h i s   i s   a   v e r y   l o n g   s t r i n g   w i t h   M a n y   d i f f e r e n t   c h A R a c t e r s   a n d   n u m b 3 r s
        // Vowels: I, I, A, E, Y, O, I, A, E, E, A, A, E, U = 14
        // Re-count based on program logic:
        // T h I s I s A v E r y l O n g s t r I n g w I t h M A n y d I f f E r E n t c h A R A c t E r s A n d n U m b r s
        // (case-insensitive)
        // I, I, A, E, Y (ignore for counting), O, I, I, A, I, E, E, A, A, E, A, U
        // a, A: 6
        // e, E: 5
        // i, I: 4
        // o, O: 1
        // u, U: 1
        // Total Vowels: 6 + 5 + 4 + 1 + 1 = 17

        // Consonants:
        // T,h,s,s,v,r,y,l,n,g,s,t,r,n,g,w,t,h,M,n,y,d,f,f,r,n,t,c,h,R,c,t,r,s,n,d,n,m,b,r,s
        // (41 consonants)
        // Manual count:
        // T,h,s,s,v,r,y,l,n,g,s,t,r,n,g,w,t,h,M,n,y,d,f,f,r,n,t,c,h,R,c,t,r,s,n,d,n,m,b,r,s
        // Count: 41
        String expectedOutput1 = "Number of Vowel Characters     =  17";
        String expectedOutput2 = "Number of Consonant Characters =  41";

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(expectedOutput1), "Output should contain: " + expectedOutput1 + "\nActual output: " + actualOutput);
        assertTrue(actualOutput.contains(expectedOutput2), "Output should contain: " + expectedOutput2 + "\nActual output: " + actualOutput);
    }
}