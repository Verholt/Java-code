package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.exception.DangerousGoodsException;
import com.dsv.road.masterdata.jpa.DangerousGoodsManager;
import com.dsv.road.masterdata.model.*;
import com.dsv.road.shared.masterdata.dto.*;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author ext.jesper.munkholm
 *         <p/>
 *         This implements the business layer of the customer order module
 */
@Stateless
@Interceptors(LoggerInterceptor.class)
public class DangerousGoodsBean {

    @Inject
    DangerousGoodsManager dangerousGoodsManager;

    private Mapper mapper = DozerUtility.getMapper();

    public DangerousGoodsBean() {
        //added explicit constructor declaration for WebSphere
    }

    public DtoDangerousGoodsBundle getDangerousGoodsBundle(Long id) {
        if (id == null) {
            throw new DangerousGoodsException(ResponseErrorCode.WRONG_INPUT, "Cannot search for id=NULL");
        }
        DangerousGoodsBundle dangerousGoodsBundle = dangerousGoodsManager.getDangerousGoodsBundle(id);
        if (dangerousGoodsBundle == null) {
            throw new DangerousGoodsException(ResponseErrorCode.NOT_FOUND, "DangerousGoodsBundle with id [" +id+ "] not found.");
        }
        return mapper.map(dangerousGoodsBundle, DtoDangerousGoodsBundle.class);
    }

    public DtoDangerousGoodsBundle getDangerousGoodsBundleByTime(String time) {
        if (time == null) {
            throw new DangerousGoodsException(ResponseErrorCode.WRONG_INPUT, "Cannot search for id=NULL");
        }
        try {
            Date actualDate;
            if (!StringUtils.isEmpty(time)) {
                actualDate = DateUtil.parseDate(time);
            } else {
                actualDate = new Date();
            }
            DangerousGoodsBundle dangerousGoodsBundle = dangerousGoodsManager.getDangerousGoodsBundle(actualDate);
            if (dangerousGoodsBundle == null) {
                throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Retrieving DangerousGoodsBundle failed");
            }
            return mapper.map(dangerousGoodsBundle, DtoDangerousGoodsBundle.class);
        } catch (DateParseException e) {
            throw new DangerousGoodsException(ResponseErrorCode.WRONG_INPUT, "The date was not valid");
        }
    }

    public DtoDangerousGoodsBundle insertDangerousGoodsBundle(DtoDangerousGoodsBundle dtoDangerousGoodsBundle) {
        if (dtoDangerousGoodsBundle == null) {
            throw new DangerousGoodsException(ResponseErrorCode.INSERT_WITH_NULL, "Cannot insert NULL into database");
        }
        DangerousGoodsBundle dangerousGoodsBundle = mapper.map(dtoDangerousGoodsBundle, DangerousGoodsBundle.class);
        dangerousGoodsBundle.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dangerousGoodsBundle = dangerousGoodsManager.insertBundle(dangerousGoodsBundle);
        if (dangerousGoodsBundle == null) {
            throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Inserting DangerousGoodsBundle failed");
        }
        return mapper.map(dangerousGoodsBundle, DtoDangerousGoodsBundle.class);
    }

    public void deleteDangerousGoodsBundle(Long id) {
        if (id == null) {
            throw new DangerousGoodsException(ResponseErrorCode.DELETE_WITH_NULL, "Cannot delete for id=NULL");
        }
        dangerousGoodsManager.deleteDangerousGoodsBundle(id);
    }

    public void updateDangerousGoodsBundle(DtoDangerousGoodsBundle dtoDangerousGoodsBundle) {
        DangerousGoodsBundle dangerousGoodsBundle = mapper.map(dtoDangerousGoodsBundle, DangerousGoodsBundle.class);
        dangerousGoodsBundle.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        dangerousGoodsManager.updateDangerousGoodsBundle(dangerousGoodsBundle);
    }

    public DtoDangerousGoodsLite getDangerousGoodsLiteById(Long id, TransportDivision division) {
        return getDangerousGoodsLiteById(id, new TransportDivision[] {division});
    }

    public DtoDangerousGoodsLite getDangerousGoodsLiteById(Long id, TransportDivision[] divisions) {
        DangerousGoods dangerousGoods = dangerousGoodsManager.getDangerousGoodById(id);
        if (dangerousGoods == null) {
            throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Retrieving DangerousGoods failed");
        }
        return dangerousGoodsToDtoDangerousGoodsLite(dangerousGoods, divisions);
    }

    public DtoDangerousGoods getDangerousGoodsById(Long id) {
        DangerousGoods dangerousGoods = dangerousGoodsManager.getDangerousGoodById(id);
        if (dangerousGoods == null) {
            throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Retrieving DangerousGoods failed");
        }
        return mapper.map(dangerousGoods, DtoDangerousGoods.class);
    }

