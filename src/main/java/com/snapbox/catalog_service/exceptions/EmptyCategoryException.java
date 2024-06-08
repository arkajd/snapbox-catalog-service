package com.snapbox.catalog_service.exceptions;

public class EmptyCategoryException extends RuntimeException {
    public EmptyCategoryException(String message) {
        super(message);
    }
}
