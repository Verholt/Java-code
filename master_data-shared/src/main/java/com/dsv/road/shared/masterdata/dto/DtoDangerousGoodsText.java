package com.dsv.road.shared.masterdata.dto;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoDangerousGoodsText extends AbstractDto implements Serializable {
    private static final long serialVersionUID = 1L;
    Long id;
    String languageCode;
    String dangerousGoodsText;
    Long dangerousGoodsId;
}
