package klik.client.mvp.unitelement;

import com.github.gwtbootstrap.client.ui.Button;
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
	@UiField HTMLPanel widgetPanel;
	@UiField Button onOffButton;
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
	public void setRow(String code, String name, boolean status) {
		nameLabel.setText(name);
		onOffButton.setType(status ? ButtonType.SUCCESS : ButtonType.DANGER);
	}

	@UiHandler("extendButton")
	void onExtendClick(ClickEvent e) {
		invisiblePanel.setVisible(!invisiblePanel.isVisible());
		extendButton.setIcon(invisiblePanel.isVisible() ? IconType.CHEVRON_UP : IconType.CHEVRON_DOWN);
	}
}
