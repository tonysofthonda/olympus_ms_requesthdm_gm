package com.honda.olympus.ms.requesthdm_gm.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.honda.olympus.ms.requesthdm_gm.domain.AfeColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeFixedOrder;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModel;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelType;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonMTOC;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonMaxTransit;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RequestHdmGmService 
{
	
	@Autowired
	private AfeService afeService;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private MaxTransitService maxTransitService;
	
	
	@Scheduled(fixedDelayString = "${timelapse}", timeUnit = TimeUnit.MINUTES)
	public void launchProcess() 
	{
		log.info("RequestHdmGm:: Process Start");
		
		log.debug(">>> finding fixed orders");
		List<AfeFixedOrder> fixedOrderList = afeService.findFixedOrders();		
		for (AfeFixedOrder fixedOrder: fixedOrderList)  
		{
			log.debug(">>> finding model-color");
			AfeModelColor modelColor = afeService.findModelColor(fixedOrder);
			if (modelColor == null) continue; 
			
			log.debug(">>> finding color");
			AfeColor color = afeService.findColor(modelColor);
			if (color == null) continue;
			
			log.debug(">>> finding model");
			AfeModel model = afeService.findModel(modelColor);
			if (model == null) continue;
			
			log.debug(">>> finding model-type");
			AfeModelType modelType = afeService.findModelType(model);
			if (modelType == null) continue;
			
			
			log.debug(">>> sending jsonMTOC");
			JsonMTOC jsonMTOC = new JsonMTOC(model.getCode(), modelType.getModelType(), "", color.getCode());
			String translatorRes = translatorService.sendRequest(jsonMTOC);
			if (!StringUtils.hasText(translatorRes)) continue;
			
			
			log.debug(">>> sending jsonMaxTransit");
			JsonMaxTransit jsonMaxTransit = new JsonMaxTransit("REQUEST", Arrays.asList(translatorRes));
			List<JsonResponse> jsonResponseList = maxTransitService.sendRequest(jsonMaxTransit);
			
			if (!jsonResponseList.isEmpty()) 
			{ 
				log.debug(">>> updating fixed order");
				afeService.updateFixedOrder(fixedOrder);
				
				log.debug("# fixed order processed: {}", fixedOrder);
			}
		}
		
		log.info("RequestHdmGm:: Process End");
	}
	
}
