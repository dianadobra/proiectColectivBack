package com.webcbg.eppione.companysettings.rest.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.webcbg.eppione.companysettings.rest.dto.ErrorInfo;
import com.webcbg.eppione.companysettings.service.errors.InvalidDataException;
import com.webcbg.eppione.companysettings.service.errors.ResourceAlreadyExistException;
import com.webcbg.eppione.companysettings.service.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@RestController
public class GlobalControllerExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ErrorInfoFactory errorInfoFactory;

	@ExceptionHandler(value = { EntityNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorInfo handleEntityNotFoundException(final HttpServletRequest request, final EntityNotFoundException e) {
		return ErrorInfo.builder().status(HttpStatus.NOT_FOUND.value()).messageKey("resource.notfound")
				.message(messageSource.getMessage("resource.notfound", null, Locale.ENGLISH)).internalCode(40401)
				.developerMessage(e.getMessage()).build();
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorInfo handleResourceNotFoundException(final HttpServletRequest request,
			final ResourceNotFoundException e) {
		return ErrorInfo.builder().status(HttpStatus.NOT_FOUND.value())
				.messageKey(e.getMessageKey()).message(messageSource.getMessage(e.getMessageKey(),
						e.getMessageArguments(), e.getMessage(), Locale.ENGLISH))
				.internalCode(40402).developerMessage(e.getMessage()).build();
	}

	@ExceptionHandler(value = { ResourceAlreadyExistException.class })
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorInfo handleResourceAlreadyExistException(final ResourceAlreadyExistException e) {
		return errorInfoFactory.getLocalizedInstance(HttpStatus.CONFLICT, e);
	}

	@ExceptionHandler(value = { InvalidDataException.class })
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorInfo handleInvalidDataException(final HttpServletRequest request, final InvalidDataException e) {
		return ErrorInfo.builder().status(HttpStatus.CONFLICT.value())
				.messageKey(e.getMessageKey()).message(messageSource.getMessage(e.getMessageKey(),
						e.getMessageArguments(), e.getMessage(), Locale.ENGLISH))
				.internalCode(40901).developerMessage(e.getMessage()).build();
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorInfo handleException(final Exception e) {
		logger.error(e.getMessage(), e);
		return errorInfoFactory.getInstance(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { HttpMediaTypeException.class })
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ErrorInfo handleException(final HttpMediaTypeException e) {
		return errorInfoFactory.getInstance(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorInfo handleInvalidParamException(final HttpServletRequest request,
			final HttpMessageNotReadableException e) {
		String key = "";
		Object[] arg = new Object[2];
		if (e.getMostSpecificCause() instanceof InvalidFormatException) {

			final InvalidFormatException formatException = (InvalidFormatException) e.getMostSpecificCause();
			final List<JsonMappingException.Reference> path = formatException.getPath();
			key = "request.invalid.field.value"; // request.invalid.field.value
													// = Invalid value {0} for
													// field {1}

			arg[0] = formatException.getValue();
			arg[1] = path.get(path.size() - 1).getFieldName();

		}
		if (e.getCause() instanceof JsonMappingException && e.getCause().getCause() instanceof InvalidDataException) {
			final InvalidDataException e1 = (InvalidDataException) e.getCause().getCause();
			key = e1.getMessageKey();
			arg = e1.getMessageArguments();
		}
		return ErrorInfo.builder().status(HttpStatus.BAD_REQUEST.value()).messageKey(key)
				.message(messageSource.getMessage(key, arg, Locale.ENGLISH)).internalCode(40404)
				.developerMessage(e.getMessage()).build();

	}
}
