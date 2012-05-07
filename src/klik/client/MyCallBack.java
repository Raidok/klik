package klik.client;

import klik.shared.event.AlertEvent;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class MyCallBack<T> implements AsyncCallback<T> {

	private final EventBus eventBus;

	public MyCallBack(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void onFailure(Throwable caught) {
		eventBus.fireEvent(new AlertEvent(AlertType.ERROR, "Failed! Message: " + caught.getMessage()));
	}

	@Override
	public abstract void onSuccess(T result);

}
