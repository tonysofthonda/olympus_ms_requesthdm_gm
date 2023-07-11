package com.honda.olympus.ms.requesthdm_gm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AfeFixedOrder {

	private Integer id;
	private Integer modelColorId;
	private Long actionId;
	private Boolean flagGm;

	public AfeFixedOrder(Integer id, Integer modelColorId) {
		this.id = id;
		this.modelColorId = modelColorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModelColorId() {
		return modelColorId;
	}

	public void setModelColorId(Integer modelColorId) {
		this.modelColorId = modelColorId;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Boolean getFlagGm() {
		return flagGm;
	}

	public void setFlagGm(Boolean flagGm) {
		this.flagGm = flagGm;
	}

}
