package klik.client.mvp.tabbar;

import com.github.gwtbootstrap.client.ui.Dropdown;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class TabBarView extends ViewImpl implements TabBarPresenter.MyView {

	public interface Binder extends UiBinder<Widget, TabBarView> {
	}

	private final Widget widget;
	@UiField Dropdown tabGroupSelect;

	@Inject
	public TabBarView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@UiHandler("tabGroupSelect")
	void onChange(ChangeEvent event) {
		tabGroupSelect.setText(tabGroupSelect.getLastSelectedNavLink().getText()+":");
	}


}
