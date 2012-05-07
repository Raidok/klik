package klik.server.guice;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		super.configureServlets();
		serve("/" + ActionImpl.DEFAULT_SERVICE_NAME+ "*").with(DispatchServiceImpl.class);
	}

}
