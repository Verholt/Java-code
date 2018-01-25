package com.dsv.road.masterdata.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrderProfileList {

    private List<CustomerOrderProfile> list;

    public CustomerOrderProfileList() {
        // On purpose for Dozer, Jackson etc.
    }

    public CustomerOrderProfileList(List<CustomerOrderProfile> list) {
        this.list = list;
    }

    public List<CustomerOrderProfile> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<CustomerOrderProfile> list) {
        this.list = list;
    }

}
