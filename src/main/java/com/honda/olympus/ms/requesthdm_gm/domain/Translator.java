package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Translator 
{
	private String hondaModel;
	private String hondaType; 
	private String hondaOption;
	private String hondaColor;
}
