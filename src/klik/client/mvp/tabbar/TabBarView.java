package klik.client.mvp.tabbar;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class TabBarView extends ViewImpl implements TabBarPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, TabBarView> {
	}

	@Inject
	public TabBarView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}