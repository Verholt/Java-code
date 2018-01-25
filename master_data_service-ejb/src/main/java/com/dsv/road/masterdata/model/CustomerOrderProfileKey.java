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
@NamedQueries({
	@NamedQuery(name = CustomerOrderProfileKey.FIND_ALL_PROFILE_KEYS_NAMED_QUERY, query = "SELECT b FROM CustomerOrderProfileKey b"),
	@NamedQuery(name = CustomerOrderProfileKey.SEARCH_PROFILE_KEYS_NAMED_QUERY, query = "SELECT b FROM CustomerOrderProfileKey b WHERE UPPER(b.key) LIKE :" + CustomerOrderProfileKey.FILTER_QUERY_PARAM)
})
@Table(name = "CO_PROFILE_KEY")
@Interceptors(LoggerInterceptor.class)
public class CustomerOrderProfileKey {

	public static final String FIND_ALL_PROFILE_KEYS_NAMED_QUERY = "findAllProfileKeys";
	public static final String FILTER_QUERY_PARAM = "filter";
	public static final String SEARCH_PROFILE_KEYS_NAMED_QUERY = "searchProfileKeys";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Long id;
	
	@Column(name = "KEY", nullable = false, length = 50, unique = true)
	String key;
	
	@Column(name = "DESCRIPTION", nullable = false, length = 1000)
	String description;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
