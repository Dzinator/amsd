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
	/*
	private JSpinner startTimeSpinner;
	private JLabel startTimeLabel;
	private JSpinner endTimeSpinner;
	private JLabel endTimeLabel;
	private JButton addEventButton;
	*/
	
	// data elements
	private String error = null;
	private Integer selectedPatient = -1;
	private HashMap<Integer, PatientProfile> patients;
	private Integer selectedType = -1;
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
		
		//TODO other comboLists
		
		
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
		/*
		startTimeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor); // will only show the current time
		startTimeLabel = new JLabel();
		endTimeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor); // will only show the current time
		endTimeLabel = new JLabel();
		addEventButton = new JButton();
		*/

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
		/*
		startTimeLabel.setText("Start Time:");
		endTimeLabel.setText("End time:");
		addEventButton.setText("Add Event");
		addEventButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addEventButtonActionPerformed(evt);
			}
		});
		*/
		
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
			patients = new HashMap<Integer, PatientProfile>();
			patientList.removeAllItems();
			Iterator<Participant> pIt = amsd.getParticipants().iterator();
			Integer index = 0;
			while (pIt.hasNext()) {
				Participant p = pIt.next();
				patients.put(index, p);
				patientList.addItem(p.getName());
				index++;
			}
			selectedPatient = -1;
			patientList.setSelectedIndex(selectedPatient);
			// event list
			events = new HashMap<Integer, Event>();
			visitTypeList.removeAllItems();
			Iterator<Event> eIt = amsd.getEvents().iterator();
			index = 0;
			while (eIt.hasNext()) {
				Event e = eIt.next();
				events.put(index, e);
				visitTypeList.addItem(e.getName());
				index++;
			}
			selectedEvent = -1;
			visitTypeList.setSelectedIndex(selectedEvent);
			// participant
			personNameTextField.setText("");
			// event
			personPhoneTextField.setText("");
			datePicker.getModel().setValue(null);
			startTimeSpinner.setValue(new Date()); 
			endTimeSpinner.setValue(new Date());
		}

		// this is needed because the size of the window changes depending on whether an error message is shown or not
		pack();
	}

	private void addPersonButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		EventRegistrationController erc = new EventRegistrationController();
		error = erc.createParticipant(personNameTextField.getText());
		// update visuals
		refreshData();
	}

	private void addEventButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// call the controller
		EventRegistrationController erc = new EventRegistrationController();
		// JSpinner actually returns a date and time
		// force the same date for start and end time to ensure that only the times differ
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((Date) startTimeSpinner.getValue());
		calendar.set(2000, 1, 1);
		Time startTime = new Time(calendar.getTime().getTime());
		calendar.setTime((Date) endTimeSpinner.getValue());
		calendar.set(2000, 1, 1);
		Time endTime = new Time(calendar.getTime().getTime());
		error = erc.createEvent(personPhoneTextField.getText(), (java.sql.Date) datePicker.getModel().getValue(), startTime, endTime);
		// update visuals
		refreshData();
	}

	private void bookButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		if (selectedPatient < 0)
			error = error + "Participant needs to be selected for registration! ";
		if (selectedEvent < 0)
			error = error + "Event needs to be selected for registration!";
		error = error.trim();
		if (error.length() == 0) {
			// call the controller
			EventRegistrationController erc = new EventRegistrationController();
			error = erc.register(patients.get(selectedPatient), events.get(selectedEvent));
		}
		// update visuals
		refreshData();			
	}

}

