package com.dsv.road.masterdata.exception;

import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class ProfileException extends ApplicationException {
    public ProfileException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, message, cause);
    }

    public ProfileException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, message);
    }
}
