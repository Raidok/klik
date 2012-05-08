package klik.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PropertiesManager implements Servlet {

	private static String FILE_NAME = "server.properties";
	private static Properties properties;


	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		return "Servlet for loading and storing server properties";
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		properties = new Properties();
		loadProperties();
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
		storeProperties();
	}

	private void loadProperties() {
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

	private void storeProperties() {
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

	private void createFile() {
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
