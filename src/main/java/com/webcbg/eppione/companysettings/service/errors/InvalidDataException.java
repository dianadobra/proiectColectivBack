package com.webcbg.eppione.companysettings.service.errors;

public class InvalidDataException extends LocalizableRuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Class entityClass;

    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException(String message, String messageKey, Object... messageArguments) {
        super(message, messageKey, messageArguments);
    }

    public InvalidDataException(Class entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

    public Class getEntityClass() {
        return entityClass;
    }

}
