package klik.shared.model;

import klik.shared.constants.X10;

import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.Order;

@GenDto
public class UnitStatus {

	@Order(1) int id;
	@Order(3) String address;
	@Order(5) String name;
	@Order(2) X10.Type type;
	@Order(4) X10.State state;
	@Order(6) int value;

}
