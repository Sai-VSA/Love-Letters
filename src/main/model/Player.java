package model;

public class Player {
    private final String playerName;
    private Card[] playerHand;
    private boolean playerTurn;
    private boolean drewCard;
    private boolean eliminated;
    private boolean immune;

    /*
     * REQUIRES: playerName is a non-zero length String
     * EFFECTS: Creates a player with the given playerName
     *          with a max hand size of two.
     *      Sets all other player values to false
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.playerHand = new Card[2];
        this.playerTurn = false;
        this.drewCard = false;
        this.eliminated = false;
        this.immune = false;
    }

    /*
     * REQUIRES: Player has <=1 cards in playerHand
     * MODIFIES: this
     * EFFECTS: Adds the inputted Card to playerHand
     */
    public void addCard(Card card) {
        if (playerHand[0] == null) {
            playerHand[0] = card;
        } else if (playerHand[1] == null) {
            playerHand[1] = card;
        }
    }

    /*
     * REQUIRES: Player has >0 cards in playerHand
     * MODIFIES: this
     * EFFECTS: Removes inputted card from player hand
     */
    public Card discardCard(Card card) {
        if (playerHand[0].returnCardName().equals(card.returnCardName())) {
            playerHand[0] = null;
        } else if (playerHand[1].returnCardName().equals(card.returnCardName())) {
            playerHand[1] = null;
        }
        return card;
    }

    /* MODIFIES: this
     * EFFECTS: Flips playTurn to its opposite value
     */
    public void flipTurn() {
        playerTurn = (!playerTurn);
    }

    /* MODIFIES: this
     * EFFECTS: Flips playerTurn boolean
     */
    public void flipDraw() {
        drewCard = (!drewCard);
    }

    /* MODIFIES: this
     * EFFECTS:: Eliminates player
     */
    public void setEliminated() {
        eliminated = true;
    }

    /*
     * EFFECTSs: Returns player name
     */
    public String returnPlayerName() {
        return playerName;
    }

    /*
     * EFFECTS: Returns player hand
     */
    public Card[] returnPlayerHand() {
        return playerHand;
    }

    /*
     * EFFECTS: Returns if card has been drawn by player this turn
     */
    public boolean returnDrewCard() {
        return drewCard;
    }

    /*
     * EFFECTS: Returns if player has been eliminated
     */
    public boolean returnEliminated() {
        return eliminated;
    }

    /*
     * EFFECTS: Returns player handSize
     */
    public int returnHandSize() {

        if (playerHand[0] == null && playerHand[1] == null) {
            return 0;
        } else if (playerHand[0] == null || playerHand[1] == null) {
            return 1;
        } else {
            return 2;
        }
    }

    /*
     * EFFECTS: Return state of playerTurn
     */
    public boolean returnPlayerTurn() {
        return playerTurn;
    }

    /*
     * EFFECTS: Returns if player is immune to card effects
     */
    public boolean returnImmune() {
        return immune;
    }

    /* MODIFIES: this
     * EFFECTS: Flips player immunity to card effects
     */
    public void flipImmune() {
        this.immune = (!immune);
    }

    /* REQUIRES: Player to have a card in hand
     * EFFECTS: Returns slot with player card
     */
    public int returnSlotWithCard() {

        if (playerHand[1] == null) {
            return 0;
        } else {
            return 1;
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets player hand to a specific card
    public void setHand(Card[] c) {
        playerHand = c;
    }
}










