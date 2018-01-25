package com.dsv.road.shared.masterdata.dto;

import com.dsv.road.shared.masterdata.dto.constraints.NumberCollectionConstraints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumberCollectionDto {
    private Long id;
    @Size(max = NumberCollectionConstraints.NAME_LENGTH)
    private String name;
    private Long sharedNumberCollection;
    @Size(max = NumberCollectionConstraints.DESCRIPTION_LENGTH)
    private String description;
    private Long warningLimit;
    private Integer warningWhen;
    @Size(max = NumberCollectionConstraints.WARNING_METHOD_LENGTH)
    private String warningMethod;
    @Size(max = NumberCollectionConstraints.WARNING_WHO_LENGTH)
    private String warningWho;
    private String warningGiven;
    private String lastAssigned;
    @Size(max = NumberCollectionConstraints.LOCATION_LENGTH)
    private String location;
    @Size(max = NumberCollectionConstraints.COUNTRY_LENGHT)
    private String country;
    @Valid
    private List<NumberRangeDto> numberRanges;
    @Valid
    private List<NumberPoolDto> numberPools;
}
