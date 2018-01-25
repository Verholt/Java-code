package com.dsv.road.masterdata.exception;

import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class DangerousGoodsException extends ApplicationException {
	public DangerousGoodsException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
		super(responseErrorCode, DangerousGoodsException.class.getName(), message, cause);
	}

	public DangerousGoodsException(ResponseErrorCode responseErrorCode, String message) {
		super(responseErrorCode, DangerousGoodsException.class.getName(), message);
	}
}
