package com.honda.olympus.ms.requesthdm_gm.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.honda.olympus.ms.requesthdm_gm.domain.Event;
import com.honda.olympus.ms.requesthdm_gm.domain.Status;
import com.honda.olympus.ms.requesthdm_gm.property.Properties;
import com.honda.olympus.ms.requesthdm_gm.property.Query;
import com.honda.olympus.ms.requesthdm_gm.property.Service;


@Component
public class EventHandler 
{
	
	private static final String MSG_DB_CONNECTION_ERROR = "No es posible conectarse a la base de datos %s, %s, %s, %s"; 
	private static final String MSG_MODEL_COLOR_ERROR = "NO existe el model_color_id %s en la tabla AFE_MODEL_COLOR con el query 2 (%s)";
	private static final String MSG_COLOR_ERROR = "NO existe el color_id %s en la tabla AFE_COLOR con el query 3 (%s)";
	private static final String MSG_MODEL_ERROR = "NO existe el model_id %s en la tabla AFE_MODEL con el query 4 (%s)";
	private static final String MSG_MODEL_TYPE_ERROR = "NO existe el MODEL_TYPE_ID %s en la tabla AFE_MODEL_TYPE con el query 5 (%s)";
	private static final String MSG_FIXED_ORDER_OK = "Actualización exitosa del registro con id %s en la tabla AFE_ORDER_EV";
	private static final String MSG_FIXED_ORDER_ERROR = "Fallo en la ejecución del query de actualización en la tabla AFE_FIXED_EV con el query 6 (%s)";
	
	private static final String EMPTY = "";
	
	
	@Autowired private Properties props;
	@Autowired private Service service;
	@Autowired private Query query;
	
	
	public Event dbConnectionError() {
		String message = String.format(MSG_DB_CONNECTION_ERROR, props.getDbname(), props.getDbhost(), props.getDbport(), props.getDbuser());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findModelColorError(Integer modelColorId) {
		String message = String.format(MSG_MODEL_COLOR_ERROR, modelColorId, query.getFindModelColorKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findColorError(Integer colorId) {
		String message = String.format(MSG_COLOR_ERROR, colorId, query.getFindColorKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findModelError(Integer modelId) {
		String message = String.format(MSG_MODEL_ERROR, modelId, query.getFindModelKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findModelTypeError(Integer modelTypeId){
		String message = String.format(MSG_MODEL_TYPE_ERROR, modelTypeId, query.getFindModelTypeKEY());	
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY); 
	}
	
	public Event updateFixedOrderOk(Integer fixedOrderId) {
		String message = String.format(MSG_FIXED_ORDER_OK, fixedOrderId);
		return new Event(service.getServiceName(), Status._SUCCESS, message, EMPTY); 
	}
	
	public Event updateFixedOrderError() {
		String message = String.format(MSG_FIXED_ORDER_ERROR, query.getUpdateFixedOrderKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY); 
	}
	
}
