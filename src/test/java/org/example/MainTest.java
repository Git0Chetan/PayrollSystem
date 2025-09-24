package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("Should print greeting and loop messages to console")
    void main_ShouldPrintExpectedOutput() {
        // Given
        String[] args = {}; // main method expects a String array for arguments

        // When
        Main.main(args);

        // Then
        String expectedOutput = "Hello and welcome!i = 1\ni = 2\ni = 3\ni = 4\ni = 5\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString(StandardCharsets.UTF_8));
    }
}