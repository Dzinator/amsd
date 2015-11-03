package amsd;

import static org.junit.Assert.fail;
import amsd.model.AppointmentManagementSystem;
import amsd.persistence.PersistenceAMSD;
import amsd.persistence.PersistenceXStream;
import amsd.view.*;

public class Amsd {

	public static void main(String[] args) {
		// load model
		PersistenceAMSD.loadEventRegistrationModel("amsd.xml");
        Runtime.getRuntime().addShutdownHook(new Thread(){
        	
        	public void run(){
        		AppointmentManagementSystem ams = AppointmentManagementSystem.getInstance();
        		PersistenceAMSD.initializeXStream("amsd.xml");

        		if (!PersistenceXStream.saveToXMLwithXStream(ams))
        			fail("Could not save file.");
        	}
        	
        });
        
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookingPage().setVisible(true);
                AppointmentWindow.getInstance().setVisible(true);
                
            }
        });

        

	}

}
