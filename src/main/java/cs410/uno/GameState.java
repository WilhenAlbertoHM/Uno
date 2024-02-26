package cs410.uno;
import java.util.Random;

/**
 * GamesState is a class that represents the current state of the Uno game, containing:
 *     - startGame() - Initializes the game with already arranged players,
 *                    their initial hands have been dealt, and the discard pile and
 *                    draw pile have been created.
 *     - getDeck() - Returns the draw pile.
 *     - isPlayable() - Returns true if the given card is playable from the color, digit,
 *                      and special instruction, false otherwise.
 *     - playMatchingCardOrDraw() - Simulates the action of a player choosing the card to play,
 *                                  or draw, if applicable.
 *     - handleSpecialCardsNext() - Handles the special cards that are placed on the discard pile,
 *                                  checking if it affects the next player's turn.
 *     - runOneTurn() - Runs one turn of the game, keeping it going until game is over.
 *     - isGameOver() - Returns true if the game is over.
 *
 *     - Invariant 1: The game must have at least 2 players to start.
 *     - Invariant 2: A player must have more than 0 normal cards for each digit and color.
 *     - Invariant 3: A player can have 0 or more special cards of each kind for each color.
 *     - Invariant 4: A player can have 0 or more wild cards.
 */
public class GameState {
    // Number of colors.
    private static final int NUM_COLORS = 4;

