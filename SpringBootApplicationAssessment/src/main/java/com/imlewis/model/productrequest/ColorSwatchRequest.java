package com.imlewis.model.productrequest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Naveen Chandra 
 * ValueObject for ColorSwatchRequest Feed
 */
@JsonSerialize
public class ColorSwatchRequest {
	private String basicColor;
	private String color;
	private String skuId;
	public String getBasicColor() {
		return basicColor;
	}
	public void setBasicColor(String basicColor) {
		this.basicColor = basicColor;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
}
