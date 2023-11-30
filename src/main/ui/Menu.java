package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

// GUI for BudgetApp
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

    private static final String enterEntString = "Remove Entertainment Subscription Associated";
    private static final String enterLivString = "Remove Living Expense Subscription Associated";
    private static final String enterAcString = "Remove Academic Expense Subscription Associated";

    private static final String payEntString = "Pay for a Entertainment Subscription";
    private static final String payLivString = "Pay for a Living Expense Subscription";
    private static final String payAcString = "Pay for a Academic Expense Subscription";

    private static final String enterEntPayString = "Pay for Entertainment Subscription Associated";
    private static final String enterLivPayString = "Pay for Living Expense Subscription Associated";
    private static final String enterAcPayString = "Pay for Academic Expense Subscription Associated";

    private static final String fundEntString = "Add Fund to Entertainment Subscriptions";
    private static final String fundLivString = "Add Fund to Living Expense Subscriptions";
    private static final String fundAcString = "Add Fund to Academic Expense Subscriptions";

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 800;

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

    private JPanel fundPanel;
    private JLabel fundLabel;
    private JTextField fundAmountText;
    private JButton fundEnt;
    private JButton fundLiv;
    private JButton fundAc;

    private JPanel payPanel;
    private JLabel payLabel;
    private JButton payEntButton;
    private JButton payLivButton;
    private JButton payAcButton;

    private JPanel payEntPanel;
    private JScrollPane payEntScroll;
    private JLabel payEntSubs;
    private JLabel payEntLabel;
    private JLabel payEntInstruct;
    private JTextField payEntText;
    private JButton payEntChoice;

    private JPanel payLivPanel;
    private JScrollPane payLivScroll;
    private JLabel payLivSubs;
    private JLabel payLivLabel;
    private JLabel payLivInstruct;
    private JTextField payLivText;
    private JButton payLivChoice;

    private JPanel payAcPanel;
    private JScrollPane payAcScroll;
    private JLabel payAcSubs;
    private JLabel payAcLabel;
    private JLabel payAcInstruct;
    private JTextField payAcText;
    private JButton payAcChoice;

    private JPanel viewPanel;
    private JLabel viewEntLabel;
    private JScrollPane viewEntScroll;
    private JLabel viewEntSubs;
    private JLabel viewLivLabel;
    private JScrollPane viewLivScroll;
    private JLabel viewLivSubs;
    private JLabel viewAcLabel;
    private JScrollPane viewAcScroll;
    private JLabel viewAcSubs;

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
    private JScrollPane removeEntScroll;
    private JLabel removeEntLabel;
    private JLabel removeEntInstruct;
    private JTextField removeEntText;
    private JButton removeEntChoice;

    private JPanel removeLivPanel;
    private JScrollPane removeLivScroll;
    private JLabel removeLivLabel;
    private JLabel removeLivInstruct;
    private JTextField removeLivText;
    private JButton removeLivChoice;

    private JPanel removeAcPanel;
    private JScrollPane removeAcScroll;
    private JLabel removeAcLabel;
    private JLabel removeAcInstruct;
    private JTextField removeAcText;
    private JButton removeAcChoice;

    private JLabel entSubs;
    private JLabel livSubs;
    private JLabel acSubs;

    private JPanel choosePanel;
    private JLabel chooseLabel;
    private JButton entButton;
    private JButton livButton;
    private JButton acButton;

    private StringPrinter sp;

    // EFFECTS: Makes a new JFrame with all panels, labels, and panels loaded
    public Menu() {
        super("Budget App");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            // EFFECTS: closes application and prints log to console
            public void windowClosing(WindowEvent e) {
                sp = new StringPrinter();
                sp.printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        makeMainMenu();

        makeAddPanel();
        makeRemovePanel();
        makeViewPanel();
        makePayPanel();
        makeFundPanel();

        mainPanel.setVisible(true);
        setResizable(false);
    }

    // EFFECT: Makes the main menu and buttons
    public void makeMainMenu() {
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
    }

    // EFFECT: makes a new JPanel for funds
    public void makeFundPanel() {
        fundPanel = new JPanel(new GridLayout(6,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        fundPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createFundTexts();
        addLabelsToFund();
    }

    // EFFECTS: adds all necessary labels, buttons, and pane to the panel
    public void addLabelsToFund() {
        fundPanel.add(fundLabel);
        fundPanel.add(fundAmountText);
        fundPanel.add(fundEnt);
        fundPanel.add(fundLiv);
        fundPanel.add(fundAc);
    }

    // EFFECTS: creates the components to be shown on the panel
    public void createFundTexts() {
        fundLabel = new JLabel("Enter the Amount of Funds to Apply");

        fundAmountText = new JTextField(10);

        fundEnt = new JButton(fundEntString);
        fundEnt.setActionCommand(fundEntString);
        fundEnt.addActionListener(this);

        fundLiv = new JButton(fundLivString);
        fundLiv.setActionCommand(fundLivString);
        fundLiv.addActionListener(this);

        fundAc = new JButton(fundAcString);
        fundAc.setActionCommand(fundAcString);
        fundAc.addActionListener(this);

        adjustFundSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustFundSettings() {
        fundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fundLabel.setFont(new Font("Arial", Font.BOLD, 24));

        fundAmountText.setMaximumSize(new Dimension(1200, 400));
        fundAmountText.setHorizontalAlignment(SwingConstants.CENTER);

        fundEnt.setBackground(Color.WHITE);
        fundEnt.setForeground(Color.BLACK);
        fundEnt.setFont(new Font("Arial", Font.BOLD, 12));

        fundLiv.setBackground(Color.WHITE);
        fundLiv.setForeground(Color.BLACK);
        fundLiv.setFont(new Font("Arial", Font.BOLD, 12));

        fundAc.setBackground(Color.WHITE);
        fundAc.setForeground(Color.BLACK);
        fundAc.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECT: makes a new JPanel for pay
    public void makePayPanel() {
        payPanel = new JPanel(new GridLayout(5,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        payPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createPayTexts();
        addLabelstoPay();
        makePayEntPanel();
        makePayLivPanel();
        makePayAcPanel();
    }

    // EFFECT: makes a new JPanel for academic pay
    public void makePayAcPanel() {
        payAcPanel = new JPanel(new GridLayout(6,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        payAcPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createPayAcTexts();
        addLabelstoPayAc();
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoPayAc() {
        payAcPanel.add(payAcLabel);
        payAcPanel.add(payAcScroll);
        payAcPanel.add(payAcInstruct);
        payAcPanel.add(payAcText);
        payAcPanel.add(payAcChoice);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
    public void createPayAcTexts() {
        payAcLabel = new JLabel("Living Expense Subscriptions");

        payAcSubs = new JLabel("");
        payAcSubs.setText(acSubs.getText());

        payAcScroll = new JScrollPane(payAcSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        payAcInstruct = new JLabel("Enter the Associated Integer to Pay");

        payAcText = new JTextField(10);

        payAcChoice = new JButton(enterAcPayString);
        payAcChoice.setActionCommand(enterAcPayString);
        payAcChoice.addActionListener(this);

        adjustPayAcSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustPayAcSettings() {
        payAcLabel.setHorizontalAlignment(SwingConstants.CENTER);
        payAcLabel.setFont(new Font("Arial", Font.BOLD, 24));

        payAcSubs.setFont(new Font("Arial", Font.BOLD, 12));
        payAcSubs.setHorizontalAlignment(SwingConstants.CENTER);

        payAcScroll.setSize(new Dimension(100, 100));

        payAcInstruct.setFont(new Font("Arial", Font.BOLD, 24));
        payAcInstruct.setHorizontalAlignment(SwingConstants.CENTER);

        payAcText.setMaximumSize(new Dimension(1200, 400));
        payAcText.setHorizontalAlignment(SwingConstants.CENTER);

        payAcChoice.setBackground(Color.WHITE);
        payAcChoice.setForeground(Color.BLACK);
        payAcChoice.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECT: makes a new JPanel for living expense pay
    public void makePayLivPanel() {
        payLivPanel = new JPanel(new GridLayout(6,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        payLivPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createPayLivTexts();
        addLabelstoPayLiv();
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoPayLiv() {
        payLivPanel.add(payLivLabel);
        payLivPanel.add(payLivScroll);
        payLivPanel.add(payLivInstruct);
        payLivPanel.add(payLivText);
        payLivPanel.add(payLivChoice);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
    public void createPayLivTexts() {
        payLivLabel = new JLabel("Living Expense Subscriptions");

        payLivSubs = new JLabel("");
        payLivSubs.setText(livSubs.getText());

        payLivScroll = new JScrollPane(payLivSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        payLivInstruct = new JLabel("Enter the Associated Integer to Pay");

        payLivText = new JTextField(10);

        payLivChoice = new JButton(enterLivPayString);
        payLivChoice.setActionCommand(enterLivPayString);
        payLivChoice.addActionListener(this);

        adjustPayLivSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustPayLivSettings() {
        payLivLabel.setHorizontalAlignment(SwingConstants.CENTER);
        payLivLabel.setFont(new Font("Arial", Font.BOLD, 24));

        payLivSubs.setFont(new Font("Arial", Font.BOLD, 12));
        payLivSubs.setHorizontalAlignment(SwingConstants.CENTER);

        payLivScroll.setSize(new Dimension(100, 100));

        payLivInstruct.setFont(new Font("Arial", Font.BOLD, 24));
        payLivInstruct.setHorizontalAlignment(SwingConstants.CENTER);

        payLivText.setMaximumSize(new Dimension(1200, 400));
        payLivText.setHorizontalAlignment(SwingConstants.CENTER);

        payLivChoice.setBackground(Color.WHITE);
        payLivChoice.setForeground(Color.BLACK);
        payLivChoice.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECT: makes a new JPanel for entertainment pay
    public void makePayEntPanel() {
        payEntPanel = new JPanel(new GridLayout(6,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        payEntPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createPayEntTexts();
        addLabelstoPayEnt();
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoPayEnt() {
        payEntPanel.add(payEntLabel);
        payEntPanel.add(payEntScroll);
        payEntPanel.add(payEntInstruct);
        payEntPanel.add(payEntText);
        payEntPanel.add(payEntChoice);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
    public void createPayEntTexts() {
        payEntLabel = new JLabel("Entertainment Subscriptions");

        payEntSubs = new JLabel("");
        payEntSubs.setText(entSubs.getText());

        payEntScroll = new JScrollPane(payEntSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        payEntInstruct = new JLabel("Enter the Associated Integer to Pay");

        payEntText = new JTextField(10);

        payEntChoice = new JButton(enterEntPayString);
        payEntChoice.setActionCommand(enterEntPayString);
        payEntChoice.addActionListener(this);

        adjustPayEntSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustPayEntSettings() {
        payEntLabel.setHorizontalAlignment(SwingConstants.CENTER);
        payEntLabel.setFont(new Font("Arial", Font.BOLD, 24));

        payEntSubs.setFont(new Font("Arial", Font.BOLD, 12));
        payEntSubs.setHorizontalAlignment(SwingConstants.CENTER);

        payEntScroll.setSize(new Dimension(100, 100));

        payEntInstruct.setFont(new Font("Arial", Font.BOLD, 24));
        payEntInstruct.setHorizontalAlignment(SwingConstants.CENTER);

        payEntText.setMaximumSize(new Dimension(1200, 400));
        payEntText.setHorizontalAlignment(SwingConstants.CENTER);

        payEntChoice.setBackground(Color.WHITE);
        payEntChoice.setForeground(Color.BLACK);
        payEntChoice.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoPay() {
        payPanel.add(payLabel);
        payPanel.add(payEntButton);
        payPanel.add(payLivButton);
        payPanel.add(payAcButton);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
    public void createPayTexts() {
        payLabel = new JLabel("Choose a Category");

        payEntButton = new JButton(payEntString);
        payEntButton.setActionCommand(payEntString);
        payEntButton.addActionListener(this);

        payLivButton = new JButton(payLivString);
        payLivButton.setActionCommand(payLivString);
        payLivButton.addActionListener(this);

        payAcButton = new JButton(payAcString);
        payAcButton.setActionCommand(payAcString);
        payAcButton.addActionListener(this);

        adjustPaySettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustPaySettings() {
        payLabel.setHorizontalAlignment(SwingConstants.CENTER);
        payLabel.setFont(new Font("Arial", Font.BOLD, 24));

        payEntButton.setBackground(Color.WHITE);
        payEntButton.setForeground(Color.BLACK);
        payEntButton.setFont(new Font("Arial", Font.BOLD, 12));

        payLivButton.setBackground(Color.WHITE);
        payLivButton.setForeground(Color.BLACK);
        payLivButton.setFont(new Font("Arial", Font.BOLD, 12));

        payAcButton.setBackground(Color.WHITE);
        payAcButton.setForeground(Color.BLACK);
        payAcButton.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECT: makes a new JPanel for view subscriptions
    public void makeViewPanel() {
        viewPanel = new JPanel(new GridLayout(7,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createViewTexts();
        addLabelstoView();
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoView() {
        viewPanel.add(viewEntLabel);
        viewPanel.add(viewEntScroll);
        viewPanel.add(viewLivLabel);
        viewPanel.add(viewLivScroll);
        viewPanel.add(viewAcLabel);
        viewPanel.add(viewAcScroll);
    }

    // EFFECTS: adjusts the components' settings
    public void createViewTexts() {
        viewEntLabel = new JLabel("Entertainment");

        viewEntSubs = new JLabel("");

        viewEntScroll = new JScrollPane(viewEntSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewLivLabel = new JLabel("Living Expense");

        viewLivSubs = new JLabel("");

        viewLivScroll = new JScrollPane(viewLivSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewAcLabel = new JLabel("Academic Expense");

        viewAcSubs = new JLabel("");

        viewAcScroll = new JScrollPane(viewAcSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        adjustViewSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustViewSettings() {
        viewEntLabel.setMaximumSize(new Dimension(1200, 400));
        viewEntLabel.setHorizontalAlignment(SwingConstants.CENTER);

        viewEntSubs.setHorizontalAlignment(SwingConstants.CENTER);

        viewLivLabel.setMaximumSize(new Dimension(1200, 400));
        viewLivLabel.setHorizontalAlignment(SwingConstants.CENTER);

        viewLivSubs.setHorizontalAlignment(SwingConstants.CENTER);

        viewAcLabel.setMaximumSize(new Dimension(1200, 400));
        viewAcLabel.setHorizontalAlignment(SwingConstants.CENTER);

        viewAcSubs.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // EFFECT: makes a new JPanel for view subscriptions
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

    // EFFECTS: adds all components to the panel
    public void addLabelstoRemoveEnt() {
        removeEntPanel.add(removeEntLabel);
        removeEntPanel.add(removeEntScroll);
        removeEntPanel.add(removeEntInstruct);
        removeEntPanel.add(removeEntText);
        removeEntPanel.add(removeEntChoice);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
    public void createRemoveEntTexts() {
        removeEntLabel = new JLabel("Entertainment Subscriptions");

        removeEntScroll = new JScrollPane(entSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        removeEntInstruct = new JLabel("Enter the Associated Integer to Remove");

        removeEntText = new JTextField(10);

        removeEntChoice = new JButton(enterEntString);
        removeEntChoice.setActionCommand(enterEntString);
        removeEntChoice.addActionListener(this);

        adjustRemoveEntSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustRemoveEntSettings() {
        removeEntLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeEntLabel.setFont(new Font("Arial", Font.BOLD, 24));

        entSubs.setFont(new Font("Arial", Font.BOLD, 12));
        entSubs.setHorizontalAlignment(SwingConstants.CENTER);

        removeEntScroll.setSize(new Dimension(100, 100));

        removeEntInstruct.setFont(new Font("Arial", Font.BOLD, 24));
        removeEntInstruct.setHorizontalAlignment(SwingConstants.CENTER);

        removeEntText.setMaximumSize(new Dimension(1200, 400));
        removeEntText.setHorizontalAlignment(SwingConstants.CENTER);

        removeEntChoice.setBackground(Color.WHITE);
        removeEntChoice.setForeground(Color.BLACK);
        removeEntChoice.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECT: makes a new JPanel for remove subscriptions
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
        makeRemoveLivPanel();
        makeRemoveAcPanel();
    }

    // EFFECT: makes a new JPanel for remove academic subscriptions
    public void makeRemoveAcPanel() {
        removeAcPanel = new JPanel(new GridLayout(6,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeAcPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createRemoveAcTexts();
        addLabelstoRemoveAc();
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoRemoveAc() {
        removeAcPanel.add(removeAcLabel);
        removeAcPanel.add(removeAcScroll);
        removeAcPanel.add(removeAcInstruct);
        removeAcPanel.add(removeAcText);
        removeAcPanel.add(removeAcChoice);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
    public void createRemoveAcTexts() {
        removeAcLabel = new JLabel("Living Expense Subscriptions");

        removeAcScroll = new JScrollPane(acSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        removeAcInstruct = new JLabel("Enter the Associated Integer to Remove");

        removeAcText = new JTextField(10);

        removeAcChoice = new JButton(enterAcString);
        removeAcChoice.setActionCommand(enterAcString);
        removeAcChoice.addActionListener(this);

        adjustRemoveAcSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustRemoveAcSettings() {
        removeAcLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeAcLabel.setFont(new Font("Arial", Font.BOLD, 24));

        acSubs.setFont(new Font("Arial", Font.BOLD, 12));
        acSubs.setHorizontalAlignment(SwingConstants.CENTER);

        removeAcScroll.setSize(new Dimension(40, 100));

        removeAcInstruct.setFont(new Font("Arial", Font.BOLD, 24));
        removeAcInstruct.setHorizontalAlignment(SwingConstants.CENTER);

        removeAcText.setMaximumSize(new Dimension(1200, 400));
        removeAcText.setHorizontalAlignment(SwingConstants.CENTER);

        removeAcChoice.setBackground(Color.WHITE);
        removeAcChoice.setForeground(Color.BLACK);
        removeAcChoice.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECT: makes a new JPanel for remove living subscriptions
    public void makeRemoveLivPanel() {
        removeLivPanel = new JPanel(new GridLayout(6,1));
        JButton returnButton = new JButton(returnString);
        returnButton.setActionCommand(returnString);
        returnButton.addActionListener(this);
        returnButton.setBackground(Color.WHITE);
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeLivPanel.add(returnButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createRemoveLivTexts();
        addLabelstoRemoveLiv();
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoRemoveLiv() {
        removeLivPanel.add(removeLivLabel);
        removeLivPanel.add(removeLivScroll);
        removeLivPanel.add(removeLivInstruct);
        removeLivPanel.add(removeLivText);
        removeLivPanel.add(removeLivChoice);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
    public void createRemoveLivTexts() {
        removeLivLabel = new JLabel("Living Expense Subscriptions");

        removeLivScroll = new JScrollPane(livSubs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        removeLivInstruct = new JLabel("Enter the Associated Integer to Remove");

        removeLivText = new JTextField(10);

        removeLivChoice = new JButton(enterLivString);
        removeLivChoice.setActionCommand(enterLivString);
        removeLivChoice.addActionListener(this);

        adjustRemoveLivSettings();
    }

    // EFFECTS: adjusts the components' settings
    public void adjustRemoveLivSettings() {
        removeLivLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeLivLabel.setFont(new Font("Arial", Font.BOLD, 24));

        livSubs.setFont(new Font("Arial", Font.BOLD, 12));
        livSubs.setHorizontalAlignment(SwingConstants.CENTER);

        removeLivScroll.setSize(new Dimension(40, 100));

        removeLivInstruct.setFont(new Font("Arial", Font.BOLD, 24));
        removeLivInstruct.setHorizontalAlignment(SwingConstants.CENTER);

        removeLivText.setMaximumSize(new Dimension(1200, 400));
        removeLivText.setHorizontalAlignment(SwingConstants.CENTER);

        removeLivChoice.setBackground(Color.WHITE);
        removeLivChoice.setForeground(Color.BLACK);
        removeLivChoice.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // EFFECTS: adds all components to the panel
    public void addLabelstoRemove() {
        removePanel.add(categoryLabel);
        removePanel.add(removeEntButton);
        removePanel.add(removeLivButton);
        removePanel.add(removeAcButton);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
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

    // EFFECTS: adjusts the components' settings
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

    // EFFECT: makes a new JPanel for add choice subscriptions
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

    // EFFECTS: adds all components to the panel
    public void addLabelstoChoose() {
        choosePanel.add(chooseLabel);
        choosePanel.add(entButton);
        choosePanel.add(livButton);
        choosePanel.add(acButton);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
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

    // EFFECTS: adjusts the components' settings
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

    // EFFECT: makes a new JPanel for add subscriptions
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
        makeChoosePanel();
    }

    // EFFECTS: adds all components to the panel
    public void addLabelsToAdd() {
        addPanel.add(confirmButton);
        addPanel.add(name);
        addPanel.add(text1);
        addPanel.add(price);
        addPanel.add(text2);
    }

    // EFFECTS: creates the necessary components to be shown on the panel
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

    // EFFECTS: adjusts the components' settings
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

    // EFFECTS: makes the buttons on main menu with no actions and listeners
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

    // EFFECTS: adds action command and listener to each button
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

    // EFFECTS: initialize model and persistence
    public void initSublist() {
        subList = new SubscriptionList(0, 0, 0);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        entSubs = new JLabel("");
        entSubs.setText("No Subscriptions!");
        livSubs = new JLabel("");
        livSubs.setText("No Subscriptions!");
        acSubs = new JLabel("");
        acSubs.setText("No Subscriptions!");
    }

    // EFFECTS: actionListener for every button pressed
    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(addString)) {
            initAddPanel();
        } else if (e.getActionCommand().equals(removeString)) {
            initRemovePanel();
        } else if (e.getActionCommand().equals(viewString)) {
            initViewPanel();
        } else if (e.getActionCommand().equals(payString)) {
            initPayPanel();
        } else if (e.getActionCommand().equals(fundString)) {
            initFundPanel();
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
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            returnToMenu();
        } else if (e.getActionCommand().equals(addLivString)) {
            subList.addLivSub(new Subscription(text1.getText(), Double.parseDouble(text2.getText()),
                    Double.parseDouble(text2.getText()), false));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            returnToMenu();
        } else if (e.getActionCommand().equals(addAcString)) {
            subList.addAcSub(new Subscription(text1.getText(), Double.parseDouble(text2.getText()),
                    Double.parseDouble(text2.getText()), false));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            returnToMenu();
        } else if (e.getActionCommand().equals(removeEntString)) {
            initRemoveEntPanel();
        } else if (e.getActionCommand().equals(removeLivString)) {
            initRemoveLivPanel();
        } else if (e.getActionCommand().equals(removeAcString)) {
            initRemoveAcPanel();
        } else if (e.getActionCommand().equals(enterEntString)) {
            subList.removeEntSub(subList.getListOfEntSubs().get(parseInt(removeEntText.getText())));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
        } else if (e.getActionCommand().equals(enterLivString)) {
            subList.removeLivSub(subList.getListOfLivSubs().get(parseInt(removeLivText.getText())));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
        } else if (e.getActionCommand().equals(enterAcString)) {
            subList.removeAcSub(subList.getListOfAcSubs().get(parseInt(removeAcText.getText())));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
        } else if (e.getActionCommand().equals(payEntString)) {
            initPayEntPanel();
        } else if (e.getActionCommand().equals(payLivString)) {
            initPayLivPanel();
        } else if (e.getActionCommand().equals(payAcString)) {
            initPayAcPanel();
        } else if (e.getActionCommand().equals(enterEntPayString)) {
            subList.payForEntSub(subList.getListOfEntSubs().get(parseInt(payEntText.getText())));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            payEntSubs.setText(entSubs.getText());
            payLivSubs.setText(livSubs.getText());
            payAcSubs.setText(acSubs.getText());
        } else if (e.getActionCommand().equals(enterLivPayString)) {
            subList.payForLivSub(subList.getListOfLivSubs().get(parseInt(payLivText.getText())));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            payEntSubs.setText(entSubs.getText());
            payLivSubs.setText(livSubs.getText());
            payAcSubs.setText(acSubs.getText());
        } else if (e.getActionCommand().equals(enterAcPayString)) {
            subList.payForAcSub(subList.getListOfAcSubs().get(parseInt(payAcText.getText())));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            payEntSubs.setText(entSubs.getText());
            payLivSubs.setText(livSubs.getText());
            payAcSubs.setText(acSubs.getText());
        } else if (e.getActionCommand().equals(fundEntString)) {
            subList.addEntBudget(Double.parseDouble(fundAmountText.getText()));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            payEntSubs.setText(entSubs.getText());
            payLivSubs.setText(livSubs.getText());
            payAcSubs.setText(acSubs.getText());
            returnToMenu();
        } else if (e.getActionCommand().equals(fundLivString)) {
            subList.addLivBudget(Double.parseDouble(fundAmountText.getText()));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            payEntSubs.setText(entSubs.getText());
            payLivSubs.setText(livSubs.getText());
            payAcSubs.setText(acSubs.getText());
            returnToMenu();
        } else if (e.getActionCommand().equals(fundAcString)) {
            subList.addAcBudget(Double.parseDouble(fundAmountText.getText()));
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
            payEntSubs.setText(entSubs.getText());
            payLivSubs.setText(livSubs.getText());
            payAcSubs.setText(acSubs.getText());
            returnToMenu();
        }
    }

    // EFFECTS: initializes the fund panel and make it visible to user
    public void initFundPanel() {
        add(fundPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(true);
    }

    // EFFECTS: initializes the academic panel and make it visible to user
    public void initPayAcPanel() {
        add(payAcPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(true);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the pay living expense panel and make it visible to user
    public void initPayLivPanel() {
        add(payLivPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(true);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the pay entertainment panel and make it visible to user
    public void initPayEntPanel() {
        add(payEntPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(true);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the pay panel and make it visible to user
    public void initPayPanel() {
        payEntSubs.setText(entSubs.getText());
        payLivSubs.setText(livSubs.getText());
        payAcSubs.setText(acSubs.getText());
        add(payPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(true);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the view panel and make it visible to user
    public void initViewPanel() {
        viewEntSubs.setText(entSubs.getText());
        viewLivSubs.setText(livSubs.getText());
        viewAcSubs.setText(acSubs.getText());
        add(viewPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(true);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the remove academic panel and make it visible to user
    public void initRemoveAcPanel() {
        add(removeAcPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(true);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the remove living expense panel and make it visible to user
    public void initRemoveLivPanel() {
        add(removeLivPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(true);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the remove entertainment panel and make it visible to user
    public void initRemoveEntPanel() {
        add(removeEntPanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(true);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the remove panel and make it visible to user
    public void initRemovePanel() {
        add(removePanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        removePanel.setVisible(true);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: initializes the add choice panel and make it visible to user
    public void initChoosePanel() {
        add(choosePanel);
        addPanel.setVisible(false);
        mainPanel.setVisible(false);
        choosePanel.setVisible(true);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: returns to then main panel and make it visible to user
    public void returnToMenu() {
        choosePanel.setVisible(false);
        mainPanel.setVisible(true);
        addPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: returns to add and make it visible to user
    public void initAddPanel() {
        add(addPanel);
        choosePanel.setVisible(false);
        addPanel.setVisible(true);
        mainPanel.setVisible(false);
        removePanel.setVisible(false);
        removeEntPanel.setVisible(false);
        removeLivPanel.setVisible(false);
        removeAcPanel.setVisible(false);
        viewPanel.setVisible(false);
        payPanel.setVisible(false);
        payEntPanel.setVisible(false);
        payLivPanel.setVisible(false);
        payAcPanel.setVisible(false);
        fundPanel.setVisible(false);
    }

    // EFFECTS: load the saved data
    public void loadSublist() {
        try {
            subList = jsonReader.read();
            System.out.println("Loaded subscriptions from " + JSON_STORE);
            updateEntSubs();
            updateLivSubs();
            updateAcSubs();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: updates the academic expense subs label
    public void updateAcSubs() {
        if (subList.getNumAcSubs() == 0) {
            acSubs.setText("No Subscriptions!");
        } else {
            String subs = "";
            int j = 0;
            for (Subscription s : subList.getListOfAcSubs()) {
                subs += "" + (j) + " -> " + s.getName()
                        + "\n" + "Amount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "Budget available: " + "$" + subList.getAcBudget()
                        + "\n" + "\n";
                j++;
            }
            acSubs.setText("<html><pre>Current Subscriptions: \n" + subs + "\n</pre></html>");
        }
    }

    // EFFECTS: updates the living expense expense subs label
    public void updateLivSubs() {
        if (subList.getNumLivSubs() == 0) {
            livSubs.setText("No Subscriptions!");
        } else {
            String subs = "";
            int j = 0;
            for (Subscription s : subList.getListOfLivSubs()) {
                subs += "" + (j) + " -> " + s.getName()
                        + "\n" + "Amount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "Budget available: " + "$" + subList.getLivBudget()
                        + "\n" + "\n";
                j++;
            }
            livSubs.setText("<html><pre>Current Subscriptions: \n" + subs + "\n</pre></html>");
        }
    }

    // EFFECTS: updates the entertainment expense subs label
    public void updateEntSubs() {
        if (subList.getNumEntSubs() == 0) {
            entSubs.setText("No Subscriptions!");
        } else {
            String subs = "";
            int j = 0;
            for (Subscription s : subList.getListOfEntSubs()) {
                subs += "" + (j) + " -> " + s.getName()
                        + "\n" + "Amount to pay: " + "$" + s.getApparentPrice()
                        + "\n" + "Budget available: " + "$" + subList.getEntBudget()
                        + "\n" + "\n";
                j++;
            }
            entSubs.setText("<html><pre>Current Subscriptions: \n" + subs + "\n</pre></html>");
        }
    }

    // EFFECTS: saves the current sublist
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
}
