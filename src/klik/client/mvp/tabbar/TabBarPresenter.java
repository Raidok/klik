package klik.client.mvp.tabbar;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class TabBarPresenter extends PresenterWidget<TabBarPresenter.MyView> {

	public interface MyView extends View {
	}

	@Inject
	public TabBarPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
