package Attendance;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class forgotPass extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField emailField;
    private JButton btnNewButton_1;
    private JButton btnNewButton;

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
        // Create an instance of the adminMain frame
        forgotPass frame = new forgotPass();
        
        // Set the frame size
        frame.setSize(450, 300);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);

        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public forgotPass() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(239, 241, 243));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Admin Name");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(150, 36, 114, 34);
        contentPane.add(lblNewLabel);

        // Create the emailField with a similar style to Facebook's login input field
        emailField = new JTextField();
        emailField.setText("Enter Admin Name");  // Default text (placeholder)
        emailField.setForeground(new Color(150, 150, 150)); // Placeholder color (gray)
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        emailField.setBounds(98, 81, 210, 30);
        emailField.setBorder(new LineBorder(new Color(200, 200, 200), 2, true));  // Border with rounded corners
        emailField.setBackground(Color.WHITE);
        contentPane.add(emailField);
        emailField.setColumns(10);

        // Use a flag to track if the placeholder is visible
        final String placeholder = "Enter Admin Name";

        // Make sure the placeholder text is visible when the app starts
        emailField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                // If the current text is placeholder, clear it
                if (emailField.getText().equals(placeholder)) {
                    emailField.setText("");  // Clear the placeholder when focused
                    emailField.setForeground(Color.BLACK); // Set text color to black when user types
                }
            }

            public void focusLost(FocusEvent e) {
                // If the text is empty, restore the placeholder
                if (emailField.getText().isEmpty()) {
                    emailField.setText(placeholder);  // Set placeholder back if the field is empty
                    emailField.setForeground(new Color(150, 150, 150)); // Set the placeholder color back
                }
            }
        });

        // Set up the action buttons
        btnNewButton_1 = new JButton("Get Password");
        btnNewButton_1.setBounds(68, 138, 107, 30);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_1_1 = new JButton("BACK");
        btnNewButton_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		mainLogin pi = new mainLogin();
				pi.setVisible(false);
				setVisible(false);
				mainLogin.go();
        	}
        });
        btnNewButton_1_1.setBounds(136, 190, 123, 30);
        contentPane.add(btnNewButton_1_1);
        
        btnNewButton = new JButton("Reset Pass");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Resetpass pi = new Resetpass();
				pi.setVisible(false);
				setVisible(false);
				Resetpass.go();
        	}
        });
        btnNewButton.setBounds(219, 138, 107, 30);
        contentPane.add(btnNewButton);

        // Handle the action for "Get Password"
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Existing logic for fetching password
            }
        });

        // Set focus to the "Get Password" button instead of the email field
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the admin name (email) entered by the user
                String adminName = emailField.getText();

                if (adminName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter the Admin name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Query the database to get the corresponding username and password
                try {
                    // Establish connection to the database
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

                    // SQL query to fetch the username and password for the entered admin name
                    String query = "SELECT USERNAME, PASSWORD FROM admin WHERE ADMIN_NAME = ?";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, adminName);  // Set the entered admin name to the query

                    // Execute the query
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        // Fetch the username and password from the result set
                        String username = rs.getString("USERNAME");
                        String password = rs.getString("PASSWORD");

                        // Show the username and password to the user
                        JOptionPane.showMessageDialog(null, "Username: " + username + "\nPassword: " + password, 
                                                      "Account Details", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // If no matching admin name is found, show an error
                        JOptionPane.showMessageDialog(null, "Admin name not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Close the resources
                    rs.close();
                    pst.close();
                    con.close();

                } catch (Exception ex) {
                    // Handle any exceptions (e.g., database connection issues)
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }
}
