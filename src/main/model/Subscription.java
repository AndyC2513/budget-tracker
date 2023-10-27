package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a subscription with a name, price, apparent price, and payment status
public class Subscription implements Writable {
    private final String name;
    private final double price;
    private double apparentPrice;
    private boolean paid;

    //EFFECTS: Constructs a new subscription with a name, price, apparent price, and pay status
    public Subscription(String name, double price, double apparentPrice, boolean paid) {
        this.name = name;
        this.price = price;
        this.apparentPrice = price;
        this.paid = false;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPaid() {
        return paid;
    }

    // EFFECTS: returns 0 if subscription has been paid, otherwise apparent price is same as price
    public double getApparentPrice() {
        return apparentPrice;
    }

    public void setPaid() {
        paid = true;
    }

    public void setApparentPrice() {
        apparentPrice = 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("apparent", apparentPrice);
        json.put("paid", paid);
        return json;
    }
}
