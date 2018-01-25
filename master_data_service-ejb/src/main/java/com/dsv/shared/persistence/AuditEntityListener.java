package com.dsv.shared.persistence;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(AuditableEntity e) {
        e.setCreatedBy("John Doe");
        e.setCreatedAt(new Date());
    }

    @PreUpdate
    public void preUpdate(AuditableEntity e) {
        e.setUpdatedBy("John Doe");
        e.setUpdatedAt(new Date());
    }
}
