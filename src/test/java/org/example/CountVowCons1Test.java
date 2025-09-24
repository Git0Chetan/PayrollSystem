package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class CountVowCons1Test {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInputAndRunMain(String input, int expectedVowels, int expectedConsonants) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(inContent);

        CountVowCons1.main(new String[]{});

        String actualOutput = outContent.toString(StandardCharsets.UTF_8);

        assertTrue(actualOutput.contains("Number of Vowel Characters     =  " + expectedVowels),
                "Expected vowel count " + expectedVowels + " not found in output: " + actualOutput);
        assertTrue(actualOutput.contains("Number of Consonant Characters =  " + expectedConsonants),
                "Expected consonant count " + expectedConsonants + " not found in output: " + actualOutput);
    }

    @Test
    @DisplayName("Test main method with mixed string (lowercase)")
    void testMainWithMixedLowercaseString() {
        String input = "hello world";
        // e, o, o = 3 vowels
        // h, l, l, w, r, l, d = 7 consonants
        provideInputAndRunMain(input, 3, 7);
    }

    @Test
    @DisplayName("Test main method with mixed string (uppercase)")
    void testMainWithMixedUppercaseString() {
        String input = "HELLO WORLD";
        // E, O, O = 3 vowels
        // H, L, L, W, R, L, D = 7 consonants
        provideInputAndRunMain(input, 3, 7);
    }

    @Test
    @DisplayName("Test main method with mixed string (mixed case)")
    void testMainWithMixedCaseString() {
        String input = "JaVa PrOgRaM";
        // a, a, O, a = 4 vowels
        // J, v, P, r, g, r, m = 7 consonants
        provideInputAndRunMain(input, 4, 7);
    }

    @Test
    @DisplayName("Test main method with only vowels")
    void testMainWithOnlyVowels() {
        String input = "aeiouAEIOU";
        provideInputAndRunMain(input, 10, 0);
    }

    @Test
    @DisplayName("Test main method with only consonants")
    void testMainWithOnlyConsonants() {
        String input = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
        provideInputAndRunMain(input, 0, 42);
    }

    @Test
    @DisplayName("Test main method with empty string")
    void testMainWithEmptyString() {
        String input = "";
        provideInputAndRunMain(input, 0, 0);
    }

    @Test
    @DisplayName("Test main method with spaces only")
    void testMainWithSpacesOnly() {
        String input = "   ";
        provideInputAndRunMain(input, 0, 0);
    }

    @Test
    @DisplayName("Test main method with special characters and numbers")
    void testMainWithSpecialCharactersAndNumbers() {
        String input = "!@#$%^&*()12345";
        provideInputAndRunMain(input, 0, 0);
    }

    @Test
    @DisplayName("Test main method with mixed characters including special chars, spaces, and numbers")
    void testMainWithMixedChars() {
        String input = "Test 123 String!@#";
        // E, i = 2 vowels
        // T, s, t, S, t, r, n, g = 8 consonants
        provideInputAndRunMain(input, 2, 8);
    }

    @Test
    @DisplayName("Test main method with a long string")
    void testMainWithLongString() {
        String input = "The quick brown fox jumps over the lazy dog. A quick brown fox. This is a very long sentence to test the functionality.";
        // Vowels:
        // T_e_ (_u_i_c_k b_ro_w_n f_o_x j_u_mp_s _o_v_e_r th_e_ l_a_z_y d_o_g. _A_ _qu_i_c_k b_ro_w_n f_o_x. Th_i_s _i_s _a_ v_e_r_y l_o_n_g s_e_n_t_e_nc_e_ t_o_ t_e_s_t th_e_ f_u_nct_io_n_a_l_i_t_y.
        // e,u,i,o,o,u,o,e,e,a,o,u,i,o,o,i,i,a,e,o,e,e,e,o,e,u,io,a,i = 29
        // Consonants:
        // Th_ _qu_c_k br_w_n f_x j_mp_s _v_r th_ l_z_y d_g. _qu_c_k br_w_n f_x. Th_s _s _ v_ry l_ng s_n_n_c_ t_ t_s_t th_ f_n_ct_n_l_ty.
        // 75
        provideInputAndRunMain(input, 29, 75);
    }
}