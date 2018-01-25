package com.dsv.road.shared.masterdata.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class AbstractDto implements Serializable {

    private static final long serialVersionUID = -980211859885528975L;

    protected String updatedBy;
    protected String createdBy;
}
