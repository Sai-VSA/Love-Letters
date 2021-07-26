package ui;

import model.*;

import java.util.Scanner;


public class GameInterface {

    private Player player1;
    private Player player2;
    private Deck deck;
    private Player playingPlayer;
    private int turnState;
    private int gameState;
    private Scanner input;
    private CardEffects ce;
    private Card lastPlayed;


    /* EFFECT: starts game
     */
    public GameInterface() {
        startGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void startGame() {
        String userInput = null;

        init();
        gameState = 0;
        if (gameState == 0) {
            displayGUI();
            gameState = 1;
        }

        while ((!player1.returnEliminated()) && (!player2.returnEliminated())) {
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (gameState == 1) {
                baseState(userInput);
            } else if (gameState == 2) {
                checkRuleState(userInput);
            } else if (gameState == 3) {
                cardPlayer(userInput);
            } else if (gameState == 4) {
                guardState(userInput);
            }
        }

        printEndGame();
    }

    // EFFECTS: initializes the players and scanner
    private void init() {
        input = new Scanner(System.in);
        player1 = new Player("player1");
        player2 = new Player("player2");
        deck = new Deck();
        player1.addCard(deck.drawCard());
        player2.addCard(deck.drawCard());
        gameState = 1;
        turnState = 1;
        playingPlayer = player1;
        player1.flipTurn();
    }

    // EFFECTS: processes user command at beginning interface
    private void baseState(String command) {
        if (command.equals("a")) {
            displayRules();
        } else if (command.equals("b")) {
            drawAndReset();
        } else if (playingPlayer.returnDrewCard() == true && playingPlayer.returnHandSize() == 2
                && command.equals("c")) {
            printPlayerHand(playingPlayer);
            gameState = 3;
            System.out.println("\n Press C to go back to main.");
        } else if (command.equals("c")) {
            printPlayerHand(playingPlayer);
        } else if (command.equals("d")) {
            printDiscardPile();
        } else if (command.equals("e")) {
            nextTurn();
        } else {
            System.out.println("Please choose a valid option.");
        }
    }

    // EFFECT: Opens and activates interface for playing a card
    private void cardPlayer(String command) {
        int a;
        if (command.equals("a") || command.equals("b")) {
            if (command.equals("a")) {
                a = 0;
            } else {
                a = 1;
            }
            lastPlayed = playingPlayer.returnPlayerHand()[a];
            if (playingPlayer.returnPlayerHand()[a].returnCardName() == "Guard") {
                gameState = 4;
                System.out.println("You played Guard.\nType ___ to guess: \nA -> RoyalSubject \nB -> Gossip \nC -> "
                        + "Companion \nD -> Hero \nE -> Wizard \nF -> Lady \nG -> Princess");
            } else {
                playCard(playingPlayer.returnPlayerHand()[a]);
            }

        } else if (command.equals("c")) {
            gameState = 1;
            displayGUI();
        } else {
            System.out.println("Please choose a valid option, \n please try not adding spaces.");
        }
    }

    // EFFECT: plays guard from player's hand
    private void guardState(String c) {
        ce = new CardEffects(deck, player1, player2);
        deck.discardCard(playingPlayer.discardCard(lastPlayed));


        ce.playGuard(c);


        gameState = 1;
        displayGUI();
    }

    // EFFECT: plays a card from player's hand
    private void playCard(Card c) {
        ce = new CardEffects(deck, player1, player2);
        deck.discardCard(playingPlayer.discardCard(c));

        if ((turnState == 1 && player2.returnImmune() == true)
                || (turnState == 2 && player1.returnImmune() == true)) {
            System.out.println("The opponent was immune.");
        } else {
            if (c.returnCardName().equals("Royal Subject")) {
                System.out.println(ce.playRoyalSubject());
            } else if (c.returnCardName().equals("Gossip")) {
                ce.playGossip();
            } else if (c.returnCardName().equals("Companion")) {
                ce.playCompanion();
            } else if (c.returnCardName().equals("Hero")) {
                ce.playHero();
            } else if (c.returnCardName().equals("Wizard")) {
                ce.playWizard();
            } else if (c.returnCardName().equals("Princess")) {
                playingPlayer.setEliminated();
            }
        }

        System.out.println("Your card \"" + c.returnCardName() + "\" was played successfully.");

        gameState = 1;
        displayGUI();
    }

    // EFFECT: Draws a card from deck and adds it to player's hand
    private void drawAndReset() {
        if (turnState == 1) {
            if (player1.returnDrewCard() == false) {
                Card a = deck.returnDeck().peek();
                System.out.println("\nYou drew the " + a.returnCardName() + ".");
                player1.addCard(deck.drawCard());
                player1.flipDraw();
            } else {
                System.out.println("Sorry, you already drew this turn.");
            }
        } else if (turnState == 2) {
            if (player2.returnDrewCard() == false) {
                Card a = deck.returnDeck().peek();
                System.out.println("\nYou drew the " + a.returnCardName() + ".");
                player2.addCard(deck.drawCard());
                player2.flipDraw();
            } else {
                System.out.println("Sorry, you already drew this turn.");
            }
        }
    }


    // EFFECTS: processes user command during Check Rules
    private void checkRuleState(String command) {
        if (command.equals("a")) {
            displayRules();
        } else if (command.equals("b")) {
            displayCards();
        } else if (command.equals("c")) {
            gameState = 1;
            displayGUI();
        } else {
            System.out.println("Please choose a valid option.");
        }
    }


    // EFFECTS: displays rules and list of cards to user
    private void displayRules() {
        System.out.println("\n   1. Set Up: The game starts with a pre-shuffled deck on a random players turn.");
        System.out.println("   2. Goal: Either eliminate all other players or survive till the end.");
        System.out.println("   3. Win Condition: If more than one player is alive when the deck runs out, "
                + "\n  \tcompare hand sizes. The player with the larger hand wins.");
        System.out.println("   4. Turns: The players each begin their turn with one card in hand,"
                + "\n  \tthen they draw and play one card, before passing to the next players turn.");
        System.out.println("   5. Special Rule: If you discard a hero with hero, you win the game!");
        System.out.println("\n Press A to refresh \n Press B to see the list of cards \n Press C to go back");
        gameState = 2;
    }

    // EFFECTS: Print game cards and their effects
    private void displayCards() {
        System.out.println("\n The list of cards, along with their number, name(quantity) and ability are:");
        System.out.println("\n 1. Guard(5): Guess the opposing player's card,"
                + "\n \t they are eliminated if you guess right.");
        System.out.println(" 2. Royal Subject(2): See the opponent's hand.");
        System.out.println(" 3. Gossip(3): Compare hands with the opponents,"
                + "\n \t lower hand is eliminated.");
        System.out.println(" 4. Companion(2): You are immune to card effects until your next turn.");
        System.out.println(" 5. Hero(2): The opponent discards their hand and then draws a card.");
        System.out.println(" 6. Wizard(1): Swap hands with your opponent");
        System.out.println(" 7. Lady(1): You must discard the Lady if you are holding Wizard or Hero.");
        System.out.println(" 8. Princess(1): You lose if you discard this card.");
        System.out.println("\n Press A to see rules \n Press B to refresh \n Press C to go back to main menu.");
    }

    // EFFECTS: Shows hand of player during player's turn
    private void showOwnHand() {
        if (turnState == 1) {
            printPlayerHand(player1);
        } else if (turnState == 2) {
            printPlayerHand(player2);
        }
        System.out.println("\n Press C to return");
    }

    // EFFECTS: Print game cards and their effects
    private void printDiscardPile() {
        int c = (deck.returnCardsInDiscard());
        System.out.println("The discard pile contains:");
        for (int i = 0; i < c; i++) {
            System.out.println(i + ". " + deck.returnDiscardPile()[i].returnCardName());
        }
    }

    // EFFECTS: prints a card's number, name and ability
    private void cardToText(Card a) {
        System.out.println(a.returnCardNumber() + ". " + a.returnCardName() + "\n" + a.returnCardAbility());
    }

    // EFFECTS: prints a player's hand
    private void printPlayerHand(Player p) {
        System.out.println("\t" + p.returnPlayerName() + "'s Hand:");
        if (!(p.returnPlayerHand()[0] == null)) {
            if ((p.returnHandSize() == 2)) {
                System.out.println("\nPress A to play:");
            }
            cardToText(p.returnPlayerHand()[0]);
        }

        if (!(p.returnPlayerHand()[1] == null)) {
            if ((p.returnHandSize() == 2)) {
                System.out.println("\nPress B to play:");
            }
            cardToText(p.returnPlayerHand()[1]);
        }
    }


    // REQUIRES: One of the players is eliminated
    // EFFECTS: Prints winning player name and
    private void printEndGame() {
        if (player1.returnEliminated() == true) {
            System.out.println(player2.returnPlayerName() + " wins!");
        } else if (player2.returnEliminated() == true) {
            System.out.println(player1.returnPlayerName() + " wins!");
        }

        System.out.println("\nThe down cards were: ");
        System.out.println(deck.returnDownCards()[0].returnCardName());
        System.out.println(deck.returnDownCards()[1].returnCardName());
        System.out.println(deck.returnDownCards()[2].returnCardName());
        System.out.println(deck.returnDownCards()[3].returnCardName());
    }

    // EFFECTS: displays basic game interface
    private void displayGUI() {
        System.out.println(playingPlayer.returnPlayerName() + "'s turn : \t \t Cards remaining in deck: "
                + deck.returnDeck().size());
        System.out.println("\nPress A to: Check Rules");
        System.out.println("Press B to: Draw Card");
        System.out.println("Press C to: Check Hand");
        System.out.println("Press D to: Check Discard Pile");
        System.out.println("Press E to: End Turn");
    }

    // EFFECTS: passes to next player's turn
    private void nextTurn() {

        if ((playingPlayer.returnDrewCard() == true) && (playingPlayer.returnHandSize() == 1)) {
            for (int i = 0; i <= 20; i++) {
                System.out.println();
            }
            playingPlayer.flipDraw();
            playingPlayer.flipTurn();
            if (turnState == 1) {
                playingPlayer = player2;
                turnState = 2;
            } else if (turnState == 2) {
                turnState = 1;
                playingPlayer = player1;
            }
            if (playingPlayer.returnImmune() == true) {
                playingPlayer.flipImmune();
            }
            playingPlayer.flipTurn();
            gameState = 1;
            displayGUI();
        } else {
            System.out.println("\nPlease finish both draw and play phases before trying to end your turn.");
        }

    }

}



