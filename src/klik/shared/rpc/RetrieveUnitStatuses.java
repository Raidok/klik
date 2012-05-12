package klik.shared.rpc;

import java.util.ArrayList;

import klik.shared.model.UnitStatusDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

@GenDispatch(isSecure=false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class RetrieveUnitStatuses {

	@Out(1) ArrayList<UnitStatusDto> unitList;

}
