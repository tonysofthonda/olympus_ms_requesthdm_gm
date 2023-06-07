package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AfeModelColor 
{
	
	private Integer modelId;
	private Integer colorId;
	
	
	public AfeModelColor(Integer modelId, Integer colorId) {
		this.modelId = modelId;
		this.colorId = colorId; 
	}
	
}
