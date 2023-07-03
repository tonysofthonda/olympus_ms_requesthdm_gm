package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JsonMaxTransit extends JsonTransform {
    private String request;
    private List<String> details;
}
