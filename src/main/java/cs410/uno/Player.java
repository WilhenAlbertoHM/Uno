package cs410.uno;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a player that has a hand, represented by an ArrayList of cards.
 * Constructor takes in a name for the player.
 * This class contains the following methods:
 *     - getHand() - Returns the current hand of the player.
 *     - getName() - Returns the name of the player.
 *     - addToHand() - Adds the input card to the player's hand.
 *     - getCard() - Places a card to the discard pile, if applicable.
 *     - removeCard() - Removes the given card from the player's hand.
 *     - isHandEmpty() - Checks if the player's hand is empty or not.
 *     - toString() - Returns the formatted String of Player, displaying the name of the player.
 */
public class Player {
    // Represents the name of a player.
    private final String name;
    // Represents the hand of a player.
    private List<Card> hand;

    /**
     * Constructor that initializes the hand of a player.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    // Getters.
    public List<Card> getHand() {
        return this.hand;
    }
    public String getName() {
        return this.name;
    }

    /**
     * Adds a card to the player's hand from the draw pile.
     * @param card a card to be added to the hand.
     */
    public void addToHand(Card card) {
        this.hand.add(card);
    }

    /**
     * Places a card to the discard pile, if applicable.
     * @return a card from the player's hand.
     */
    public Card getCard(int index) {
        if (isHandEmpty()) {
            throw new IllegalStateException("Hand is empty!");
        } else if (index < 0 || index >= this.hand.size()) {
            throw new IllegalArgumentException("Illegal index");
        }
        return this.hand.get(index);
    }

    /**
     * Removes the given card from the player's hand
     * @param card card to be removed
     */
    public void removeCard(Card card) {
        if (isHandEmpty()) {
            throw new IllegalStateException("Hand is empty!");
        }
        this.hand.remove(card);
    }

    /**
     * @return true if the player's hand is empty, false otherwise.
     */
    public boolean isHandEmpty() {
        return this.hand.isEmpty();
    }

    /**
     * @return the formatted String of Player, displaying the name of the player.
     */
    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}