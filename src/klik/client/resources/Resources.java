package klik.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

	@Source("style.css")
	@CssResource.NotStrict
	CssResource css();

	@Source("loading1.gif")
	ImageResource loading();
}
