package amsd.controller;

import java.util.List;

import amsd.model.AppointmentManagementSystem;
import amsd.model.DentistProfile;
import amsd.model.PatientProfile;
import amsd.model.Person;

public class Controller {

	AppointmentManagementSystem ams;
	
	public Controller(){
		ams = AppointmentManagementSystem.getInstance();
	}
	
	public String addPatient(String name, int phoneNumber)
	{
		if (name == null || name.trim().length() == 0)
			return "Patient name cannot be empty!";
		
		Person p = getPerson(name, phoneNumber);
		
		if(p.hasPatientProfile()){
			return "patient profile already exists";
		}
		
		PatientProfile patient = new PatientProfile(0, p);
		ams.addPatientProfile(patient);
		ams.addPerson(p);
		return null;
	}

	private Person getPerson(String name, int phoneNumber) {
		List<Person> pList = ams.getPersons();
		for(Person p : pList){
			if(p.getName().equals(name))
				return p;
		}
		Person p = new Person(name, phoneNumber);
		ams.addPerson(p);
		return p;
	}

	public String addDentist(String name, int phoneNumber) {
		if (name == null || name.trim().length() == 0)
			return "Dentist name cannot be empty!";
		Person p = new Person(name, phoneNumber);
		DentistProfile dentist = new DentistProfile(p);
		ams.addDentistProfile(dentist);
		ams.addPerson(p);
		return null;
	}
	
}
