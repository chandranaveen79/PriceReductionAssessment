package com.imlewis.model.productresponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Naveen Chandra 
 * ValueObject for ProductResponse Property difference
 *         has been ignored in JSON as its used for driving the sort
 *         functionality based on maximum price reduction difference
 */
@JsonSerialize
@JsonIgnoreProperties({ "difference" })
public class ProductResponse {
	private String productId;
	private String title;
	private List<ColorSwatchResponse> colorSwatches;
	private String nowPrice;
	private String priceLabel;
	private float difference;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ColorSwatchResponse> getColorSwatches() {
		return colorSwatches;
	}

	public void setColorSwatches(List<ColorSwatchResponse> colorSwatches) {
		this.colorSwatches = colorSwatches;
	}

	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getPriceLabel() {
		return priceLabel;
	}

	public void setPriceLabel(String priceLabel) {
		this.priceLabel = priceLabel;
	}

	public float getDifference() {
		return difference;
	}

	public void setDifference(float difference) {
		this.difference = difference;
	}
}