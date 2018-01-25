package com.dsv.road.shared.masterdata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoDangerousGoodsLite extends AbstractDto implements Serializable {

    private static final long serialVersionUID = 1L;

    Long id;
    String dmgId;
    String unNumber;
    String addDesc;
    String properShipmentName;
    String classId;
    String classifCode;
    String packingGroup;
    String labels;
    String specialProvisions;
    String tunnelCode;
    String exceptedQuantities;
    String factor;
    String transportCategory;
    Long dangerousGoodsBundleId;

    private List<DtoDangerousGoodsText> dangerousGoodsTexts;

    public List<DtoDangerousGoodsText> getDangerousGoodsTexts() {
        if (dangerousGoodsTexts == null) {
            dangerousGoodsTexts = new ArrayList<>();
        }
        return dangerousGoodsTexts;
    }

}
