package com.dsv.road.shared.masterdata.dto;

import java.io.Serializable;

public class DtoProfileAttribute extends AbstractDto implements Serializable {

    private static final long serialVersionUID = -4811247630347040261L;

    private Long id;
    private String key;
    private String value;
    private String usage;
    private Long keyId;

    public DtoProfileAttribute() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
