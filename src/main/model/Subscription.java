package model;

// Represents a subscription with a name, price, apparent price, and payment status
public class Subscription {
    protected String name;
    private final double price;
    protected double apparentPrice;
    protected boolean paid;

    //EFFECTS: Constructs a new subscription with a name, price, apparent price, and pay status
    public Subscription(String name, double price) {
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
}
