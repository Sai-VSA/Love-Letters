package model;


import static java.lang.Character.toUpperCase;

// Code for the card interactions in LoveLetters
public class CardEffects {
    Deck deck;
    Player player1;
    Player player2;
    Player p1;
    Card c1;
    Player p2;
    Card c2;

    //EFFECTS: Instantiates CardEffect fields
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
    public String playGuard(String s) {
        int a = subGuard(s.toUpperCase());
        Player b;
        if (player1.returnPlayerTurn() == true) {
            int cardLocation = player2.returnSlotWithCard();
            b = player2;
        } else {
            int cardLocation = player1.returnSlotWithCard();
            b = player1;
        }
        int cardLocation = b.returnSlotWithCard();
        if (b.returnPlayerHand()[cardLocation].returnCardNumber() == a) {
            b.setEliminated();
            return "Your guess was correct!";
        } else {
            return "Your guess was incorrect";
        }
    }


    public int subGuard(String a) {
        if (a.equalsIgnoreCase("A")) {
            return 2;
        } else if (a.equalsIgnoreCase("B")) {
            return 3;
        } else if (a.equalsIgnoreCase("C")) {
            return 4;
        } else if (a.equalsIgnoreCase("D")) {
            return 5;
        } else if (a.equalsIgnoreCase("E")) {
            return 6;
        } else if (a.equalsIgnoreCase("F")) {
            return 7;
        } else {
            return 8;
        }
    }

    // REQUIRES: Opposing player's hand not empty
    // EFFECTS: Returns card in opposing players hand
    public String playRoyalSubject() {
        if (player1.returnPlayerTurn() == true) {
            Player a = player2;
            Card c = a.returnPlayerHand()[a.returnSlotWithCard()];
            return "Your opponents hand has: \n" + c.returnCardNumber() + ". " + c.returnCardName();
        } else {
            Player a = player1;
            Card c = a.returnPlayerHand()[a.returnSlotWithCard()];
            return "Your opponents hand has: \n" + c.returnCardNumber() + ". " + c.returnCardName();
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
                int b = player2.returnSlotWithCard();
                deck.discardCard(player2.returnPlayerHand()[b]);
                player2.drawCard();
                player2.discardCard(player2.returnPlayerHand()[b]);
            }
        } else if (player2.returnPlayerTurn() == true) {
            Player a = player1;
            Card c = a.returnPlayerHand()[a.returnSlotWithCard()];
            if (c.returnCardName() == "Princess" || c.returnCardName() == "Hero") {
                player1.setEliminated();
            } else {
                int b = player1.returnSlotWithCard();
                deck.discardCard(player1.returnPlayerHand()[b]);
                player1.drawCard();
                player1.discardCard(player1.returnPlayerHand()[b]);
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



