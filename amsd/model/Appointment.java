/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;
import java.sql.Date;

// line 113 "../../model.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private Date date;
  private int time;

  //Appointment State Machines
  enum Sm { Booked, Canceled, Attended, Missed, FeePaid }
  private Sm sm;

  //Appointment Associations
  private PatientProfile patientProfile;
  private EmployeeProfile employeeProfile;
  private Availability availability;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Date aDate, int aTime, PatientProfile aPatientProfile, EmployeeProfile aEmployeeProfile, Availability aAvailability)
  {
    date = aDate;
    time = aTime;
    boolean didAddPatientProfile = setPatientProfile(aPatientProfile);
    if (!didAddPatientProfile)
    {
      throw new RuntimeException("Unable to create appointment due to patientProfile");
    }
    boolean didAddEmployeeProfile = setEmployeeProfile(aEmployeeProfile);
    if (!didAddEmployeeProfile)
    {
      throw new RuntimeException("Unable to create appointment due to employeeProfile");
    }
    boolean didAddAvailability = setAvailability(aAvailability);
    if (!didAddAvailability)
    {
      throw new RuntimeException("Unable to create appointment due to availability");
    }
    setSm(Sm.Booked);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(int aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public int getTime()
  {
    return time;
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

  public boolean cancel(long today)
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Booked:
        if (getDate().getTime()-today>=twoDaysInMillis)
        {
          setSm(Sm.Canceled);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean miss()
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Booked:
        setSm(Sm.Missed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean attend()
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Booked:
        setSm(Sm.Attended);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean payfee()
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Missed:
        setSm(Sm.FeePaid);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setSm(Sm aSm)
  {
    sm = aSm;

    // entry actions and do activities
    switch(sm)
    {
      case Canceled:
        // line 131 "../../model.ump"
        this.getAvailability().cancel();
      		this.delete();
        break;
    }
  }

  public PatientProfile getPatientProfile()
  {
    return patientProfile;
  }

  public EmployeeProfile getEmployeeProfile()
  {
    return employeeProfile;
  }

  public Availability getAvailability()
  {
    return availability;
  }

  public boolean setPatientProfile(PatientProfile aPatientProfile)
  {
    boolean wasSet = false;
    if (aPatientProfile == null)
    {
      return wasSet;
    }

    PatientProfile existingPatientProfile = patientProfile;
    patientProfile = aPatientProfile;
    if (existingPatientProfile != null && !existingPatientProfile.equals(aPatientProfile))
    {
      existingPatientProfile.removeAppointment(this);
    }
    patientProfile.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setEmployeeProfile(EmployeeProfile aEmployeeProfile)
  {
    boolean wasSet = false;
    if (aEmployeeProfile == null)
    {
      return wasSet;
    }

    EmployeeProfile existingEmployeeProfile = employeeProfile;
    employeeProfile = aEmployeeProfile;
    if (existingEmployeeProfile != null && !existingEmployeeProfile.equals(aEmployeeProfile))
    {
      existingEmployeeProfile.removeAppointment(this);
    }
    employeeProfile.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setAvailability(Availability aNewAvailability)
  {
    boolean wasSet = false;
    if (aNewAvailability == null)
    {
      //Unable to setAvailability to null, as appointment must always be associated to a availability
      return wasSet;
    }
    
    Appointment existingAppointment = aNewAvailability.getAppointment();
    if (existingAppointment != null && !equals(existingAppointment))
    {
      //Unable to setAvailability, the current availability already has a appointment, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Availability anOldAvailability = availability;
    availability = aNewAvailability;
    availability.setAppointment(this);

    if (anOldAvailability != null)
    {
      anOldAvailability.setAppointment(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    PatientProfile placeholderPatientProfile = patientProfile;
    this.patientProfile = null;
    placeholderPatientProfile.removeAppointment(this);
    EmployeeProfile placeholderEmployeeProfile = employeeProfile;
    this.employeeProfile = null;
    placeholderEmployeeProfile.removeAppointment(this);
    Availability existingAvailability = availability;
    availability = null;
    if (existingAvailability != null)
    {
      existingAvailability.setAppointment(null);
    }
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "time" + ":" + getTime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "patientProfile = "+(getPatientProfile()!=null?Integer.toHexString(System.identityHashCode(getPatientProfile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employeeProfile = "+(getEmployeeProfile()!=null?Integer.toHexString(System.identityHashCode(getEmployeeProfile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "availability = "+(getAvailability()!=null?Integer.toHexString(System.identityHashCode(getAvailability())):"null")
     + outputString;
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 116 ../../model.ump
  final long twoDaysInMillis = 1000*60*60*24*2 ;

  
}