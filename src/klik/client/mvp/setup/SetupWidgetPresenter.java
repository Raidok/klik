package klik.client.mvp.setup;

import java.util.LinkedHashMap;

import klik.client.MyCallback;
import klik.client.dispatch.CachingDispatchAsync;
import klik.shared.constants.ServerAction;
import klik.shared.event.AlertEvent;
import klik.shared.rpc.RetrieveSetupAction;
import klik.shared.rpc.RetrieveSetupResult;
import klik.shared.rpc.SaveSetupAction;
import klik.shared.rpc.SaveSetupResult;
import klik.shared.rpc.SendServerCommandAction;
import klik.shared.rpc.SendServerCommandResult;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SetupWidgetPresenter extends
PresenterWidget<SetupWidgetPresenter.MyView>
implements SetupWidgetUiHandlers {

	public interface MyView extends PopupView, HasUiHandlers<SetupWidgetUiHandlers> {
		void fillFields(boolean isRunning, LinkedHashMap<String, String> comPorts, String activePort);
		void setIsRunning(boolean isRunning);
		String getSelectedPort();
	}

	private final CachingDispatchAsync dispatcher;

	@Inject
	public SetupWidgetPresenter(final EventBus eventBus, final MyView view,
			final CachingDispatchAsync dispatcher) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		dispatcher.execute(new RetrieveSetupAction(), new MyCallback<RetrieveSetupResult>(this) {
			@Override
			public void onSuccesss(RetrieveSetupResult result) {
				getView().fillFields(result.isRunning(), result.getComPorts(), result.getActivePort());
			}
		});
	}

	@Override
	public void onClose() {
	}

	@Override
	public void onShutDown() {
		dispatchServerCommand(ServerAction.SHUT_DOWN);
	}

	@Override
	public void onRestart() {
		dispatchServerCommand(ServerAction.RESTART);
	}

	@Override
	public void onSave() {
		dispatcher.execute(new SaveSetupAction(getView().getSelectedPort()), new MyCallback<SaveSetupResult>(this) {
			@Override
			public void onSuccesss(SaveSetupResult result) {
				getEventBus().fireEvent(new AlertEvent(AlertType.SUCCESS, "Saved!"));
				getView().hide();
			}
		});
	}

	private void dispatchServerCommand(ServerAction action) {
		dispatcher.execute(new SendServerCommandAction(action), new MyCallback<SendServerCommandResult>(this) {
			@Override
			public void onSuccesss(SendServerCommandResult result) {
				getView().setIsRunning(result.isRunning());
				if (result.isRunning()) {
					getEventBus().fireEvent(new AlertEvent(AlertType.SUCCESS, result.getMessage()));
				} else {
					getEventBus().fireEvent(new AlertEvent(AlertType.ERROR, result.getMessage()));
				}
			}
		});
	}
}