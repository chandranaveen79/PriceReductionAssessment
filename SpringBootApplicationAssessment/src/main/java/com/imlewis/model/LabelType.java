package com.imlewis.model;

/**
 * @author Naveen Chandra 
 * Enum for LabelType
 */
public enum LabelType {
	SHOWWASNOW("ShowWasNow"), SHOWWASTHENNOW("ShowWasThenNow"), SHOWPERCDSCOUNT("ShowPercDscount");

	private String labelType;

	private LabelType(String labelType) {
		this.labelType = labelType;
	}

	@Override
	public String toString() {
		return labelType;
	}
}
