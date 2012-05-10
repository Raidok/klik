package klik.shared.rpc;

import klik.shared.model.UnitEventDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

@GenDispatch(isSecure=false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class UnitEvent {

	@In(1) UnitEventDto event;

}
