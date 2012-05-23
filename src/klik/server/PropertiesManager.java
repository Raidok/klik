package klik.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.google.gwt.dev.util.collect.HashMap;

/**
 * This class handles the server properties so they won't
 * disappear when restarting the app. Kind of a database..
 * @author raido
 */
public class PropertiesManager {

	private static final String FILE_NAME = "server.properties";
	private static Properties properties;

	private PropertiesManager() {
	}

	/**
	 * Loads value of the given key from the properties file
	 * @param key
	 * @return value stored in the file
	 */
	public static String getProperty(String key) {
		ensureLoaded();
		String value = properties.getProperty(key);
		return value;
	}

	/**
	 * Saves the given key-value pair to the properties file
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		ensureLoaded();
		properties.setProperty(key, value);
		storeProperties();
	}

	/**
	 * Removes the given key from the file.
	 * @param key
	 */
	public static void removeProperty(String key) {
		ensureLoaded();
		properties.remove(key);
		storeProperties();
	}

	/**
	 * Gets the submap of the given prefix and cuts it off from the
	 * @param prefix
	 */
	public static HashMap<String, String> getSubMap(String prefix) {
		ensureLoaded();
		HashMap<String, String> map = new HashMap<String, String>();
		for (Map.Entry<Object, Object> e : properties.entrySet()) {
			if (e.getKey() instanceof String && e.getValue() instanceof String) {
				if (((String) e.getKey()).indexOf(prefix) == 0) {
					String key = ((String) e.getKey()).substring(prefix.length());
					map.put(key, (String) e.getValue());
				}
			}
		}
		return map;
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
