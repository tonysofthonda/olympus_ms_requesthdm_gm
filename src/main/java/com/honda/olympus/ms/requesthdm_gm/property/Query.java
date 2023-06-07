package com.honda.olympus.ms.requesthdm_gm.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;


@Getter
@Component
public class Query 
{
	private final String findFixedOrdersKEY = "findFixedOrders";
	private final String findModelColorKEY  = "findModelColor";
	private final String findColorKEY = "findColor";
	private final String findModelKEY = "findModel";
	private final String findModelTypeKEY = "findModelType";
	private final String updateFixedOrderKEY = "updateFixedOrder";
	
	@Value("${findFixedOrders}") private String findFixedOrders;
	@Value("${findModelColor}")  private String findModelColor;
	@Value("${findColor}") private String findColor;
	@Value("${findModel}") private String findModel;
	@Value("${findModelType}") private String findModelType;
	@Value("${updateFixedOrder}") private String updateFixedOrder;	
}
