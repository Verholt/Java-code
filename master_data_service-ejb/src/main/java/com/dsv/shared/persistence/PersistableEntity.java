package com.dsv.shared.persistence;

import lombok.Data;

import javax.persistence.*;

@SuppressWarnings("serial")
@MappedSuperclass
@Data
public class PersistableEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;
}