package amsd.persistence;

import java.util.Iterator;

import amsd.model.*;

public class PersistenceAMSD {

	public static void initializeXStream() {
		PersistenceXStream.setFilename("amsd.xml");
		
		PersistenceXStream.setAlias("appointment", Appointment.class);
		//PersistenceXStream.setAlias("availability", Availability.class);
		PersistenceXStream.setAlias("person", Person.class);
		PersistenceXStream.setAlias("patient", PatientProfile.class);
		PersistenceXStream.setAlias("employee", EmployeeProfile.class);
		PersistenceXStream.setAlias("dentist", DentistProfile.class);
		PersistenceXStream.setAlias("hygienist", HygienistProfile.class);
		PersistenceXStream.setAlias("amsd", AppointmentManagementSystem.class);
	}

	public static void loadEventRegistrationModel() {
		AppointmentManagementSystem apms = AppointmentManagementSystem.getInstance();
		PersistenceAMSD.initializeXStream();
		AppointmentManagementSystem apms2 = (AppointmentManagementSystem) PersistenceXStream.loadFromXMLwithXStream();
		if (apms2 != null) {
			//TODO might need to add support for availabilities
			Iterator<Appointment> apIt = apms2.getAppointments().iterator();
			while (apIt.hasNext())
				apms.addAppointment(apIt.next());
			Iterator<Person> pIt = apms2.getPersons().iterator();
			while (pIt.hasNext())
				apms.addPerson(pIt.next());
			Iterator<PatientProfile> ppIt = apms2.getPatientProfiles().iterator();
			while (ppIt.hasNext())
				apms.addPatientProfile(ppIt.next());
			/*
			Iterator<EmployeeProfile> epIt = apms2.get.iterator();
			while (epIt.hasNext())
				apms.addEmployeeProfile(epIt.next());
			*/
			Iterator<DentistProfile> dpIt = apms2.getDentistProfiles().iterator();
			while (dpIt.hasNext())
				apms.addDentistProfile(dpIt.next());
			Iterator<HygienistProfile> hpIt = apms2.getHygienistProfiles().iterator();
			while (hpIt.hasNext())
				apms.addHygienistProfile(hpIt.next());
		}
	}

}
