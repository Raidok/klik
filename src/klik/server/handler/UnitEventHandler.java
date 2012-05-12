package klik.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.server.Process;
import klik.server.TempUnitHolder;
import klik.shared.constants.X10.State;
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
			System.out.println("COMMAND "+action.getEvent().getAddress()+" "+action.getEvent().getFunction()+" "+action.getEvent().getValue());
			Process.sendCommand(action.getEvent());
			UnitStatusDto unit = TempUnitHolder.getStatus(action.getEvent().getAddress());
			State state = null;
			switch (action.getEvent().getFunction()) {
			case ON:
				state = State.ON;
				break;
			case DIM:
			case BRIGHT:
				state = State.DIM;
				break;
			default:
			case OFF:
				state = State.OFF;
				break;
			}
			unit = new UnitStatusDto(unit.getType(), unit.getAddress(), state, unit.getName(), unit.getValue());
			TempUnitHolder.setStatus(unit.getAddress(), unit);
			return new UnitEventResult(unit);
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