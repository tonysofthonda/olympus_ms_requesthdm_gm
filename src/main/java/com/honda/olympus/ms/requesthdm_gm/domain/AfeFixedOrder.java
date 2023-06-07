package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AfeFixedOrder 
{
	
	private Integer id;
	private Integer modelColorId;
	
	
	public AfeFixedOrder(Integer id, Integer modelColorId) {
		this.id = id;
		this.modelColorId = modelColorId;
	}
	
}
