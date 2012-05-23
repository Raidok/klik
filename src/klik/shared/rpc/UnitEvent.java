package klik.shared.rpc;

import java.util.ArrayList;

import klik.shared.model.UnitEventDto;
import klik.shared.model.UnitStatusDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

@GenDispatch(isSecure=false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class UnitEvent {

	@In(1) UnitEventDto event;
	@Out(1) ArrayList<UnitStatusDto> statusList;

}
