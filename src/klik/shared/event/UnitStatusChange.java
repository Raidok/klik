package klik.shared.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class UnitStatusChange {

	@Order(1) String code;

}
