package com.example.productsale.exception;

public class RSAException {

    private final static int ERROR_CODE = 630;

    public static class InvalidPublicKey extends BaseException {

        public InvalidPublicKey(String message) {
            super(ERROR_CODE + 0, message);
        }

        public InvalidPublicKey(String message, Throwable cause) {
            super(ERROR_CODE + 0, message, cause);
        }
    }

    public static class InvalidPrivateKey extends BaseException {

        public InvalidPrivateKey(String message) {
            super(ERROR_CODE + 1, message);
        }

        public InvalidPrivateKey(String message, Throwable cause) {
            super(ERROR_CODE + 1, message, cause);
        }
    }
}
