package klik.client.mvp.unitelementlist;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import klik.client.MyCallback;
import klik.client.dispatch.CachingDispatchAsync;
import klik.client.mvp.unitelement.UnitElementPresenter;
import klik.shared.event.RefreshEvent;
import klik.shared.event.UnitStatusChangeEvent;
import klik.shared.event.UnitStatusChangeEvent.UnitStatusChangeHandler;
import klik.shared.model.UnitStatusDto;
import klik.shared.rpc.RetrieveUnitStatusesAction;
import klik.shared.rpc.RetrieveUnitStatusesResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class UnitElementListPresenter extends
PresenterWidget<UnitElementListPresenter.MyView> {

	public static final Type<RevealContentHandler<?>> TYPE_UnitList = new Type<RevealContentHandler<?>>();

	public interface MyView extends View {
	}

	private final CachingDispatchAsync dispatcher;
	private final Provider<UnitElementPresenter> unitElementProvider;
	private Map<String, UnitStatusDto> unitsMap;

	@Inject
	public UnitElementListPresenter(final EventBus eventBus, final MyView view,
			CachingDispatchAsync dispatcher, Provider<UnitElementPresenter> unitElementProvider) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		this.unitElementProvider = unitElementProvider;
		unitsMap = new LinkedHashMap<String, UnitStatusDto>();
	}

	@Override
	protected void onBind() {
		super.onBind();
		// listens to refresh button presses
		addRegisteredHandler(RefreshEvent.getType(), new RefreshEvent.RefreshHandler() {
			@Override
			public void onRefresh(RefreshEvent event) {
				retrieveAndRefresh();
			}
		});
		// listens to units' changes
		addRegisteredHandler(UnitStatusChangeEvent.getType(), new UnitStatusChangeHandler() {
			@Override
			public void onUnitStatusChange(UnitStatusChangeEvent event) {
				if (unitsMap.containsKey(event.getAddress())) {
					unitsMap.put(event.getAddress(), event.getStatus());
				} else {
					throw new RuntimeException("This should never happen!");
				}
			}
		});
	}

	private void retrieveAndRefresh() {
		dispatcher.execute(new RetrieveUnitStatusesAction(), new MyCallback<RetrieveUnitStatusesResult>(this) {
			@Override
			public void onSuccesss(RetrieveUnitStatusesResult result) {
				refresh(result.getUnitList());
			}
		});
	}

	public void refresh(List<UnitStatusDto> unitList) {
		for (UnitStatusDto unitStatus : unitList) {
			String address = unitStatus.getAddress();
			if (unitsMap.containsKey(address)) {
				if (!unitStatus.equals(unitsMap.get(address))) {
					GWT.log("update "+address);
					fireEvent(new UnitStatusChangeEvent(address, unitStatus));
					unitsMap.put(address, unitStatus); // override with a copy of the new status
				} else {
					GWT.log("same "+address);
				}
			} else {
				UnitElementPresenter unitElement = unitElementProvider.get();
				unitElement.setStatus(unitStatus);
				addToSlot(UnitElementListPresenter.TYPE_UnitList, unitElement);
				unitsMap.put(address, unitStatus);
				GWT.log("added "+address);
			}

		}
	}
}
