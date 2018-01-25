package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.ReferenceTypeException;
import com.dsv.road.masterdata.model.ReferenceType;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Named
@Interceptors(LoggerInterceptor.class)
public class ReferenceTypeManager {

    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;


    public List<ReferenceType> getReferenceTypes() {
        try {
            TypedQuery<ReferenceType> list = em.createNamedQuery("findAllReferenceTypes", ReferenceType.class);
            List<ReferenceType> referenceTypes = list.getResultList();
            return new ArrayList<>(referenceTypes);
        } catch (PersistenceException e) {
            throw new ReferenceTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding all reference types", e);
        }
    }

    public List<ReferenceType> getDefaultReferenceTypes() {
        try {
            TypedQuery<ReferenceType> list = em.createNamedQuery("findDefaultReferenceTypes", ReferenceType.class);
            List<ReferenceType> referenceTypes = list.getResultList();
            return new ArrayList<>(referenceTypes);
        } catch (PersistenceException e) {
            throw new ReferenceTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding default reference types", e);
        }
    }

    public ReferenceType createReferenceType(ReferenceType referenceType) {
        try {
            em.persist(referenceType);
            em.flush();
        } catch (PersistenceException e) {
            throw new ReferenceTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while creating reference types", e);
        }
        return referenceType;
    }

    public ReferenceType getReferenceType(Long id) {
        try {
            return em.find(ReferenceType.class, id);
        } catch (PersistenceException e) {
            throw new ReferenceTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding reference type with id: " + id, e);
        }
    }

    public ReferenceType updateReferenceType(ReferenceType referenceType) {
        try {
            ReferenceType updated = em.merge(referenceType);
            em.flush();
            return updated;
        } catch (PersistenceException e) {
            throw new ReferenceTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating reference type", e);
        }
    }

    public boolean deleteReferenceType(Long id) {
        try {
        	ReferenceType rt = getReferenceType(id);
            em.remove(rt);
            return true;
        } catch (PersistenceException e) {
            throw new ReferenceTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting reference type with id: " + id, e);
        }
    }

    public boolean exists(Long id) {
        try {
            return getReferenceType(id) != null;
        } catch (PersistenceException e) {
            throw new ReferenceTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading ", e);
        }
    }
}
