package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.HsCode;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class HsCodeTextException extends ApplicationException {

    public HsCodeTextException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, HsCode.class.getName(), message, cause);
    }

    public HsCodeTextException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, HsCode.class.getName(), message);
    }
}
