package com.yama.crowd.exceptions;

public class NameInRoleAlreadyException extends RuntimeException {
    public NameInRoleAlreadyException() {
        super();
    }

    public NameInRoleAlreadyException(String message) {
        super(message);
    }

    public NameInRoleAlreadyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameInRoleAlreadyException(Throwable cause) {
        super(cause);
    }

    protected NameInRoleAlreadyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
