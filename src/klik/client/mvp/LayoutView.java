package klik.client.mvp;

import java.util.Date;

import klik.client.resources.Resources;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LayoutView extends ViewWithUiHandlers<LayoutUiHandlers> implements LayoutPresenter.MyView {

	public interface Binder extends UiBinder<Widget, LayoutView> {
	}

	private final Widget widget;
	private String loading;
	private String backup;
	private long loadStart;
	@UiField HTMLPanel alertPanel;
	@UiField HTMLPanel contentPanel;
	@UiField Button refreshButton;

	@Inject
	public LayoutView(final Binder binder, final Resources resources) {
		loading = new Image(resources.loading()).toString();
		widget = binder.createAndBindUi(this);
		backup = refreshButton.getElement().getInnerHTML();
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
			refreshButton.getElement().setInnerHTML(loading);
			loadStart = new Date().getTime();
		} else {
			GWT.log("hide loading");
			long time = (new Date().getTime() - loadStart);
			GWT.log("TIME:"+time);
			new Timer() {
				@Override
				public void run() {
					refreshButton.getElement().setInnerHTML(backup);
				}
			}.schedule(time < 200 ? 200 : 0); // run it with a little delay
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

	@UiHandler("refreshButton")
	void onRefreshClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().onRefresh();
		}
	}
}
