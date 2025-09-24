package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class BubbleSortTest {

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

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    private String getOutput() {
        // Normalize line endings and trim to make assertions robust
        // The last printed element has a newline, which trim() would remove.
        // So expected strings should not have a trailing newline on the very last line.
        return outputStreamCaptor.toString().replaceAll("\\r\\n", "\n").trim();
    }

    @Test
    @DisplayName("Test main method with a standard array of integers")
    void testMainMethod_StandardInput() {
        // Input: Size 3, elements 1, 5, 2
        // Bubble sort (descending) -> [5, 2, 1]
        // Output sequence (smallest to largest): 1, 2, 5
        String input = "3\n1\n5\n2\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expected =
            "Enter the Array size = " +
            "Enter Array 3 elements  = " +
            "Sorting Integers using the Bubble Sort\n" +
            "1\n" +
            "2\n" +
            "5"; // No trailing newline, as getOutput() trims

        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Test main method with an empty array (Size = 0)")
    void testMainMethod_EmptyArray() {
        String input = "0\n"; // Size 0
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expected =
            "Enter the Array size = " +
            "Enter Array 0 elements  = " +
            "Sorting Integers using the Bubble Sort"; // No elements are printed for Size 0

        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Test main method with a single element array")
    void testMainMethod_SingleElementArray() {
        String input = "1\n42\n"; // Size 1, element 42
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expected =
            "Enter the Array size = " +
            "Enter Array 1 elements  = " +
            "Sorting Integers using the Bubble Sort\n" +
            "42"; // Only one element is printed

        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Test main method with an already sorted array (descending)")
    void testMainMethod_AlreadySortedDescending() {
        // Input: Size 4, elements 9, 7, 5, 3 (already descending)
        // Bubble sort (descending) -> [9, 7, 5, 3] (no swaps)
        // Output sequence: 3, 5, 7, 9
        String input = "4\n9\n7\n5\n3\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expected =
            "Enter the Array size = " +
            "Enter Array 4 elements  = " +
            "Sorting Integers using the Bubble Sort\n" +
            "3\n" +
            "5\n" +
            "7\n" +
            "9";

        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Test main method with a reverse sorted array (ascending to be sorted descending)")
    void testMainMethod_ReverseSortedAscending() {
        // Input: Size 4, elements 1, 2, 3, 4 (ascending)
        // Bubble sort (descending) -> [4, 3, 2, 1]
        // Output sequence: 1, 2, 3, 4
        String input = "4\n1\n2\n3\n4\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expected =
            "Enter the Array size = " +
            "Enter Array 4 elements  = " +
            "Sorting Integers using the Bubble Sort\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "4";

        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Test main method with duplicate elements")
    void testMainMethod_DuplicateElements() {
        // Input: Size 5, elements 3, 1, 4, 1, 2
        // Bubble sort (descending) -> [4, 3, 2, 1, 1]
        // Output sequence: 1, 1, 2, 3, 4
        String input = "5\n3\n1\n4\n1\n2\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expected =
            "Enter the Array size = " +
            "Enter Array 5 elements  = " +
            "Sorting Integers using the Bubble Sort\n" +
            "1\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "4";

        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Test main method with negative array size input should throw NegativeArraySizeException")
    void testMainMethod_NegativeArraySize() {
        String input = "-1\n"; // Negative Size
        provideInput(input);

        // The `main` method doesn't handle this exception, so it propagates.
        assertThrows(NegativeArraySizeException.class, () -> BubbleSort.main(new String[]{}));

        // Verify that the initial prompt was printed before the exception
        String actualOutputBeforeTrim = outputStreamCaptor.toString().replaceAll("\\r\\n", "\n");
        assertTrue(actualOutputBeforeTrim.contains("Enter the Array size = "));
        // Also ensure no other prompts appear as the program should terminate early
        assertFalse(actualOutputBeforeTrim.contains("Enter Array"));
    }
}