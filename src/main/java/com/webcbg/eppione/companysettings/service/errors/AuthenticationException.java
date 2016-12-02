package com.webcbg.eppione.companysettings.service.errors;

public class AuthenticationException extends LocalizableRuntimeException{
	
	private static final long serialVersionUID = 1L;

	private Class entityClass;

	public AuthenticationException(String message) {
        super(message);
    }

	public AuthenticationException(String message, String messageKey, Object... messageArguments) {
        super(message, messageKey, messageArguments);
    }

	public AuthenticationException(Class entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

	public Class getEntityClass() {
		return entityClass;
	}

}
