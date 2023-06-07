package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JsonMTOC 
{
	private String hondaModel;
	private String hondaType; 
	private String hondaOption;
	private String hondaColor;
	
	public JsonMTOC(String hondaModel, String hondaType, String hondaOption, String hondaColor) {
		this.hondaModel = hondaModel;
		this.hondaType = hondaType;
		this.hondaOption = hondaOption;
		this.hondaColor = hondaColor;
	}
	
}
