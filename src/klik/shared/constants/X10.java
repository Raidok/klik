package klik.shared.constants;

public class X10 {

	private X10() {
	}

	public enum Type {
		APPLIANCE, ONOFF_LIGHT, DIMMABLE_LIGHT
	}

	public enum State {
		ON, OFF, DIM;

		int value;

		public State setValue(int value) {
			this.value = value;
			return this;
		}

		public int getValue() {
			return value;
		}
	}

	public enum Function {
		ALL_UNITS_OFF,ALL_LIGHTS_ON,ON,OFF,DIM,BRIGHT,ALL_LIGHTS_OFF;

		int value;

		public void setValue(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
