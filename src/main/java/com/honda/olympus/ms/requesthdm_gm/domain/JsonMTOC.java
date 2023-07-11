package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JsonMTOC extends JsonTransform {

    private String XProdDivisionCode;
    private String Model;
    private String Type;
    private String Option;
    private String Color;
    private String GMMdlId;
    private String RPOCode;
    
	public JsonMTOC(String xProdDivisionCode, String model, String type, String option, String color, String gMMdlId,
			String rPOCode) {
		super();
		XProdDivisionCode = xProdDivisionCode;
		Model = model;
		Type = type;
		Option = option;
		Color = color;
		GMMdlId = gMMdlId;
		RPOCode = rPOCode;
	}
    
    

}
