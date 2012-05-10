package klik.shared.model;

import klik.shared.constants.X10;

import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.Order;

@GenDto
public class UnitStatus {

	@Order(1) X10.Type type;
	@Order(2) String address;
	@Order(3) X10.State state;
	@Order(4) String name;

}
