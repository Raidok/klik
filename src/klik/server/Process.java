package klik.server;

import klik.server.x10.TJX10P;
import klik.shared.model.UnitEventDto;
import x10.util.ThreadSafeQueue;


public class Process extends Thread {

	private volatile boolean isRunning;
	private static Process INSTANCE;
	private static ThreadSafeQueue queue;
	private static TJX10P cs;

	public Process() {
		super("BackgroundProcess");
		String comPort = PropertiesManager.getProperty("cm11.port");
		if (comPort != null && !comPort.isEmpty()) {
			cs = new TJX10P(comPort);
			return;
		}
		System.out.println("BackgroundProcess did not start!");

	}

	@Override
	public void run() {
		synchronized (this) {
			while(isRunning) {
				if (queue.peek() instanceof UnitEventDto) {
					cs.addEvent((UnitEventDto) queue.dequeue());
				}
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	public static void startNewThread() {
		if (INSTANCE == null || !INSTANCE.isAlive()) {
			INSTANCE = new Process();
			INSTANCE.start();
		} else {
			System.out.println("Cannot start new thread!");
		}
	}

	public static void restartThread() {
		stopThread();
		startNewThread();
	}

	public static void stopThread() {
		INSTANCE.isRunning = false;
		try {
			INSTANCE.join(); // wait for the thread to finish it's last loop
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Stopped!");
	}
}