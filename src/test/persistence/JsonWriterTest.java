package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Deck d = new Deck();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //Pass
        }
    }

    @Test
    void testWriterBasicState() {
        try {
            Deck d1 = new Deck();
            Player p1 = new Player("player1");
            Player p2 = new Player("player2");
            d1.setPlayers(p1, p2);
            p1.setDeck(d1);
            p2.setDeck(d1);
            p1.drawCard();
            p1.drawCard();
            p2.drawCard();
            JsonWriter writer = new JsonWriter("./data/Test3");
            writer.open();
            writer.writeFile(p1, p2, d1);
            writer.close();

            JsonReader reader = new JsonReader("./data/Test3");
            p1 = reader.readP1();
            assertEquals("player1", p1.returnPlayerName());
            assertFalse(p1.returnPlayerTurn());
            assertFalse(p1.returnDrewCard());
            assertFalse(p1.returnImmune());
            assertEquals(2, p1.returnHandSize());

            p2 = reader.readP2();
            assertEquals("player2", p2.returnPlayerName());
            assertFalse(p2.returnPlayerTurn());
            assertFalse(p2.returnDrewCard());
            assertFalse(p2.returnImmune());
            assertEquals(1, p2.returnHandSize());

            Deck d2 = new Deck();
            d2.reloadDeck(reader.readDeck(), reader.readDiscard(), reader.readDown());
            assertEquals(d1.returnDeck().size(), d2.returnDeck().size());
            assertEquals(d1.returnDeck().peek().returnCardName(), d2.returnDeck().peek().returnCardName());
            assertEquals(d1.returnCardsInDiscard(), d2.returnCardsInDiscard());
            assertEquals(d1.returnDiscardPile()[0], d2.returnDiscardPile()[0]);
            assertEquals(d1.returnDownCards()[0].returnCardName(), d2.returnDownCards()[0].returnCardName());
            assertEquals(d1.returnDownCards()[3].returnCardName(), d2.returnDownCards()[3].returnCardName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }


    }
}
