package br.com.engenhodesoftware.util.ejb3.application;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Superclass of exceptions that can happen during any CRUD operation. Extends regular exceptions by providing a
 * collection of error messages that are to be displayed to the user by the controller. Also implements iterator,
 * iterating through the error messages.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class CrudException extends Exception implements Iterable<CrudValidationError> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Collection of validation errors. */
	private Collection<CrudValidationError> validationErrors = new HashSet<CrudValidationError>();

	/**
	 * Constructor.
	 * 
	 * @param message
	 *          Developer-friendly exception message.
	 * @param messageKey
	 *          User-friendly error message key.
	 * @param messageParams
	 *          Parameters for user-friendly error message.
	 */
	public CrudException(String message, String messageKey, Object[] messageParams) {
		super(message);
		addValidationError(messageKey, messageParams);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *          Developer-friendly exception message.
	 * @param fieldName
	 *          Name of the field to which this message is related.
	 * @param messageKey
	 *          User-friendly error message key.
	 * @param messageParams
	 *          Parameters for user-friendly error message.
	 */
	public CrudException(String message, String fieldName, String messageKey, Object[] messageParams) {
		super(message);
		addValidationError(fieldName, messageKey, messageParams);
	}

	/**
	 * Adds a validation error to the collection of validation errors.
	 * 
	 * @param messageKey
	 *          User-friendly error message key.
	 * @param messageParams
	 *          Parameters for user-friendly error message.
	 */
	public void addValidationError(String messageKey, Object[] messageParams) {
		validationErrors.add(new CrudValidationError(messageKey, messageParams));
	}

	/**
	 * Adds a validation error to the collection of validation errors.
	 * 
	 * @param fieldName
	 *          Name of the field to which this message is related.
	 * @param messageKey
	 *          User-friendly error message key.
	 * @param messageParams
	 *          Parameters for user-friendly error message.
	 */
	public void addValidationError(String fieldName, String messageKey, Object[] messageParams) {
		validationErrors.add(new CrudValidationError(fieldName, messageKey, messageParams));
	}

	/** @see java.lang.Iterable#iterator() */
	@Override
	public Iterator<CrudValidationError> iterator() {
		return validationErrors.iterator();
	}
}
