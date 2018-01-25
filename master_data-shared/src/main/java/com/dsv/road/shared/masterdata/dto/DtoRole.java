package com.dsv.road.shared.masterdata.dto;

import java.io.Serializable;

public class DtoRole extends AbstractDto implements Serializable {

    private static final long serialVersionUID = -7130538774685965751L;

    Long id;
	String role;

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}
}
