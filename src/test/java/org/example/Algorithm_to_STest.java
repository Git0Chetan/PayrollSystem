package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

// Mockito is not required for this test class as System.in and System.out
// are redirected directly, and the Scanner is instantiated within the main method itself,
// not as an injectable dependency.

public class Algorithm_to_STest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        // Capture System.out
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        // Restore original System.in and System.out
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    /**
     * Helper method to set simulated input for System.in.
     * @param input The string to feed as input.
     */
    private void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    /**
     * Helper method to construct the expected output string for the main method.
     * This method correctly reflects that prompts use `print` (no newline) and
     * the sorting output uses `println` (with newline).
     *
     * @param size The array size provided as input.
     * @param expectedSortingPrints An array of integers representing the specific values
     *                              printed by `System.out.println(arr[j]);` in each outer loop iteration.
     * @return The complete expected output string.
     */
    private String getExpectedOutput(int size, int[] expectedSortingPrints) {
        StringBuilder sb = new StringBuilder();
        sb.append("Enter the Array size = "); // System.out.print
        sb.append("Enter Array ").append(size).append(" elements  = "); // System.out.format (which uses print)
        sb.append("Sorting Integers using the Bubble Sort\n"); // System.out.println
        for (int val : expectedSortingPrints) {
            sb.append(val).append("\n"); // System.out.println
        }
        return sb.toString();
    }


    // Test case for a standard array of positive integers
    @Test
    @DisplayName("Tests main method with a standard array of positive integers")
    void testMainWithStandardPositiveIntegers() {
        // Input: Array size 3, elements 10, 20, 5
        String input = "3\n10\n20\n5\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {10, 20, 5}
        // i=0:
        //   j=0: arr[1](20) > arr[0](10) -> swap. arr = {20, 10, 5}
        //   j=1: arr[2](5) > arr[1](10) -> false.
        //   After inner loop, j is 1. Prints arr[1] = 10.
        //
        // i=1: Array is {20, 10, 5}
        //   j=0: arr[1](10) > arr[0](20) -> false.
        //   After inner loop, j is 0. Prints arr[0] = 20.
        //
        // i=2: Array is {20, 10, 5}
        //   Inner loop condition (j < 3-2-1) is (j < 0), so loop doesn't run.
        //   After inner loop, j is 0. Prints arr[0] = 20.
        int[] expectedSortingPrints = {10, 20, 20};
        String expectedOutput = getExpectedOutput(3, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an empty array (size 0)
    @Test
    @DisplayName("Tests main method with an empty array (size 0)")
    void testMainWithEmptyArray() {
        // Input: Array size 0
        String input = "0\n";
        setInput(input);

        // Expected output analysis:
        // Size = 0. Array is created with 0 length.
        // Loop for reading elements (for i=0; i<Size; i++) doesn't run.
        // Sorting loops (for i=0; i<Size; i++) don't run. No prints from the sorting part.
        int[] expectedSortingPrints = {}; // No elements printed during sort
        String expectedOutput = getExpectedOutput(0, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for a single element array (size 1)
    @Test
    @DisplayName("Tests main method with a single element array (size 1)")
    void testMainWithSingleElementArray() {
        // Input: Array size 1, element 42
        String input = "1\n42\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {42}
        // i=0:
        //   Inner loop condition (j < 1-0-1) is (j < 0), so loop doesn't run.
        //   After inner loop, j is 0. Prints arr[0] = 42.
        int[] expectedSortingPrints = {42};
        String expectedOutput = getExpectedOutput(1, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array that is already sorted in descending order
    @Test
    @DisplayName("Tests main method with an already sorted array (descending)")
    void testMainWithAlreadySortedDescendingArray() {
        // Input: Array size 3, elements 30, 20, 10
        String input = "3\n30\n20\n10\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {30, 20, 10}
        // i=0: No swaps occur. j ends as 1. Prints arr[1] = 20.
        // i=1: No swaps occur. j ends as 0. Prints arr[0] = 30.
        // i=2: No swaps occur. j ends as 0. Prints arr[0] = 30.
        int[] expectedSortingPrints = {20, 30, 30};
        String expectedOutput = getExpectedOutput(3, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array sorted in ascending order (reverse sorted for descending bubble sort)
    @Test
    @DisplayName("Tests main method with a reverse sorted array (ascending)")
    void testMainWithReverseSortedAscendingArray() {
        // Input: Array size 3, elements 10, 20, 30
        String input = "3\n10\n20\n30\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {10, 20, 30}
        // i=0:
        //   j=0: arr[1](20) > arr[0](10) -> swap. arr = {20, 10, 30}
        //   j=1: arr[2](30) > arr[1](10) -> swap. arr = {20, 30, 10}
        //   After inner loop, j is 1. Prints arr[1] = 30.
        //
        // i=1: Array is {20, 30, 10}
        //   j=0: arr[1](30) > arr[0](20) -> swap. arr = {30, 20, 10}
        //   After inner loop, j is 0. Prints arr[0] = 30.
        //
        // i=2: Array is {30, 20, 10}
        //   Inner loop condition (j < 3-2-1) is (j < 0), so loop doesn't run.
        //   After inner loop, j is 0. Prints arr[0] = 30.
        int[] expectedSortingPrints = {30, 30, 30};
        String expectedOutput = getExpectedOutput(3, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case with duplicate elements
    @Test
    @DisplayName("Tests main method with duplicate elements")
    void testMainWithDuplicateElements() {
        // Input: Array size 4, elements 5, 2, 5, 1
        String input = "4\n5\n2\n5\n1\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {5, 2, 5, 1}
        // i=0:
        //   j=0: arr[1](2) > arr[0](5) F. arr = {5, 2, 5, 1}
        //   j=1: arr[2](5) > arr[1](2) T. arr = {5, 5, 2, 1}
        //   j=2: arr[3](1) > arr[2](2) F. arr = {5, 5, 2, 1}
        //   After inner loop, j is 2. Prints arr[2] = 2.
        //
        // i=1: Array is {5, 5, 2, 1}
        //   j=0: arr[1](5) > arr[0](5) F. arr = {5, 5, 2, 1}
        //   j=1: arr[2](2) > arr[1](5) F. arr = {5, 5, 2, 1}
        //   After inner loop, j is 1. Prints arr[1] = 5.
        //
        // i=2: Array is {5, 5, 2, 1}
        //   j=0: arr[1](5) > arr[0](5) F. arr = {5, 5, 2, 1}
        //   After inner loop, j is 0. Prints arr[0] = 5.
        //
        // i=3: Array is {5, 5, 2, 1}
        //   Inner loop condition (j < 4-3-1) is (j < 0), so loop doesn't run.
        //   After inner loop, j is 0. Prints arr[0] = 5.
        int[] expectedSortingPrints = {2, 5, 5, 5};
        String expectedOutput = getExpectedOutput(4, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case with negative numbers
    @Test
    @DisplayName("Tests main method with negative numbers")
    void testMainWithNegativeNumbers() {
        // Input: Array size 3, elements -10, -5, -20
        String input = "3\n-10\n-5\n-20\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {-10, -5, -20}
        // i=0:
        //   j=0: arr[1](-5) > arr[0](-10) T. arr = {-5, -10, -20}
        //   j=1: arr[2](-20) > arr[1](-10) F. arr = {-5, -10, -20}
        //   After inner loop, j is 1. Prints arr[1] = -10.
        //
        // i=1: Array is {-5, -10, -20}
        //   j=0: arr[1](-10) > arr[0](-5) F. arr = {-5, -10, -20}
        //   After inner loop, j is 0. Prints arr[0] = -5.
        //
        // i=2: Array is {-5, -10, -20}
        //   Inner loop condition (j < 3-2-1) is (j < 0), so loop doesn't run.
        //   After inner loop, j is 0. Prints arr[0] = -5.
        int[] expectedSortingPrints = {-10, -5, -5};
        String expectedOutput = getExpectedOutput(3, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case with mixed positive, negative, and zero numbers
    @Test
    @DisplayName("Tests main method with mixed positive, negative, and zero numbers")
    void testMainWithMixedNumbers() {
        // Input: Array size 4, elements 0, -5, 10, 2
        String input = "4\n0\n-5\n10\n2\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {0, -5, 10, 2}
        // i=0:
        //   j=0: arr[1](-5) > arr[0](0) F. arr = {0, -5, 10, 2}
        //   j=1: arr[2](10) > arr[1](-5) T. arr = {0, 10, -5, 2}
        //   j=2: arr[3](2) > arr[2](-5) T. arr = {0, 10, 2, -5}
        //   After inner loop, j is 2. Prints arr[2] = 2.
        //
        // i=1: Array is {0, 10, 2, -5}
        //   j=0: arr[1](10) > arr[0](0) T. arr = {10, 0, 2, -5}
        //   j=1: arr[2](2) > arr[1](0) T. arr = {10, 2, 0, -5}
        //   After inner loop, j is 1. Prints arr[1] = 2.
        //
        // i=2: Array is {10, 2, 0, -5}
        //   j=0: arr[1](2) > arr[0](10) F. arr = {10, 2, 0, -5}
        //   After inner loop, j is 0. Prints arr[0] = 10.
        //
        // i=3: Array is {10, 2, 0, -5}
        //   Inner loop condition (j < 4-3-1) is (j < 0), so loop doesn't run.
        //   After inner loop, j is 0. Prints arr[0] = 10.
        int[] expectedSortingPrints = {2, 2, 10, 10};
        String expectedOutput = getExpectedOutput(4, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for a larger array (size 5, already sorted descending)
    @Test
    @DisplayName("Tests main method with a larger array")
    void testMainWithLargerArray() {
        // Input: Array size 5, elements 5, 4, 3, 2, 1
        String input = "5\n5\n4\n3\n2\n1\n";
        setInput(input);

        // Expected output analysis:
        // Initial array: {5, 4, 3, 2, 1} (already sorted descending)
        // i=0: No swaps. j ends as 3. Prints arr[3] = 2.
        // i=1: No swaps. j ends as 2. Prints arr[2] = 3.
        // i=2: No swaps. j ends as 1. Prints arr[1] = 4.
        // i=3: No swaps. j ends as 0. Prints arr[0] = 5.
        // i=4: No swaps. j ends as 0. Prints arr[0] = 5.
        int[] expectedSortingPrints = {2, 3, 4, 5, 5};
        String expectedOutput = getExpectedOutput(5, expectedSortingPrints);

        Algorithm_to_S.main(new String[]{});

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}