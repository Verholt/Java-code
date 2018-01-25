package com.dsv.road.shared.masterdata.exception;

import com.dsv.shared.rest.exception.BaseValidationException;
import com.dsv.shared.rest.exception.ValidationError;

import java.util.List;

@javax.ejb.ApplicationException(rollback = true)
public class ValidationException extends BaseValidationException {

    protected ValidationException(List<ValidationError> errors) {
        super(errors);
    }

    protected ValidationException(List<ValidationError> errors, Throwable cause) {
        super(errors, cause);
    }

    protected ValidationException(String message, List<ValidationError> errors) {
        super(message, errors);
    }

    protected ValidationException(String message, List<ValidationError> errors, Throwable cause) {
        super(message, errors, cause);
    }
}
