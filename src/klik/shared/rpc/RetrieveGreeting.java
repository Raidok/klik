package klik.shared.rpc;

import java.util.ArrayList;

import klik.shared.model.UnitDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

@GenDispatch(isSecure=false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class RetrieveGreeting {

	@Out(1) String message;
	@Out(2) boolean isSetUp;
	@Out(3) ArrayList<UnitDto> unitList;
}
