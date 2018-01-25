package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.HsCode;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;


public class HsCodeException extends ApplicationException {

    public HsCodeException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, HsCode.class.getName(), message, cause);
    }

    public HsCodeException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, HsCode.class.getName(), message);
    }
}
