package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCard {
    private Card Guard;
    private Card RoyalSubject;
    private Card Gossip;
    private Card Companion;
    private Card Hero;
    private Card Wizard;
    private Card Lady;
    private Card Princess;


    @BeforeEach
    public void setUp() {
        Guard = new Card("Guard", 1);
        RoyalSubject = new Card("Royal Subject", 2);
        Gossip = new Card("Gossip", 3);
        Companion = new Card("Companion", 4);
        Hero = new Card("Hero", 5);
        Wizard = new Card("Wizard", 6);
        Lady = new Card("Lady", 7);
        Princess = new Card("Princess", 8);
    }


    @Test
    public void testReturnCardName() {
        assertEquals("Guard", Guard.returnCardName());
        assertEquals("Royal Subject", RoyalSubject.returnCardName());
        assertEquals("Gossip", Gossip.returnCardName());
        assertEquals("Companion", Companion.returnCardName());
        assertEquals("Hero", Hero.returnCardName());
        assertEquals("Wizard", Wizard.returnCardName());
        assertEquals("Lady", Lady.returnCardName());
        assertEquals("Princess", Princess.returnCardName());
    }

    @Test
    public void testReturnCardNumber () {
        assertEquals(1, Guard.returnCardNumber());
        assertEquals(2, RoyalSubject.returnCardNumber());
        assertEquals(3, Gossip.returnCardNumber());
        assertEquals(4, Companion.returnCardNumber());
        assertEquals(5, Hero.returnCardNumber());
        assertEquals(6, Wizard.returnCardNumber());
        assertEquals(7, Lady.returnCardNumber());
        assertEquals(8, Princess.returnCardNumber());
    }

    @Test
    public void testReturnCardAbility () {
     assertEquals("Guess a non-guard card which you think the other player has, "
       + "if the player is holding that card, they are eliminated from the game.", Guard.returnCardAbility());
     assertEquals("Look at your opponents hand.", RoyalSubject.returnCardAbility());
     assertEquals("Compare hands with your opponent: lower hand is eliminated.", Gossip.returnCardAbility());
     assertEquals("You are immune to opponent card effects until your next turn.",
             Companion.returnCardAbility());
     assertEquals("Your opponent discards their hand and draws a new card.", Hero.returnCardAbility());
     assertEquals("Swap hands with your opponent.", Wizard.returnCardAbility());
     assertEquals("You must discard this card if you are holding either the hero"
             + "or the wizard.", Lady.returnCardAbility());
     assertEquals( "You lose if you discard this card.", Princess.returnCardAbility());
    }
    }