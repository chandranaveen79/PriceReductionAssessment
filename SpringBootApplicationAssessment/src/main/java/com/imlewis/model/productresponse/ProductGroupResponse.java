package com.imlewis.model.productresponse;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Naveen Chandra 
 * ValueObject for ProductGroupResponse
 */
@JsonSerialize
public class ProductGroupResponse {
	List<ProductResponse> products;
	
	public ProductGroupResponse(List<ProductResponse> products) {
		this.products = products;
	}

	public List<ProductResponse> getProducts() {
		return products;
	}

	public void setProducts(List<ProductResponse> products) {
		this.products = products;
	}
}
