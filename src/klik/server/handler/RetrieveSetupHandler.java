package klik.server.handler;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.shared.rpc.RetrieveSetupAction;
import klik.shared.rpc.RetrieveSetupResult;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class RetrieveSetupHandler implements ActionHandler<RetrieveSetupAction, RetrieveSetupResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public RetrieveSetupHandler(final Log logger,
			final Provider<ServletContext> servletContext,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public RetrieveSetupResult execute(final RetrieveSetupAction action,
			final ExecutionContext context) throws ActionException {
		logger.debug("RetrieveSetupHandler");
		try {
			@SuppressWarnings("rawtypes")
			Enumeration en = CommPortIdentifier.getPortIdentifiers();
			LinkedHashMap<String, String> ports = new LinkedHashMap<String, String>();
			for (; en.hasMoreElements();) {
				String port = en.nextElement().toString();
				logger.debug(" port:"+port);
				ports.put(port, port);
			}
			return new RetrieveSetupResult(ports);
		}
		catch (Exception cause) {
			logger.error("Unable to send comPortMap", cause);
			throw new ActionException(cause);
		}
	}

	@Override
	public void undo(RetrieveSetupAction action, RetrieveSetupResult result,
			ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<RetrieveSetupAction> getActionType() {
		return RetrieveSetupAction.class;
	}
}