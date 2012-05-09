package klik.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.server.Process;
import klik.server.PropertiesManager;
import klik.shared.rpc.SaveSetupAction;
import klik.shared.rpc.SaveSetupResult;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SaveSetupHandler implements ActionHandler<SaveSetupAction, SaveSetupResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public SaveSetupHandler(final Log logger,
			final Provider<ServletContext> servletContext,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public SaveSetupResult execute(final SaveSetupAction action,
			final ExecutionContext context) throws ActionException {
		logger.debug("SaveSetupHandler");
		try {
			System.out.println("SAVE");
			PropertiesManager.setProperty("cm11.port", action.getComPort());
			try {
				Process.restartThread();
			} catch (Exception e) {
				throw new ActionException("Background process restart failed!");
			}
			return new SaveSetupResult();
		}
		catch (Exception cause) {
			logger.error("Unable to send response", cause);
			throw new ActionException(cause);
		}
	}

	@Override
	public void undo(SaveSetupAction action, SaveSetupResult result,
			ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<SaveSetupAction> getActionType() {
		return SaveSetupAction.class;
	}
}