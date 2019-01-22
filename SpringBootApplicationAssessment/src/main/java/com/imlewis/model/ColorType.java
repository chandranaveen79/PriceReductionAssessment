package com.imlewis.model;

/**
 * @author Naveen Chandra 
 * Enum for ColorType
 */
public enum ColorType {
	RED("RED"), GREEN("GREEN"), BLUE("BLUE");

	private String colorType;

	private ColorType(String colorType) {
		this.colorType = colorType;
	}

	@Override
	public String toString() {
		return colorType;
	}
}
