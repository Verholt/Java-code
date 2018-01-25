package com.dsv.road.shared.masterdata.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoDangerousGoods extends AbstractDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String dgmId;
    private String unNumber;
    private String addDesc;
    private Long dangerousGoodsBundleId;
    private DtoDangerousGoodsDivision iata;
    private DtoDangerousGoodsDivision imdg;
    private DtoDangerousGoodsAdr adr;
    private List<DtoDangerousGoodsText> dangerousGoodsTexts;

    public List<DtoDangerousGoodsText> getDangerousGoodsTexts() {
        if (dangerousGoodsTexts == null) {
            dangerousGoodsTexts = new ArrayList<>();
        }
        return dangerousGoodsTexts;
    }

}
