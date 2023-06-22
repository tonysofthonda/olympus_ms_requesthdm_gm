package com.honda.olympus.ms.requesthdm_gm.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.honda.olympus.ms.requesthdm_gm.client.RestTemplateFactory;
import com.honda.olympus.ms.requesthdm_gm.client.TranslatorClient;
import com.honda.olympus.ms.requesthdm_gm.domain.JsonMTOC;
import com.honda.olympus.ms.requesthdm_gm.handler.TranslatorEventHandler;
import com.honda.olympus.ms.requesthdm_gm.property.Properties;
import com.honda.olympus.ms.requesthdm_gm.property.Service;


@TestMethodOrder(OrderAnnotation.class)
public class TranslatorServiceTest {

    static Service service;
    static Properties props;
    static JsonMTOC jsonMTOC;

    @BeforeAll
    static void beforeAll() {
        service = new Service();
        service.setServiceName("ms.requesthdm_gm");

        props = new Properties();
        jsonMTOC = new JsonMTOC();
    }


    @Test
    @Order(1)
    void shouldGetNoInfoErrorEvent() {
        // event handler
        TranslatorEventHandler handler = spy(new TranslatorEventHandler());
        handler.setService(service);

        // translator client
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.OK);

        TranslatorClient client = mock(TranslatorClient.class);
        when(client.sendRequest(jsonMTOC)).thenReturn(response);

        // translator service
        TranslatorService ts = new TranslatorService();
        ts.setTranslatorClient(client);
        ts.setLogEventService(mock(LogEventService.class));
        ts.setNotificationService(mock(NotificationService.class));
        ts.setEventHandler(handler);

        ts.sendRequest(jsonMTOC);

        verify(handler).noInfoError();
    }


    @Test
    @Order(2)
    void shouldGetNoConnectionErrorEvent() {
        // event handler
        TranslatorEventHandler handler = spy(new TranslatorEventHandler());
        handler.setService(service);
        props.setUrlmtoc("http://localhost:8098/translator");
        handler.setProps(props);

        // translator client
        int timewait = 3;
        RestTemplateFactory rtFactory = new RestTemplateFactory(timewait);
        TranslatorClient client = new TranslatorClient(rtFactory, props);

        // translator service
        TranslatorService ts = new TranslatorService();
        ts.setTranslatorClient(client);
        ts.setLogEventService(mock(LogEventService.class));
        ts.setNotificationService(mock(NotificationService.class));
        ts.setEventHandler(handler);

        ts.sendRequest(jsonMTOC);

        verify(handler).noConnectionError();
    }


    @Test
    @Order(3)
    void shouldGetTimeoutErrorEvent() {
        // event handler
        TranslatorEventHandler handler = spy(new TranslatorEventHandler());
        handler.setService(service);
        props.setUrlmtoc("http://54.193.217.196:8098/translator");

        handler.setProps(props);

        // translator client
        int timewait = 1;
        RestTemplateFactory rtFactory = new RestTemplateFactory(timewait);
        TranslatorClient client = new TranslatorClient(rtFactory, props);

        // translator service
        TranslatorService ts = new TranslatorService();
        ts.setTranslatorClient(client);
        ts.setLogEventService(mock(LogEventService.class));
        ts.setNotificationService(mock(NotificationService.class));
        ts.setEventHandler(handler);

        ts.sendRequest(jsonMTOC);

        verify(handler).timeoutError();
    }


    @Test
    @Order(4)
    void shouldGetHttpStatusErrorEvent() {
        // event handler
        TranslatorEventHandler handler = spy(new TranslatorEventHandler());
        handler.setService(service);

        // translator client
        String response = "Invalid request !";
        HttpClientErrorException exception =
                HttpClientErrorException.create(
                        HttpStatus.BAD_REQUEST, "Bad Request", null, response.getBytes(), null);

        TranslatorClient client = mock(TranslatorClient.class);
        when(client.sendRequest(jsonMTOC)).thenThrow(exception);

        // translator service
        TranslatorService ts = new TranslatorService();
        ts.setTranslatorClient(client);
        ts.setLogEventService(mock(LogEventService.class));
        ts.setNotificationService(mock(NotificationService.class));
        ts.setEventHandler(handler);

        ts.sendRequest(jsonMTOC);

        verify(handler).httpStatusError(response);
    }

}
