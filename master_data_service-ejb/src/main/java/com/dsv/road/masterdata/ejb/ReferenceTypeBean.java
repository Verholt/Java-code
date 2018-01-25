package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.jpa.ReferenceTypeManager;
import com.dsv.road.masterdata.model.ReferenceType;
import com.dsv.road.shared.masterdata.dto.DtoReferenceType;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Interceptors(LoggerInterceptor.class)
public class ReferenceTypeBean {

    @Inject
    ReferenceTypeManager referenceTypeManager;

    Mapper mapper = DozerUtility.getMapper();


    public List<DtoReferenceType> getReferenceTypes() {
        List<ReferenceType> packageTypes = referenceTypeManager.getReferenceTypes();
        return toDto(packageTypes);
    }

    public List<DtoReferenceType> getDefaultReferenceTypes() {
        List<ReferenceType> packageTypes = referenceTypeManager.getDefaultReferenceTypes();
        return toDto(packageTypes);
    }

	private List<DtoReferenceType> toDto(List<ReferenceType> packageTypes) {
		List<DtoReferenceType> dtoReferenceTypes = new ArrayList<>(packageTypes.size());
        for(ReferenceType p : packageTypes){
            dtoReferenceTypes.add(mapper.map(p, DtoReferenceType.class));
        }
        return dtoReferenceTypes;
	}
    
    public DtoReferenceType getReferenceType(Long id) {
        ReferenceType packageType = referenceTypeManager.getReferenceType(id);
        if (packageType != null) {
            return mapper.map(packageType, DtoReferenceType.class);
        } else {
            return null;
        }
    }

    public DtoReferenceType createReferenceType(DtoReferenceType dtoReferenceType) {
        ReferenceType p = mapper.map(dtoReferenceType, ReferenceType.class);
        p.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        p.setCreatedBy("Manually created");
        p = referenceTypeManager.createReferenceType(p);
        return mapper.map(p, DtoReferenceType.class);
    }

    public boolean deleteReferenceType(Long id) {
        return referenceTypeManager.deleteReferenceType(id);
    }

    public DtoReferenceType updateReferenceType(DtoReferenceType newReferenceType) {
        ReferenceType p = mapper.map(newReferenceType, ReferenceType.class);
        p.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        p.setUpdatedBy("Manually updated");
        p = referenceTypeManager.updateReferenceType(p);
        return mapper.map(p, DtoReferenceType.class);
    }
    public boolean exists(Long id) {
        return referenceTypeManager.exists(id);
    }
}