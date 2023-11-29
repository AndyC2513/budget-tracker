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

    // EFFECTS: returns a string containing associated information about subs


    // MODIFIES: this
    // EFFECTS: adds a Subscription to the entertainment list
    public void addEntSub(Subscription sub) {
        entertainment.add(sub);
        EventLog.getInstance().logEvent(new Event("Entertainment Subscription Added."));
    }

    // MODIFIES: this
    // EFFECTS: adds a Subscription to the living expense list
    public void addLivSub(Subscription sub) {
        living.add(sub);
        EventLog.getInstance().logEvent(new Event("Living Expense Subscription Added."));
    }

    // MODIFIES: this
    // EFFECTS: adds a Subscription to the academic expense list
    public void addAcSub(Subscription sub) {
        academic.add(sub);
        EventLog.getInstance().logEvent(new Event("Academic Expense Subscription Added."));
    }


    // EFFECTS: removes subscription from entertainment list
    public void removeEntSub(Subscription sub) {
        entertainment.remove(sub);
        EventLog.getInstance().logEvent(new Event("Entertainment Subscription Removed."));
    }

    // EFFECTS: removes subscription from living expense list
    public void removeLivSub(Subscription sub) {
        living.remove(sub);
        EventLog.getInstance().logEvent(new Event("Living Expense Subscription Removed."));
    }

    // EFFECTS: removes subscription from academic expense list
    public void removeAcSub(Subscription sub) {
        academic.remove(sub);
        EventLog.getInstance().logEvent(new Event("Academic Expense Subscription Removed."));
    }

    // MODIFIES: this
    // EFFECTS: subtracts the subscription amount from budget, sets payment status to true, and sets apparent price to 0
    //          if not enough fund, inform the user of their lack of funds
    public boolean payForEntSub(Subscription sub) {
        if (entertainment.contains(sub)) {
            if (entBudget >= sub.getPrice() && !sub.isPaid()) {
                entBudget = entBudget - sub.getPrice();
                sub.setPaid();
                sub.setApparentPrice();
                EventLog.getInstance().logEvent(new Event("Entertainment Subscription Paid."));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: subtracts the subscription amount from budget, sets payment status to true, and sets apparent price to 0
    //          if not enough fund, inform the user of their lack of funds
    public boolean payForLivSub(Subscription sub) {
        if (living.contains(sub)) {
            if (livBudget >= sub.getPrice() && !sub.isPaid()) {
                livBudget = livBudget - sub.getPrice();
                sub.setPaid();
                sub.setApparentPrice();
                EventLog.getInstance().logEvent(new Event("Living Expense Subscription Paid."));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: subtracts the subscription amount from budget, sets payment status to true, and sets apparent price to 0
    //          if not enough fund, inform the user of their lack of funds
    public boolean payForAcSub(Subscription sub) {
        if (academic.contains(sub)) {
            if (acBudget >= sub.getPrice() && !sub.isPaid()) {
                acBudget = acBudget - sub.getPrice();
                sub.setPaid();
                sub.setApparentPrice();
                EventLog.getInstance().logEvent(new Event("Academic Expense Subscription Paid."));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds more budget to the appropriate list
    public void addEntBudget(double amount) {
        entBudget = entBudget + amount;
        EventLog.getInstance().logEvent(new Event("Entertainment Funds added."));
    }

    // MODIFIES: this
    // EFFECTS: adds more budget to the appropriate list
    public void addLivBudget(double amount) {
        livBudget = livBudget + amount;
        EventLog.getInstance().logEvent(new Event("Living Funds added."));
    }

    // MODIFIES: this
    // EFFECTS: adds more budget to the appropriate list
    public void addAcBudget(double amount) {
        acBudget = acBudget + amount;
        EventLog.getInstance().logEvent(new Event("Academic Funds added."));
    }

    // EFFECTS: returns the size of the appropriate Subscriptions list
    public int getNumEntSubs() {
        return entertainment.size();
    }

    // EFFECTS: returns the size of the appropriate Subscriptions list
    public int getNumLivSubs() {
        return living.size();
    }

    // EFFECTS: returns the size of the appropriate Subscriptions list
    public int getNumAcSubs() {
        return academic.size();
    }

    // EFFECTS: returns the list of appropriate sub of each category
    public ArrayList<Subscription> getListOfEntSubs() {
        return this.entertainment;
    }

    // EFFECTS: returns the list of appropriate sub of each category
    public ArrayList<Subscription> getListOfLivSubs() {
        return this.living;
    }

    // EFFECTS: returns the list of appropriate sub of each category
    public ArrayList<Subscription> getListOfAcSubs() {
        return this.academic;
    }

    // EFFECTS: returns the appropriate list's budget
    public double getEntBudget() {
        return entBudget;
    }

    // EFFECTS: returns the appropriate list's budget
    public double getLivBudget() {
        return livBudget;
    }

    // EFFECTS: returns the appropriate list's budget
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