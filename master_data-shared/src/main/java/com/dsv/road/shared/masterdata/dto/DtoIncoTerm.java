package com.dsv.road.shared.masterdata.dto;

import java.io.Serializable;

public class DtoIncoTerm extends AbstractDto implements Serializable {

    private static final long serialVersionUID = 7036919127687894991L;

    String shortAbbreviation;
    String longAbbreviation;
    String description;

    public DtoIncoTerm() {
        super();
    }

    public String getShortAbbreviation() {
        return shortAbbreviation;
    }

    public void setShortAbbreviation(String shortAbbreviation) {
        this.shortAbbreviation = shortAbbreviation;
    }

    public String getLongAbbreviation() {
        return longAbbreviation;
    }

    public void setLongAbbreviation(String longAbbreviation) {
        this.longAbbreviation = longAbbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
