package amsd.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import amsd.model.Appointment;
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
	
	public String setAvailable(String name, Date date, boolean available){
		Person p = getPerson(name);
		if(p == null){
			return "Person not found";
		}
		
		EmployeeProfile profile = p.getEmployeeProfile();
		
		boolean alreadyBookedOnDay = false;
		boolean foundAvailability = false;
		List<Availability> availOnDay = new ArrayList<>();
		
		for(Availability avail : profile.getAvailabilities()){
			if(date.equals(avail.getDate())){
				foundAvailability = true;
				availOnDay.add(avail);
				if(avail.hasAppointment()){
					alreadyBookedOnDay = true;
				}
			}
		}
		
		if(alreadyBookedOnDay){
			if(available){
				return "Already set to available on that day";
			} else {
				return "Cannot cancel availability, patients already booked";
			}
		} else if(foundAvailability && !available){
			//remove all availabilities
			for(Availability avail : availOnDay){
				avail.delete();
			}
		} else if(foundAvailability && available){
			//nothing to do
			return "availability already set";
		} else{ //!foundAvailability
			if(available){
				int[] times = {8,9,10,11,13,14,15,16};
				for(int time : times){
					profile.addAvailability(date, time);
				}
			} else {
				return "No availabilities added";
			}
		}
		
		
			
		
		
		return null;
	}

	public String makeDentistReservation(String name, Date date, int time) {
		
		PatientProfile patient = getPatient(name);
		if(patient == null){
			return "No patient profile found";
		}
		
		String found = "No availabilities at this date and time";
		for(DentistProfile dentist : ams.getDentistProfiles()){
			Availability avail = dentist.getAvailability(date, time);
			if(avail != null){
				//make appointment
				ams.addAppointment(new Appointment(date, time, patient, dentist, avail));
				found = null;
				break;
			}
		}
		
		return found;
		
		
	}
	
	public String makeHygienistReservation(String name, Date date, int time) {
		
		PatientProfile patient = getPatient(name);
		if(patient == null){
			return "No patient profile found";
		}
		
		String found = "No availabilities at this date and time";
		for(HygienistProfile dentist : ams.getHygienistProfiles()){
			Availability avail = dentist.getAvailability(date, time);
			if(avail != null){
				//make appointment
				ams.addAppointment(new Appointment(date, time, patient, dentist, avail));
				found = null;
				break;
			}
		}
		
		return found;
		
		
	}
	
	public String makeReservation(String name, Date date, int time, boolean dentist){
		if(dentist){
			return makeDentistReservation(name, date, time);
		} else {
			return makeHygienistReservation(name, date, time);
		}
	}

	private PatientProfile getPatient(String name) {
		Person p = getPerson(name);
		return p.getPatientProfile();
	}


}
