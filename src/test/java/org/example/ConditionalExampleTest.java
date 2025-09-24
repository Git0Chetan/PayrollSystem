package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConditionalExampleTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testMainMethod_fixedScore_shouldPrintPassed() {
        // The main method has a hardcoded score of 75.
        // This test verifies that for this score, the correct message is printed.
        ConditionalExample.main(new String[]{});

        String expectedOutput = "You passed!" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString(),
                "The main method should print 'You passed!' for a score of 75.");
    }
}