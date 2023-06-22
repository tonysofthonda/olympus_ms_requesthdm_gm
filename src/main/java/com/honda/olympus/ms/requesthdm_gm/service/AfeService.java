package com.honda.olympus.ms.requesthdm_gm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.honda.olympus.ms.requesthdm_gm.domain.AfeColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeFixedOrder;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModel;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelType;
import com.honda.olympus.ms.requesthdm_gm.domain.Event;
import com.honda.olympus.ms.requesthdm_gm.handler.AfeEventHandler;
import com.honda.olympus.ms.requesthdm_gm.repository.AfeRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AfeService 
{	
	
	@Autowired 
	private AfeRepository afeRepository;
	
	@Autowired 
	private LogEventService logEventService;
	@Autowired 
	private NotificationService notificationService;
	
	@Autowired
	private AfeEventHandler eventHandler;
	
	
	public List<AfeFixedOrder> findFixedOrders() {
		try {
			return afeRepository.findFixedOrders();
		} 
		catch (DataAccessException exception) {
			handleDataAccessException(exception);
			return new ArrayList<>();
		}
	}
	

	public AfeModelColor findModelColor(AfeFixedOrder fixedOrder) {
		try {
			AfeModelColor afeModelColor = afeRepository.findModelColor(fixedOrder);

			if (afeModelColor == null) {
				logEvent( eventHandler.findModelColorError(fixedOrder) );
			}
			return afeModelColor;
		} 
		catch (DataAccessException exception) {
			handleDataAccessException(exception);
			return null;
		}
	}
	
	
	public AfeColor findColor(AfeModelColor modelColor) {
		try {
			AfeColor afeColor = afeRepository.findColor(modelColor);
			if (afeColor == null) {
				logEvent( eventHandler.findColorError(modelColor) );
			}
			return afeColor;
		}
		catch (DataAccessException exception) {
			handleDataAccessException(exception);
			return null;
		}
	}
	
	
	public AfeModel findModel(AfeModelColor modelColor) {
		try {
			AfeModel afeModel = afeRepository.findModel(modelColor);
			if (afeModel == null) {
				logEvent( eventHandler.findModelError(modelColor) );
			}
			return afeModel;
		}
		catch (DataAccessException exception) {
			handleDataAccessException(exception);
			return null;
		}
	}
	
	
	public AfeModelType findModelType(AfeModel model) {
		try { 
			AfeModelType afeModelType = afeRepository.findModelType(model); 
			if (afeModelType == null) {
				logEvent( eventHandler.findModelTypeError(model) );
			}
			return afeModelType;
		}
		catch (DataAccessException exception) {
			handleDataAccessException(exception);
			return null;
		}
	}
	
	
	public void updateFixedOrder(AfeFixedOrder fixedOrder) {
		try {
			int rowsAffected = afeRepository.updateFixedOrder(fixedOrder);
			if (rowsAffected == 1) {
				Event event = eventHandler.updateFixedOrderOk(fixedOrder);
				logEventService.logEvent(event);
				notificationService.sendNotification(event);
			}
			else {
				logEvent( eventHandler.updateFixedOrderError() );
			}
		}
		catch (DataAccessException exception) {
			handleDataAccessException(exception);
		}
	}
	
	
	private void logEvent(Event event) {
		logEventService.logEvent(event);
		log.error("### {}", event.getMsg());
	}
	
	
	private void handleDataAccessException(DataAccessException exception) {
		log.error("### Error found while connecting to DB: {}", exception);
		Event event = eventHandler.dbConnectionError();
		logEventService.logEvent(event);
		notificationService.sendNotification(event);
	}
	
}
