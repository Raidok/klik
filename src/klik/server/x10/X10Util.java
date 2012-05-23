package klik.server.x10;

import java.util.ArrayList;
import java.util.List;

import klik.server.data.X10Unit;
import klik.shared.model.UnitStatusDto;

public class X10Util {

	public static ArrayList<UnitStatusDto> createDto(List<X10Unit> unitList) {
		ArrayList<UnitStatusDto> list = new ArrayList<UnitStatusDto>(unitList.size());
		for (X10Unit unit : unitList) {
			list.add(new UnitStatusDto(unit.getId(),
					unit.getType(),
					unit.getAddress(),
					unit.getState(),
					unit.getName(),
					unit.getValue()));
		}
		return list;
	}
}
