package cs410.uno;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a card in Uno.
 *
 * A card is considered a normal card if it has a color (red, yellow, green, or blue),
 * and it has either a digit (0-9), or a special instruction on its face.
 *
 * A card that contains a special instruction on its face is considered a special card
 * and the different kinds are:
 *      - "Skip": The next player is skipped, and the player after the
 *                skipped player takes the next turn.
 *      - "Reverse": The order of play around the circle is reversed. If it was previously
 *                   clockwise, it now becomes counter-clockwise, and vice versa.
 *      - "Draw Two": The next player must draw two cards, and then their turn is over, and
 *                    play continues with the player after them. They do not get an opportunity
 *                    to play a card that turn.
 *
 * A card is considered a wild card if it has no color, but the player declares a
 * wild card's effective color when the card is played.
 *
 * Invariant: A card must follow the criteria listed above;
 *            a card can only be a normal or a wild card.
 *
 * This class contains three constructors: one for wild cards, one for normal cards,
 * and one for special cards.
 * It also contains the following methods:
 *      - getDigit() - Returns the digit of the card.
 *      - getSpecialInstruct() - Returns the special instruction of the card.
 *      - getColor() - Returns the color of the card.
 *      - setColor() - Sets the color for the wild card.
 *      - isSpecial() - Checks whether a card contains a special instruction.
 *      - isWild() - Checks whether a card is a wild card.
 *      - isNormal() - Checks whether a card is a normal card.
 *      - getColors() - Returns the list of playable colors.
 *      - getDigits() - Returns the list of playable digits.
 *      - getSpecialInstructs() - Returns the list of playable special instructions,
 *                                including the wild card.
 *      - toString() - Returns the formatted String of the card, showing the color,
 *                     digit, and special instruction.
 *      - equals() - Returns true if the object is an instance of the Card class, false otherwise.
 */
public class Card {
    // Represents the list of colors that a card can represent.
    private static final String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};

    // Represents the list of digits that a card can represent.
    private static final int[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    // Represents the list of special cards that a card can represent.
    private static final String[] specialInstructs = {"SKIP", "REVERSE", "DRAW TWO", "WILD"};

    // Represents the color of the card.
    private String color;

    // Represents the digit of the card.
    private final int digit;

    // Represents a special instruction of the card.
    private final String specialInstruct;

    /**
     * Constructor that initializes the wild card.
     * @param wildCard represents the wild card.
     */
    public Card(String wildCard) {
        // If the input string is "WILD", set specialInstruct to
        // represent the wild card special instruction.
        // Otherwise, throw an IllegalArgumentException.
        wildCard = wildCard.toUpperCase();
        if (wildCard.equals("WILD")) {
            this.color = "~";
            this.digit = -1;
            this.specialInstruct = "WILD";
        } else {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    /**
     * Constructor that initializes the color and digit of the card.
     * @param color represents the color of the card.
     * @param digit represents the digit of the card.
     */
    public Card(String color, int digit) {
        // If the color and digit are not in the lists of colors and digits,
        // throw an IllegalArgumentException is thrown.
        // Otherwise, set the corresponding color and digit,
        // and specialInstruct to "NORMAL" to indicate that the card is not special.
        color = color.toUpperCase();
        if (Arrays.asList(colors).contains(color) ||
                Arrays.asList(digits).contains(digit)) {
            this.color = color;
            this.digit = digit;
            this.specialInstruct = "NORMAL";
        } else {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    /**
     * Constructor that initializes the color and special instruction of the card.
     * @param color represents the color of the card.
     * @param specialInstruct represents the special instruction of the card.
     */
    public Card(String color, String specialInstruct) {
        // If the color and special instruction are not in the lists of colors and specialInstruct,
        // throw an IllegalArgumentException is thrown.
        // Otherwise, set the corresponding color and specialInstruct, and digit to -1
        // to indicate that the card is special; no digit.
        color = color.toUpperCase();
        specialInstruct = specialInstruct.toUpperCase();
        if (Arrays.asList(colors).contains(color)
                || (Arrays.asList(specialInstructs).contains(specialInstruct)
                    && !specialInstruct.equals("WILD"))) {
            this.color = color;
            this.digit = -2; // so that it distinguishes WILD from SPECIAL INSTRUCTION;
                            // digit = -1 in prev constructor
            this.specialInstruct = specialInstruct;
        } else {
            throw new IllegalArgumentException("Invalid input!");
        }
    }

    // Getters.
    public int getDigit() {
        return this.digit;
    }
     // Note: If it's not a special card, return "Normal".
    public String getSpecialInstruct() {
        return this.specialInstruct;
    }
    public String getColor() {
        return this.color;
    }

    /**
     * Setter to change color if and only if the card is a wild card.
     * @param color color to be changed to.
     */
    public void setColor(String color) {
        if (this.specialInstruct.equals("WILD")) {
            this.color = color;
        }
    }

    /**
     * @return true if the card contains a special instruction, false otherwise.
     */
    public boolean isSpecial() {
        return Arrays.asList(specialInstructs).contains(this.getSpecialInstruct());
    }

    /**
     * @return true if the card is a wild card, false otherwise.
     */
    public boolean isWild() {
        return this.getSpecialInstruct().equals("WILD");
    }

    /**
     * @return true if the card is a normal, false otherwise.
     */
    public boolean isNormal() {
        return Arrays.asList(colors).contains(this.color) ||
                Arrays.asList(digits).contains(this.digit);
    }

    /**
     * @return the list of colors.
     */
    public static String[] getColors() {
        return colors;
    }

    /**
     * @return the list of digits.
     */
    public static int[] getDigits() {
        return digits;
    }

    /**
     * @return the list of special instructions.
     */
    public static String[] getSpecialInstructs() {
        return specialInstructs;
    }

    /**
     * @return the color, digit, and special instruction of a card, if applicable,
     * in the form of a String.
     */
    @Override
    public String toString() {
        if (getDigit() < 0) {
            return String.format("%s %s\n", getColor(), getSpecialInstruct());
        }
        return String.format("%s %d %s\n",
                getColor(), getDigit(), getSpecialInstruct());
    }

    /**
     * Returns true if the object is an instance of the Card class, false otherwise.
     * @param other the object to be compared against the Card class.
     * @return true if the given object is of type Card, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Card) {
            Card otherCard = (Card) other;
            return this.color.equals(otherCard.color)
                    && this.digit == otherCard.digit
                    && this.specialInstruct.equals(otherCard.specialInstruct);
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Card red1 = new Card("Red", 1);
        Card blueSkip = new Card("Blue", "Skip");
        Card wild = new Card("Wild");
        System.out.println(red1.getColor());
        System.out.println(blueSkip.getSpecialInstruct());
        System.out.println(red1);
        System.out.println(blueSkip);
        System.out.println(wild);
    }
}