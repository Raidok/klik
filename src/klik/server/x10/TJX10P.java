package klik.server.x10;

import klik.shared.constants.X10;
import klik.shared.constants.X10.Function;
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
	public static Command createCommand(Function function, String address, int value) {
		byte func = createFunction(function);
		if (function == Function.BRIGHT ||
				function == Function.DIM) {
			return new Command(address, func, value);
		}
		return new Command(address, func);
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
