package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        return outputStreamCaptor.toString().trim();
    }

    @Test
    @DisplayName("Test main method with a normal unsorted array")
    void testMain_UnsortedArray() {
        String input = "3\n10\n20\n5\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 3 elements  = " +
                "Sorting Integers using the Bubble Sort\n" +
                "5\n" +
                "10\n" +
                "20";
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    @DisplayName("Test main method with an already sorted array (descending)")
    void testMain_AlreadySortedDescendingArray() {
        String input = "3\n20\n10\n5\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 3 elements  = " +
                "Sorting Integers using the Bubble Sort\n" +
                "5\n" +
                "10\n" +
                "20";
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    @DisplayName("Test main method with an already sorted array (ascending)")
    void testMain_AlreadySortedAscendingArray() {
        String input = "3\n5\n10\n20\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 3 elements  = " +
                "Sorting Integers using the Bubble Sort\n" +
                "5\n" +
                "10\n" +
                "20";
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    @DisplayName("Test main method with a single element array")
    void testMain_SingleElementArray() {
        String input = "1\n42\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 1 elements  = " +
                "Sorting Integers using the Bubble Sort\n" +
                "42";
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    @DisplayName("Test main method with an empty array (size 0)")
    void testMain_EmptyArray() {
        String input = "0\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 0 elements  = " +
                "Sorting Integers using the Bubble Sort";
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    @DisplayName("Test main method with an array containing duplicate elements")
    void testMain_DuplicateElementsArray() {
        String input = "4\n10\n5\n10\n20\n";
        provideInput(input);

        BubbleSort.main(new String[]{});

        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 4 elements  = " +
                "Sorting Integers using the Bubble Sort\n" +
                "5\n" +
                "10\n" +
                "10\n" +
                "20";
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    @DisplayName("Test main method with invalid non-integer input for array size")
    void testMain_InvalidSizeInput() {
        String input = "abc\n";
        provideInput(input);

        assertThrows(InputMismatchException.class, () -> {
            BubbleSort.main(new String[]{});
        });

        String actualOutput = getOutput();
        assertTrue(actualOutput.contains("Enter the Array size = "));
    }

    @Test
    @DisplayName("Test main method with invalid non-integer input for an array element")
    void testMain_InvalidElementInput() {
        String input = "3\n10\nabc\n5\n";
        provideInput(input);

        assertThrows(InputMismatchException.class, () -> {
            BubbleSort.main(new String[]{});
        });

        String actualOutput = getOutput();
        assertTrue(actualOutput.contains("Enter the Array size = "));
        assertTrue(actualOutput.contains("Enter Array 3 elements  = "));
    }

    @Test
    @DisplayName("Test main method with large array size and elements")
    void testMain_LargeArray() {
        int size = 10;
        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append("\n");
        // Provide elements in reverse order to ensure significant sorting
        for (int i = size; i >= 1; i--) {
            inputBuilder.append(i).append("\n");
        }
        provideInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        StringBuilder expectedOutputBuilder = new StringBuilder();
        expectedOutputBuilder.append("Enter the Array size = ");
        expectedOutputBuilder.append("Enter Array ").append(size).append(" elements  = ");
        expectedOutputBuilder.append("Sorting Integers using the Bubble Sort\n");
        // The output prints sorted elements in ascending order (5, 10, 10, 20)
        // for an array sorted descending (20, 10, 10, 5)
        // So for 10, 9, ..., 1 input, sorted array is 10, 9, ..., 1.
        // Output will be 1, 2, ..., 10.
        for (int i = 1; i <= size; i++) {
            expectedOutputBuilder.append(i).append("\n");
        }
        expectedOutputBuilder.setLength(expectedOutputBuilder.length() - 1); // Remove last newline

        assertEquals(expectedOutputBuilder.toString(), getOutput());
    }
}