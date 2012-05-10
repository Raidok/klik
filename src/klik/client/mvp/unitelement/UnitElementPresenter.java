package klik.client.mvp.unitelement;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class UnitElementPresenter extends
PresenterWidget<UnitElementPresenter.MyView> {

	public interface MyView extends View {
		void setRow(String code, String name, boolean status);
	}

	@Inject
	public UnitElementPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
