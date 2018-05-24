package com.hsc.cat.enums;

public enum SdlcCategory {
	REQUIREMENT_GATHERING(1, "Requirement Gathering and Analysis"), DESIGN(2, "Design"), CODING(3,
			"Coding"), TESTING(4, "Testing");
	int value;
	String name;

	private SdlcCategory(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
