package klik.server.x10;

import klik.shared.constants.X10;
import klik.shared.constants.X10.Function;
import klik.shared.model.UnitEventDto;
import x10.Command;

/**
 * Utility class to connect to the Java X10 Project
 * @author raido
 */
public class TJX10P {

	private TJX10P() {
	}

	/**
	 * Translates UnitEventDto to the Java X10 Project Command object
	 * @param ev event
	 * @return Command
	 */
	public static Command createCommand(UnitEventDto ev) {
		System.out.println("event arrived:"+ev.getAddress()+" "+ev.getFunction()+" "+ev.getValue());
		byte function = createFunction(ev.getFunction());

		if (ev.getFunction() == Function.BRIGHT ||
				ev.getFunction() == Function.DIM) {
			return new Command(ev.getAddress(), function, ev.getValue());
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
			throw new RuntimeException("Illegal function " + function + "!");
		}
	}
}
