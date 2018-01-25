package com.dsv.road.shared.masterdata.dto;

import java.util.Date;

/**
 * Created by EXT.piotr.komisarki on 2016-02-23.
 */
public class DtoLogMessage {

    private Date date;
    private String message;

    public DtoLogMessage() {
    }

    public DtoLogMessage(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
