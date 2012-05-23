package klik.server.handler;

import javax.servlet.http.HttpServletRequest;

import klik.server.data.DataManager;
import klik.server.x10.X10Util;
import klik.shared.rpc.RetrieveGreetingAction;
import klik.shared.rpc.RetrieveGreetingResult;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class RetrieveGreetingHandler implements ActionHandler<RetrieveGreetingAction, RetrieveGreetingResult> {
	private final Log logger;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public RetrieveGreetingHandler(final Log logger,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletRequest = servletRequest;
	}

	@Override
	public RetrieveGreetingResult execute(final RetrieveGreetingAction action,
			final ExecutionContext context) throws ActionException {

		try {

			// check session
			if (servletRequest.get().getSession(false) == null) {
				servletRequest.get().getSession(true); // start a session
				return new RetrieveGreetingResult("Looks like you are here for the first time. " +
						" Please check the settings before starting.",
						false, X10Util.createDto(DataManager.getUnits()));
			} else {
				return new RetrieveGreetingResult(null, true, X10Util.createDto(DataManager.getUnits()));
			}

		} catch (Exception cause) {
			logger.error("Unable to send message", cause);
			throw new ActionException(cause);
		}
	}

	@Override
	public void undo(RetrieveGreetingAction action, RetrieveGreetingResult result,
			ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<RetrieveGreetingAction> getActionType() {
		return RetrieveGreetingAction.class;
	}
}