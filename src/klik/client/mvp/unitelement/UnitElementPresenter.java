package klik.client.mvp.unitelement;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class UnitElementPresenter extends
PresenterWidget<UnitElementPresenter.MyView> implements UnitElementUiHandlers {

	public interface MyView extends View, HasUiHandlers<UnitElementUiHandlers> {
		void setRow(String code, String name, boolean status);
	}

	@Inject
	public UnitElementPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	public void extend() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOn(boolean isOn) {
		// TODO Auto-generated method stub

	}
}
