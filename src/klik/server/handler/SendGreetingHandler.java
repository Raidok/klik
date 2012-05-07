package klik.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.shared.rpc.SendGreeting;
import klik.shared.rpc.SendGreetingResult;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SendGreetingHandler implements ActionHandler<SendGreeting, SendGreetingResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public SendGreetingHandler(final Log logger,
			final Provider<ServletContext> servletContext,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public SendGreetingResult execute(final SendGreeting action,
			final ExecutionContext context) throws ActionException {

		try {
			String serverInfo = servletContext.get().getServerInfo();

			String userAgent = servletRequest.get().getHeader("User-Agent");

			final String message = "I am running " + serverInfo
					+ ". It looks like you are using:" + userAgent;

			return new SendGreetingResult(message, false);
		}
		catch (Exception cause) {
			logger.error("Unable to send message", cause);
			throw new ActionException(cause);
		}
	}

	@Override
	public void undo(SendGreeting action, SendGreetingResult result,
			ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<SendGreeting> getActionType() {
		return SendGreeting.class;
	}
}