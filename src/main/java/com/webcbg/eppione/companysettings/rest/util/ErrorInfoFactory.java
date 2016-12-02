package com.webcbg.eppione.companysettings.rest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.webcbg.eppione.companysettings.rest.dto.ErrorInfo;
import com.webcbg.eppione.companysettings.rest.dto.FieldErrorInfo;
import com.webcbg.eppione.companysettings.service.errors.LocalizableRuntimeException;

@Component
public class ErrorInfoFactory {

	private final MessageSource messageSource;

	@Autowired
	public ErrorInfoFactory(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ErrorInfo getInstance(final Exception e, final HttpStatus status) {
		return ErrorInfo.builder().status(status.value()).messageKey(e.toString()).message(e.getMessage())
				.developerMessage(e.getMessage()).build();
	}

	public ErrorInfo getLocalizedInstance(final HttpStatus status, final LocalizableRuntimeException e) {
		return ErrorInfo.builder().status(status.value())
				.messageKey(e.getMessageKey()).message(messageSource.getMessage(e.getMessageKey(),
						e.getMessageArguments(), e.getMessage(), getCurrentUserLocale()))
				.developerMessage(e.getMessage()).build();
	}

	public ErrorInfo getLocalizedInstance(final HttpStatus status, final String messageKey,
			final Object... messageArguments) {
		return ErrorInfo.builder().status(status.value()).messageKey(messageKey)
				.message(messageSource.getMessage(messageKey, messageArguments, getCurrentUserLocale())).build();
	}

	private Locale getCurrentUserLocale() {
		return Locale.ENGLISH;
	}

	public static ErrorInfo buildBadRequestErrorInfo(final String message, final String messageKey,
			final BindingResult bindingResult) {
		final List<FieldErrorInfo> fieldErrors = new ArrayList<>();
		final List<FieldError> errors = bindingResult.getFieldErrors();
		for (final FieldError error : errors) {
			fieldErrors.add(new FieldErrorInfo(error.getDefaultMessage(), error.getField(),
					messageKey + "." + error.getField() + "." + error.getCode(), error.getRejectedValue()));
		}
		return ErrorInfo.builder().status(HttpStatus.BAD_REQUEST.value()).message(message).messageKey(messageKey)
				.fieldErrors(fieldErrors).build();
	}

	public ResponseEntity<ErrorInfo> createResponseEntityForError(final HttpStatus httpStatus, final String messageKey,
			final Object... messageArguments) {
		return ResponseEntity.status(httpStatus).body(getLocalizedInstance(httpStatus, messageKey, messageArguments));
	}

	public ResponseEntity<ErrorInfo> createResponseEntityForErrorWithDetails(final HttpStatus httpStatus,
			final String messageKey, final String details, final Object... messageArguments) {
		final ErrorInfo i = getLocalizedInstance(httpStatus, messageKey, messageArguments);
		i.setDeveloperMessage(details);
		return ResponseEntity.status(httpStatus).body(i);
	}

	public ErrorInfo buildBadRequestErrorInfo(final String message, final String messageKey,
			final List<FieldError> errors) {
		final List<FieldErrorInfo> fieldErrors = new ArrayList<>();
		for (final FieldError error : errors) {
			fieldErrors
					.add(new FieldErrorInfo(
							messageSource.getMessage(error.getCode(), error.getArguments(), error.getDefaultMessage(),
									getCurrentUserLocale()),
							error.getField(), error.getCode(), error.getRejectedValue()));
		}
		return ErrorInfo.builder().status(HttpStatus.BAD_REQUEST.value()).message(message).messageKey(messageKey)
				.fieldErrors(fieldErrors).build();
	}

}
