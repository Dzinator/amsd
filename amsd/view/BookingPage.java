package amsd.view;

import java.awt.Color;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import amsd.controller.*;
import amsd.model.*;

public class BookingPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;
	
	// UI elements
	private JLabel errorMessage;
	private JComboBox<String> patientList;
	private JLabel patientLabel;
	private JButton bookButton;		//book button
	private JTextField personNameTextField;
	private JLabel personNameLabel;
	private JButton addPersonButton;	//add button
	private JTextField personPhoneTextField;
	private JLabel personPhoneLabel;
	
	private JComboBox<String> visitTypeList;
	private JLabel visitTypeLabel;
	private JComboBox<String> visitTimeList;
	private JLabel visitTimeLabel;
	private JComboBox<String> personTypeList;
	private JLabel personTypeLabel;
	
	
	private JDatePickerImpl datePicker;
	private JLabel dateLabel;
	
	// data elements
	private String error = null;
	private Integer selectedPatient = -1;
	private HashMap<Integer, Person> patients;
	private Integer selectedType = -1;
	private Integer selectedTime = -1;
	private Integer selectedPersonType = -1;
	//private HashMap<Integer, Event> events;
	
	/** Creates new form EventRegistrationPage */
	public BookingPage() {
		initComponents();
		refreshData();
	}

	/** This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		// elements for booking
		patientList = new JComboBox<String>(new String[0]);
		patientList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedPatient = cb.getSelectedIndex();
			}
		});
		patientLabel = new JLabel();
		
		//visit types (for employees and patients
		String[] types = {"available", "unavailable", "checkup", "cleanup"};
		
		visitTypeList = new JComboBox<String>(types);
		visitTypeList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedType = cb.getSelectedIndex();
			}
		});
		visitTypeLabel = new JLabel();
		
		String[] times = {"8","9","10","11","13","14","15","16"};
		visitTimeList = new JComboBox<String>(times);
		visitTimeList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTime = cb.getSelectedIndex();
			}
		});
		visitTimeLabel = new JLabel();
		
		String[] personTypes = {"Patient","Dentist","Hygienist"};
		personTypeList = new JComboBox<String>(personTypes);
		personTypeList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedPersonType = cb.getSelectedIndex();
			}
		});
		personTypeLabel = new JLabel();
		
		
		bookButton = new JButton();
		
		// elements for adding a person
		personNameTextField = new JTextField();
		personNameLabel = new JLabel();
		personPhoneTextField = new JTextField();
		personPhoneLabel = new JLabel();
		
		addPersonButton = new JButton();
		
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		dateLabel = new JLabel();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Booking");

		patientLabel.setText("Select Person:");
		visitTypeLabel.setText("Select Action:");
		bookButton.setText("Schedule");
		bookButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bookButtonActionPerformed(evt);
			}
		});

		personNameLabel.setText("Name:");
		addPersonButton.setText("Add Participant");
		addPersonButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addPersonButtonActionPerformed(evt);
			}
		});

		personPhoneLabel.setText("Phone:");
		dateLabel.setText("Date:");
		visitTypeLabel.setText("Type:");
		visitTimeLabel.setText("Time:");
		personTypeLabel.setText("Person Type:");
		
		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(patientLabel)
								.addComponent(dateLabel)
								.addComponent(personNameLabel)
								.addComponent(personPhoneLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(patientList)
								.addComponent(datePicker)
								.addComponent(personNameTextField, 200, 200, 400)
								.addComponent(personPhoneTextField, 200, 200, 400))
						.addGroup(layout.createParallelGroup()
								.addComponent(visitTypeLabel)
								.addComponent(visitTimeLabel)
								.addComponent(personTypeLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(visitTypeList)
								.addComponent(visitTimeList)
								.addComponent(bookButton)
								.addComponent(personTypeList)
								.addComponent(addPersonButton)))
				);

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {bookButton, patientLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addPersonButton, personNameTextField});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {personPhoneTextField});
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(patientLabel)
						.addComponent(patientList)
						.addComponent(visitTypeLabel)
						.addComponent(visitTypeList))
				.addGroup(layout.createParallelGroup()
						.addComponent(dateLabel)
						.addComponent(datePicker)
						.addComponent(visitTimeLabel)
						.addComponent(visitTimeList))
				.addComponent(bookButton)		
				.addGroup(layout.createParallelGroup()
						.addComponent(personNameLabel)
						.addComponent(personNameTextField)
						.addComponent(personTypeLabel)
						.addComponent(personTypeList))
				.addGroup(layout.createParallelGroup()
						.addComponent(personPhoneLabel)
						.addComponent(personPhoneTextField))
				.addGroup(layout.createParallelGroup()						
						.addComponent(addPersonButton))
				);
		
		pack();
	}

	private void refreshData() {
		AppointmentManagementSystem amsd = AppointmentManagementSystem.getInstance();
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// participant list
			patients = new HashMap<Integer, Person>();
			patientList.removeAllItems();
			Iterator<Person> pIt = amsd.getPersons().iterator();
			Integer index = 0;
			while (pIt.hasNext()) {
				Person p = pIt.next();
				patients.put(index, p);
				patientList.addItem(p.getName());
				index++;
			}
			selectedPatient = -1;
			patientList.setSelectedIndex(selectedPatient);
			
			selectedType = -1;
			selectedTime = -1;
			selectedPersonType = -1;
			
			visitTypeList.setSelectedIndex(selectedType);
			visitTimeList.setSelectedIndex(selectedTime);
			personTypeList.setSelectedIndex(selectedPersonType);
			// participant
			personNameTextField.setText("");
			// event
			personPhoneTextField.setText("");
			datePicker.getModel().setValue(null);
		}

		// this is needed because the size of the window changes depending on whether an error message is shown or not
		pack();
	}

	private void addPersonButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		Controller c = new Controller();
		
		error = "";
		if (personNameTextField.getText().length() == 0)
			error = error + "A person name must be entered! ";
		if (personPhoneTextField.getText().length() == 0)
			error = error + "A phone number must be entered! ";
		if (selectedPersonType < 0)
			error = error + "A person type must be selected! ";
		error = error.trim();
		if (error.length() == 0) {
			if(selectedType == 0) {
				//add patient
				try {
					error = c.addPatient(personNameTextField.getText(), Integer.parseInt(personPhoneTextField.getText()));
				} catch (NumberFormatException e) {
					error = error + "Enter a valid phone number(no hyphens or parenthesis)!";
				}
			}
			else if(selectedType == 1) {
				//add dentist
				try {
					error = c.addDentist(personNameTextField.getText(), Integer.parseInt(personPhoneTextField.getText()));
				} catch (NumberFormatException e) {
					error = error + "Enter a valid phone number(no hyphens or parenthesis)!";
				}
			}
			else {
				//add hygienist
				try {
					error = c.addHygienist(personNameTextField.getText(), Integer.parseInt(personPhoneTextField.getText()));
				} catch (NumberFormatException e) {
					error = error + "Enter a valid phone number(no hyphens or parenthesis)!";
				}
			}
		}
		
		// update visuals
		refreshData();
	}
	
	private void bookButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		if (selectedPatient < 0)
			error = error + "A person needs to be selected! ";
		if (selectedType < 0)
			error = error + "A type needs to be selected! ";
		if (datePicker.getModel().getValue() == null)
			error = error + "A date needs to be selected! ";
		error = error.trim();
		if (error.length() == 0) {
			// call the controller
			Controller c = new Controller();
			//TODO handle different requests
			Person p = patients.get(selectedPatient);
			java.sql.Date date = (java.sql.Date) datePicker.getModel().getValue();
			int time = 9;
			if(selectedType < 2) {
				//employee scheduling availabilities
				if(!p.hasEmployeeProfile())
					error = error + "A valid employee needs to be selected! ";
				else if(selectedType == 0) {
					//set day available --> DON'T CARE ABOUT TIME
					error = c.setAvailable(p.getName(), date, true);
				}
				else {
					//set day unavailable --> DON'T CARE ABOUT TIME
					error = c.setAvailable(p.getName(), date, false);
				}
			}
			else {
				//patient appointment
				if (selectedTime < 0)
					error = error + "A time needs to be selected! ";
				/*TODO fix hasPatientProfile and uncomment this
				else if(!p.hasPatientProfile())
					error = error + "A valid patient needs to be selected! ";
				*/
				else if(selectedType == 2){
					//check-up
					error = c.makeDentistAppointment(p.getName(), date, time);
				}
				else {
					//clean-up
					error = c.makeHygienistAppointment(p.getName(), date, time);
				}
			}
			
		}
		// update visuals
		refreshData();			
	}

}

