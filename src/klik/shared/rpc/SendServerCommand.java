package klik.shared.rpc;

import klik.shared.constants.ServerAction;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

@GenDispatch(isSecure=false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class SendServerCommand {

	@In(1) ServerAction action;
	@Out(1) boolean running;
	@Out(2) String message;

}
