package com.honda.olympus.ms.requesthdm_gm.service;

import com.honda.olympus.ms.requesthdm_gm.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class RequestHdmGmService {

    @Autowired
    private AfeService afeService;

    @Autowired
    private TranslatorService translatorService;

    @Autowired
    private MaxTransitService maxTransitService;


    @Scheduled(fixedDelayString = "${timelapse}", timeUnit = TimeUnit.MINUTES)
    public void launchProcess() {
        log.info("RequestHdmGm:: Process Start");

        List<AfeFixedOrder> fixedOrderList = afeService.findFixedOrders();

        for (AfeFixedOrder fixedOrder : fixedOrderList) {
            AfeModelColor modelColor = afeService.findModelColor(fixedOrder);

            if (modelColor == null) continue;

            AfeColor color = afeService.findColor(modelColor);
            if (color == null) continue;

            AfeModel model = afeService.findModel(modelColor);
            if (model == null) continue;

            AfeModelType modelType = afeService.findModelType(model);
            if (modelType == null) continue;

            JsonMTOC jsonMTOC = new JsonMTOC(model.getCode(), modelType.getModelType(), "", color.getCode());
            log.info("Requesthdm:: Request MTOC: {}", jsonMTOC.toJson());
            String translatorRes = translatorService.sendRequest(jsonMTOC);

            if (!StringUtils.hasText(translatorRes)) continue;

            

            JsonMaxTransit jsonMaxTransit = new JsonMaxTransit("REQUEST", Collections.singletonList(translatorRes));
            
            
         // TODO: Se agrega para imprimir log
            MaxTransitRequest maxTransitRequest = MaxTransitRequest.builder()
                    .topic("HONDA_ORDER_REQUEST")
                    .source("hdm")
                    .details(Collections.singletonList(jsonMaxTransit.getDetails().toString()))
                    .build();

            log.info("Requesthdm:: MaxTransit Request V2: {}", maxTransitRequest.toString());
            
            //log.info("Requesthdm:: Maxtransit Request V1: {}", jsonMaxTransit.getDetails().toString());

            List<JsonResponse> jsonResponseList = maxTransitService.sendRequest(jsonMaxTransit);

            if (!jsonResponseList.isEmpty()) {
                afeService.updateFixedOrder(fixedOrder);
            }
        }

        log.info("RequestHdmGm:: Process End");
    }

}
