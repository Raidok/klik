package klik.server;

import java.util.LinkedHashMap;
import java.util.List;
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
				X10.Type.ONOFF_LIGHT,
				"C9",
				X10.State.ON,
				"Wall");
		unitMap.put(unit.getAddress(), unit);
	}

	public static List<UnitStatusDto> getList() {
		return (List<UnitStatusDto>) unitMap.values();
	}

	public static UnitStatusDto getStatus(String address) {
		return unitMap.get(address);
	}
}
