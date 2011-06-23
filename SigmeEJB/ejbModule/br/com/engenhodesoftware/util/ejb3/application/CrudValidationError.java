package br.com.engenhodesoftware.util.ejb3.application;

import java.io.Serializable;

/**
 * Represents a validation error that can happen during a CRUD operation. It provides to the controller the key for
 * retrieving the error message in the resource bundles and parameters to be inserted in the message. It can also
 * provide a field name, so the controller attaches the message to a specific field of the form.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class CrudValidationError implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Name of the field this error is related to. */
	private String fieldName;

	/** Error message key, so the controller can look at the resource bundle for it. */
	private String messageKey;

	/** Message parameters that are inserted in the error message by the controller. */
	private Object[] messageParams;

	/**
	 * Constructor.
	 * 
	 * @param messageKey
	 *          Error message key.
	 * @param messageParams
	 *          Error message parameters.
	 */
	public CrudValidationError(String messageKey, Object[] messageParams) {
		this.messageKey = messageKey;
		this.messageParams = messageParams;
	}

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 *          Name of related field.
	 * @param messageKey
	 *          Error message key.
	 * @param messageParams
	 *          Error message parameters.
	 */
	public CrudValidationError(String fieldName, String messageKey, Object[] messageParams) {
		this.fieldName = fieldName;
		this.messageKey = messageKey;
		this.messageParams = messageParams;
	}

	/** Getter for fieldName. */
	public String getFieldName() {
		return fieldName;
	}

	/** Setter for fieldName. */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/** Getter for messageKey. */
	public String getMessageKey() {
		return messageKey;
	}

	/** Setterfor messageKey. */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	/** Getter for messageParams. */
	public Object[] getMessageParams() {
		return messageParams;
	}

	/** Setter for messageParams. */
	public void setMessageParams(Object[] messageParams) {
		this.messageParams = messageParams;
	}
}
