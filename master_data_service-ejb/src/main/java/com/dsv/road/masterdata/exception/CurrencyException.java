package com.dsv.road.masterdata.exception;

import com.dsv.road.masterdata.model.Currency;
import com.dsv.road.shared.masterdata.exception.ApplicationException;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;

public class CurrencyException extends ApplicationException {
    public CurrencyException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
        super(responseErrorCode, Currency.class.getName(), message, cause);
    }

    public CurrencyException(ResponseErrorCode responseErrorCode, String message) {
        super(responseErrorCode, Currency.class.getName(), message);
    }
}
