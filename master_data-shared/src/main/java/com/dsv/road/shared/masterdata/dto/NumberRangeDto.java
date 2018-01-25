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
public class NumberRangeDto {
    private Long id;
    private int rangeNo;
    private Long startValue;
    private Long minValue;
    private Long maxValue;
    private Long recentValue;
    private int step;
    private int cycle;
    private int unbroken;
    private String validFrom;
    private String validTo;
    @Size(max = NumberCollectionConstraints.NUMBER_RANGE_OUTPUT_FORMAT_LENGTH)
    private String outputFormat;
    private ActiveState state;
    private String lastAssigned;
    private Long numberCollectionId;
    private CheckDigitMethodType checkDigitMethodType;
}

