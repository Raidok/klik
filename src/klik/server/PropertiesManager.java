package klik.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * This class handles the server properties so they won't
 * disappear when restarting the app.
 * @author raido
 */
public class PropertiesManager {

	private static final String FILE_NAME = "server.properties";
	private static Properties properties;

	private PropertiesManager() {
	}

	public static String getProperty(String key) {
		ensureLoaded();
		String value = properties.getProperty(key);
		System.out.println("requested key:"+key+" value:"+value);
		return value;
	}

	public static void setProperty(String key, String value) {
		ensureLoaded();
		properties.setProperty(key, value);
		storeProperties();
	}

	private static void ensureLoaded() {
		if (properties == null) {
			properties = new Properties();
			loadProperties();
		}
	}

	private static void loadProperties() {
		try {
			properties.load(new FileInputStream(FILE_NAME));
		} catch (FileNotFoundException e) {
			createFile();
		} catch (Exception e) {
			System.out.println("Exception while loading properties.");
			e.printStackTrace();
		}
		System.out.println("Properties loaded!");
	}

	private static void storeProperties() {
		try {
			properties.store(new FileOutputStream(FILE_NAME), null);
		} catch (FileNotFoundException e) {
			createFile();
		} catch (IOException e) {
			System.out.println("Exception while storing properties.");
			e.printStackTrace();
		}
		System.out.println("Properties safely stored!");
	}

	private static void createFile() {
		System.out.println("Creating server.properties file...");
		try {
			new FileWriter(FILE_NAME);
		} catch (IOException e1) {
			System.out.println("Exception while creating file.");
			e1.printStackTrace();
		}
		System.out.println("File created!");
	}
}
