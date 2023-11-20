package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/sublist.json";
    private static final String addString = "Add Subscription";
    private static final String removeString = "Remove Subscription";
    private static final String viewString = "View Subscriptions";
    private static final String payString = "Pay Subscription(s)";
    private static final String fundString = "Add Funds";
    private static final String saveString = "Save";
    private static final String loadString = "Load from Saves";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 500;
    private SubscriptionList subList;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton payButton;
    private JButton fundButton;
    private JButton saveButton;
    private JButton loadButton;


    private JPanel mainPanel;
    private JLabel mainLabel;


    // EFFECTS: Makes a new JFrame
    public Menu() {
        super("Budget App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));

        mainPanel = new JPanel();
        mainLabel = new JLabel("Choose an Option");
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Icon mainIcon = new ImageIcon("./data/Stonk.jpg");
        JLabel iconLabel = new JLabel(mainIcon);
        iconLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        mainPanel.add(mainLabel);

        initSublist();
        initButtons();
        initButtonsPurpose();
        mainPanel.add(iconLabel);

        add(mainPanel);

        setVisible(true);
        setResizable(false);
    }

    private void initButtons() {
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
    }

    private void initButtonsPurpose() {
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

    private void initSublist() {
        subList = new SubscriptionList(0, 0, 0);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(addString)) {
            System.out.println("A");
        } else if (e.getActionCommand().equals(removeString)) {
            System.out.println("B");
        } else if (e.getActionCommand().equals(viewString)) {
            System.out.println("C");
        } else if (e.getActionCommand().equals(payString)) {
            System.out.println("D");
        } else if (e.getActionCommand().equals(fundString)) {
            System.out.println("E");
        } else if (e.getActionCommand().equals(saveString)) {
            System.out.println("F");
        } else if (e.getActionCommand().equals(loadString)) {
            System.out.println("G");
        }
    }
}
