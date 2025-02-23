package Attendance;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;



public class addStudent extends JFrame {
	private static final long serialVersionUID = 1L;
	ButtonGroup bg = new ButtonGroup();    
	private JPanel contentPane;
	private JTextField studfield;
	private JTextField fnfield;
	private JTextField mnfield;
	private JTextField lnfield;
	private JTextField coursefield;
	private JRadioButton RadioRegular;
	private JRadioButton RadioIregular;

	
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
	    addStudent frame = new addStudent();
	    
	    // Set the frame size
	    frame.setSize(924, 503);

	    // Center the frame on the screen
	    frame.setLocationRelativeTo(null);

	    // Make the frame visible
	    frame.setVisible(true);

	    // Set default close operation
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    showidandtable();
	}
	        
	
	Connection sqlConn = null;
	PreparedStatement pst = null;
	static Statement st = null;
	static ResultSet rs = null;
	static int q, i , id , Deleteitem;
	private static JTable table_1;
	private JTextField searchField;

	public static void showidandtable() {
	    Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;

	    try {
	        // Establish connection to the database
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

	        // Query to retrieve student records
	        String sql = "SELECT * FROM reg_student";
	        st = conn.createStatement();
	        rs = st.executeQuery(sql);
	        
	        // Get the table model
	        DefaultTableModel RecordTable = (DefaultTableModel) table_1.getModel();
	        RecordTable.setRowCount(0); // Clear existing rows in the table
	        
	        
	            while (rs.next()) {
	                RecordTable.addRow(new Object[] {
	                    rs.getString("STUDENT_NUMBER"),
	                    rs.getString("FIRST_NAME"),
	                    rs.getString("MIDDLE_NAME"),
	                    rs.getString("LAST_NAME"),
	                    rs.getString("COURSE"),
	                    rs.getString("STATUS")
	                });
	            }
	            table_1.setModel(RecordTable);
	        
	        
	        
	        
	        

	        // Loop through the ResultSet and add rows to the table
	        while (rs.next()) {
	            ArrayList<String> columnData = new ArrayList<>();
	            columnData.add(rs.getString("STUDENT_NUMBER")); // Replace with actual column name
	            columnData.add(rs.getString("FIRST_NAME"));
	            columnData.add(rs.getString("MIDDLE_NAME"));
	            columnData.add(rs.getString("LAST_NAME"));
	            columnData.add(rs.getString("COURSE"));
	            columnData.add(rs.getString("STATUS"));

	            // Add row to the table
	            RecordTable.addRow(columnData.toArray());
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
	        }
	    }
	}
	
	private JComboBox<String> comboBox; // Changed to JComboBox<String>

	private void sortTable(String columnName) {
	    String sql = null;
	    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "")) {
	        switch (columnName) {
	            case "STUDENT NUMBER":
	                sql = "SELECT * FROM reg_student ORDER BY `STUDENT_NUMBER`";
	                break;
	            case "FIRST NAME":
	                sql = "SELECT * FROM reg_student ORDER BY `FIRST_NAME`";
	                break;
	            case "MIDDLE NAME":
	                sql = "SELECT * FROM reg_student ORDER BY `MIDDLE_NAME`";
	                break;
	            case "LAST NAME":
	                sql = "SELECT * FROM reg_student ORDER BY `LAST_NAME`";
	                break;
	            default:
	                sql = "SELECT * FROM reg_student"; // Default: no sorting if invalid column
	                break;
	        }

	        try (Statement st = con.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	            DefaultTableModel model = (DefaultTableModel) table_1.getModel();
	            model.setRowCount(0);
	            while (rs.next()) {
	                model.addRow(new Object[]{
	                        rs.getString("STUDENT_NUMBER"),
	                        rs.getString("FIRST_NAME"),
	                        rs.getString("MIDDLE_NAME"),
	                        rs.getString("LAST_NAME"),
	                        rs.getString("COURSE"),
	                        rs.getString("STATUS")
	                });
	            }
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(null, "Error sorting table: " + ex.getMessage());
	    }
	}
	
	
	
	public addStudent() {
		
	    // Set up the frame and panel
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 924, 503);
	    contentPane = new JPanel();
	    contentPane.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		table_1.clearSelection();

	    	}
	    });
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    
	    
	    // Initialize Labels and Text Fields
	    JLabel student_id = new JLabel("STUDENT ID");
	    student_id.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    student_id.setBounds(36, 105, 104, 19);
	    contentPane.add(student_id);

	    JLabel First_Name = new JLabel("FIRST NAME");
	    First_Name.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    First_Name.setBounds(36, 146, 104, 19);
	    contentPane.add(First_Name);

	    JLabel Middle_Name = new JLabel("MIDDLE NAME");
	    Middle_Name.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    Middle_Name.setBounds(36, 190, 115, 19);
	    contentPane.add(Middle_Name);

	    JLabel Last_Name = new JLabel("LAST NAME");
	    Last_Name.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    Last_Name.setBounds(36, 234, 115, 19);
	    contentPane.add(Last_Name);

	    JLabel lblNewLabel_1 = new JLabel("STUDENT REGISTER");
	    lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 33));
	    lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel_1.setBounds(389, 8, 482, 68);
	    contentPane.add(lblNewLabel_1);

	    JLabel Course = new JLabel("COURSE");
	    Course.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    Course.setBounds(36, 279, 115, 19);
	    contentPane.add(Course);

	    JLabel Status = new JLabel("STATUS");
	    Status.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    Status.setBounds(36, 323, 115, 19);
	    contentPane.add(Status);

	    // Initialize text fields
	    studfield = new JTextField();
	    studfield.setBounds(150, 105, 162, 20);
	    contentPane.add(studfield);
	    studfield.setColumns(10);

	    fnfield = new JTextField();
	    fnfield.setColumns(10);
	    fnfield.setBounds(150, 146, 162, 20);
	    contentPane.add(fnfield);

	    mnfield = new JTextField();
	    mnfield.setColumns(10);
	    mnfield.setBounds(150, 190, 162, 20);
	    contentPane.add(mnfield);

	    lnfield = new JTextField();
	    lnfield.setColumns(10);
	    lnfield.setBounds(150, 234, 162, 20);
	    contentPane.add(lnfield);
	    

	    // Initialize radio buttons as instance variables (not locally inside the constructor)
	    RadioRegular = new JRadioButton("REGULAR");
	    RadioRegular.setBackground(new Color(239, 241, 243));
	    RadioRegular.setBounds(146, 322, 71, 23);
	    contentPane.add(RadioRegular);

	    RadioIregular = new JRadioButton("IRREGULAR");
	    RadioIregular.setBackground(new Color(239, 241, 243));
	    RadioIregular.setBounds(223, 322, 109, 23);
	    contentPane.add(RadioIregular);

	    // Add radio buttons to the ButtonGroup
	    bg.add(RadioRegular);
	    bg.add(RadioIregular);
	    
	    
		JButton btnNewButton = new JButton("INSERT");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewButton.setBackground(new Color(188, 188, 188));
	    btnNewButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");
	                PreparedStatement pst = con.prepareStatement(
	                    "INSERT INTO reg_student (STUDENT_NUMBER,FIRST_NAME,MIDDLE_NAME,LAST_NAME,COURSE,STATUS)value(?,?,?,?,?,?)");

	                // Set the values from the input fields
	                pst.setString(1, studfield.getText().toUpperCase()); 
	                pst.setString(2, fnfield.getText().toUpperCase());
	                pst.setString(3, mnfield.getText().toUpperCase());
	                pst.setString(4, lnfield.getText().toUpperCase());
	                pst.setString(5, coursefield.getText());

	                // Determine the status based on selected radio button
	                String stat = "REGULAR";  // Default value
	                if (RadioIregular.isSelected()) {
	                    stat = "IRREGULAR";  // If "IRREGULAR" is selected, update status
	                }
	                pst.setString(6, stat);

	                // Execute the query
	                pst.executeUpdate();

	                // Show success message
	                JOptionPane.showMessageDialog(null, "Inserted New Student Successfully");
	                
	                showidandtable(); 

	            } catch (ClassNotFoundException ex) {
	                java.util.logging.Logger.getLogger(addStudent.class.getName())
	                    .log(java.util.logging.Level.SEVERE, "JDBC Driver not found:", ex);
	            } catch (SQLException ex) {
	                java.util.logging.Logger.getLogger(addStudent.class.getName())
	                    .log(java.util.logging.Level.SEVERE, "SQL error occurred:", ex);
	            } catch (Exception ex) {
	                java.util.logging.Logger.getLogger(addStudent.class.getName())
	                    .log(java.util.logging.Level.SEVERE, "An unexpected error occurred:", ex);
	            }
	        }
	    });
		
		btnNewButton.setBounds(150, 363, 89, 36);
		contentPane.add(btnNewButton);
		
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnDelete.setBackground(new Color(188, 188, 188));
		btnDelete.addMouseListener(new MouseAdapter() {
			
		});
		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        DefaultTableModel RecordTable = (DefaultTableModel)table_1.getModel();
		        int SelectedRows = table_1.getSelectedRow();

		        if (SelectedRows == -1) {
		            JOptionPane.showMessageDialog(null, "Please select a student to delete.");
		            return; // Exit if no row is selected
		        }

		        try {
		            // Get the STUDENT_NUMBER (id) of the selected row
		            int id = Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());
		            
		            // Confirm deletion from the user
		            int deleteItem = JOptionPane.showConfirmDialog(null, "Do you want to delete this item?", "WARNING", JOptionPane.YES_NO_OPTION);

		            if (deleteItem == JOptionPane.YES_OPTION) {
		                // Database connection and deletion
		                Class.forName("com.mysql.cj.jdbc.Driver");
		                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");
		                PreparedStatement pst = con.prepareStatement("DELETE FROM reg_student WHERE STUDENT_NUMBER = ?");

		                // Set the STUDENT_NUMBER to delete
		                pst.setInt(1, id);
		                pst.executeUpdate();

		                // Success message and table refresh
		                JOptionPane.showMessageDialog(null, "Record Deleted Successfully");
		                showidandtable();  // Refresh the table to show updated records
		                
		                // Clear fields
		                studfield.setText("");
		                fnfield.setText("");
		                mnfield.setText("");
		                lnfield.setText("");
		                coursefield.setText("");
		                bg.clearSelection(); 
		            }
		        } catch (ClassNotFoundException ex) {
		            java.util.logging.Logger.getLogger(addStudent.class.getName())
		                .log(java.util.logging.Level.SEVERE, "JDBC Driver not found:", ex);
		        } catch (SQLException ex) {
		            java.util.logging.Logger.getLogger(addStudent.class.getName())
		                .log(java.util.logging.Level.SEVERE, "SQL error occurred:", ex);
		        } catch (Exception ex) {
		            java.util.logging.Logger.getLogger(addStudent.class.getName())
		                .log(java.util.logging.Level.SEVERE, "An unexpected error occurred:", ex);
		        }
		    }
		});

					 
	
						 
				
				
			
			
			;
		btnDelete.setBounds(150, 414, 89, 36);
		contentPane.add(btnDelete);
		
		JButton btnNewButton_1_1 = new JButton("UPDATE");
		btnNewButton_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewButton_1_1.setBackground(new Color(188, 188, 188));
		btnNewButton_1_1.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        try {
		            // Load MySQL JDBC driver
		            Class.forName("com.mysql.cj.jdbc.Driver");

		            // Establish connection to the database
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

		            // Prepare the SQL query (corrected to update the 'reg_student' table)
		            PreparedStatement pst = con.prepareStatement(
		                "UPDATE reg_student SET STUDENT_NUMBER = ?, FIRST_NAME = ?, MIDDLE_NAME = ?, LAST_NAME = ?, COURSE = ?, STATUS = ? WHERE STUDENT_NUMBER = ?");

		            // Set the values from the input fields
		            pst.setString(1, studfield.getText());
		            pst.setString(2, fnfield.getText());
		            pst.setString(3, mnfield.getText());
		            pst.setString(4, lnfield.getText());
		            pst.setString(5, coursefield.getText());

		            // Determine the status based on selected radio button
		            String stat = "REGULAR";  // Default value
		            if (RadioIregular.isSelected()) {
		                stat = "IRREGULAR";  // If "IRREGULAR" is selected, update status
		            }
		            pst.setString(6, stat);

		            // Assuming the STUDENT_NUMBER is the unique identifier to update
		            pst.setString(7, studfield.getText());

		            // Execute the query
		            pst.executeUpdate();

		            // Show success message
		            JOptionPane.showMessageDialog(null, "Record Successfully updated");

		            // Refresh the table to show new data
		            showidandtable();

		        } catch (ClassNotFoundException ex) {
		            java.util.logging.Logger.getLogger(addStudent.class.getName())
		                .log(java.util.logging.Level.SEVERE, "JDBC Driver not found:", ex);
		        } catch (SQLException ex) {
		            java.util.logging.Logger.getLogger(addStudent.class.getName())
		                .log(java.util.logging.Level.SEVERE, "SQL error occurred:", ex);
		        } catch (Exception ex) {
		            java.util.logging.Logger.getLogger(addStudent.class.getName())
		                .log(java.util.logging.Level.SEVERE, "An unexpected error occurred:", ex);
		        }
		    }
		   
		});

				
				
				
			
				
				
				
				
				
			
		
		;
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btnNewButton_1_1.setBounds(258, 414, 89, 36);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnRestore = new JButton("CLEAR");
		btnRestore.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnRestore.setBackground(new Color(188, 188, 188));
		btnRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studfield.setText("");
				fnfield.setText("");
				mnfield.setText("");
				lnfield.setText("");
				bg.clearSelection();
				
				
			}
			
			
		});
	
		
		
		btnRestore.setBounds(258, 363, 89, 36);
		contentPane.add(btnRestore);
		
		JButton btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMain pi = new adminMain();
				pi.setVisible(false);
				setVisible(false);
				adminMain.go();
			}
		});
		btnNewButton_1.setBounds(793, 417, 89, 30);
		contentPane.add(btnNewButton_1);
				
	
		
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        	}});
        scrollPane.setBounds(357, 101, 519, 241);
        contentPane.add(scrollPane);
        
        table_1 = new JTable();
        table_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		        		
        		int row = table_1.rowAtPoint(e.getPoint());
                int column = table_1.columnAtPoint(e.getPoint());

                // If the click is on a particular column or row, prevent editing
                if (row >= 0 && column >= 0) {
                    // Here, you can add conditions if you want to disable editing only for certain columns or rows
                    table_1.getCellEditor(row, column).stopCellEditing(); // Stops any editing that might have started
                }
                DefaultTableModel RecordTable = (DefaultTableModel)table_1.getModel();
				int SelectedRows = table_1.getSelectedRow();
				
				studfield.setText(RecordTable.getValueAt(SelectedRows, 0).toString());
				fnfield.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
				mnfield.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
				lnfield.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
				coursefield.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
				
				if (RecordTable.getValueAt(SelectedRows, 5).toString().equals("REGULAR")) {
				    RadioRegular.setSelected(true);
				} else {
				    RadioIregular.setSelected(true);
				}
        	
        	}
        });
        table_1.setModel(new DefaultTableModel(
        	    new Object[][] {
        	        {null, null, null, null, null, null},
        	   
        	     
        	    },
        	    new String[] {
        	        "STUDENT NUMBER", "FIRST NAME", "MIDDLE NAME", "LAST NAME ", "COURSE", "STATUS"
        	    }
        	) {
        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return false; // This disables editing for all cells
        	    }
        	    
        	    
        	});
        ;
        table_1.getColumnModel().getColumn(0).setPreferredWidth(101);
        table_1.getColumnModel().getColumn(1).setPreferredWidth(124);
        table_1.getColumnModel().getColumn(2).setPreferredWidth(119);
        table_1.getColumnModel().getColumn(3).setPreferredWidth(109);
        table_1.getColumnModel().getColumn(5).setPreferredWidth(91);
        scrollPane.setViewportView(table_1);
        
        JLabel student_id_1 = new JLabel("STUDENT_NUM:");
        student_id_1.setForeground(Color.BLACK);
        student_id_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        student_id_1.setBounds(74, 41, 123, 19);
        contentPane.add(student_id_1);
        
        
        
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
                         PreparedStatement pst = con.prepareStatement("SELECT STUDENT_NUMBER, FIRST_NAME, MIDDLE_NAME, LAST_NAME, COURSE, STATUS FROM reg_student WHERE STUDENT_NUMBER = ?")) {

                        pst.setString(1, STUDENT_NUMBER);

                        // Execute the query
                        try (ResultSet rs = pst.executeQuery()) {

                            if (rs.next()) {
                                // Retrieve the values from the result set
                                String dbStudentNumber = rs.getString("STUDENT_NUMBER");
                                String dbFirstName = rs.getString("FIRST_NAME");
                                String dbMiddleName = rs.getString("MIDDLE_NAME");
                                String dbLastName = rs.getString("LAST_NAME");
                                String dbCourse = rs.getString("COURSE");
                                String dbStatus = rs.getString("STATUS");
                 

                                // Set the text fields
                                studfield.setText(dbStudentNumber);
                                fnfield.setText(dbFirstName);
                                mnfield.setText(dbMiddleName);
                                lnfield.setText(dbLastName);
                                coursefield.setText(dbCourse);

                                if ("REGULAR".equalsIgnoreCase(dbStatus)) {
                                    RadioRegular.setSelected(true);
                                    RadioIregular.setSelected(false);
                                } else if ("IRREGULAR".equalsIgnoreCase(dbStatus)) {
                                	RadioRegular.setSelected(false);
                                	RadioIregular.setSelected(true);
                                }
                                
                				
                            } else {
                                // Clear the fields if no record is found
                                studfield.setText("");
                                fnfield.setText("");
                                mnfield.setText("");
                                lnfield.setText("");
                                coursefield.setText("");
                                
                                bg.clearSelection();
                                
                            }
                        }
                    }

                } catch (Exception queryException) {
                    queryException.printStackTrace();
                    // Optionally, you can show a dialog to the user
                    JOptionPane.showMessageDialog(null, "Error querying the database: " + queryException.getMessage());
                }
            }
        	
        	
        	
        	
     
        });
        
        
        searchField.setBounds(193, 40, 186, 23);
        contentPane.add(searchField);
        searchField.setColumns(10);
        
        
        JRadioButton RadioRegular = new JRadioButton("REGULAR");
		RadioRegular.setBounds(146, 322, 71, 23);
		contentPane.add(RadioRegular);
		
		JRadioButton RadioIregular = new JRadioButton("IREGULAR");
		RadioIregular.setBounds(223, 322, 109, 23);
		contentPane.add(RadioIregular);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(RadioRegular);
		bg.add(RadioIregular);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(239, 241, 243));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setForeground(SystemColor.desktop);
		panel.setBounds(35, 11, 387, 80);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		comboBox = new JComboBox<>(); // Initialize comboBox here
		comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"STUDENT NUMBER", "FIRST NAME", "MIDDLE NAME", "LAST NAME"}));
		comboBox.addActionListener(new ActionListener() { // Add ActionListener
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String selectedColumn = (String) comboBox.getSelectedItem();
		        sortTable(selectedColumn);
		    }
		});
		comboBox.setBounds(418, 353, 115, 22);
		contentPane.add(comboBox);

		
		JLabel lblNewLabel = new JLabel("Sorted By:");
		lblNewLabel.setBounds(357, 352, 60, 25);
		contentPane.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(239, 241, 243));
		panel_1.setBounds(0, 0, 908, 464);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
			    coursefield = new JTextField();
			    coursefield.setBounds(151, 279, 160, 20);
			    panel_1.add(coursefield);
			    coursefield.setColumns(10);
			    coursefield.setText("BSIT");
			    coursefield.setHorizontalAlignment(JTextField.CENTER);
			    coursefield.setEditable(false);
	
		
	}
}
