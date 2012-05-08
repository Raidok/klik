package klik.client.mvp;

import klik.client.resources.Resources;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.NavText;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class LayoutView extends ViewImpl implements LayoutPresenter.MyView {

	public interface Binder extends UiBinder<Widget, LayoutView> {
	}

	private final Widget widget;

	@UiField(provided = true) final Resources resources;
	@UiField HTMLPanel alertPanel;
	@UiField HTMLPanel contentPanel;
	@UiField NavText loading;

	@Inject
	public LayoutView(final Binder binder, final Resources resources) {
		this.resources = resources;
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == LayoutPresenter.TYPE_SetContent) {
			contentPanel.clear();

			if (content != null) {
				contentPanel.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void showLoading(boolean visible) {
		if (visible) {
			GWT.log("show loading");
			loading.setVisible(visible);
		} else {
			GWT.log("hide loading");
			new Timer() {
				@Override
				public void run() {
					loading.setVisible(false);
				}
			}.schedule(50); // run it with a little delay
		}
	}

	@Override
	public void clearAlerts() {
		if (alertPanel.getWidgetCount() > 0) {
			alertPanel.clear();
		}
	}

	@Override
	public void showAlert(AlertType type, String message) {
		Alert alert = new Alert();
		alert.setType(type);
		alert.setText(message);
		alert.setAnimation(true);
		alertPanel.add(alert);
	}
}
