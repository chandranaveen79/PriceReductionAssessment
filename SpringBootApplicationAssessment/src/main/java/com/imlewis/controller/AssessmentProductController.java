package com.imlewis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.imlewis.model.productrequest.ProductGroupRequest;
import com.imlewis.model.productresponse.ProductGroupResponse;
import com.imlewis.service.AssessmentService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen Chandra 
 * Controller Class to build the product list
 */
@RestController
@Slf4j
public class AssessmentProductController {

	@Value("${listOfProductsURL}")
	private String productsURL;

	@Autowired
	private AssessmentService assessmentService;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * Method to provide the price reduction product list, the call will be delegated to service class
	 */
	@GetMapping(value = "/priceReductionList")
	public @ResponseBody ProductGroupResponse priceReductionList(
			@RequestParam(name = "labelType", required = false, defaultValue = "ShowWasNow") String labelType) {
		log.info("Label Type passed {}", labelType);
		log.info("Product URL {}", productsURL);
		ProductGroupRequest productGroup = new RestTemplate().getForObject(productsURL, ProductGroupRequest.class);
		return assessmentService.buildProductsFromFeed(productGroup, labelType);
	}
}
