/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package amsd.model;
import java.util.*;
import java.sql.Date;

// line 3 "../../model.ump"
public class AppointmentManagementSystem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static AppointmentManagementSystem theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AppointmentManagementSystem Associations
  private List<Person> persons;
  private List<Appointment> appointments;
  private List<PatientProfile> patientProfiles;
  private List<DentistProfile> dentistProfiles;
  private List<HygienistProfile> hygienistProfiles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private AppointmentManagementSystem()
  {
    persons = new ArrayList<Person>();
    appointments = new ArrayList<Appointment>();
    patientProfiles = new ArrayList<PatientProfile>();
    dentistProfiles = new ArrayList<DentistProfile>();
    hygienistProfiles = new ArrayList<HygienistProfile>();
  }

  public static AppointmentManagementSystem getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new AppointmentManagementSystem();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Person getPerson(int index)
  {
    Person aPerson = persons.get(index);
    return aPerson;
  }

  public List<Person> getPersons()
  {
    List<Person> newPersons = Collections.unmodifiableList(persons);
    return newPersons;
  }

  public int numberOfPersons()
  {
    int number = persons.size();
    return number;
  }

  public boolean hasPersons()
  {
    boolean has = persons.size() > 0;
    return has;
  }

  public int indexOfPerson(Person aPerson)
  {
    int index = persons.indexOf(aPerson);
    return index;
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

  public PatientProfile getPatientProfile(int index)
  {
    PatientProfile aPatientProfile = patientProfiles.get(index);
    return aPatientProfile;
  }

  public List<PatientProfile> getPatientProfiles()
  {
    List<PatientProfile> newPatientProfiles = Collections.unmodifiableList(patientProfiles);
    return newPatientProfiles;
  }

  public int numberOfPatientProfiles()
  {
    int number = patientProfiles.size();
    return number;
  }

  public boolean hasPatientProfiles()
  {
    boolean has = patientProfiles.size() > 0;
    return has;
  }

  public int indexOfPatientProfile(PatientProfile aPatientProfile)
  {
    int index = patientProfiles.indexOf(aPatientProfile);
    return index;
  }

  public DentistProfile getDentistProfile(int index)
  {
    DentistProfile aDentistProfile = dentistProfiles.get(index);
    return aDentistProfile;
  }

  public List<DentistProfile> getDentistProfiles()
  {
    List<DentistProfile> newDentistProfiles = Collections.unmodifiableList(dentistProfiles);
    return newDentistProfiles;
  }

  public int numberOfDentistProfiles()
  {
    int number = dentistProfiles.size();
    return number;
  }

  public boolean hasDentistProfiles()
  {
    boolean has = dentistProfiles.size() > 0;
    return has;
  }

  public int indexOfDentistProfile(DentistProfile aDentistProfile)
  {
    int index = dentistProfiles.indexOf(aDentistProfile);
    return index;
  }

  public HygienistProfile getHygienistProfile(int index)
  {
    HygienistProfile aHygienistProfile = hygienistProfiles.get(index);
    return aHygienistProfile;
  }

  public List<HygienistProfile> getHygienistProfiles()
  {
    List<HygienistProfile> newHygienistProfiles = Collections.unmodifiableList(hygienistProfiles);
    return newHygienistProfiles;
  }

  public int numberOfHygienistProfiles()
  {
    int number = hygienistProfiles.size();
    return number;
  }

  public boolean hasHygienistProfiles()
  {
    boolean has = hygienistProfiles.size() > 0;
    return has;
  }

  public int indexOfHygienistProfile(HygienistProfile aHygienistProfile)
  {
    int index = hygienistProfiles.indexOf(aHygienistProfile);
    return index;
  }

  public static int minimumNumberOfPersons()
  {
    return 0;
  }

  public boolean addPerson(Person aPerson)
  {
    boolean wasAdded = false;
    if (persons.contains(aPerson)) { return false; }
    persons.add(aPerson);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePerson(Person aPerson)
  {
    boolean wasRemoved = false;
    if (persons.contains(aPerson))
    {
      persons.remove(aPerson);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addPersonAt(Person aPerson, int index)
  {  
    boolean wasAdded = false;
    if(addPerson(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePersonAt(Person aPerson, int index)
  {
    boolean wasAdded = false;
    if(persons.contains(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPersonAt(aPerson, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfAppointments()
  {
    return 0;
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    appointments.add(aAppointment);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    if (appointments.contains(aAppointment))
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

  public static int minimumNumberOfPatientProfiles()
  {
    return 0;
  }

  public boolean addPatientProfile(PatientProfile aPatientProfile)
  {
    boolean wasAdded = false;
    if (patientProfiles.contains(aPatientProfile)) { return false; }
    patientProfiles.add(aPatientProfile);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePatientProfile(PatientProfile aPatientProfile)
  {
    boolean wasRemoved = false;
    if (patientProfiles.contains(aPatientProfile))
    {
      patientProfiles.remove(aPatientProfile);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addPatientProfileAt(PatientProfile aPatientProfile, int index)
  {  
    boolean wasAdded = false;
    if(addPatientProfile(aPatientProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPatientProfiles()) { index = numberOfPatientProfiles() - 1; }
      patientProfiles.remove(aPatientProfile);
      patientProfiles.add(index, aPatientProfile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePatientProfileAt(PatientProfile aPatientProfile, int index)
  {
    boolean wasAdded = false;
    if(patientProfiles.contains(aPatientProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPatientProfiles()) { index = numberOfPatientProfiles() - 1; }
      patientProfiles.remove(aPatientProfile);
      patientProfiles.add(index, aPatientProfile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPatientProfileAt(aPatientProfile, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfDentistProfiles()
  {
    return 0;
  }

  public boolean addDentistProfile(DentistProfile aDentistProfile)
  {
    boolean wasAdded = false;
    if (dentistProfiles.contains(aDentistProfile)) { return false; }
    dentistProfiles.add(aDentistProfile);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDentistProfile(DentistProfile aDentistProfile)
  {
    boolean wasRemoved = false;
    if (dentistProfiles.contains(aDentistProfile))
    {
      dentistProfiles.remove(aDentistProfile);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addDentistProfileAt(DentistProfile aDentistProfile, int index)
  {  
    boolean wasAdded = false;
    if(addDentistProfile(aDentistProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDentistProfiles()) { index = numberOfDentistProfiles() - 1; }
      dentistProfiles.remove(aDentistProfile);
      dentistProfiles.add(index, aDentistProfile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDentistProfileAt(DentistProfile aDentistProfile, int index)
  {
    boolean wasAdded = false;
    if(dentistProfiles.contains(aDentistProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDentistProfiles()) { index = numberOfDentistProfiles() - 1; }
      dentistProfiles.remove(aDentistProfile);
      dentistProfiles.add(index, aDentistProfile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDentistProfileAt(aDentistProfile, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfHygienistProfiles()
  {
    return 0;
  }

  public boolean addHygienistProfile(HygienistProfile aHygienistProfile)
  {
    boolean wasAdded = false;
    if (hygienistProfiles.contains(aHygienistProfile)) { return false; }
    hygienistProfiles.add(aHygienistProfile);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHygienistProfile(HygienistProfile aHygienistProfile)
  {
    boolean wasRemoved = false;
    if (hygienistProfiles.contains(aHygienistProfile))
    {
      hygienistProfiles.remove(aHygienistProfile);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addHygienistProfileAt(HygienistProfile aHygienistProfile, int index)
  {  
    boolean wasAdded = false;
    if(addHygienistProfile(aHygienistProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHygienistProfiles()) { index = numberOfHygienistProfiles() - 1; }
      hygienistProfiles.remove(aHygienistProfile);
      hygienistProfiles.add(index, aHygienistProfile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHygienistProfileAt(HygienistProfile aHygienistProfile, int index)
  {
    boolean wasAdded = false;
    if(hygienistProfiles.contains(aHygienistProfile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHygienistProfiles()) { index = numberOfHygienistProfiles() - 1; }
      hygienistProfiles.remove(aHygienistProfile);
      hygienistProfiles.add(index, aHygienistProfile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHygienistProfileAt(aHygienistProfile, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    persons.clear();
    appointments.clear();
    patientProfiles.clear();
    dentistProfiles.clear();
    hygienistProfiles.clear();
  }

}