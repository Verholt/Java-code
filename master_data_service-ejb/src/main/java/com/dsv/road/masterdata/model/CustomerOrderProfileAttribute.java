package com.dsv.road.masterdata.model;

import java.sql.Timestamp;

import javax.interceptor.Interceptors;
import javax.persistence.*;

import com.dsv.shared.logger.LoggerInterceptor;

@NamedQuery(name = CustomerOrderProfileAttribute.FIND_ALL_PROFILES_ATTR_NAMED_QUERY, query = "SELECT a FROM CustomerOrderProfileAttribute a")
@Entity
@Table(name = "CO_PROFILE_MAP",
	   uniqueConstraints=@UniqueConstraint(columnNames={"PROFILE_ID", "PROFILE_KEY_ID"}))
@Interceptors(LoggerInterceptor.class)
public class CustomerOrderProfileAttribute {

    public static final String FIND_ALL_PROFILES_ATTR_NAMED_QUERY = "findAllProfilesMaps";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Long id;
	
	@ManyToOne
	@JoinColumn(name="PROFILE_ID", nullable=false)
	private CustomerOrderProfile profile;
	
	@Column(name = "PROFILE_KEY_ID", nullable = false)
	Long keyId;
	
	@Column(name = "VALUE", length = 50)
	String value;
	
	@Column(name = "USAGE", length = 50)
	String usage;
	
	
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

	public CustomerOrderProfile getProfile() {
		return profile;
	}

	public void setProfile(CustomerOrderProfile profile) {
		this.profile = profile;
	}

	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
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
