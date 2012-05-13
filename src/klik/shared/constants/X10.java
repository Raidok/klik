package klik.shared.constants;

public class X10 {

	private X10() {
	}

	public enum Type {
		APPLIANCE, ONOFF_LIGHT, DIMMABLE_LIGHT
	}

	public enum State {
		ON, OFF, DIM;
	}

	public enum Function {
		ALL_UNITS_OFF,ALL_LIGHTS_ON,ON,OFF,DIM,BRIGHT,ALL_LIGHTS_OFF; // TODO soft-off
	}
}
