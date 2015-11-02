package amsd.test.controller;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import amsd.controller.Controller;
import amsd.model.Appointment;
import amsd.model.AppointmentManagementSystem;
import amsd.persistence.PersistenceAMSD;

public class TestController {

	
	@Before
	public void beforeTest(){
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		ams.delete();
	}
	
	@Test
	public void testCreatePatient() {
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		assertEquals(0, ams.getPatientProfiles().size());
		
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		String error = controller.addPatient(name, number);
		
		// check error
		assertNull(error);
		
		// check model in memory
		checkResultPatient(name, number, ams);
		
	
	}
	
	@Test
	public void testDuplicatePatient() {
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		assertEquals(0, ams.getPatientProfiles().size());

		
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		controller.addPatient(name, number);
		String error = controller.addPatient(name, number);
		
		// check error
		assertNotNull(error);
		assertEquals(1, ams.getPersons().size());
		// check model in memory
		checkResultPatient(name, number, ams);
		
	
	}
	
	
	@Test
	public void testCreateDentist() {
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		assertEquals(0, ams.getDentistProfiles().size());
		
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		String error = controller.addDentist(name, number);
		
		// check error
		assertNull(error);
		
		// check model in memory
		checkResultDentist(name, number, ams);
	}
	
	@Test
	public void testCreateDentistAndPatient() {
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		assertEquals(0, ams.getDentistProfiles().size());
		assertEquals(0, ams.getPatientProfiles().size());
	
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		String error = controller.addDentist(name, number);
		assertNull(error);
		error = controller.addPatient(name, number);
		// check error
		assertNull(error);
		
		// check model in memory
		checkResultDentist(name, number, ams);
		checkResultPatient(name, number, ams);
	}
	
	@Test
	public void testCreateDentistAndHygienist() {
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		assertEquals(0, ams.getDentistProfiles().size());
		assertEquals(0, ams.getHygienistProfiles().size());
	
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		String error = controller.addDentist(name, number);
		assertNull(error);
		error = controller.addHygienist(name, number);
		// check error
		assertNotNull(error);
		
		// check model in memory
		checkResultDentist(name, number, ams);
	}


	@SuppressWarnings("deprecation")
	@Test
	public void testAddAvailability(){
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		assertEquals(0, ams.getDentistProfiles().size());
		
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		String error = controller.addDentist(name, number);
		assertNull(error);
		
		
		
		Date date = new Date(2016, 5, 3);
		
		controller.setAvailable("Oscar", date, true);
		
		assertEquals(8, ams.getDentistProfile(0).getAvailabilities().size());
		
		
		//Can't add twice
		controller.setAvailable("Oscar",date,true);
		assertEquals(8, ams.getDentistProfile(0).getAvailabilities().size());
		
		//remove dates
		controller.setAvailable("Oscar",date,false);
		assertEquals(0, ams.getDentistProfile(0).getAvailabilities().size());
		
		
		
		
	}
	
	@Test
	public void testMakeAppointment(){
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		String error = controller.addDentist(name, number);
		
		name = "Dave";
		number = 12345609;
		
		error = controller.addPatient(name, number);
		
		checkResultPatient(name, number, ams);
		checkResultDentist("Oscar", 1234567, ams);
		

		Date date = new Date(2016, 5, 3);
		
		controller.setAvailable("Oscar", date, true);
		
		controller.makeDentistAppointment("Dave", date, 8);
		error = 
				controller.makeDentistAppointment("Dave", date, 8);
		assertNotNull(error);
		
		//No availability at that time
		error = controller.makeDentistAppointment("Dave", new Date(2015,4,5), 12);
		assertNotNull(error);
		
		//Dentist can't make a reservation
		error = controller.makeDentistAppointment("Oscar", date, 12);
		assertNotNull(error);
		
		//make sure availability is booked at right time.
		
		assertEquals(8,ams.getPatientProfile(0).getAppointment(0).getTime());
		assertEquals(5,ams.getPatientProfile(0).getAppointment(0).getDate().getMonth());
		 
		assertEquals(1,ams.getAppointments().size());
		
		//Make sure the state of the availability is booked
		assertTrue(ams.getDentistProfile(0).getAvailability(date, 8)
				.getSmFullName().equals("Booked"));
		assertTrue(ams.getDentistProfile(0).getAvailability(date, 8).hasAppointment());
		
	}
	
