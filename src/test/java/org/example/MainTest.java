package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testMainMethodOutputHappyPathEmptyArgs() {
        String lineSeparator = System.lineSeparator();
        StringBuilder expectedOutputBuilder = new StringBuilder();
        expectedOutputBuilder.append("Hello and welcome!").append(lineSeparator);
        for (int i = 1; i <= 5; i++) {
            expectedOutputBuilder.append("i = ").append(i).append(lineSeparator);
        }
        String expectedOutput = expectedOutputBuilder.toString();

        Main.main(new String[]{});

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMainMethodOutputHappyPathPopulatedArgs() {
        String lineSeparator = System.lineSeparator();
        StringBuilder expectedOutputBuilder = new StringBuilder();
        expectedOutputBuilder.append("Hello and welcome!").append(lineSeparator);
        for (int i = 1; i <= 5; i++) {
            expectedOutputBuilder.append("i = ").append(i).append(lineSeparator);
        }
        String expectedOutput = expectedOutputBuilder.toString();

        Main.main(new String[]{"arg1", "arg2"});

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMainMethodOutputEdgeCaseNullArgs() {
        String lineSeparator = System.lineSeparator();
        StringBuilder expectedOutputBuilder = new StringBuilder();
        expectedOutputBuilder.append("Hello and welcome!").append(lineSeparator);
        for (int i = 1; i <= 5; i++) {
            expectedOutputBuilder.append("i = ").append(i).append(lineSeparator);
        }
        String expectedOutput = expectedOutputBuilder.toString();

        Main.main(null);

        assertEquals(expectedOutput, outContent.toString());
    }
}