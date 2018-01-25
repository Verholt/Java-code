package com.dsv.shared.dozer;

import org.dozer.Mapper;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

public class DozerResource {

    @Produces
    @Default
    public Mapper createDozerMapper() {
        return DozerUtility.getMapper();
    }

}
