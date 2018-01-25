package com.dsv.road.shared.masterdata.dto;

import java.io.Serializable;

public class DtoCurrency extends AbstractDto implements Serializable {

    private static final long serialVersionUID = 2725791986089768829L;

    String shortAbbreviation;
    String longAbbreviation;
    String description;

    public DtoCurrency() {
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
