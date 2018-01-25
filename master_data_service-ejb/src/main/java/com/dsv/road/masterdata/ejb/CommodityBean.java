package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.jpa.CommodityManager;
import com.dsv.road.masterdata.model.HsCode;
import com.dsv.road.masterdata.model.HsCodeText;
import com.dsv.road.shared.masterdata.dto.DtoHsCode;
import com.dsv.road.shared.masterdata.dto.DtoHsCodeText;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import com.google.common.collect.Lists;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXT.K.Theilgaard on 19-05-2015.
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class CommodityBean {
    @Inject
    CommodityManager commodityManager;
    private Mapper mapper = DozerUtility.getMapper();

    public List<DtoHsCode> getAllHsCodes() {
        List<HsCode> hsCodes = commodityManager.getHsCodes();
        List<DtoHsCode> dtoHsCodes = Lists.newArrayList();
        for (HsCode HsCode : hsCodes) {
            dtoHsCodes.add(mapper.map(HsCode, DtoHsCode.class));
        }
        return dtoHsCodes;
    }

    public DtoHsCode getHsCode(long id) {
        HsCode hsCode = commodityManager.getHsCode(id);
        if (hsCode == null) {
            return null;
        }
        return mapper.map(hsCode, DtoHsCode.class);
    }

    public DtoHsCodeText getHsCodeText(long id)  {
        HsCodeText hsCodeText = commodityManager.getHsCodeText(id);
        if(hsCodeText == null){
            return null;
        }
        return mapper.map(hsCodeText, DtoHsCodeText.class);
    }

    public List<DtoHsCodeText> getAllHsCodeTexts() {
        List<HsCodeText> hsCodeTexts = commodityManager.getHsCodeTexts();
        List<DtoHsCodeText> dtoHsCodeTexts = Lists.newArrayList();
        for (HsCodeText HsCodeText : hsCodeTexts) {
            dtoHsCodeTexts.add(mapper.map(HsCodeText, DtoHsCodeText.class));
        }
        return dtoHsCodeTexts;
    }

    public DtoHsCode insertHsCode(DtoHsCode dtoHsCode) {
        HsCode hsCode = commodityManager.insertHsCode(mapper.map(dtoHsCode, HsCode.class));
        return mapper.map(hsCode, DtoHsCode.class);
    }

    public DtoHsCodeText insertHsCodeText(DtoHsCodeText dtoHsCodeText) {
        HsCodeText hsCodeText = commodityManager.insertHsCodeText(mapper.map(dtoHsCodeText, HsCodeText.class));
        return mapper.map(hsCodeText, DtoHsCodeText.class);
    }

    public List<DtoHsCode> insertHsCodes(List<DtoHsCode> dtoHsCodes) {
        List<HsCode> hsCodes = Lists.newArrayList();
        for (DtoHsCode dtoHsCode : dtoHsCodes) {
            hsCodes.add(mapper.map(dtoHsCode, HsCode.class));
        }
        List<HsCode> insertedHsCodes = commodityManager.insertHsCodes(hsCodes);
        List<DtoHsCode> insertedDtoHsCodes = Lists.newArrayList();
        for(HsCode hsCode : insertedHsCodes){
            insertedDtoHsCodes.add(mapper.map(hsCode, DtoHsCode.class));
        }
        return insertedDtoHsCodes;
    }

    public List<DtoHsCodeText> insertHsCodeTexts(List<DtoHsCodeText> dtoHsCodeTexts) {
        ArrayList<HsCodeText> hsCodeTexts = Lists.newArrayList();
        for (DtoHsCodeText dtoHsCodeText : dtoHsCodeTexts) {
            hsCodeTexts.add(mapper.map(dtoHsCodeText, HsCodeText.class));
        }
        List<HsCodeText> insertedHsCodeTexts = commodityManager.insertHsCodeTexts(hsCodeTexts);
        List<DtoHsCodeText> insertedDtoHsCodeTexts = Lists.newArrayList();
        for (HsCodeText hsCodeText : insertedHsCodeTexts){
            insertedDtoHsCodeTexts.add(mapper.map(hsCodeText, DtoHsCodeText.class));
        }
        return insertedDtoHsCodeTexts;
    }

    public DtoHsCode updateHsCode(long id, DtoHsCode dtoHsCode) {
        HsCode hsCode = commodityManager.updateHsCode(id, mapper.map(dtoHsCode, HsCode.class));
        return mapper.map(hsCode, DtoHsCode.class);
    }

    public HsCodeText updateHsCodeText(long id, DtoHsCodeText dtoHsCodeText) {
        HsCodeText hsCodeText = commodityManager.updateHsCodeText(id, mapper.map(dtoHsCodeText, HsCodeText.class));
        return mapper.map(hsCodeText, HsCodeText.class);
    }

    public void deleteHsCode(long id) {
        commodityManager.deleteHsCode(id);
    }

    public void deleteHsCodeText(long id) {
        commodityManager.deleteHsCodeText(id);
    }

    public List<DtoHsCodeText> searchHsCodeText(String filter, int limit) {
        List<HsCodeText> hsCodeTexts = commodityManager.searchHsCodeTexts(filter, limit);
        List<DtoHsCodeText> dtoHsCodeTexts = Lists.newArrayList();
        for(HsCodeText hsCodeText : hsCodeTexts){
            dtoHsCodeTexts.add(mapper.map(hsCodeText, DtoHsCodeText.class));
        }
        return dtoHsCodeTexts;
    }

    public List<DtoHsCodeText> searchHsCodeText(String filter, String language, int limit) {
        List<HsCodeText> hsCodeTexts = commodityManager.searchHsCodeTexts(filter, language, limit);
        List<DtoHsCodeText> dtoHsCodeTexts = Lists.newArrayList();
        for(HsCodeText HsCodeText : hsCodeTexts){
            dtoHsCodeTexts.add(mapper.map(HsCodeText, DtoHsCodeText.class));
        }
        return dtoHsCodeTexts;
    }
}
