package persistence;

import org.json.JSONObject;

public interface Runner {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