    // Represents the list of colors.
    private static final String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};


    // Number of players.
    private final int countPlayers;

    // Number of cards initially dealt to each player.
    private final int countInitialCardsPerPlayer;

    // Number of normal cards for each digit and color.
    // Example: 2 means 2 Red "0" cards, 2 Yellow "0" cards, etc.
    private final int countDigitCardsPerColor;

    // Number of special cards of each kind for each color.
    // Example: 1 means 1 Red Skip card, 1 Yellow Skip card, ...,
    // 1 Red Reverse card, 1 Yellow Reverse card, etc.
    private final int countSpecialCardsPerColor;

    // Number of total wild cards.
    private final int countWildCards;

    // Represents the deck, both draw and discard piles.
    private final Deck deck;

    // Represents the list of players.
    private Player[] players;

    // Represents the index of current player.
    private int currentPlayerIdx;

    /**
     * Constructor that initializes the state of the Uno game, with the respective
     * number of players and cards that each player must have according to the invariants.
     *
     * @param countPlayers number of players.
     * @param countInitialCardsPerPlayer number of cards initially dealt to each player.
     * @param countDigitCardsPerColor number of normal cards for each digit and color.
     * @param countSpecialCardsPerColor number of special cards of each kind for each color.
     * @param countWildCards number of total wild cards.
     */
    public GameState(int countPlayers, int countInitialCardsPerPlayer,
                     int countDigitCardsPerColor, int countSpecialCardsPerColor,
                     int countWildCards) {
        // If there are less than 2 players or negative number of cards,
        // throw an IllegalArgumentException error.
        int totalCards = NUM_COLORS * countDigitCardsPerColor +
                NUM_COLORS * countSpecialCardsPerColor + countWildCards;

        if (countPlayers < 2 || countInitialCardsPerPlayer <= 0 || countDigitCardsPerColor <= 0
                || countSpecialCardsPerColor < 0 || countWildCards < 0
                || totalCards < countPlayers) {
            throw new IllegalArgumentException("Illegal input!");

        } else {
            // Initialize instance variables.
            this.countPlayers = countPlayers;
            this.countInitialCardsPerPlayer = countInitialCardsPerPlayer;
            this.countDigitCardsPerColor = countDigitCardsPerColor;
            this.countSpecialCardsPerColor = countSpecialCardsPerColor;
            this.countWildCards = countWildCards;
            this.currentPlayerIdx = 0;

            // Initialize the list of players and deck.
            this.players = new Player[countPlayers];
            this.deck = new Deck(countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);

            // Add players to the list of players.
            for (int i = 0; i < countPlayers; i++) {
                Player player = new Player(String.format("Player %d\n", i + 1));
                for (int j = 0; j < countInitialCardsPerPlayer; j++) {
                    player.addToHand(this.deck.draw());
                }
                this.players[i] = player;
            }

            System.out.printf("Total Players: %d\n", this.players.length);
            System.out.printf("Hand size per player: %d\n\n", this.players[0].getHand().size());

            // Draws the first card from deck.
            this.deck.addCardToDiscard(this.deck.draw());

            // If the first card is a wild card, set the card to a random color.
            if (this.deck.getTopDiscardCard().isWild()) {
                Random random = new Random();
                int randomIdx = random.nextInt(NUM_COLORS);
                this.deck.getTopDiscardCard().setColor(colors[randomIdx]);
            }
            System.out.printf("Starting Top Card: %s\n", this.deck.getTopDiscardCard().toString());
        }
    }

    /**
     * After the startGame method ends, the game state should represent the
     * situation immediately before the first player takes their first turn.
     * That is, the players should be arranged, their initial hands have been dealt,
     * and the discard pile and draw pile have been created.
     *
     * @param countPlayers number of players.
     * @param countInitialCardsPerPlayer number of cards initially dealt to each player.
     * @param countDigitCardsPerColor number of normal cards for each digit and color.
     * @param countSpecialCardsPerColor number of special cards of each kind for each color.
     * @param countWildCards number of total wild cards.
     * @return An initialized GameState where each player can play according to the rules.
     */
    public static GameState startGame(int countPlayers, int countInitialCardsPerPlayer,
                                      int countDigitCardsPerColor, int countSpecialCardsPerColor,
                                      int countWildCards) {
        return new GameState(countPlayers, countInitialCardsPerPlayer, countDigitCardsPerColor,
                countSpecialCardsPerColor, countWildCards);
    }

    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns true if the card is playable. A card is playable if the color, digit,
     * or special instruction of the card matches with the card on top of the discard pile.
     * A card is also playable if it's a wild card.
     * @param card card to be compared against the card on top of the discard pile.
     * @return true if the card is playable, otherwise false.
     */
    public boolean isPlayable(Card card) {
        String cardColor = card.getColor();
        int cardDigit = card.getDigit();
        String cardSpecial = card.getSpecialInstruct();

        String discardColor = this.deck.getTopDiscardCard().getColor();
        int discardDigit = this.deck.getTopDiscardCard().getDigit();
        String discardSpecial = this.deck.getTopDiscardCard().getSpecialInstruct();

        return (cardColor.equals(discardColor) || cardDigit == discardDigit || card.isWild());
    }

    /**
     * Simulates the player's actions upon seeing the top card on the discard pile.
     * If no match is found, draw a card. If the drawn card is a match, play that card,
     * otherwise, the player keeps the card.
     */
    public void playMatchingCardOrDraw() {
        // Get the color and digit of the card on top of discard pile.
        String topDiscardedCardColor = this.deck.getTopDiscardCard().getColor();
        int topDiscardedCardDigit = this.deck.getTopDiscardCard().getDigit();

        // Check every card in the player's hand against the card on the discard pile.
        int i = 0;
        int totalCardsInHand = this.players[currentPlayerIdx].getHand().size();
        while (i < totalCardsInHand) {
            // Get the card from the player.
            Card playerCard = this.players[currentPlayerIdx].getCard(i);

            // Get card's color and digit.
            String playerCardColor = playerCard.getColor();
            int playerCardDigit = playerCard.getDigit();

            // If the color or digit of the card matches the card on the discard pile,
            // or it's a wild card, the player plays its card on the discard pile.
            if (isPlayable(playerCard)) {
                this.players[currentPlayerIdx].removeCard(playerCard);
                this.deck.addCardToDiscard(playerCard);

                System.out.printf("Player %d plays %sCurrent hand size: %s\n\n",
                        this.currentPlayerIdx + 1,
                        playerCard.toString(),
                        this.players[currentPlayerIdx].getHand().size());
                break;
            }

            i++; // Check next card.
        }

        // If the player has the same number of cards in his hand
        // (no card match found -> card not played, yet),
        // draw a card from the draw pile and check if the player can place that card.
        if (totalCardsInHand == this.players[currentPlayerIdx].getHand().size()) {
            Card drawnCard = this.deck.draw();
            String drawnCardColor = drawnCard.getColor();
            int drawnCardDigit = drawnCard.getDigit();

            // If the drawn card is playable, add it to the discard pile.
            if (isPlayable(drawnCard)) {
                this.deck.addCardToDiscard(drawnCard);

            // Otherwise, add the drawn card to the player's hand.
            } else {
                this.players[currentPlayerIdx].addToHand(drawnCard);
            }
        }
    }

    /**
     * Handles with the special instruction cards.
     *
     */
    private void handleSpecialCardsNext() {
        // Get the next/prev player, accounting for the "circular" behavior.
        // E.g, 4 players in game, and it's Player 3's turn. Player 3 places a
        // skip special instruction card. Next player will be Player 1, not Player 5.
        // E.g., 4 players in game, and it's Player 1's turn. Player 1 places a
        // reverse special instruction card. Next player will be Player 4, not Player 0.
        int nextPlayerIdx = (this.currentPlayerIdx + 1) % this.countPlayers;
        int nextNextPlayerIdx = (this.currentPlayerIdx + 2) % this.countPlayers;
        int prevPlayerIdx = (this.currentPlayerIdx - 1 + this.countPlayers) % this.countPlayers;

        // If the card placed by player is a wild card,
        // set the current card placed to a random color
        // (AIs are playing, color will be random so no need to give choice).
        if (this.deck.getTopDiscardCard().isWild()) {
            Random random = new Random();
            int randomIdx = random.nextInt(NUM_COLORS);
            this.deck.getTopDiscardCard().setColor(colors[randomIdx]);
            this.currentPlayerIdx = nextPlayerIdx;

        // In addition, if the card placed by player is a special card,
        // check which special instruction does it apply to the other players.
        } else if (this.deck.getTopDiscardCard().isSpecial()) {

            // If the placed card is a "SKIP", skip the next player.
            switch (this.deck.getTopDiscardCard().getSpecialInstruct()) {
                case "SKIP":
                    this.currentPlayerIdx = nextNextPlayerIdx;
                    break;

                // If the placed card is a "REVERSE",
                // change the direction of the normal flow among players.
                // E.g., Clockwise to counter-clockwise.
                case "REVERSE":
                    this.currentPlayerIdx = prevPlayerIdx;
                    break;

                // If the placed card is a "DRAW TWO", add two cards from
                // the draw pile to the next player's hand
                case "DRAW TWO":
                    this.players[nextPlayerIdx].addToHand(deck.draw());
                    this.players[nextPlayerIdx].addToHand(deck.draw());
                    this.currentPlayerIdx = nextNextPlayerIdx;
                    break;
            }
        } else {
            this.currentPlayerIdx = nextPlayerIdx;
        }
    }

    /** The current player takes their turn, and if they play a special card
     * the corresponding effects are performed. When the method returns,
     * the next player is ready to take their turn.
     * If the game is already over, this method has no effect.
     */
    public void runOneTurn() {
        // If the draw pile is empty, refresh the draw pile from the discard pile.
        if (this.deck.isEmpty()) {
            this.deck.refreshDrawPile();
        }

        // Check if player has a card to play. If not, draw a card from draw pile.
        // If the drawn card is a match, play the card, otherwise add the card
        // to the player's hand.
        playMatchingCardOrDraw();

        // Deal with the next turn depending on the card on top of the discard pile
        // (deal with possible special cards).
        handleSpecialCardsNext();
    }

    /**
     * @return true if the game is over by checking for an empty hand, false otherwise.
     */
    public boolean isGameOver() {
        for (Player player : this.players) {
            if (player.isHandEmpty()) {
                System.out.printf("%sWON!!!", player.toString());
                return true;
            }
        }
        return false;
    }

    /**
     * Main function to simulate a game of Uno.
     */
    public static void main(String[] args) {
        GameState game = new GameState(4, 7, 1, 1, 0);
        while (!game.isGameOver()) {
            game.runOneTurn();
        }
    }
}
