package klik.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.server.Process;
import klik.server.TempUnitHolder;
import klik.shared.constants.X10.Function;
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
			String address = action.getEvent().getAddress(); // replace with id
			logger.debug("COMMAND "+address+" "+action.getEvent().getFunction()+" "+action.getEvent().getValue());
			UnitStatusDto unit = TempUnitHolder.getStatus(address);
			State state = null;
			int value = unit.getValue();
			switch (action.getEvent().getFunction()) {
			case ON:
				state = State.ON;
				value = 100;
				break;
			case BRIGHT:
				/*if (unit.getValue() < 50) { // TODO should be user modifiable
					value = 40;
				}*/
			case DIM:
				state = State.DIM;
				value += action.getEvent().getValue();
				if (value >= 0) { // TODO should be user modifiable
					if (value >= 100) {
						value = 100;
						state = State.ON;
					}
					break;
				}
			case OFF:
			default:
				state = State.OFF;
				value = 0;
			}

			logger.debug("processed state:"+state+" value:"+value+" diff:"+(unit.getValue() - value));

			Function function = action.getEvent().getFunction();
			int amount = 0;
			if (state == State.ON) { // override function if jump to 0/100 occured
				function = Function.ON;
			} else if (state == State.OFF) {
				function = Function.OFF;
			} else {
				amount = value == 0 ? 0 : Math.abs(unit.getValue() - value);
			}
			Process.sendCommand(function, address, amount);

			unit = new UnitStatusDto(unit.getType(), address, state, unit.getName(), value);
			TempUnitHolder.setStatus(address, unit);

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