package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.ReferenceType;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class ReferenceTypeException extends ApplicationException {
    public ReferenceTypeException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, ReferenceType.class.getName(), message, cause);
    }

    public ReferenceTypeException(ResponseErrorCode resultCode, String message) {
        super(resultCode, ReferenceType.class.getName(), message);
    }
}
