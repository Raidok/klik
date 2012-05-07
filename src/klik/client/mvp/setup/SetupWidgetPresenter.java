package klik.client.mvp.setup;

import klik.shared.event.AlertEvent;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SetupWidgetPresenter extends PresenterWidget<SetupWidgetPresenter.MyView>
implements SetupWidgetUiHandlers {

	public interface MyView extends PopupView, HasUiHandlers<SetupWidgetUiHandlers> {
	}

	@Inject
	public SetupWidgetPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	public void onClose() {
		getView().hide();
	}

	@Override
	public void onSave() {
		getEventBus().fireEvent(new AlertEvent(AlertType.SUCCESS, "Saved!"));
		getView().hide();
	}
}
