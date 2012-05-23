package klik.server.data;

import klik.shared.constants.X10;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "x10_units")
public class X10Unit {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(index = true)
	private String address;

	@DatabaseField(index = true)
	private char houseCode;

	@DatabaseField
	private X10.Type type;

	@DatabaseField
	private X10.State state;

	@DatabaseField
	private String name;

	@DatabaseField
	private int value;

	X10Unit() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		houseCode = address.toCharArray()[0];
		this.address = address;
	}

	public char getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(char houseCode) {
		char[] c = address.toCharArray();
		c[0] = houseCode;
		address = c.toString();
		this.houseCode = houseCode;
	}

	public X10.Type getType() {
		return type;
	}

	public void setType(X10.Type type) {
		this.type = type;
	}

	public X10.State getState() {
		return state;
	}

	public void setState(X10.State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}
}
