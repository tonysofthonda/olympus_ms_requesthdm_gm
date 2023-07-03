package com.honda.olympus.ms.requesthdm_gm.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class MaxTransitRequest {
    private String source;
    private String topic;
    private List<String> details;
	@Override
	public String toString() {
		return "MaxTransitRequest [source=" + source + ", topic=" + topic + ", details=" + details + "]";
	}
    
    
}

