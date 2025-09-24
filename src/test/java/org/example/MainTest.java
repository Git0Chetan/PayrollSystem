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
    void testMainMethodPrintsExpectedOutput() {
        // The main method does not use its arguments, so passing null is acceptable.
        Main.main(null);

        // Construct the expected output string precisely based on the Main class's behavior.
        // printf does not add a newline, while println does.
        String expectedOutput = "Hello and welcome!" +
                                "i = 1" + System.lineSeparator() +
                                "i = 2" + System.lineSeparator() +
                                "i = 3" + System.lineSeparator() +
                                "i = 4" + System.lineSeparator() +
                                "i = 5" + System.lineSeparator();

        // Assert that the captured standard output matches the expected string.
        assertEquals(expectedOutput, outContent.toString(), "The output of the main method did not match expectations.");
    }
}