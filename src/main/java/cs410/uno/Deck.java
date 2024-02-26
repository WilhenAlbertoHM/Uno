package cs410.uno;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of Uno cards that consists of a draw and discard pile.
 * This class has two constructors: one for having the draw pile filed, ready to start a game,
 * while the other has empty draw and discard piles. The constructor with input mostly represent
 * the draw pile with cards, and the other one represents mainly the discard pile.
 *
 * This class contains the following methods:
 *      - getDrawPile() - Returns the draw pile.
 *      - getDiscardPile() - Returns the discard pile.
 *      - fillDeck() - Helper function to fill the draw pile with cards.
 *      - shuffle() - Shuffles the draw pile.
 *      - addCardToDiscard() - Adds a card to the discard pile,
 *                             simulating the action of placing a card by the player.
 *      - refreshDrawPile() - Refreshes the draw pile when empty with the discard pile,
 *                            and shuffles the deck once refilled.
 *      - draw() - Returns and removes a card from the top of the draw pile.
 *      - getTopDiscardCard() - Returns a card from the top discard pile. Acts like a peek function.
 *      - isEmpty() - Returns true if the draw pile is empty, false otherwise.
 */
public class Deck {
    // Represents the draw pile.
    private List<Card> drawPile;

    // Represents the discard pile.
    private List<Card> discardPile;

    /**
     * Constructor that initializes the draw pile with cards.
     * @param countDigitCardsPerColor number of normal cards for each digit and color.
     * @param countSpecialCardsPerColor number of special cards of each kind for each color.
     * @param countWildCards number of total wild cards.
     */
    public Deck(int countDigitCardsPerColor,
                int countSpecialCardsPerColor,
                int countWildCards) {
        // If number of cards are invalid, throw an IllegalArgumentException.
        if (countDigitCardsPerColor <= 0
                || countSpecialCardsPerColor < 0 || countWildCards < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        fillDeck(countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
    }

    /**
     * Constructor that has the draw and discard piles empty.
     * Symbolizes the discard pile.
     */
    public Deck() {
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    /**
     * Getter function that returns the draw pile.
     * @return the draw pile.
     */
    public List<Card> getDrawPile() {
        return drawPile;
    }

    /**
     * Getter function that returns the discard pile.
     * @return the discard pile.
     */
    public List<Card> getDiscardPile() {
        return discardPile;
    }

    /**
     * Fills the draw pile with cards.
     */
    private void fillDeck(int countDigitCardsPerColor,
                               int countSpecialCardsPerColor,
                               int countWildCards) {
        // If the deck is not empty yet, avoid creating another deck until it's empty.
        // Throw an IllegalArgumentException error to warn user.
        if (!isEmpty()) {
            throw new IllegalStateException("Can't create a new deck, yet!");
        }

        // Get the lists of colors and special instructions.
        String[] colors = Card.getColors();
        String[] specialCards = Card.getSpecialInstructs();

        // For each color, add digit and special instruction cards to draw pile.
        for (String color : colors) {
            // Digits.
            for (int i = 0; i < countDigitCardsPerColor; i++) {
                for (int digit = 0; digit < 10; digit++) {
                    this.drawPile.add(new Card(color, digit));
                }
            }
            // Special instructions.
            for (int i = 0; i < countSpecialCardsPerColor; i++) {
                for (String specialCard : specialCards) {
                    this.drawPile.add(new Card(color, specialCard));
                }
            }
        }

        // Add wild cards to the draw pile.
        for (int i = 0; i < countWildCards; i++) {
            this.drawPile.add(new Card("WILD"));
        }

        // Shuffle the draw pile.
        shuffle();
    }

    /**
     * Shuffles the draw pile.
     */
    private void shuffle() {
        if (isEmpty()) {
            throw new IllegalStateException("Draw pile is empty!");
        }
        Collections.shuffle(this.drawPile);
    }

    /**
     * Adds card to the discard pile.
     */
    public void addCardToDiscard(Card card) {
        this.discardPile.add(card);
    }

    /**
     * Refreshes the draw pile from the discard pile.
     * The top card of the discard pile is retained;
     * the new discard pile consists of only that card.
     * The other cards in the discard pile are shuffled and placed face-down,
     * and they become the new draw pile.
     */
    public void refreshDrawPile() {
        if (!this.discardPile.isEmpty() && this.drawPile.isEmpty()) {
            Card topCard = discardPile.remove(discardPile.size() - 1);
            drawPile.addAll(discardPile);
            discardPile.clear();
            addCardToDiscard(topCard);
            shuffle();
        }
    }

    /**
     * Returns a card from the top of the draw pile.
     */
    public Card draw() {
        if (isEmpty()) {
            throw new IllegalStateException("Draw pile is empty!");
        }
        return this.drawPile.remove(this.drawPile.size() - 1);
    }

    /**
     * Retrieves the top card from the discard pile.
     * @return The top card on the discard pile.
     */
    public Card getTopDiscardCard() {
        if (this.discardPile.isEmpty()) {
            throw new IllegalStateException("Discard pile is empty!");
        }
        return discardPile.get(discardPile.size() - 1);
    }

    /**
     * @return true if the draw pile is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.drawPile.isEmpty();
    }
}