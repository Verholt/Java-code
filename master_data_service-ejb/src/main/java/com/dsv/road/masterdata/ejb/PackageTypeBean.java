package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.jpa.PackageTypeManager;
import com.dsv.road.masterdata.model.PackageType;
import com.dsv.road.shared.masterdata.dto.DtoPackageType;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ext.jesper.munkholm
 *         <p/>
 *         This implements the business layer of the customer order module
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class PackageTypeBean {

    @Inject
    PackageTypeManager packageTypeManager;

    Mapper mapper = DozerUtility.getMapper();

    public List<DtoPackageType> getPackageTypes() {
        List<PackageType> packageTypes = packageTypeManager.getPackageTypes();
        List<DtoPackageType> dtoPackageTypes = new ArrayList<>();
        for (PackageType p : packageTypes) {
            dtoPackageTypes.add(mapper.map(p, DtoPackageType.class));
        }
        return dtoPackageTypes;
    }

    public DtoPackageType getPackageType(String shortAbbreviation) {
        PackageType packageType = packageTypeManager.getPackageType(shortAbbreviation);
        if (packageType != null) {
            return mapper.map(packageType, DtoPackageType.class);
        } else {
            return null;
        }
    }

    public DtoPackageType createPackageType(DtoPackageType dtoPackageType) {
        PackageType p = mapper.map(dtoPackageType, PackageType.class);
        p.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        p.setCreatedBy("Manually created");
        p = packageTypeManager.createPackageType(p);
        return mapper.map(p, DtoPackageType.class);
    }

    public boolean deletePackageType(String shortAbbreviation) {
        return packageTypeManager.deletePackageType(shortAbbreviation);
    }

    public DtoPackageType updatePackageType(DtoPackageType newPackageType) {
        PackageType p = mapper.map(newPackageType, PackageType.class);
        p.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        p.setCreatedBy("Manually updated");
        p = packageTypeManager.updatePackageType(p);
        return mapper.map(p, DtoPackageType.class);
    }
    public boolean exists(String shortAbbreviation) {
        return packageTypeManager.exists(shortAbbreviation);
    }

}