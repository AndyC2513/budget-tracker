package persistence;

import org.json.JSONObject;

// transforms objects into json object
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
