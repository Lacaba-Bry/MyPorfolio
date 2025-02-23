package Attendance;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.prefs.Preferences;
import java.awt.SystemColor;

public class mainLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userField;
	private JPasswordField passwordField;
	private Preferences prefs;
	private String placeHolder = "Place Holder";
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
	
	static void go() {
	    // Create an instance of the adminMain frame
	    mainLogin frame = new mainLogin();
	    
	    // Set the frame size
	    frame.setSize(830, 550);

	    // Center the frame on the screen
	    frame.setLocationRelativeTo(null);

	    // Make the frame visible
	    frame.setVisible(true);

	    // Set default close operation
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	}
	

	
	
	Connection sqlConn = null;
	PreparedStatement pst = null;
	static Statement st = null;
	static ResultSet rs = null;
	
	public mainLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.desktop);
		panel.setBounds(0, 0, 254, 511);
		contentPane.add(panel);
		panel.setLayout(null);
	
		
		JLabel lblNewLabel_3 = new JLabel("");
		Image lgs = new ImageIcon(this.getClass().getResource("/LOGS.png")).getImage();
		lblNewLabel_3.setIcon(new ImageIcon(lgs)); // Corrected setting of the icon
		lblNewLabel_3.setBounds(-70, 89, 314, 248);
		panel.add(lblNewLabel_3);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(239, 241, 243));
		panel_1.setBounds(254, 0, 560, 511);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/profile-user.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img)); // Corrected setting of the icon
		lblNewLabel.setBounds(244, 85, 80, 69);
		panel_1.add(lblNewLabel);
		
		userField = new JTextField("Username");  // Set placeholder text
		userField.setBounds(156, 204, 233, 27);
		panel_1.add(userField);
		userField.setColumns(10);

		  userField.setForeground(new Color(150, 150, 150));  // Light grey for placeholder opacity

	        // Remove placeholder text when the user focuses on the field and restore if left empty
	        userField.addFocusListener(new FocusAdapter() {
	            public void focusGained(FocusEvent e) {
	                if (userField.getText().equals("Username")) {
	                    userField.setText("");  // Clear the placeholder when focused
	                    userField.setForeground(Color.BLACK); // Set text color to black when user types
	                }
	            }

	            public void focusLost(FocusEvent e) {
	                if (userField.getText().isEmpty()) {
	                    userField.setText("Username");  // Set placeholder back if the field is empty
	                    userField.setForeground(new Color(150, 150, 150)); // Set the placeholder opacity
	                }
	            }
	        });
	        

	        // Create passwordField (JPasswordField)
	        passwordField = new JPasswordField("Password");
	        passwordField.setBounds(156, 242, 233, 27);
	        panel_1.add(passwordField);

	        // Set a faded placeholder text color for the password field
	        passwordField.setForeground(new Color(150, 150, 150));  // Light grey for placeholder opacity

	        // Remove placeholder text when the user focuses on the field and restore if left empty
	        passwordField.addFocusListener(new FocusAdapter() {
	            public void focusGained(FocusEvent e) {
	                if (String.valueOf(passwordField.getPassword()).equals("Password")) {
	                    passwordField.setText("");  // Clear the placeholder when focused
	                    passwordField.setForeground(Color.BLACK); // Set text color to black when user types
	                }
	            }

	            public void focusLost(FocusEvent e) {
	                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
	                    passwordField.setText("Password");  // Set placeholder back if the field is empty
	                    passwordField.setForeground(new Color(150, 150, 150)); // Set the placeholder opacity
	                }
	            }
	       
		});
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Remember me");
		chckbxNewCheckBox.setBackground(new Color(239, 241, 243));
		chckbxNewCheckBox.setBounds(152, 276, 113, 23);
		panel_1.add(chckbxNewCheckBox);
		
		JLabel lblNewLabel_1 = new JLabel("Forgot password?");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	forgotPass pi = new forgotPass();
				pi.setVisible(false);
				setVisible(false);
				forgotPass.go();
		    }

		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Change cursor to HAND_CURSOR when mouse enters the label area
		        lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        // Reset cursor back to default when mouse exits the label area
		        lblNewLabel_1.setCursor(Cursor.getDefaultCursor());
		    }
		});
	    lblNewLabel_1.setBounds(283, 278, 106, 19);
	    panel_1.add(lblNewLabel_1);
	    panel_1.setLayout(null);
	    
	    JButton btnNewButton = new JButton("Login");
	    btnNewButton.addActionListener(new ActionListener() {
	    	 public void actionPerformed(ActionEvent e) {
	    	        // Retrieve the username and password from the user input fields
	    	        String enteredUsername = userField.getText();
	    	        String enteredPassword = new String(passwordField.getPassword());

	    	        // Validate if both fields are filled
	    	        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
	    	            JOptionPane.showMessageDialog(null, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
	    	            return;
	    	        }
	    	        

	    	        // Try to login by checking the credentials against the database
	    	        try {
	    	            // Connect to the database
	    	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");
	    	            
	    	            // SQL query to check if the username and password match
	    	            String query = "SELECT * FROM admin WHERE USERNAME = ? AND PASSWORD = ?";
	    	            PreparedStatement pst = con.prepareStatement(query);
	    	            pst.setString(1, enteredUsername);  // Set the entered username to the query
	    	            pst.setString(2, enteredPassword);  // Set the entered password to the query
	    	            
	    	            // Execute the query
	    	            ResultSet rs = pst.executeQuery();

	    	            if (rs.next()) {
	    	                // Credentials are valid, user is logged in
	    	                JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
	    	                
	    	                // Store credentials if "Remember Me" is checked
	    	                if (chckbxNewCheckBox.isSelected()) {
	    	                    Preferences prefs = Preferences.userNodeForPackage(mainLogin.class);
	    	                    prefs.put("username", enteredUsername);
	    	                    prefs.put("password", enteredPassword);
	    	                } else {
	    	                    Preferences prefs = Preferences.userNodeForPackage(mainLogin.class);
	    	                    prefs.remove("username");
	    	                    prefs.remove("password");
	    	                }
	    	            
	    	            
	    	            adminMain pi = new adminMain();
	    				pi.setVisible(false);
	    				setVisible(false);
	    				adminMain.go();
	    				dispose();

	    	            // Close the resources
	    	            rs.close();
	    	            pst.close();
	    	            con.close();
	    	            
	    	        
	    	            } else {
	    	                // Invalid credentials
	    	                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
	    	                // Clear the fields
	    	                userField.setText("");
	    	                passwordField.setText("");

	    	                // Highlight borders to indicate error
	    	                userField.setBorder(new LineBorder(new Color(240, 40, 73), 1)); // Light red borde
	    	            }

	    	            // Close the resources
	    	            rs.close();
	    	            pst.close();
	    	            con.close();

	    	        } catch (SQLException ex) {
	    	            // Handle SQL exception
	    	            JOptionPane.showMessageDialog(null, "Database connection error. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
	    	            ex.printStackTrace();
	    	        }
	    	    }
	    	});
	    
	    Preferences prefs = Preferences.userNodeForPackage(mainLogin.class);
	    String savedUsername = prefs.get("username", "");
	    String savedPassword = prefs.get("password", "");
	    if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
	        userField.setText(savedUsername);
	        passwordField.setText(savedPassword);
	        chckbxNewCheckBox.setSelected(true); // Mark "Remember Me" checkbox as selected
	    }

	    
	    passwordField.addKeyListener(new KeyAdapter() {
	        public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                btnNewButton.doClick(); // Simulate a button click when Enter is pressed
	            }
	        }
	    });

	    
	    
	    btnNewButton.setBorder(new LineBorder(UIManager.getColor("RadioButton.light"), 1, true));
	    btnNewButton.setBackground(new Color(239, 238, 234));
	    btnNewButton.setBounds(221, 320, 103, 27);
	    panel_1.add(btnNewButton);
	    
	    JLabel lblNewLabel_2 = new JLabel("Administrator");
	    lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    lblNewLabel_2.setBounds(233, 158, 88, 23);
	    panel_1.add(lblNewLabel_2);
	    
	    
	    
	    // this is line round panel
	    round_pane1 panel_2 = new round_pane1(new Color(239, 241, 243), new Color(0,0,0), 20);
	    panel_2.setBounds(90, 122, 367, 257);
	    panel_1.add(panel_2);
	    panel_1.setLayout(null);
	    
	    JPanel panel_3 = new JPanel();
	    panel_3.setBounds(0, 476, 814, 35);
	    contentPane.add(panel_3);
	    panel_3.setLayout(null);
	    
	    
	}
	/*
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(getText().length() == 0) {
		int h = getHeight();
		Insets ins = getInsets();
		FontMetrics fm =g.getFontMetrics();
		int bh = getBackground().getRGB();
		int fg = getForeground().getRGB();
		int m =0xfefefefe;
		int c = ((bg&m)>>>1)+((fg&m)>>>1);
		g.setColor(new Color(c, true));
		g.drawString(getplaceHolder, ins.left, h/2+fm.getAscent()/2-2);
	
	 public String getPlaceholder() {
	    	return placeHolder;
	    }
	 public void setPlaceHolder(String placeHolder){
		 this.placeHolder = placeHolder;
	 }
	 */
	 
	 
	class round_pane1 extends JPanel {
		private static final long serialVersionUID = 1L;
	    private Color borderColor;
	    private int cornerRadius;

	    public round_pane1(Color backgroundColor, Color borderColor, int cornerRadius) {
	        this.setBackground(backgroundColor);
	        this.borderColor = borderColor;
	        this.cornerRadius = cornerRadius;
	        this.setOpaque(false); // Make the panel non-opaque to allow rounded edges
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g.create();

	        // Enable anti-aliasing for smooth corners
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        // Draw the rounded rectangle background
	        g2d.setColor(getBackground());
	        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

	        // Draw the border
	        g2d.setColor(borderColor);
	        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

	        g2d.dispose();
	    }
	}
	
	  // Method to add placeholder style (set color to gray, etc.)
    private static void addPlaceHolderStyle(JPasswordField passwordField) {
        passwordField.setForeground(new java.awt.Color(153, 153, 153)); // Gray color
    }

    // Method to remove placeholder style (set color to default)
    private static void removePlaceholderStyle(JPasswordField passwordField) {
        passwordField.setForeground(java.awt.Color.BLACK); // Default text color
    }
    
   
}
