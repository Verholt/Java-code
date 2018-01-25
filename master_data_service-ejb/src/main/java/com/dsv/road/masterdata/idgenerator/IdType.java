package com.dsv.road.masterdata.idgenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ID_TYPE")
public class IdType {
	@Id
	@Column(name = "NAME")
	String name;
	
	@Column(name = "VALUE")
	Long value;

	public IdType() {
		// On purpose for Dozer, Jackson etc.
	}
	
	public IdType(String name, Long value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	
}
