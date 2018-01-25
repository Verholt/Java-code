package com.dsv.road.masterdata.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by EXT.Andreas.Froesig on 11-12-2015.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@AttributeOverrides({
    @AttributeOverride(name = "properShipmentName", column = @Column(name = "ADR_PSN")),
    @AttributeOverride(name = "classId", column = @Column(name = "ADR_CLASSIF_ID")),
    @AttributeOverride(name = "packingGroup", column = @Column(name = "ADR_PACKING_GROUP")),
    @AttributeOverride(name = "labels", column = @Column(name = "ADR_LABELS")),
    @AttributeOverride(name = "specialProvisions", column = @Column(name = "ADR_SPECIAL_PROVISIONS")),
    @AttributeOverride(name = "exceptedQuantities", column = @Column(name = "ADR_EXCEPTED_QUANTITIES")),
    @AttributeOverride(name = "dangerous", column = @Column(name = "ADR_IS_DANGEROUS")),
})
public class DangerousGoodsAdr extends DangerousGoodsDivision {
    @Column(name = "ADR_CLASSIF_CODE")
    protected String classifCode;
    @Column(name = "ADR_FACTOR")
    protected String factor;
    @Column(name = "ADR_TRANSPORT_CATEGORY")
    protected String transportCategory;
    @Column(name = "ADR_TUNNEL_CODE")
    protected String tunnelCode;

}
