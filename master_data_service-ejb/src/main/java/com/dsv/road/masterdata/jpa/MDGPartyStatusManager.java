package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.MDGPartyStatusException;
import com.dsv.road.masterdata.model.MDGPartyStatus;
import com.dsv.road.masterdata.model.MDGPartyStatusPrimaryKey;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;

@Named
@Interceptors(LoggerInterceptor.class)
public class MDGPartyStatusManager {
	@PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
	private EntityManager em;


	public MDGPartyStatus createMDGPartyStatus(MDGPartyStatus mDGPartyStatus) {
		try {
			MDGPartyStatus status = getMDGPartyStatus(
					mDGPartyStatus.getStatusCode(),
					mDGPartyStatus.getLocality());
			if (status == null) {
				em.persist(mDGPartyStatus);
				em.flush();
			} else {
				em.merge(mDGPartyStatus);
			}
			return mDGPartyStatus;
		} catch (PersistenceException e) {
			throw new MDGPartyStatusException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while creating MDGPartyStatus", e);
		}
	}

	public MDGPartyStatus getMDGPartyStatus(String statusCode, String locality) {
		try {
			MDGPartyStatusPrimaryKey key = new MDGPartyStatusPrimaryKey();
			key.setStatusCode(statusCode);
			key.setLocality(locality);
			return em.find(MDGPartyStatus.class, key);
		} catch (PersistenceException e) {
			throw new MDGPartyStatusException(ResponseErrorCode.INTERNAL_PROBLEM,
					"Error while finding MDGPartyStatus with code: " + statusCode + " and locality: " + locality, e);
		}
	}

	public MDGPartyStatus updateMDGPartyStatus(MDGPartyStatus mDGPartyStatus) {
		try {
			return em.merge(mDGPartyStatus);
		} catch (PersistenceException e) {
			throw new MDGPartyStatusException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating MDGPartyStatus", e);
		}
	}

	public boolean deleteMDGPartyStatus(String statusCode, String locality) {
		try {
			MDGPartyStatus p = getMDGPartyStatus(statusCode, locality);
			em.remove(p);
		} catch (PersistenceException e) {
			throw new MDGPartyStatusException(ResponseErrorCode.INTERNAL_PROBLEM,
					"Error while deleting MDGPartyStatus with code: " + statusCode + " and locality: " + locality, e);
		}
		return true;
	}
}
