package com.honda.olympus.ms.requesthdm_gm.service;

import com.honda.olympus.ms.requesthdm_gm.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class RequestHdmGmService {

    private final String DIVISION_CODE = "A";

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

            MTOCTranslator mtocTranslator = MTOCTranslator.builder()
                    .XProdDivisionCode(DIVISION_CODE)
                    .Model(model.getCode())
                    .Type(modelType.getModelType())
                    .Option(Strings.EMPTY)
                    .Color(color.getCode())
                    .GMMdlId(Strings.EMPTY)
                    .RPOCode(Strings.EMPTY).build();

            MtocTranslatorResponse mtocTranslatorResponse = translatorService.sendRequest(mtocTranslator);

            if (null == mtocTranslatorResponse) continue;

            JsonMaxTransit jsonMaxTransit = JsonMaxTransit.builder()
                    .topic("HONDA_ORDER_REQUEST")
                    .source("hdm")
                    .details(Collections.singletonList(mtocTranslator.toString()))
                    .build();

            List<JsonResponse> jsonResponseList = maxTransitService.sendRequest(jsonMaxTransit);

            if (!jsonResponseList.isEmpty()) {
                afeService.updateFixedOrder(fixedOrder);

                log.info("RequestHdmGm:: fixed order processed: {}", fixedOrder);
            }
        }

        log.info("RequestHdmGm:: Process End");
    }

}
