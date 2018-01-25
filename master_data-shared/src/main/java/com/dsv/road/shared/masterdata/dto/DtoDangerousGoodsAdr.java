package com.dsv.road.shared.masterdata.dto;

import lombok.*;

import javax.persistence.Column;

/**
 * Created by EXT.Andreas.Froesig on 11-12-2015.
 */
@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class DtoDangerousGoodsAdr extends DtoDangerousGoodsDivision {
    @Column(name = "CLASSIF_CODE")
    private String classifCode;
    @Column(name = "FACTOR")
    private String factor;
    @Column(name = "TRANSPORT_CATEGORY")
    private String transportCategory;
    @Column(name = "TUNNEL_CODE")
    private String tunnelCode;
}
