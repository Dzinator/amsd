package amsd.test.persistence;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import amsd.controller.Controller;
import amsd.model.AppointmentManagementSystem;
import amsd.persistence.PersistenceAMSD;
import amsd.persistence.PersistenceXStream;

public class TestPersistence {

	@Before
	public void setUp() throws Exception {
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		
		Controller controller = new Controller();
		
		controller.addPatient("Oscar", "1234567");
		controller.addHygienist("Dave", "1236547");
		
		Date date = new Date(2016,4,2);
		int time = 9;
		
		controller.setAvailable("Dave", date, true);
		controller.makeHygienistAppointment("Oscar", date, time);
		
		controller.makeHygienistAppointment("Oscar", date, time + 1);
		
		controller.missAppointment("Oscar", date, time);
		
		
	}
	
	@After
	public void tearDown() throws Exception {
		// clear all registrations
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		ams.delete();
	}
	
	@Test
	public void test() {
		// save model
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
//		PersistenceAMSD pers = new PersistenceAMSD();
		PersistenceAMSD.initializeXStream("amsdPTest.xml");

		if (!PersistenceXStream.saveToXMLwithXStream(ams))
			fail("Could not save file.");

		// clear the model in memory
		ams.delete();		
		assertEquals(0,ams.getAppointments().size());
		assertEquals(0,ams.getDentistProfiles().size());
		assertEquals(0,ams.getHygienistProfiles().size());
		assertEquals(0,ams.getPatientProfiles().size());

		// load model
		
		PersistenceXStream.setFilename("amsdPTest.xml");
		
		ams = (AppointmentManagementSystem) PersistenceXStream.loadFromXMLwithXStream();
		if(ams == null){
			fail("Could not load file");
		}
		
		assertEquals(2,ams.getPersons().size());
		assertEquals(1,ams.getHygienistProfiles().size());
		assertEquals(1,ams.getPatientProfiles().size());
		assertEquals(0,ams.getDentistProfiles().size());
		
		
		assertTrue(ams.getPatientProfile(0).getPerson().getName().equals("Oscar"));
		assertTrue(ams.getHygienistProfile(0).getPerson().getName().equals("Dave"));
		
		assertEquals(1,ams.getPatientProfile(0).getMissedAppointments());

	}

}

