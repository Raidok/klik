package klik.client.mvp.unitelement;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UnitElementView extends ViewImpl implements
UnitElementPresenter.MyView {

	private final Widget widget;
	@UiField Label nameLabel;
	@UiField Button onOffButton;

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
}
