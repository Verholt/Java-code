package com.dsv.road.masterdata.model;


import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.persistence.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.interceptor.Interceptors;
import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DANGEROUS_GOODS_PACKAGE_TYPE")
@NamedQuery(name = DangerousGoodsPackageType.FIND_ALL, query = "SELECT i FROM DangerousGoodsPackageType i")
@Interceptors(LoggerInterceptor.class)
public class DangerousGoodsPackageType extends AuditableEntity {

    public static final String FIND_ALL = "findAllDangerousGoodsPackageTypes";

    @Id
    @Column(name = "SHORT_ABBREVIATION", length = 5)
    String shortAbbreviation;

    @Column(name = "TYPE_OF_PACKAGING")
    String typeOfPackaging;

    @Column(name = "REMARKS")
    String remarks;
}
