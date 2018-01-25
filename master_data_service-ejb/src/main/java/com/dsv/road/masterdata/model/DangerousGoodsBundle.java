package com.dsv.road.masterdata.model;

import com.dsv.shared.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "DANGEROUS_GOODS_BUNDLE")
@NamedQueries({
        @NamedQuery(name = DangerousGoodsBundle.GET_BUNDLE_AT, query = "SELECT b FROM DangerousGoodsBundle b WHERE b.validFrom<:time AND (:time<b.validTo OR b.validTo IS NULL)"),
        @NamedQuery(name = DangerousGoodsBundle.FIND_BY_UN_NUMBER, query = "SELECT h FROM DangerousGoodsBundle b JOIN b.dangerousGoodsList h WHERE  h.unNumber=:unNumber AND b.validFrom<CURRENT_TIMESTAMP AND (CURRENT_TIMESTAMP<b.validTo OR b.validTo IS NULL)"),
        @NamedQuery(name = DangerousGoodsBundle.UPDATE_CURRENT_BUNDLE_TO_INACTIVE, query = "UPDATE DangerousGoodsBundle u SET u.validTo = :validTo WHERE u.validTo IS NULL"),
        @NamedQuery(name = DangerousGoodsBundle.UPDATE_PREVIOUS_BUNDLE_TO_ACTIVE, query = "UPDATE DangerousGoodsBundle u SET u.validTo = NULL WHERE u.validTo = :validTo"),
        @NamedQuery(name = DangerousGoodsBundle.SEARCH_IN_CURRENT_DANGEROUS_GOODS, query = "SELECT j FROM DangerousGoods j WHERE j.dangerousGoodsBundleId in (SELECT i FROM DangerousGoodsBundle i  WHERE i.validFrom<CURRENT_TIMESTAMP AND (CURRENT_TIMESTAMP<i.validTo OR i.validTo IS NULL)) AND (j.forSearch LIKE :filter)"), })

public class DangerousGoodsBundle extends PersistableEntity {
    private static final long serialVersionUID = 1L;
    public static final String GET_BUNDLE_AT = "getDGBundleAt";
    public static final String FIND_BY_UN_NUMBER = "findDGByUnNumber";
    public static final String UPDATE_CURRENT_BUNDLE_TO_INACTIVE = "updateCurrentDGBundleToInactive";
    public static final String UPDATE_PREVIOUS_BUNDLE_TO_ACTIVE = "updatePreviousDGBundleToActive";
    public static final String SEARCH_IN_CURRENT_DANGEROUS_GOODS = "searchInCurrentDangerousGoods";


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_FROM", nullable = false)
    Date validFrom;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_TO")
    Date validTo;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = DangerousGoods.class)
    @JoinColumn(name = "DANGEROUS_GOODS_BUNDLE_ID")
    private List<DangerousGoods> dangerousGoodsList;
}
