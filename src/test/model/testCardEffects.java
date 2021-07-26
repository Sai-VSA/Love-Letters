package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class testCardEffects {
    Deck deck;
    Player player1;
    Player player2;
    Card c1;
    Card c2;
    Card c3;
    Card c4;
    private CardEffects ce;
    Card[] hand1;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        player1 = new Player("Sai");
        player2 = new Player("player2");
        ce = new CardEffects(deck, player1, player2);
        c1 = new Card("Hero", 5);
        c2 = new Card("Princess", 8);
        c3 = new Card("Guard", 1);

        hand1 = new Card[2];
    }

    @Test
    void testPlayGuard() {
        player1.flipTurn();
        player1.addCard(c1);
        player2.addCard(c1);
        ce.playGuard("Princess");
        assertFalse(player2.returnEliminated());
        ce.playGuard("Hero");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayRoyalSubject() {
        player1.flipTurn();
        player2.addCard(c1);
        player1.addCard(c2);
        assertEquals(5 + " " + "Hero" + "\n" + "Your opponent discards their hand and draws a new card."
                , ce.playRoyalSubject());

    }

    @Test
    void testPlayRoyalSubject2() {
        player2.flipTurn();
        player1.addCard(c1);
        player2.addCard(c2);
        assertEquals(5 + " " + "Hero" + "\n" + "Your opponent discards their hand and draws a new card."
                , ce.playRoyalSubject());

    }


    @Test
     void testPlayGossip() {
        player1.flipTurn();
        player1.addCard(c1);
        player2.addCard(c2);
        ce.playGossip();
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGossip1() {
        player1.flipTurn();
        player1.addCard(c2);
        player2.addCard(c2);
        assertFalse(player2.returnEliminated());
        player2.discardCard(c2);
        player2.addCard(c1);
        ce.playGossip();
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayCompanion() {
        player2.flipTurn();
    ce.playCompanion();
    assertTrue(player2.returnImmune());
        player1.flipTurn();
        ce.playCompanion();
        assertTrue(player1.returnImmune());
    }

    @Test
     void testPlayHero() {
        player1.addCard(c3);
        player2.addCard(c1);
        player2.flipTurn();
        c4 = deck.returnDeck().peek();
        ce.playHero();
        assertEquals(c4.returnCardName(), player1.returnPlayerHand()[player1.returnSlotWithCard()].returnCardName());
        player1.discardCard(c4);
        player1.addCard(c2);
        ce.playHero();
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayHero1() {
        player2.addCard(c3);
        player1.addCard(c1);
        player1.flipTurn();
        c4 = deck.returnDeck().peek();
        ce.playHero();
        assertEquals(c4.returnCardName(), player2.returnPlayerHand()[player2.returnSlotWithCard()].returnCardName());
        player2.discardCard(c4);
        player2.addCard(c2);
        ce.playHero();
        assertTrue(player2.returnEliminated());
    }

     @Test
     void testPlayWizard() {
        hand1[0] = c1;
        player1.addCard(c2);
        player2.addCard(c1);
        assertEquals(hand1[0], player2.returnPlayerHand()[0]);
        ce.playWizard();
         assertEquals(hand1[0], player1.returnPlayerHand()[0]);


    }

    @Test
    void testPlayWizard1() {
        hand1[0] = c2;
        player1.addCard(c2);
        player2.addCard(c1);
        assertEquals(hand1[0], player1.returnPlayerHand()[0]);
        ce.playWizard();
        assertEquals(hand1[0], player2.returnPlayerHand()[0]);


    }


}
