package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class MainTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testMainMethodOutput() {
        // Call the main method
        Main.main(new String[]{});

        // Construct the expected output string
        StringBuilder expectedOutputBuilder = new StringBuilder();
        expectedOutputBuilder.append("Hello and welcome!");
        for (int i = 1; i <= 5; i++) {
            expectedOutputBuilder.append("i = ").append(i).append(System.lineSeparator());
        }
        String expectedOutput = expectedOutputBuilder.toString();

        // Get the actual captured output
        String actualOutput = outputStreamCaptor.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"); // Normalize line endings

        // Assert that the captured output matches the expected output
        Assertions.assertEquals(expectedOutput.replace("\r\n", "\n"), actualOutput);
    }
}