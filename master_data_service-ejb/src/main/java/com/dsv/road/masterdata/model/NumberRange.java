package com.dsv.road.masterdata.model;

import com.dsv.road.shared.masterdata.dto.ActiveState;
import com.dsv.road.shared.masterdata.dto.CheckDigitMethodType;
import com.dsv.road.shared.masterdata.dto.constraints.NumberCollectionConstraints;
import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NUMBER_RANGE")
@Interceptors(LoggerInterceptor.class)
@NamedQueries({
        @NamedQuery(name = NumberRange.GET_RANGES_FOR_COLLECTION, query = "SELECT b FROM NumberRange b WHERE b.numberCollectionId=:"+NumberRange.ID_PARAM),
})
public class NumberRange extends PersistableEntity {
    public static final String ID_PARAM = "id";
    public static final String GET_RANGES_FOR_COLLECTION = "GET_RANGES_FOR_COLLECTION";
    @Column(name = "RANGE_NO")
    private int rangeNo;
    @Column(name = "START_VALUE")
    private Long startValue;
    @Column(name = "MIN_VALUE")
    private Long minValue;
    @Column(name = "MAX_VALUE")
    private Long maxValue;
    @Column(name = "RECENT_VALUE")
    private Long recentValue;
    @Column(name = "STEP")
    private int step;
    @Column(name = "CYCLE")
    private int cycle;
    @Column(name = "UNBROKEN")
    private int unbroken;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_FROM")
    private Date validFrom;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_TO")
    private Date validTo;
    @Column(name = "OUTPUT_FORMAT",length = NumberCollectionConstraints.NUMBER_RANGE_OUTPUT_FORMAT_LENGTH)
    private String outputFormat;
    @Column(name = "STATE",length = NumberCollectionConstraints.NUMBER_RANGE_STATE_LENGTH)
    private ActiveState state;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ASSIGNED")
    private Date lastAssigned;
    @Column(name = "NUMBER_COLLECTION_ID")
    private Long numberCollectionId;
    @Enumerated(EnumType.STRING)
    @Column(name = "CHECK_DIGIT_METHOD_TYPE")
    private CheckDigitMethodType checkDigitMethodType;
}
