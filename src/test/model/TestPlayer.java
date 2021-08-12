package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {
    Player Player1;
    Player Player2;
    Card Guard;
    Card Companion;
    Card Princess;
    Deck deck;

    @BeforeEach
    public void setUp() {
        Player1 = new Player("Sai");
        Player2 = new Player("Jimmy");
        Guard = new Card("Guard", 1);
        Companion = new Card("Companion", 4);
        Princess = new Card("Princess", 8);
        deck = new Deck();
        deck.setPlayers(Player1, Player2);
        Player1.setDeck(deck);
        Player2.setDeck(deck);
    }

    @Test
    public void testAddCard() {
        Card i = deck.returnDeck().peek();
        assertEquals(0, Player1.returnHandSize());
        Player1.drawCard();
        assertEquals(i, Player1.returnPlayerHand()[0]);
        i = deck.returnDeck().peek();
        Player1.drawCard();
        assertEquals(i, Player1.returnPlayerHand()[1]);
        Player1.discardCard(Guard);
        assertEquals(1, Player1.returnHandSize());
        i = deck.returnDeck().peek();
        Player1.drawCard();
        assertEquals(i, Player1.returnPlayerHand()[0]);
        assertEquals(2, Player1.returnHandSize());
    }

    @Test
    public void testDiscardCard() {
        assertEquals(Princess, Player1.discardCard(Princess));
        assertNull(Player1.returnPlayerHand()[1]);
        assertNull(Player1.returnPlayerHand()[1]);
        assertEquals(Guard, Player1.discardCard(Guard));
        assertNull(Player1.returnPlayerHand()[0]);
    }

    @Test
    public void testEliminate() {
        Player1.setEliminated();
        assertTrue(Player1.returnEliminated());
    }

    @Test
    public void testFlipDraw() {
        Player1.flipDraw();
        assertTrue(Player1.returnDrewCard());
        Player1.flipDraw();
        assertFalse(Player1.returnDrewCard());
    }

    @Test
    public void testFlipTurn() {
        Player1.flipTurn();
        assertTrue(Player1.returnPlayerTurn());
        Player1.flipTurn();
        assertFalse(Player1.returnPlayerTurn());
    }

    @Test
    public void testFlipImmune() {
        Player1.flipImmune();
        assertTrue(Player1.returnImmune());
        Player1.flipImmune();
        assertFalse(Player1.returnImmune());
    }

    @Test
    public void testReturnPlayerName() {
        assertEquals("Sai", Player1.returnPlayerName());
        assertEquals("Jimmy", Player2.returnPlayerName());
    }

    @Test
    public void testReturnSlotWithCard() {
        Card i = deck.returnDeck().peek();
        Player1.drawCard();
        assertEquals(0, Player1.returnSlotWithCard());
        Player1.drawCard();
        Player1.discardCard(i);
        assertEquals(1, Player1.returnSlotWithCard());
    }
}


