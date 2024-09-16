package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RestaurantManagementSystem extends JFrame implements ActionListener {

    private JLabel label1, label2, label3, titleLabel;
    private JTextField textField1, textField2, textField3;
    private JButton addButton, viewButton, deleteButton, clearButton, billButton, exitButton, orderButton;
    private JPanel panel, buttonPanel;
    private JTextArea billArea;
    private ArrayList<String[]> menuItems = new ArrayList<>();
    private ArrayList<String[]> orders = new ArrayList<>();

    public RestaurantManagementSystem() {
        // Set frame properties
        setTitle("Restaurant Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 230, 250));

        // Title label
        titleLabel = new JLabel("Restaurant Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(new Color(25, 25, 112));

        // Labels
        label1 = new JLabel("Item ID:");
        label2 = new JLabel("Item Name:");
        label3 = new JLabel("Price (in INR):");

        // Text fields
        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(10);

        // Buttons
        addButton = new JButton("Add Item");
        viewButton = new JButton("View Menu");
        deleteButton = new JButton("Delete Item");
        clearButton = new JButton("Clear");
        orderButton = new JButton("Order Item");
        billButton = new JButton("Generate Bill");
        exitButton = new JButton("Exit");

        // Set button styles
        setButtonStyle(addButton, new Color(50, 205, 50));
        setButtonStyle(viewButton, new Color(30, 144, 255));
        setButtonStyle(deleteButton, new Color(255, 69, 0));
        setButtonStyle(clearButton, new Color(255, 215, 0));
        setButtonStyle(orderButton, new Color(218, 112, 214));
        setButtonStyle(billButton, new Color(75, 0, 130));
        setButtonStyle(exitButton, new Color(220, 20, 60));

        // Adding action listeners
        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        orderButton.addActionListener(this);
        billButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Bill area
        billArea = new JTextArea(10, 40);
        billArea.setEditable(false);
        billArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        billArea.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));

        // Panel setup
        panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.setBackground(new Color(230, 230, 250));

        // Adding components to the panel
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);

        // Button panel setup
        buttonPanel = new JPanel(new GridLayout(1, 7, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(230, 230, 250));

        // Adding buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(orderButton);
        buttonPanel.add(billButton);
        buttonPanel.add(exitButton);

        // Layout setup
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(billArea), BorderLayout.EAST);

        setVisible(true);
    }

    // Method to set button style
    private void setButtonStyle(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String[] menuItem = new String[3];
            menuItem[0] = textField1.getText();
            menuItem[1] = textField2.getText();
            menuItem[2] = textField3.getText();
            menuItems.add(menuItem);
            JOptionPane.showMessageDialog(this, "Item added successfully");
            clearFields();
        } else if (e.getSource() == viewButton) {
            String[] columns = {"Item ID", "Item Name", "Price (INR)"};
            Object[][] data = new Object[menuItems.size()][3];
            for (int i = 0; i < menuItems.size(); i++) {
                data[i][0] = menuItems.get(i)[0];
                data[i][1] = menuItems.get(i)[1];
                data[i][2] = menuItems.get(i)[2];
            }
            JTable table = new JTable(data, columns);
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("Menu");
            frame.add(scrollPane);
            frame.setSize(400, 300);
            frame.setVisible(true);
        } else if (e.getSource() == deleteButton) {
            String itemID = JOptionPane.showInputDialog(this, "Enter Item ID to delete:");
            for (int i = 0; i < menuItems.size(); i++) {
                if (menuItems.get(i)[0].equals(itemID)) {
                    menuItems.remove(i);
                    JOptionPane.showMessageDialog(this, "Item deleted successfully");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Item not found");
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == orderButton) {
            String itemID = JOptionPane.showInputDialog(this, "Enter Item ID to order:");
            for (String[] menuItem : menuItems) {
                if (menuItem[0].equals(itemID)) {
                    orders.add(menuItem);
                    JOptionPane.showMessageDialog(this, "Item ordered: " + menuItem[1]);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Item not found in menu");
        } else if (e.getSource() == billButton) {
            generateBill();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void generateBill() {
        StringBuilder bill = new StringBuilder();
        bill.append("Restaurant Bill\n");
        bill.append("=================\n");
        double total = 0.0;
        for (String[] order : orders) {
            bill.append(order[1]).append(" - ₹").append(order[2]).append("\n");
            total += Double.parseDouble(order[2]);
        }
        bill.append("=================\n");
        bill.append("Total: ₹").append(total).append("\n");
        billArea.setText(bill.toString());
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }

    public static void main(String[] args) {
        new RestaurantManagementSystem();
    }
}


