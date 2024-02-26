package cs410.uno;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    @Test
    void testGameState() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameState(1, 1, 1, 1, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new GameState(2, 1, 0, 0, 1));
        assertThrows(IllegalStateException.class,
                () -> new GameState(300, 100, 200, 300, 400));
        assertFalse(new GameState(2, 2, 2, 2, 2).getDeck().isEmpty());
    }

    @Test
    void testStartGame() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameState(2, 1, 0, 0, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new GameState(100, 120, -100, 40, 30));
    }
}