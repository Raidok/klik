package klik.shared.model;

import klik.shared.constants.X10;

import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.Order;

@GenDto
public class UnitEvent {

	@Order(1) String address; // TODO replace with id
	@Order(2) X10.Function function;
	@Order(3) int value;

}
