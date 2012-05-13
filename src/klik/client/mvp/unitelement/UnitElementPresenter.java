package klik.client.mvp.unitelement;

import klik.client.MyCallback;
import klik.client.dispatch.CachingDispatchAsync;
import klik.shared.constants.X10;
import klik.shared.constants.X10.State;
import klik.shared.constants.X10.Type;
import klik.shared.event.UnitStatusChangeEvent;
import klik.shared.event.UnitStatusChangeEvent.UnitStatusChangeHandler;
import klik.shared.model.UnitEventDto;
import klik.shared.model.UnitStatusDto;
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
		void set(String address, String name, boolean isOn, boolean isDimmable);
		void setOn(boolean isOn);
	}

	private final CachingDispatchAsync dispatcher;
	private UnitStatusDto status;
	private String address; // TODO replace with id

	@Inject
	public UnitElementPresenter(final EventBus eventBus, final MyView view,
			CachingDispatchAsync dispatcher, UnitStatusDto status) {
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
					setStatus(event.getStatus());
				}
			}
		});
	}

	public void setStatus(UnitStatusDto status) {
		if (this.status == null || !this.status.equals(status)) {
			this.status = status;
			this.address = status.getAddress();
			getView().set(address, status.getName(), !status.getState().equals(State.OFF), status.getType().equals(Type.DIMMABLE_LIGHT));
		}
	}

	@Override
	public void extend() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOn(boolean isOn) {
		if (isOn) {
			dispatch(X10.Function.ON, 100);
		} else {
			dispatch(X10.Function.OFF, 0);
		}
	}

	@Override
	public void bright() {
		dispatch(X10.Function.BRIGHT, 10);
	}

	@Override
	public void dim() {
		dispatch(X10.Function.DIM, -10);
	}

	private void dispatch(X10.Function function, int value) {
		GWT.log("SEND:"+function+" level:"+value);
		dispatcher.execute(new UnitEventAction(new UnitEventDto(address, function, value)), new MyCallback<UnitEventResult>(this) {

			@Override
			public void onSuccesss(UnitEventResult result) {
				setStatus(result.getStatus());
				GWT.log("receive:"+result.getStatus().getState()+" level:"+result.getStatus().getValue());
				getView().setOn(result.getStatus().getValue() > 0);
			}
		});
	}
}