	@Test
	public void testCancelAppointment(){
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		controller.addDentist(name, number);
		
		name = "Dave";
		number = 12345609;
		
		controller.addPatient(name, number);
		
		Date date = new Date(2016, 5, 3);
		
		controller.setAvailable("Oscar", date, true);
		
		controller.makeDentistAppointment("Dave", date, 8);
		
		
		controller.cancelAppointment("Dave", date, 8);
		
		assertEquals(1,ams.getAppointments().size());
		//Make sure the state of the availability is booked
		assertTrue(ams.getDentistProfile(0).getAvailability(date, 8)
				.getSmFullName().equals("Available"));
		assertFalse(ams.getDentistProfile(0).getAvailability(date, 8).hasAppointment());
		
		//Can't cancel appointment twice
		String error = controller.cancelAppointment("Dave", date, 8);
		assertNotNull(error);
	}
	
	
	@Test
	public void testMissAppointment(){
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		controller.addDentist(name, number);
		
		name = "Dave";
		number = 12345609;
		
		controller.addPatient(name, number);
		
		Date date = new Date(2016, 5, 3);
		
		controller.setAvailable("Oscar", date, true);
		
		controller.makeDentistAppointment("Dave", date, 8);
		controller.makeDentistAppointment("Dave", date, 9);
		controller.makeDentistAppointment("Dave", date, 10);
		
		assertEquals(3,ams.getAppointments().size());
		
		controller.missAppointment("Dave", date, 8);
		
		assertEquals(1,ams.getPatientProfile(0).getMissedAppointments());
		assertTrue(ams.getPatientProfile(0).getSmFullName().equals("Allowed"));
		
		controller.missAppointment("Dave", date, 8);
		assertEquals(2,ams.getPatientProfile(0).getMissedAppointments());
		assertTrue(ams.getPatientProfile(0).getSmFullName().equals("NotAllowed"));
	
		String error  = 
				controller.makeDentistAppointment("Dave", date, 8);;
		assertNotNull(error);
		
	}
	
	public void testPayFee(){
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		String name = "Oscar";
		int number = 1234567;
		
		Controller controller = new Controller();
		controller.addDentist(name, number);
		
		name = "Dave";
		number = 12345609;
		
		controller.addPatient(name, number);
		
		Date date = new Date(2016, 5, 3);
		
		controller.setAvailable("Oscar", date, true);
		
		controller.makeDentistAppointment("Dave", date, 8);
		controller.makeDentistAppointment("Dave", date, 9);
		controller.makeDentistAppointment("Dave", date, 10);
		
		assertEquals(3,ams.getAppointments().size());
			
		controller.missAppointment("Dave", date, 8);
		
		assertEquals(1,ams.getPatientProfile(0).getMissedAppointments());
		assertTrue(ams.getPatientProfile(0).getSmFullName().equals("Allowed"));
		
		controller.missAppointment("Dave", date, 8);
		assertEquals(2,ams.getPatientProfile(0).getMissedAppointments());
		assertTrue(ams.getPatientProfile(0).getSmFullName().equals("NotAllowed"));
	
		controller.payFee("Dave",date,8);
		
		assertEquals(1,ams.getPatientProfile(0).getMissedAppointments());
		assertTrue(ams.getPatientProfile(0).getSmFullName().equals("Allowed"));
	}
	
	@Test
	public void testRebookingCancelledAppointment(){
		PersistenceAMSD.loadEventRegistrationModel("amsd.xml");
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		
		Controller c = new Controller();
		
		Calendar cal = Calendar.getInstance();
		cal.set(2015,Calendar.NOVEMBER,11,0,0,0);
		Date date = new Date(cal.getTime().getTime());
		
		
		c.makeDentistAppointment("mona",date, 8);
		assertEquals(1,ams.getPatientProfile(0).getAppointments().size());
		c.cancelAppointment("mona", date, 8);
		
		assertEquals(0,ams.getPatientProfile(0).getAppointments().size());
		assertEquals(1,ams.getAppointments().size());
		
		PersistenceAMSD.saveEventRegistrationModel("amsd1.xml");
		
		ams.delete();
		PersistenceAMSD.loadEventRegistrationModel("amsd1.xml");

		assertEquals(0,ams.getPatientProfile(0).getAppointments().size());
		assertEquals(1,ams.getAppointments().size());
		Appointment app = ams.getAppointment(0);
		assertTrue(app.getSmFullName().equals("Canceled"));


		c.makeDentistAppointment("mona",date, 8);
		assertEquals(1,ams.getPatientProfile(0).getAppointments().size());
		
		String error = c.cancelAppointment("mona", date, 8);
		assertNull(error);
	}
	
	private void checkResutHygienist(String name, int number,
			AppointmentManagementSystem ams) {
		assertEquals(1, ams.getHygienistProfiles().size());
		assertTrue(ams.getHygienistProfile(0).getPerson().getName().equals(name));
		assertEquals(number,ams.getHygienistProfile(0).getPerson().getPhoneNumber());
		
	}


	private void checkResultPatient(String name, int number,
			AppointmentManagementSystem ams) {
		assertEquals(1, ams.getPatientProfiles().size());
		assertTrue(ams.getPatientProfile(0).getPerson().getName().equals(name));
		assertEquals(number,ams.getPatientProfile(0).getPerson().getPhoneNumber());
	}
	
	private void checkResultDentist(String name, int number,
			AppointmentManagementSystem ams) {
		assertEquals(1, ams.getDentistProfiles().size());
		assertTrue(ams.getDentistProfile(0).getPerson().getName().equals(name));
		assertEquals(number,ams.getDentistProfile(0).getPerson().getPhoneNumber());
	}
	
	
	
}
