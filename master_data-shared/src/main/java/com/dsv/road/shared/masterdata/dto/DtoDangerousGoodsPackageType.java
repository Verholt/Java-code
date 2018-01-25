package com.dsv.road.shared.masterdata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by EXT.Andreas.Froesig on 01-12-2015.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DtoDangerousGoodsPackageType {
    String shortAbbreviation;
    String typeOfPackaging;
    String remarks;
}
