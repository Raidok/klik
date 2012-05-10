package klik.client.mvp.unitelement;

import klik.client.MyCallback;
import klik.client.dispatch.CachingDispatchAsync;
import klik.shared.constants.X10;
import klik.shared.model.UnitEventDto;
import klik.shared.rpc.UnitEventAction;
import klik.shared.rpc.UnitEventResult;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class UnitElementPresenter extends
PresenterWidget<UnitElementPresenter.MyView> implements UnitElementUiHandlers {

	public interface MyView extends View, HasUiHandlers<UnitElementUiHandlers> {
		void setRow(String code, String name, boolean status);
		void setOn(boolean isOn);
	}

	private final CachingDispatchAsync dispatcher;
	private String code;

	@Inject
	public UnitElementPresenter(final EventBus eventBus, final MyView view,
			CachingDispatchAsync dispatcher) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	public void set(String code, String name, boolean status) {
		getView().setRow(code, name, status);
		getView().setOn(status);
		this.code = code;
	}

	@Override
	public void extend() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOn(boolean isOn) {
		X10.Function function;
		if (isOn) {
			function = X10.Function.ON;
		} else {
			function = X10.Function.OFF;
		}
		dispatcher.execute(new UnitEventAction(new UnitEventDto(code, function)), new MyCallback<UnitEventResult>(this) {

			@Override
			public void onSuccess(UnitEventResult result) {
				getView().setOn(result.getStatus().getState().equals(X10.State.ON));
			}
		});
	}


}
