package com.honda.olympus.ms.requesthdm_gm.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.honda.olympus.ms.requesthdm_gm.client.MaxTransitClient;
import com.honda.olympus.ms.requesthdm_gm.domain.Event;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonMaxTransit;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonResponse;
import com.honda.olympus.ms.requesthdm_gm.handler.MaxTransitEventHandler;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Setter
@Service
public class MaxTransitService 
{
	
	@Autowired
	private MaxTransitClient maxTransitClient;
	
	@Autowired 
	private LogEventService logEventService;
	@Autowired 
	private NotificationService notificationService;
	
	@Autowired
	private MaxTransitEventHandler eventHandler; 
	
	
	public List<JsonResponse> sendRequest(JsonMaxTransit jsonMaxTransit) {
		try {
			return maxTransitClient.sendRequest(jsonMaxTransit).getBody();
		}
		catch (ResourceAccessException exception) {
			if (exception.getCause() instanceof SocketTimeoutException) {
				sendEvent(eventHandler.timeoutError(), null);
				return new ArrayList<>();
			}
			sendEvent(eventHandler.noConnectionError(), exception);
			return new ArrayList<>();
		}
		catch (HttpClientErrorException exception) {
			sendEvent(eventHandler.httpStatusError(exception.getResponseBodyAsString()), null);
			return new ArrayList<>();
		}
	}
	
	
	private void sendEvent(Event event, Exception exception) {
		log.error("### {}", event.getMsg(), exception);
		logEventService.logEvent(event);
		notificationService.sendNotification(event);
	}
	
}
