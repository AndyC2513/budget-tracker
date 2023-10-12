package model;

// Represents a subscription with a name and a price
public class Subscription {
    private String name;
    private double price;
    private boolean paid;

    //Constructs a new subscription with a name, price, and pay status
    public Subscription(String name, double price) {
        this.name = name;
        this.price = price;
        this.paid = false;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public boolean isPaid() {
        return this.paid;
    }

}
