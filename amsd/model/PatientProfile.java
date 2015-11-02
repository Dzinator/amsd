/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;
import amsd.controller.*;
import java.util.*;
import java.sql.Date;

// line 57 "../../model.ump"
public class PatientProfile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PatientProfile Attributes
  private int missedAppointments;

  //PatientProfile State Machines
  enum Sm { Allowed, NotAllowed }
  private Sm sm;

  //PatientProfile Associations
  private List<Appointment> appointments;
  private Person person;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PatientProfile(int aMissedAppointments, Person aPerson)
  {
    // line 61 "../../model.ump"
    if(aPerson.hasPatientProfile()){
    			throw new IllegalArgumentException("Person already has patient profile");
    		}
    missedAppointments = aMissedAppointments;
    appointments = new ArrayList<Appointment>();
    boolean didAddPerson = setPerson(aPerson);
    if (!didAddPerson)
    {
      throw new RuntimeException("Unable to create patientProfile due to person");
    }
    setSm(Sm.Allowed);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMissedAppointments(int aMissedAppointments)
  {
    boolean wasSet = false;
    // line 70 "../../model.ump"
    if( aMissedAppointments < 0){
         		return false;
         	}
    missedAppointments = aMissedAppointments;
    wasSet = true;
    return wasSet;
  }

  public int getMissedAppointments()
  {
    return missedAppointments;
  }

  public String getSmFullName()
  {
    String answer = sm.toString();
    return answer;
  }

  public Sm getSm()
  {
    return sm;
  }

  public boolean miss(Appointment app)
  {
    // line 95 "../../model.ump"
    setMissedAppointments(getMissedAppointments() + 1); 
      			app.miss();
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Allowed:
        if (getMissedAppointments()>=2)
        {
          setSm(Sm.NotAllowed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean payFee(Appointment app)
  {
    // line 99 "../../model.ump"
    setMissedAppointments(getMissedAppointments() - 1);
      			app.payfee();
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case NotAllowed:
        if (getMissedAppointments()<2)
        {
          setSm(Sm.Allowed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setSm(Sm aSm)
  {
    sm = aSm;
  }

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

  public Person getPerson()
  {
    return person;
  }

  public static int minimumNumberOfAppointments()
  {
    return 0;
  }

  public Appointment addAppointment(Date aDate, int aTime, EmployeeProfile aEmployeeProfile, Availability aAvailability)
  {
    return new Appointment(aDate, aTime, this, aEmployeeProfile, aAvailability);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    PatientProfile existingPatientProfile = aAppointment.getPatientProfile();
    boolean isNewPatientProfile = existingPatientProfile != null && !this.equals(existingPatientProfile);
    if (isNewPatientProfile)
    {
      aAppointment.setPatientProfile(this);
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
    //Unable to remove aAppointment, as it must always have a patientProfile
    if (!this.equals(aAppointment.getPatientProfile()))
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

  public boolean setPerson(Person aNewPerson)
  {
    boolean wasSet = false;
    if (aNewPerson == null)
    {
      //Unable to setPerson to null, as patientProfile must always be associated to a person
      return wasSet;
    }
    
    PatientProfile existingPatientProfile = aNewPerson.getPatientProfile();
    if (existingPatientProfile != null && !equals(existingPatientProfile))
    {
      //Unable to setPerson, the current person already has a patientProfile, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Person anOldPerson = person;
    person = aNewPerson;
    person.setPatientProfile(this);

    if (anOldPerson != null)
    {
      anOldPerson.setPatientProfile(null);
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
    Person existingPerson = person;
    person = null;
    if (existingPerson != null)
    {
      existingPerson.setPatientProfile(null);
    }
  }

  // line 78 "../../model.ump"
   public Appointment getAppointment(Date date, int time){
    for(Appointment app : getAppointments()){
		  if(Controller.datesEqual(app.getDate(),date) && app.getTime() == time){
			  return app;
		  }
		}
	  return null;
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "missedAppointments" + ":" + getMissedAppointments()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "person = "+(getPerson()!=null?Integer.toHexString(System.identityHashCode(getPerson())):"null")
     + outputString;
  }
}