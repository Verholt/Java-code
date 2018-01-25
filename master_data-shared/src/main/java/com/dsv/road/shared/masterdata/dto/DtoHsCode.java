package com.dsv.road.shared.masterdata.dto;

import java.util.ArrayList;
import java.util.List;

public class DtoHsCode {
    private Long id;
    private String hsCode;
    private String validFrom;
    private String validTo;
    private List<DtoHsCodeText> hsCodeTextObjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public List<DtoHsCodeText> getHsCodeTextObjects() {
        if (hsCodeTextObjects == null) {
            hsCodeTextObjects = new ArrayList<DtoHsCodeText>();
        }
        return hsCodeTextObjects;
    }

    public void setHsCodeTextObjects(List<DtoHsCodeText> hsCodeTextObjects) {
        this.hsCodeTextObjects = hsCodeTextObjects;
    }
}
