package amsd.test.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import amsd.controller.Controller;
import amsd.model.AppointmentManagementSystem;

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
	
	@Test
	
	
	private void checkResutHygienist(String name, int number,
			AppointmentManagementSystem ams) {
		assertEquals(1, ams.getHygienistProfiles().size());
		assertTrue(ams.getHygienistProfile(0).getPerson().getName().equals(name));
		assertEquals(number,ams.getHygienistProfile(0).getPerson().getPhoneNumber());
		
	}

	@Test
	public void testAddAvailability(){
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		assertEquals(0, ams.getDentistProfiles().size());
		
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
