package persistence;

import java.io.*;
import model.*;
import org.json.JSONObject;


// Represents a writer that writes JSON representation of workroom to file

public class JsonWriter {
    // Large part of methods are based on JSonSerializationDemo. Link below:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // MODIFIES: this
    // EFFECTS: writes JSON representation of game variables to file
    public void writeFile(Player p1, Player p2, Deck d) {
        JSONObject json = new JSONObject();
        json.put("player1", writePlayer(p1));
        json.put("player2", writePlayer(p2));
        json.put("deck", writeDeck(d));
        json.put("gameState", 1);
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of a player to file
    public JSONObject writePlayer(Player p) {
        JSONObject json = p.toJson();
        return json;
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of the deck to file
    public JSONObject writeDeck(Deck d) {
        JSONObject json = d.toJson();
        return json;
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}


