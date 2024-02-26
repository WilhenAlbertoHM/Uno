package cs410.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void testIsNormal() {
        Card card1 = new Card("RED", 1);
        Card card2 = new Card("YELLOW", "SKIP");
        Card card3 = new Card("WILD");
        assertTrue(card1.isNormal());
        assertTrue(card2.isNormal());
        assertFalse(card3.isNormal());
    }

    @Test
    void testIsSpecial() {
        Card card1 = new Card("WILD");
        Card card2 = new Card("blue", 8);
        Card card3 = new Card("yellow", "draw two");
        assertTrue(card1.isSpecial());
        assertFalse(card2.isSpecial());
        assertTrue(card3.isSpecial());
    }

    @Test
    void testIsWild() {
        Card card1 = new Card("green", 9);
        Card card2 = new Card("WILD");
        Card card3 = new Card("bLuE", "rEVERse");
        assertFalse(card1.isWild());
        assertTrue(card2.isWild());
        assertFalse(card3.isWild());
    }

    @Test
    void testGetColor() {
        Card card1 = new Card("WILD");
        Card card2 = new Card("red", 4);
        assertEquals("~", card1.getColor());
        assertEquals("RED", card2.getColor());
    }

    @Test
    void testSetColor() {
        Card card1 = new Card("RED", 3);
        Card card2 = new Card("WILD");

        card1.setColor("BLUE");
        assertEquals("RED", card1.getColor());
        card2.setColor("BLUE");
        assertEquals("BLUE", card2.getColor());
    }

    @Test
    void testGetDigit() {
        Card card1 = new Card("BLUE", 3);
        Card card2 = new Card("red", "skip");
        Card card3 = new Card("wild");

        assertEquals(3, card1.getDigit());
        assertEquals(-2, card2.getDigit());
        assertEquals(-1, card3.getDigit());
    }

    @Test
    void testGetSpecialInstruct() {
        Card card1 = new Card("Green", "DRAW TWO");
        Card card2 = new Card("red", "wild");
        Card card3 = new Card("green", 1);
        assertEquals("DRAW TWO", card1.getSpecialInstruct());
        assertEquals("WILD", card2.getSpecialInstruct());
        assertEquals("NORMAL", card3.getSpecialInstruct());
    }

    @Test
    void testToString() {
        Card card1 = new Card("WILD");
        assertEquals("~ WILD\n", card1.toString());
        card1.setColor("RED");
        assertEquals("RED WILD\n", card1.toString());

        Card card2 = new Card("RED", "REVERSE");
        assertEquals("RED REVERSE\n", card2.toString());

        Card card3 = new Card("Green", "3");
        assertEquals("GREEN 3\n", card3.toString());
    }

    @Test
    void testEquals() {
        assertEquals(new Card("Red", 1) , new Card("RED", 1));
        assertEquals(new Card("blue", "reVERSE"),
                new Card("blue", "reverse"));
        assertNotEquals(new Card("WILD"),
                new Card("RED", "WILD"));
    }
}