package Attendance;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import javax.swing.border.EtchedBorder;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import javax.swing.*;
import java.awt.Font;



public class markAttendance extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table_2;
	private JComboBox<String> comboBox;
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
	    markAttendance frame = new markAttendance();
	    
	    // Set the frame size
	    frame.setSize(960, 801);

	    // Center the frame on the screen
	    frame.setLocationRelativeTo(null);

	    // Make the frame visible
	    frame.setVisible(true);

	    // Set default close operation
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    showtable();
	}
	
	Connection sqlConn = null;
	PreparedStatement pst = null;
	static Statement st = null;
	static ResultSet rs = null;
	private JButton PresentButton;
	private JButton AbsentButton;
	private JButton LateButton;
	private JButton PresentAllButton;
	private JButton btnBack;
	private JButton btnBack_1;
	private JLabel lblNewLabel;
	 private static void showtable() {
	        Connection conn = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;

	        try {
	            // Establish connection to the database
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

	            // Query to retrieve only STUDENT_NUMBER and FULL_NAME columns
	            String sql = "SELECT STUDENT_NUMBER, FULL_NAME FROM attendance ORDER BY `attendance`.`FULL_NAME` ASC";
	            pst = conn.prepareStatement(sql);
	            rs = pst.executeQuery();

	            // Create a table model and set column names
	            DefaultTableModel RecordTable = new DefaultTableModel(new String[]{"STUDENT_NUMBER", "FULL_NAME"}, 0);

	            // Loop through the ResultSet and add rows to the table model
	            while (rs.next()) {
	                String studentNumber = rs.getString("STUDENT_NUMBER");
	                String fullName = rs.getString("FULL_NAME");
	                RecordTable.addRow(new Object[]{studentNumber, fullName});
	            }

	            // Set the table model to the JTable
	            table_2.setModel(RecordTable);
	            
	            

	        } catch (ClassNotFoundException ex) {
	            JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
	        } finally {
	            // Close resources
	            try {
	                if (rs != null) rs.close();
	                if (pst != null) pst.close();
	                if (conn != null) conn.close();
	            } catch (SQLException ex) {
	                JOptionPane.showMessageDialog(null, "Error closing resources: " + ex.getMessage());
	            }
	        }
	    }
	 
	 
	
	
	 
	public markAttendance() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 801);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(239, 241, 243));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane.setBounds(169, 110, 740, 632);
		contentPane.add(scrollPane);
		
		table_2 = new JTable();
		table_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		
				        int selectedRow = table_2.getSelectedRow(); // Get the selected row index
				        if (selectedRow != -1) {
				            String studentNumber = table_2.getValueAt(selectedRow, 0).toString(); // Get STUDENT_NUMBER
				            String fullName = table_2.getValueAt(selectedRow, 1).toString(); // Get FULL_NAME
				        }
				    }
			
			
		});
		table_2.setRowHeight(26);
		scrollPane.setViewportView(table_2);
		
		PresentButton = new JButton("PRESENT");
		PresentButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
			        int selectedRow = table_2.getSelectedRow(); // Get the selected row
			        if (selectedRow == -1) {
			            JOptionPane.showMessageDialog(null, "Please select a row in the table!");
			            return;
			        }

			        try {
			            // Get the selected column and STUDENT_NUMBER
			            String selectedColumn = comboBox.getSelectedItem().toString();
			            String studentNumber = table_2.getValueAt(selectedRow, 0).toString();

			            // Establish connection to the database
			            Class.forName("com.mysql.cj.jdbc.Driver");
			            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

			            // Build the update query for the selected row and column
			            String updateQuery = "UPDATE attendance SET `" + selectedColumn + "` = 'PRESENT' WHERE STUDENT_NUMBER = ?";
			            PreparedStatement stmt = conn.prepareStatement(updateQuery);
			            stmt.setString(1, studentNumber); // Bind the student number to the query

			            // Execute the update query
			            int rowsUpdated = stmt.executeUpdate();
			            JOptionPane.showMessageDialog(null, rowsUpdated + " record(s) updated to 'PRESENT'.");

			            // Refresh the table to reflect changes
			            String query = "SELECT STUDENT_NUMBER, FULL_NAME, `" + selectedColumn + "` FROM attendance";
			            PreparedStatement fetchStmt = conn.prepareStatement(query);
			            ResultSet rs = fetchStmt.executeQuery();

			            // Update the JTable with the new data
			            DefaultTableModel tableModel = new DefaultTableModel(new String[]{"STUDENT_NUMBER", "FULL_NAME", selectedColumn}, 0);
			            while (rs.next()) {
			                String number = rs.getString("STUDENT_NUMBER");
			                String name = rs.getString("FULL_NAME");
			                String status = rs.getString(selectedColumn); // Get the updated value
			                tableModel.addRow(new Object[]{number, name, status});
			            }

			            table_2.setModel(tableModel);

			            // Close resources
			            rs.close();
			            fetchStmt.close();
			            stmt.close();
			            conn.close();
			        } catch (ClassNotFoundException ex) {
			            JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
			        }
			  }
		});
		PresentButton.setBounds(27, 110, 119, 44);
		contentPane.add(PresentButton);
		
		AbsentButton = new JButton("ABSENT");
		AbsentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int selectedRow = table_2.getSelectedRow(); // Get the selected row
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Please select a row in the table!");
		            return;
		        }

		        try {
		            // Get the selected column and STUDENT_NUMBER
		            String selectedColumn = comboBox.getSelectedItem().toString();
		            String studentNumber = table_2.getValueAt(selectedRow, 0).toString();

		            // Establish connection to the database
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

		            // Build the update query for the selected row and column
		            String updateQuery = "UPDATE attendance SET `" + selectedColumn + "` = 'ABSENT' WHERE STUDENT_NUMBER = ?";
		            PreparedStatement stmt = conn.prepareStatement(updateQuery);
		            stmt.setString(1, studentNumber); // Bind the student number to the query

		            // Execute the update query
		            int rowsUpdated = stmt.executeUpdate();
		            JOptionPane.showMessageDialog(null, rowsUpdated + " record(s) updated to 'ABSENT'.");

		            // Refresh the table to reflect changes
		            String query = "SELECT STUDENT_NUMBER, FULL_NAME, `" + selectedColumn + "` FROM attendance";
		            PreparedStatement fetchStmt = conn.prepareStatement(query);
		            ResultSet rs = fetchStmt.executeQuery();

		            // Update the JTable with the new data
		            DefaultTableModel tableModel = new DefaultTableModel(new String[]{"STUDENT_NUMBER", "FULL_NAME", selectedColumn}, 0);
		            while (rs.next()) {
		                String number = rs.getString("STUDENT_NUMBER");
		                String name = rs.getString("FULL_NAME");
		                String status = rs.getString(selectedColumn); // Get the updated value
		                tableModel.addRow(new Object[]{number, name, status});
		            }

		            table_2.setModel(tableModel);

		            // Close resources
		            rs.close();
		            fetchStmt.close();
		            stmt.close();
		            conn.close();
		        } catch (ClassNotFoundException ex) {
		            JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
		        }
		  }
	});
		AbsentButton.setBounds(27, 275, 119, 44);
		contentPane.add(AbsentButton);
		
		LateButton = new JButton("LATE");
		LateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int selectedRow = table_2.getSelectedRow(); // Get the selected row
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Please select a row in the table!");
		            return;
		        }

		        try {
		            // Get the selected column and STUDENT_NUMBER
		            String selectedColumn = comboBox.getSelectedItem().toString();
		            String studentNumber = table_2.getValueAt(selectedRow, 0).toString();

		            // Establish connection to the database
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

		            // Build the update query for the selected row and column
		            String updateQuery = "UPDATE attendance SET `" + selectedColumn + "` = 'LATE' WHERE STUDENT_NUMBER = ?";
		            PreparedStatement stmt = conn.prepareStatement(updateQuery);
		            stmt.setString(1, studentNumber); // Bind the student number to the query

		            // Execute the update query
		            int rowsUpdated = stmt.executeUpdate();
		            JOptionPane.showMessageDialog(null, rowsUpdated + " record(s) updated to 'LATE'.");

		            // Refresh the table to reflect changes
		            String query = "SELECT STUDENT_NUMBER, FULL_NAME, `" + selectedColumn + "` FROM attendance";
		            PreparedStatement fetchStmt = conn.prepareStatement(query);
		            ResultSet rs = fetchStmt.executeQuery();

		            // Update the JTable with the new data
		            DefaultTableModel tableModel = new DefaultTableModel(new String[]{"STUDENT_NUMBER", "FULL_NAME", selectedColumn}, 0);
		            while (rs.next()) {
		                String number = rs.getString("STUDENT_NUMBER");
		                String name = rs.getString("FULL_NAME");
		                String status = rs.getString(selectedColumn); // Get the updated value
		                tableModel.addRow(new Object[]{number, name, status});
		            }

		            table_2.setModel(tableModel);

		            // Close resources
		            rs.close();
		            fetchStmt.close();
		            stmt.close();
		            conn.close();
		        } catch (ClassNotFoundException ex) {
		            JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
		        }
		  }
	});
		LateButton.setBounds(27, 330, 119, 44);
		contentPane.add(LateButton);
		
		PresentAllButton = new JButton("PRESENT / ALL");
		PresentAllButton.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        try {
				            // Establish connection to the database
				            Class.forName("com.mysql.cj.jdbc.Driver");
				            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

				            // Get the selected column from the comboBox
				            String selectedColumn = comboBox.getSelectedItem().toString();

				            // Build the update query to set all values in the selected column to "Present"
				            String updateQuery = "UPDATE attendance SET `" + selectedColumn + "` = 'PRESENT'";
				            PreparedStatement stmt = conn.prepareStatement(updateQuery);

				            // Execute the update query
				            int rowsUpdated = stmt.executeUpdate();
				            JOptionPane.showMessageDialog(null, rowsUpdated + " rows updated to 'PRESENT' for column " + selectedColumn);

				            // Refresh the table to reflect changes
				            String query = "SELECT STUDENT_NUMBER, FULL_NAME, `" + selectedColumn + "` FROM attendance";
				            PreparedStatement fetchStmt = conn.prepareStatement(query);
				            ResultSet rs = fetchStmt.executeQuery();

				            // Update the JTable with the new data
				            DefaultTableModel tableModel = new DefaultTableModel(new String[]{"STUDENT_NUMBER", "FULL_NAME", selectedColumn}, 0);
				            while (rs.next()) {
				                String studentNumber = rs.getString("STUDENT_NUMBER");
				                String fullName = rs.getString("FULL_NAME");
				                String selectedDateValue = rs.getString(selectedColumn); // Get the updated value
				                tableModel.addRow(new Object[]{studentNumber, fullName, selectedDateValue});
				            }

				            table_2.setModel(tableModel);
				            

				            // Close resources
				            rs.close();
				            fetchStmt.close();
				            stmt.close();
				            conn.close();
					        JOptionPane.showMessageDialog(null, "  PRESENT ALL FROM " + selectedColumn);
				        } catch (ClassNotFoundException ex) {
				            JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
				        } catch (SQLException ex) {
				            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
				        }
				    }
		
			
	
			
		});
		PresentAllButton.setBounds(27, 165, 119, 44);
		contentPane.add(PresentAllButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(239, 241, 243));
		panel.setForeground(SystemColor.desktop);

		// Corrected border with black title color
		panel.setBorder(new TitledBorder(
		    BorderFactory.createLineBorder(Color.BLACK), // Border style (line border)
		    "Search Date", // Title of the border
		    TitledBorder.LEADING, // Title alignment
		    TitledBorder.TOP, // Title position
		    null, // Default font
		    Color.BLACK // Title color
		));

		panel.setBounds(10, 11, 347, 77);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		

		
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(91, 30, 119, 27);
		panel.add(comboBox);
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		
		// Populate the combo box with columns automatically
		try {
			comboBox.setBackground(new Color(255, 255, 255));
		    // Establish connection to the database
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

		    // Query to retrieve all column names
		    String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'attendance'";
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);

		    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		    int index = 0; // To track the index of the columns
		    while (rs.next()) {
		        if (index >= 3) { // Only add items starting from index 3
		            String columnName = rs.getString("COLUMN_NAME");
		            model.addElement(columnName); // Add column names to the combo box
		        }
		        index++;
		    }

		    comboBox.setModel(model); // Set the model to the combo box

		    // Close resources
		    rs.close();
		    stmt.close();
		    conn.close();
		} catch (ClassNotFoundException ex) {
		    JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
		} catch (SQLException ex) {
		    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
		}
		

		
		JButton btnNewButton = new JButton("GO");
		btnNewButton.setBounds(220, 30, 70, 27);
		panel.add(btnNewButton);
		
		lblNewLabel = new JLabel("DATE:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(35, 34, 46, 19);
		panel.add(lblNewLabel);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            // Establish connection to the database
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

		            // Get the selected column from the comboBox
		            String selectedColumn = comboBox.getSelectedItem().toString();

		            // Build the query to fetch the data
		            String query = "SELECT STUDENT_NUMBER, FULL_NAME, `" + selectedColumn + "` FROM attendance";
		            PreparedStatement stmt = conn.prepareStatement(query);
		            ResultSet rs = stmt.executeQuery();

		            // Create a table model to populate the JTable
		            DefaultTableModel tableModel = new DefaultTableModel(new String[]{"STUDENT_NUMBER", "FULL_NAME", selectedColumn}, 0);

		            // Loop through the ResultSet and add rows to the table model
		            while (rs.next()) {
		                String studentNumber = rs.getString("STUDENT_NUMBER");
		                String fullName = rs.getString("FULL_NAME");
		                String selectedDateValue = rs.getString(selectedColumn); // Get the value for the selected column
		                tableModel.addRow(new Object[]{studentNumber, fullName, selectedDateValue});
		            }

		            // Set the table model to the JTable
		            table_2.setModel(tableModel);

		            // Close resources
		            rs.close();
		            stmt.close();
		            conn.close();
		        } catch (ClassNotFoundException ex) {
		            JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
		        }
		    }
		
			
		});
		
				comboBox.addMouseListener(new MouseAdapter() {
					@Override
					
					public void mouseClicked(MouseEvent e) {
					    try {
					    	comboBox.setBackground(new Color(239, 241, 243));
					        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
					        model.removeAllElements(); // Clear existing data
		
					        // Establish connection to the database
					        Class.forName("com.mysql.cj.jdbc.Driver");
					        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");
		
					        // Query to retrieve all column names
					        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'attendance'";
					        Statement stmt = conn.createStatement();
					        ResultSet rs = stmt.executeQuery(query);
		
					        int index = 0; // To track the index of the columns
					        while (rs.next()) {
					            if (index >= 3) { // Only add items starting from index 3
					                String columnName = rs.getString("COLUMN_NAME");
					                model.addElement(columnName);  // Add the column names to the combo box
					            }
					            index++; // Increment the index
					        }
		
					        // Close resources
					        rs.close();
					        stmt.close();
					        conn.close();
					    } catch (ClassNotFoundException ex) {
					        JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
					    } catch (SQLException ex) {
					        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
					    }
					}
						
					
				});
		
		
		
		btnBack_1 = new JButton("BACK");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMain pi = new adminMain();
				pi.setVisible(false);
				setVisible(false);
				adminMain.go();
			}
		});
		btnBack_1.setBounds(27, 698, 119, 44);
		contentPane.add(btnBack_1);
		
		
	
		
	}
}
