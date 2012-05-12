package klik.server.handler;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.shared.constants.X10;
import klik.shared.model.UnitStatusDto;
import klik.shared.rpc.RetrieveUnitStatusesAction;
import klik.shared.rpc.RetrieveUnitStatusesResult;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class RetrieveUnitStatusesHandler implements ActionHandler<RetrieveUnitStatusesAction, RetrieveUnitStatusesResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public RetrieveUnitStatusesHandler(final Log logger,
			final Provider<ServletContext> servletContext,
			final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	@Override
	public RetrieveUnitStatusesResult execute(final RetrieveUnitStatusesAction action,
			final ExecutionContext context) throws ActionException {

		try {
			ArrayList<UnitStatusDto> list = new ArrayList<UnitStatusDto>();
			list.add(new UnitStatusDto(X10.Type.DIMMABLE_LIGHT, "C9", X10.State.OFF, "Desk"));
			list.add(new UnitStatusDto(X10.Type.DIMMABLE_LIGHT, "A1", X10.State.ON, "Wall"));

			return new RetrieveUnitStatusesResult(list);
		}
		catch (Exception cause) {
			logger.error("Unable to send message", cause);
			throw new ActionException(cause);
		}
	}

	@Override
	public void undo(RetrieveUnitStatusesAction action, RetrieveUnitStatusesResult result,
			ExecutionContext context) throws ActionException {
		// Nothing to do here
	}

	@Override
	public Class<RetrieveUnitStatusesAction> getActionType() {
		return RetrieveUnitStatusesAction.class;
	}
}