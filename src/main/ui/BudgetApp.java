package ui;

import model.Subscription;
import model.SubscriptionList;

import javax.naming.AuthenticationNotSupportedException;
import java.util.ArrayList;
import java.util.Scanner;

// Subscription budget application
public class BudgetApp {
    private ArrayList<SubscriptionList> subList;
    private Scanner input;

    //EFFECTS: instantiates a new budget app
    public BudgetApp() {
        runBudget();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBudget() {
        boolean keepGoing = true;
        String command = null;

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

        System.out.println("\nGoodbye!");
    }

    private void processCommand(String command) {
        switch (command) {
            case "e":
                SubscriptionList list = displayList(0);
                if (subList.get(0).getNumSubs() == 0) {
                    System.out.println("No subscriptions yet!");
                    break;
                }
                command = input.next();
                modifyWhich(command, list);
                break;
            case "l":
                SubscriptionList list1 = displayList(1);
                if (subList.get(1).getNumSubs() == 0) {
                    System.out.println("No subscriptions yet!");
                    break;
                }
                command = input.next();
                modifyWhich(command, list1);
                break;
            case "a":
                SubscriptionList list2 = displayList(2);
                if (subList.get(2).getNumSubs() == 0) {
                    System.out.println("No subscriptions yet!");
                    break;
                }
                command = input.next();
                modifyWhich(command, list2);
                break;
        }
    }

    private boolean isEmptySub() {
        return subList.get(0).getNumSubs() == 0;
    }

    private void modifyWhich(String command, SubscriptionList list) {
        // turn command into string, then access given index
        int i = Integer.parseInt(command);
        Subscription sub = list.getListofSubs().get(i);
        System.out.println(String.valueOf(sub.getPrice()) + " 8=====D " + sub.getName());
    }

    // MODIFIES: this
    // EFFECTS: initializes a subscription list
    private void init() {
        subList = new ArrayList<>();
        SubscriptionList entList = new SubscriptionList();
        SubscriptionList livList = new SubscriptionList();
        SubscriptionList acList = new SubscriptionList();
        entList.addSub(new Subscription("tony1", 699999.89));
        entList.addSub(new Subscription("tony", 69));
        entList.addSub(new Subscription("tony", 69));
        entList.addSub(new Subscription("tony", 69));
        entList.addSub(new Subscription("tony", 69));
        subList.add(entList);
        subList.add(livList);
        subList.add(acList);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\te -> entertainment list");
        System.out.println("\tl -> living expenses");
        System.out.println("\ta -> academic expenses");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays given list to user
    private SubscriptionList displayList(int i) {
        SubscriptionList list = this.subList.get(i);
        int j = 0;
        for (Subscription s : list.getListofSubs()) {
            System.out.println("\t" + String.valueOf(j) + " -> " + s.getName());
            j++;
        }
        return list;
    }
}
