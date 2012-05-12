package klik.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import klik.shared.constants.X10;
import klik.shared.model.UnitStatusDto;

public class TempUnitHolder {

	private static Map<String, UnitStatusDto> unitMap;

	static {
		unitMap = new LinkedHashMap<String, UnitStatusDto>();
		UnitStatusDto unit = new UnitStatusDto(
				X10.Type.ONOFF_LIGHT,
				"C9",
				X10.State.ON,
				"Desk");
		unitMap.put(unit.getAddress(), unit);
		unit = new UnitStatusDto(
				X10.Type.DIMMABLE_LIGHT,
				"A1",
				X10.State.ON.setValue(80),
				"Wall");
		unitMap.put(unit.getAddress(), unit);
	}

	public static ArrayList<UnitStatusDto> getList() {
		return new ArrayList<UnitStatusDto>(unitMap.values());
	}

	public static UnitStatusDto getStatus(String address) {
		return unitMap.get(address);
	}
}
