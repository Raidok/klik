package klik.shared.event;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.event.shared.GwtEvent;

public class AlertEvent extends GwtEvent<AlertEventHandler> {
	public static Type TYPE = new Type();

	private final AlertType alertType;
	private final String message;

	public AlertEvent(AlertType type, String message) {
		this.alertType = type;
		this.message = message;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Type getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AlertEventHandler handler) {
		handler.onAlert(this);
	}

}
