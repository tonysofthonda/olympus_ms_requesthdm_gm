package com.honda.olympus.ms.requesthdm_gm.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JsonResponse 
{
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" ;
	
	private String action;
	private String vehOrderNbr;
	private String modelYearNbr;
	private String sellingSrcCd;
	private String optionCode;
	private String originType;
	private String externConfigIdentfr;
	private String orderTyoeCd;
	private String mdseModlDesgtr;
	private String chrgBusnsAsctCd;
	private String chrgBusnsFncCd;
	private String shipBusnsAsctCd;
	private String shioBusnsFncCd;
	private Integer rqstIdentfr;
	private String reqstStatus;
	private List<String> message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
	private LocalDateTime voLastChgTimestamp;
}
