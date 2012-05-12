package klik.shared.event;

import klik.shared.model.UnitStatusDto;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class UnitStatusChange {

	@Order(1) String address;
	@Order(2) UnitStatusDto status;

}
