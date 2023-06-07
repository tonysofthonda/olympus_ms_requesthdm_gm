package com.honda.olympus.ms.requesthdm_gm.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.honda.olympus.ms.requesthdm_gm.domain.Event;
import com.honda.olympus.ms.requesthdm_gm.domain.Status;
import com.honda.olympus.ms.requesthdm_gm.property.Properties;
import com.honda.olympus.ms.requesthdm_gm.property.Service;

import lombok.Setter;


@Setter
@Component
public class TranslatorEventHandler 
{
	
	private static final String MSG_TIMEOUT_ERROR = "Tiempo de espera agotado en la consulta a la API MTOC-RPO TRASLATOR ubicada en %s";
	private static final String MSG_HTTP_STATUS_ERROR = "La API MTOC-RPO TRASLATOR retorno un error: %s";
	private static final String MSG_NO_INFO_ERROR = "La respuesta de la API MTOC-RPO TRASLATOR no retorna información";
	private static final String MSG_NO_CONN_ERROR = "Sin conexión a la API MTOC-RPO TRASLATOR";
	
	private static final String EMPTY = "";
	
	
	@Autowired private Properties props;
	@Autowired private Service service;
	
	
	public Event timeoutError() {
		String message = String.format(MSG_TIMEOUT_ERROR, props.getUrlmtoc());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event httpStatusError(String response) {
		String message = String.format(MSG_HTTP_STATUS_ERROR, response);
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event noInfoError() {
		String message = String.format(MSG_NO_INFO_ERROR);
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event noConnectionError() {
		return new Event(service.getServiceName(), Status._FAIL, MSG_NO_CONN_ERROR, EMPTY);
	}

}
