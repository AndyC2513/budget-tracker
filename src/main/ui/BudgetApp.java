package ui;

import model.Subscription;
import model.SubscriptionList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

// Subscription budget application
public class BudgetApp {
    private static final String JSON_STORE = "./data/sublist.json";
    private SubscriptionList subList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the budget application
    public BudgetApp() {
        runBudget();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBudget() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equalsIgnoreCase("q")) {
                keepGoing = false;
            } else {
                processCommand(command.toLowerCase());
            }
        }

        System.out.println("\nExiting Program...");
    }

    // MODIFIES: this
    // EFFECTS: initializes a list of subscription list for user to choose from
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        subList = new SubscriptionList(0, 0, 0);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays the menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a subscription");
        System.out.println("\tr -> remove a subscription");
        System.out.println("\tp -> pay for a subscription");
        System.out.println("\tv -> view subscriptions");
        System.out.println("\tf -> add more budget");
        System.out.println("\tn -> resets subscriptions");
        System.out.println("\ts -> save subscriptions to file");
        System.out.println("\tl -> load subscriptions from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECT: processes user command and enters a subscription list of user's choosing
    private void processCommand(String command) {

        if (Objects.equals(command, "a")) {
            showAdd();
        } else if (Objects.equals(command, "r")) {
            showRemove();
        } else if (Objects.equals(command, "p")) {
            showPay();
        } else if (Objects.equals(command, "v")) {
            showView();
        } else if (Objects.equals(command, "f")) {
            showFund();
        } else if (Objects.equals(command, "s")) {
            saveSubList();
        } else if (Objects.equals(command, "l")) {
            loadSubList();
        } else if (Objects.equals(command, "n")) {
            showReset();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves the current subscription lists to file
    private void saveSubList() {
        try {
            jsonWriter.open();
            jsonWriter.write(subList);
            jsonWriter.close();
            System.out.println("Subscriptions Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads subscriptions from file
    private void loadSubList() {
        try {
            subList = jsonReader.read();
            System.out.println("Loaded subscriptions from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays the possible reset options to user
    private void showReset() {
        if (subList.getNumEntSubs() == 0 && subList.getNumLivSubs() == 0 && subList.getNumAcSubs() == 0) {
            System.out.println("\nThere are no subscriptions in all category!");
        } else {
            System.out.println("\nPlease enter category you want to reset");
            System.out.println("\te <- entertainment");
            System.out.println("\tl <- living expense");
            System.out.println("\ta <- academic expense");
            System.out.println("\tn <- reset all");
            String choice = input.next();
            if (Objects.equals(choice, "e")) {
                resetEnt();
            } else if (Objects.equals(choice, "l")) {
                resetLiv();
            } else if (Objects.equals(choice, "a")) {
                resetAc();
            } else if (Objects.equals(choice, "n")) {
                resetAll();
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resets all subscriptions from entertainment expense
    private void resetEnt() {
        if (subList.getNumEntSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            ArrayList<Subscription> ents = subList.getListOfEntSubs();
            for (Subscription s : ents) {
                s.resetPrice();
            }
            System.out.println("All entertainment subscriptions reset");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets all subscriptions from living expense
    private void resetLiv() {
        if (subList.getNumLivSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            ArrayList<Subscription> livs = subList.getListOfLivSubs();
            for (Subscription s : livs) {
                s.resetPrice();
            }
            System.out.println("All living expense subscriptions reset");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets all subscriptions from academics
    private void resetAc() {
        if (subList.getNumAcSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            ArrayList<Subscription> acs = subList.getListOfAcSubs();
            for (Subscription s : acs) {
                s.resetPrice();
            }
            System.out.println("All academic subscriptions reset");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets all subscriptions
    private void resetAll() {
        resetEnt();
        resetLiv();
        resetAc();
    }

    // EFFECTS: asks user for name and price to construct a new Subscription
    private void showAdd() {
        System.out.println("\nPlease enter subscription name");
        String name = input.next();
        System.out.println("\nPlease enter subscription price");
        double price = input.nextDouble();
        Subscription sub = new Subscription(name, price, price, false);
        askAdd(sub);
    }

    // MODIFIES: this
    // EFFECTS: adds the constructed subscription to the category of user's choosing
    private void askAdd(Subscription sub) {
        System.out.println("\nWhich category is this subscription?");
        System.out.println("\te <- entertainment");
        System.out.println("\tl <- living expense");
        System.out.println("\ta <- academic expense");
        String choice = input.next();
        if (Objects.equals(choice,"e")) {
            subList.addEntSub(sub);
            System.out.println("Subscription added, remember to pay!");
        } else if (Objects.equals(choice, "l")) {
            subList.addLivSub(sub);
            System.out.println("Subscription added, remember to pay!");
        } else if (Objects.equals(choice, "a")) {
            subList.addAcSub(sub);
            System.out.println("Subscription added, remember to pay!");
        } else {
            System.out.println("Invalid choice");
        }
    }

    // EFFECTS: asks user which category they want to remove a subscription from
    private void showRemove() {
        if (subList.getNumEntSubs() == 0 && subList.getNumLivSubs() == 0 && subList.getNumAcSubs() == 0) {
            System.out.println("\nThere are no subscriptions in all category!");
        } else {
            System.out.println("\nWhich category do you want to remove from?");
            System.out.println("\te <- entertainment");
            System.out.println("\tl <- living expense");
            System.out.println("\ta <- academic expense");
            System.out.println("\tr <- remove all");
            String choice = input.next();
            if (Objects.equals(choice, "e")) {
                showRemoveEnt();
            } else if (Objects.equals(choice, "l")) {
                showRemoveLiv();
            } else if (Objects.equals(choice, "a")) {
                showRemoveAc();
            } else if (Objects.equals(choice, "r")) {
                removeAll();
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all subscriptions from all categories
    private void removeAll() {
        for (int i = 0; i == subList.getNumEntSubs() - 1; i++) {
            Subscription sub = subList.getListOfEntSubs().get(i);
            subList.removeEntSub(sub);
        }
        for (int i = 0; i == subList.getNumLivSubs() - 1; i++) {
            Subscription sub = subList.getListOfLivSubs().get(i);
            subList.removeLivSub(sub);
        }
        for (int i = 0; i == subList.getNumAcSubs() - 1; i++) {
            Subscription sub = subList.getListOfAcSubs().get(i);
            subList.removeAcSub(sub);
        }
        System.out.println("\nAll subscriptions cleared!");
    }

    // MODIFIES: this
    // EFFECTS: asks user to choose a subscription from entertainment to remove
    private void showRemoveEnt() {
        if (subList.getNumEntSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfEntSubs()) {
                System.out.println("\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getEntBudget());
                j++;
            }
            System.out.println("\nEnter the associated integer to remove the attached subscription");
            int choice = input.nextInt();
            Subscription sub = subList.getListOfEntSubs().get(choice);
            subList.removeEntSub(sub);
            System.out.println("\nSuccessfully removed");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to choose a subscription from living to remove
    private void showRemoveLiv() {
        if (subList.getNumLivSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfLivSubs()) {
                System.out.println("\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getLivBudget());
                j++;
            }
            System.out.println("\nEnter the associated integer to remove the attached subscription");
            int choice = input.nextInt();
            Subscription sub = subList.getListOfLivSubs().get(choice);
            subList.removeLivSub(sub);
            System.out.println("\nSuccessfully removed");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to choose a subscription from academic to remove
    private void showRemoveAc() {
        if (subList.getNumAcSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfAcSubs()) {
                System.out.println("\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getAcBudget());
                j++;
            }
            System.out.println("\nEnter the associated integer to remove the attached subscription");
            int choice = input.nextInt();
            Subscription sub = subList.getListOfAcSubs().get(choice);
            subList.removeAcSub(sub);
            System.out.println("\nSuccessfully removed");
        }
    }

    // EFFECTS: asks user to choose a category to pay for a subscription in that category
    private void showPay() {
        if (subList.getNumAcSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            System.out.println("\nWhich category do you want to pay for?");
            System.out.println("\te <- entertainment");
            System.out.println("\tl <- living expense");
            System.out.println("\ta <- academic expense");
            String choice = input.next();
            if (Objects.equals(choice, "e")) {
                showPayEnt();
            } else if (Objects.equals(choice, "l")) {
                showPayLiv();
            } else if (Objects.equals(choice, "a")) {
                showPayAc();
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to pay for a subscription in their chosen category
    private void showPayEnt() {
        if (subList.getNumEntSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfEntSubs()) {
                System.out.println("\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getEntBudget());
                j++;
            }
            System.out.println("\nEnter the associated integer to pay the attached subscription");
            int choice = input.nextInt();
            Subscription sub = subList.getListOfEntSubs().get(choice);
            if (subList.payForEntSub(sub)) {
                System.out.println("\nPayment successful!");
            } else {
                System.out.println("\nNot enough fund in budget!");
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: asks user to pay for a subscription in their chosen category
    private void showPayLiv() {
        if (subList.getNumLivSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfLivSubs()) {
                System.out.println("\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getLivBudget());
                j++;
            }
            System.out.println("\nEnter the associated integer to pay the attached subscription");
            int choice = input.nextInt();
            Subscription sub = subList.getListOfLivSubs().get(choice);
            if (subList.payForLivSub(sub)) {
                System.out.println("\nPayment successful!");
            } else {
                System.out.println("\nNot enough fund in budget!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to pay for a subscription in their chosen category
    private void showPayAc() {
        if (subList.getNumAcSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfAcSubs()) {
                System.out.println("\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getAcBudget());
                j++;
            }
            System.out.println("\nEnter the associated integer to pay the attached subscription");
            int choice = input.nextInt();
            Subscription sub = subList.getListOfAcSubs().get(choice);
            if (subList.payForAcSub(sub)) {
                System.out.println("\nPayment successful!");
            } else {
                System.out.println("\nNot enough fund in budget!");
            }
        }
    }

    // EFFECTS: asks user to choose a category to view
    private void showView() {
        System.out.println("\nWhich category do you want to view?");
        System.out.println("\te <- entertainment");
        System.out.println("\tl <- living expense");
        System.out.println("\ta <- academic expense");
        System.out.println("\tv <- view all");
        String choice = input.next();
        if (Objects.equals(choice, "e")) {
            showEnt();
        } else if (Objects.equals(choice, "l")) {
            showLiv();
        } else if (Objects.equals(choice, "a")) {
            showAc();
        } else if (Objects.equals((choice), "v")) {
            showEnt();
            showLiv();
            showAc();
        } else {
            System.out.println("Invalid choice");
        }
    }

    // EFFECTS: shows user their chosen category and the associated subscriptions
    private void showEnt() {
        if (subList.getNumEntSubs() == 0) {
            System.out.println("\nEntertainment" + "\nThere are no subscriptions in this category!"
                    + "\n" + "\tBudget available: " + "$" + subList.getEntBudget());
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfEntSubs()) {
                System.out.println("\nEntertainment\n" + "\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getEntBudget());
                j++;
            }
        }
    }

    // EFFECTS: shows user their chosen category and the associated subscriptions
    private void showLiv() {
        if (subList.getNumLivSubs() == 0) {
            System.out.println("\nLiving Expense" + "\nThere are no subscriptions in this category!"
                    + "\n" + "\tBudget available: " + "$" + subList.getLivBudget());
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfLivSubs()) {
                System.out.println("\nLiving Expense\n" + "\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getLivBudget());
                j++;
            }
        }
    }

    // EFFECTS: shows user their chosen category and the associated subscriptions
    private void showAc() {
        if (subList.getNumAcSubs() == 0) {
            System.out.println("\nAcademic Expense" + "\nThere are no subscriptions in this category!"
                    + "\n" + "\tBudget available: " + "$" + subList.getAcBudget());
        } else {
            int j = 0;
            for (Subscription s : subList.getListOfAcSubs()) {
                System.out.println("\nAcademic Expense\n" + "\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getAcBudget());
                j++;
            }
        }
    }

    // EFFECTS: asks user to choose a category to add funds to
    private void showFund() {
        System.out.println("\nWhich category do you want to add funds to?");
        System.out.println("\te <- entertainment");
        System.out.println("\tl <- living expense");
        System.out.println("\ta <- academic expense");
        String choice = input.next();
        if (Objects.equals(choice,"e")) {
            showEntFunds();
        } else if (Objects.equals(choice, "l")) {
            showLivFunds();
        } else if (Objects.equals(choice, "a")) {
            showAcFunds();
        } else {
            System.out.println("Invalid choice");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user for amount of funds and adds it to the entertainment category
    private void showEntFunds() {
        System.out.println("\nPlease enter the amount of funding you wish to add");
        double fund = input.nextDouble();
        subList.addEntBudget(fund);
        System.out.println("\nFund Added!");
        System.out.println("\nYou now have a budget of " + "$" + subList.getEntBudget() + " for this category");
        if (subList.getNumEntSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            System.out.println("\nRemember to pay for subscriptions!");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user for amount of funds and adds it to the living expense category
    private void showLivFunds() {
        System.out.println("\nPlease enter the amount of funding you wish to add");
        double fund = input.nextDouble();
        subList.addLivBudget(fund);
        System.out.println("\nFund Added!");
        System.out.println("\nYou now have a budget of " + "$" + subList.getLivBudget() + " for this category");
        if (subList.getNumLivSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            System.out.println("\nRemember to pay for subscriptions!");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user for amount of funds and adds it to the academic category
    private void showAcFunds() {
        System.out.println("\nPlease enter the amount of funding you wish to add");
        double fund = input.nextDouble();
        subList.addAcBudget(fund);
        System.out.println("\nFund Added!");
        System.out.println("\nYou now have a budget of " + "$" + subList.getAcBudget() + " for this category");
        if (subList.getNumAcSubs() == 0) {
            System.out.println("\nThere are no subscriptions in this category!");
        } else {
            System.out.println("\nRemember to pay for subscriptions!");
        }
    }
}
