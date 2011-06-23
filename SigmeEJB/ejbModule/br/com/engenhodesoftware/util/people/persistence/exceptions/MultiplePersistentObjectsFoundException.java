package br.com.engenhodesoftware.util.people.persistence.exceptions;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * This exception represents the case in a DAO where a single result was expected but multiple were found. It is the
 * checked exception version of <code>javax.persistence.NonUniqueResultException</code>.
 * 
 * It is necessary because since NonUniqueResultException is not checked and our DAOs are EJBs, the EJB container
 * considers the exception as an EJBException and does all sorts of exception handling we find undesirable in many
 * cases.
 * 
 * @see javax.persistence.NoResultException
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public class MultiplePersistentObjectsFoundException extends CheckedQueryException {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Constructor. */
	public MultiplePersistentObjectsFoundException(Throwable cause, Class<? extends PersistentObject> entityClass, Object ... params) {
		super(cause, entityClass, params);
	}
}
