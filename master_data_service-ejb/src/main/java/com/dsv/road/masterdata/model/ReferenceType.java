package com.dsv.road.masterdata.model;

import java.sql.Timestamp;

import javax.interceptor.Interceptors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.dsv.shared.logger.LoggerInterceptor;

@Entity
@Table(name = "REFERENCE_TYPE")
@NamedQueries({
	@NamedQuery(name = "findAllReferenceTypes", query = "SELECT i FROM ReferenceType i"),
	@NamedQuery(name = "findDefaultReferenceTypes", query = "SELECT i FROM ReferenceType i WHERE i.isDefault = true")
})
@Interceptors(LoggerInterceptor.class)
public class ReferenceType {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

	@Column(name = "REFERENCE_TYPE", nullable = false, length = 50)
	String referenceType;

	@Column(name = "IS_DEFAULT", nullable = false)	
	Boolean isDefault = false;
	
	@Column(name = "CREATED_AT", nullable = false)
	Timestamp createdAt;

	@Column(name = "CREATED_BY", length = 50, nullable = false)
	String createdBy;

	@Column(name = "UPDATED_AT")
	Timestamp updatedAt;

	@Column(name = "UPDATED_BY", length = 50)
	String updatedBy;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	
	public Boolean getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
