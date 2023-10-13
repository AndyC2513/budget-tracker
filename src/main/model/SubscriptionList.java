package model;

import java.util.ArrayList;

// Represents a list of Subscriptions and commands that can be done to the subscriptions in the list
public class SubscriptionList {
    private ArrayList<Subscription> listofSubs;
    protected double budget;

    // Creates a new ArrayList in the constructor initial budget
    public SubscriptionList() {
        listofSubs = new ArrayList<>();
        budget = 1000;

    }

    // MODIFIES: this
    // EFFECTS: adds a Subscription to the list
    public void addSub(Subscription sub) {
        listofSubs.add(sub);
    }

    // EFFECTS: returns true and removes subscription if it is in the list
    public void removeSub(Subscription sub) {
        listofSubs.remove(sub);
    }

    // MODIFIES: sub, this
    // EFFECTS: subtracts the subscription amount from budget, sets payment status to true, and sets apparent price to 0
    public void payForSub(Subscription sub) {
        if (listofSubs.contains(sub)) {
            budget = budget - sub.getPrice();
            sub.paid = true;
            sub.apparentPrice = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds more budget to this list
    public void addBudget(double amount) {
        budget = budget + amount;
    }

    // EFFECTS: returns the size of the Subscriptions list
    public int getNumSubs() {
        return listofSubs.size();
    }

    // EFFECTS: returns the list of subs
    public ArrayList<Subscription> getListOfSubs() {
        return this.listofSubs;
    }

    // EFFECTS: returns the list's budget
    public double getBudget() {
        return budget;
    }
}