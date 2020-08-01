package com.example.productsale.exception;

public class SHAException {

    private final static int ERROR_CODE = 640;

    public static class InvalidKey extends BaseException {

        public InvalidKey(String message) {
            super(ERROR_CODE + 0, message);
        }

        public InvalidKey(String message, Throwable cause) {
            super(ERROR_CODE + 0, message, cause);
        }
    }
}
