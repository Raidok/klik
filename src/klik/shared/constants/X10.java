package klik.shared.constants;

public class X10 {

	private X10() {
	}

	public enum Type {
		APPLIANCE, ONOFF_LIGHT, DIMMABLE_LIGHT
	}

	public enum HouseCode {
		A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P
	}

	public enum UnitCode {
		U1,U2,U3,U4,U5,U6,U7,U8,U9,U10,U11,U12,U13,U14,U15,U16
	}

	public enum State {
		ON, OFF, DIM;

		int value;

		public void setValue(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum Dimming {
		DIM, BRIGHT;

		int value;

		public void setValue(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
