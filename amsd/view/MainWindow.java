package amsd.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import amsd.model.*;
import amsd.controller.*;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 5174491816909068789L;
	
	private JTable table;
	private AppointmentManagementSystem ams;
	
	Object[] columnNames = {"Appointments","Missed","Paid"};
	Object[][] rows = new Object[50][3];
	
	public MainWindow() {
		initialize();
		//refresh();
	}

	private void refresh() {
		List<Appointment> appointments = ams.getAppointments();
		int i = 0;
		for(Appointment a : appointments){
			if(a == null) continue;
			String entry;
			try {
				entry = a.getPatientProfile().getPerson().getName() 
						+ " WITH " + a.getEmployeeProfile().getPerson().getName()
						+ " ON " + a.getDate() +" AT "+ a.getTime()+":00";
			} catch (NullPointerException e) {
				continue;
			}
			rows[i][0] = entry;
			rows[i][1] = a.getSmFullName() == "Canceled";
			rows[i][2] = a.getSmFullName() == "FeePaid";
			
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
		
		setBounds(700, 200, 600, 400);
		setTitle("Appointments");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		table = new JTable(rows,columnNames);
		table.getColumnModel().getColumn(0).setPreferredWidth(310);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pay();
			}
		});
		getContentPane().add(refreshButton, BorderLayout.LINE_END);
		getContentPane().add(cancelButton, BorderLayout.NORTH);
		getContentPane().add(payButton, BorderLayout.SOUTH);
		
		refresh();
	}
	
	private void cancel() {
		/*
		 * Cancel selected appointment
		 */
		String result = "";
		Controller c = new Controller();
		
		int selectedAppointment = table.getSelectedRow();
		if(selectedAppointment >= ams.getAppointments().size()) {
			result = "Please select an appointment";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		try {
			Appointment a = ams.getAppointment(selectedAppointment);
			result = c.cancelAppointment(a.getPatientProfile().getPerson().getName(), 
								a.getDate(), 
								a.getTime());
		} catch (NullPointerException e) {
			result = "Appointment couldn't be canceled!";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		if(result == null) {
			result = "Appointment successfully canceled!";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		result.trim();
		if(result.length() > 0) {
			JOptionPane.showMessageDialog(this, result);
		}
		
		refresh();
	}
	
	private void pay() {
		/*
		 * Pay selected appointment
		 */
		String result = "";
		Controller c = new Controller();
		
		int selectedAppointment = table.getSelectedRow();
		if(selectedAppointment >= ams.getAppointments().size()) {
			result = "Please select an appointment";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		try {
			Appointment a = ams.getAppointment(selectedAppointment);
			result = c.payFee(a.getPatientProfile().getPerson().getName(), 
								a.getDate(), 
								a.getTime());
		} catch (NullPointerException e) {
			result = "Appointment couldn't be paid!";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		if(result == null) {
			result = "Appointment successfully paid!";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		result.trim();
		if(result.length() > 0) {
			JOptionPane.showMessageDialog(this, result);
		}
		
		refresh();
		
	}
}
