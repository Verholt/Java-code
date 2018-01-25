package com.dsv.road.shared.masterdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * Created by EXT.Andreas.Froesig on 11-12-2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class DtoDangerousGoodsDivision {
    @Column(name = "PSN")
    protected String properShipmentName;
    @Column(name = "CLASSIF_ID")
    protected String classId;
    @Column(name = "PACKING_GROUP")
    protected String packingGroup;
    @Column(name = "LABELS")
    protected String labels;
    @Column(name = "SPECIAL_PROVISIONS")
    protected String specialProvisions;
    @Column(name = "EXCEPTED_QUANTITIES")
    protected String exceptedQuantities;
    @Column(name = "IS_DANGEROUS")
    protected boolean dangerous;

}