    public List<DtoDangerousGoodsLite> searchDangerousGoodsLite(String filter, TransportDivision division, int limit) {
        return searchDangerousGoodsLite(filter, new TransportDivision[] {division}, limit);
    }

    public List<DtoDangerousGoodsLite> searchDangerousGoodsLite(String filter, TransportDivision[] divisions, int limit) {
        List<DangerousGoods> dangerousGoods = dangerousGoodsManager.searchCurrentDangerousGoods(filter, limit);
        if (dangerousGoods == null) {
            throw new DangerousGoodsException(ResponseErrorCode.INTERNAL_PROBLEM, "Retrieving dangerousGoods failed");
        }
        List<DtoDangerousGoodsLite> dtoDangerousGoods = new ArrayList<>();
        for (DangerousGoods dangerousGood : dangerousGoods) {
            dtoDangerousGoods.add(dangerousGoodsToDtoDangerousGoodsLite(dangerousGood, divisions));
        }
        return dtoDangerousGoods;
    }

    public boolean exists(Long id) {
        return dangerousGoodsManager.exists(id);
    }

    private DtoDangerousGoodsLite dangerousGoodsToDtoDangerousGoodsLite(DangerousGoods dangerousGoods, TransportDivision[] divisions) {
        DtoDangerousGoodsLite.DtoDangerousGoodsLiteBuilder dtoDangerousGoodsLiteBuilder = DtoDangerousGoodsLite.builder();

        dtoDangerousGoodsLiteBuilder
                .id(dangerousGoods.getId())
                .addDesc(dangerousGoods.getAddDesc())
                .unNumber(dangerousGoods.getUnNumber())
                .dmgId(dangerousGoods.getDgmId()).dangerousGoodsBundleId(dangerousGoods.getDangerousGoodsBundleId());

        List<TransportDivision> divisionsList = Arrays.asList(divisions);

        if (divisionsList.contains(TransportDivision.ADR)) {
            fillBuilderWithDataFromAdr(dangerousGoods, dtoDangerousGoodsLiteBuilder);
            return dtoDangerousGoodsLiteBuilder.build();
        }

        DangerousGoodsDivision combinedDangerousGoodsDivision = new DangerousGoodsDivision();
        for (TransportDivision division : divisionsList) {
            switch (division) {
                case IATA:
                    combinedDangerousGoodsDivision.append(dangerousGoods.getIata());
                    break;
                case IMDG:
                    combinedDangerousGoodsDivision.append(dangerousGoods.getImdg());
                    break;
                default:
                    throw new DangerousGoodsException(ResponseErrorCode.UNKNOWN_DIVISION, "the given division is not a recognized target for mapping of dangerous goods");
            }
        }
        addInfoFromDivision(dtoDangerousGoodsLiteBuilder, combinedDangerousGoodsDivision);

        return dtoDangerousGoodsLiteBuilder.build();
    }

    private void fillBuilderWithDataFromAdr(DangerousGoods dangerousGoods,
                DtoDangerousGoodsLite.DtoDangerousGoodsLiteBuilder dtoDangerousGoodsLiteBuilder) {
        addInfoFromAdr(dtoDangerousGoodsLiteBuilder, dangerousGoods.getAdr());
        List<DtoDangerousGoodsText> dtoDangerousGoodsTexts = DangerousGoodsText.toDtoDangerousGoodsTexts(dangerousGoods.getDangerousGoodsTexts());
        dtoDangerousGoodsLiteBuilder.dangerousGoodsTexts(dtoDangerousGoodsTexts);
    }

    private void addInfoFromAdr(DtoDangerousGoodsLite.DtoDangerousGoodsLiteBuilder goodsLiteBuilder, DangerousGoodsAdr goodsDevision) {
        goodsLiteBuilder
                .classifCode(goodsDevision.getClassifCode())
                .factor(goodsDevision.getFactor())
                .transportCategory(goodsDevision.getTransportCategory())
                .tunnelCode(goodsDevision.getTunnelCode());

        addInfoFromDivision(goodsLiteBuilder, goodsDevision);
    }

    private void addInfoFromDivision(DtoDangerousGoodsLite.DtoDangerousGoodsLiteBuilder goodsLiteBuilder, DangerousGoodsDivision goodsDevision) {
        goodsLiteBuilder
                .properShipmentName(goodsDevision.getProperShipmentName())
                .classId(goodsDevision.getClassId())
                .packingGroup(goodsDevision.getPackingGroup())
                .labels(goodsDevision.getLabels())
                .specialProvisions(goodsDevision.getSpecialProvisions())
                .exceptedQuantities(goodsDevision.getExceptedQuantities());
    }

}