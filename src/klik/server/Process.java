package klik.server;


public class Process extends Thread {

	private static Process INSTANCE;

	public Process() {
		super("BackgroundProcess");
		System.out.println("LOADER CONSTRUCT");
		String port = PropertiesManager.getProperty("cm11.port");
		System.out.println("got port : ["+port+"]");
	}

	@Override
	public void run() {
		for (int j = 0; j < 5; j++) {
			System.out.println("run");
			for(int i = 0; i < 1000000; i++);
		}
		System.out.println("what's next?");
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
		try {
			System.out.println("Stopping...");
			INSTANCE.wait();
			INSTANCE.join();
			System.out.println("Stopped");
		} catch (InterruptedException e) {
			System.out.println("Join failed!");
			e.printStackTrace();
		}
	}
}