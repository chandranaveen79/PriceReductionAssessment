package com.imlewis.model.productresponse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Naveen Chandra 
 * ValueObject for ColorSwatchResponse
 */
@JsonSerialize
public class ColorSwatchResponse {
	private String color;
	private String rgbColor;
	private String skuid;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRgbColor() {
		return rgbColor;
	}

	public void setRgbColor(String rgbColor) {
		this.rgbColor = rgbColor;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
}
