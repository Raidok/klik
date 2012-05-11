package klik.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.server.Process;
import klik.shared.constants.X10.Function;
import klik.shared.constants.X10.State;
import klik.shared.constants.X10.Type;
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
			System.out.println("COMMAND "+action.getEvent().getAddress()+" "+action.getEvent().getFunction());
			Process.sendCommand(action.getEvent());
			return new UnitEventResult(new UnitStatusDto(
					Type.DIMMABLE_LIGHT,
					"XX",
					action.getEvent().getFunction().equals(Function.ON) ? State.ON : State.OFF,
					"asd"));
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