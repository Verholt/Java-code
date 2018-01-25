package com.dsv.road.masterdata.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrderProfileKeyList {

    private List<CustomerOrderProfileKey> list;

    public CustomerOrderProfileKeyList() {
        super();
    }

    public CustomerOrderProfileKeyList(List<CustomerOrderProfileKey> list) {
        this.list = list;
    }

    public List<CustomerOrderProfileKey> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<CustomerOrderProfileKey> list) {
        this.list = list;
    }

}
