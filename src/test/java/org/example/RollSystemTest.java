package org.example;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

        import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RollSystemTest {

    private RollSystem rollSystem;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        rollSystem = new RollSystem();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("roll: two positive numbers should return correct sum")
    void testAdd_PositiveNumbers() {
        assertEquals(5, rollSystem.roll(2, 3));
    }

    @Test
    @DisplayName("roll: positive and negative numbers should return correct sum")
    void testAdd_PositiveAndNegativeNumbers() {
        assertEquals(-1, rollSystem.roll(2, -3));
        assertEquals(1, rollSystem.roll(-2, 3));
    }

    @Test
    @DisplayName("roll: zero with a number should return the number")
    void testAdd_Zero() {
        assertEquals(5, rollSystem.roll(5, 0));
        assertEquals(-5, rollSystem.roll(-5, 0));
        assertEquals(0, rollSystem.roll(0, 0));
    }

    @Test
    @DisplayName("roll: large numbers near Integer.MAX_VALUE and Integer.MIN_VALUE")
    void testAdd_LargeNumbers() {
        assertEquals(Integer.MAX_VALUE, rollSystem.roll(Integer.MAX_VALUE - 1, 1));
        assertEquals(Integer.MIN_VALUE, rollSystem.roll(Integer.MIN_VALUE + 1, -1));
    }
}