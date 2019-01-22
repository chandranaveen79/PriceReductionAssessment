package com.imlewis.model.productrequest;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Naveen Chandra 
 * ValueObject for ProductRequest Feed
 */
@JsonSerialize
public class ProductRequest {
	private String productId;
	private String title;
	private PriceRequest price;
	private List<ColorSwatchRequest> colorSwatches;
	
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
	public PriceRequest getPrice() {
		return price;
	}
	public void setPrice(PriceRequest price) {
		this.price = price;
	}
	public List<ColorSwatchRequest> getColorSwatches() {
		return colorSwatches;
	}
	public void setColorSwatches(List<ColorSwatchRequest> colorSwatches) {
		this.colorSwatches = colorSwatches;
	}
	
}