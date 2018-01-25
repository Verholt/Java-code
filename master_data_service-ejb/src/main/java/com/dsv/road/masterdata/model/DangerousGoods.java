package com.dsv.road.masterdata.model;

import com.dsv.shared.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by EXT.Andreas.Froesig on 11-12-2015.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "DANGEROUS_GOODS")
public class DangerousGoods extends PersistableEntity {

    @Column(name = "DGM_ID")
    String dgmId;
    @Column(name = "UN_NUMBER")
    String unNumber;
    @Column(name = "ADD_DESCR")
    String addDesc;
    @Column(name = "FOR_SEARCH", length = 200)
    String forSearch;

    @Embedded//(prefix="iata")
    @AttributeOverrides({
            @AttributeOverride(name = "properShipmentName", column = @Column(name = "IATA_PSN")),
            @AttributeOverride(name = "classId", column = @Column(name = "IATA_CLASSIF_ID")),
            @AttributeOverride(name = "packingGroup", column = @Column(name = "IATA_PACKING_GROUP")),
            @AttributeOverride(name = "labels", column = @Column(name = "IATA_LABELS")),
            @AttributeOverride(name = "specialProvisions", column = @Column(name = "IATA_SPECIAL_PROVISIONS")),
            @AttributeOverride(name = "exceptedQuantities", column = @Column(name = "IATA_EXCEPTED_QUANTITIES")),
            @AttributeOverride(name = "dangerous", column = @Column(name = "IATA_IS_DANGEROUS")),
    })
    DangerousGoodsDivision iata;

    @Embedded//(prefix="imgd")
    @AttributeOverrides({
            @AttributeOverride(name = "properShipmentName", column = @Column(name = "IMDG_PSN")),
            @AttributeOverride(name = "classId", column = @Column(name = "IMDG_CLASSIF_ID")),
            @AttributeOverride(name = "packingGroup", column = @Column(name = "IMDG_PACKING_GROUP")),
            @AttributeOverride(name = "labels", column = @Column(name = "IMDG_LABELS")),
            @AttributeOverride(name = "specialProvisions", column = @Column(name = "IMDG_SPECIAL_PROVISIONS")),
            @AttributeOverride(name = "exceptedQuantities", column = @Column(name = "IMDG_EXCEPTED_QUANTITIES")),
            @AttributeOverride(name = "dangerous", column = @Column(name = "IMDG_IS_DANGEROUS")),
    })
    DangerousGoodsDivision imdg;

    @Embedded//(prefix="adr")
    DangerousGoodsAdr adr;
    
    @Column(name = "DANGEROUS_GOODS_BUNDLE_ID")
    Long dangerousGoodsBundleId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = DangerousGoodsText.class)
    @JoinColumn(name = "DANGEROUS_GOODS_ID")
    List<DangerousGoodsText> dangerousGoodsTexts;

    @PrePersist
    public void updateSearchColumn() {
        if (this.addDesc == null) {
            forSearch = String.valueOf(unNumber);
        } else {
            forSearch = String.valueOf(unNumber) + ' ' + ((addDesc.length()<195)?addDesc.toUpperCase():addDesc.substring(1,195)).toUpperCase();
        }


    }
}
