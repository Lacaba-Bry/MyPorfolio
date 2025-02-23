package Attendance;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;


public class adminMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTime;
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
	    adminMain frame = new adminMain();
	    
	    // Set the frame size
	    frame.setSize(1080, 690);

	    // Center the frame on the screen
	    frame.setLocationRelativeTo(null);

	    // Make the frame visible
	    frame.setVisible(true);

	    // Set default close operation
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    showandtable();
	}
	        


	

	
	
	
	
	
	
	public void time() {
	    Calendar cal = Calendar.getInstance();
	    int second = cal.get(Calendar.SECOND);
	    int minute = cal.get(Calendar.MINUTE);
	    int hour = cal.get(Calendar.HOUR);  // 12-hour format
	    int amPm = cal.get(Calendar.AM_PM);  // AM or PM

	    // Adjust the hour for standard time (AM/PM)
	    String period = (amPm == Calendar.AM) ? "AM" : "PM";
	    
	    // If hour == 0 (midnight), set it to 12
	    if (hour == 0) {
	        hour = 12;
	    } 
	    // If hour > 12 (post-noon), adjust it to standard 12-hour format
	    else if (hour > 12) {
	        hour -= 12;
	    }

	    // Update the label with the formatted time
	    lblTime.setText("TIME: " + String.format("%02d:%02d:%02d %s", hour, minute, second, period));
	}
	
	
	
	/**
	 * Create the frame.
	 */
	
static Statement st = null;
static ResultSet rs = null;
private static JTable Admintable;

