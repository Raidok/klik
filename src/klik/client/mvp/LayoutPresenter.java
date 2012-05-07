package klik.client.mvp;

import klik.shared.event.AlertEvent;
import klik.shared.event.AlertEventHandler;

import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class LayoutPresenter extends Presenter<LayoutPresenter.MyView, LayoutPresenter.MyProxy> {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetContent = new Type<RevealContentHandler<?>>();

	public interface MyView extends View {
		void clearAlerts();
		void showAlert(AlertType type, String message);
	}

	@ProxyStandard
	public interface MyProxy extends Proxy<LayoutPresenter> {
	}

	@Inject
	public LayoutPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onBind() {
		super.onBind();
		getEventBus().addHandler(AlertEvent.TYPE, new AlertEventHandler() {

			@Override
			public void onAlert(AlertEvent event) {
				getView().clearAlerts();
				getView().showAlert(event.getAlertType(), event.getMessage());
			}
		});
	}
}
