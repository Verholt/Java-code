package com.dsv.road.masterdata.jpa;

import com.dsv.road.masterdata.exception.PackageTypeException;
import com.dsv.road.masterdata.model.PackageType;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.logger.LoggerInterceptor;

import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Named
@Interceptors(LoggerInterceptor.class)
public class PackageTypeManager {
    @PersistenceContext(unitName = PersistenceConstants.MASTERDATA_UNIT_NAME)
    private EntityManager em;

    public List<PackageType> getPackageTypes() {
        try {
            TypedQuery<PackageType> list = em.createNamedQuery("findAllPackageTypes", PackageType.class);
            List<PackageType> packageTypes = list.getResultList();
            return new ArrayList<>(packageTypes);
        } catch (PersistenceException e) {
            throw new PackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while finding list of all package types", e);
        }
    }

    public PackageType createPackageType(PackageType packageType) {
        try {
            em.persist(packageType);
            em.flush();
        } catch (PersistenceException e) {
            throw new PackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while creating package type", e);
        }
        return packageType;
    }

    public PackageType getPackageType(String shortAbbreviation) {
        try {
            return em.find(PackageType.class, shortAbbreviation);
        } catch (PersistenceException e) {
            throw new PackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM,
                    "Error while getting package type with shortAbbreviation: " + shortAbbreviation, e);
        }
    }

    public PackageType updatePackageType(PackageType packageType) {
        try {
            return em.merge(packageType);
        } catch (PersistenceException e) {
            throw new PackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while updating package type", e);
        }
    }

    public boolean deletePackageType(String shortAbbreviation) {
        try {
            PackageType p = getPackageType(shortAbbreviation);
            em.remove(p);
            return true;
        } catch (PersistenceException e) {
            throw new PackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM,
                    "Error while deleting package type wiht shortAbbreviation: " + shortAbbreviation, e);
        }
    }

    public boolean exists(String shortAbbreviation) {
        try {
            return getPackageType(shortAbbreviation) != null;

        } catch (PersistenceException e) {
            throw new PackageTypeException(ResponseErrorCode.INTERNAL_PROBLEM, "Error while reading", e);
        }
    }
}
