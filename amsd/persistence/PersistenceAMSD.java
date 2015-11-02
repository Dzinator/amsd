package amsd.persistence;

import static org.junit.Assert.fail;

import java.util.Iterator;

import amsd.model.*;

public class PersistenceAMSD {

	
	public static void saveEventRegistrationModel(String filename){
		PersistenceAMSD.initializeXStream(filename);
		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
		if (!PersistenceXStream.saveToXMLwithXStream(ams))
			fail("Could not save file.");
	}
	
	public static void initializeXStream(String filename) {
		PersistenceXStream.setFilename(filename);
		
		PersistenceXStream.setAlias("appointment", Appointment.class);
		PersistenceXStream.setAlias("person", Person.class);
		PersistenceXStream.setAlias("patient", PatientProfile.class);
		PersistenceXStream.setAlias("employee", EmployeeProfile.class);
		PersistenceXStream.setAlias("dentist", DentistProfile.class);
		PersistenceXStream.setAlias("hygienist", HygienistProfile.class);
		PersistenceXStream.setAlias("amsd", AppointmentManagementSystem.class);
	}

	public static void loadEventRegistrationModel(String filename) {
		AppointmentManagementSystem apms = AppointmentManagementSystem.getInstance();
		PersistenceAMSD.initializeXStream(filename);
		AppointmentManagementSystem apms2 = (AppointmentManagementSystem) PersistenceXStream.loadFromXMLwithXStream();
		if (apms2 != null) {
			Iterator<Person> pIt = apms2.getPersons().iterator();
			while (pIt.hasNext())
				apms.addPerson(pIt.next());
			Iterator<PatientProfile> ppIt = apms2.getPatientProfiles().iterator();
			while (ppIt.hasNext())
				apms.addPatientProfile(ppIt.next());
			Iterator<DentistProfile> dpIt = apms2.getDentistProfiles().iterator();
			while (dpIt.hasNext())
				apms.addDentistProfile(dpIt.next());
			Iterator<HygienistProfile> hpIt = apms2.getHygienistProfiles().iterator();
			while (hpIt.hasNext())
				apms.addHygienistProfile(hpIt.next());
			Iterator<Appointment> apIt = apms2.getAppointments().iterator();
			while (apIt.hasNext())
				apms.addAppointment(apIt.next());
		}
	}

}
