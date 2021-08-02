package persistence;

import model.*;
import ui.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads Game information from JSON data stored in file
public class JsonReader {
    // Large part of methods are based on JSonSerializationDemo. Link below:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads player1 state from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player readP1() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject.getJSONObject("player1"));
    }

    // EFFECTS: reads player2 state from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player readP2() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject.getJSONObject("player2"));
    }

    // EFFECTS: reads gameState from file and returns it;
    // throws IOException if an error occurs reading data from file
    public int readGameState() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getInt("gameState");
    }

    // EFFECTS: reads discardPile from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Card[] readDiscard() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDiscard(jsonObject.getJSONObject("deck").getJSONArray("discardPile"), 11);
    }

    // EFFECTS: reads downCards from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Card[] readDown() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDiscard(jsonObject.getJSONObject("deck").getJSONArray("downCards"), 4);
    }

    // EFFECTS: reads deck from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Queue<Card> readDeck() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDeck(jsonObject.getJSONObject("deck").getJSONArray("deck"));
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Player from JSON array and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        String playerName = jsonObject.getString("name");
        Player player = new Player(playerName);
        Card[] playerHand = parseHand(jsonObject.getJSONArray("playerHand"));
        if (!(player.returnPlayerTurn() == jsonObject.getBoolean("playerTurn"))) {
            player.flipTurn();
        }
        if (!(player.returnImmune() == jsonObject.getBoolean("immune"))) {
            player.flipImmune();
        }
        if (!(player.returnDrewCard() == jsonObject.getBoolean("drewCard"))) {
            player.flipDraw();
        }
        player.setHand(playerHand);

        return player;
    }

    // EFFECTS: parses playerHand from JSON object and returns it
    private Card[] parseHand(JSONArray jsonObject) {
        int a = 0;
        Card[] c = new Card[2];
        for (Object json : jsonObject) {
            Card c1 = parseCard((JSONObject) json);
            c[a] = c1;
            a++;
        }
        return c;
    }

    // EFFECTS: parses Card from JSON object and returns it
    private Card parseCard(JSONObject json) {
        Card a = new Card(json.getString("cardName"), json.getInt("cardNumber"));
        return a;
    }

    // EFFECTS: parses discardPile from JSON object and returns it
    public Card[] parseDiscard(JSONArray jsonObject, int num) {
        int a = 0;
        Card[] c = new Card[num];
        for (Object json : jsonObject) {
            Card c1 = parseCard((JSONObject) json);
            c[a] = c1;
            a++;
        }
        return c;
    }

    // EFFECTS: parses Deck from JSON object and returns it
    public Queue<Card> parseDeck(JSONArray jsonObject) {

        Queue<Card> c = new LinkedList<Card>();
        for (Object json : jsonObject) {
            Card c1 = parseCard((JSONObject) json);
            c.add(c1);
        }
        return c;
    }


}
