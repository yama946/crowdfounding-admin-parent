package com.yama.crowd.exceptions;

public class LoginFaliedException extends RuntimeException {

    public LoginFaliedException() {
    }

    public LoginFaliedException(String message) {
        super(message);
    }

    public LoginFaliedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFaliedException(Throwable cause) {
        super(cause);
    }

    protected LoginFaliedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
