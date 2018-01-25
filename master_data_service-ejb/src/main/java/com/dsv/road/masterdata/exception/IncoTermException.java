package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.IncoTerm;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class IncoTermException extends ApplicationException {
    public IncoTermException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, IncoTerm.class.getName(), message, cause);
    }

    public IncoTermException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, IncoTerm.class.getName(), message);
    }
}
