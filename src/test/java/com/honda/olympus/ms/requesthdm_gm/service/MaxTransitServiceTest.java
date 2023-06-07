package com.honda.olympus.ms.requesthdm_gm.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.honda.olympus.ms.requesthdm_gm.client.MaxTransitClient;
import com.honda.olympus.ms.requesthdm_gm.client.RestTemplateFactory;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonMaxTransit;
import com.honda.olympus.ms.requesthdm_gm.handler.MaxTransitEventHandler;
import com.honda.olympus.ms.requesthdm_gm.property.Properties;
import com.honda.olympus.ms.requesthdm_gm.property.Service;


@TestMethodOrder(OrderAnnotation.class)
public class MaxTransitServiceTest 
{
	
	static Service service; 
	static Properties props;
	static JsonMaxTransit jsonMaxTransit;
	static RestTemplateFactory rtFactory;
	
	
	@BeforeAll
	static void beforeAll() 
	{
		service = new Service();
		service.setServiceName("ms.requesthdm_gm");
		
		props = new Properties();
		props.setUrlmax("http://localhost:8099/maxtransit");
		
		jsonMaxTransit = new JsonMaxTransit();
	}
	
	
	@Test
	@Order(1)
	void shouldGetNoConnectionErrorEvent() 
	{
		// event handler
		MaxTransitEventHandler handler = spy(new MaxTransitEventHandler());
		handler.setService(service);
		handler.setProps(props);
		
		// translator client
		int timewait = 3;
		RestTemplateFactory rtFactory = new RestTemplateFactory(timewait);
		MaxTransitClient client = new MaxTransitClient(rtFactory, props);
		
		// max transit service
		MaxTransitService mts = new MaxTransitService();
		mts.setMaxTransitClient(client);
		mts.setLogEventService( mock(LogEventService.class) );
		mts.setNotificationService( mock(NotificationService.class) );
		mts.setEventHandler(handler);
		
		mts.sendRequest(jsonMaxTransit);
		
		verify(handler).noConnectionError();
	}
	
	
	@Test
	@Order(2)
	void shouldGetTimeoutErrorEvent() 
	{
		// event handler
		MaxTransitEventHandler handler = spy(new MaxTransitEventHandler());
		handler.setService(service);
		handler.setProps(props);
		
		// translator client
		int timewait = 1;
		RestTemplateFactory rtFactory = new RestTemplateFactory(timewait);
		MaxTransitClient client = new MaxTransitClient(rtFactory, props);
		
		// translator service
		MaxTransitService mts = new MaxTransitService();
		mts.setMaxTransitClient(client);
		mts.setLogEventService( mock(LogEventService.class) );
		mts.setNotificationService( mock(NotificationService.class) );
		mts.setEventHandler(handler);
		
		mts.sendRequest(jsonMaxTransit);
		
		verify(handler).timeoutError();
	}
	
	
	@Test
	@Order(3)
	void shouldGetHttpStatusErrorEvent() 
	{
		// event handler
		MaxTransitEventHandler handler = spy(new MaxTransitEventHandler());
		handler.setService(service);
		
		// translator client
		String response = "Invalid request !"; 
		HttpClientErrorException exception = 
			HttpClientErrorException.create(
				HttpStatus.BAD_REQUEST, "Bad Request", null, response.getBytes(), null);
		
		MaxTransitClient client = mock(MaxTransitClient.class);
		when(client.sendRequest(jsonMaxTransit)).thenThrow(exception);
		
		// translator service
		MaxTransitService mts = new MaxTransitService();
		mts.setMaxTransitClient(client);
		mts.setLogEventService( mock(LogEventService.class) );
		mts.setNotificationService( mock(NotificationService.class) );
		mts.setEventHandler(handler);
		
		mts.sendRequest(jsonMaxTransit);		
		
		verify(handler).httpStatusError(response);
	}
	
}
