package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.DangerousGoodsPackageType;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class DangerousGoodsPackageTypeException extends ApplicationException {
    public DangerousGoodsPackageTypeException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, DangerousGoodsPackageType.class.getName(), message, cause);
    }

    public DangerousGoodsPackageTypeException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, DangerousGoodsPackageType.class.getName(), message);
    }
}
