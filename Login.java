import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    // Declare variables
    private JTextField userNameField;
    private JPasswordField passwordField;

    public Login() throws ClassNotFoundException {
        setLookAndFeel();
        initializeUI();
    }

    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    private void initializeUI() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        // ... (Your existing code for creating components)

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(30, 300, 130, 100);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        loginButton.setBackground(Color.WHITE);
        loginButton.addActionListener(this);
        loginButton.setActionCommand("login");
        loginPanel.add(loginButton);

        // Sign-up button
        JButton signupButton = new JButton("Sign up");
        signupButton.setBounds(180, 300, 130, 100);
        signupButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        signupButton.setBackground(Color.WHITE);
        signupButton.addActionListener(this);
        signupButton.setActionCommand("signup");
        loginPanel.add(signupButton);

        setTitle("Graphical Calculator");
        Dimension dimension = new Dimension(360, 500);
        setPreferredSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        add(loginPanel);
        pack();
    }

    private Connection establishConnection() throws SQLException {
        String jdbcUrl = "jdbc:sqlite:C:\\Users\\Niturzion\\Documents\\SQLite\\sqlite-tools-win32-x86-3360000\\users.db";
        return DriverManager.getConnection(jdbcUrl);
    }

    private boolean isValidInput(String username, String password) {
        return isAlphanumeric(username) && isAlphanumeric(password) &&
                username.length() <= 20 && password.length() <= 20;
    }

    public void login(String username, String password) {
        if (isValidInput(username, password)) {
            try (Connection connection = establishConnection()) {
                String sql = "SELECT * FROM User WHERE Username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    try (ResultSet result = statement.executeQuery()) {
                        if (userNameField.getText().equals("Username") || new String(passwordField.getPassword()).equals("Password")) {
                            JOptionPane.showMessageDialog(this, "Please enter some data");
                            return;
                        }

                        if (result.next()) {
                            String realPassword = result.getString("Password");
                            if (password.equals(realPassword)) {
                                // Start the main program
                                Main.startProgram();
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Login credentials are not valid!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Login credentials are not valid!");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username and password must be less than 20 characters long and must be alphanumeric");
        }
    }

    public void signUp(String username, String password) {
        if (isValidInput(username, password)) {
            try (Connection connection = establishConnection()) {
                String sql = "SELECT * FROM User WHERE Username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    try (ResultSet result = statement.executeQuery()) {
                        if (userNameField.getText().equals("Username") || new String(passwordField.getPassword()).equals("Password")) {
                            JOptionPane.showMessageDialog(this, "Please enter some data");
                            return;
                        }

                        if (result.next()) {
                            JOptionPane.showMessageDialog(this, "This Username is already taken");
                        } else {
                            // Add the data to the database
                            result.last();
                            int numberOfUsers = result.getRow();
                            result.beforeFirst();

                            sql = "INSERT INTO User VALUES (?, ?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(sql)) {
                                insertStatement.setInt(1, numberOfUsers + 1);
                                insertStatement.setString(2, username);
                                insertStatement.setString(3, password);
                                insertStatement.execute();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username and password must be less than 20 characters long and must be alphanumeric");
        }
    }

    public static boolean isAlphanumeric(String str) {
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("login".equals(e.getActionCommand())) {
            login(userNameField.getText(), new String(passwordField.getPassword()));
        } else if ("signup".equals(e.getActionCommand())) {
            signUp(userNameField.getText(), new String(passwordField.getPassword()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Login();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
