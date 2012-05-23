package klik.client.mvp.tabbar;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class TabBarPresenter extends PresenterWidget<TabBarPresenter.MyView> {

	public interface MyView extends View {
	}

	private final PlaceManager placeManager;

	@Inject
	public TabBarPresenter(final EventBus eventBus, final MyView view,
			PlaceManager placeManager) {
		super(eventBus, view);
		this.placeManager = placeManager;
	}

	@Override
	protected void onBind() {
		super.onBind();
		placeManager.getCurrentPlaceRequest();
	}
}
