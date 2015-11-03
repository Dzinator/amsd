package amsd.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import amsd.model.*;
import amsd.controller.*;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 5174491816909068789L;
	static MainWindow theInstance;
	private JTable table;
	private AppointmentManagementSystem ams;
	
	Object[] columnNames = {"Appointments","Status","Paid"};
	Object[][] rows = new Object[50][3];
	
	int nbRows = 0;
	
	private MainWindow() {
		initialize();
		//refresh();
	}
	
	public static MainWindow getInstance()
	  {
	    if(theInstance == null)
	    {
	      theInstance = new MainWindow();
	    }
	    return theInstance;
	  }

	public void refresh() {
		List<Appointment> appointments = ams.getAppointments();
		
		
		for(int j = 0; j < 50; j++){
			//reset all rows 
			rows[j][0] = "";
			rows[j][1] = "";
			rows[j][2] = "";
			nbRows = 0;
		}
		
		
		for(Appointment a : appointments){
			
			String entry;
			try {
				entry = a.getPatientProfile().getPerson().getName() 
						+ " WITH " + a.getEmployeeProfile().getPerson().getName()
						+ " ON " + a.getDate() +" AT "+ a.getTime()+":00";
			} catch (NullPointerException e) {
				continue;
			}
			rows[nbRows][0] = entry;
			rows[nbRows][1] = a.getSmFullName();
			rows[nbRows][2] = a.getSmFullName() == "FeePaid";
			
			nbRows++;
		}

		table.repaint();
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
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
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
		refreshButton.setSize(50, 20);
		
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
		JButton missButton = new JButton("Miss");
		missButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miss();
			}
		});
		JButton attendButton = new JButton("Attend");
		attendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				attend();
			}
		});
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(cancelButton);
		buttons.add(attendButton);
		buttons.add(missButton);
		buttons.add(payButton);
		
		getContentPane().add(refreshButton, BorderLayout.NORTH);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		
		
		refresh();
	}
	
	private void cancel() {
		/*
		 * Cancel selected appointment
		 */
		String result = "";
		Controller c = new Controller();
		
		int selectedAppointment = table.getSelectedRow();
		if(selectedAppointment < 0 || selectedAppointment >= nbRows) {
			result = "Please select an appointment";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		try {
			Appointment a = ams.getAppointment(selectedAppointment  + (ams.getAppointments().size() - nbRows));
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
		if(selectedAppointment < 0 || selectedAppointment >= nbRows) {
			result = "Please select an appointment";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		try {
			Appointment a = ams.getAppointment(selectedAppointment + (ams.getAppointments().size() - nbRows));
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
	
	private void miss() {
		/*
		 * Miss selected appointment
		 */
		String result = "";
		Controller c = new Controller();
		
		int selectedAppointment = table.getSelectedRow();
		if(selectedAppointment < 0 || selectedAppointment >= nbRows) {
			result = "Please select an appointment";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		try {
			Appointment a = ams.getAppointment(selectedAppointment  + (ams.getAppointments().size() - nbRows));
			result = c.missAppointment(a.getPatientProfile().getPerson().getName(), 
								a.getDate(), 
								a.getTime());
		} catch (NullPointerException e) {
			result = "Appointment couldn't be missed!";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		if(result == null) {
			result = "Appointment successfully missed!";
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
	
	private void attend() {
		/*
		 * Attend selected appointment
		 */
		String result = "";
		Controller c = new Controller();
		
		int selectedAppointment = table.getSelectedRow();
		if(selectedAppointment < 0 || selectedAppointment >= nbRows) {
			result = "Please select an appointment";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		try {
			Appointment a = ams.getAppointment(selectedAppointment  + (ams.getAppointments().size() - nbRows));
			//TODO replace the commented call with the attend call
			/*
			result = c.missAppointment(a.getPatientProfile().getPerson().getName(), 
								a.getDate(), 
								a.getTime());
			*/
		} catch (NullPointerException e) {
			result = "Appointment couldn't be attended!";
			JOptionPane.showMessageDialog(this, result);
			refresh();
			return;
		}
		if(result == null) {
			result = "Appointment successfully attended!";
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
