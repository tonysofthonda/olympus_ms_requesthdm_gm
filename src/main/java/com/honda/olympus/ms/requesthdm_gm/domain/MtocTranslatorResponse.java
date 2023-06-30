package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MtocTranslatorResponse {

    private String xProdDivisionCode;
    private String model;
    private String type;
    private String option;
    private String color;
    private String gmMdlId;
    private List<String> rpoCode;

}
