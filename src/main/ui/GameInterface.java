package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Represents the LoveLetters game interface and user interactions
public class GameInterface extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/saveFile.json";
    private Player player1;
    private Player player2;
    private Deck deck;
    private Player playingPlayer;
    private int turnState;
    private int gameState;
    private Scanner input;
    private CardEffects ce;
    private Card lastPlayed;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem save;
    private JMenuItem load;


    /* EFFECT: starts game
     */
    public GameInterface() throws FileNotFoundException {
        startGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void startGame() {
        String userInput = null;

        init();
        if (gameState == 0) {
            displayGUI();
            gameState = 1;
        }

        while ((!player1.returnEliminated()) && (!player2.returnEliminated())) {
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (deck.returnDeck().size() == 0) {
                lastRemaining();
            }

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


    // MODIFIES: this, player
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
        gameState = 0;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        startGui();
    }

    // MODIFIES: this
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
            System.out.println("\nPress C to go back to main.");
        } else if (command.equals("c")) {
            printPlayerHand(playingPlayer);
        } else if (command.equals("d")) {
            printDiscardPile();
            startGui();
        } else if (command.equals("e")) {
            nextTurn();
        } else if (command.equals("f")) {
            saveState();
        } else if (command.equals("g")) {
            loadState();
        } else {
            System.out.println("Please choose a valid option.");
        }
    }

    // MODIFIES: this
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
            if (playingPlayer.returnPlayerHand()[a].returnCardNumber() == 1) {
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
            System.out.println("Please choose a valid option, \ntry not adding spaces.");
        }
    }

    // MODIFIES: this
    // EFFECT: plays guard from player's hand
    private void guardState(String c) {
        ce = new CardEffects(deck, player1, player2);
        deck.discardCard(playingPlayer.discardCard(lastPlayed));

        if (valid(c)) {
            if (((turnState == 1) && (player2.returnImmune() == false))
                    || ((turnState == 2) && (player1.returnImmune() == false))) {
                System.out.println(ce.playGuard(c));
            } else {
                System.out.println("The chosen opponent was immune this turn.");
            }

            gameState = 1;
            displayGUI();
            startGui();
        } else {
            System.out.println("Please guess a valid card.");
        }
    }

    // MODIFIES: this, deck, player
    // EFFECT: plays a card from player's hand
    private void playCard(Card c) {
        ce = new CardEffects(deck, player1, player2);
        deck.discardCard(playingPlayer.discardCard(c));

        displayGUI();

        if ((turnState == 1 && (player2.returnImmune() == true))
                || (turnState == 2 && (player1.returnImmune() == true))) {
            System.out.println("The opponent was immune.");
        } else {
            System.out.println("\nYour card \"" + c.returnCardName() + "\" was played successfully.");
            if (c.returnCardName().equals("Royal Subject")) {
                System.out.println(ce.playRoyalSubject());
            } else if (c.returnCardName().equals("Gossip")) {
                System.out.println(ce.playGossip());
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

        startGui();
    }

    // MODIFIES: this, player
    // EFFECT: Draws a card from deck and adds it to player's hand
    private void drawAndReset() {
        if (turnState == 1) {
            if (player1.returnDrewCard() == false) {
                Card a = deck.returnDeck().peek();
                System.out.println("You drew the " + a.returnCardName() + ".");
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


    // MODIFIES: this
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


    // MODIFIES: this
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
        for (int i = 1; i < (c + 1); i++) {
            System.out.println(i + ". " + deck.returnDiscardPile()[i - 1].returnCardName());
        }
    }

    // EFFECTS: prints a card's number, name and ability
    private void cardToText(Card a) {
        System.out.println(a.returnCardNumber() + ". " + a.returnCardName() + "\n" + a.returnCardAbility());
    }

    // EFFECTS: prints a player's hand
    private void printPlayerHand(Player p) {
        System.out.println("\n" + p.returnPlayerName() + "'s Hand:");
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


    // EFFECTS: Prints winning player name and down cards
    private void printEndGame() {
        System.out.println("\nThe down cards were: ");
        System.out.println(deck.returnDownCards()[0].returnCardName());
        System.out.println(deck.returnDownCards()[1].returnCardName());
        System.out.println(deck.returnDownCards()[2].returnCardName());
        System.out.println(deck.returnDownCards()[3].returnCardName());

        System.out.println();
        printDiscardPile();

        System.out.println("\n" + player1.returnPlayerName() + "'s hand:");
        if (player1.returnHandSize() > 0) {
            cardToText(player1.returnPlayerHand()[player1.returnSlotWithCard()]);
        }
        System.out.println("\n" + player2.returnPlayerName() + "'s hand:");
        if (player2.returnHandSize() > 0) {
            cardToText(player2.returnPlayerHand()[player2.returnSlotWithCard()]);
        }

        if (player1.returnEliminated() == true) {
            System.out.println("\n" + player2.returnPlayerName() + " wins!");
        } else if (player2.returnEliminated() == true) {
            System.out.println();
            System.out.println(player1.returnPlayerName() + " wins!");
        } else if (deck.returnDeck().size() == 0) {
            lastRemaining();
        }
    }

    //// MODIFIES: this, Player
    // EFFECTS: Compares player wins with empty deck
    private void lastRemaining() {
        if ((player1.returnHandSize() == 0) || (player2.returnHandSize() == 0)) {
            if (player1.returnHandSize() == 0) {
                player1.setEliminated();
            } else if (player2.returnHandSize() == 0) {
                player2.setEliminated();
            }
        } else if (player1.returnHandSize() == 1 && player2.returnHandSize() == 1) {
            if (player1.returnPlayerHand()[player1.returnSlotWithCard()].returnCardNumber()
                    == player2.returnPlayerHand()[player2.returnSlotWithCard()].returnCardNumber()) {
                System.out.println("Tie game!");
            } else if (player1.returnPlayerHand()[player1.returnSlotWithCard()].returnCardNumber()
                    > player2.returnPlayerHand()[player2.returnSlotWithCard()].returnCardNumber()) {

                player2.setEliminated();
            } else {
                player1.setEliminated();
            }
        }
    }

    // EFFECTS: displays basic game interface
    private void displayGUI() {
        System.out.println();
        System.out.println(playingPlayer.returnPlayerName() + "'s turn: \t \t Cards remaining in deck: "
                + deck.returnDeck().size());
        System.out.println("\nPress A to: Check Rules");
        System.out.println("Press B to: Draw Card");
        System.out.println("Press C to: Check Hand");
        System.out.println("Press D to: Check Discard Pile");
        System.out.println("Press E to: End Turn");
        System.out.println("\nPress F to: Save As Recent Game");
        System.out.println("Press G to: Reload Previously Saved Game");
        gameState = 1;
    }

    // MODIFIES: this, Player
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

    // MODIFIES: this
    // EFFECTS: Makes other variables match loaded values
    private void saveState() {
        try {
            jsonWriter.open();
            jsonWriter.writeFile(player1, player2, deck);
            jsonWriter.close();
            System.out.println("Save Successful!");
            JOptionPane.showMessageDialog(null, "Save Successful!");
        } catch (FileNotFoundException e) {
            System.out.println("File destination not present.");
        }
    }


    // this, Deck
    // EFFECTS: Loads previously saved gameState from file
    private void loadState() {
        try {
            player1 = jsonReader.readP1();
            player2 = jsonReader.readP2();
            gameState = jsonReader.readGameState();
            deck.reloadDeck(jsonReader.readDeck(), jsonReader.readDiscard(), jsonReader.readDown());
            loadBoard();
            displayGUI();
            startGui();
            System.out.println("Load Successful!");
            JOptionPane.showMessageDialog(null, "Load Successful!");
        } catch (IOException e) {
            System.out.println("Unable to read from " + JSON_STORE);
            displayGUI();
        }
    }

    // MODIFIES: this
    // EFFECTS: Makes other variables match loaded values
    private void loadBoard() {
        if (player1.returnPlayerTurn() == true) {
            turnState = 1;
            playingPlayer = player1;
        } else {
            turnState = 2;
            playingPlayer = player2;
        }
        int discardSize = (16 - (deck.returnDeck().size() + 4 + player1.returnHandSize() + player2.returnHandSize()));

        if (discardSize >= 1) {
            lastPlayed = deck.returnDiscardPile()[discardSize - 1];
        }
    }


    // EFFECTS: Returns true if String c is a valid value for guard
    public Boolean valid(String c) {
        if (c.equalsIgnoreCase("A")
                || c.equalsIgnoreCase("B")
                || c.equalsIgnoreCase("C")
                || c.equalsIgnoreCase("D")
                || c.equalsIgnoreCase("E")
                || c.equalsIgnoreCase("F")
                || c.equalsIgnoreCase("G")) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: This
    // EFFECTS: Initializes Frame and Menu values
    public void startGui() {
        if (!(frame == null)) {
            frame.dispose();
        }
        makeFrame();
        makeMenu();
    }

    // MODIFIES: This
    // EFFECTS: Initializes Frame values
    public void makeFrame() {
        frame = new JFrame();
        frame.setTitle("Love Letters Virtual");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 800);
        makeDiscards();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(new GridLayout());


        ImageIcon frameLogo = new ImageIcon("data/Images/img_1.png");
        frame.setIconImage(frameLogo.getImage());
    }

    // REQUIRES: makeFrame called prior
    // MODIFIES: This
    // EFFECTS: Creates cards from discardPile and related operations
    public void makeDiscards() {
        DragAndDrop drag = new DragAndDrop(deck.returnDiscardPile(), (deck.returnCardsInDiscard() - 1));
        frame.add(drag);
    }


    // REQUIRES: makeFrame called prior
    // MODIFIES: This
    // EFFECTS: Initializes Menu values
    public void makeMenu() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");

        file.add(save);
        file.add(load);
        menuBar.add(file);
        frame.setJMenuBar(menuBar);
        menuBar.setVisible(true);

        save.addActionListener(this);
        load.addActionListener(this);
        frame.setVisible(true);

    }


    // MODIFIES: this
    // EFFECTS: Creates actions for menu values
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == save) {
            saveState();

        } else if (e.getSource() == load) {
            loadState();
        }

    }
}



