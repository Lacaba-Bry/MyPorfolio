package Attendance;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JPasswordField;

public class Resetpass extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    go();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    static void go() {
        Resetpass frame = new Resetpass();
        frame.setSize(499, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Resetpass() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 499, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(239, 241, 243));

        JLabel lblTitle = new JLabel("PASSWORD RESET");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblTitle.setBounds(120, 20, 250, 40);
        contentPane.add(lblTitle);

        JLabel lblUsername = new JLabel("USERNAME:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(50, 100, 120, 30);
        contentPane.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(200, 102, 200, 30);
        contentPane.add(usernameField);

        JLabel lblOldPassword = new JLabel("OLD PASSWORD:");
        lblOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblOldPassword.setBounds(50, 150, 120, 30);
        contentPane.add(lblOldPassword);

        JLabel lblNewPassword = new JLabel("NEW PASSWORD:");
        lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewPassword.setBounds(50, 200, 120, 30);
        contentPane.add(lblNewPassword);

        JLabel lblConfirmPassword = new JLabel("CONFIRM PASSWORD:");
        lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblConfirmPassword.setBounds(50, 250, 150, 30);
        contentPane.add(lblConfirmPassword);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetPassword();
            }
        });
        btnReset.setBounds(249, 299, 100, 30);
        contentPane.add(btnReset);

        JButton btnBack = new JButton("BACK");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainLogin pi = new mainLogin();
                pi.setVisible(false);
                setVisible(false);
                mainLogin.go();
            }
        });
        btnBack.setBounds(373, 370, 100, 30);
        contentPane.add(btnBack);
        
        oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(200, 152, 198, 30);
        contentPane.add(oldPasswordField);
        
        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(200, 207, 198, 30);
        contentPane.add(newPasswordField);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(200, 250, 200, 30);
        contentPane.add(confirmPasswordField);
    }

    /**
     * Method to reset the password.
     */
    private void resetPassword() {
        String username = usernameField.getText();
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "New Password and Confirm Password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Connect to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

            // Verify the username and old password
            String query = "SELECT * FROM admin WHERE USERNAME = ? AND PASSWORD = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, oldPassword);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Update the password
                String updateQuery = "UPDATE admin SET PASSWORD = ? WHERE USERNAME = ?";
                PreparedStatement updatePst = con.prepareStatement(updateQuery);
                updatePst.setString(1, newPassword);
                updatePst.setString(2, username);
                updatePst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                updatePst.close();
                
                mainLogin pi = new mainLogin();
				pi.setVisible(false);
				setVisible(false);
				mainLogin.go();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or old password.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
