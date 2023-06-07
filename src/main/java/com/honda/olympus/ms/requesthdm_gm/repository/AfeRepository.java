package com.honda.olympus.ms.requesthdm_gm.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.honda.olympus.ms.requesthdm_gm.domain.AfeColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeFixedOrder;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModel;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelColor;
import com.honda.olympus.ms.requesthdm_gm.domain.AfeModelType;

import static com.honda.olympus.ms.requesthdm_gm.util.SqlUtil.getInt;


@Repository
@PropertySource("classpath:query.properties")
public class AfeRepository 
{
	
	@Value("${findFixedOrders}")
	private String findFixedOrdersSQL;
	
	@Value("${findModelColor}")
	private String findModelColorSQL; 
	
	@Value("${findColor}")
	private String findColorSQL; 
	
	@Value("${findModel}")
	private String findModelSQL; 
	
	@Value("${findModelType}")
	private String findModelTypeSQL; 
	
	@Value("${updateFixedOrder}")
	private String updateFixedOrderSQL; 
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<AfeFixedOrder> findFixedOrders() 
	{
		return jdbcTemplate.query(
			findFixedOrdersSQL, 
			(rs, rowNum) -> 
			{
				AfeFixedOrder fixedOrder = new AfeFixedOrder();
				
				fixedOrder.setId( getInt(rs, "id") );
				fixedOrder.setModelColorId( getInt(rs, "modelColorId") );
				
				return fixedOrder;
			});
	}
	
	
	public AfeModelColor findModelColor(AfeFixedOrder fixedOrder) 
	{
		List<AfeModelColor> list = jdbcTemplate.query(
			findModelColorSQL, 
			(rs, rowNum) -> 
			{
				AfeModelColor modelColor = new AfeModelColor();
				
				modelColor.setColorId( getInt(rs, "colorId") );
				modelColor.setModelId( getInt(rs, "modelId") );
				
				return modelColor;
			}, 
			fixedOrder.getModelColorId());
		
		return list.isEmpty() ? null : list.get(0);
	}
	
	
	public AfeColor findColor(AfeModelColor modelColor) 
	{
		List<AfeColor> list = jdbcTemplate.query(
			findColorSQL, 
			(rs, rowNum) -> 
			{
				AfeColor color = new AfeColor();
				color.setCode( rs.getString("code") );
				return color; 
			}, 
			modelColor.getColorId());
		
		return list.isEmpty() ? null : list.get(0);
	}
	
	
	public AfeModel findModel(AfeModelColor modelColor) 
	{
		List<AfeModel> list = jdbcTemplate.query(
			findModelSQL, 
			(rs, rowNum) -> 
			{
				AfeModel model = new AfeModel();
				
				model.setCode( rs.getString("code") );
				model.setModelTypeId( getInt(rs, "modelTypeId") );
				
				return model;
			},
			modelColor.getModelId());
		
		return list.isEmpty() ? null : list.get(0);
	}
	
	
	public AfeModelType findModelType(AfeModel model) 
	{
		List<AfeModelType> list = jdbcTemplate.query(
			findModelTypeSQL, 
			(rs, rowNum) -> 
			{
				AfeModelType modelType = new AfeModelType();
				modelType.setModelType( rs.getString("modelType") );
				return modelType;
			}, 
			model.getModelTypeId());
		
		return list.isEmpty() ? null : list.get(0);
	}
	
	
	public int updateFixedOrder(AfeFixedOrder fixedOrder) {
		return jdbcTemplate.update(updateFixedOrderSQL, fixedOrder.getId());
	}
	
}
