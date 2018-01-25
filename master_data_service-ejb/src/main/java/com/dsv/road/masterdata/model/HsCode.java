package com.dsv.road.masterdata.model;

import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = HsCode.FIND_ALL_HS_CODES_QUERY, query = "SELECT b FROM HsCode b")
@Table(name = "HS_CODE")
@Interceptors(LoggerInterceptor.class)
public class HsCode  extends PersistableEntity {

    public static final String FIND_ALL_HS_CODES_QUERY = "findAllHsCodes";

    @Column(name = "HS_CODE", length = 12)
    String hsCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_FROM")
    Date validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_TO")
    Date validTo;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = HsCodeText.class)
    @JoinColumn(name = "HS_CODE", referencedColumnName = "HS_CODE")
    private List<HsCodeText> hsCodeTextObjects;
}
