package br.com.engenhodesoftware.util.people.persistence.exceptions;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * Abstract class representing checked exceptions that are thrown by query methods in the DAOs.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
public abstract class CheckedQueryException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The entity class. */
	private Class<? extends PersistentObject> entityClass;

	/** The parameters used in the query. */
	private Object[] params;

	/** Constructor from superclass, using fields. */
	public CheckedQueryException(Throwable cause, Class<? extends PersistentObject> entityClass, Object ... params) {
		super(cause);
		this.entityClass = entityClass;
		this.params = params;
	}

	/** Getter for entityClass. */
	public Class<? extends PersistentObject> getEntityClass() {
		return entityClass;
	}

	/** Setter for entityClass. */
	public void setEntityClass(Class<? extends PersistentObject> entityClass) {
		this.entityClass = entityClass;
	}

	/** Getter for params. */
	public Object[] getParams() {
		return params;
	}

	/** Setter for params. */
	public void setParams(Object[] params) {
		this.params = params;
	}
}
