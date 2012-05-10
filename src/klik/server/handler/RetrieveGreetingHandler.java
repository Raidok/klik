package klik.server.handler;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import klik.shared.constants.X10;
import klik.shared.model.UnitDto;
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

			ArrayList<UnitDto> list = new ArrayList<UnitDto>();
			list.add(new UnitDto(X10.Type.DIMMABLE_LIGHT, X10.HouseCode.C, X10.UnitCode.U9, X10.State.ON, "Desk"));
			list.add(new UnitDto(X10.Type.DIMMABLE_LIGHT, X10.HouseCode.A, X10.UnitCode.U1, X10.State.OFF, "Wall"));

			return new RetrieveGreetingResult(message, false, list);
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