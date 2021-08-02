package model;

import org.json.JSONObject;

import java.lang.*;

// Represents a card in Love Letters
public class Card {
    private String cardName;
    private int cardNumber;
    private String cardAbility;

    /*
     * REQUIRES: cardName and cardNumber should be from a Love Letter card
     * EFFECTS: Creates the Love Letter card linked with the given information.
     *          cardName is cardName and cardNumber is cardNumber
     *          The cardAbility is then set using cardName
     */
    public Card(String cardName, int cardNumber) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        if (cardName.equals("Guard")) {
            this.cardAbility = "Guess a non-guard card which you think the other player has, "
                    + "if the player is holding that card, they are eliminated from the game.";
        } else if (cardName.equals("Royal Subject")) {
            this.cardAbility = "Look at your opponents hand.";
        } else if (cardName.equals("Gossip")) {
            this.cardAbility = "Compare hands with your opponent: lower hand is eliminated.";
        } else if (cardName.equals("Companion")) {
            this.cardAbility = "You are immune to opponent card effects until your next turn.";
        } else if (cardName.equals("Hero")) {
            this.cardAbility = "Your opponent discards their hand and draws a new card.";
        } else if (cardName.equals("Wizard")) {
            this.cardAbility = "Swap hands with your opponent.";
        } else if (cardName.equals("Lady")) {
            this.cardAbility = "You must discard this card if you are holding either the hero"
                    + " or the wizard.";
        } else if (cardName.equals("Princess")) {
            this.cardAbility = "You lose if you discard this card.";
        }
    }


    /*
     * EFFECTS: returns name of a card
     */
    public String returnCardName() {
        return this.cardName;
    }

    /*
     * EFFECTS: returns number of a card
     */
    public int returnCardNumber() {
        return this.cardNumber;
    }

    /*
     * EFFECTS: returns ability of a card
     */
    public String returnCardAbility() {
        return this.cardAbility;
    }

}
