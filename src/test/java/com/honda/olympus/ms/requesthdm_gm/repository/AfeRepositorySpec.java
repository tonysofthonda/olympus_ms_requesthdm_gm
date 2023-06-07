package com.honda.olympus.ms.requesthdm_gm.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.honda.olympus.ms.requesthdm_gm.domain.AfeColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeFixedOrder;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModel;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelType;
import com.honda.olympus.ms.requesthdm_gm.service.RequestHdmGmService;


@SpringBootTest
@MockBean(RequestHdmGmService.class)
public class AfeRepositorySpec 
{
	
	@Autowired
	AfeRepository afeRepo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Test
	void afeRepositoryIsNotNull() {
		assertNotNull(afeRepo);
	}
	
	
	@Test
	void shouldFindFixedOrders() {
		List<AfeFixedOrder> fixedOrders = afeRepo.findFixedOrders();
		assertFalse(fixedOrders.isEmpty());
	}
	
	
	@Test
	void shouldfindModelColor() {
		AfeFixedOrder fixedOrder = new AfeFixedOrder(7, 10);
		AfeModelColor modelColor = afeRepo.findModelColor(fixedOrder);
		assertNotNull(modelColor);
	}
	
	
	@Test
	void shouldFindColor() {
		AfeModelColor modelColor = new AfeModelColor(11, 12);
		AfeColor color = afeRepo.findColor(modelColor);
		assertNotNull(color);
	}
	
	
	@Test
	void shouldFindModel() {
		AfeModelColor modelColor = new AfeModelColor(11, 12);
		AfeModel model = afeRepo.findModel(modelColor);
		assertNotNull(model);
	}
	
	
	@Test
	void shouldFindModelType() {
		AfeModel model = new AfeModel("modelcode", 1);
		AfeModelType modelType = afeRepo.findModelType(model);
		assertNotNull(modelType);
	}
	
	
	@Test 
	void shouldUpdateFixedOrder() {
		AfeFixedOrder fixedOrder = new AfeFixedOrder(10, 2024);
		int result = afeRepo.updateFixedOrder(fixedOrder);
		assertEquals(1, result);
		
		String updateSQL = "update afedb.afe_fixed_orders_ev set envio_flag = 'false' where id = ?";
		jdbcTemplate.update(updateSQL, fixedOrder.getId());
	}
	
}
