package klik.client.mvp.unitelement;

import com.gwtplatform.mvp.client.UiHandlers;

public interface UnitElementUiHandlers extends UiHandlers {
	void extend();
	void setOn(boolean isOn);
	void bright();
	void dim();
	void edit();
}
