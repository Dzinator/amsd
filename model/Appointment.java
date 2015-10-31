/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;
import java.sql.Date;

// line 68 "../../model.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private Date date;
  private int time;

  //Appointment State Machines
  enum Sm { Available, Booked, Canceled, Attended, Missed, FeePaid }
  private Sm sm;

  //Appointment Associations
  private PatientProfile patientProfile;
  private EmployeeProfile employeeProfile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Date aDate, int aTime, PatientProfile aPatientProfile, EmployeeProfile aEmployeeProfile)
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
    setSm(Sm.Available);
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

  public boolean book()
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Available:
        setSm(Sm.Booked);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel(Date today)
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Booked:
        if (getDate().getTime()-today.getTime()>=twoDaysInMillis)
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
  }

  public PatientProfile getPatientProfile()
  {
    return patientProfile;
  }

  public EmployeeProfile getEmployeeProfile()
  {
    return employeeProfile;
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

  public void delete()
  {
    PatientProfile placeholderPatientProfile = patientProfile;
    this.patientProfile = null;
    placeholderPatientProfile.removeAppointment(this);
    EmployeeProfile placeholderEmployeeProfile = employeeProfile;
    this.employeeProfile = null;
    placeholderEmployeeProfile.removeAppointment(this);
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "time" + ":" + getTime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "patientProfile = "+(getPatientProfile()!=null?Integer.toHexString(System.identityHashCode(getPatientProfile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employeeProfile = "+(getEmployeeProfile()!=null?Integer.toHexString(System.identityHashCode(getEmployeeProfile())):"null")
     + outputString;
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 71 ../../model.ump
  final long twoDaysInMillis = 1000*60*60*24*2 ;

  
}