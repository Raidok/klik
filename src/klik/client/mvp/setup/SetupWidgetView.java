package klik.client.mvp.setup;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.Modal;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewCloseHandler;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class SetupWidgetView extends PopupViewWithUiHandlers<SetupWidgetUiHandlers>
implements SetupWidgetPresenter.MyView {

	public interface Binder extends UiBinder<Widget, SetupWidgetView> {
	}

	private final Widget widget;
	@UiField(provided = true) Modal modal;
	@UiField ControlGroup activeGroup;
	@UiField Label activePortLabel;
	@UiField Label statusLabel;
	@UiField ListBox comPortListBox;
	@UiField Button shutDownBtn;
	@UiField Button restartBtn;
	@UiField Button saveBtn;

	@Inject
	public SetupWidgetView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		setUpDialog();
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	private void setUpDialog() {
		modal = new Modal() {
			@Override
			protected void onUnload() {
				SetupWidgetView.this.hide();
			}
		};
	}

	@Override
	public void center() {
		modal.show();
	}

	@Override
	public void hide() {
		modal.hide();
		if (getUiHandlers() != null) {
			getUiHandlers().onClose();
		}
	}

	@Override
	public void setAutoHideOnNavigationEventEnabled(boolean autoHide) {
	}

	@Override
	public void setCloseHandler(PopupViewCloseHandler popupViewCloseHandler) {
	}

	@Override
	public void setPosition(int left, int top) {
	}

	@Override
	public void show() {
		modal.show();
	}

	@UiHandler("shutDownBtn")
	public void onShutDownClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().onShutDown();
		}
	}

	@UiHandler("restartBtn")
	public void onRestartClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().onRestart();
		}
	}

	@UiHandler("saveBtn")
	public void onSaveClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().onSave();
		}
	}

	@Override
	public void fillFields(boolean isRunning, LinkedHashMap<String, String> comPorts, String activePort) {
		comPortListBox.clear();
		setIsRunning(isRunning);
		if (activePort != null) {
			activePortLabel.setText(activePort);
			activeGroup.setVisible(true);
		} else {
			activeGroup.setVisible(false);
		}
		for (Map.Entry<String, String> e : comPorts.entrySet()) {
			comPortListBox.addItem(e.getKey(), e.getValue());
		}
	}

	@Override
	public void setIsRunning(boolean isRunning) {
		if (isRunning) {
			statusLabel.setText("Running");
			shutDownBtn.setText("Shut down");
		} else {
			statusLabel.setText("Stopped");
			shutDownBtn.setText("Start up");
		}
	}

	@Override
	public String getSelectedPort() {
		return comPortListBox.getValue(comPortListBox.getSelectedIndex());
	}
}
