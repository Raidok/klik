package klik.client;

import klik.client.gin.MyGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MainEntry implements EntryPoint {
	public final MyGinjector ginjector = GWT.create(MyGinjector.class);

	@Override
	public void onModuleLoad() {

		DelayedBindRegistry.bind(ginjector);
		ginjector.getCss();
		ginjector.getPlaceManager().revealCurrentPlace();
	}
}