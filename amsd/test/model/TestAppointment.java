package amsd.test.model;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import amsd.model.AppointmentManagementSystem;

public class TestAppointment {

	
	static AppointmentManagementSystem ams;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ams = AppointmentManagementSystem.getInstance();
		
	}

	
}
