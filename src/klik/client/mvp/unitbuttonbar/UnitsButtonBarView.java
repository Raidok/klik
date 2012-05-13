package klik.client.mvp.unitbuttonbar;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class UnitsButtonBarView extends ViewWithUiHandlers<UnitsButtonBarUiHandlers> implements
UnitsButtonBarPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, UnitsButtonBarView> {
	}

	@Inject
	public UnitsButtonBarView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
