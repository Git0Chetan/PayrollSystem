package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class Algorithm_to_STest {

    @Test
    void testMainWithSingleElement() {
        String input = "1\n7\n"; // Size = 1, element = 7
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());

        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));

        try {
            Algorithm_to_S.main(new String[]{});
        } catch (Exception e) {
            fail("Main threw an exception: " + e);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = outContent.toString();

        // Basic assertions to verify interaction and result
        assertTrue(output.contains("Enter the Array size ="));
        assertTrue(output.contains("Enter Array 1 elements  = "));
        assertTrue(output.contains("Sorting Integers using the Bubble Sort"));
        assertTrue(output.contains("7"));
    }
}