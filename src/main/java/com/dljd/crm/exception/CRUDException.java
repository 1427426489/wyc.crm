package com.dljd.crm.exception;

public class CRUDException extends RuntimeException {
    public CRUDException() {
        super();
    }

    public CRUDException(String message) {
        super(message);
    }
}
