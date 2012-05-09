package klik.server;


public class Process extends Thread {

	private static Process INSTANCE;
	private boolean isRunning;

	public Process() {
		super("BackgroundProcess");
		System.out.println("LOADER CONSTRUCT");
		String port = PropertiesManager.getProperty("cm11.port");
		System.out.println("got port : ["+port+"]");
		isRunning = true;
	}

	@Override
	public void run() {
		while(isRunning) {
			synchronized (this) {
				System.out.println("run");
				for(int i = 0; i < Integer.MAX_VALUE; i++); // make it to do some work, temporarily
				try {
					wait(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("stops");
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