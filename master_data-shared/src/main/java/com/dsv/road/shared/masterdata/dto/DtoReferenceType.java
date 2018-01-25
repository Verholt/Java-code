package com.dsv.road.shared.masterdata.dto;

import java.io.Serializable;

public class DtoReferenceType extends AbstractDto implements Serializable {

    private static final long serialVersionUID = 4140116120392885070L;

    private Long id;
    private String referenceType;
    private Boolean isDefault = false;

    public DtoReferenceType() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
