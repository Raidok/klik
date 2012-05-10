package klik.server;


public class Process extends Thread {

	private static Process INSTANCE;
	private volatile boolean isRunning;

	public Process() {
		super("BackgroundProcess");
		System.out.println("LOADER CONSTRUCT");
		String port = PropertiesManager.getProperty("cm11.port");
		System.out.println("got port : ["+port+"]");
		isRunning = true;
	}

	@Override
	public void run() {
		synchronized (this) {
			while(isRunning) {
				System.out.println("run");
				for(int i = 0; i < Integer.MAX_VALUE; i++); // make it to do some work, temporarily
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
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
		INSTANCE.isRunning = false;
		try {
			INSTANCE.join(); // wait for the thread to finish it's last loop
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Stopped!");
	}
}