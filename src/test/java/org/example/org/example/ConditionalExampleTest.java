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
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testMainMethodPrintsPassed() {
        // The score is hardcoded to 75, which is >= 60, so it should print "You passed!"
        ConditionalExample.main(new String[]{});

        String expectedOutput = "You passed!";
        // Use trim() to remove any trailing newlines or spaces that System.out.println might add
        String actualOutput = outputStreamCaptor.toString().trim();

        assertEquals(expectedOutput, actualOutput, "Main method should print 'You passed!' for score 75.");
    }
}