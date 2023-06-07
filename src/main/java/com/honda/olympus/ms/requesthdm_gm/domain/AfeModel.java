package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AfeModel 
{
	
	private String code;
	private Integer modelTypeId;
	
	
	public AfeModel(String code, Integer modelTypeId) {
		this.code = code;
		this.modelTypeId = modelTypeId;
	}
	
}
