package klik.client.mvp.unitelement;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonGroup;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class UnitElementView extends ViewWithUiHandlers<UnitElementUiHandlers> implements
UnitElementPresenter.MyView {

	private final Widget widget;
	@UiField Button extendButton;
	@UiField Label nameLabel;
	@UiField ButtonGroup plusMinusButtons;
	@UiField Button minusButton;
	@UiField Button plusButton;
	@UiField Button onButton;
	@UiField Button offButton;
	@UiField HTMLPanel invisiblePanel;

	public interface Binder extends UiBinder<Widget, UnitElementView> {
	}

	@Inject
	public UnitElementView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setRow(String code, String name, boolean status, boolean isDimmable) {
		nameLabel.setText(name);
		plusMinusButtons.setVisible(isDimmable);
		setOn(status);
	}

	@UiHandler("extendButton")
	void onExtendClick(ClickEvent e) {
		invisiblePanel.setVisible(!invisiblePanel.isVisible());
		extendButton.setIcon(invisiblePanel.isVisible() ? IconType.CHEVRON_UP : IconType.CHEVRON_DOWN);
	}

	@UiHandler("minusButton")
	void minusClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().dim();
		}
	}

	@UiHandler("plusButton")
	void plusClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().bright();
		}
	}

	@UiHandler("onButton")
	void onClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().setOn(true);
		}
	}

	@UiHandler("offButton")
	void offClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().setOn(false);
		}
	}

	@Override
	public void setOn(boolean isOn) {
		if (isOn) {
			onButton.setType(ButtonType.SUCCESS);
			offButton.removeStyle(ButtonType.DANGER);
		} else {
			onButton.removeStyle(ButtonType.SUCCESS);
			offButton.setType(ButtonType.DANGER);
		}
	}
}
