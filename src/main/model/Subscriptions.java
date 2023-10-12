package model;

import java.util.ArrayList;

// Represents a list of Subscriptions and commands that can be done to the subscriptions in the list
public class Subscriptions {
    private ArrayList<Subscription> listofSubs;

    // Creates a new ArrayList in the constructor
    public Subscriptions() {
        listofSubs = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: returns true and adds a Subscription to the list, else return false
    public boolean addSub(Subscription sub) {
        if (listofSubs.contains(sub)) {
            return false;
        } else {
            listofSubs.add(sub);
            return true;
        }
    }

    // EFFECTS: returns true and removes subscription if it is in the list
    public boolean removeSub(Subscription sub) {
        if (listofSubs.contains(sub)) {
            listofSubs.remove(sub);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns the size of the Subscriptions list
    public int getNumSubs() {
        return listofSubs.size();
    }

    // EFFECTS: list all the subscriptions and their name, price and payment status
    public String getSubList() {
        return null; // stub
    }

}