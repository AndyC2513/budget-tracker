package ui;

import model.Subscription;
import model.SubscriptionList;

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

        System.out.println("\nExiting Program...");
    }

    // EFFECT: processes user command
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
                System.out.println("No subscriptions yet!");
                System.out.println("Want to add Subscription?");
                addSubList(list);
                return;
            }
            command = input.next();
            modifyWhich(command, list);
        }
    }

    private SubscriptionList addSubList(SubscriptionList list) {
        String selection = "";

        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("y for yes");
            System.out.println("n for no");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        Scanner info = new Scanner(System.in);
        if (selection.equals("y")) {
            System.out.println("Enter Subscription name:");
            String name = info.nextLine();

            System.out.println("Enter Subscription price:");
            int price = info.nextInt();
            Subscription newSub =  new Subscription(name, price);
            list.addSub(newSub);
            return list;
        } else {
            return null;
        }
    }

    // EFFECT: turn command (string) into int, then access given index
    private void modifyWhich(String command, SubscriptionList list) {
        int i = Integer.parseInt(command);
        Subscription sub = list.getListofSubs().get(i);
        System.out.println(sub.getName() + "\n" + "$" + String.valueOf(sub.getPrice())
                + "\n" + String.valueOf(sub.isPaid()));
        if (!sub.isPaid()) {
            System.out.println("Want to pay for subscription?");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a subscription list
    private void init() {
        subList = new ArrayList<>();
        SubscriptionList entList = new SubscriptionList();
        SubscriptionList livList = new SubscriptionList();
        SubscriptionList acList = new SubscriptionList();
        entList.addSub(new Subscription("Tony", 699999.89));
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
        int j = 0;
        for (Subscription s : list.getListofSubs()) {
            System.out.println("\t" + String.valueOf(j) + " -> " + s.getName());
            j++;
        }
        return list;
    }
}
