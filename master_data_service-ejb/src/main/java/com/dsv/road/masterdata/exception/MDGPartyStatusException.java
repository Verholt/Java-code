package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.MDGPartyStatus;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class MDGPartyStatusException extends ApplicationException {
    public MDGPartyStatusException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, MDGPartyStatus.class.getName(), message, cause);
    }

    public MDGPartyStatusException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, MDGPartyStatus.class.getName(), message);
    }
}
