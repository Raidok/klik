package klik.client.mvp.home;

import klik.client.mvp.tabbar.TabBarPresenter;
import klik.client.mvp.unitbuttonbar.UnitsButtonBarPresenter;
import klik.client.mvp.unitelementlist.UnitElementListPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.ProviderBundle;

public class HomeUnitsProvider extends ProviderBundle {

	public static final int ID_TabBar = 0;
	public static final int ID_ButtonBar = 1;
	public static final int ID_UnitList = 2;
	public static final int BUNDLE_SIZE = 3;

	@Inject
	public HomeUnitsProvider(Provider<TabBarPresenter> tabBarPresenter,
			Provider<UnitsButtonBarPresenter> buttonBarProvider,
			Provider<UnitElementListPresenter> unitListProvider) {
		super(BUNDLE_SIZE);
		providers[ID_TabBar] = tabBarPresenter;
		providers[ID_ButtonBar] = buttonBarProvider;
		providers[ID_UnitList] = unitListProvider;
	}

}
