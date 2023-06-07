package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AfeModelType 
{
	private String modelType;
	
	public AfeModelType(String modelType) {
		this.modelType = modelType;
	}
}
