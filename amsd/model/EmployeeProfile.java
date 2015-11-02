/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import amsd.controller.Controller;

// line 64 "../../model.ump"
public class EmployeeProfile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EmployeeProfile Associations
  private List<Appointment> appointments;
  private List<Availability> availabilities;
  private Person person;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EmployeeProfile(Person aPerson)
  {
    appointments = new ArrayList<Appointment>();
    availabilities = new ArrayList<Availability>();
    boolean didAddPerson = setPerson(aPerson);
    if (!didAddPerson)
    {
      throw new RuntimeException("Unable to create employeeProfile due to person");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }

  public Availability getAvailability(int index)
  {
    Availability aAvailability = availabilities.get(index);
    return aAvailability;
  }

  public List<Availability> getAvailabilities()
  {
    List<Availability> newAvailabilities = Collections.unmodifiableList(availabilities);
    return newAvailabilities;
  }

  public int numberOfAvailabilities()
  {
    int number = availabilities.size();
    return number;
  }

  public boolean hasAvailabilities()
  {
    boolean has = availabilities.size() > 0;
    return has;
  }

  public int indexOfAvailability(Availability aAvailability)
  {
    int index = availabilities.indexOf(aAvailability);
    return index;
  }

  public Person getPerson()
  {
    return person;
  }

  public static int minimumNumberOfAppointments()
  {
    return 0;
  }

  public Appointment addAppointment(Date aDate, int aTime, PatientProfile aPatientProfile, Availability aAvailability)
  {
    return new Appointment(aDate, aTime, aPatientProfile, this, aAvailability);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    if (appointments.contains(aAppointment)) { return false; }
    if (appointments.contains(aAppointment)) { return false; }
    EmployeeProfile existingEmployeeProfile = aAppointment.getEmployeeProfile();
    boolean isNewEmployeeProfile = existingEmployeeProfile != null && !this.equals(existingEmployeeProfile);
    if (isNewEmployeeProfile)
    {
      aAppointment.setEmployeeProfile(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a employeeProfile
    if (!this.equals(aAppointment.getEmployeeProfile()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfAvailabilities()
  {
    return 0;
  }

  public Availability addAvailability(Date aDate, int aTime)
  {
    return new Availability(aDate, aTime, this);
  }

  public boolean addAvailability(Availability aAvailability)
  {
    boolean wasAdded = false;
    if (availabilities.contains(aAvailability)) { return false; }
    if (availabilities.contains(aAvailability)) { return false; }
    if (availabilities.contains(aAvailability)) { return false; }
    EmployeeProfile existingEmployeeProfile = aAvailability.getEmployeeProfile();
    boolean isNewEmployeeProfile = existingEmployeeProfile != null && !this.equals(existingEmployeeProfile);
    if (isNewEmployeeProfile)
    {
      aAvailability.setEmployeeProfile(this);
    }
    else
    {
      availabilities.add(aAvailability);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAvailability(Availability aAvailability)
  {
    boolean wasRemoved = false;
    //Unable to remove aAvailability, as it must always have a employeeProfile
    if (!this.equals(aAvailability.getEmployeeProfile()))
    {
      availabilities.remove(aAvailability);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addAvailabilityAt(Availability aAvailability, int index)
  {  
    boolean wasAdded = false;
    if(addAvailability(aAvailability))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAvailabilities()) { index = numberOfAvailabilities() - 1; }
      availabilities.remove(aAvailability);
      availabilities.add(index, aAvailability);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAvailabilityAt(Availability aAvailability, int index)
  {
    boolean wasAdded = false;
    if(availabilities.contains(aAvailability))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAvailabilities()) { index = numberOfAvailabilities() - 1; }
      availabilities.remove(aAvailability);
      availabilities.add(index, aAvailability);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAvailabilityAt(aAvailability, index);
    }
    return wasAdded;
  }

  public boolean setPerson(Person aNewPerson)
  {
    boolean wasSet = false;
    if (aNewPerson == null)
    {
      //Unable to setPerson to null, as employeeProfile must always be associated to a person
      return wasSet;
    }
    
    EmployeeProfile existingEmployeeProfile = aNewPerson.getEmployeeProfile();
    if (existingEmployeeProfile != null && !equals(existingEmployeeProfile))
    {
      //Unable to setPerson, the current person already has a employeeProfile, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Person anOldPerson = person;
    person = aNewPerson;
    person.setEmployeeProfile(this);

    if (anOldPerson != null)
    {
      anOldPerson.setEmployeeProfile(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
    for(int i=availabilities.size(); i > 0; i--)
    {
      Availability aAvailability = availabilities.get(i - 1);
      aAvailability.delete();
    }
    Person existingPerson = person;
    person = null;
    if (existingPerson != null)
    {
      existingPerson.setEmployeeProfile(null);
    }
  }

  // line 72 "../../model.ump"
   public Availability getAvailability(Date date, int time){
    for(Availability avail : getAvailabilities()){
		  if(Controller.datesEqual(date,avail.getDate()) && avail.getTime() == time){
			  return avail;
		  }
		}
	  return null;
  }


// line 81 "../../model.ump"
   public Appointment getAppointment(Date date, int time){
    for(Appointment app : getAppointments()){
		  if(app.getDate().equals(date) && app.getTime() == time){
			  return app;
		  }
		}
	  return null;
  }

}