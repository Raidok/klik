package klik.client.mvp.setup;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class SetupWidgetView extends PopupViewWithUiHandlers<SetupWidgetUiHandlers>
implements SetupWidgetPresenter.MyView {

	public interface Binder extends UiBinder<Widget, SetupWidgetView> {
	}

	private final Widget widget;
	@UiField Anchor close;
	@UiField Button save;
	@UiField ListBox comPortListBox;

	@Inject
	public SetupWidgetView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@UiHandler("close")
	public void onCloseClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().onClose();
		}
	}

	@UiHandler("save")
	public void onSaveClick(ClickEvent e) {
		if (getUiHandlers() != null) {
			getUiHandlers().onSave();
		}
	}

	@Override
	public void fillFields(LinkedHashMap<String, String> comPorts) {
		for (Map.Entry<String, String> e : comPorts.entrySet()) {
			comPortListBox.addItem(e.getKey(), e.getValue());
		}
	}

	@Override
	public String getSelectedPort() {
		return comPortListBox.getValue(comPortListBox.getSelectedIndex());
	}
}