private static void showandtable() {
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    
    try {
        // Establish connection to the database
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "");

        // Query to retrieve student records
        String sql = "SELECT * FROM attendance ORDER BY FULL_NAME ASC"; // Get all columns (including dynamically added ones)
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        
        
        // Custom table model to make the table non-editable
        DefaultTableModel RecordTable = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };

        // Set the table model to the table
        Admintable.setModel(RecordTable);
        RecordTable.setRowCount(0); // Clear existing rows in the t

        // Ensure columns are not added multiple times
        if (RecordTable.getColumnCount() == 0) {
            // Add static columns (ID_NUM, STUDENT_NUMBER, FULL_NAME)
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
            rowData.add(rs.getString("STUDENT_NUMBER"));
            rowData.add(rs.getString("FULL_NAME"));

            // Add the attendance data for each student (dates)
            for (int i = 4; i <= rs.getMetaData().getColumnCount(); i++) {
            	String columnName = rs.getMetaData().getColumnName(i);
            	rowData.add(rs.getString(columnName));
          
      
            }

            RecordTable.addRow(rowData.toArray());
        }
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Apply the renderer to all columns
        for (int i = 0; i < Admintable.getColumnCount(); i++) {
        	Admintable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Disable row and column selection
        Admintable.setRowSelectionAllowed(false);
        Admintable.setColumnSelectionAllowed(false);
        
        
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
	 
	public adminMain() {
		setResizable(false);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 690);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color (239, 241, 243));
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("");
		Image images = new ImageIcon(this.getClass().getResource("/LOGOSS.png")).getImage();
		lblNewLabel_3.setIcon(new ImageIcon(images)); // Corrected setting of the icon
		lblNewLabel_3.setBounds(58, 11, 132, 109);
		contentPane.add(lblNewLabel_3);
		
		
		JButton btnNewButton = new JButton("REFRESH");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewButton.setBackground(new Color(182, 182, 182));
		btnNewButton.setBounds(33, 138, 144, 44);
		contentPane.add(btnNewButton);
		
		JButton btnAddStudent = new JButton("EDIT STUDENT");
		btnAddStudent.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnAddStudent.setBackground(new Color(182, 182, 182));
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStudent pi = new addStudent();
				pi.setVisible(false);
				setVisible(false);
				addStudent.go();
			}
		});
		btnAddStudent.setBounds(33, 204, 144, 44);
		contentPane.add(btnAddStudent);
		
		JButton btnDashboard = new JButton("DASHBOARD");
		btnDashboard.setBackground(new Color(182, 182, 182));
		btnDashboard.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Display a message dialog when the button is clicked
		        JOptionPane.showMessageDialog(null, "updates coming soon.", "Information", JOptionPane.INFORMATION_MESSAGE);
		    }
		});

		btnDashboard.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnDashboard.setBounds(33, 439, 144, 44);
		contentPane.add(btnDashboard);
		
		JButton btnAbout = new JButton("ABOUT");
		btnAbout.setBackground(new Color(182, 182, 182));
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JOptionPane.showMessageDialog(null, "updates coming soon.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnAbout.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnAbout.setBounds(33, 494, 144, 44);
		contentPane.add(btnAbout);
		
		JButton btnBack = new JButton("Logout");
		btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnBack.setBackground(new Color(182, 182, 182));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				        // Show confirmation dialog
				 int response = JOptionPane.showConfirmDialog(null, 
				  "Are you sure you want to logout?", 
				  "Logout Confirmation", 
				  JOptionPane.YES_NO_OPTION, 
				   JOptionPane.QUESTION_MESSAGE);
				        
				        // Check which button was pressed (Yes or No)
				 if (response == JOptionPane.YES_OPTION) {
				            // User clicked Yes
				 JOptionPane.showMessageDialog(null, "You have logged out.");
				 mainLogin pi = new mainLogin();
				 pi.setVisible(false);
				 setVisible(false);
				 mainLogin.go();
				 } else {
				   // User clicked No, do nothing or close the dialog
				            JOptionPane.showMessageDialog(null, "You chose not to logout.");
				        
				        }
				    }
			
				});	
				
		
		btnBack.setBounds(106, 563, 85, 30);
		contentPane.add(btnBack);
		
		JLabel lblNewLabel_1 = new JLabel("Filter By:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(200, 43, 58, 14);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(200, 64, 124, 22);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1_1 = new JLabel("Sort By:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(392, 43, 58, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Show data");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1.setBounds(584, 43, 93, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(390, 64, 124, 22);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_1_1 = new JComboBox();
		comboBox_1_1.setBounds(584, 64, 124, 22);
		contentPane.add(comboBox_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("EXPORT TO EXCEL");
		btnNewButton_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewButton_1_1_1.setBackground(new Color(182, 182, 182));
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1_1_1.setBounds(760, 42, 132, 44);
		contentPane.add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1_1_1_1 = new JButton("COMPUTED GRADE");
		btnNewButton_1_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewButton_1_1_1_1.setBackground(new Color(182, 182, 182));
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grades pi = new Grades();
				pi.setVisible(false);
				setVisible(false);
				Grades.go();

			}
		});
		btnNewButton_1_1_1_1.setBounds(908, 43, 132, 44);
		contentPane.add(btnNewButton_1_1_1_1);
		
		lblTime = new JLabel("TIME:");
		lblTime.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblTime.setBounds(20, 604, 181, 47);  // Adjusted for better space
		contentPane.add(lblTime);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					
										

				
				
			
				
				}});
		
		
		scrollPane.setBounds(201, 138, 840, 502);
		contentPane.add(scrollPane);
		
		
		Admintable = new JTable(new DefaultTableModel());
		Admintable.setRowHeight(30);
		scrollPane.setViewportView(Admintable);
		
		
		JButton btnAddSchedule = new JButton("ADD SCHEDULE");
		btnAddSchedule.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnAddSchedule.setBackground(new Color(182, 182, 182));
		btnAddSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSchedule pi = new addSchedule();
				pi.setVisible(false);
				setVisible(false);
				addSchedule.go();
	            
			}
		});
		btnAddSchedule.setBounds(33, 271, 144, 44);
		contentPane.add(btnAddSchedule);
		
		JButton btnNewButton_1_1_1_2 = new JButton("MARK ATTENDANCE");
		btnNewButton_1_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewButton_1_1_1_2.setBackground(new Color(182, 182, 182));
		btnNewButton_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markAttendance pi = new markAttendance();
				pi.setVisible(false);
				setVisible(false);
				markAttendance.go();
			
		}});
		
		
		
		
		btnNewButton_1_1_1_2.setBounds(33, 338, 144, 44);
		contentPane.add(btnNewButton_1_1_1_2);
		
		JButton btnBack_2 = new JButton("Back");
		btnBack_2.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnBack_2.setBackground(new Color(182, 182, 182));
		btnBack_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBack_2.setBounds(10, 563, 85, 30);
		contentPane.add(btnBack_2);
		
		Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time();  // Call the time method every second
            }
        });
		timer.start();  
		}
	}



