package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AfeColor 
{
	private String code;
	
	public AfeColor(String code) {
		this.code = code; 
	}
}
