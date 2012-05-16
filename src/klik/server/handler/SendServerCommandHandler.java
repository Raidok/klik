package klik.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.server.Process;
import klik.shared.rpc.SendServerCommandAction;
import klik.shared.rpc.SendServerCommandResult;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SendServerCommandHandler implements ActionHandler<SendServerCommandAction, SendServerCommandResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public SendServerCommandHandler(final Log logger,
			final Provider<ServletContext> servletContext,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public SendServerCommandResult execute(final SendServerCommandAction action,
			final ExecutionContext context) throws ActionException {
		logger.debug("SaveSetupHandler");
		try {
			switch (action.getAction()) {
			case RESTART:
				try {
					Process.restartThread();
					return new SendServerCommandResult(true, "Background process was restarted!");
				} catch (Exception e) {
					throw new ActionException("Background process restart failed!", e);
				}

			default:
				try {
					Process.shutDownThread();
					return new SendServerCommandResult(true, "Background process was shut down!");
				} catch (Exception e) {
					throw new ActionException("Background process shut down failed!", e);
				}
			}

		}
		catch (Exception cause) {
			logger.error("Unable to send response", cause);
			throw new ActionException(cause);
		}
	}

	@Override
	public void undo(SendServerCommandAction action, SendServerCommandResult result,
			ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<SendServerCommandAction> getActionType() {
		return SendServerCommandAction.class;
	}
}