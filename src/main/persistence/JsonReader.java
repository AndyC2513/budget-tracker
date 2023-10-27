package persistence;

import model.SubscriptionList;
import model.Subscription;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Reads subscriptions from json file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads subscription list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SubscriptionList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSubscriptionList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses subscription list from JSON object and returns it
    private SubscriptionList parseSubscriptionList(JSONObject jsonObject) {
        double entBudget = jsonObject.getDouble("entBudget");
        double livBudget = jsonObject.getDouble("livBudget");
        double acBudget = jsonObject.getDouble("acBudget");
        SubscriptionList sl = new SubscriptionList(entBudget, livBudget, acBudget);
        addEntSubs(sl, jsonObject);
        addLivSubs(sl, jsonObject);
        addAcSubs(sl, jsonObject);
        return sl;
    }

    // MODIFIES: sl
    // EFFECTS: parses entertainment subscriptions from JSON object and adds them to entertainment subscription list
    private void addEntSubs(SubscriptionList sl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entertainment");
        for (Object json : jsonArray) {
            JSONObject nextSub = (JSONObject) json;
            addJsonEntSub(sl, nextSub);
        }
    }

    // MODIFIES: sl
    // EFFECTS: parses living subscription from JSON object and adds them to living expense subscription list
    private void addLivSubs(SubscriptionList sl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("living");
        for (Object json : jsonArray) {
            JSONObject nextSub = (JSONObject) json;
            addJsonLivSub(sl, nextSub);
        }
    }

    // MODIFIES: sl
    // EFFECTS: parses academic subscription from JSON object and adds them to academic subscription list
    private void addAcSubs(SubscriptionList sl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("academic");
        for (Object json : jsonArray) {
            JSONObject nextSub = (JSONObject) json;
            addJsonAcSub(sl, nextSub);
        }
    }

    // MODIFIES: sl
    // EFFECTS: parses subscription from JSON object and adds it to entertainment subscription list
    private void addJsonEntSub(SubscriptionList sl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        double apparentPrice = jsonObject.getDouble("apparent");
        boolean paid = jsonObject.getBoolean("paid");
        Subscription sub = new Subscription(name, price, apparentPrice, paid);
        sl.addEntSub(sub);
    }

    // MODIFIES: sl
    // EFFECTS: parses subscription from JSON object and adds it to subscription list
    private void addJsonLivSub(SubscriptionList sl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        double apparentPrice = jsonObject.getDouble("apparent");
        boolean paid = jsonObject.getBoolean("paid");
        Subscription sub = new Subscription(name, price, apparentPrice, paid);
        sl.addLivSub(sub);
    }

    // MODIFIES: sl
    // EFFECTS: parses subscription from JSON object and adds it to subscription list
    private void addJsonAcSub(SubscriptionList sl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        double apparentPrice = jsonObject.getDouble("apparent");
        boolean paid = jsonObject.getBoolean("paid");
        Subscription sub = new Subscription(name, price, apparentPrice, paid);
        sl.addAcSub(sub);
    }
}
