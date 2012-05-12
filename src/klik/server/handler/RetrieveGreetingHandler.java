package klik.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.server.TempUnitHolder;
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
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public RetrieveGreetingHandler(final Log logger,
			final Provider<ServletContext> servletContext,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public RetrieveGreetingResult execute(final RetrieveGreetingAction action,
			final ExecutionContext context) throws ActionException {

		try {
			String serverInfo = servletContext.get().getServerInfo();

			String userAgent = servletRequest.get().getHeader("User-Agent");

			final String message = "I am running " + serverInfo
					+ ". It looks like you are using:" + userAgent;

			return new RetrieveGreetingResult(message, false, TempUnitHolder.getList());
		}
		catch (Exception cause) {
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