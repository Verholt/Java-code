package com.dsv.road.masterdata.model;

import java.sql.Timestamp;
import java.util.List;

import javax.interceptor.Interceptors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.dsv.shared.logger.LoggerInterceptor;

@Entity
@NamedQueries({
	@NamedQuery(name = CustomerOrderProfile.FIND_ALL_PROFILES_NAMED_QUERY,
            query = "SELECT b FROM CustomerOrderProfile b"),
	@NamedQuery(name = CustomerOrderProfile.SEARCH_PROFILES_NAMED_QUERY,
            query = "SELECT b FROM CustomerOrderProfile b WHERE  UPPER(b.name) LIKE :" + CustomerOrderProfile.FILTER_QUERY_PARAM)
})
@Table(name = "CO_PROFILE")
@Interceptors(LoggerInterceptor.class)
public class CustomerOrderProfile {

    public static final String FIND_ALL_PROFILES_NAMED_QUERY = "findAllProfiles";
    public static final String SEARCH_PROFILES_NAMED_QUERY = "searchProfiles";
    public static final String FILTER_QUERY_PARAM = "filter";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "profile")
	private List<CustomerOrderProfileAttribute> attributes;
	
	@Column(name = "NAME", nullable = false, length = 50, unique = true)
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = false, length = 1000)
	private String description;
	
	@Column(name = "CREATED_AT", nullable = false)
	private Timestamp createdAt;

	@Column(name = "CREATED_BY", length = 50, nullable = false)
	private String createdBy;

	@Column(name = "UPDATED_AT")
	private Timestamp updatedAt;

	@Column(name = "UPDATED_BY", length = 50)
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public List<CustomerOrderProfileAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CustomerOrderProfileAttribute> keyValues) {
		this.attributes = keyValues;
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
