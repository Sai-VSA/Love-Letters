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

    @BeforeEach
    void setUp() {
        deck = new Deck();
        player1 = new Player("Sai");
        player2 = new Player("player2");
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
    }

    @Test
    void testPlayGuardA() {
        player2.flipTurn();
        assertTrue(player2.returnPlayerTurn());
        player1.addCard(RoyalSubject);
        ce.playGuard("A");
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGuardB() {
        player1.flipTurn();
        assertTrue(player1.returnPlayerTurn());
        player2.addCard(Gossip);
        ce.playGuard("B");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardC() {
        player1.flipTurn();
        player2.addCard(Companion);
        ce.playGuard("C");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardD() {
        player1.flipTurn();
        player1.addCard(Hero);
        player2.addCard(Hero);
        ce.playGuard("G");
        assertFalse(player2.returnEliminated());
        ce.playGuard("D");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardE() {
        player2.flipTurn();
        player1.addCard(Wizard);
        ce.playGuard("E");
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGuardF() {
        player1.flipTurn();
        player2.addCard(Lady);
        ce.playGuard("F");
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardG() {
        player1.flipTurn();
        player2.addCard(Princess);
        assertEquals("Your guess was correct!", ce.playGuard("G"));
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayGuardH() {
        player2.flipTurn();
        player1.addCard(Princess);
        assertEquals("Your guess was incorrect", ce.playGuard("A"));
        assertFalse(player1.returnEliminated());
    }

    @Test
    void testPlayGuardH1() {
        player1.flipTurn();
        player2.addCard(Companion);
        assertEquals("Your guess was correct!", ce.playGuard("C"));
        assertTrue(player2.returnEliminated());
    }

    @Test
    void testPlayRoyalSubject() {
        player1.flipTurn();
        player2.addCard(Princess);
        player1.addCard(Hero);
        assertEquals("Your opponents hand has: \n" + 8 + ". " + "Princess"
                , ce.playRoyalSubject());

    }

    @Test
    void testPlayRoyalSubject1() {
        player2.flipTurn();
        player1.addCard(Princess);
        player2.addCard(Hero);
        assertEquals("Your opponents hand has: \n" + 8 + ". " + "Princess"
                , ce.playRoyalSubject());

    }

    @Test
    void testPlayGossipWin() {
        player2.flipTurn();
        player2.addCard(Princess);
        player1.addCard(Princess);
        assertFalse(player1.returnEliminated());
        player1.discardCard(Princess);
        player1.addCard(Hero);
        ce.playGossip();
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGossipLose() {
        player1.flipTurn();
        player1.addCard(Princess);
        player2.addCard(Princess);
        ce.playGossip();
        assertFalse(player2.returnEliminated());
        player1.discardCard(Princess);
        player1.addCard(Hero);
        ce.playGossip();
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayGossipTie() {
        player1.flipTurn();
        player1.addCard(Princess);
        player2.addCard(Princess);
        assertFalse(player2.returnEliminated());
        player2.discardCard(Princess);
        player2.addCard(Hero);
        ce.playGossip();
        assertTrue(player2.returnEliminated());
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
        player1.addCard(Guard);
        player2.addCard(Hero);
        player2.flipTurn();
        assertTrue(player2.returnPlayerTurn());
        c4 = deck.returnDeck().peek();
        ce.playHero();
        assertEquals(c4.returnCardName(), player1.returnPlayerHand()[player1.returnSlotWithCard()].returnCardName());
        player1.discardCard(c4);
        player1.addCard(Princess);
        assertEquals("Princess", player1.returnPlayerHand()[player1.returnSlotWithCard()].returnCardName());
        assertFalse(player1.returnEliminated());
        ce.playHero();
        assertTrue(player1.returnEliminated());
    }

    @Test
    void testPlayHero1() {
        player2.addCard(Guard);
        player1.addCard(Hero);
        player1.flipTurn();
        c4 = deck.returnDeck().peek();
        ce.playHero();
        assertEquals(c4.returnCardName(), player2.returnPlayerHand()[player2.returnSlotWithCard()].returnCardName());
        player2.discardCard(c4);
        player2.addCard(Princess);
        assertEquals("Princess", player2.returnPlayerHand()[player2.returnSlotWithCard()].returnCardName());
        assertFalse(player2.returnEliminated());
        ce.playHero();
        assertTrue(player2.returnEliminated());
    }


    @Test
    void testPlayWizard() {
        hand1[0] = Hero;
        player1.addCard(Princess);
        player2.addCard(Hero);
        assertEquals(hand1[0], player2.returnPlayerHand()[0]);
        ce.playWizard();
        assertEquals(hand1[0], player1.returnPlayerHand()[0]);


    }

    @Test
    void testPlayWizard1() {
        hand1[0] = Princess;
        player1.addCard(Princess);
        player2.addCard(Hero);
        assertEquals(hand1[0], player1.returnPlayerHand()[0]);
        ce.playWizard();
        assertEquals(hand1[0], player2.returnPlayerHand()[0]);


    }


}
