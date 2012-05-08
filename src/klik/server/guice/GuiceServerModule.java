package klik.server.guice;

import klik.server.handler.RetrieveSetupHandler;
import klik.server.handler.SendGreetingHandler;
import klik.shared.rpc.RetrieveSetupAction;
import klik.shared.rpc.SendGreeting;

import org.apache.commons.logging.Log;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GuiceServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(SendGreeting.class, SendGreetingHandler.class);
		bindHandler(RetrieveSetupAction.class, RetrieveSetupHandler.class);
		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
	}

}
