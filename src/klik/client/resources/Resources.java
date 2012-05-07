package klik.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {

	@Source("style.css")
	@CssResource.NotStrict
	CssResource css();

}
