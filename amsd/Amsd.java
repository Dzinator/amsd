package amsd;

import amsd.persistence.PersistenceAMSD;
import amsd.view.*;

public class Amsd {

	public static void main(String[] args) {
		// load model
		//PersistenceAMSD.loadEventRegistrationModel();
		
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookingPage().setVisible(true);
                new MainWindow().setVisible(true);
            }
        });

	}

}
