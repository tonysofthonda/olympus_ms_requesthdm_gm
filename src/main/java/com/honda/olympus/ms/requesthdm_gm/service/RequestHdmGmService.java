package com.honda.olympus.ms.requesthdm_gm.service;

import com.honda.olympus.ms.requesthdm_gm.domain.*;
import com.honda.olympus.ms.requesthdm_gm.util.Constants;
import com.honda.olympus.ms.requesthdm_gm.util.SqlUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class RequestHdmGmService {

	@Autowired
	private AfeService afeService;

	@Autowired
	private TranslatorService translatorService;

	@Autowired
	private MaxTransitService maxTransitService;
	
	@Autowired
	Environment environment; 

	@Scheduled(fixedDelayString = "${timelapse}", timeUnit = TimeUnit.MINUTES)
	public void launchProcess() {
		log.info("RequestHdmGm:: Process Start");

		String ipAddress = environment.getProperty("local.server.ip");

		String obs = String.format(Constants.CLIENT_IP_TIMESTAMP, ipAddress, SqlUtil.getTimeStamp());

		//Query1
		List<AfeFixedOrder> fixedOrderList = afeService.findFixedOrders();

		for (AfeFixedOrder fixedOrder : fixedOrderList) {

			//Query2
			AfeModelColor modelColor = afeService.findModelColor(fixedOrder);
			if (modelColor == null)
				continue;

			//Query3
			AfeColor color = afeService.findColor(modelColor);
			if (color == null)
				continue;
			
			//Query4
			AfeModel model = afeService.findModel(modelColor);
			if (model == null)
				continue;

			//Query5
			AfeModelType modelType = afeService.findModelType(model);
			if (modelType == null)
				continue;

			//Query6
			AfeAction action = afeService.findAction(fixedOrder);
			if (action == null)
				continue;

			//Query7
			AfeDivision division = afeService.findDivision(model);
			if (division == null)
				continue;

			JsonMTOC jsonMTOC = new JsonMTOC(Constants.A_VALUE,model.getCode(), modelType.getModelType(), "", color.getCode(),"","");
			log.info("Requesthdm:: Request MTOC: {}", jsonMTOC.toJson());
			String translatorRes = translatorService.sendRequest(jsonMTOC);

			if (!StringUtils.hasText(translatorRes))
				continue;

			JsonMaxTransit jsonMaxTransit = new JsonMaxTransit("REQUEST", Collections.singletonList(translatorRes));

			MaxTransitRequest maxTransitRequest = MaxTransitRequest.builder().topic("HONDA_ORDER_REQUEST").source("hdm")
					.details(Collections.singletonList(jsonMaxTransit.getDetails().toString())).build();

			log.info("Requesthdm:: MaxTransit Request V2: {}", maxTransitRequest.toString());

			List<JsonResponse> jsonResponseList = maxTransitService.sendRequest(jsonMaxTransit);

			//Query8
			if (!jsonResponseList.isEmpty()) {
				afeService.updateFixedOrder(fixedOrder, obs);
			}

			//Query9
			afeService.insertOrdenActionHisotory(fixedOrder, action, obs);
		}

		log.info("RequestHdmGm:: Process End");
	}

}
