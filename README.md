# Uno

## Overview
This Uno game is a project from the course "Introduction to Software Engineering" from the University of Massachusetts Boston.

## Usage
You can run the `src/main/java/uno/GameState.java` program to simulate the AI-based game and check who wins!

## Rules
The game is run between 4 AI players using random logic on the console. Here are the following rules:

1. The game is played with a deck where the number of digit, special, and wild cards can be customized (as seen in the main function in `GameState.java`).
2. The game is played with 4 players, and the size of the player's initial hand can also be customized.
3. Every player's turn consists of attempting to play a card.
4. If a player is unable to play a card, they must draw a card from the deck until they can play a card.
5. The first player to have no cards in their hand wins the game.

## Limitations
Since this is a simplified version of the regular Uno game, here are some things to consider:

1. There is no stacking of draw 2 and 4 cards.
2. Players with 1 card in their hand do not need to call "Uno".
3. You cannot challenge draw four cards (a modern Uno game rule).
4. The jump-in rule from the Uno Steam game is not implemented.

## Future Improvements
A friendly GUI can be implemented to make it much more fun for the players. This can also shift to a player vs. player game rather than an AI-based game. Certain parts of the code can be improved, but this is an initial approach to the simplified Uno game to learn more about software engineering principles. More specifically, unit testing, documentation, and code readability.

## Acknowledgments
This project belongs to CS410 - Introduction to Software Engineering from the University of Massachusetts Boston. 
