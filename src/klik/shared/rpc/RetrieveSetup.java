package klik.shared.rpc;

import java.util.LinkedHashMap;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

@GenDispatch(isSecure=false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class RetrieveSetup {

	@Out(1) boolean running;
	@Out(2) LinkedHashMap<String, String> comPorts;
	@Out(3) String activePort;

}
