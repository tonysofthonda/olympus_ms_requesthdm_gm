package com.honda.olympus.ms.requesthdm_gm.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonMaxTransit 
{
	private String request;
	private List<String> details;
}
