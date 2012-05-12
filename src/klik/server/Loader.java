package klik.server;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This class deals with the background processes.
 * The init method gets called when the servlet container starts,
 * it is set to do this in the web.xml file.
 * @author raido
 */
public class Loader implements Servlet {

	private static Process INSTANCE;

	public Loader() {
		System.out.println("Initial load");
	}

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return "Loads and runs background daemons.";
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		Process.restartThread();
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		// do nothing
	}


}
