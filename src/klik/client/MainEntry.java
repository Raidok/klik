package klik.client;

import klik.client.gin.MyGinjector;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MainEntry implements EntryPoint, UncaughtExceptionHandler {
	public final MyGinjector ginjector = GWT.create(MyGinjector.class);

	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(this);
		DelayedBindRegistry.bind(ginjector);
		ginjector.getCss();
		ginjector.getPlaceManager().revealCurrentPlace();
	}

	@Override
	public void onUncaughtException(Throwable e) {
		Log.error(e.getMessage(), e);
	}
}