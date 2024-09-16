import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineBankManagementSystem {

    public static void main(String[] args) {
      
        new LoginScreen();
    }
}


class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheck;

    public LoginScreen() {
        setTitle("iBanking - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

      
        usernameField = new JTextField();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

       
        passwordField = new JPasswordField();
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

       
        rememberMeCheck = new JCheckBox("Remember me");
        panel.add(rememberMeCheck);

    
        JButton loginButton = new JButton("Sign In");
        loginButton.addActionListener(new LoginButtonListener());
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    // Listener for the login button
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Validate login and go to AccountSelectionScreen
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
           
            new AccountSelectionScreen();
            dispose(); 
    }
}

// Account Selection Screen
class AccountSelectionScreen extends JFrame {

    public AccountSelectionScreen() {
        setTitle("iBanking - Choose Account");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        // Displaying available accounts in table format
        panel.add(new JLabel("Bank Account ID"));
        panel.add(new JLabel("Available Balance"));
        panel.add(new JLabel("Account Type"));

      
        addAccount(panel, "10002", "693,423.00", "Saving Account");
        addAccount(panel, "10000", "8,002,489.00", "Checking Account");

        add(panel);
        setVisible(true);
    }

    private void addAccount(JPanel panel, String id, String balance, String type) {
        panel.add(new JLabel(id));
        panel.add(new JLabel(balance));
        panel.add(new JLabel(type));
        
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> new AccountDetailsScreen(id, balance, type));
        panel.add(selectButton);
    }
}


class AccountDetailsScreen extends JFrame {

    public AccountDetailsScreen(String accountId, String balance, String accountType) {
        setTitle("iBanking - Account Details");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Account summary
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1));
        summaryPanel.add(new JLabel("Account ID: " + accountId));
        summaryPanel.add(new JLabel("Available Balance: " + balance));
        summaryPanel.add(new JLabel("Account Type: " + accountType));
        panel.add(summaryPanel, BorderLayout.NORTH);

        // Transaction history
        String[] columnNames = {"Transaction Time", "Amount", "Type"};
        String[][] data = {
                {"06/13/2024 06:47", "2,500.00", "Sent to Account ID - 10002"},
                {"08/14/2024 05:41", "123.00", "Sent to Account ID - 10002"},
                {"07/15/2024 11:26", "1,000.00", "Deposit"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
}