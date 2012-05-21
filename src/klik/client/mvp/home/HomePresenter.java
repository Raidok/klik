package klik.client.mvp.home;

import java.util.List;

import klik.client.MyCallback;
import klik.client.NameTokens;
import klik.client.dispatch.CachingDispatchAsync;
import klik.client.mvp.LayoutPresenter;
import klik.client.mvp.tabbar.TabBarPresenter;
import klik.client.mvp.unitbuttonbar.UnitsButtonBarPresenter;
import klik.client.mvp.unitelementlist.UnitElementListPresenter;
import klik.shared.event.SetupEvent;
import klik.shared.model.UnitStatusDto;
import klik.shared.rpc.RetrieveGreetingAction;
import klik.shared.rpc.RetrieveGreetingResult;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

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
	private final AsyncProvider<HomeUnitsProvider> homeUnitsProvider;

	@Inject
	public HomePresenter(EventBus eventBus, MyView view, MyProxy proxy,
			CachingDispatchAsync dispatcher, AsyncProvider<HomeUnitsProvider> homeUnitsProvider) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.homeUnitsProvider = homeUnitsProvider;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		dispatcher.execute(new RetrieveGreetingAction(), new MyCallback<RetrieveGreetingResult>(this) {
			@Override
			public void onSuccesss(RetrieveGreetingResult result) {
				handleGreetingResult(result);
			}
		});
	}

	void handleGreetingResult(final RetrieveGreetingResult result) {
		getView().setSetupBtnVisible(!result.isSetUp()); // hide setup button

		if (result.getMessage() != null) { // show message if available
			getView().setHeroUnitMessage(result.getMessage());
		} else { // otherwise hide herounit
			getView().setHeroUnitVisible(false);
		}

		if (result.getUnitList().size() > 0) {
			homeUnitsProvider.get(new MyCallback<HomeUnitsProvider>(this) {

				@Override
				public void onSuccesss(HomeUnitsProvider result2) {
					handleProvider(result.getUnitList(), result2);
				}
			});
		}
	}


	void handleProvider(List<UnitStatusDto> unitList, HomeUnitsProvider result) {
		addToSlot(TYPE_ButtonBar, (TabBarPresenter) result.get(HomeUnitsProvider.ID_TabBar).get());
		addToSlot(TYPE_ButtonBar, (UnitsButtonBarPresenter) result.get(HomeUnitsProvider.ID_ButtonBar).get());
		UnitElementListPresenter listPresenter = (UnitElementListPresenter) result.get(HomeUnitsProvider.ID_UnitList).get();
		listPresenter.refresh(unitList);
		setInSlot(HomePresenter.TYPE_Content, listPresenter);
		getView().setContentVisible(true);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.TYPE_SetContent, this);
	}

	@Override
	public void onSetupClick() {
		fireEvent(new SetupEvent(true));
	}
}
