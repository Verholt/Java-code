package com.dsv.road.masterdata.model;

import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.interceptor.Interceptors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROLE")
@NamedQuery(name = "searchRoles", query = "SELECT j FROM Role j WHERE UPPER(j.role) LIKE :filter")
@Interceptors(LoggerInterceptor.class)
public class Role extends PersistableEntity {
	@Column(name = "ROLE")
	String role;
}
