package com.webcbg.eppione.companysettings.service.errors;

public class ConstraintViolationException extends LocalizableRuntimeException{

	private static final long serialVersionUID = 1L;

	private Class entityClass;

	public ConstraintViolationException(String message) {
        super(message);
    }

	public ConstraintViolationException(String message, String messageKey, Object... messageArguments) {
        super(message, messageKey, messageArguments);
    }

	public ConstraintViolationException(Class entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

	public Class getEntityClass() {
		return entityClass;
	}

}
