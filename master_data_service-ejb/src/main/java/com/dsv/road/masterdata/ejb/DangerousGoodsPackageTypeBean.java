package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.jpa.DangerousGoodsPackageTypeManager;
import com.dsv.road.masterdata.model.DangerousGoodsPackageType;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoodsPackageType;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import com.google.common.collect.Lists;
import org.dozer.Mapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXT.Andreas.Froesig on 16-11-2015.
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class DangerousGoodsPackageTypeBean {

    @EJB
    private DangerousGoodsPackageTypeManager dangerousGoodsPackageTypeManager;
    private Mapper mapper = DozerUtility.getMapper();

    public List<DtoDangerousGoodsPackageType> readAll() {
        List<DtoDangerousGoodsPackageType> dtoDangerousGoodsPackageTypes = Lists.newArrayList();
        List<DangerousGoodsPackageType> dangerousGoodsPackageTypes = dangerousGoodsPackageTypeManager.findAll();
        for (DangerousGoodsPackageType dangerousGoodsPackageType : dangerousGoodsPackageTypes) {
            dtoDangerousGoodsPackageTypes.add(mapper.map(dangerousGoodsPackageType, DtoDangerousGoodsPackageType.class));
        }
        return dtoDangerousGoodsPackageTypes;
    }

    public DtoDangerousGoodsPackageType create(DtoDangerousGoodsPackageType dtoDangerousGoodsPackageType)  {
        DangerousGoodsPackageType dangerousGoodsPackageType = mapper.map(dtoDangerousGoodsPackageType, DangerousGoodsPackageType.class);
        dangerousGoodsPackageType = dangerousGoodsPackageTypeManager.create(dangerousGoodsPackageType);
        return mapper.map(dangerousGoodsPackageType, DtoDangerousGoodsPackageType.class);
    }

    public DtoDangerousGoodsPackageType read(String id) {
        DangerousGoodsPackageType dangerousGoodsPackageType = dangerousGoodsPackageTypeManager.getById(id);
        if (dangerousGoodsPackageType == null) {
            return null;
        }
        return mapper.map(dangerousGoodsPackageType, DtoDangerousGoodsPackageType.class);
    }

    public DtoDangerousGoodsPackageType update(DtoDangerousGoodsPackageType dtoShipment) {
        DangerousGoodsPackageType dangerousGoodsPackageType = mapper.map(dtoShipment, DangerousGoodsPackageType.class);
        dangerousGoodsPackageType = dangerousGoodsPackageTypeManager.update(dangerousGoodsPackageType);
        return mapper.map(dangerousGoodsPackageType, DtoDangerousGoodsPackageType.class);
    }

    public boolean exists(String id)  {
        return dangerousGoodsPackageTypeManager.exists(id);
    }

    public void delete(String id) {
        dangerousGoodsPackageTypeManager.delete(id);
    }
}