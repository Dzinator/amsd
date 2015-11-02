/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;
import java.sql.Date;

// line 182 "../../model.ump"
public class Availability
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Availability Attributes
  private Date date;
  private int time;

  //Availability State Machines
  enum Sm { Available, Booked }
  private Sm sm;

  //Availability Associations
  private Appointment appointment;
  private EmployeeProfile employeeProfile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Availability(Date aDate, int aTime, EmployeeProfile aEmployeeProfile)
  {
    date = aDate;
    time = aTime;
    boolean didAddEmployeeProfile = setEmployeeProfile(aEmployeeProfile);
    if (!didAddEmployeeProfile)
    {
      throw new RuntimeException("Unable to create availability due to employeeProfile");
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

  public boolean book(Appointment a)
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Available:
        // line 188 "../../model.ump"
        setAppointment(a);
        setSm(Sm.Booked);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    Sm aSm = sm;
    switch (aSm)
    {
      case Booked:
        setSm(Sm.Available);
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

  public Appointment getAppointment()
  {
    return appointment;
  }

  public boolean hasAppointment()
  {
    boolean has = appointment != null;
    return has;
  }

  public EmployeeProfile getEmployeeProfile()
  {
    return employeeProfile;
  }

  public boolean setAppointment(Appointment aNewAppointment)
  {
    boolean wasSet = false;
    if (appointment != null && !appointment.equals(aNewAppointment) && equals(appointment.getAvailability()))
    {
      //Unable to setAppointment, as existing appointment would become an orphan
      return wasSet;
    }

    appointment = aNewAppointment;
    Availability anOldAvailability = aNewAppointment != null ? aNewAppointment.getAvailability() : null;

    if (!this.equals(anOldAvailability))
    {
      if (anOldAvailability != null)
      {
        anOldAvailability.appointment = null;
      }
      if (appointment != null)
      {
        appointment.setAvailability(this);
      }
    }
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
      existingEmployeeProfile.removeAvailability(this);
    }
    employeeProfile.addAvailability(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Appointment existingAppointment = appointment;
    appointment = null;
    if (existingAppointment != null)
    {
      existingAppointment.delete();
    }
    EmployeeProfile placeholderEmployeeProfile = employeeProfile;
    this.employeeProfile = null;
    placeholderEmployeeProfile.removeAvailability(this);
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "time" + ":" + getTime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointment = "+(getAppointment()!=null?Integer.toHexString(System.identityHashCode(getAppointment())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employeeProfile = "+(getEmployeeProfile()!=null?Integer.toHexString(System.identityHashCode(getEmployeeProfile())):"null")
     + outputString;
  }
}