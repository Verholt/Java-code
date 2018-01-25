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
public class DtoDangerousGoodsBundle extends AbstractDto implements Serializable {
    private static final long serialVersionUID = -229236099970844768L;

    Long id;
    String validFrom;
    String validTo;
    private List<DtoDangerousGoods> dangerousGoodsList;

    public List<DtoDangerousGoods> getDangerousGoodsList() {
        if (dangerousGoodsList == null) {
            return new ArrayList<>();
        }
        return dangerousGoodsList;
    }
}
