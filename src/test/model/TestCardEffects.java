package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TestCardEffects {
    Deck deck;
    Player player1;
    Player player2;
    Card Hero;
    Card Princess;
    Card Guard;
    Card RoyalSubject;
    Card Gossip;
    Card Companion;
    Card Wizard;
    Card Lady;
    Card c4;


    private CardEffects ce;
    Card[] hand1;
    Card[] hand2;

    @BeforeEach
    void setUp() {
        player1 = new Player("Sai");
        player2 = new Player("player2");
        deck = new Deck();
        deck.setPlayers(player1, player2);
        player1.setDeck(deck);
        player2.setDeck(deck);
        ce = new CardEffects(deck, player1, player2);
        Guard = new Card("Guard", 1);
        RoyalSubject = new Card("Royal Subject", 2);
        Gossip = new Card("Gossip", 3);
        Companion = new Card("Companion", 4);
        Hero = new Card("Hero", 5);
        Wizard = new Card("Wizard", 6);
        Lady = new Card("Lady", 7);
        Princess = new Card("Princess", 8);


        hand1 = new Card[2];
        hand2 = new Card[2];
        player1.setHand(hand1);
        player2.setHand(hand2);
    }

    @Test
    void testPlayGuardA() {
        player2.flipTurn();
        assertTrue(player2.returnPlayerTurn());
        hand1[0] = (RoyalSubject);
        hand2[0] = (RoyalSubject);
        ce.playGuard("A");
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGuardB() {
        player1.flipTurn();
        assertTrue(player1.returnPlayerTurn());
        hand1[0] = (Gossip);
        hand2[0] = (Gossip);
        ce.playGuard("B");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardC() {
        player1.flipTurn();
        hand1[0] = (Companion);
        hand2[0] = (Companion);
        ce.playGuard("C");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardD() {
        player1.flipTurn();
        hand1[0] = (Hero);
        hand2[0] = (Hero);
        ce.playGuard("G");
        assertFalse(player2.returnEliminated());
        ce.playGuard("D");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardE() {
        player2.flipTurn();
        hand1[0] = (Wizard);
        hand2[0] = (Wizard);
        ce.playGuard("E");
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGuardF() {
        player1.flipTurn();
        hand1[0] = (Lady);
        hand2[0] = (Lady);
        ce.playGuard("F");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardG() {
        player1.flipTurn();
        hand1[0] = (Princess);
        hand2[0] = (Princess);
        assertEquals("Your guess was correct!", ce.playGuard("G"));
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardH() {
        player2.flipTurn();
        hand1[0] = (Princess);
        hand2[0] = (Princess);
        assertEquals("Your guess was incorrect", ce.playGuard("A"));
        assertFalse(player1.returnEliminated());
    }

    @Test
    void testPlayGuardH1() {
        player1.flipTurn();
        hand1[0] = Companion;
        hand2[0] = Companion;
        assertEquals("Your guess was correct!", ce.playGuard("C"));
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayRoyalSubject() {
        player1.flipTurn();
        hand1[0] = (Princess);
        hand2[0] = (Princess);
        assertEquals("Your opponents hand has: \n" + 8 + ". " + "Princess"
                , ce.playRoyalSubject());

    }

    @Test
    void testPlayRoyalSubject1() {
        player2.flipTurn();
        hand1[0] = (Princess);
        hand2[0] = (Princess);
        assertEquals("Your opponents hand has: \n" + 8 + ". " + "Princess"
                , ce.playRoyalSubject());

    }

    @Test
    void testPlayGossipWin() {
        player2.flipTurn();
        hand1[0] = (Princess);
        hand2[0] = (Princess);
        assertFalse(player1.returnEliminated());
        hand1[0] = (Hero);
        ce.playGossip();
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGossipLose() {
        player1.flipTurn();
        hand1[0] = (Princess);
        hand2[0] = Princess;
        ce.playGossip();
        assertFalse(player2.returnEliminated());
        hand1[0] = Hero;
        ce.playGossip();
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayCompanion() {
        player2.flipTurn();
        ce.playCompanion();
        assertTrue(player2.returnPlayerTurn());
        assertTrue(player2.returnImmune());
        player1.flipTurn();
        ce.playCompanion();
        assertTrue(player1.returnImmune());
    }

    @Test
    void testPlayCompanion1() {
        player1.flipTurn();
        ce.playCompanion();
        assertTrue(player1.returnImmune());
        player1.flipTurn();
        player2.flipTurn();
        ce.playCompanion();
        assertTrue(player2.returnImmune());
    }

    @Test
    void testPlayHero() {
        hand1[0] = (Guard);
        hand2[0] = (Hero);
        player2.flipTurn();
        assertTrue(player2.returnPlayerTurn());
        c4 = deck.returnDeck().peek();
        ce.playHero("A");
        assertEquals(c4.returnCardName(), player1.returnPlayerHand()[player1.returnSlotWithCard()].returnCardName());
        player1.discardCard(c4);
        hand1[0] = (Princess);
        assertEquals("Princess", player1.returnPlayerHand()[player1.returnSlotWithCard()].returnCardName());
        assertFalse(player1.returnEliminated());
        ce.playHero("A");
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayHero1() {
        hand2[0] = (Guard);
        hand1[0] = (Hero);
        player1.flipTurn();
        c4 = deck.returnDeck().peek();
        ce.playHero("B");
        assertEquals(c4.returnCardName(), player2.returnPlayerHand()[player2.returnSlotWithCard()].returnCardName());
        assertFalse(player2.returnEliminated());
        hand2[0] = Princess;
        player2.setHand(hand2);
        assertFalse(player2.returnEliminated());
        assertEquals("Princess", player2.returnPlayerHand()[player2.returnSlotWithCard()].returnCardName());
        ce.playHero("B");
        assertTrue(player2.returnEliminated());
    }


    @Test
    void testPlayWizard() {
        hand1[0] = Hero;
        hand2[0] = Princess;
        assertEquals(hand2[0].returnCardName(), player2.returnPlayerHand()[0].returnCardName());
        ce.playWizard();
        assertEquals(hand2[0].returnCardName(), player1.returnPlayerHand()[0].returnCardName());

    }
}
