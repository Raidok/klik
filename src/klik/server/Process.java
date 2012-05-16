package klik.server;

import java.io.IOException;

import klik.server.x10.TJX10P;
import klik.shared.constants.X10.Function;
import x10.CM11ASerialController;
import x10.OperationTimedOutException;

/**
 * Class that handles running various background processes like
 * the CM11A serial controller for example.
 * @author raido
 */
public class Process {

	private static CM11ASerialController cm;
	private static boolean isRunning = false;

	private Process() {
	}

	private static void createInstance() {
		String comPort = PropertiesManager.getProperty("cm11.port");
		if (comPort != null && !comPort.isEmpty()) {
			try {
				cm = new CM11ASerialController(comPort);
				isRunning = true;
			} catch (IOException e) {
				e.printStackTrace();
				isRunning = false;
			}
			return;
		}
	}

	public static void sendCommand(Function function, String address, int value) {
		cm.addCommand(TJX10P.createCommand(function, address, value));
	}

	public static void restartThread() {
		shutDownThread();
		createInstance();
	}

	public static void shutDownThread() {
		if (cm != null) {
			try {
				cm.shutdown(0);
				isRunning = false;
			} catch (OperationTimedOutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static boolean isRunning() {
		return isRunning;
	}
}