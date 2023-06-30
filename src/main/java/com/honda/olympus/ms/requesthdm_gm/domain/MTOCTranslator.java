package com.honda.olympus.ms.requesthdm_gm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@ToString()
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class MTOCTranslator {

    private String XProdDivisionCode;
    private String Model;
    private String Type;
    private String Option;
    private String Color;
    private String GMMdlId;
    private String RPOCode;

}
