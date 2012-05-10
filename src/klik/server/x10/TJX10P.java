package klik.server.x10;

import klik.shared.constants.X10;
import klik.shared.constants.X10.Function;
import klik.shared.model.UnitEventDto;
import x10.Command;
import x10.Controller;

public class TJX10P {

	private static Controller cm;

	public TJX10P(String comPort) {
		/*try {
			cm = new CM11ASerialController(comPort);
			return;
		} catch (IOException e) {
			System.out.println("Creating controller failed!");
		}*/
	}

	public void addEvent(UnitEventDto ev) {
		cm.addCommand(createCommand(ev));
	}

	public static Command createCommand(UnitEventDto ev) {
		System.out.println("event arrived:"+ev.getAddress()+" "+ev.getFunction()+ev.getFunction().getValue());
		byte function = createFunction(ev.getFunction());

		if (ev.getFunction() == Function.BRIGHT ||
				ev.getFunction() == Function.DIM) {
			return new Command(ev.getAddress(), function, ev.getFunction().getValue());
		}
		return new Command(ev.getAddress(), function);
	}

	/**
	 * Translates function from one "language" to another
	 * @param function enum
	 * @return function byte suitable for sending to the network
	 */
	private static byte createFunction(X10.Function function) {
		switch (function) {
		case ON:
			return Command.ON;
		case OFF:
			return Command.OFF;
		case BRIGHT:
			return Command.BRIGHT;
		case DIM:
			return Command.DIM;
		case ALL_LIGHTS_OFF:
			return Command.ALL_LIGHTS_OFF;
		case ALL_LIGHTS_ON:
			return Command.ALL_LIGHTS_ON;
		case ALL_UNITS_OFF:
			return Command.ALL_UNITS_OFF;
		default:
			// this should never happen
			throw new RuntimeException("Illegal function!");
		}
	}
}
