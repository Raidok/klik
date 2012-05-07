package klik.client;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class MyPlaceManager extends PlaceManagerImpl {

	private final PlaceRequest defaultPlaceRequest;
	private final PlaceRequest errorPlaceRequest;

	@Inject
	public MyPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {
		super(eventBus, tokenFormatter);
		defaultPlaceRequest = new PlaceRequest(NameTokens.home);
		errorPlaceRequest = new PlaceRequest(NameTokens.error);
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest, false);
	}

	@Override
	public void revealErrorPlace(String invalidHistoryToken) {
		// does not fire a new History item
		revealPlace(errorPlaceRequest, false);
	}

}
