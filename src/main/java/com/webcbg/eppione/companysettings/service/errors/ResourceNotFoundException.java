package com.webcbg.eppione.companysettings.service.errors;

/**
 * Created by oltud on 5/13/2016.
 */
public class ResourceNotFoundException extends LocalizableRuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Class entityClass;

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, String messageKey, Object... messageArguments) {
        super(message, messageKey, messageArguments);
    }

    public ResourceNotFoundException(Class entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

    public Class getEntityClass() {
        return entityClass;
    }
}
