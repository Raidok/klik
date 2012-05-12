package klik.client.mvp.unitelement;

import klik.client.MyCallback;
import klik.client.dispatch.CachingDispatchAsync;
import klik.shared.constants.X10;
import klik.shared.event.UnitStatusChangeEvent;
import klik.shared.event.UnitStatusChangeEvent.UnitStatusChangeHandler;
import klik.shared.model.UnitEventDto;
import klik.shared.rpc.UnitEventAction;
import klik.shared.rpc.UnitEventResult;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class UnitElementPresenter extends
PresenterWidget<UnitElementPresenter.MyView> implements UnitElementUiHandlers {

	public interface MyView extends View, HasUiHandlers<UnitElementUiHandlers> {
		void setRow(String address, String name, boolean status);
		void setOn(boolean isOn);
	}

	private final CachingDispatchAsync dispatcher;
	private String address;

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
		addRegisteredHandler(UnitStatusChangeEvent.getType(), new UnitStatusChangeHandler() {
			@Override
			public void onUnitStatusChange(UnitStatusChangeEvent event) {
				if (address.equals(event.getAddress())) {
					GWT.log("UPDATED "+address + event.getStatus().getState().equals(X10.State.ON));
					getView().setRow(address, event.getStatus().getName(), event.getStatus().getState().equals(X10.State.ON));
				}
			}
		});
	}

	public void set(String address, String name, boolean status) {
		getView().setRow(address, name, status);
		getView().setOn(status);
		this.address = address;
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
		dispatcher.execute(new UnitEventAction(new UnitEventDto(address, function)), new MyCallback<UnitEventResult>(this) {

			@Override
			public void onSuccesss(UnitEventResult result) {
				fireEvent(new UnitStatusChangeEvent(address, result.getStatus()));
				GWT.log("result.getStatus().getState():"+result.getStatus().getState());
				getView().setOn(result.getStatus().getState().equals(X10.State.ON));
			}
		});
	}

	public String getCode() {
		return address;
	}

}
