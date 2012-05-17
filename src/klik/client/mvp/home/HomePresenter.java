package klik.client.mvp.home;

import klik.client.MyCallback;
import klik.client.NameTokens;
import klik.client.dispatch.CachingDispatchAsync;
import klik.client.mvp.LayoutPresenter;
import klik.client.mvp.setup.SetupWidgetPresenter;
import klik.client.mvp.unitbuttonbar.UnitsButtonBarPresenter;
import klik.client.mvp.unitelementlist.UnitElementListPresenter;
import klik.shared.rpc.RetrieveGreetingAction;
import klik.shared.rpc.RetrieveGreetingResult;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy>
implements HomeUiHandlers {

	public static final Type<RevealContentHandler<?>> TYPE_Content = new Type<RevealContentHandler<?>>();
	public static final Type<RevealContentHandler<?>> TYPE_ButtonBar = new Type<RevealContentHandler<?>>();

	public interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
		void setHeroUnitVisible(boolean visible);
		void setHeroUnitMessage(String message);
		void setSetupBtnVisible(boolean visible);
		void setContentVisible(boolean visible);
	}

	@ProxyStandard
	@NameToken(NameTokens.home)
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	private final CachingDispatchAsync dispatcher;
	private final Provider<UnitsButtonBarPresenter> buttonBarProvider;
	private final AsyncProvider<SetupWidgetPresenter> setupDialogProvider;
	private final AsyncProvider<UnitElementListPresenter> unitListProvider;

	@Inject
	public HomePresenter(EventBus eventBus, MyView view, MyProxy proxy,
			CachingDispatchAsync dispatcher, AsyncProvider<SetupWidgetPresenter> setupDialogProvider,
			AsyncProvider<UnitElementListPresenter> unitListProvider,
			Provider<UnitsButtonBarPresenter> buttonBarProvider) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.setupDialogProvider = setupDialogProvider;
		this.unitListProvider = unitListProvider;
		this.buttonBarProvider = buttonBarProvider;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		dispatcher.execute(new RetrieveGreetingAction(), new MyCallback<RetrieveGreetingResult>(this) {
			@Override
			public void onSuccesss(final RetrieveGreetingResult result) {
				getView().setSetupBtnVisible(!result.isSetUp());
				if (result.getMessage() != null) {
					getView().setHeroUnitMessage(result.getMessage());
				} else {
					getView().setHeroUnitVisible(false);
				}
				setInSlot(HomePresenter.TYPE_ButtonBar, buttonBarProvider.get());

				if (result.getUnitList().size() > 0) {
					unitListProvider.get(new MyCallback<UnitElementListPresenter>(HomePresenter.this) {
						@Override
						public void onSuccesss(UnitElementListPresenter result2) {
							result2.refresh(result.getUnitList());
							setInSlot(HomePresenter.TYPE_Content, result2);
							getView().setContentVisible(true);
						}
					});
				}
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.TYPE_SetContent, this);
	}

	@Override
	public void onSetupClick() {
		setupDialogProvider.get(new MyCallback<SetupWidgetPresenter>(this) {

			@Override
			public void onSuccesss(SetupWidgetPresenter result) {
				RevealRootPopupContentEvent.fire(HomePresenter.this, result);
			}

		});
	}
}
