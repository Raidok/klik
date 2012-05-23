package klik.server.x10;

import java.util.ArrayList;
import java.util.List;

import klik.server.Process;
import klik.server.data.DataManager;
import klik.server.data.X10Unit;
import klik.shared.constants.X10.Function;
import klik.shared.constants.X10.State;
import klik.shared.model.UnitEventDto;
import klik.shared.model.UnitStatusDto;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;

public class X10UnitEventHandler {

	@Inject
	private static Log logger;

	public static ArrayList<UnitStatusDto> handleEvent(UnitEventDto event) {
		int value = Math.abs(event.getValue());
		logger.info("send event "+event.getAddress()+" "+event.getFunction()+(value != 0 ? " "+value : ""));
		Process.sendCommand(event.getFunction(), event.getAddress(), value);
		return handleEvent(event.getFunction(), event.getAddress(), event.getValue(), true);
	}

	/**
	 * Also used by TJX10P UnitListener.
	 * @param function
	 * @param address
	 * @param value
	 * @return
	 */
	public static ArrayList<UnitStatusDto> handleEvent(Function function, String address, int value, boolean relative) {
		logger.info("handleEvent "+address+" "+function+(value != 0 ? " "+value : ""));
		List<X10Unit> list = null;
		switch (function) {
		case ALL_LIGHTS_OFF:
		case ALL_LIGHTS_ON:
		case ALL_UNITS_OFF:
			list = DataManager.getUnitsWithHouseCode(address.toCharArray()[0]);
			break;
		default:
			list = DataManager.getUnitsWithAddress(address);
		}
		ArrayList<UnitStatusDto> returnList =
				new ArrayList<UnitStatusDto>(list == null ? 0 : list.size());
		if (list != null) {
			for (X10Unit unit : list) {
				unit = handleFunction(function, unit, value, relative);
				DataManager.updateUnit(unit);
				returnList.add(new UnitStatusDto(unit.getId(),
						unit.getType(),
						unit.getAddress(),
						unit.getState(),
						unit.getName(),
						unit.getValue()));
			}
		}
		logger.debug("list size "+returnList.size());
		return returnList;
	}

	private static X10Unit handleFunction(Function function, X10Unit unit, int value, boolean relative) {
		State state = null;
		switch (function) {
		case ON:
			if (!unit.getState().equals(State.DIM)) {
				state = State.ON;
				value = 100;
			}
			break;
		case BRIGHT:
			if (unit.getValue() == 0) {
				value = 100;
				state = State.ON;
				break;
			}
		case DIM:
			state = State.DIM;
			if (relative) {
				value = unit.getValue() + value;
			}
			if (value >= 0) {
				if (value >= 100) {
					value = 100;
					state = State.ON;
				}
				break;
			} else {
				state = State.OFF;
				value = 0;
			}
		case OFF:
		default:
			state = State.OFF;
			value = 0;
		}
		unit.setState(state);
		unit.setValue(value);
		return unit;
	}
}
