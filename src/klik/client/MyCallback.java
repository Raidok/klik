package klik.client;

import klik.shared.event.AlertEvent;
import klik.shared.event.LoadingEvent;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class MyCallback<T> implements AsyncCallback<T> {

	private final HasHandlers handlers;

	public MyCallback(HasHandlers handlers) {
		this.handlers = handlers;
		handlers.fireEvent(new LoadingEvent(true));
	}

	@Override
	public void onFailure(Throwable caught) {
		handlers.fireEvent(new AlertEvent(AlertType.ERROR, "Failed! Message: " + caught.getMessage()));
	}

	@Override
	public void onSuccess(T result) {
		onSuccesss(result);
		handlers.fireEvent(new LoadingEvent(false));
	}

	public abstract void onSuccesss(T result);

}
