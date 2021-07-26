package model;


import static java.lang.Character.toUpperCase;

public class CardEffects {
    Deck deck;
    Player player1;
    Player player2;
    Player p1;
    Card c1;
    Player p2;
    Card c2;

    public CardEffects(Deck deck, Player p1, Player p2) {
        this.deck = deck;
        player1 = p1;
        player2 = p2;
        this.p1 = p1;
        c1 = null;
        this.p2 = p2;
        c2 = null;
    }

    // REQUIRES: A valid card name
    // MODIFIES: Player
    // EFFECTS: If guess is correct, eliminated guessed player
    public void playGuard(String s) {
        String a = subGuard(s.toUpperCase());
        if (player1.returnPlayerTurn() == true) {
            int cardLocation = player1.returnSlotWithCard();
            if (player2.returnPlayerHand()[cardLocation].returnCardName().equalsIgnoreCase(a)) {
                player2.setEliminated();
            } else if (player1.returnPlayerTurn() == true) {
                if (player1.returnPlayerHand()[cardLocation].returnCardName().equalsIgnoreCase(a)) {
                    player1.setEliminated();
                }
            }
        }
    }

    public String subGuard(String a) {
        if (a == "A") {
            return "Royal Subject";
        } else if (a == "B") {
            return "Gossip";
        } else if (a == "C") {
            return "Companion";
        } else if (a == "D") {
            return "Hero";
        } else if (a == "E") {
            return "Wizard";
        } else if (a == "F") {
            return "Lady";
        } else if (a == "G") {
            return "Princess";
        } else {
            return "Invalid letter";
        }
    }

    // REQUIRES: Opposing player's hand not empty
    // EFFECTS: Returns card in opposing players hand
    public String playRoyalSubject() {
        if (player1.returnPlayerTurn() == true) {
            Player a = player2;
            Card c = a.returnPlayerHand()[a.returnSlotWithCard()];
            return c.returnCardNumber() + " " + c.returnCardName() + "\n" + c.returnCardAbility();
        } else if (player2.returnPlayerTurn() == true) {
            Player a = player1;
            Card c = a.returnPlayerHand()[a.returnSlotWithCard()];
            return c.returnCardNumber() + " " + c.returnCardName() + "\n" + c.returnCardAbility();
        } else {
            return "That player is immune to other card effects this turn.";
        }

    }

    // REQUIRES: Opposing player's hand not empty
    // MODIFIES: Player
    // EFFECTS: Returns card in opposing players hand
    public String playGossip() {

        if (player1.returnPlayerTurn() == true) {
            p1 = player1;
            p2 = player2;
            c1 = p1.returnPlayerHand()[p1.returnSlotWithCard()];
            c2 = p2.returnPlayerHand()[p2.returnSlotWithCard()];
        } else if (player2.returnPlayerTurn() == true) {
            p1 = player2;
            p2 = player1;
            c1 = p1.returnPlayerHand()[p1.returnSlotWithCard()];
            c2 = p2.returnPlayerHand()[p2.returnSlotWithCard()];
        }

        if (c1.returnCardNumber() == c2.returnCardNumber()) {
            return p1.returnPlayerName() + "'s " + c1.returnCardName() + " ties with "
                    + p2.returnPlayerName() + "'s " + c2.returnCardName();
        } else {
            if (c1.returnCardNumber() > c2.returnCardNumber()) {
                p2.setEliminated();
            } else if (c1.returnCardNumber() < c2.returnCardNumber()) {
                p1.setEliminated();
            }
            return p1.returnPlayerName() + "'s " + c1.returnCardName() + " beats "
                    + p2.returnPlayerName() + "'s " + c2.returnCardName();
        }
    }


    //MODIFIES: Player
    //EFFECT: Makes player immune until their next turn
    public void playCompanion() {
        if (player1.returnPlayerTurn() == true) {
            player1.flipImmune();
        } else if (player2.returnPlayerTurn() == true) {
            player2.flipImmune();
        }
    }


    //MODIFIES: Player
    //EFFECT: Discards opposing players hand and makes them draw,
    //        Eliminates player if princess is discarded
    public void playHero() {
        if (player1.returnPlayerTurn() == true) {
            Player a = player2;
            Card c = a.returnPlayerHand()[a.returnSlotWithCard()];
            if ((c.returnCardName() == "Princess" || c.returnCardName() == "Hero")) {
                player2.setEliminated();
            } else {
                player2.discardCard(player2.returnPlayerHand()[player2.returnSlotWithCard()]);
                player2.addCard(deck.drawCard());
                deck.discardCard(player2.returnPlayerHand()[player2.returnSlotWithCard()]);
            }
        } else if (player2.returnPlayerTurn() == true) {
            Player a = player1;
            Card c = a.returnPlayerHand()[a.returnSlotWithCard()];
            if (c.returnCardName() == "Princess" || c.returnCardName() == "Hero") {
                player1.setEliminated();
            } else {
                player1.discardCard(player1.returnPlayerHand()[player1.returnSlotWithCard()]);
                deck.discardCard(player1.returnPlayerHand()[player1.returnSlotWithCard()]);
                player1.addCard(deck.drawCard());
            }
        }

    }

    // REQUIRES: Hand not empty
    // MODIFIES: Player
    //EFFECT: Swaps hands between yourself and the other player
    public void playWizard() {
        Card[] hold;
        hold = player2.returnPlayerHand();
        player2.setHand(player1.returnPlayerHand());
        player1.setHand(hold);
    }
}



