package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.DangerousGoodsPackageTypeException;
import com.dsv.road.masterdata.model.DangerousGoodsPackageType;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@Interceptors(LoggerInterceptor.class)
public class DangerousGoodsPackageTypeManager {

    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;

    public List<DangerousGoodsPackageType> findAll() {
        try {
            TypedQuery<DangerousGoodsPackageType> list = em.createNamedQuery(DangerousGoodsPackageType.FIND_ALL, DangerousGoodsPackageType.class);
            return list.getResultList();
        } catch (PersistenceException e) {
            throw new DangerousGoodsPackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while retrieving  for DangerousGoodsPackageType", e);
        }
    }

    public DangerousGoodsPackageType create(DangerousGoodsPackageType dangerousGoodsPackageType) {
        try {
            em.persist(dangerousGoodsPackageType);
            em.flush();
        } catch (PersistenceException e) {
            throw new DangerousGoodsPackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while creating DangerousGoodsPackageType", e);
        }
        return dangerousGoodsPackageType;
    }

    public DangerousGoodsPackageType getById(String id) {
        try {
            return em.find(DangerousGoodsPackageType.class, id);
        } catch (PersistenceException e) {
            throw new DangerousGoodsPackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding DangerousGoodsPackageType with id:", e);
        }
    }

    public DangerousGoodsPackageType update(DangerousGoodsPackageType dangerousGoodsPackageType) {
        try {
            return em.merge(dangerousGoodsPackageType);
        } catch (PersistenceException e) {
            throw new DangerousGoodsPackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating DangerousGoodsPackageType", e);
        }
    }

    public boolean exists(String id) {
        try {
            return getById(id) != null;

        } catch (PersistenceException e) {
            throw new DangerousGoodsPackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading DangerousGoodsPackageType", e);
        }
    }

    public boolean delete(String id) {
        try {
            DangerousGoodsPackageType p = getById(id);
            em.remove(p);
            return true;
        } catch (PersistenceException e) {
            throw new DangerousGoodsPackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while deleting DangerousGoodsPackageType", e);
        }
    }


}
