package com.dsv.road.shared.masterdata.dto;

import com.dsv.road.shared.masterdata.dto.constraints.NumberCollectionConstraints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumberPoolDto {
    private Long id;
    private Long number;
    @Size(max = NumberCollectionConstraints.NUMBER_POOL_NUMBER_FORMATTED_LENGTH)
    private String numberFormatted;
    private String assigned;
    private Long numberCollectionId;
}
