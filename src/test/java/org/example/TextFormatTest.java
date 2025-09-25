package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextFormatTest {
    private TextFormat TextOperations;

    @Test
    void testReverse() {
        assertEquals("cba", TextOperations.reverse("abc"));
        assertNull(TextOperations.reverse(null));
        assertEquals("", TextOperations.reverse(""));
    }

    @Test
    void testIsPalindrome() {
        assertTrue(TextOperations.isPalindrome("madam"));
        assertTrue(TextOperations.isPalindrome("A man, a plan, a canal: Panama"));
        assertFalse(TextOperations.isPalindrome("abc"));
        assertFalse(TextOperations.isPalindrome((String) null));
    }

    @Test
    void testToUpperCase() {
        assertEquals("ABC", TextOperations.toUpperCase("abc"));
        assertNull(TextOperations.toUpperCase(null));
    }

    @Test
    void testCountOccurrences() {
        assertEquals(2, TextOperations.countOccurrences("abababa", "aba"));
        assertEquals(0, TextOperations.countOccurrences("aaaa", "bb"));
        assertEquals(0, TextOperations.countOccurrences("test", ""));
        assertEquals(0, TextOperations.countOccurrences(null, "abc"));
        assertEquals(0, TextOperations.countOccurrences("text", null));
    }

    @Test
    void testRemoveVowels() {
        assertEquals("Hll", TextOperations.removeVowels("Hello"));
        assertEquals("", TextOperations.removeVowels("aeiouAEIOU"));
        assertNull(TextOperations.removeVowels(null));
    }
}