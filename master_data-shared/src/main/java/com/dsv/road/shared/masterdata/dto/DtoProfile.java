package com.dsv.road.shared.masterdata.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DtoProfile extends AbstractDto implements Serializable {

    private static final long serialVersionUID = -3026243166649300616L;

    private Long id;
    private String name;
    private String description;
    private List<DtoProfileAttribute> attributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DtoProfileAttribute> getAttributes() {
        if (attributes == null) {
            this.attributes = new ArrayList<>();
        }
        return attributes;
    }

    public void setAttributes(List<DtoProfileAttribute> attributes) {
        this.attributes = attributes;
    }
}
