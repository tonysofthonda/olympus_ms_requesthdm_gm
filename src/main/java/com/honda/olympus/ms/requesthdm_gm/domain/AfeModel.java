package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AfeModel 
{
	
	private String code;
	private Integer modelTypeId;
	private Long divisionId;
	
	
	public AfeModel(String code, Integer modelTypeId) {
		this.code = code;
		this.modelTypeId = modelTypeId;
	}


	public AfeModel(String code, Integer modelTypeId, Long divisionId) {
		this.code = code;
		this.modelTypeId = modelTypeId;
		this.divisionId = divisionId;
	}
	
	
}
