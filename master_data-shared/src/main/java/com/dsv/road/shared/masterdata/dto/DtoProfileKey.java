package com.dsv.road.shared.masterdata.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DtoProfileKey extends AbstractDto implements Serializable {

    private static final long serialVersionUID = 2146029163604484547L;

    @NotNull
    Long id;
    String key;
    String description;

    public DtoProfileKey() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
