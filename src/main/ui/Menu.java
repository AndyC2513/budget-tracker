package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Menu extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/sublist.json";
    private static final String addString = "Add Subscription";
    private static final String removeString = "Remove Subscription";
    private static final String viewString = "View Subscriptions";
    private static final String payString = "Pay Subscription(s)";
    private static final String fundString = "Add Funds";
    private static final String saveString = "Save";
    private static final String loadString = "Load from Saves";

    private static final String addEntString = "Entertainment";
    private static final String addLivString = "Living Expense";
    private static final String addAcString = "Academic Expense";
    private static final String confirmString = "Add to Subscriptions";
    private static final String returnString = "Return to Main Menu";

    private static final String removeEntString = "Remove from Entertainment";
    private static final String removeLivString = "Remove from Living Expense";
    private static final String removeAcString = "Remove from Academic Expense";

    private static final String enterEntString = "Remove Sub Associated";

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 500;

    private SubscriptionList subList;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JPanel mainPanel;
    private JLabel mainLabel;
    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton payButton;
    private JButton fundButton;
    private JButton saveButton;
    private JButton loadButton;

    private JPanel addPanel;
    private JLabel name;
    private JLabel price;
    private JTextField text1;
    private JTextField text2;
    private JButton confirmButton;

    private JPanel removePanel;
    private JLabel categoryLabel;
    private JButton removeEntButton;
    private JButton removeLivButton;
    private JButton removeAcButton;

    private JPanel removeEntPanel;
    private JLabel removeEntLabel;
    private JLabel removeEntSubs;
    private JLabel removeEntInstruct;
    private JTextField removeEntText;
    private JButton removeEntChoice;

    private String entSubs;
    private String livSubs;
    private String acSubs;

    private JPanel choosePanel;
    private JLabel chooseLabel;
    private JButton entButton;
    private JButton livButton;
    private JButton acButton;

    // EFFECTS: Makes a new JFrame
    public Menu() {
        super("Budget App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        mainPanel = new JPanel();
        mainLabel = new JLabel("Choose an Option");
        Icon mainIcon = new ImageIcon("./data/Stonk.jpg");
        JLabel iconLabel = new JLabel(mainIcon);
        iconLabel.setMinimumSize(new Dimension(20,20));
        iconLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        mainPanel.add(mainLabel);
        initSublist();
        initButtons();
        initButtonsPurpose();
        mainPanel.add(iconLabel);
        add(mainPanel);

        makeAddPanel();
        makeChoosePanel();
        makeRemovePanel();
        // makeRemoveLivPanel();
        // makeRemoveAcPanel();

        mainPanel.setVisible(true);
        setResizable(false);
    }

    public void makeRemoveEntPanel() {
        removeEntPanel = new JPanel(new GridLayout(6,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeEntPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createRemoveEntTexts();
        addLabelstoRemoveEnt();
    }

    public void addLabelstoRemoveEnt() {
        removeEntPanel.add(removeEntLabel);
        removeEntPanel.add(removeEntSubs);
        removeEntPanel.add(removeEntInstruct);
        removeEntPanel.add(removeEntText);
        removeEntPanel.add(removeEntChoice);
    }

    public void createRemoveEntTexts() {
        removeEntLabel = new JLabel("Entertainment Subscriptions");

        removeEntSubs = new JLabel(entSubs);

        removeEntInstruct = new JLabel("Enter the Associated Integer to Remove");

        removeEntText = new JTextField(10);

        removeEntChoice = new JButton(enterEntString);
        removeEntChoice.setActionCommand(enterEntString);
        removeEntChoice.addActionListener(this);

        adjustRemoveEntSettings();
    }

    public void adjustRemoveEntSettings() {
        removeEntLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeEntLabel.setFont(new Font("Arial", Font.BOLD, 24));

        removeEntSubs.setFont(new Font("Arial", Font.BOLD, 12));
        removeEntSubs.setHorizontalAlignment(SwingConstants.CENTER);

        removeEntInstruct.setFont(new Font("Arial", Font.BOLD, 24));
        removeEntInstruct.setHorizontalAlignment(SwingConstants.CENTER);

        removeEntText.setMaximumSize(new Dimension(1200, 400));
        removeEntText.setHorizontalAlignment(SwingConstants.CENTER);

        removeEntChoice.setBackground(Color.WHITE);
        removeEntChoice.setForeground(Color.BLACK);
        removeEntChoice.setFont(new Font("Arial", Font.BOLD, 12));
    }

    public void makeRemovePanel() {
        removePanel = new JPanel(new GridLayout(5,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        removePanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createRemoveTexts();
        addLabelstoRemove();
        makeRemoveEntPanel();
    }

    public void addLabelstoRemove() {
        removePanel.add(categoryLabel);
        removePanel.add(removeEntButton);
        removePanel.add(removeLivButton);
        removePanel.add(removeAcButton);
    }

    public void createRemoveTexts() {
        categoryLabel = new JLabel("Choose a Category to Remove From");
        removeEntButton = new JButton(removeEntString);
        removeEntButton.setActionCommand(removeEntString);
        removeEntButton.addActionListener(this);

        removeLivButton = new JButton(removeLivString);
        removeLivButton.setActionCommand(removeLivString);
        removeLivButton.addActionListener(this);

        removeAcButton = new JButton(removeAcString);
        removeAcButton.setActionCommand(removeAcString);
        removeAcButton.addActionListener(this);

        adjustRemoveSettings();
    }

    public void adjustRemoveSettings() {
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 24));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        removeEntButton.setBackground(Color.WHITE);
        removeEntButton.setForeground(Color.BLACK);
        removeEntButton.setFont(new Font("Arial", Font.BOLD, 12));

        removeLivButton.setBackground(Color.WHITE);
        removeLivButton.setForeground(Color.BLACK);
        removeLivButton.setFont(new Font("Arial", Font.BOLD, 12));

        removeAcButton.setBackground(Color.WHITE);
        removeAcButton.setForeground(Color.BLACK);
        removeAcButton.setFont(new Font("Arial", Font.BOLD, 12));
    }

    public void makeChoosePanel() {
        choosePanel = new JPanel(new GridLayout(5,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        choosePanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createChooseTexts();
        addLabelstoChoose();
    }

    public void addLabelstoChoose() {
        choosePanel.add(chooseLabel);
        choosePanel.add(entButton);
        choosePanel.add(livButton);
        choosePanel.add(acButton);
    }

    public void createChooseTexts() {
        entButton = new JButton(addEntString);
        entButton.setActionCommand(addEntString);
        entButton.addActionListener(this);

        livButton = new JButton(addLivString);
        livButton.setActionCommand(addLivString);
        livButton.addActionListener(this);

        acButton = new JButton(addAcString);
        acButton.setActionCommand(addAcString);
        acButton.addActionListener(this);

        chooseLabel = new JLabel("What Category is this Subscription?");
        chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);

        adjustChooseSettings();
    }

    public void adjustChooseSettings() {
        entButton.setBackground(Color.WHITE);
        entButton.setForeground(Color.BLACK);
        entButton.setFont(new Font("Arial", Font.BOLD, 12));

        livButton.setBackground(Color.WHITE);
        livButton.setForeground(Color.BLACK);
        livButton.setFont(new Font("Arial", Font.BOLD, 12));

        acButton.setBackground(Color.WHITE);
        acButton.setForeground(Color.BLACK);
        acButton.setFont(new Font("Arial", Font.BOLD, 12));

        chooseLabel.setFont(new Font("Arial", Font.BOLD, 24));
    }

    public void makeAddPanel() {
        addPanel = new JPanel(new GridLayout(0, 2));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);

        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        addPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createSubTexts();
        addLabelsToAdd();
    }

    public void addLabelsToAdd() {
        addPanel.add(confirmButton);
        addPanel.add(name);
        addPanel.add(text1);
        addPanel.add(price);
        addPanel.add(text2);
    }

    public void createSubTexts() {
        confirmButton = new JButton(confirmString);
        confirmButton.setActionCommand(confirmString);
        confirmButton.addActionListener(this);

        name = new JLabel("Name of Subscription:");
        text1 = new JTextField(10);
        price = new JLabel("Price of Subscription:");
        text2 = new JTextField(10);

        adjustAddSettings();
    }

    public void adjustAddSettings() {
        confirmButton.setBackground(Color.WHITE);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 12));

        name.setFont(new Font("Arial", Font.BOLD, 24));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        price.setFont(new Font("Arial", Font.BOLD, 24));
        price.setHorizontalAlignment(SwingConstants.CENTER);

        text1.setMaximumSize(new Dimension(1200, 400));
        text2.setMaximumSize(new Dimension(1200, 400));
    }

    public void initButtons() {
        addButton = new JButton(addString);
        mainPanel.add(addButton);

        removeButton = new JButton(removeString);
        mainPanel.add(removeButton);

        viewButton = new JButton(viewString);
        mainPanel.add(viewButton);

        payButton = new JButton(payString);
        mainPanel.add(payButton);

        fundButton = new JButton(fundString);
        mainPanel.add(fundButton);

        saveButton = new JButton(saveString);
        mainPanel.add(saveButton);

        loadButton = new JButton(loadString);
        mainPanel.add(loadButton);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void initButtonsPurpose() {
        addButton.setActionCommand(addString);
        addButton.addActionListener(this);

        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(this);

        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(this);

        payButton.setActionCommand(payString);
        payButton.addActionListener(this);

        fundButton.setActionCommand(fundString);
        fundButton.addActionListener(this);

        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(this);

        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(this);
    }

    public void initSublist() {
        subList = new SubscriptionList(0, 0, 0);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        entSubs = "No Subscriptions";
        livSubs = "No Subscriptions";
        acSubs = "No Subscriptions";
    }

    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(addString)) {
            initAddPanel();
        } else if (e.getActionCommand().equals(removeString)) {
            initRemovePanel();
        } else if (e.getActionCommand().equals(viewString)) {
            System.out.println("C");
        } else if (e.getActionCommand().equals(payString)) {
            System.out.println("D");
        } else if (e.getActionCommand().equals(fundString)) {
            System.out.println("E");
        } else if (e.getActionCommand().equals(saveString)) {
            saveSublist();
        } else if (e.getActionCommand().equals(loadString)) {
            loadSublist();
        } else if (e.getActionCommand().equals(returnString)) {
            returnToMenu();
        } else if (e.getActionCommand().equals(confirmString)) {
            initChoosePanel();
        } else if (e.getActionCommand().equals(addEntString)) {
            subList.addEntSub(new Subscription(text1.getText(), Double.parseDouble(text2.getText()),
                    Double.parseDouble(text2.getText()), false));
            System.out.println("Subscription Added!");
            returnToMenu();
        } else if (e.getActionCommand().equals(addLivString)) {
            subList.addLivSub(new Subscription(text1.getText(), Double.parseDouble(text2.getText()),
                    Double.parseDouble(text2.getText()), false));
            System.out.println("Subscription Added!");
            returnToMenu();
        } else if (e.getActionCommand().equals(addAcString)) {
            subList.addAcSub(new Subscription(text1.getText(), Double.parseDouble(text2.getText()),
                    Double.parseDouble(text2.getText()), false));
            System.out.println("Subscription Added!");
            returnToMenu();
        } else if (e.getActionCommand().equals(removeEntString)) {
            initRemoveEntPanel();
        } else if (e.getActionCommand().equals(removeLivString)) {
            System.out.println("G");
        } else if (e.getActionCommand().equals(removeAcString)) {
            System.out.println("H");
        } else if (e.getActionCommand().equals(enterEntString)) {
            subList.removeEntSub(subList.getListOfEntSubs().get(parseInt(removeEntText.getText())));
            System.out.println("Subscription Removed!");
        }
    }

    public void initRemoveEntPanel() {
        add(removeEntPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(true);
    }

    public void initRemovePanel() {
        add(removePanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(true);
        removeEntPanel.setVisible(false);
    }

    public void loadSublist() {
        try {
            subList = jsonReader.read();
            System.out.println("Loaded subscriptions from " + JSON_STORE);
            entSubs = updateEntString();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private String updateEntString() {
        if (subList.getNumEntSubs() == 0) {
            return "No Subscriptions";
        } else {
            StringBuilder stb = new StringBuilder();
            int j = 0;
            for (Subscription s : subList.getListOfEntSubs()) {
                stb.append("\t" + (j) + " -> " + s.getName()
                        + "\n" + "\tAmount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "\tBudget available: " + "$" + subList.getEntBudget() + "\n");
                j++;
            }
            return stb.toString();
        }
    }

    public void saveSublist() {
        try {
            jsonWriter.open();
            jsonWriter.write(subList);
            jsonWriter.close();
            System.out.println("Subscriptions Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public void initChoosePanel() {
        add(choosePanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        choosePanel.setVisible(true);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
    }

    public void returnToMenu() {
        choosePanel.setVisible(false);
        mainPanel.setVisible(true);
        addPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
    }

    public void initAddPanel() {
        add(addPanel);
        choosePanel.setVisible(false);
        addPanel.setVisible(true);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
    }
}
