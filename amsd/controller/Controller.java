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
		if(profile == null){
			return "No employee profile";
		}
		
		boolean alreadyBookedOnDay = false;
		boolean foundAvailability = false;
		List<Availability> availOnDay = new ArrayList<>();
		
		for(Availability avail : profile.getAvailabilities()){
			if(datesEqual(date,avail.getDate())){
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
		} else if(foundAvailability){
			if(available){
				//nothing to do
				return "availability already set";
			} else {
			//remove all availabilities
				for(Availability avail : availOnDay){
					avail.delete();
				}
			}
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

	public String makeDentistAppointment(String name, Date date, int time) {
		
		PatientProfile patient = getPatient(name);
		if(patient == null){
			return "No patient profile found";
		}
		
		if(!patient.getSmFullName().equals("Allowed")){
			return "Cannot make appointment. Patient has outstanding payments.";
		}
		
		
		String found = "No availabilities at this date and time";
		for(DentistProfile dentist : ams.getDentistProfiles()){
			Availability avail = dentist.getAvailability(date, time);
			if(avail != null && avail.getSmFullName().equals("Available")){
				
				//make appointment
				Appointment app = new Appointment(date, time, patient, dentist, avail);
				ams.addAppointment(app);
				avail.book(app);
				found = null;
				break;
			}
		}
		
		return found;
		
		
	}
	
	public String makeHygienistAppointment(String name, Date date, int time) {
		
		PatientProfile patient = getPatient(name);
		if(patient == null){
			return "No patient profile found";
		}
		
		if(!patient.getSmFullName().equals("Allowed")){
			return "Cannot make appointment. Patient has outstanding payments.";
		}
		
		String found = "No availabilities at this date and time";
		for(HygienistProfile hygienist : ams.getHygienistProfiles()){
			Availability avail = hygienist.getAvailability(date, time);
			if(avail != null){
				//make appointment
				Appointment appoint = new Appointment(date, time, patient, hygienist, avail);
				ams.addAppointment(appoint);
				avail.book(appoint);
				found = null;
				break;
			}
		}
		
		return found;
		
		
	}
	
	public String makeAppointment(String name, Date date, int time, boolean dentist){
		if(dentist){
			return makeDentistAppointment(name, date, time);
		} else {
			return makeHygienistAppointment(name, date, time);
		}
	}

	private PatientProfile getPatient(String name) {
		Person p = getPerson(name);
		return p.getPatientProfile();
	}

	public String cancelAppointment(String name, Date date, int time) {
		PatientProfile patient = getPatient(name);
		if(patient == null){
			return "No patient profile found";
		}
		
		Appointment cancelApp = patient.getAppointment(date, time);
		if(cancelApp != null){
			if(cancelApp.cancel(System.currentTimeMillis())){
				cancelApp.delete();
				ams.removeAppointment(cancelApp);
			} else {
				return "Could not cancel appointment";
			}
			
		} else {
			return "Appointment not found";
		}
		
		
		
		return null;
	}

	public String missAppointment(String name, Date date, int time) {
		PatientProfile patient = getPatient(name);
		if(patient == null){
			return "No patient profile found";
		}
		
		Appointment missApp = patient.getAppointment(date, time);
		if(missApp == null){
			return "Appointment not found";
		} 
		
		patient.miss(missApp);
		
		return null;
		
	}

	public String payFee(String name, Date date, int time) {
		PatientProfile patient = getPatient(name);
		if(patient == null){
			return "No patient profile found";
		}
		
		if(patient.getMissedAppointments() == 0){
			return "No outstanding fees for patient";
		}
		
		Appointment missApp = patient.getAppointment(date, time);
		if(missApp == null){
			return "Appointment not found";
		} 
		
		patient.payFee(missApp);
		
		return null;
		
	}

	
	public String updatePhoneNumber(String name, int number){
		Person person = getPerson(name);
		if(person == null){
			return "Person not found";
		}
		
		person.setPhoneNumber(number);
		
		return null;
	}
	
	public static boolean datesEqual(Date date1, Date date2) {
		  return date1.getDate() == date2.getDate() &&
				  date1.getMonth() == date2.getMonth() &&
				  date1.getYear() == date2.getYear();
	}


}
