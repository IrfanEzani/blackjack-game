# Blackjack Game Simulation
![image](https://github.com/IrfanEzani/blackjack-game/assets/59435235/d6d46d11-7501-4fd9-b860-3ff23dcaf2d1)

## Overview
A comprehensive Java application implementing the classic casino game, Blackjack (21). This project encompasses object-oriented design principles, featuring modularized packages for game logic (blackjack), card deck handling (deckOfCards), and graphical user interface (GUI). The core gameplay logic in BlackjackModel.java integrates simple algorithms for card dealing, hand assessment, and game outcome determination. The deckOfCards package abstracts card representations with Card, Deck, Rank, and Suit classes, facilitating easy deck manipulation and card management. The GUI, built using Java Swing, provides a rich interactive experience, enabled by the BlackjackGUI.java and ImageLoader.java classes, supporting image caching for optimal performance.

## About Blackjack
The goal of the game is to beat the dealer's hand without going over 21. Each card has a value (number cards are worth their number, face cards are worth 10, and Aces can be 1 or 11). Players are dealt two cards and can choose to "Hit" (take another card) or "Stand" (keep their current hand). The dealer plays after the players, and the goal is to get a higher total than the dealer without exceeding 21. A "Blackjack" is when your first two cards are an Ace and a 10-value card, which is the best possible hand.

## Features
- **Core Gameplay**: Play against a virtual dealer in a simulated game of Blackjack.
- **Graphical User Interface**: A clean and interactive GUI for an engaging gaming experience.
- **Deck Handling**: An implemented deck of cards with shuffling and dealing functionalities.
- **Game Logic**: Comprehensive game logic to handle various scenarios in Blackjack.

## Project Structure
- `src/blackjack`: Contains the core game logic.
  - `BlackjackModel.java`: Main game logic.
  - `GameResult.java`: Enum for game results.
  - `HandAssessment.java`: Enum for hand status.
- `src/deckOfCards`: Represents and manages a deck of cards.
- `src/GUI`: Graphical interface for the game.
  - `BlackjackGUI.java`: Main GUI component.
  - `ImageLoader.java`: Utility for image loading.

## How to Run
1. Run `java -jar Driver.jar` to start the application.
2. Interact with the application using the GUI to play the game.

## Exploration
Extract the JAR file to see the detailed codes of the application
