package klik.client.gin;

import klik.client.mvp.LayoutPresenter;
import klik.client.mvp.admin.AdminPresenter;
import klik.client.mvp.error.ErrorPresenter;
import klik.client.mvp.home.HomePresenter;
import klik.client.mvp.setup.SetupWidgetPresenter;
import klik.client.mvp.unitelement.UnitElementPresenter;
import klik.client.mvp.unitelementlist.UnitElementListPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.resources.client.CssResource;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({ MyClientModule.class, DispatchAsyncModule.class })
public interface MyGinjector extends Ginjector {
	EventBus getEventBus();
	PlaceManager getPlaceManager();
	CssResource getCss();
	Provider<LayoutPresenter> getLayoutPresenter();
	Provider<HomePresenter> getHomePresenter();
	Provider<AsyncProvider<SetupWidgetPresenter>> getSetupDialog();
	Provider<Provider<UnitElementPresenter>> getUnitElement();
	AsyncProvider<AdminPresenter> getAdminPresenter();
	AsyncProvider<ErrorPresenter> getErrorPresenter();
	AsyncProvider<SetupWidgetPresenter> getSetupPresenter();
	AsyncProvider<UnitElementListPresenter> getUnitListPresenter();
}
