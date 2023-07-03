package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MaxTransitRequest {
    private String source;
    private String topic;
    private List<String> details;
}

