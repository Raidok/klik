package klik.client.gin;

import klik.client.MyPlaceManager;
import klik.client.dispatch.CachingDispatchAsync;
import klik.client.mvp.LayoutPresenter;
import klik.client.mvp.LayoutView;
import klik.client.mvp.addedit.AddEditWidgetPresenter;
import klik.client.mvp.addedit.AddEditWidgetView;
import klik.client.mvp.admin.AdminPresenter;
import klik.client.mvp.admin.AdminView;
import klik.client.mvp.error.ErrorPresenter;
import klik.client.mvp.error.ErrorView;
import klik.client.mvp.home.HomePresenter;
import klik.client.mvp.home.HomeView;
import klik.client.mvp.setup.SetupWidgetPresenter;
import klik.client.mvp.setup.SetupWidgetView;
import klik.client.mvp.tabbar.TabBarPresenter;
import klik.client.mvp.tabbar.TabBarView;
import klik.client.mvp.unitbuttonbar.UnitsButtonBarPresenter;
import klik.client.mvp.unitbuttonbar.UnitsButtonBarView;
import klik.client.mvp.unitelement.UnitElementPresenter;
import klik.client.mvp.unitelement.UnitElementView;
import klik.client.mvp.unitelementlist.UnitElementListPresenter;
import klik.client.mvp.unitelementlist.UnitElementListView;
import klik.client.resources.Resources;

import com.google.gwt.resources.client.CssResource;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class MyClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(MyPlaceManager.class));
		bind(CachingDispatchAsync.class).in(Singleton.class);

		bindPresenter(LayoutPresenter.class, LayoutPresenter.MyView.class,
				LayoutView.class, LayoutPresenter.MyProxy.class);
		bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
				HomeView.class, HomePresenter.MyProxy.class);
		bindPresenter(AdminPresenter.class, AdminPresenter.MyView.class,
				AdminView.class, AdminPresenter.MyProxy.class);
		bindPresenter(ErrorPresenter.class, ErrorPresenter.MyView.class,
				ErrorView.class, ErrorPresenter.MyProxy.class);

		bindSingletonPresenterWidget(SetupWidgetPresenter.class, SetupWidgetPresenter.MyView.class,
				SetupWidgetView.class);
		bindSingletonPresenterWidget(TabBarPresenter.class,
				TabBarPresenter.MyView.class, TabBarView.class);
		bindSingletonPresenterWidget(UnitsButtonBarPresenter.class,
				UnitsButtonBarPresenter.MyView.class, UnitsButtonBarView.class);
		bindSingletonPresenterWidget(UnitElementListPresenter.class,
				UnitElementListPresenter.MyView.class,
				UnitElementListView.class);
		bindPresenterWidget(UnitElementPresenter.class,
				UnitElementPresenter.MyView.class, UnitElementView.class);
		bindSingletonPresenterWidget(AddEditWidgetPresenter.class,
				AddEditWidgetPresenter.MyView.class, AddEditWidgetView.class);
	}

	@Inject
	@Provides CssResource getCss(final Resources resources) {
		CssResource style = resources.css();
		style.ensureInjected();
		return style;
	}

}
