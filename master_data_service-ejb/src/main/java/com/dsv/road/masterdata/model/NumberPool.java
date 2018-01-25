package com.dsv.road.masterdata.model;

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
@Table(name = "NUMBER_POOL")
@Interceptors(LoggerInterceptor.class)
public class NumberPool extends PersistableEntity {
    @Column(name = "NUMBER")
    private Long number;
    @Column(name = "NUMBER_FORMATTED",length = NumberCollectionConstraints.NUMBER_POOL_NUMBER_FORMATTED_LENGTH)
    private String numberFormatted;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ASSIGNED")
    private Date assigned;
    @Column(name = "NUMBER_COLLECTION_ID")
    private Long numberCollectionId;
}
