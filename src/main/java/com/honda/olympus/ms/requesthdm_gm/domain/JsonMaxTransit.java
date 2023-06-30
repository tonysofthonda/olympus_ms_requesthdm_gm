package com.honda.olympus.ms.requesthdm_gm.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonMaxTransit {
    private String source;
    private String topic;
    private List<String> details;
}
