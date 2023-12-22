# Dice Game

This project contains a simple console-based dice game implementation in Java. The game involves two players who take turns rolling five dice, selecting a category, and adding the dice that match the selected category to their score. The game continues until all seven categories have been played or a player chooses to forfeit. The player with the highest score at the end of the game wins.

## Features

* Two-player mode: The game supports a two-player mode where two players compete against each other.
* Category selection: Players can choose from seven categories (Ones, Twos, Threes, Fours, Fives, Sixes, Sequence).
* Scoring: The score is calculated based on the number of dice that match the selected category. The 'Sequence' category has special rules.
* Turn order: The game alternates between the two players.
* Forfeiting: A player can choose to forfeit their turn.
* Scoreboard: The game keeps track of the scores for each player and displays the scoreboard after each turn.

## How to Run

To run the game, execute the `DiceGame` class's `main` method. Here is an example command to run the game using the `javac` and `java` commands:

```bash
javac DiceGame.java
java DiceGame
```

After running these commands, the game should start and prompt the players to make their moves.

## Code Structure

The `DiceGame` class is the main class that controls the flow of the game. It contains the following methods:

* `playGame()`: This method runs the main game loop. It alternates between the two players and calls the `playTurn()` method for each player's turn.
* `playTurn()`: This method handles a single player's turn. It gets the player's choice to either roll the dice or forfeit their turn, throws the dice, lets the player select a category, sets aside the dice that match the selected category, calculates the score, updates the score table, and displays the score table.
* `switchPlayer()`: This method switches the current player.
* `displayScoreTable()`: This method prints the current state of the score table to the console.
* `declareWinner()`: This method declares the winner of the game based on the final scores.

Other helper methods in the class handle specific parts of the game logic such as throwing the dice (`throwDice()`), getting the selected category from the user (`selectDiceNumber()`), setting aside the dice that match the selected category (`setAsideDice()`), and calculating the sum of a list of integers (`sumOfList()`).

## Future Improvements

Possible future improvements include:

* Adding a graphical user interface (GUI) instead of a console-based interface.
* Allowing more than two players.
* Adding more complex scoring rules.
* Implementing network multiplayer support so that players can play over a network.
