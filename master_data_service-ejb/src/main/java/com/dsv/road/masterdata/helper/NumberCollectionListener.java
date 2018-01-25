package com.dsv.road.masterdata.helper;

import com.dsv.road.masterdata.model.NumberCollection;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class NumberCollectionListener {
    @PrePersist
    @PreUpdate
    public void preModification(NumberCollection e) {
        e.setSearchString(e.getName().toUpperCase()+" "+e.getDescription().toUpperCase());
    }
}
