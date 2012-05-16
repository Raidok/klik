package klik.server.guice;

import klik.server.handler.RetrieveGreetingHandler;
import klik.server.handler.RetrieveSetupHandler;
import klik.server.handler.RetrieveUnitStatusesHandler;
import klik.server.handler.SaveSetupHandler;
import klik.server.handler.SendServerCommandHandler;
import klik.server.handler.UnitEventHandler;
import klik.shared.rpc.RetrieveGreetingAction;
import klik.shared.rpc.RetrieveSetupAction;
import klik.shared.rpc.RetrieveUnitStatusesAction;
import klik.shared.rpc.SaveSetupAction;
import klik.shared.rpc.SendServerCommandAction;
import klik.shared.rpc.UnitEventAction;

import org.apache.commons.logging.Log;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GuiceServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(RetrieveGreetingAction.class, RetrieveGreetingHandler.class);
		bindHandler(RetrieveSetupAction.class, RetrieveSetupHandler.class);
		bindHandler(SaveSetupAction.class, SaveSetupHandler.class);
		bindHandler(UnitEventAction.class, UnitEventHandler.class);
		bindHandler(RetrieveUnitStatusesAction.class, RetrieveUnitStatusesHandler.class);
		bindHandler(SendServerCommandAction.class, SendServerCommandHandler.class);
		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
	}

}
