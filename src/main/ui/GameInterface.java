package ui;

import model.*;

import java.util.Arrays;
import java.util.Scanner;


public class GameInterface {

    private Player player1;
    private Player player2;
    private Deck deck;
    private Player playingPlayer;
    private int turnState;
    private int gameState;
    private Scanner input;


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

        while (player1.returnEliminated() && player2.returnEliminated()) {
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if(gameState == 1) {
                process1(userInput);
            } else if (gameState == 2) {
                //process2(userInput);
            } else if (gameState == 3) {
                //process3(userInput);
            } else if (gameState == 4) {
               // process4(userInput);
            }
        }

        printEndGame();
    }

    // EFFECTS: initializes the players and scanner
    private void init() {
        Scanner userName = new Scanner(System.in);
        player1 = new Player(input.nextLine());
        player2 = new Player(input.nextLine());
        deck = new Deck();
        gameState = 1;
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void process1(String command) {
        if (command.equals(null)) {
            displayGUI();
        } else if (command.equals("a")) {
            displayRules();
        } else if (command.equals("b")) {
            //drawAndReset();
        } else if (command.equals("c")) {
            showOwnHand();
        } else if (command.equals("d")) {
           // checkDiscard();
        } else if (command.equals("e")) {
           // nextTurn();
        } else {
            System.out.println("Please choose a valid option.");
        }
    }

    // EFFECTS: displays rules and list of cards to user
    private void displayRules() {
        System.out.println("\n Set Up: The game starts with a pre-shuffled deck on a random players turn./");
        System.out.println("\t Goal: Either eliminate all other players or survive till the end.");
        System.out.println("\t If more than one player is alive when the deck runs out, "
                + "compare hand sizes. The player with the larger hand wins.");
        System.out.println("\t The players each begin their turn with one card in hand,"
                + "\n they then draw and player one card, before passing to the next players turn.");
        System.out.println("\t Special Rule: If you discard a hero with hero, you win the game!");
        System.out.println("\t Press A to see list of Cards, Press B to go back");
        gameState = 2;
    }

    // EFFECTS: Print game cards and their effects
    private void displayCards() {
        System.out.println("\t The list of cards, along with their number, name(quantity) and ability are:");
        System.out.println("\t 1. Guard(5): Guess the opposing player's card, "
                + "\n they are eliminated if you guess right.");
        System.out.println("\t 2. Royal Subject(2): See the opponent's hand.");
        System.out.println("\t 3. Gossip(3): Compare hands with the opponents, "
                + "\n lower hand is eliminated.");
        System.out.println("\t 4. Companion(2): You are immune to card effects until your next turn.");
        System.out.println("\t 5. Hero(2): The opponent discards their hand and then draws a card.");
        System.out.println("\t 6. Wizard(1): Swap hands with your opponent");
        System.out.println("\t 7. Lady(1): You must discard the Lady if you are holding Wizard or Hero.");
        System.out.println("\t 8. Princess(1): You lose if you discard this card.");
        System.out.println("\t Press A to see Rules, Press B to go back");
    }

    // EFFECTS: Shows hand of player during player's turn
    private void showOwnHand() {
        if (turnState == 1) {
            printPlayerHand(player1);
        } else if (turnState == 2) {
            printPlayerHand(player2);
        }
        System.out.println("\t Press C to return");
    }

    // EFFECTS: Print game cards and their effects
    private void printDiscardPile() {
        int c = (deck.returnCardsInDiscard());
        System.out.println("The discard pile contains:");
        for (int i = 0; i < c; i++) {
            System.out.println("\n1. " + deck.returnDiscardPile()[i].returnCardName());
        }
        System.out.println("\n Press B to go back");
    }

    // EFFECTS: prints a card's number, name and ability
    private void cardToText(Card a) {
        System.out.println("\n" + a.returnCardNumber() + a.returnCardName() + "\n" + a.returnCardAbility());
    }

    // EFFECTS: prints a player's hand
    private void printPlayerHand(Player p) {
        System.out.println("\t" + p.returnPlayerName() + "'s Hand:");
        if (!(p.returnPlayerHand()[0] == null)) {
            if ((p.returnHandSize() == 2)) {
                System.out.println("\t Press A to play:");
            }
            cardToText(p.returnPlayerHand()[0]);
        }
        cardToText(p.returnPlayerHand()[0]);
        if (!(p.returnPlayerHand()[1] == null)) {
            if ((p.returnHandSize() == 2)) {
                System.out.println("\t Press B to play:");
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

        System.out.println("\n The down cards were: ");
        System.out.println(deck.returnDownCards()[0].returnCardName());
        System.out.println(deck.returnDownCards()[1].returnCardName());
        System.out.println(deck.returnDownCards()[2].returnCardName());
        System.out.println(deck.returnDownCards()[3].returnCardName());
    }

    // EFFECTS: displays basic game interface
    private void displayGUI() {
        System.out.println("\nPress A to: Check Rules");
        System.out.println("\nPress B to: Draw Card");
        System.out.println("\nPress C to: Check Hand");
        System.out.println("\nPress D to: Check Discard Pile");
        System.out.println("\nPress E to: End Turn");
    }


}

