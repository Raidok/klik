package klik.client.mvp.admin;

import klik.client.NameTokens;
import klik.client.mvp.LayoutPresenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class AdminPresenter extends
Presenter<AdminPresenter.MyView, AdminPresenter.MyProxy> {

	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.admin)
	public interface MyProxy extends ProxyPlace<AdminPresenter> {
	}

	@Inject
	public AdminPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.TYPE_SetContent, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
