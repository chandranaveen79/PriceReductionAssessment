package com.imlewis.model.productrequest;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Naveen Chandra 
 * ValueObject for ProductGroupRequest Feed
 */
@JsonSerialize
public class ProductGroupRequest {
	List<ProductRequest> products;

	public List<ProductRequest> getProducts() {
		return products;
	}

	public void setProducts(List<ProductRequest> products) {
		this.products = products;
	}
}
