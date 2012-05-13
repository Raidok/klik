package klik.client.mvp.home;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Hero;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
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
	@UiField HTMLPanel buttonBar;
	@UiField HTMLPanel contentPanel;

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

	@Override
	public void addToSlot(Object slot, Widget content) {
		if (slot == HomePresenter.TYPE_Content) {
			if (content != null) {
				contentPanel.add(content);
			}
		} else {
			super.addToSlot(slot, content);
		}
	}

	@Override
	public void removeFromSlot(Object slot, Widget content) {
		if (slot == HomePresenter.TYPE_Content) {
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
		if (slot == HomePresenter.TYPE_Content) {
			contentPanel.clear();
			if (content != null) {
				contentPanel.add(content);
			}
		} else if (slot == HomePresenter.TYPE_ButtonBar) {
			buttonBar.clear();
			if (content != null) {
				buttonBar.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void setContentVisible(boolean visible) {
		contentPanel.setVisible(visible);
	}
}
