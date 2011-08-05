package br.com.engenhodesoftware.util.ejb3.application;

import java.util.List;

import br.com.engenhodesoftware.util.ejb3.application.filters.Filter;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * Abstract application class that implements CrudServiceLocal, providing general functionality that can be reused by
 * application classes that are specific to the application being developed.
 * 
 * Concrete subclasses must implement abstract methods to fill in the blanks that are application-specific, such as what
 * is the DAO class and how to create an empty new entity.
 * 
 * This abstract class provides empty implementations for validate methods to make them optional for the bean developer.
 * One very important such method is
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 * @see br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public abstract class CrudService<T extends PersistentObject> implements CrudServiceLocal<T> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Provides the DAO so the objects can be retrieved, stored and deleted from the database. As this is class-specific,
	 * this method must be overridden by the subclasses.
	 * 
	 * @return The actual DAO.
	 */
	protected abstract BaseDAO<T> getDAO();

	/**
	 * Creates an empty entity to be stored. As this is class-specific, this method must be overridden by the subclasses.
	 * 
	 * @return An empty entity.
	 */
	protected abstract T createNewEntity();

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#authorize() */
	public void authorize() {}

	/**
	 * Callback method that allows subclasses to intercept the moment exactly before the persisting (creating or updating)
	 * of the entity object in order to perform any checks that might be necessary due to manipulation of this object in
	 * the presentation layer.
	 * 
	 * @param newEntity
	 *          The entity to be persisted.
	 * @param oldEntity
	 *          The old entity from the database, in case of updates.
	 * 
	 * @return The entity object that is going to be persisted.
	 */
	protected T validate(T newEntity, T oldEntity) {
		return newEntity;
	}

	/**
	 * Logs operations over one entity, i.e., creation, retrieval, udpate or deletion of an entity. Default implementation
	 * does nothing, so logging is optional in the subclasses.
	 * 
	 * @param operation
	 *          The operation that is being logged.
	 * @param entity
	 *          The entity that is being manipulated.
	 */
	protected void log(CrudOperation operation, T entity) {}

	/**
	 * Logs operations over many entities, i.e., listing of entities. Default implementation does nothing, so logging is
	 * optional in the subclasses.
	 * 
	 * @param operation
	 *          The opration that is being logged.
	 * @param entities
	 *          The entities that are being manipulated.
	 * @param interval
	 *          Array of size 2 with the interval [a, b) (retrieves objects from index a through b-1).
	 */
	protected void log(CrudOperation operation, List<T> entities, int ... interval) {}

	/**
	 * Helper method that adds a validation error to an existing CRUD exception or creates a new one in case it doesn't
	 * exist yet. This method creates a global message.
	 * 
	 * @param crudException
	 *          The possibly-existing CRUD exception.
	 * @param message
	 *          The developer-friendly message for the exception.
	 * @param messageKey
	 *          The key to find the error message in the resource bundle.
	 * @param messageParams
	 *          The params to be inserted in the error message.
	 * 
	 * @return The newly-created CRUD exception or the old CRUD exception with a new validation error.
	 */
	protected CrudException addValidationError(CrudException crudException, String message, String messageKey, Object ... messageParams) {
		if (crudException == null) {
			crudException = new CrudException(message, messageKey, messageParams);
		}
		else {
			crudException.addValidationError(messageKey, messageParams);
		}
		return crudException;
	}

	/**
	 * Helper method that adds a validation error to an existing CRUD exception or creates a new one in case it doesn't
	 * exist yet. This method attaches the message to a specific field in the form.
	 * 
	 * @param crudException
	 *          The possibly-existing CRUD exception.
	 * @param message
	 *          The developer-friendly message for the exception.
	 * @param fieldName
	 *          The name of the field to which the message should be attached.
	 * @param messageKey
	 *          The key to find the error message in the resource bundle.
	 * @param messageParams
	 *          The params to be inserted in the error message.
	 * 
	 * @return The newly-created CRUD exception or the old CRUD exception with a new validation error.
	 */
	protected CrudException addValidationError(CrudException crudException, String message, String fieldName, String messageKey, Object ... messageParams) {
		if (crudException == null) {
			crudException = new CrudException(message, fieldName, messageKey, messageParams);
		}
		else {
			crudException.addValidationError(fieldName, messageKey, messageParams);
		}
		return crudException;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#validateCreate(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateCreate(T entity) throws CrudException {}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#validateUpdate(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateUpdate(T entity) throws CrudException {}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#validateDelete(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateDelete(T entity) throws CrudException {}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#count() */
	@Override
	public long count() {
		return getDAO().retrieveCount();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#countFiltered(br.com.engenhodesoftware.util.ejb3.application.filters.Filter, java.lang.String) */
	@Override
	public long countFiltered(Filter<?> filterType, String filter) {
		return getDAO().retrieveFilteredCount(filterType, filter);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#create(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void create(T entity) {
		// Validates the entity before persisting.
		entity = validate(entity, null);

		// Save the entity.
		log(CrudOperation.CREATE, entity);
		getDAO().save(entity);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#retrieve(java.lang.Long) */
	@Override
	public T retrieve(Long id) {
		// Retrieves the real entity from the database.
		T entity = getDAO().retrieveById(id);
		log(CrudOperation.RETRIEVE, entity);
		return entity;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#update(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void update(T entity) {
		// Validates the entity before persisting.
		entity = validate(entity, getDAO().retrieveById(entity.getId()));

		// Save the entity.
		log(CrudOperation.UPDATE, entity);
		getDAO().save(entity);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#delete(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void delete(T entity) {
		// Retrieves the real entity from the database.
		entity = getDAO().retrieveById(entity.getId());
		if (entity != null) {
			// Deletes the entity.
			getDAO().delete(entity);
			log(CrudOperation.DELETE, entity);
		}
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#list(int[]) */
	@Override
	public List<T> list(int ... interval) {
		List<T> entities = getDAO().retrieveSome(interval);
		log(CrudOperation.LIST, entities, interval);
		return entities;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal#filter(br.com.engenhodesoftware.util.ejb3.application.filters.Filter, java.lang.String, int[]) */
	@Override
	public List<T> filter(Filter<?> filter, String filterParam, int ... interval) {
		List<T> entities = getDAO().retrieveSomeWithFilter(filter, filterParam, interval);
		log(CrudOperation.LIST, entities, interval);
		return entities;
	}
}
