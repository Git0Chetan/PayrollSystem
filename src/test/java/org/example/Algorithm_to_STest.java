package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Algorithm_to_STest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture console output
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Although no @Mock annotations are used directly for Scanner (due to its instantiation
        // within the main method and the strategy of redirecting System.in),
        // we include this to strictly adhere to the instruction for Mockito setup if required.
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        // Restore original System.in and System.out after each test
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    /**
     * Helper method to provide input to System.in for the Scanner.
     * Each element/size should be followed by a newline.
     * @param data The input string for System.in.
     */
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    /**
     * Helper method to construct the full expected output string, including prompts
     * and the numbers printed during the sorting process.
     * @param size The array size provided as input.
     * @param sortedPrints An array representing the numbers expected to be printed
     *                     one by one during the bubble sort iterations.
     * @return The complete expected console output string.
     */
    private String getFullExpectedOutput(int size, int[] sortedPrints) {
        StringBuilder sb = new StringBuilder();
        // The first prompt does not end with a newline
        sb.append("Enter the Array size = ");
        // The second prompt uses format and also does not end with a newline
        sb.append(String.format("Enter Array %d elements  = ", size));
        // The "Sorting" message ends with a newline
        sb.append("Sorting Integers using the Bubble Sort").append(System.lineSeparator());

        // Each number printed during sorting ends with a newline
        if (sortedPrints != null && sortedPrints.length > 0) {
            sb.append(IntStream.of(sortedPrints)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator())))
              .append(System.lineSeparator()); // Add a newline after the last printed number
        }
        return sb.toString();
    }


    // Test case for a typical array of positive integers, unsorted.
    @Test
    void testMain_PositiveUnsortedNumbers() {
        // Input: Size 3, elements 1, 5, 2
        // Bubble sort (descending): [1, 5, 2] -> [5, 1, 2] -> [5, 2, 1]
        // Prints arr[Size - i - 1]:
        // i=0: arr[2] (value after sort of first pass) = 1
        // i=1: arr[1] (value after sort of second pass) = 2
        // i=2: arr[0] (value after sort of third pass) = 5
        String input = "3\n1\n5\n2\n";
        provideInput(input);

        int[] expectedPrintedNumbers = {1, 2, 5};
        String expectedOutput = getFullExpectedOutput(3, expectedPrintedNumbers);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for a single element array.
    @Test
    void testMain_SingleElementArray() {
        // Input: Size 1, element 42
        // Bubble sort loops won't perform any swaps.
        // Prints arr[Size - i - 1]:
        // i=0: arr[0] = 42
        String input = "1\n42\n";
        provideInput(input);

        int[] expectedPrintedNumbers = {42};
        String expectedOutput = getFullExpectedOutput(1, expectedPrintedNumbers);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array that is already sorted in descending order.
    @Test
    void testMain_AlreadySortedDescending() {
        // Input: Size 3, elements 5, 2, 1 (already descending)
        // Bubble sort will perform no swaps. Array remains [5, 2, 1].
        // Prints arr[Size - i - 1]:
        // i=0: arr[2] = 1
        // i=1: arr[1] = 2
        // i=2: arr[0] = 5
        String input = "3\n5\n2\n1\n";
        provideInput(input);

        int[] expectedPrintedNumbers = {1, 2, 5};
        String expectedOutput = getFullExpectedOutput(3, expectedPrintedNumbers);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array that is already sorted in ascending order.
    @Test
    void testMain_AlreadySortedAscending() {
        // Input: Size 3, elements 1, 2, 5 (ascending)
        // Bubble sort (descending): [1, 2, 5] -> [2, 1, 5] -> [2, 5, 1] -> [5, 2, 1]
        // Prints arr[Size - i - 1]:
        // i=0: arr[2] (value after sort of first pass) = 1
        // i=1: arr[1] (value after sort of second pass) = 2
        // i=2: arr[0] (value after sort of third pass) = 5
        String input = "3\n1\n2\n5\n";
        provideInput(input);

        int[] expectedPrintedNumbers = {1, 2, 5};
        String expectedOutput = getFullExpectedOutput(3, expectedPrintedNumbers);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array with duplicate elements.
    @Test
    void testMain_ArrayWithDuplicates() {
        // Input: Size 4, elements 3, 1, 3, 2
        // Bubble sort (descending): [3, 1, 3, 2] -> ... -> [3, 3, 2, 1]
        // Prints arr[Size - i - 1]:
        // i=0: arr[3] = 1
        // i=1: arr[2] = 2
        // i=2: arr[1] = 3
        // i=3: arr[0] = 3
        String input = "4\n3\n1\n3\n2\n";
        provideInput(input);

        int[] expectedPrintedNumbers = {1, 2, 3, 3};
        String expectedOutput = getFullExpectedOutput(4, expectedPrintedNumbers);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array containing negative numbers and zero.
    @Test
    void testMain_NegativeNumbersAndZero() {
        // Input: Size 4, elements -5, 0, -1, 2
        // Bubble sort (descending): [-5, 0, -1, 2] -> ... -> [2, 0, -1, -5]
        // Prints arr[Size - i - 1]:
        // i=0: arr[3] = -5
        // i=1: arr[2] = -1
        // i=2: arr[1] = 0
        // i=3: arr[0] = 2
        String input = "4\n-5\n0\n-1\n2\n";
        provideInput(input);

        int[] expectedPrintedNumbers = {-5, -1, 0, 2};
        String expectedOutput = getFullExpectedOutput(4, expectedPrintedNumbers);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an empty array (size 0).
    @Test
    void testMain_EmptyArray() {
        // Input: Size 0
        // The array is created as new int[0].
        // Both input reading loop and sorting loops will not execute as Size is 0.
        // Only prompts and the "Sorting..." message will be printed.
        String input = "0\n";
        provideInput(input);

        int[] expectedPrintedNumbers = {}; // No numbers are printed during sort for size 0.
        String expectedOutput = getFullExpectedOutput(0, expectedPrintedNumbers);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}