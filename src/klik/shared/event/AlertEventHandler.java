package klik.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface AlertEventHandler extends EventHandler {

	void onAlert(AlertEvent event);
}