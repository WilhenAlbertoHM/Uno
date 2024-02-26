package cs410.uno;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testPlayerName() {
        Player player1 = new Player("July");
        assertEquals("July", player1.getName());
        Player player2 = new Player("Brendan");
        assertEquals("Brendan", player2.getName());
    }

    @Test
    void testGetHand() {
        Player player1 = new Player("Roberto");
        player1.addToHand(new Card("Red", 1));
        player1.addToHand(new Card("WILD"));
        player1.addToHand(new Card("Blue", 2));
        assertEquals(List.of(new Card("RED", 1),
                new Card("WILD"),
                new Card("BLUE", 2)), player1.getHand());

        Player player2 = new Player("Leo");
        assertEquals(List.of(), player2.getHand());
    }

    @Test
    void testAddToHand() {
        Player player = new Player("Trojan");
        player.addToHand(new Card("RED", 7));
        player.addToHand(new Card("WILD"));
        player.addToHand(new Card("YELLOW", "DRAW TWO"));
        assertEquals(List.of(new Card("RED", 7),
                new Card("WILD"),
                new Card("YELLOW", "DRAW TWO")), player.getHand());
    }

    @Test
    void testGetCard() {
        Player player1 = new Player("John");
        assertThrows(IllegalStateException.class, () -> player1.getCard(0));
        player1.addToHand(new Card("WILD"));
        assertThrows(IllegalArgumentException.class, () -> player1.getCard(4));

        Player player2 = new Player("Juan");
        player2.addToHand(new Card("GREEN", 4));
        player2.addToHand(new Card("BLUE", "REVERSE"));
        player2.addToHand(new Card("WILD"));
        assertEquals(new Card("GREEN", 4), player2.getCard(0));
    }

    @Test
    void testRemoveCard() {
        Player player = new Player("Ryan");
        assertThrows(IllegalStateException.class,
                () -> player.removeCard(new Card("WILD")));

        player.addToHand(new Card("GREEN", "DRAW TWO"));
        player.addToHand(new Card("YELLOW", "SKIP"));
        player.addToHand(new Card("WILD"));

        player.removeCard(new Card("WILD"));
        assertEquals(List.of(new Card("GREEN", "DRAW TWO"),
                new Card("YELLOW", "SKIP")), player.getHand());
    }

    @Test
    void testIsHandEmpty() {
        Player player = new Player("Sarah");
        player.addToHand(new Card("Blue", "Draw Two"));
        player.addToHand(new Card("Green", "Skip"));
        player.addToHand(new Card("Red", "Draw Two"));
        player.addToHand(new Card("WILD"));
        player.removeCard(player.getCard(0));
        player.removeCard(player.getCard(0));
        assertFalse(player.isHandEmpty());

        player.removeCard(player.getCard(0));
        player.removeCard(player.getCard(0));
        assertTrue(player.isHandEmpty());
    }

    @Test
    void testToString() {
        Player player1 = new Player("Alfredo");
        Player player2 = new Player("Jean");
        assertEquals("Alfredo", player1.toString());
        assertEquals("Jean", player2.toString());
    }
}