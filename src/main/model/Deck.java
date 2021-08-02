package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Runner;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Arrays;

// Represents the shuffled deck, discarded cards and cards excluded from game
public class Deck implements Runner {
    private Queue<Card> deck;
    private Card[] unshuffledDeck;
    private Card[] discardPile;
    private Card[] downCards;
    private boolean empty;

    Card guard1;
    Card guard2;
    Card guard3;
    Card guard4;
    Card guard5;
    Card royalSubject1;
    Card royalSubject2;
    Card gossip1;
    Card gossip2;
    Card companion1;
    Card companion2;
    Card hero1;
    Card hero2;
    Card wizard;
    Card lady;
    Card princess;

    /* EFFECTS: Creates cards and shuffles deck with them
     */
    public Deck() {
        unshuffledDeck = new Card[16];
        deck = new LinkedList<Card>();
        downCards = new Card[4];
        discardPile = new Card[11];
        createCards();
        createUnshuffled();
        randomizeCards();
    }

    public void createCards() {
        guard1 = new Card("Guard", 1);
        guard2 = new Card("Guard", 1);
        guard3 = new Card("Guard", 1);
        guard4 = new Card("Guard", 1);
        guard5 = new Card("Guard", 1);
        royalSubject1 = new Card("Royal Subject", 2);
        royalSubject2 = new Card("Royal Subject", 2);
        gossip1 = new Card("Gossip", 3);
        gossip2 = new Card("Gossip", 3);
        companion1 = new Card("Companion", 4);
        companion2 = new Card("Companion", 4);
        hero1 = new Card("Hero", 5);
        hero2 = new Card("Hero", 5);
        wizard = new Card("Wizard", 6);
        lady = new Card("Lady", 7);
        princess = new Card("Princess", 8);
    }

    /* MODIFIES: this
     * EFFECTS: creates an unshuffled deck
     */
    public void createUnshuffled() {
        unshuffledDeck[0] = guard1;
        unshuffledDeck[1] = (guard2);
        unshuffledDeck[2] = (guard3);
        unshuffledDeck[3] = (guard4);
        unshuffledDeck[4] = (guard5);
        unshuffledDeck[5] = (royalSubject1);
        unshuffledDeck[6] = (royalSubject2);
        unshuffledDeck[7] = (gossip1);
        unshuffledDeck[8] = (gossip2);
        unshuffledDeck[9] = (companion1);
        unshuffledDeck[10] = (companion2);
        unshuffledDeck[11] = (hero1);
        unshuffledDeck[12] = (hero2);
        unshuffledDeck[13] = (wizard);
        unshuffledDeck[14] = (lady);
        unshuffledDeck[15] = (princess);
    }

    /* MODIFIES: this
     * EFFECTS: Creates a shuffled deck
     */
    public void randomizeCards() {
        Random random = new Random();
        int rand;
        int num = 0;
        int down = 0;

        Card[] accessedArray = new Card[16];

        while (deck.size() < 12) {
            rand = random.nextInt(16);
            if (!(Arrays.asList(accessedArray).contains(unshuffledDeck[rand]))) {
                accessedArray[num] = unshuffledDeck[rand];
                deck.add(unshuffledDeck[rand]);
                num++;
            }
            while ((deck.size() > 10) && (Arrays.asList(downCards).contains(null))) {
                rand = random.nextInt(16);
                if (!(Arrays.asList(accessedArray).contains(unshuffledDeck[rand]))) {
                    accessedArray[num] = unshuffledDeck[rand];
                    downCards[down] = unshuffledDeck[rand];
                    num++;
                    down++;
                }
            }
        }
    }

    /*
     * EFFECTS: Returns deck
     */
    public Queue<Card> returnDeck() {
        return deck;
    }

    /* REQUIRES: Deck is not empty
     * MODIFIES: this
     * EFFECTS: Draws a card from Deck
     * Sets deck to empty if the draw makes deck empty
     */
    public Card drawCard() {
        if (deck.size() == 1) {
            empty = true;
        }
        Card i = deck.peek();
        deck.remove();

        return i;
    }

    /* REQUIRES: A pre-defined Card
     * MODIFIES: this
     * EFFECTS: Adds a card to discardPile
     */
    public Card discardCard(Card card) {
        discardPile[returnCardsInDiscard()] = card;

        return card;
    }

    /*
     * EFFECTS: Returns amount of cards discarded
     */
    public int returnCardsInDiscard() {
        int n = 0;

        for (int i = 0; i <= 9; i++) {
            if (!(discardPile[i] == null)) {
                n++;
            }
        }
        return n;
    }

    /*
     * EFFECTS: Returns array of discarded cards
     */
    public Card[] returnDiscardPile() {
        return discardPile;
    }

    /*
     * EFFECTS: Returns true if deck is empty, false otherwise
     */
    public boolean returnDeckState() {
        return empty;
    }

    /*
     * EFFECTS: Returns downCards in the deck
     */
    public Card[] returnDownCards() {
        return downCards;
    }

    /* MODIFIES: this
     * EFFECTS: Reloads deck with new values
     */
    public void reloadDeck(Queue<Card> deck, Card[] discardPile, Card[] downCards) {
        this.deck = deck;
        this.discardPile = discardPile;
        this.downCards = downCards;

        if (deck.size() == 0) {
            this.empty = true;
        }
    }

    //REQUIRES: UI already run
    //MODIFIES: data
    //EFFECTS: Converts Deck information for JSON readability
    @Override
    public JSONObject toJson() {
        int num = 0;
        JSONObject deckMain = new JSONObject();
        JSONArray deckArray = new JSONArray();
        JSONArray discardArray = new JSONArray();
        JSONArray downArray = new JSONArray();

        for (Card c : deck) {
            JSONObject a = new JSONObject();
            a.put("cardName", c.returnCardName());
            a.put("cardNumber", c.returnCardNumber());
            deckArray.put(num, a);
            num++;
        }

        toJsonArray(discardPile, returnCardsInDiscard(), discardArray);
        toJsonArray(downCards, 4, downArray);

        deckMain.put("deck", deckArray);
        deckMain.put("discardPile", discardArray);
        deckMain.put("downCards", downArray);

        return deckMain;
    }

    //REQUIRES: UI already run
    //EFFECTS: Converts Array information for JSON readability
    public void toJsonArray(Card[] c, int num, JSONArray b) {
        for (int i = 0; i < num; i++) {
            JSONObject a = new JSONObject();
            a.put("cardName", c[i].returnCardName());
            a.put("cardNumber", c[i].returnCardNumber());
            b.put(i, a);
        }
    }
}
///
