package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of Subscriptions and commands that can be done to the subscriptions in the list
public class SubscriptionList implements Writable {
    private ArrayList<Subscription> entertainment;
    private ArrayList<Subscription> living;
    private ArrayList<Subscription> academic;
    private double entBudget;
    private double livBudget;
    private double acBudget;

    // Creates a new ArrayList in the constructor initial budget
    public SubscriptionList(double entBudget, double livBudget, double acBudget) {
        entertainment = new ArrayList<>();
        living = new ArrayList<>();
        academic = new ArrayList<>();
        this.entBudget = entBudget;
        this.livBudget = livBudget;
        this.acBudget = acBudget;
    }

    // MODIFIES: this
    // EFFECTS: adds a Subscription to the appropriate list
    public void addEntSub(Subscription sub) {
        entertainment.add(sub);
    }

    public void addLivSub(Subscription sub) {
        living.add(sub);
    }

    public void addAcSub(Subscription sub) {
        academic.add(sub);
    }


    // EFFECTS: removes subscription from appropriate list
    public void removeEntSub(Subscription sub) {
        entertainment.remove(sub);
    }

    public void removeLivSub(Subscription sub) {
        living.remove(sub);
    }

    public void removeAcSub(Subscription sub) {
        academic.remove(sub);
    }

    // MODIFIES: sub, this
    // EFFECTS: subtracts the subscription amount from budget, sets payment status to true, and sets apparent price to 0
    //          if not enough fund, inform the user of their lack of funds
    public void payForEntSub(Subscription sub) {
        if (entertainment.contains(sub)) {
            if (entBudget >= sub.getPrice()) {
                entBudget = entBudget - sub.getPrice();
                sub.setPaid();
                sub.setApparentPrice();
                System.out.println("\nPayment successful!");
            } else {
                System.out.println("\nNot enough fund in budget!");
            }
        } else {
            System.out.println("\nSubscription doesn't exist in this category!");
        }
    }

    public void payForLivSub(Subscription sub) {
        if (living.contains(sub)) {
            if (livBudget >= sub.getPrice()) {
                livBudget = livBudget - sub.getPrice();
                sub.setPaid();
                sub.setApparentPrice();
                System.out.println("\nPayment successful!");
            } else {
                System.out.println("\nNot enough fund in budget!");
            }
        } else {
            System.out.println("\nSubscription doesn't exist in this category!");
        }
    }

    public void payForAcSub(Subscription sub) {
        if (academic.contains(sub)) {
            if (acBudget >= sub.getPrice()) {
                acBudget = acBudget - sub.getPrice();
                sub.setPaid();
                sub.setApparentPrice();
                System.out.println("\nPayment successful!");
            } else {
                System.out.println("\nNot enough fund in budget!");
            }
        } else {
            System.out.println("\nSubscription doesn't exist in this category!");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds more budget to the appropriate list
    public void addEntBudget(double amount) {
        entBudget = entBudget + amount;
    }

    public void addLivBudget(double amount) {
        livBudget = livBudget + amount;
    }

    public void addAcBudget(double amount) {
        acBudget = acBudget + amount;
    }

    // EFFECTS: returns the size of the appropriate Subscriptions list
    public int getNumEntSubs() {
        return entertainment.size();
    }

    public int getNumLivSubs() {
        return living.size();
    }

    public int getNumAcSubs() {
        return academic.size();
    }

    // EFFECTS: returns the list of appropriate sub of each category
    public ArrayList<Subscription> getListOfEntSubs() {
        return this.entertainment;
    }

    public ArrayList<Subscription> getListOfLivSubs() {
        return this.living;
    }

    public ArrayList<Subscription> getListOfAcSubs() {
        return this.academic;
    }

    // EFFECTS: returns the appropriate list's budget
    public double getEntBudget() {
        return entBudget;
    }

    public double getLivBudget() {
        return livBudget;
    }

    public double getAcBudget() {
        return acBudget;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("entBudget", entBudget);
        json.put("livBudget", livBudget);
        json.put("acBudget", acBudget);
        json.put("entertainment", entertainmentToJson());
        json.put("living", livingToJson());
        json.put("academic", academicToJson());
        return json;
    }

    private JSONArray entertainmentToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subscription s : entertainment) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    private JSONArray livingToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subscription s : living) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    private JSONArray academicToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subscription s : academic) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

}