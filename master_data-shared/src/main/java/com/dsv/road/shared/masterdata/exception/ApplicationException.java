package com.dsv.road.shared.masterdata.exception;

import com.dsv.shared.rest.exception.BaseApplicationException;

/**
 * Base for all application / business logic exception
 */
@javax.ejb.ApplicationException(rollback = true)
public class ApplicationException extends BaseApplicationException {
    /**
     * Constructor of app exceptin.
     * @param responseErrorCode
     *          enum defines list of valid errors...
     */
    public ApplicationException(final ResponseErrorCode responseErrorCode, final String message, final Throwable cause) {
        super(responseErrorCode.getHttpStatus(), responseErrorCode.getStatusCode(), message, cause );
    }

    public ApplicationException(final ResponseErrorCode responseErrorCode, final String message) {
        super(responseErrorCode.getHttpStatus(), responseErrorCode.getStatusCode(), message);
    }

    public ApplicationException(final ResponseErrorCode responseErrorCode, final String entity, final String message, final Throwable cause) {
        super(responseErrorCode.getHttpStatus(), responseErrorCode.getStatusCode(), entity, message, cause);
    }

    public ApplicationException(final ResponseErrorCode responseErrorCode, final String entity, final String message) {
        super(responseErrorCode.getHttpStatus(), responseErrorCode.getStatusCode(), entity, message);
    }
}
