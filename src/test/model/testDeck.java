package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Arrays;

public class testDeck {
    Deck deck;

    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testDrawCard() {
        assertEquals(11, deck.returnDeck().size());
        deck.drawCard();
        assertEquals(10, deck.returnDeck().size());
        assertFalse(deck.returnDeckState());
        for (int i = 1; i<=9; i++) {
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
        assertEquals(a, deck.returnDiscardPile()[1]);

    }

}
