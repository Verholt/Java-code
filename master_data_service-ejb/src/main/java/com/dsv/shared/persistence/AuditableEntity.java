package com.dsv.shared.persistence;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
@Data
public class AuditableEntity implements Serializable {
    @Version
    @Column(name = "OPT_LOCK")
    protected Long version;

    @Column(name = "CREATED_BY")
    protected String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    protected Date createdAt;

    @Column(name = "UPDATED_BY")
    protected String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    protected Date updatedAt;
}