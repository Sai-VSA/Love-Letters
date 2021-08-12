package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Arrays;

    class TestDeck {
    Deck deck;
    Player player1;
    Player player2;

    @BeforeEach
    public void setUp() {
        player1 = new Player("sai");
        player2 = new Player("two");
        deck = new Deck();
        deck.setPlayers(player1, player2);
    }

    @Test
    public void testDrawCard() {
        assertEquals(12, deck.returnDeck().size());
        deck.drawCard();
        assertEquals(11, deck.returnDeck().size());
        assertFalse(deck.returnDeckState());
        for (int i = 0; i<=10; i++) {
            deck.drawCard();
        }
        assertEquals(0, deck.returnDeck().size());
        assertTrue(deck.returnDeckState());
    }

    @Test
    public void testDiscardCard() {
        Card a = deck.drawCard();
        Card b = deck.drawCard();
        assertEquals(0, deck.returnCardsInDiscard());
        deck.discardCard(a);
        assertEquals(a, deck.returnDiscardPile()[0]);
        deck.discardCard(b);
        assertEquals(b, deck.returnDiscardPile()[1]);

    }

}
