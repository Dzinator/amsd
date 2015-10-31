/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;
import java.sql.Date;

// line 110 "../../model.ump"
public class Availability
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Availability Attributes
  private Date date;
  private int time;

  //Availability Associations
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

  public EmployeeProfile getEmployeeProfile()
  {
    return employeeProfile;
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
            "  " + "employeeProfile = "+(getEmployeeProfile()!=null?Integer.toHexString(System.identityHashCode(getEmployeeProfile())):"null")
     + outputString;
  }
}