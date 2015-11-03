package amsd.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import amsd.controller.*;
import amsd.model.*;

public class BookingPage extends JFrame {

	private static final int PATIENT_TYPE = 0;
	private static final int DENTIST_TYPE = 1;
	private static final int HYGIENIST_TYPE = 2;
	private static final int AVAILABLE_ACTION = 0;
	private static final int UNAVAILABLE_ACTION = 1;
	private static final int CHECKUP_ACTION = 0;
	private static final int CLEANUP_ACTION = 1;
	
	private static final long serialVersionUID = -4426310869335015542L;

	// UI elements
	private JLabel errorMessage;
	private JComboBox<String> personList;
	private JLabel patientLabel;
	private JButton bookButton; // book button
	private JTextField personNameTextField;
	private JLabel personNameLabel;
	private JButton addPersonButton; // add button
	private JButton updatePhoneNbButton;
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
	private Integer actionType = 0;
	private Integer selectedTime = -1;
	private Integer selectedPersonType = 0;
	

	private String[] times = { "8", "9", "10", "11", "13", "14", "15", "16" };
	private String[] employeeActions = { "available", "unavailable" };
	private String[] patientActions = { "checkup", "cleanup"  };

	// private HashMap<Integer, Event> events;

	/** Creates new form EventRegistrationPage */
	public BookingPage() {
		initComponents();
		refreshData();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// elements for booking
		personList = new JComboBox<String>(new String[0]);
		personList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedPatient = cb.getSelectedIndex();
			}
		});
		patientLabel = new JLabel();

		// visit types (for employees and patients

		visitTypeList = new JComboBox<String>(patientActions);
		visitTypeList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				actionType = cb.getSelectedIndex();
			}
		});
		visitTypeLabel = new JLabel();

		visitTimeList = new JComboBox<String>(times);
		visitTimeList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedTime = cb.getSelectedIndex();
			}
		});
		visitTimeLabel = new JLabel();

		String[] personTypes = { "Patient", "Dentist", "Hygienist" };
		personTypeList = new JComboBox<String>(personTypes);
		personTypeList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedPersonType = cb.getSelectedIndex();
				refreshPersonList();
				refreshActionList();
				
			}



		});
		personTypeLabel = new JLabel();

		bookButton = new JButton();
		addPersonButton = new JButton();
		updatePhoneNbButton = new JButton();

		// elements for adding a person
		personNameTextField = new JTextField();
		personNameLabel = new JLabel();
		personPhoneTextField = new JTextField();
		personPhoneLabel = new JLabel();

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
		addPersonButton.setText("Add Person");
		addPersonButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addPersonButtonActionPerformed(evt);
			}
		});
		
		updatePhoneNbButton.setText("Update Phone");
		updatePhoneNbButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updatePhoneButtonActionPerformed(evt);
			}
		});

		personPhoneLabel.setText("Phone:");
		dateLabel.setText("Date:");
		visitTypeLabel.setText("Action:");
		visitTimeLabel.setText("Time:");
		personTypeLabel.setText("Person Type:");

		//lines for sections
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
		
		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout
				.createParallelGroup()
				.addComponent(errorMessage)
				.addComponent(separator)
				.addComponent(separator2)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup()
												.addComponent(personTypeLabel)
												.addComponent(patientLabel)
												.addComponent(dateLabel)
												.addComponent(personNameLabel)
												.addComponent(personPhoneLabel))
								.addGroup(
										layout.createParallelGroup()
												.addComponent(personTypeList)
												.addComponent(personList)
												.addComponent(datePicker)
												.addComponent(
														personNameTextField,
														200, 200, 400)
												.addComponent(
														personPhoneTextField,
														200, 200, 400)
												.addComponent(updatePhoneNbButton))
								.addGroup(
										layout.createParallelGroup()
												.addComponent(visitTypeLabel)
												.addComponent(visitTimeLabel))
								.addGroup(
										layout.createParallelGroup()
												.addComponent(visitTypeList)
												.addComponent(visitTimeList)
												.addComponent(bookButton)
												.addComponent(addPersonButton))));

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {
				bookButton, patientLabel });
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {
				addPersonButton, personNameTextField });
		layout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { personPhoneTextField });

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(
						layout.createParallelGroup().addComponent(personTypeLabel)
								.addComponent(personTypeList))
				.addComponent(separator)
				.addGroup(
						layout.createParallelGroup().addComponent(patientLabel)
								.addComponent(personList)
								.addComponent(visitTypeLabel)
								.addComponent(visitTypeList))
				.addGroup(
						layout.createParallelGroup().addComponent(dateLabel)
								.addComponent(datePicker)
								.addComponent(visitTimeLabel)
								.addComponent(visitTimeList))
				.addComponent(bookButton)
				.addComponent(separator2)
				.addGroup(
						layout.createParallelGroup()
								.addComponent(personNameLabel)
								.addComponent(personNameTextField))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(personPhoneLabel)
								.addComponent(personPhoneTextField))
				.addGroup(
						layout.createParallelGroup().addComponent(updatePhoneNbButton)
													.addComponent(addPersonButton)));

		pack();
	}

	private void refreshPersonList() {
		AppointmentManagementSystem amsd = AppointmentManagementSystem
				.getInstance();
		personList.removeAllItems();

		// participant list
		switch (selectedPersonType) {
		case PATIENT_TYPE:
			for (PatientProfile p : amsd.getPatientProfiles()) {
				personList.addItem(p.getPerson().getName());
			}
			break;
		case DENTIST_TYPE:
			for (DentistProfile p : amsd.getDentistProfiles()) {
				personList.addItem(p.getPerson().getName());
			}
			break;
		case HYGIENIST_TYPE:
			for (HygienistProfile p : amsd.getHygienistProfiles()) {
				personList.addItem(p.getPerson().getName());
			}
			break;
		default:
			break;

		}
	}
	
	private void refreshActionList() {
		visitTypeList.removeAllItems();
		String[] actions = null;
		switch (selectedPersonType) {
		case PATIENT_TYPE:
			actions = patientActions;
			break;
		case DENTIST_TYPE:
		case HYGIENIST_TYPE:
			actions = employeeActions;
			break;
		default:
			break;
		}
		for(String s : actions){
			visitTypeList.addItem(s);
		}
	}

	private void refreshData() {
		AppointmentManagementSystem amsd = AppointmentManagementSystem
				.getInstance();
		errorMessage.setText(error);
		refreshPersonList();

		selectedPatient = -1;
		personList.setSelectedIndex(selectedPatient);

		actionType = -1;
		selectedTime = -1;
		// selectedPersonType = -1;

		visitTypeList.setSelectedIndex(actionType);
		visitTimeList.setSelectedIndex(selectedTime);
		// participant
		personNameTextField.setText("");
		// event
		personPhoneTextField.setText("");
		datePicker.getModel().setValue(null);

		// this is needed because the size of the window changes depending on
		// whether an error message is shown or not
		pack();
		
		MainWindow.getInstance().refresh();
	}

	private void addPersonButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		Controller c = new Controller();

		error = "";
		if (selectedPersonType < 0)
			error = error + "A person type must be selected! ";
		error = error.trim();
		if (error.length() == 0) {

			switch (selectedPersonType) {
			case PATIENT_TYPE:
					error = c.addPatient(personNameTextField.getText(),
							personPhoneTextField.getText());
				
				break;

			case DENTIST_TYPE:
				
					error = c.addDentist(personNameTextField.getText(),
							personPhoneTextField.getText());
								
				break;
			case HYGIENIST_TYPE:
			
					error = c.addHygienist(personNameTextField.getText(),
							personPhoneTextField.getText());
				
				break;
			}
		}
		// update visuals
		refreshData();
	}

	private void bookButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		if (selectedPatient < 0)
			error = error + "A person needs to be selected! ";
		if (actionType < 0)
			error = error + "A type needs to be selected! ";
		if (datePicker.getModel().getValue() == null)
			error = error + "A date needs to be selected! ";
		error = error.trim();
		if (error.length() == 0) {
			// call the controller
			Controller c = new Controller();
			java.sql.Date date = (java.sql.Date) datePicker.getModel()
					.getValue();
		
			
			String name = personList.getItemAt(selectedPatient);
			
			switch(selectedPersonType){
			case PATIENT_TYPE:
				if (selectedTime < 0)
					error = error + "A time needs to be selected! ";
				else {
					int time = Integer.parseInt(times[selectedTime]);
					if(actionType == CHECKUP_ACTION){
						error = c.makeDentistAppointment(name, date, time);
					} else {
						 error = c.makeHygienistAppointment(name, date, time);
					}
				}
				break;
			case DENTIST_TYPE:
			case HYGIENIST_TYPE:
				if(actionType == AVAILABLE_ACTION)
					error = c.setAvailable(name, date, true);
				else
					error = c.setAvailable(name, date, false);
				break;
			}	
		}
		// update visuals
		refreshData();
	}

	private void updatePhoneButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//update phone number of selected person
		error = "";
		if (selectedPatient < 0)
			error = error + "A person needs to be selected! ";
		if (personPhoneTextField.getText().length() == 0)
			error = error + "A phone number must be entered! ";
		
		error = error.trim();
		if (error.length() == 0) {
			// call the controller
			Controller c = new Controller();
			
			String name = personList.getItemAt(selectedPatient);
			error = c.updatePhoneNumber(name, personPhoneTextField.getText());
			
			
		}
		// update visuals
		refreshData();
	}
}
