package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.Role;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class NumberCollectionException extends ApplicationException {
	public NumberCollectionException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
		super(responseErrorCode, Role.class.getName(), message, cause);
	}

	public NumberCollectionException(ResponseErrorCode responseErrorCode, String message) {
		super(responseErrorCode, Role.class.getName(), message);
	}
}
