package com.imlewis.service;

import com.imlewis.model.productrequest.ProductGroupRequest;
import com.imlewis.model.productresponse.ProductGroupResponse;

/**
 * @author Naveen Chandra 
 * Interface to build the product list
 */
public interface AssessmentService {
	ProductGroupResponse buildProductsFromFeed(ProductGroupRequest productGroup, String labelType);
}
