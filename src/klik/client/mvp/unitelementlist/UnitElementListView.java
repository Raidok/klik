package klik.client.mvp.unitelementlist;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UnitElementListView extends ViewImpl implements
UnitElementListPresenter.MyView {

	public interface Binder extends UiBinder<Widget, UnitElementListView> {
	}

	private final Widget widget;
	@UiField HTMLPanel contentPanel;

	@Inject
	public UnitElementListView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void addToSlot(Object slot, Widget content) {
		if (slot == UnitElementListPresenter.TYPE_UnitList) {
			if (content != null) {
				contentPanel.add(content);
			}
		} else {
			super.addToSlot(slot, content);
		}
	}

	@Override
	public void removeFromSlot(Object slot, Widget content) {
		if (slot == UnitElementListPresenter.TYPE_UnitList) {
			contentPanel.clear();

			if (content != null) {
				for (int i = 0; i < contentPanel.getWidgetCount(); i++) {
					if (content.equals(contentPanel.getWidget(i))) {
						contentPanel.getWidget(i).removeFromParent();
					}
				}
			}
		} else {
			super.removeFromSlot(slot, content);
		}
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == UnitElementListPresenter.TYPE_UnitList) {
			contentPanel.clear();

			if (content != null) {
				contentPanel.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}
}
