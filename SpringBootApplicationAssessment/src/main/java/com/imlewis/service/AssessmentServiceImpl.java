package com.imlewis.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.beryx.awt.color.ColorFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.imlewis.model.ColorType;
import com.imlewis.model.LabelType;
import com.imlewis.model.productrequest.ColorSwatchRequest;
import com.imlewis.model.productrequest.PriceRequest;
import com.imlewis.model.productrequest.ProductGroupRequest;
import com.imlewis.model.productrequest.ProductRequest;
import com.imlewis.model.productresponse.ColorSwatchResponse;
import com.imlewis.model.productresponse.ProductGroupResponse;
import com.imlewis.model.productresponse.ProductResponse;

/**
 * @author Naveen Chandra Class which has been delegated from the controller to
 *         build the product list
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {

	public static final String GBP_CURRENCY = "£";
	public static final String NOW_PRICE_DELIMITER = "to";
	public static final String TWO_DECIMAL_POINTS = "%.2f";
	public static final String NO_DECIMAL_POINTS = "%.0f";
	public static final String COLOR_APPENDER = "#";
	public static final String HEX_COLOR_APPENDER = "0";
	public static final String NOT_A_VALID_COLOR = "N/A";

	/**
	 * Method to build the product list which are qualified for price reduction as
	 * per the usecase
	 */
	public ProductGroupResponse buildProductsFromFeed(ProductGroupRequest productGroup, String labelType) {
		List<ProductRequest> products = productGroup.getProducts();
		return new ProductGroupResponse(buildProductsFromFeed(products, labelType));
	}

	/**
	 * Method to iterate the products from the feed and build the filtered price
	 * reduced product list
	 */
	private List<ProductResponse> buildProductsFromFeed(List<ProductRequest> products, String labelType) {
		List<ProductResponse> productReponseList = new ArrayList<ProductResponse>();
		for (ProductRequest productRequest : products) {
			filterForEligibleProducts(productReponseList, productRequest, labelType);
		}
		sortProductResponseList(productReponseList);
		return productReponseList;
	}

	/**
	 * Method to filter the eligible products from the feed and consider only those
	 * products which are qualified for price reduction
	 * <ul>
	 * <li>Set up the product id, title, nowPrice</li>
	 * <li>ColorSwatch</li>
	 * <li>PriceLabel</li>
	 * </ul>
	 */
	private void filterForEligibleProducts(List<ProductResponse> productReponseList, ProductRequest productRequest,
			String labelType) {
		if (checkIfItsAValidProduct(productRequest)) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setProductId(productRequest.getProductId());
			productResponse.setTitle(productRequest.getTitle());
			productResponse.setNowPrice(GBP_CURRENCY + getNowPrice(productRequest.getPrice().getNow()));
			buildColorSwatch(productRequest, productResponse);
			buildPriceLabel(productRequest.getPrice(), productResponse, labelType);
			productResponse.setDifference(getFloatValue(productRequest.getPrice().getWas())
					- getFloatValue(filteredNowPrice(productRequest.getPrice().getNow())));
			productReponseList.add(productResponse);
		}
	}

	/**
	 * Method to sort the product list based on JSON ignored property - "difference"
	 * to sort in higher price reduction valued entities on the top of the list
	 * Multiplication with -1 to get the reverse order
	 */
	private void sortProductResponseList(List<ProductResponse> productReponseList) {
		Collections.sort(productReponseList, new Comparator<ProductResponse>() {
			public int compare(ProductResponse productResponse1, ProductResponse productResponse2) {
				return Float.compare(productResponse1.getDifference(), productResponse2.getDifference()) * (-1);
			}
		});
	}

	/**
	 * Method to boolean check if the product is qualified by checking if "Was"
	 * price is available and price reduction is more than zero
	 */
	private boolean checkIfItsAValidProduct(ProductRequest productRequest) {
		return StringUtils.isNotEmpty(productRequest.getPrice().getWas())
				&& getFloatValue(filteredNowPrice(productRequest.getPrice().getNow())) < getFloatValue(
						productRequest.getPrice().getWas());
	}

	/**
	 * Method to extract the formatted float value as stated in the usecase
	 */
	private String getNowPrice(JsonNode nowRequest) {
		return getFormattedPrice(getFloatValue(filteredNowPrice(nowRequest)));
	}

	/**
	 * Method to extract the float value from String value
	 */
	private float getFloatValue(String value) {
		return StringUtils.isNotEmpty(value) ? Float.parseFloat(value) : 0.0f;
	}

	/**
	 * Method to extract the plain value from JSON node as "price" is a JSONNode
	 */
	private String filteredNowPrice(JsonNode nowRequest) {
		String nowPriceString = StringUtils.isEmpty(nowRequest.asText()) ? nowRequest.toString() : nowRequest.asText();

		if (nowPriceString.contains(NOW_PRICE_DELIMITER)) {
			nowPriceString = nowRequest.get(NOW_PRICE_DELIMITER).asText();
		}
		return nowPriceString;
	}

	/**
	 * Method to get the price in 2 decimal points if value is less than 10 or
	 * without decimal otherwise
	 */
	private String getFormattedPrice(Float price) {
		return price < 10 ? String.format(TWO_DECIMAL_POINTS, price) : String.format(NO_DECIMAL_POINTS, price);
	}

	/**
	 * Method to build the colorswatch element from the feed
	 */
	private void buildColorSwatch(ProductRequest productRequest, ProductResponse productResponse) {
		List<ColorSwatchRequest> colorSwatchesFromFeed = productRequest.getColorSwatches();
		List<ColorSwatchResponse> colorSwatches = new ArrayList<ColorSwatchResponse>();
		for (ColorSwatchRequest colorSwatchFromFeed : colorSwatchesFromFeed) {
			ColorSwatchResponse colorSwatch = new ColorSwatchResponse();
			colorSwatch.setColor(colorSwatchFromFeed.getColor());
			colorSwatch.setRgbColor(getHexColor(colorSwatchFromFeed.getBasicColor()));
			colorSwatch.setSkuid(colorSwatchFromFeed.getSkuId());
			colorSwatches.add(colorSwatch);
		}
		productResponse.setColorSwatches(colorSwatches);
	}

	/**
	 * Method to retrieve the Hexadecimal color using ColorFactory device (AWT
	 * package)
	 */
	private String getHexColor(String colorString) {
		StringBuffer colorResponse = new StringBuffer(COLOR_APPENDER);
		try {
			Color color = ColorFactory.valueOf(colorString.toLowerCase());
			colorResponse.append(getRGBCoodinates(color, ColorType.RED.toString()));
			colorResponse.append(getRGBCoodinates(color, ColorType.GREEN.toString()));
			colorResponse.append(getRGBCoodinates(color, ColorType.BLUE.toString()));
		} catch (IllegalArgumentException illegalArgumentException) {
			colorResponse = new StringBuffer(NOT_A_VALID_COLOR);
		}
		return colorResponse.toString();
	}

	/**
	 * Method to retrieve RGB coordinates from the color type suppied
	 */
	private String getRGBCoodinates(Color colorRGB, String type) {
		int intColorValue = 0;

		switch (ColorType.valueOf(type)) {
		case RED: {
			intColorValue = colorRGB.getRed();
			break;
		}
		case GREEN: {
			intColorValue = colorRGB.getGreen();
			break;
		}
		case BLUE: {
			intColorValue = colorRGB.getBlue();
			break;
		}
		}
		return Integer.toHexString(intColorValue).length() < 2 ? HEX_COLOR_APPENDER + Integer.toHexString(intColorValue)
				: Integer.toHexString(intColorValue);
	}

	/**
	 * Method to build the pricelabel element from the feed
	 */
	private void buildPriceLabel(PriceRequest priceRequest, ProductResponse productResponse, String labelType) {
		StringBuffer priceLabel = new StringBuffer("");

		switch (LabelType.valueOf(labelType.toUpperCase())) {
		case SHOWWASNOW: {
			if (StringUtils.isNotEmpty(priceRequest.getWas())) {
				priceLabel.append("Was ").append(GBP_CURRENCY)
						.append(getFormattedPrice(getFloatValue(priceRequest.getWas()))).append(", ");
				priceLabel.append("now ").append(GBP_CURRENCY).append(getNowPrice(priceRequest.getNow()));
			}
			break;
		}
		case SHOWWASTHENNOW: {
			String thenPrice = null;
			if (StringUtils.isNotEmpty(priceRequest.getThen2())) {
				thenPrice = priceRequest.getThen2();
			} else if (StringUtils.isNotEmpty(priceRequest.getThen1())) {
				thenPrice = priceRequest.getThen1();
			}

			if (StringUtils.isNotEmpty(priceRequest.getWas())) {
				priceLabel.append("Was ").append(GBP_CURRENCY)
						.append(getFormattedPrice(getFloatValue(priceRequest.getWas()))).append(", ");
				if (StringUtils.isNotEmpty(thenPrice)) {
					priceLabel.append("then ").append(GBP_CURRENCY).append(getFormattedPrice(getFloatValue(thenPrice)))
							.append(", ");
				}
				priceLabel.append("now ").append(GBP_CURRENCY).append(getNowPrice(priceRequest.getNow()));
			}
			break;
		}
		case SHOWPERCDSCOUNT: {
			if (StringUtils.isNotEmpty(priceRequest.getWas())) {
				float previousPrice = getFloatValue(priceRequest.getWas());
				float currentPrice = getFloatValue(filteredNowPrice(priceRequest.getNow()));
				if (previousPrice > 0) {
					int discount = (int) Math.round((currentPrice / previousPrice) * 100);
					priceLabel.append(discount).append("% off").append(" - now ").append(GBP_CURRENCY)
							.append(currentPrice);
				}
			}
			break;
		}
		}
		productResponse.setPriceLabel(priceLabel.toString());
	}
}