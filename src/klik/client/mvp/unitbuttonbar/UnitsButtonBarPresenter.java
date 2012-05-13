package klik.client.mvp.unitbuttonbar;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class UnitsButtonBarPresenter extends
PresenterWidget<UnitsButtonBarPresenter.MyView> implements UnitsButtonBarUiHandlers {

	public interface MyView extends View, HasUiHandlers<UnitsButtonBarUiHandlers> {
	}

	@Inject
	public UnitsButtonBarPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
