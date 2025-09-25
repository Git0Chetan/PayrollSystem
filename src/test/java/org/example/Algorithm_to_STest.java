package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Algorithm_to_STest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        // Capture System.out
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        // Restore original System.in and System.out
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Helper method to set simulated input for System.in.
     * Each test should set its own input.
     * @param input The string to feed into System.in
     */
    private void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    // Test case for a small array of positive integers, unsorted
    @Test
    @DisplayName("Test main with small unsorted array of positive integers")
    void testMain_SmallUnsortedPositiveIntegers() {
        String input = "3" + System.lineSeparator() +
                       "10" + System.lineSeparator() +
                       "20" + System.lineSeparator() +
                       "5" + System.lineSeparator();
        setInput(input);

        Algorithm_to_S.main(new String[]{});

        // The algorithm sorts in descending order and prints arr[Size - i - 1] after each pass.
        // For {10, 20, 5}, the final sorted array is {20, 10, 5}.
        // Output trace:
        // i=0: array becomes {20,10,5}, prints arr[2] which is 5
        // i=1: array remains {20,10,5}, prints arr[1] which is 10
        // i=2: array remains {20,10,5}, prints arr[0] which is 20
        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 3 elements  = " +
                "Sorting Integers using the Bubble Sort" + System.lineSeparator() +
                "5" + System.lineSeparator() +
                "10" + System.lineSeparator() +
                "20" + System.lineSeparator();

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array with elements already sorted in descending order
    @Test
    @DisplayName("Test main with array already sorted in descending order")
    void testMain_AlreadySortedDescending() {
        String input = "3" + System.lineSeparator() +
                       "30" + System.lineSeparator() +
                       "20" + System.lineSeparator() +
                       "10" + System.lineSeparator();
        setInput(input);

        Algorithm_to_S.main(new String[]{});

        // For {30, 20, 10}, the array is already sorted descending.
        // Output trace:
        // i=0: array remains {30,20,10}, prints arr[2] which is 10
        // i=1: array remains {30,20,10}, prints arr[1] which is 20
        // i=2: array remains {30,20,10}, prints arr[0] which is 30
        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 3 elements  = " +
                "Sorting Integers using the Bubble Sort" + System.lineSeparator() +
                "10" + System.lineSeparator() +
                "20" + System.lineSeparator() +
                "30" + System.lineSeparator();

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for a single element array
    @Test
    @DisplayName("Test main with a single element array")
    void testMain_SingleElementArray() {
        String input = "1" + System.lineSeparator() +
                       "42" + System.lineSeparator();
        setInput(input);

        Algorithm_to_S.main(new String[]{});

        // For {42}, the array is of size 1. The sorting loops will run but no swaps.
        // Outer loop runs for i=0. Inner loop (j < 1-0-1 = 0) does not run.
        // Prints arr[j] where j will be 0 after inner loop (as j is declared outside).
        // So prints arr[0] which is 42.
        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 1 elements  = " +
                "Sorting Integers using the Bubble Sort" + System.lineSeparator() +
                "42" + System.lineSeparator();

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for an array with duplicate elements
    @Test
    @DisplayName("Test main with array containing duplicate elements")
    void testMain_DuplicateElements() {
        String input = "4" + System.lineSeparator() +
                       "10" + System.lineSeparator() +
                       "5" + System.lineSeparator() +
                       "10" + System.lineSeparator() +
                       "20" + System.lineSeparator();
        setInput(input);

        Algorithm_to_S.main(new String[]{});

        // For {10, 5, 10, 20}, sorted descending is {20, 10, 10, 5}
        // Output trace:
        // i=0: arr becomes {10,10,20,5}, prints arr[3] which is 5
        // i=1: arr becomes {10,20,10,5}, prints arr[2] which is 10
        // i=2: arr becomes {20,10,10,5}, prints arr[1] which is 10
        // i=3: arr remains {20,10,10,5}, prints arr[0] which is 20
        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 4 elements  = " +
                "Sorting Integers using the Bubble Sort" + System.lineSeparator() +
                "5" + System.lineSeparator() +
                "10" + System.lineSeparator() +
                "10" + System.lineSeparator() +
                "20" + System.lineSeparator();

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
    
    // Test case for zero array size
    @Test
    @DisplayName("Test main with zero array size")
    void testMain_ZeroSizeArray() {
        String input = "0" + System.lineSeparator();
        setInput(input);

        Algorithm_to_S.main(new String[]{});

        // For Size=0, the loops for input and sorting won't run.
        // The output only contains the prompts.
        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 0 elements  = " +
                "Sorting Integers using the Bubble Sort" + System.lineSeparator();

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    // Test case for negative array size, expecting NegativeArraySizeException
    @Test
    @DisplayName("Test main with negative array size, expecting NegativeArraySizeException")
    void testMain_NegativeArraySize_ThrowsException() {
        String input = "-1" + System.lineSeparator();
        setInput(input);

        // NegativeArraySizeException is a RuntimeException and not checked.
        // It's thrown during array initialization: `new int[Size]`.
        Assertions.assertThrows(
                NegativeArraySizeException.class,
                () -> Algorithm_to_S.main(new String[]{})
        );

        // Verify that the prompt for array size was printed before the exception occurred
        String expectedPartialOutput = "Enter the Array size = ";
        Assertions.assertEquals(expectedPartialOutput, outputStreamCaptor.toString());
    }

    // Test case for non-integer input for array size, expecting InputMismatchException
    @Test
    @DisplayName("Test main with non-integer input for array size, expecting InputMismatchException")
    void testMain_NonIntegerSize_ThrowsException() {
        String input = "abc" + System.lineSeparator();
        setInput(input);

        // InputMismatchException is thrown by sc.nextInt() when the input does not match the expected type.
        Assertions.assertThrows(
                InputMismatchException.class,
                () -> Algorithm_to_S.main(new String[]{})
        );

        // Verify that the prompt for array size was printed before the exception occurred
        String expectedPartialOutput = "Enter the Array size = ";
        Assertions.assertEquals(expectedPartialOutput, outputStreamCaptor.toString());
    }

    // Test case for non-integer input for array element, expecting InputMismatchException
    @Test
    @DisplayName("Test main with non-integer input for array element, expecting InputMismatchException")
    void testMain_NonIntegerElement_ThrowsException() {
        String input = "2" + System.lineSeparator() +   // Valid size
                       "10" + System.lineSeparator() +  // Valid element
                       "xyz" + System.lineSeparator(); // Invalid element for the second element
        setInput(input);

        // InputMismatchException is thrown by sc.nextInt() when the input does not match the expected type.
        Assertions.assertThrows(
                InputMismatchException.class,
                () -> Algorithm_to_S.main(new String[]{})
        );

        // Verify that the prompts were printed up to the point of reading the elements
        String expectedPartialOutput =
                "Enter the Array size = " +
                "Enter Array 2 elements  = ";
        Assertions.assertEquals(expectedPartialOutput, outputStreamCaptor.toString());
    }
    
    // Test case for insufficient input for array elements, expecting NoSuchElementException
    @Test
    @DisplayName("Test main with insufficient input for array elements, expecting NoSuchElementException")
    void testMain_InsufficientInputElements_ThrowsException() {
        String input = "3" + System.lineSeparator() + // Size 3
                       "10" + System.lineSeparator() + // Element 1
                       "20" + System.lineSeparator();  // Element 2 (missing element 3)
        setInput(input);

        // NoSuchElementException is thrown by sc.nextInt() if no more tokens are available.
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> Algorithm_to_S.main(new String[]{})
        );

        // Verify that the prompts were printed up to the point of reading the elements
        String expectedPartialOutput =
                "Enter the Array size = " +
                "Enter Array 3 elements  = ";
        Assertions.assertEquals(expectedPartialOutput, outputStreamCaptor.toString());
    }

    // Test case for an array with negative integers
    @Test
    @DisplayName("Test main with array containing negative integers")
    void testMain_NegativeIntegers() {
        String input = "3" + System.lineSeparator() +
                       "-5" + System.lineSeparator() +
                       "-20" + System.lineSeparator() +
                       "-10" + System.lineSeparator();
        setInput(input);

        Algorithm_to_S.main(new String[]{});

        // For {-5, -20, -10}, sorted descending is {-5, -10, -20}
        // Output trace:
        // i=0: arr becomes {-5,-10,-20}, prints arr[2] which is -20
        // i=1: arr remains {-5,-10,-20}, prints arr[1] which is -10
        // i=2: arr remains {-5,-10,-20}, prints arr[0] which is -5
        String expectedOutput =
                "Enter the Array size = " +
                "Enter Array 3 elements  = " +
                "Sorting Integers using the Bubble Sort" + System.lineSeparator() +
                "-20" + System.lineSeparator() +
                "-10" + System.lineSeparator() +
                "-5" + System.lineSeparator();

        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}