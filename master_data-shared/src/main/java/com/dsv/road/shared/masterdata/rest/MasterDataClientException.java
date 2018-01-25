package com.dsv.road.shared.masterdata.rest;

import com.dsv.shared.rest.client.ResponseValidationException;

@javax.ejb.ApplicationException(rollback = true)
public class MasterDataClientException extends ResponseValidationException {
    private static final long serialVersionUID = 1L;
    protected MasterDataClientException(int httpStatus, String statusCode, String message, Throwable cause) {
        super(httpStatus, statusCode, message, cause);
    }

    protected MasterDataClientException(int httpStatus, String statusCode, String message) {
        super(httpStatus, statusCode, message);
    }

    protected MasterDataClientException(int httpStatus, String statusCode, String entity, String message, Throwable cause) {
        super(httpStatus, statusCode, entity, message, cause);
    }

    protected MasterDataClientException(int httpStatus, String statusCode, String entity, String message) {
        super(httpStatus, statusCode, entity, message);
    }
}
