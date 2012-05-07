package klik.client.mvp.home;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Hero;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Well;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements HomePresenter.MyView {

	public interface Binder extends UiBinder<Widget, HomeView> {
	}

	public final Widget widget;
	@UiField Hero heroUnit;
	@UiField Paragraph message;
	@UiField Button button;
	@UiField Well well;

	@Inject
	public HomeView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setHeroUnitVisible(boolean visible) {
		heroUnit.setVisible(visible);
	}

	@Override
	public void setHeroUnitMessage(String message) {
		this.message.setText(message);
	}

	@UiHandler("button")
	public void handleSetupClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().onSetupClick();
		}
	}
}
