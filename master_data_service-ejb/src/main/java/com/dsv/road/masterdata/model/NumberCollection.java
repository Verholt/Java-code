package com.dsv.road.masterdata.model;

import com.dsv.road.masterdata.helper.NumberCollectionListener;
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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = NumberCollection.GET_ALL_COLLECTIONS_NAMED_QUERY, query = "SELECT b FROM NumberCollection b WHERE b.searchString LIKE :" + NumberCollection.SEARCH_FILTER),
        @NamedQuery(name = NumberCollection.GET_NEXT_NUMBER_RANGE, query = "SELECT r from NumberRange r WHERE " +
                "r.numberCollectionId IN (SELECT c FROM NumberCollection c WHERE " +
                                    "c.name = :" + NumberCollection.NAME_FILTER + " " +
                                "AND c.country = :" + NumberCollection.COUNTRY_FILTER + " " +
                                "AND c.location = :" + NumberCollection.LOCATION_FILTER + ") " +
                "AND r.state=com.dsv.road.shared.masterdata.dto.ActiveState.NEW " +
                "AND CURRENT_TIMESTAMP BETWEEN r.validFrom AND r.validTo " +
                "AND r.startValue>=r.minValue " +
                "AND r.startValue<=r.maxValue ORDER BY r.rangeNo ASC"),
        @NamedQuery(name = NumberCollection.GET_ACTIVE_NUMBER_RANGE, query = "SELECT r from NumberRange r WHERE " +
                "r.numberCollectionId IN (SELECT c FROM NumberCollection c WHERE " +
                                    "c.name = :" + NumberCollection.NAME_FILTER + " " +
                                    "AND c.country = :" + NumberCollection.COUNTRY_FILTER + " " +
                                    "AND c.location = :" + NumberCollection.LOCATION_FILTER + ") " +
                "AND r.state=com.dsv.road.shared.masterdata.dto.ActiveState.ACTIVE"),
        @NamedQuery(name = NumberCollection.GET_ID_OF_NUMBER_COLLECTION_BY_PARAMETERS, query = "SELECT c.id FROM NumberCollection c WHERE " +
                "c.name = :" + NumberCollection.NAME_FILTER + " " +
                "AND c.country = :" + NumberCollection.COUNTRY_FILTER + " " +
                "AND c.location = :" + NumberCollection.LOCATION_FILTER ),
        @NamedQuery(name = NumberCollection.GET_ALL_COLLECTIONS_LITE_NAMED_QUERY, query = "SELECT new NumberCollection(b.id, b.name,b.sharedNumberCollection,b.description,b.lastAssigned,b.country,b.location) FROM NumberCollection b WHERE b.searchString LIKE :" + NumberCollection.SEARCH_FILTER),
})
@Table(name = "NUMBER_COLLECTION")
@Interceptors(LoggerInterceptor.class)
@EntityListeners(NumberCollectionListener.class)
public class NumberCollection extends PersistableEntity {
    public static final String GET_ALL_COLLECTIONS_NAMED_QUERY = "GET_ALL_COLLECTIONS_NAMED_QUERY";
    public static final String GET_ALL_COLLECTIONS_LITE_NAMED_QUERY = "GET_ALL_COLLECTIONS_LITE_NAMED_QUERY";
    public static final String GET_NEXT_NUMBER_RANGE = "GET_NEXT_NUMBER_RANGE";
    public static final String GET_ACTIVE_NUMBER_RANGE = "GET_ACTIVE_NUMBER_RANGE";
    public static final String GET_ID_OF_NUMBER_COLLECTION_BY_PARAMETERS = "GET_ID_OF_NUMBER_COLLECTION_BY_PARAMETERS";

    public static final String COUNTRY_FILTER = "country";
    public static final String LOCATION_FILTER = "location";
    public static final String NAME_FILTER = "name";
    public static final String SEARCH_FILTER = "filter";
    @Column(name = "NAME", length = NumberCollectionConstraints.NAME_LENGTH)
    private String name;
    @Column(name = "SHARED_NUMBER_COLLECTION")
    private Long sharedNumberCollection;
    @Column(name = "DESCIPTION", length = NumberCollectionConstraints.DESCRIPTION_LENGTH)
    private String description;
    @Column(name = "WARNING_LIMIT")
    private Long warningLimit;
    @Column(name = "WARNING_WHEN")
    private Integer warningWhen;
    @Column(name = "WARNING_METHOD", length = NumberCollectionConstraints.WARNING_METHOD_LENGTH)
    private String warningMethod;
    @Column(name = "WARNING_WHO", length = NumberCollectionConstraints.WARNING_WHO_LENGTH)
    private String warningWho;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WARNING_GIVEN")
    private Date warningGiven;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ASSIGNED")
    private Date lastAssigned;
    @Column(name = "SEARCH_STRING", length = NumberCollectionConstraints.NAME_LENGTH + NumberCollectionConstraints.DESCRIPTION_LENGTH + 1)
    private String searchString;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = NumberRange.class)
    @JoinColumn(name = "NUMBER_COLLECTION_ID")
    private List<NumberRange> numberRanges;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = NumberPool.class)
    @JoinColumn(name = "NUMBER_COLLECTION_ID")
    private List<NumberPool> numberPools;
    @Column(name = "LOCATION", length = NumberCollectionConstraints.LOCATION_LENGTH)
    private String location;
    @Column(name = "COUNTRY", length = NumberCollectionConstraints.COUNTRY_LENGHT)
    private String country;

    public NumberCollection(Long id, String name, Long sharedNumberCollection, String description, Date lastAssigned,String country,String location) {
        this.id = id;
        this.name = name;
        this.sharedNumberCollection = sharedNumberCollection;
        this.description = description;
        this.lastAssigned = lastAssigned;
        this.country = country;
        this.location = location;
    }
}
