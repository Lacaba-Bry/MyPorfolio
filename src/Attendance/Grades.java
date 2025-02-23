package Attendance;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Grades extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	private JTextField searchField;
	private JTextField numField;
	private JTextField lateField;
	private JTextField presentField;
	private JTextField absentField;
	private JTextField gradeField;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_1_1_1_2;
	private JTextField nameField;
	private JLabel lblNewLabel_1_1_1_3;
	private JTextField sectionField;
	private JTextField courseField;
	private static JTable dateTable;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel_2_4;
	private static JTextField totalDay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Grades frame = new Grades();
					frame.setVisible(true);
					showtable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	static void go() {
	    // Create an instance of the adminMain frame
		Grades frame = new Grades();
	    
	    // Set the frame size
	    frame.setSize(860, 550);

	    // Center the frame on the screen
	    frame.setLocationRelativeTo(null);

	    // Make the frame visible
	    frame.setVisible(true);

	    // Set default close operation
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    showtable();
	}
	        

	 private static void showtable() {
	        Connection conn = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;

	        try {
	            // Establish connection to the database
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

	            // Query to retrieve only STUDENT_NUMBER and FULL_NAME columns
	            String sql = "SELECT STUDENT_NUMBER, FULL_NAME FROM attendance ORDER BY `FULL_NAME` ASC";
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
	            table.setModel(RecordTable);
	            
	            

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
	 
	 private static void showDate(String studentNumber) {
		    Connection conn = null;
		    PreparedStatement pst = null;
		    ResultSet rs = null;

		    try {
		        // Establish connection to the database
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

		        // Query to fetch data for the specific student
		        String sql = "SELECT * FROM attendance WHERE STUDENT_NUMBER = ? ";
		        pst = conn.prepareStatement(sql);
		        pst.setString(1, studentNumber); // Bind the student number to the query
		        rs = pst.executeQuery();

		        // Retrieve metadata to get column names
		        ResultSetMetaData metaData = rs.getMetaData();
		        int columnCount = metaData.getColumnCount();

		        // Create a list for column names starting from the 4th column
		        List<String> columnNames = new ArrayList<>();
		        for (int i = 4; i <= columnCount; i++) {
		            columnNames.add(metaData.getColumnName(i));  // Add column names dynamically
		        }

		        // Create a table model with dynamic column names
		        DefaultTableModel RecordTable = new DefaultTableModel(columnNames.toArray(new String[0]), 0);

		        // Loop through the ResultSet and add rows to the table model
		        while (rs.next()) {
		            // Collect data from columns starting from the 4th column
		            List<Object> rowData = new ArrayList<>();
		            for (int i = 4; i <= columnCount; i++) {
		                rowData.add(rs.getObject(i));  // Add each attendance status to row data
		            }
		            RecordTable.addRow(rowData.toArray());
		        }

		        // Set the table model to the JTable
		        dateTable.setModel(RecordTable);

		        // Update total day count
		        totalDay.setText(String.valueOf(columnNames.size()));  // Set total days based on number of columns

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

	 
	 private void updateAttendanceCounts(String studentNumber) {
		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");
		         PreparedStatement pst = conn.prepareStatement(
		             "SELECT * FROM attendance WHERE STUDENT_NUMBER = ?")) {

		        // Set the student number
		        pst.setString(1, studentNumber);

		        try (ResultSet rs = pst.executeQuery()) {
		            if (rs.next()) {
		                // Get metadata to dynamically count attendance for each date
		                ResultSetMetaData metaData = rs.getMetaData();
		                int columnCount = metaData.getColumnCount();

		                // Initialize counters for Present, Late, Absent
		                int presentCount = 0, lateCount = 0, absentCount = 0;

		                // Initialize totalDays as the number of columns starting from the 4th column
		                int totalDays = columnCount - 3; // Subtract the first 3 columns (STUDENT_NUMBER, FULL_NAME, etc.)

		                // Loop through the date columns (starting from the 4th column)
		                for (int i = 4; i <= columnCount; i++) {
		                    String status = rs.getString(i);

		                    // Count non-null status as a valid attendance entry
		                    if (status != null) {
		                        // Increment attendance counts based on status
		                        if ("Present".equalsIgnoreCase(status)) {
		                            presentCount++;
		                        } else if ("Late".equalsIgnoreCase(status)) {
		                            lateCount++;
		                        } else if ("Absent".equalsIgnoreCase(status)) {
		                            absentCount++;
		                        }
		                    }
		                }

		                // Set the values in the respective text fields
		                presentField.setText(String.valueOf(presentCount));
		                lateField.setText(String.valueOf(lateCount));
		                absentField.setText(String.valueOf(absentCount));
		                totalDay.setText(String.valueOf(totalDays)); // Set total days

		                // Calculate the attendance percentage
		                double attendancePercentage = 0;
		                if (totalDays > 0) {
		                    // Calculate attendance percentage using the corrected formula
		                    attendancePercentage = ((presentCount + (lateCount * 0.6666)) / totalDays) * 100;
		                }

		                // Set the grade (10% of the attendance percentage)
		                double grade = (attendancePercentage / 100) * 10;
		                gradeField.setText(String.format("%.2f", grade)); // Set the grade (2 decimal places)
		            } else {
		                // Clear fields if no data is found
		                presentField.setText("0");
		                lateField.setText("0");
		                absentField.setText("0");
		                totalDay.setText("0"); // Clear total days field
		                gradeField.setText("0"); // Clear grade field

		                // Clear the dateTable
		                DefaultTableModel model = (DefaultTableModel) dateTable.getModel();
		                model.setRowCount(0); // Clear rows
		            }
		        }
		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(null, "Error retrieving attendance counts: " + ex.getMessage());
		    }
		}





	 
	public Grades() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(239, 241, 243));
		contentPane.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Clear the selection when the mouse is clicked outside the table
		        table.clearSelection();  // Clears both row and column selections
		    }
		});
	
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 417, 799, 83);
		contentPane.add(scrollPane);
		

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int row = table.getSelectedRow();  // Get the clicked row

		        if (row != -1) {
		            // Get the STUDENT_NUMBER from the first column of the selected row
		            String studentNumber = (table.getValueAt(row, 0) != null) ? table.getValueAt(row, 0).toString() : "";
		            searchField.setText(studentNumber);  // Set the student number in the search field

		            // Retrieve student name from the second column (assuming it is in the second column of the table)
		            String studentName = (table.getValueAt(row, 1) != null) ? table.getValueAt(row, 1).toString() : "";

		            // Set student number and name in the respective fields
		            numField.setText(studentNumber);
		            nameField.setText(studentName);

		            // Call methods to show the attendance details
		            showDate(studentNumber);
		            updateAttendanceCounts(studentNumber);
		        }
		    }
		});

		// Disable editing for the table
		table.setDefaultEditor(Object.class, null);  // Disable cell editing
		table.setEnabled(true);

		
		
		scrollPane.setViewportView(table);
		
		
		searchField = new JTextField();
		
		searchField.addKeyListener(new KeyAdapter() {
			
		    @Override
		    public void keyReleased(KeyEvent e) {
		        try {
		            String STUDENT_NUMBER = searchField.getText();

		            // Load MySQL JDBC driver
		            Class.forName("com.mysql.cj.jdbc.Driver");

		            // Establish connection to the database
		            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");
		                 PreparedStatement pst = con.prepareStatement(
		                     "SELECT STUDENT_NUMBER, FULL_NAME FROM attendance WHERE STUDENT_NUMBER = ?")) {

		                // Bind the student number to the query
		                pst.setString(1, STUDENT_NUMBER);

		                // Execute the query
		                try (ResultSet rs = pst.executeQuery()) {
		                    if (rs.next()) {
		                        // Retrieve the values from the result set
		                        String dbStudentNumber = rs.getString("STUDENT_NUMBER");
		                        String dbFullName = rs.getString("FULL_NAME");

		                        // Set the text fields
		                        numField.setText(dbStudentNumber);
		                        nameField.setText(dbFullName);

		                        // Display the student's attendance data in dateTable
		                        showDate(dbStudentNumber);

		                        // Update present, late, and absent counts
		                        updateAttendanceCounts(dbStudentNumber);
		                    } else {
		                        // Clear the fields if no record is found
		                        numField.setText("");
		                        nameField.setText("");

		                        // Clear the attendance counts
		                        presentField.setText("0");
		                        lateField.setText("0");
		                        absentField.setText("0");
		                        totalDay.setText("0");
		                        gradeField.setText("0");

		                        // Clear the dateTable
		                        DefaultTableModel model = (DefaultTableModel) dateTable.getModel();
		                        model.setRowCount(0); // Clear rows
		                    }
		                }
		            }

		        } catch (Exception queryException) {
		            queryException.printStackTrace();
		            // Optionally, show a dialog to the user
		            JOptionPane.showMessageDialog(null, "Error querying the database: " + queryException.getMessage());
		        }
		    }
		    
		});
		
		
	        
		searchField.setBounds(108, 381, 117, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		
		
		JLabel lblNewLabel = new JLabel("STUDENT NUM");
		lblNewLabel.setBounds(23, 376, 86, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("STUDENT NUMBER");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(23, 45, 182, 30);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		numField = new JTextField();
		numField.setBounds(169, 49, 182, 27);
		numField.setBackground(new Color(239, 241, 243));
		contentPane.add(numField);
		numField.setColumns(10);
		numField.setHorizontalAlignment(JTextField.CENTER);
		numField.setEditable(false);
		numField.setFocusable(false);
		numField.setBorder(new LineBorder(Color.BLACK));
		
		lateField = new JTextField();
		lateField.setBounds(473, 381, 86, 20);
		contentPane.add(lateField);
		lateField.setColumns(10);
		lateField.setHorizontalAlignment(JTextField.CENTER);
		lateField.setEditable(false);
		lateField.setFocusable(false);
		
		
		presentField = new JTextField();
		presentField.setBounds(473, 314, 86, 20);
		contentPane.add(presentField);
		presentField.setColumns(10);
		presentField.setHorizontalAlignment(JTextField.CENTER);
		presentField.setEditable(false);
		presentField.setFocusable(false);
		
		absentField = new JTextField();
		absentField.setBounds(473, 345, 86, 20);
		contentPane.add(absentField);
		absentField.setColumns(10);
		absentField.setHorizontalAlignment(JTextField.CENTER);
		absentField.setEditable(false);
		absentField.setFocusable(false);
		
		JLabel lblNewLabel_2 = new JLabel("PRESENT");
		lblNewLabel_2.setBounds(398, 317, 65, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("ABSENT");
		lblNewLabel_2_1.setBounds(398, 348, 46, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("LATE");
		lblNewLabel_2_2.setBounds(397, 384, 46, 14);
		contentPane.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("GRADE");
		lblNewLabel_2_3.setBounds(616, 347, 61, 17);
		contentPane.add(lblNewLabel_2_3);
		
		gradeField = new JTextField();
		gradeField.setBounds(677, 345, 86, 20);
		contentPane.add(gradeField);
		gradeField.setColumns(10);
		gradeField.setHorizontalAlignment(JTextField.CENTER);
		gradeField.setEditable(false);
		gradeField.setFocusable(false);
	    
		
		lblNewLabel_1_1_1_2 = new JLabel("STUDENT NAME");
		lblNewLabel_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_2.setBounds(23, 92, 182, 30);
		contentPane.add(lblNewLabel_1_1_1_2);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(148, 95, 227, 27);
		nameField.setBackground(new Color(239, 241, 243));
		contentPane.add(nameField);
		nameField.setHorizontalAlignment(JTextField.CENTER);
		nameField.setEditable(false);
		nameField.setFocusable(false);
		nameField.setBorder(new LineBorder(Color.BLACK));
		
		lblNewLabel_1_1_1_3 = new JLabel("SECTION");
		lblNewLabel_1_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_3.setBounds(23, 139, 182, 30);
		contentPane.add(lblNewLabel_1_1_1_3);
		
		
		
		sectionField = new JTextField();
		sectionField.setBounds(169, 143, 182, 27);
		sectionField.setBackground(new Color(239, 241, 243));
	    contentPane.add(sectionField);
	    sectionField.setColumns(10);
	    sectionField.setText("BSIT-2F");
	    sectionField.setHorizontalAlignment(JTextField.CENTER);
	    sectionField.setEditable(false);
	    sectionField.setFocusable(false);
	    sectionField.setBorder(new LineBorder(Color.BLACK));
	    
	    
	    scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(397, 22, 414, 267);
	    contentPane.add(scrollPane_1);
	    
	    dateTable = new JTable();
	    scrollPane_1.setViewportView(dateTable);
	    
	    JButton btnNewButton = new JButton("BACK");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		adminMain pi = new adminMain();
				pi.setVisible(false);
				setVisible(false);
				adminMain.go();
	    	}
	    });
	    btnNewButton.setBounds(756, 376, 61, 30);
	    contentPane.add(btnNewButton);
	    
	    lblNewLabel_2_4 = new JLabel("TOTAL DAY");
	    lblNewLabel_2_4.setBounds(598, 316, 68, 17);
	    contentPane.add(lblNewLabel_2_4);
	    
	    totalDay = new JTextField();
	    totalDay.setColumns(10);
	    totalDay.setBounds(677, 314, 86, 20);
	    contentPane.add(totalDay);
	    totalDay.setHorizontalAlignment(JTextField.CENTER);
	    totalDay.setEditable(false);
	    totalDay.setFocusable(false);
	    
		
	  
	}
}
