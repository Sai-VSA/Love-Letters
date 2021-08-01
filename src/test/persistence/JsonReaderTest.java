package persistence;
import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    private Player p1;
    private Player p2;
    private Deck d = new Deck();
    private Queue<Card> deck;
    private Card[] discardPile;
    private Card[] downCards;

    @Test
    public void test1() throws IOException {
        JsonReader reader = new JsonReader("./data/Test1");

        p1 = reader.readP1();
        assertEquals("player1", p1.returnPlayerName());
        assertTrue(p1.returnPlayerTurn());
        assertTrue(p1.returnDrewCard());
        assertFalse(p1.returnImmune());
        assertEquals(2, p1.returnHandSize());

        p2 = reader.readP2();
        assertEquals("player2", p2.returnPlayerName());
        assertFalse(p2.returnPlayerTurn());
        assertFalse(p2.returnDrewCard());
        assertTrue(p2.returnImmune());
        assertEquals(1, p2.returnHandSize());

        assertEquals(1, reader.readGameState());
        assertEquals(11, reader.readDiscard().length);
        assertEquals("Companion", reader.readDiscard()[0].returnCardName());
        assertNull(reader.readDiscard()[3]);
        assertEquals(4, reader.readDown().length);
        assertEquals("Guard", reader.readDown()[0].returnCardName());

        assertEquals(7, reader.readDeck().size());
    }

    @Test
    public void deckTest() {
        JsonReader reader = new JsonReader("./data/Test1");
        try {
            d.reloadDeck(reader.readDeck(), reader.readDiscard(), reader.readDown());
        } catch (IOException e) {
            System.out.println("Exception detected.");
        }
    }
}
