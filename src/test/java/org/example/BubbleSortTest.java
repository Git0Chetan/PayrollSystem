package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BubbleSortTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    private void setInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    private String createExpectedOutput(int size, int[] inputArr, int[] sortedOutputs) {
        StringBuilder sb = new StringBuilder();
        sb.append("Enter the Array size = ");
        sb.append(size).append(System.lineSeparator());
        sb.append(String.format("Enter Array %d elements  = ", size));
        for (int i = 0; i < inputArr.length; i++) {
            sb.append(inputArr[i]).append(System.lineSeparator());
        }
        sb.append("Sorting Integers using the Bubble Sort").append(System.lineSeparator());
        for (int val : sortedOutputs) {
            sb.append(val).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Test
    void testMain_BasicSorting() {
        int size = 3;
        int[] inputArr = {1, 5, 2};
        int[] sortedOutputs = {1, 2, 5};

        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append(System.lineSeparator());
        for (int val : inputArr) {
            inputBuilder.append(val).append(System.lineSeparator());
        }
        setInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        String expectedOutput = createExpectedOutput(size, inputArr, sortedOutputs);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testMain_AlreadySortedDescending() {
        int size = 3;
        int[] inputArr = {5, 3, 1};
        int[] sortedOutputs = {1, 3, 5};

        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append(System.lineSeparator());
        for (int val : inputArr) {
            inputBuilder.append(val).append(System.lineSeparator());
        }
        setInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        String expectedOutput = createExpectedOutput(size, inputArr, sortedOutputs);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testMain_ReverseSortedAscendingInput() {
        int size = 3;
        int[] inputArr = {1, 2, 3};
        int[] sortedOutputs = {1, 2, 3};

        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append(System.lineSeparator());
        for (int val : inputArr) {
            inputBuilder.append(val).append(System.lineSeparator());
        }
        setInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        String expectedOutput = createExpectedOutput(size, inputArr, sortedOutputs);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testMain_EmptyArray() {
        int size = 0;
        int[] inputArr = {};
        int[] sortedOutputs = {};

        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append(System.lineSeparator());
        setInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        String expectedOutput = createExpectedOutput(size, inputArr, sortedOutputs);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testMain_SingleElementArray() {
        int size = 1;
        int[] inputArr = {10};
        int[] sortedOutputs = {10};

        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append(System.lineSeparator());
        for (int val : inputArr) {
            inputBuilder.append(val).append(System.lineSeparator());
        }
        setInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        String expectedOutput = createExpectedOutput(size, inputArr, sortedOutputs);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testMain_ArrayWithDuplicates() {
        int size = 4;
        int[] inputArr = {5, 2, 5, 1};
        int[] sortedOutputs = {1, 2, 5, 5};

        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append(System.lineSeparator());
        for (int val : inputArr) {
            inputBuilder.append(val).append(System.lineSeparator());
        }
        setInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        String expectedOutput = createExpectedOutput(size, inputArr, sortedOutputs);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testMain_NegativeNumbers() {
        int size = 4;
        int[] inputArr = {-5, -2, -8, -1};
        int[] sortedOutputs = {-8, -5, -2, -1};

        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(size).append(System.lineSeparator());
        for (int val : inputArr) {
            inputBuilder.append(val).append(System.lineSeparator());
        }
        setInput(inputBuilder.toString());

        BubbleSort.main(new String[]{});

        String expectedOutput = createExpectedOutput(size, inputArr, sortedOutputs);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testMain_NegativeArraySizeInput() {
        String input = "-5" + System.lineSeparator();
        setInput(input);

        assertThrows(NegativeArraySizeException.class, () -> BubbleSort.main(new String[]{}));
    }

    @Test
    void testMain_NonIntegerInputForSize() {
        String input = "abc" + System.lineSeparator();
        setInput(input);

        assertThrows(InputMismatchException.class, () -> BubbleSort.main(new String[]{}));
    }

    @Test
    void testMain_NonIntegerInputForElement() {
        String input = "2" + System.lineSeparator() + "10" + System.lineSeparator() + "xyz" + System.lineSeparator();
        setInput(input);

        assertThrows(InputMismatchException.class, () -> BubbleSort.main(new String[]{}));
    }
}