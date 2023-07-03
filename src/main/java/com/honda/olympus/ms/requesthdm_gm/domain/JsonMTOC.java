package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JsonMTOC extends JsonTransform {

    private String hondaModel;
    private String hondaType;
    private String hondaOption;
    private String hondaColor;

}
