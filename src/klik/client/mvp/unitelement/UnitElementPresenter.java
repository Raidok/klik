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
		void setRow(String address, String name, boolean status, boolean isDimmable);
		void setOn(boolean isOn);
	}

	private final CachingDispatchAsync dispatcher;
	private String address;
	private int brightness;

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
					GWT.log("UPDATED "+address + " " + event.getStatus().getState().equals(X10.State.ON));
					getView().setRow(address, event.getStatus().getName(),
							event.getStatus().getValue() > 0,
							event.getStatus().getType().equals(X10.Type.DIMMABLE_LIGHT));
				}
			}
		});
	}

	public void set(String address, String name, boolean status, boolean isDimmable) {
		getView().setRow(address, name, status, isDimmable);
		getView().setOn(status);
		this.address = address;
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

	public String getCode() {
		return address;
	}

	@Override
	public void bright() {
		dispatch(X10.Function.BRIGHT, dim(10));
	}

	@Override
	public void dim() {
		dispatch(X10.Function.DIM, dim(-10));
	}

	private int dim(int amount) {
		brightness += amount;
		brightness = brightness > 100 ? 100 : brightness < 0 ? 0 : brightness;
		return brightness;
	}

	private void dispatch(X10.Function function, int value) {
		GWT.log("SEND:"+function+" level:"+value);
		dispatcher.execute(new UnitEventAction(new UnitEventDto(address, function, value)), new MyCallback<UnitEventResult>(this) {

			@Override
			public void onSuccesss(UnitEventResult result) {
				fireEvent(new UnitStatusChangeEvent(address, result.getStatus()));
				GWT.log("receive:"+result.getStatus().getState()+" level:"+result.getStatus().getValue());
				getView().setOn(result.getStatus().getValue() > 0);
			}
		});
	}
}
