/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;

// line 18 "../../model.ump"
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String name;
  private String phoneNumber;

  //Person Associations
  private PatientProfile patientProfile;
  private EmployeeProfile employeeProfile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aName, String aPhoneNumber)
  {
    name = aName;
    phoneNumber = aPhoneNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    // line 21 "../../model.ump"
    if (aName == null) {
    			return false; }
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public PatientProfile getPatientProfile()
  {
    return patientProfile;
  }

  public boolean hasPatientProfile()
  {
    boolean has = patientProfile != null;
    return has;
  }

  public EmployeeProfile getEmployeeProfile()
  {
    return employeeProfile;
  }

  public boolean hasEmployeeProfile()
  {
    boolean has = employeeProfile != null;
    return has;
  }

  public boolean setPatientProfile(PatientProfile aNewPatientProfile)
  {
    boolean wasSet = false;
    if (patientProfile != null && !patientProfile.equals(aNewPatientProfile) && equals(patientProfile.getPerson()))
    {
      //Unable to setPatientProfile, as existing patientProfile would become an orphan
      return wasSet;
    }

    patientProfile = aNewPatientProfile;
    Person anOldPerson = aNewPatientProfile != null ? aNewPatientProfile.getPerson() : null;

    if (!this.equals(anOldPerson))
    {
      if (anOldPerson != null)
      {
        anOldPerson.patientProfile = null;
      }
      if (patientProfile != null)
      {
        patientProfile.setPerson(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setEmployeeProfile(EmployeeProfile aNewEmployeeProfile)
  {
    boolean wasSet = false;
    if (employeeProfile != null && !employeeProfile.equals(aNewEmployeeProfile) && equals(employeeProfile.getPerson()))
    {
      //Unable to setEmployeeProfile, as existing employeeProfile would become an orphan
      return wasSet;
    }

    employeeProfile = aNewEmployeeProfile;
    Person anOldPerson = aNewEmployeeProfile != null ? aNewEmployeeProfile.getPerson() : null;

    if (!this.equals(anOldPerson))
    {
      if (anOldPerson != null)
      {
        anOldPerson.employeeProfile = null;
      }
      if (employeeProfile != null)
      {
        employeeProfile.setPerson(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    PatientProfile existingPatientProfile = patientProfile;
    patientProfile = null;
    if (existingPatientProfile != null)
    {
      existingPatientProfile.delete();
    }
    EmployeeProfile existingEmployeeProfile = employeeProfile;
    employeeProfile = null;
    if (existingEmployeeProfile != null)
    {
      existingEmployeeProfile.delete();
    }
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "patientProfile = "+(getPatientProfile()!=null?Integer.toHexString(System.identityHashCode(getPatientProfile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employeeProfile = "+(getEmployeeProfile()!=null?Integer.toHexString(System.identityHashCode(getEmployeeProfile())):"null")
     + outputString;
  }
}