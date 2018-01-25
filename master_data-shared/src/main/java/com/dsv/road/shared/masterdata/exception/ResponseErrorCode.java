package com.dsv.road.shared.masterdata.exception;

public enum ResponseErrorCode {
    MISSING_PARAMETER("missingParameter", 400),
    INSERT_WITH_NULL("insertWithNullObject", 400),
    INSERT_WITH_NOTNULL_ID("insertWithNotEmptyId", 400),
    UPDATE_WITH_NULL("updateWithNullObject", 400),
    DELETE_WITH_NULL("deleteWithNullObject", 400),
    WRONG_INPUT("wrongInput", 400),
    NOT_FOUND("notFound", 404),
    UNKNOWN_DIVISION("theGivenDivisionIsNotKnown", 400),
    INTERNAL_PROBLEM("internalProblem", 500);

    private final String statusCode;
    private final int httpStatus;

    ResponseErrorCode(String statusCode, int httpStatus) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
