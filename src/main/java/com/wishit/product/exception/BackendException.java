package com.wishit.product.exception;

public class BackendException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int errorNo;

    public BackendException(int errorNo) {
        super(ErrorCodes.getMessage(errorNo));
        this.errorNo = errorNo;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public String getErrorMessage() {
        return getMessage();
    }
}