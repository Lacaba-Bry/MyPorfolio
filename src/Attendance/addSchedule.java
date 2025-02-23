package Attendance;

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
import javax.swing.JButton;
import com.raven.datechooser.DateChooser;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class addSchedule extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


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
	    addSchedule frame = new addSchedule();
	    
	    // Set the frame size
	    frame.setSize(1200, 700);

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
	private static JTable table_2;
	private JTextField Datefield;
	private JButton btnNewButton_1;


	
	

	
	private static void showtable() {
	    Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;
	    
	    try {
	        // Establish connection to the database
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

	        // Query to retrieve student records
	        String sql = "SELECT * FROM attendance"; // Get all columns (including dynamically added ones)
	        st = conn.createStatement();
	        rs = st.executeQuery(sql);
	        
	        
	        // Get the table model
	        DefaultTableModel RecordTable = (DefaultTableModel) table_2.getModel();
	        RecordTable.setRowCount(0); // Clear existing rows in the table

	        // Ensure columns are not added multiple times
	        if (RecordTable.getColumnCount() == 0) {
	            // Add static columns (ID_NUM, STUDENT_NUMBER, FULL_NAME)
	            RecordTable.addColumn("ID_NUM");
	            RecordTable.addColumn("STUDENT_NUMBER");
	            RecordTable.addColumn("FULL_NAME");
	        }

	        // Dynamically add date columns (if not already added)
	        for (int i = 4; i <= rs.getMetaData().getColumnCount(); i++) {
	            String columnName = rs.getMetaData().getColumnName(i);
	            // Check if the column already exists
	            boolean columnExists = false;
	            for (int j = 0; j < RecordTable.getColumnCount(); j++) {
	                if (RecordTable.getColumnName(j).equals(columnName)) {
	                    columnExists = true;
	                    break;
	                }
	            }
	            // If column doesn't exist, add it
	            if (!columnExists) {
	                RecordTable.addColumn(columnName);
	            }
	        }
	        
	        // Add data to rows
	        while (rs.next()) {
	            ArrayList<Object> rowData = new ArrayList<>();
	            rowData.add(rs.getString("ID_NUM"));
	            rowData.add(rs.getString("STUDENT_NUMBER"));
	            rowData.add(rs.getString("FULL_NAME"));

	            // Add the attendance data for each student (dates)
	            for (int i = 4; i <= rs.getMetaData().getColumnCount(); i++) {
	                rowData.add("");
	          
	      
	            }

	            RecordTable.addRow(rowData.toArray());
	        }
	        
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

	        // Apply the renderer to all columns
	        for (int i = 0; i < table_2.getColumnCount(); i++) {
	            table_2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
	        
            
	      
	        // Set the model to update the table
	        table_2.setModel(RecordTable);
	        
	    } catch (ClassNotFoundException ex) {
	        JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
	            if (conn != null) conn.close();
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "Error closing resources: " + ex.getMessage());
	        }
	    }
	}


	private static void refreshTable() {
	    // Clear any existing data in the table by resetting the model
	    DefaultTableModel model = (DefaultTableModel) table_2.getModel();
	    model.setRowCount(0);  // Clear existing rows
	    model.setColumnCount(0);  // Clear existing columns

	    // Now reload the table with the updated data from the database
	    showtable();  // Reload the data from the database
	}

	/**
	 * Create the frame.
	 */
	
	public addSchedule() {
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 1200, 700);
	    contentPane = new JPanel();
	    contentPane.addMouseListener(new MouseAdapter() {
	    	
	    });
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    contentPane.setBackground(new Color(239, 241, 243));

	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(293, 81, 881, 569);
	    contentPane.add(scrollPane);

	    table_2 = new JTable();
	    table_2.setRowHeight(25);
	    table_2.addMouseListener(new MouseInputAdapter() {
	        @Override
	        public void mouseReleased(MouseEvent e) {
	            if (SwingUtilities.isRightMouseButton(e)) {
	                int col = table_2.columnAtPoint(e.getPoint());

	                // Check if it's a date column (column index >= 4)
	                if (col >= 3) {
	                    String dateColumn = table_2.getColumnName(col);
	                    String date = dateColumn.substring(4).replace("_", "-"); // Extract date from column name

	                    int response = JOptionPane.showConfirmDialog(null,
	                            "Are you sure you want to delete the attendance column for " + date + "?",
	                            "Delete Date Column", JOptionPane.YES_NO_OPTION);

	                    if (response == JOptionPane.YES_OPTION) {
	                        try {
	                            Class.forName("com.mysql.cj.jdbc.Driver");
	                            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

	                            // SQL query to drop the column
	                            String dropColumnSQL = "ALTER TABLE attendance DROP COLUMN " + dateColumn;
	                            Statement stmt = conn.createStatement();
	                            stmt.executeUpdate(dropColumnSQL);

	                            stmt.close();
	                            conn.close();

	                            // After deleting the column, refresh the table
	                            refreshTable();  // This will clear the table and reload the data
	                            JOptionPane.showMessageDialog(null, "Column for date " + date + " deleted successfully!");
	                        } catch (ClassNotFoundException | SQLException ex) {
	                            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
	                        }
	                    }
	                }
	                
	                
	                
	            }
	        }}
	    
	    	
	    
	    );
	    
	    
	    
	    DefaultTableModel tableModel = new DefaultTableModel(
	    new Object[]{"ID_NUM", "STUDENT_NUM", "FULL_NAME",}, 0
	        
	        
	    );
	    table_2.setModel(tableModel); // Set the model to table_2
	    scrollPane.setViewportView(table_2);

	
	    Datefield = new JTextField();
	    Datefield.setBounds(10, 28, 181, 38);
	    contentPane.add(Datefield);
	    Datefield.setColumns(10);

	    
	    DateChooser dateChooser = new DateChooser();
	    dateChooser.setDateFormat("MMM_dd_yyyy");
	    dateChooser.setTextRefernce(Datefield);
	    dateChooser.setBounds(10, 77, 272, 222);
	    contentPane.add(dateChooser);
	    
	   
	    dateChooser.setVisible(false);

	    // MouseListener for Datefield
	    Datefield.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	dateChooser.setVisible(!dateChooser.isVisible());
	        }
	        
	    });
	    
	    
	    
	  
	    JButton btnNewButton = new JButton("ADD DATE");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	            // Get the selected date from the Datefield
	            String selectedDate = Datefield.getText();

	            if (selectedDate.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Please select a date before adding.");
	                return;
	            }

	            // Format the date to create a valid column name
	            String formattedDate = selectedDate.replace("-", "_"); // e.g., 2024-12-14 -> 2024_12_14
	            String columnName = "" + formattedDate;

	            try {
	                // Establish connection
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

	                // Check if the column already exists
	                String checkColumnSQL = "SELECT * FROM information_schema.columns " +
	                                        "WHERE table_name = 'attendance' AND column_name = ?";
	                PreparedStatement checkStmt = conn.prepareStatement(checkColumnSQL);
	                checkStmt.setString(1, columnName);
	                ResultSet rs = checkStmt.executeQuery();

	                if (!rs.next()) {
	                    // If column doesn't exist, add it
	                    String addColumnSQL = "ALTER TABLE attendance ADD COLUMN " + columnName + " VARCHAR(255)";
	                    Statement alterStmt = conn.createStatement();
	                    alterStmt.executeUpdate(addColumnSQL);
	                    alterStmt.close();

	                    JOptionPane.showMessageDialog(null, "Column for date " + selectedDate + " added successfully!");
	                } else {
	                    JOptionPane.showMessageDialog(null, "Column for date " + selectedDate + " already exists.");
	                }
	                
	                
	                
	                
	                showtable();
	                
	                
	                // Close resources
	                rs.close();
	                checkStmt.close();
	                conn.close();
	                showtable();
	            } catch (ClassNotFoundException ex) {
	                JOptionPane.showMessageDialog(null, "Database Driver not found: " + ex.getMessage());
	            } catch (SQLException ex) {
	                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
	            }
	        }
	    });

	    btnNewButton.setBounds(212, 28, 101, 38);
	    contentPane.add(btnNewButton);
	    
	    btnNewButton_1 = new JButton("BACK");
	    btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		adminMain pi = new adminMain();
				pi.setVisible(false);
				setVisible(false);
				adminMain.go();
	    	}
	    });
	    btnNewButton_1.setBounds(24, 612, 118, 38);
	    contentPane.add(btnNewButton_1);
	
	
	
	
	}
}
