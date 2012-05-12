package klik.server;

import java.io.IOException;

import klik.server.x10.TJX10P;
import klik.shared.model.UnitEventDto;
import x10.CM11ASerialController;
import x10.OperationTimedOutException;

/**
 * Class that handles running various background processes like
 * the CM11A serial controller for example.
 * @author raido
 */
public class Process {

	private static CM11ASerialController cm;

	private Process() {
	}

	private static void createInstance() {
		String comPort = PropertiesManager.getProperty("cm11.port");
		if (comPort != null && !comPort.isEmpty()) {
			try {
				cm = new CM11ASerialController(comPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		System.out.println("BackgroundProcess did not start!");

	}

	public static void sendCommand(UnitEventDto ev) {
		cm.addCommand(TJX10P.createCommand(ev));
	}

	public static void restartThread() {
		if (cm != null) {
			try {
				cm.shutdown(0);
			} catch (OperationTimedOutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		createInstance();
	}

	// TODO shutdown method to be invoked from the UI too
}