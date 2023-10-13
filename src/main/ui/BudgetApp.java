package ui;

import model.Subscription;
import model.SubscriptionList;

import java.util.ArrayList;
import java.util.Scanner;

// Subscription budget application
public class BudgetApp {
    private ArrayList<SubscriptionList> subList;
    private Scanner input;

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
    // EFFECT: processes user command and enters a subscription list of user's choosing
    private void processCommand(String command) {
        int index = -1;
        switch (command) {
            case "e":
                index = 0;
                break;
            case "l":
                index = 1;
                break;
            case "a":
                index = 2;
                break;
        }
        if (index != -1) {
            SubscriptionList list = displayList(index);
            if (subList.get(index).getNumSubs() == 0) {
                System.out.println("\tNo subscriptions yet!");
                System.out.println("\tWant to add a Subscription?");
                addSubList(list);
                return;
            }
            command = input.next();
            modifyWhich(command, list);
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user if they want to add a new subscription, or return to menu,
    // constructs a subscription from user input and adds it to list in the case where the list is empty
    private void addSubList(SubscriptionList list) {
        String selection = "";

        while (!(selection.equals("y") || selection.equals("n") || selection.equals("o"))) {
            System.out.println("\ty for yes");
            System.out.println("\tn for no");
            System.out.println("\to to return to menu");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        Scanner info = new Scanner(System.in);
        if (selection.equals("y")) {
            System.out.println("\tEnter Subscription name:");
            String name = info.nextLine();

            System.out.println("\tEnter Subscription price:");
            int price = info.nextInt();
            Subscription newSub =  new Subscription(name, price);
            list.addSub(newSub);
            System.out.println("\tNew Subscription added, remember to pay!");
        }
    }

    // EFFECT: turn command into int, then access given index
    private void modifyWhich(String command, SubscriptionList list) {
        int i = Integer.parseInt(command);
        Subscription sub = list.getListOfSubs().get(i);
        displaySubscription(list, sub);
    }

    // MODIFIES: this
    // EFFECTS: asks user if they want to add a new subscription, constructs a subscription
    // from user input and adds it to list in the case where the list is NOT empty
    private void askIfAddSub(SubscriptionList list) {
        System.out.println("\tWant to add a Subscription?");
        String selection = "";

        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("\ty for yes");
            System.out.println("\tn for no");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        Scanner info = new Scanner(System.in);
        if (selection.equals("y")) {
            System.out.println("\tEnter Subscription name:");
            String name = info.nextLine();

            System.out.println("\tEnter Subscription price:");
            int price = info.nextInt();
            Subscription newSub =  new Subscription(name, price);
            list.addSub(newSub);
            System.out.println("\tNew Subscription added, remember to pay!");
        }
    }

    // EFFECTS: displays the chosen subscription and provides user with some helpful interactions
    private void displaySubscription(SubscriptionList list, Subscription sub) {
        System.out.println("\tSubscription name: " + sub.getName() + "\n" + "\tSubscription price: " + "$"
                + (sub.getPrice()));
        subControl(list, sub);
    }

    // MODIFIES: this
    // EFFECTS: asks if the user wants to return to menu, remove, or pay for this subscription
    @SuppressWarnings("methodlength")
    private void subControl(SubscriptionList list, Subscription sub) {
        if (!sub.isPaid()) {
            System.out.println("\tSubscription is NOT paid!");
            System.out.println("\tr to remove");
            System.out.println("\tp to pay with budget");
            System.out.println("\to to return to menu");

            String choice = input.next().toLowerCase();

            while (!(choice.equals("r") || choice.equals("p") || choice.equals("o"))) {
                System.out.println("\tr to remove");
                System.out.println("\tp to pay with budget");
                System.out.println("\to to return to menu");
                choice = input.next();
                choice = choice.toLowerCase();
            }

            switch (choice) {
                case "r":
                    list.removeSub(sub);
                    System.out.println("\tSubscription removed!");
                    break;
                case "p":
                    if (list.getBudget() >= sub.getPrice()) {
                        list.payForSub(sub);
                        System.out.println("\tSubscription paid!");
                    } else if (list.getBudget() < sub.getPrice()) {
                        System.out.println("\tNot enough money in budget");
                    }
            }
        } else {
            System.out.println("\tSubscription is PAID");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a subscription list with entList having an entry
    private void init() {
        subList = new ArrayList<>();
        SubscriptionList entList = new SubscriptionList();
        SubscriptionList livList = new SubscriptionList();
        SubscriptionList acList = new SubscriptionList();
        entList.addSub(new Subscription("Netflix", 18.99));
        subList.add(entList);
        subList.add(livList);
        subList.add(acList);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays the menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\te -> entertainment list");
        System.out.println("\tl -> living expenses");
        System.out.println("\ta -> academic expenses");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: display the subscription list at index i
    private SubscriptionList displayList(int i) {
        SubscriptionList list = this.subList.get(i);
        askIfAddSub(list);
        askIfAddFund(list);
        int j = 0;
        for (Subscription s : list.getListOfSubs()) {
            System.out.println("\t" + (j) + " -> " + s.getName()
                    + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice());
            j++;
        }
        SubscriptionList paid = new SubscriptionList();
        for (Subscription s : list.getListOfSubs()) {
            if (!(s.isPaid())) {
                paid.addSub(s);
            }
        }
        double n = 0;
        for (Subscription s : paid.getListOfSubs()) {
            n = n + s.getApparentPrice();
        }
        System.out.println("\n" + "\tTotal amount to pay: " + "$" + n);
        System.out.println("\tBudget: " + "$" + list.getBudget());
        return list;
    }

    // MODIFIES: this
    // EFFECTS: increases budget if user wishes to, by the amount given by the user
    private void askIfAddFund(SubscriptionList list) {
        System.out.println("\tWant to add more funds to this list?");
        String selection = "";

        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("\ty for yes");
            System.out.println("\tn for no");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        Scanner info = new Scanner(System.in);
        if (selection.equals("y")) {
            System.out.println("\tEnter amount of funds:");
            double amount = info.nextInt();
            list.addBudget(amount);
            System.out.println("\tNew funds added!");
        }
    }
}
