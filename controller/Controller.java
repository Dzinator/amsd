package amsd.controller;

import java.sql.Date;
import java.util.List;

import amsd.model.AppointmentManagementSystem;
import amsd.model.Availability;
import amsd.model.DentistProfile;
import amsd.model.EmployeeProfile;
import amsd.model.HygienistProfile;
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
	
	private Person getPerson(String name) {
		List<Person> pList = ams.getPersons();
		for(Person p : pList){
			if(p.getName().equals(name))
				return p;
		}
		return null;
	}
	

	public String addDentist(String name, int phoneNumber) {
		if (name == null || name.trim().length() == 0)
			return "Dentist name cannot be empty!";
		Person p = getPerson(name, phoneNumber);
		if(p.hasEmployeeProfile()){
			return "employee profile already exists";
		}
		DentistProfile dentist = new DentistProfile(p);
		ams.addDentistProfile(dentist);
		return null;
	}
	
	public String addHygienist(String name, int phoneNumber) {
		if (name == null || name.trim().length() == 0)
			return "Hygienist name cannot be empty!";
		Person p = getPerson(name, phoneNumber);
		if(p.hasEmployeeProfile()){
			return "employee profile already exists";
		}
		HygienistProfile hygienist = new HygienistProfile(p);
		ams.addHygienistProfile(hygienist);
		return null;
	}
	
	public String setAvailability(String name, Date date, boolean available){
		Person p = getPerson(name);
		if(p == null){
			return "Person not found";
		}
		
		EmployeeProfile profile = p.getEmployeeProfile();
		
		List<Availability> 
		for(Availability avail : profile.getAvailabilities()){
			if(date.equals(avail.getDate())){
				
			}
		}
		
		return null;
	}


}
