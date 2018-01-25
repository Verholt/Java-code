package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.IncoTermException;
import com.dsv.road.masterdata.model.IncoTerm;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Named
@Interceptors(LoggerInterceptor.class)
public class IncoTermManager {
    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;

    public List<IncoTerm> getIncoTerms() {
        try {
            TypedQuery<IncoTerm> list = em.createNamedQuery("findAllIncoTerms", IncoTerm.class);
            List<IncoTerm> incoList = list.getResultList();
            return new ArrayList<>(incoList);
        } catch (PersistenceException e) {
            throw new IncoTermException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading IncoTerms list", e);
        }
    }

    public IncoTerm createIncoTerm(IncoTerm incoTerm) {
        try {
            em.persist(incoTerm);
            em.flush();
            return incoTerm;
        } catch (PersistenceException e) {
            throw new IncoTermException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while creating IncoTerm", e);
        }
    }

    public IncoTerm getIncoTerm(String shortAbbreviation) {
        try {
            return em.find(IncoTerm.class, shortAbbreviation);
        } catch (PersistenceException e) {
            throw new IncoTermException(ResponseErrorCode.INTERNAL_PROBLEM,
                    "Error while reading IncoTerm with shortAbbreviation: " + shortAbbreviation, e);
        }
    }

    public IncoTerm updateIncoTerm(IncoTerm incoTerm) {
        try {
            return em.merge(incoTerm);
        } catch (PersistenceException e) {
            throw new IncoTermException(ResponseErrorCode.INTERNAL_PROBLEM,
                    "Error while updating IncoTerm object", e);
        }
    }

    public boolean deleteIncoTerm(String shortAbbreviation) {
        try {
            IncoTerm p = getIncoTerm(shortAbbreviation);
            em.remove(p);
            return true;
        } catch (PersistenceException e) {
            throw new IncoTermException(ResponseErrorCode.INTERNAL_PROBLEM,
                    "Error while deleting IncoTerm with shortAbbreviation: " + shortAbbreviation, e);
        }
    }
    public boolean exists(String shortAbbreviation) {
        try {
            return getIncoTerm(shortAbbreviation) != null;

        } catch (PersistenceException e) {
            throw new IncoTermException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading ", e);
        }
    }
}
