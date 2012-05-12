package klik.client.mvp;

import klik.client.dispatch.CachingDispatchAsync;
import klik.shared.event.AlertEvent;
import klik.shared.event.AlertEventHandler;
import klik.shared.event.LoadingEvent;
import klik.shared.event.RefreshEvent;

import com.allen_sauer.gwt.log.client.Log;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.AsyncCallFailEvent;
import com.gwtplatform.mvp.client.proxy.AsyncCallFailHandler;
import com.gwtplatform.mvp.client.proxy.AsyncCallStartEvent;
import com.gwtplatform.mvp.client.proxy.AsyncCallStartHandler;
import com.gwtplatform.mvp.client.proxy.AsyncCallSucceedEvent;
import com.gwtplatform.mvp.client.proxy.AsyncCallSucceedHandler;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class LayoutPresenter extends Presenter<LayoutPresenter.MyView, LayoutPresenter.MyProxy>
implements LayoutUiHandlers {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetContent = new Type<RevealContentHandler<?>>();

	public interface MyView extends View, HasUiHandlers<LayoutUiHandlers> {
		void showLoading(boolean visible);
		void clearAlerts();
		void showAlert(AlertType type, String message);
	}

	@ProxyStandard
	public interface MyProxy extends Proxy<LayoutPresenter> {
	}

	private final CachingDispatchAsync dispatcher;

	@Inject
	public LayoutPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, CachingDispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onBind() {
		super.onBind();

		// displays alerts posted on the EventBus
		addRegisteredHandler(AlertEvent.TYPE, new AlertEventHandler() {
			@Override
			public void onAlert(AlertEvent event) {
				getView().clearAlerts();
				getView().showAlert(event.getAlertType(), event.getMessage());
			}
		});

		// displays loading message/image while gwtp makes a internal server call
		addRegisteredHandler(AsyncCallStartEvent.getType(), new AsyncCallStartHandler() {
			@Override
			public void onAsyncCallStart(AsyncCallStartEvent asyncCallStartEvent) {
				getView().showLoading(true);
				Log.debug("AsyncCallStartEvent!");
			}
		});

		// hides loading message/image when gwtp's server call succeeds
		addRegisteredHandler(AsyncCallSucceedEvent.getType(), new AsyncCallSucceedHandler() {
			@Override
			public void onAsyncCallSucceed(AsyncCallSucceedEvent asyncCallSucceedEvent) {
				getView().showLoading(false);
				Log.debug("AsyncCallSucceedEvent!");
			}
		});

		// hides loading message/image and posts an error alert when gwtp's server call fails
		addRegisteredHandler(AsyncCallFailEvent.getType(), new AsyncCallFailHandler() {
			@Override
			public void onAsyncCallFail(AsyncCallFailEvent asyncCallFailEvent) {
				getView().showLoading(false);
				fireEvent(new AlertEvent(AlertType.ERROR, "No internet connection or trouble with server!"));
			}
		});

		addRegisteredHandler(LoadingEvent.getType(), new LoadingEvent.LoadingHandler() {
			@Override
			public void onLoading(LoadingEvent event) {
				getView().showLoading(event.isLoading());
			}
		});
	}

	@Override
	public void onRefresh() {
		fireEvent(new RefreshEvent());
	}
}
