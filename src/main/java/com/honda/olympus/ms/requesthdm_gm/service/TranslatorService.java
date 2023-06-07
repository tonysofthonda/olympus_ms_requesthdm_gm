package com.honda.olympus.ms.requesthdm_gm.service;

import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import com.honda.olympus.ms.requesthdm_gm.client.TranslatorClient;
import com.honda.olympus.ms.requesthdm_gm.domain.Event;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonMTOC;
import com.honda.olympus.ms.requesthdm_gm.handler.TranslatorEventHandler;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Setter
@Service
public class TranslatorService 
{
	
	@Autowired 
	private TranslatorClient translatorClient;
	
	@Autowired 
	private LogEventService logEventService;
	@Autowired 
	private NotificationService notificationService;
	
	@Autowired
	private TranslatorEventHandler eventHandler;
	
	
	public String sendRequest(JsonMTOC jsonMTOC) {
		try {
			String response = translatorClient.sendRequest(jsonMTOC).getBody();
			if (!StringUtils.hasText(response)) {
				sendEvent(eventHandler.noInfoError(), null);
				return null;
			}
			return response;
		}
		catch (ResourceAccessException exception) 
		{
			if (exception.getCause() instanceof SocketTimeoutException) {
				sendEvent(eventHandler.timeoutError(), null);
				return null;
			}
			sendEvent(eventHandler.noConnectionError(), exception);
			return null;
		}
		catch (RestClientResponseException exception) {
			sendEvent(eventHandler.httpStatusError(exception.getResponseBodyAsString()), null);
			return null;
		}
	}
	
	
	private void sendEvent(Event event, Exception exception) {
		log.error("### {}", event.getMsg(), exception);
		logEventService.logEvent(event);
		notificationService.sendNotification(event);
	}
	
}
