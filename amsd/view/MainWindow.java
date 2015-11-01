package amsd.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import amsd.model.*;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 5174491816909068789L;
	
	//private JFrame frame;
	private JTable table;
	private JTextField textField;
	private AppointmentManagementSystem ams;
	
	Object[] columnNames = {"Appointments","Missed","Paid"};
	Object[][] rows = new Object[50][3];
	/**
	 * Launch the application.
	 
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
	*/

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		//refresh();
	}

	private void refresh() {
		String patientName = "";
		List<Person> patients = ams.getPersons();
		List<Appointment> appointments = ams.getAppointments();
		int i = 0;
		for(Appointment a : appointments){
			//DefaultTableModel model = (DefaultTableModel)table.getModel();
			String entry = a.getPatientProfile().getPerson().getName() 
					+ " WITH " + a.getEmployeeProfile().getPerson().getName()
					+ " ON " + a.getDate() +" AT "+ a.getTime()+":00";
			rows[i][0] = entry;
			rows[i][1] = a.getSmFullName() == "Canceled";
			rows[i][1] = a.getSmFullName() == "FeePaid";
			
			
			table.repaint();

			i++;
		}
		
		pack();
		repaint();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		ams = AppointmentManagementSystem.getInstance();
		
		
		//frame = new JFrame();
		setBounds(700, 200, 600, 400);
		setTitle("Appointments");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		table = new JTable(rows,columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		//scrollPane.setBounds(12, 48, 426, 263);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		//scrollPane.setViewportView(table);
		
		/*
		textField = new JTextField();
		textField.setBounds(167, 14, 114, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(57, 16, 114, 15);
		getContentPane().add(lblPatientName);
		
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
					refresh();
					}
			}
		});
		btnEnter.setBounds(294, 11, 117, 25);
		getContentPane().add(btnEnter);
		*/
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		JButton payButton = new JButton("Pay");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pay();
			}
		});
		getContentPane().add(refreshButton, BorderLayout.LINE_END);
		getContentPane().add(cancelButton, BorderLayout.NORTH);
		getContentPane().add(payButton, BorderLayout.SOUTH);
	}
	
	private void cancel() {
		// TODO Auto-generated method stub
		
	}
	
	private void pay() {
		// TODO Auto-generated method stub
		
	}
}
