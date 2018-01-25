package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.RoleException;
import com.dsv.road.masterdata.model.Role;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.ws.rs.HEAD;
import java.util.ArrayList;
import java.util.List;

@Named
@Interceptors(LoggerInterceptor.class)
public class RoleManager {
	@PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
	private EntityManager em;

	public List<Role> searchRoles(String filter, int limit) {
		try {
			String preparedFilter = prepareForSearch(filter);
			TypedQuery<Role> list = em
					.createNamedQuery("searchRoles", Role.class)
					.setParameter("filter", preparedFilter)
					.setMaxResults(limit);
			List<Role> roleList = list.getResultList();
			return new ArrayList<>(roleList);
		} catch (PersistenceException e) {
			throw new RoleException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while searching for roles", e);
		}
	}

	public Role instertRole(Role role) {
		try {
			em.persist(role);
			return role;
		} catch (PersistenceException e) {
			throw new RoleException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while inserting roles", e);
		}
	}

	public Role findRole(Long id) {
		try {
			return em.find(Role.class, id);
		} catch (PersistenceException e) {
			throw new RoleException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding role with id: " + id, e);
		}
	}

	public Role updateRole(Role role) {
		try {
			em.merge(role);
			return role;
		} catch (PersistenceException e) {
			throw new RoleException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating role", e);
		}
	}

	public Role deleteRole(Role role) {
		try {
			em.remove(role);
			return role;
		} catch (PersistenceException e) {
			throw new RoleException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting role", e);
		}
	}

	public Role deleteRole(Long id) {
		try {
			Role role = findRole(id);
			deleteRole(role);
			return role;
		} catch (PersistenceException e) {
			throw new RoleException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting role by id: " + id, e);
		}
	}

	private String prepareForSearch(String filter) {
		return filter.toUpperCase().replace(' ', '%') + "%";
	}


	public boolean exists(Long id) {
		try {
			return findRole(id) != null;

		} catch (PersistenceException e) {
			throw new RoleException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading DangerousGoodsPackageType", e);
		}
	}
}
