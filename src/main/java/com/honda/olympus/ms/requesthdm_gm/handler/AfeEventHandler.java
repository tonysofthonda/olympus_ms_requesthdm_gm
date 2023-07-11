package com.honda.olympus.ms.requesthdm_gm.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.honda.olympus.ms.requesthdm_gm.domain.AfeAction;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeFixedOrder;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModel;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelColor;
import com.honda.olympus.ms.requesthdm_gm.domain.Event;
import com.honda.olympus.ms.requesthdm_gm.domain.Status;
import com.honda.olympus.ms.requesthdm_gm.property.Properties;
import com.honda.olympus.ms.requesthdm_gm.property.Query;
import com.honda.olympus.ms.requesthdm_gm.property.Service;

import ch.qos.logback.core.joran.action.Action;


@Component
public class AfeEventHandler 
{
	
	private static final String MSG_MODEL_COLOR_ERROR = "NO existe el model_color_id %s en la tabla AFE_MODEL_COLOR con el query (%s)";
	private static final String MSG_COLOR_ERROR = "NO existe el color_id %s en la tabla AFE_COLOR con el query 3 (%s)";
	private static final String MSG_MODEL_ERROR = "NO existe el model_id %s en la tabla AFE_MODEL con el query 4 (%s)";
	private static final String MSG_MODEL_TYPE_ERROR = "NO existe el MODEL_TYPE_ID %s en la tabla AFE_MODEL_TYPE con el query 5 (%s)";
	private static final String MSG_ACTION_ERROR = "NO existe el action id %s en la tabla AFE_ACTION_EV con el query 6 (%s)";
	private static final String MSG_DIVISION_ERROR = "NO existe el divison id %s en la tabla AFE_DIVISION con el query 7 (%s)";	
	private static final String MSG_FIXED_ORDER_OK = "Actualización exitosa del registro con id %s en la tabla AFE_FIXED_ORDERS_EV";
	private static final String MSG_FIXED_ORDER_ERROR = "Fallo en la ejecución del query de actualización en la tabla AFE_FIXED_ORDERS_EV con el query 6 (%s)";
	
	private static final String EMPTY = "";
	
	private static final String MAX_TRANSIT_SAVE_ERROR = "102 Error al gurdar en MaxTransit. La orden de producción no se guardo correctamente en Maxtransit";
	private static final String MSG_INSERT_ERROR = "104 Error al guardar en BD. No se pudo insertar en la BD";
	private static final String MSG_ORDER_HISTORY_ERROR = "Fallo en la ejecución del query de actualización en la tabla AFE_ORDERS_ACTION_HISTORY con el query  (%s)";
	
	
	@Autowired private Properties props;
	@Autowired private Service service;
	@Autowired private Query query;
	
    @Value("${service.name}")
	private String serviceName;
	
	
	public Event dbConnectionError() {
		String message = String.format(MAX_TRANSIT_SAVE_ERROR, props.getDbname(), props.getDbhost(), props.getDbport(), props.getDbuser());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findModelColorError(AfeFixedOrder fixedOrder) {
		String message = String.format(MSG_MODEL_COLOR_ERROR, fixedOrder.getModelColorId(), query.getInsertOrderHistoryKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findColorError(AfeModelColor modelColor) {
		String message = String.format(MSG_COLOR_ERROR, modelColor.getColorId(), query.getFindColorKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findModelError(AfeModelColor modelColor) {
		String message = String.format(MSG_MODEL_ERROR, modelColor.getModelId(), query.getFindModelKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY);
	}
	
	public Event findModelTypeError(AfeModel model){
		String message = String.format(MSG_MODEL_TYPE_ERROR, model.getModelTypeId(), query.getFindModelTypeKEY());	
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY); 
	}
	
	public Event updateFixedOrderOk(AfeFixedOrder fixedOrder) {
		String message = String.format(MSG_FIXED_ORDER_OK, fixedOrder.getId());
		return new Event(service.getServiceName(), Status._SUCCESS, message, EMPTY); 
	}
	
	public Event updateFixedOrderError() {
		String message = String.format(MSG_FIXED_ORDER_ERROR, query.getUpdateFixedOrderKEY());
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY); 
	}
	
	public Event saveMaxtransitError( ) {
		String message = String.format(MAX_TRANSIT_SAVE_ERROR);
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY); 
	}
	
	public Event findActionError(AfeFixedOrder action){
		String message = String.format(MSG_ACTION_ERROR, action.getActionId(), query.getFindActionKEY());	
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY); 
	}
	
	public Event findDivisionError(AfeModel model){
		String message = String.format(MSG_DIVISION_ERROR, model.getDivisionId(), query.getFindActionKEY());	
		return new Event(service.getServiceName(), Status._FAIL, message, EMPTY); 
	}
	
	public Event insertActionOrderHistoryInsert() {
		String message = String.format(MSG_INSERT_ERROR);
		return new Event(service.getServiceName(), Status._SUCCESS, message, EMPTY); 
	}
	
	public Event insertActionOrderHistorySaveError() {
		String message = String.format(MSG_ORDER_HISTORY_ERROR,query.getFindActionKEY());
		return new Event(service.getServiceName(), Status._SUCCESS, message, EMPTY); 
	}
	
}
