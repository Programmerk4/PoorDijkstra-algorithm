import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NameWindow extends JFrame implements ActionListener {

    // Declare variables
    public JTextField nameField;
    public JTextField nameButton;
    public int xpos;
    public int ypos;

    // Initialise the window
    public NameWindow(int xpos, int ypos) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        this.xpos = xpos;
        this.ypos = ypos;

        JPanel namePanel = new JPanel();
        namePanel.setLayout(null);

        // create a label
        JLabel nameLabel = new JLabel("Enter name: ", SwingConstants.LEFT);
        Font f = new Font("SansSerif", Font.BOLD, 15);
        nameLabel.setBounds(0, 20, 90, 20);
        nameLabel.setFont(f);
        namePanel.add(nameLabel);

        // create a button
        nameButton = new JTextField("");
        Font f3 = new Font("SansSerif", Font.BOLD, 15);
        nameButton.setBounds(110, 20, 120, 20);
        nameButton.setFont(f3);
        nameButton.setBackground(Color.WHITE);
        nameButton.addActionListener(this); // use "this" as ActionListener
        nameButton.setActionCommand("enter");
        namePanel.add(nameButton);

        // set the window
        setTitle("Name");
        Dimension dimension = new Dimension(250, 100);
        setPreferredSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(namePanel);
        pack();
        nameButton.requestFocus(); // set focus to the text field

        setVisible(true);
    }

    // Detect when a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameButton.getText();

        // Check if the enter key is pressed
        if ("enter".equals(e.getActionCommand())) {
            // Check if the Text is alphanumeric
            if (isAlphanumeric(name)) {
                // Check if the name has already been taken
                boolean passed = !Window.vertexNames.contains(name);

                if (passed) {
                    // All checks have passed, therefore add a vertex with the name.
                    Main.window.createVertex(xpos, ypos, name);
                    Main.window.createVertex(0, 0, " ");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Name is already taken");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Input must be alphanumeric");
            }
        }
    }

    // function to check if the input contains no special characters.
    public static boolean isAlphanumeric(String str) {
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }
}