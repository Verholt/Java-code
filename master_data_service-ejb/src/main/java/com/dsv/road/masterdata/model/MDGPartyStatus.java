package com.dsv.road.masterdata.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="MDG_PARTY_STATUS")
@IdClass(value=MDGPartyStatusPrimaryKey.class)
public class MDGPartyStatus {
	@Id
	String statusCode;
	@Id
	String locality;
	@Column
	String description;
	
	@Column(name = "CREATED_AT", nullable = false)
	Timestamp createdAt;

	@Column(name = "CREATED_BY", nullable = false, length = 50)
	String createdBy;

	@Column(name = "UPDATED_AT")
	Timestamp updatedAt;

	@Column(name = "UPDATED_BY")
	String updatedBy;

	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
