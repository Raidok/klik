package klik.client.mvp.setup;

import java.util.LinkedHashMap;

import klik.client.MyCallback;
import klik.client.dispatch.CachingDispatchAsync;
import klik.shared.event.AlertEvent;
import klik.shared.rpc.RetrieveSetupAction;
import klik.shared.rpc.RetrieveSetupResult;
import klik.shared.rpc.SaveSetupAction;
import klik.shared.rpc.SaveSetupResult;

import com.allen_sauer.gwt.log.client.Log;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SetupWidgetPresenter extends PresenterWidget<SetupWidgetPresenter.MyView>
implements SetupWidgetUiHandlers {

	public interface MyView extends PopupView, HasUiHandlers<SetupWidgetUiHandlers> {
		void fillFields(LinkedHashMap<String, String> comPorts, String activePort);
		String getSelectedPort();
	}

	private final CachingDispatchAsync dispatcher;

	@Inject
	public SetupWidgetPresenter(final EventBus eventBus, final MyView view,
			CachingDispatchAsync dispatcher) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		Log.debug("BIND");
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		Log.debug("REVEAL");
		dispatcher.execute(new RetrieveSetupAction(), new MyCallback<RetrieveSetupResult>(this) {
			@Override
			public void onSuccesss(RetrieveSetupResult result) {
				getView().fillFields(result.getComPorts(), result.getActivePort());
			}
		});
	}

	@Override
	public void onClose() {
		Log.debug("SAVE");
		getView().hide();
	}

	@Override
	public void onSave() {
		Log.debug("CLOSE");
		dispatcher.execute(new SaveSetupAction(getView().getSelectedPort()), new MyCallback<SaveSetupResult>(this) {

			@Override
			public void onSuccesss(SaveSetupResult result) {
				getEventBus().fireEvent(new AlertEvent(AlertType.SUCCESS, "Saved!"));
				getView().hide();
			}
		});
	}
}