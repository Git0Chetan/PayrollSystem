package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConditionalExampleTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testMainMethodPrintsPassedForFixedScore() {
        // The main method has a hardcoded score of 75, which is >= 60
        ConditionalExample.main(new String[]{});

        // Verify that "You passed!" is printed to standard output
        assertEquals("You passed!\n", outputStreamCaptor.toString());
    }
}