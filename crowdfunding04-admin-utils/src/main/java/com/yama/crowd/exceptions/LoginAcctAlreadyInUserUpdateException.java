package com.yama.crowd.exceptions;

/**
 * 用户更新异常
 */
public class LoginAcctAlreadyInUserUpdateException extends RuntimeException {
    public LoginAcctAlreadyInUserUpdateException() {
        super();
    }

    public LoginAcctAlreadyInUserUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUserUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUserUpdateException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUserUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
