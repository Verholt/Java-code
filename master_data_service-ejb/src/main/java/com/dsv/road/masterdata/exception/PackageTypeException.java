package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.PackageType;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class PackageTypeException extends ApplicationException {
    public PackageTypeException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, PackageType.class.getName(), message, cause);
    }

    public PackageTypeException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, PackageType.class.getName(), message);
    }
}
