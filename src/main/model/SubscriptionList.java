package model;

import java.util.ArrayList;

// Represents a list of Subscriptions and commands that can be done to the subscriptions in the list
public class SubscriptionList {
    private String name;
    private ArrayList<Subscription> listofSubs;

    // Creates a new ArrayList in the constructor
    public SubscriptionList() {
        listofSubs = new ArrayList<>();
        name = "";
    }

    // MODIFIES: this
    // EFFECTS: adds a Subscription to the list
    public void addSub(Subscription sub) {
        listofSubs.add(sub);
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

    // EFFECTS: returns the list of subs
    public ArrayList<Subscription> getListofSubs() {
        return this.listofSubs;
    }

    // EFFECTS: list all the subscriptions and their name, price and payment status
    public String getSubList() {
        String retString = "";
        for (Subscription s : listofSubs) {
            retString = s.getName() + "\t" + s.getPrice() + "\t" + s.isPaid() + "\n";
        }
        return retString;
    }

}