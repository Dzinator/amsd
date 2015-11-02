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
		
		controller.addPatient("Oscar", 1234567);
		controller.addHygienist("Dave", 1236547);
		
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
		PersistenceAMSD.initializeXStream("amsd.xml");

		if (!PersistenceXStream.saveToXMLwithXStream(ams))
			fail("Could not save file.");

		// clear the model in memory
		ams.delete();		
		assertEquals(0,ams.getAppointments().size());
		assertEquals(0,ams.getDentistProfiles().size());
		assertEquals(0,ams.getHygienistProfiles().size());
		assertEquals(0,ams.getPatientProfiles().size());

		// load model
		
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
//		rm = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
//		if (rm == null)
//			fail("Could not load file.");
//		
//		// check participants
//		assertEquals(2, rm.getParticipants().size());
//		assertEquals("Martin", rm.getParticipant(0).getName());
//		assertEquals("Jennifer", rm.getParticipant(1).getName());
//		// check event
//		assertEquals(1, rm.getEvents().size());
//		assertEquals("Concert", rm.getEvent(0).getName());
//		Calendar c = Calendar.getInstance();
//		c.set(2015,Calendar.SEPTEMBER,15,8,30,0);
//		Date eventDate = new Date(c.getTimeInMillis());
//		Time startTime = new Time(c.getTimeInMillis());
//		c.set(2015,Calendar.SEPTEMBER,15,10,0,0);
//		Time endTime = new Time(c.getTimeInMillis());
//		assertEquals(eventDate.toString(), rm.getEvent(0).getEventDate().toString());
//		assertEquals(startTime.toString(), rm.getEvent(0).getStartTime().toString());
//		assertEquals(endTime.toString(), rm.getEvent(0).getEndTime().toString());
//		// check registrations
//		assertEquals(2, rm.getRegistrations().size());
//		assertEquals(rm.getEvent(0), rm.getRegistration(0).getEvent());
//		assertEquals(rm.getParticipant(0), rm.getRegistration(0).getParticipant());
//		assertEquals(rm.getEvent(0), rm.getRegistration(1).getEvent());
//		assertEquals(rm.getParticipant(1), rm.getRegistration(1).getParticipant());
	}

}

