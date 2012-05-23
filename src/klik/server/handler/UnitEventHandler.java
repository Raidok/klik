package klik.server.handler;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.server.x10.X10UnitEventHandler;
import klik.shared.model.UnitStatusDto;
import klik.shared.rpc.UnitEventAction;
import klik.shared.rpc.UnitEventResult;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class UnitEventHandler implements ActionHandler<UnitEventAction, UnitEventResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public UnitEventHandler(final Log logger,
			final Provider<ServletContext> servletContext,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public UnitEventResult execute(final UnitEventAction action,
			final ExecutionContext context) throws ActionException {
		logger.debug("UnitEventHandler");
		try {
			logger.debug("COMMAND "+action.getEvent().getAddress()+" "+action.getEvent().getFunction()+" "+action.getEvent().getValue());

			ArrayList<UnitStatusDto> list = X10UnitEventHandler.handleEvent(action.getEvent());

			return new UnitEventResult(list);
		}
		catch (Exception cause) {
			logger.error("Unable to send response", cause);
			throw new ActionException(cause);
		}
	}

	@Override
	public void undo(UnitEventAction action, UnitEventResult result,
			ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<UnitEventAction> getActionType() {
		return UnitEventAction.class;
	}
}