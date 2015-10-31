package amsd.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import amsd.model.AppointmentManagementSystem;
import amsd.model.PatientProfile;

public class MainWindow {

	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private AppointmentManagementSystem ams;
	
	Object[] columnNames = {"Appointments","Missed","Paid"};
	Object[][] rows = new Object[50][3];
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		ams = AppointmentManagementSystem.getInstance();
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 48, 426, 263);
		frame.getContentPane().add(scrollPane);
		table = new JTable(rows,columnNames);
		scrollPane.setViewportView(table);
		
		
		textField = new JTextField();
		textField.setBounds(167, 14, 114, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(57, 16, 114, 15);
		frame.getContentPane().add(lblPatientName);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String patientName = textField.getText();
				List<PatientProfile> patients = ams.getPatientProfiles();
				for(PatientProfile patient : patients){
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					rows[0][0] = patientName;
					rows[1][0] = patientName;
					if(patient.getPerson().getName().equals(patientName)){
						rows[0][0] = patientName;
					}
					model.setValueAt("hello", 0, 0);
					table.repaint();
					frame.repaint();
					frame.pack();
					}
			}
		});
		btnEnter.setBounds(294, 11, 117, 25);
		frame.getContentPane().add(btnEnter);
		
	}
}
