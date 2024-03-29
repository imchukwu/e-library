package com.cimspace.e_library.model;


public class FieldError {

    private String field;
    private String errorCode;

    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "FieldError{" +
            "field='" + field + '\'' +
            ", errorCode='" + errorCode + '\'' +
            '}';
    }
}
