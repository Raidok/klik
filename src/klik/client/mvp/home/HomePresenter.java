package klik.client.mvp.home;

import klik.client.NameTokens;
import klik.client.dispatch.CachingDispatchAsync;
import klik.client.mvp.LayoutPresenter;
import klik.client.mvp.setup.SetupWidgetPresenter;
import klik.shared.rpc.SendGreeting;
import klik.shared.rpc.SendGreetingResult;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy>
implements HomeUiHandlers {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_Content = new Type<RevealContentHandler<?>>();

	public interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
		void setHeroUnitVisible(boolean visible);
		void setHeroUnitMessage(String message);
		void addUnitRow(String code, String name, boolean status);
	}

	@ProxyStandard
	@NameToken(NameTokens.home)
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	private final CachingDispatchAsync dispatcher;
	private final AsyncProvider<SetupWidgetPresenter> setupDialogProvider;

	@Inject
	public HomePresenter(EventBus eventBus, MyView view, MyProxy proxy,
			CachingDispatchAsync dispatcher, AsyncProvider<SetupWidgetPresenter> setupDialogProvider) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.setupDialogProvider = setupDialogProvider;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		dispatcher.execute(new SendGreeting(), new AsyncCallback<SendGreetingResult>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
			}

			@Override
			public void onSuccess(SendGreetingResult result) {
				getView().setHeroUnitMessage(result.getMessage());
				getView().setHeroUnitVisible(true);
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.TYPE_SetContent, this);
	}

	@Override
	public void onSetupClick() {
		setupDialogProvider.get(new AsyncCallback<SetupWidgetPresenter>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("failz");
			}

			@Override
			public void onSuccess(SetupWidgetPresenter result) {
				RevealRootPopupContentEvent.fire(HomePresenter.this, result);
			}

		});
	}
}
