package com.dsv.road.shared.masterdata.parser.base;

public class XLSXParserException extends Exception {
    private static final long serialVersionUID = 1L;

    public XLSXParserException() {
        // On purpose
    }

    public XLSXParserException(String message) {
        super(message);
    }

    public XLSXParserException(Throwable cause) {
        super(cause);
    }

    public XLSXParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
