package klik.shared.model;

import klik.shared.constants.X10;

import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.Order;

@GenDto
public class UnitEvent {

	@Order(1) X10.HouseCode houseCode;
	@Order(2) X10.UnitCode unitCode;
	@Order(3) X10.Dimming state;

}
