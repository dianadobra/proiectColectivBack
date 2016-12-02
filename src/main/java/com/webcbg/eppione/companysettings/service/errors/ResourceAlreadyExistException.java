package com.webcbg.eppione.companysettings.service.errors;

/**
 * Created by oltud on 5/11/2016.
 */
public class ResourceAlreadyExistException extends LocalizableRuntimeException {

    public ResourceAlreadyExistException(String messageKey, Object... messageArguments) {
        super(messageKey, messageArguments);
    }

    public ResourceAlreadyExistException(String defaultMessage, String messageKey, Object... messageArguments) {
        super(defaultMessage, messageKey, messageArguments);
    }

    public ResourceAlreadyExistException(String message, Throwable cause, String messageKey, Object... messageArguments) {
        super(message, cause, messageKey, messageArguments);
    }

    public ResourceAlreadyExistException(Throwable cause, String messageKey, Object... messageArguments) {
        super(cause, messageKey, messageArguments);
    }

    public ResourceAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String messageKey, Object... messageArguments) {
        super(message, cause, enableSuppression, writableStackTrace, messageKey, messageArguments);
    }
}
