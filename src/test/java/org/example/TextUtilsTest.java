package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    @Test
    void testReverse() {
        assertEquals("cba", TextUtils.reverse("abc"));
        assertNull(TextUtils.reverse(null));
    }

    @Test
    void testIsPalindrome() {
        assertTrue(TextUtils.isPalindrome("madam"));
        assertFalse(TextUtils.isPalindrome("abc"));
        assertFalse(TextUtils.isPalindrome(null));
        assertTrue(TextUtils.isPalindrome("A man, a plan, a canal: Panama"));
    }

    @Test
    void testCountVowels() {
        assertEquals(0, TextUtils.countVowels("bc"));
        assertEquals(2, TextUtils.countVowels("hello"));
        assertEquals(0, TextUtils.countVowels(null));
    }

    @Test
    void testToUpperCase() {
        assertEquals("ABC", TextUtils.toUpperCase("abc"));
        assertNull(TextUtils.toUpperCase(null));
    }

    @Test
    void testRemoveVowels() {
        assertEquals("Hll", TextUtils.removeVowels("Hello"));
        assertEquals("", TextUtils.removeVowels("AEIOUaeiou"));
        assertNull(TextUtils.removeVowels(null));
    }
}
