package cs410.uno;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    void testEmptyDeck() {
        Deck deck = new Deck();
        assertTrue(deck.isEmpty());
        assertTrue(deck.getDiscardPile().isEmpty());
    }

    @Test
    void testDrawPile() {
        int countDigitCardPerColor = 4;
        int countSpecialCardsPerColor = 3;
        int countWildCards = 1;
        Deck deck = new Deck(countDigitCardPerColor,
                countSpecialCardsPerColor, countWildCards);
        assertFalse(deck.isEmpty());
    }

    @Test
    void testGetDrawPile() {
        int countDigitCardPerColor = 2;
        int countSpecialCardsPerColor = 1;
        int countWildCards = 0;
        Deck deck = new Deck(countDigitCardPerColor,
                countSpecialCardsPerColor, countWildCards);
        assertFalse(deck.isEmpty());
        assertFalse(deck.getDrawPile().isEmpty());
        assertTrue(deck.getDrawPile().contains(new Card("RED", 1)));
        assertTrue(deck.getDrawPile().contains(new Card("BLUE", "REVERSE")));
    }

    @Test
    void testGetDiscardPile() {
        Deck deck1 = new Deck();
        assertTrue(deck1.getDiscardPile().isEmpty());

        int countDigitCardPerColor = 1;
        int countSpecialCardsPerColor = 1;
        int countWildCards = 1;
        Deck deck2 = new Deck(countDigitCardPerColor,
                countSpecialCardsPerColor, countWildCards);
        deck2.addCardToDiscard(new Card("WILD"));
        deck2.addCardToDiscard(new Card("RED", 1));
        assertTrue(deck2.getDiscardPile().contains(new Card("WILD")));
        assertTrue(deck2.getDiscardPile().contains(new Card("RED", 1)));
        assertFalse(deck2.getDiscardPile().contains(new Card("BLUE", 3)));
    }

    @Test
    void testAddCardToDiscard() {
        int countDigitCardPerColor = 2;
        int countSpecialCardsPerColor = 1;
        int countWildCards = 2;
        Deck deck = new Deck(countDigitCardPerColor,
                countSpecialCardsPerColor, countWildCards);
        deck.addCardToDiscard(new Card("GREEN", "REVERSE"));
        assertTrue(deck.getDiscardPile().contains(new Card("GREEN", "REVERSE")));
    }

    @Test
    void testDraw() {
        Deck deck = new Deck();
        deck.getDrawPile().add(new Card("RED", 2));
        deck.getDrawPile().add(new Card("BLUE", 4));
        assertEquals(new Card("BLUE", 4), deck.draw());
    }

    @Test
    void testGetTopDiscardCard() {
        Deck deck = new Deck();
        deck.getDrawPile().add(new Card("Blue", 7));
        deck.getDrawPile().add(new Card("WILD"));
        deck.getDrawPile().add(new Card("GREEN", "REVERSE"));
        deck.addCardToDiscard(deck.draw());
        assertEquals(new Card("GREEN", "REVERSE"),
                deck.getTopDiscardCard());
    }

    @Test
    void testRefreshDrawPile() {
        Deck deck = new Deck();
        deck.addCardToDiscard(new Card("YELLOW", 5));
        deck.addCardToDiscard(new Card("YELLOW", 1));
        deck.getDrawPile().add(new Card("RED", "REVERSE"));
        deck.getDrawPile().add(new Card("WILD"));
        deck.addCardToDiscard(deck.draw());
        deck.addCardToDiscard(deck.draw());
        deck.refreshDrawPile();
        assertFalse(deck.isEmpty());
        assertEquals(3, deck.getDrawPile().size());
        assertEquals(new Card("RED", "REVERSE"),
                deck.getTopDiscardCard());
    }

    @Test
    void testIsEmpty() {
        int countDigitCardPerColor = 1;
        int countSpecialCardsPerColor = 1;
        int countWildCards = 0;
        Deck deck1 = new Deck(countDigitCardPerColor,
                countSpecialCardsPerColor, countWildCards);
        assertFalse(deck1.isEmpty());
        Deck deck2 = new Deck();
        assertTrue(deck2.isEmpty());
    }
}