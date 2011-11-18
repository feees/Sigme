package br.com.engenhodesoftware.util.ejb3.application;

import java.io.Serializable;
import java.util.List;

import br.com.engenhodesoftware.util.ejb3.application.filters.Filter;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * Interface for application classes (service classes) that implement CRUD use cases (use cases that define five
 * scenarios: list, create, retrieve, update and delete). A CRUD use case, and therefore its implementing class, refers
 * to a class, that is manipulated in that use case.
 * 
 * This interface defines methods that service classes should provide implementation for. It uses a generic types,
 * <code>T</code>, to represent the class that is manipulated by the use case, which must implement PersistentObject.
 * 
 * For instance, to implement a CRUD service for a Product class, use:
 * <code>public class ProductCrudService implements CrudServiceLocal&lt;Product&gt;</code>. Product must implement
 * PersistentObject.
 * 
 * This interface can be used together with interfaces/classes that are used in the presentation layer, forming a simple
 * CRUD framework that integrates with Java EE 6 and makes the task of implementing CRUD use cases easier. It also
 * provides a default implementation, CrudService.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceBean
 * @see br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public interface CrudService<T extends PersistentObject> extends Serializable {
	/**
	 * Empty method called by CrudAction every time the CrudService class is obtained. If this method is overridden in the
	 * concrete implementation of the CrudService and annotated with RolesAllowed authorization will be enforced by the
	 * Crud framework.
	 */
	void authorize();
	
	/**
	 * TODO: document this method.
	 * @return
	 */
	BaseDAO<T> getCrudDAO();

	/**
	 * Checks if there are errors with object creation.
	 * 
	 * @param entity
	 *          Entity object coming from the presentation layer, containing the data that is going to be sent to the
	 *          create() method for entity creation.
	 * 
	 * @throws Exception
	 *           In case business constraints aren't followed.
	 */
	void validateCreate(T entity) throws CrudException;

	/**
	 * Checks if there are errors with object update.
	 * 
	 * @param entity
	 *          Entity object coming from the presentation layer, containing the ID of the existing entity and the data,
	 *          both of which are going to be sent to the update() method for entity update.
	 * 
	 * @throws Exception
	 *           In case business constraints aren't followed.
	 */
	void validateUpdate(T entity) throws CrudException;

	/**
	 * Checks if there are errors with object deletion.
	 * 
	 * @param entity
	 *          Entity object coming from the presentation layer, containing the ID of the existing entity that is going
	 *          to be sent to the delete() method for entity deletion.
	 * 
	 * @throws Exception
	 *           In case business constraints aren't followed.
	 */
	void validateDelete(T entity) throws CrudException;

	/**
	 * Informs how many persistent objects of the manipulated class exist in the persistent store.
	 * 
	 * @return The persistent object count.
	 */
	long count();

	/**
	 * Informs how many persistent objects of the manipulated class match the filter criteria.
	 * 
	 * @param filterType
	 *          The type of filtering.
	 * @param filter
	 *          The search string for the filtering.
	 * 
	 * @return The persistent object count.
	 */
	long countFiltered(Filter<?> filterType, String filter);

	/**
	 * Creates a new entity of the manipulated class, i.e., persists a new instance.
	 * 
	 * @param entity
	 *          A new entity object to be persisted.
	 */
	void create(T entity);

	/**
	 * Retrieves an existing entity from the persistent store, given its ID.
	 * 
	 * @param id
	 *          The ID of the existing entity.
	 * 
	 * @return The existing entity or <code>null</code>, if the given ID doesn't correspond to any entities.
	 */
	T retrieve(Long id);

	/**
	 * Updates the data of an existing entity in the persistent store.
	 * 
	 * @param entity
	 *          An existing entity object to be persisted.
	 */
	void update(T entity);

	/**
	 * Deletes an existing entity, i.e., removes it from the persistent store.
	 * 
	 * @param entity
	 *          An existing entity object to be deleted.
	 */
	void delete(T entity);

	/**
	 * List existing entities, given a range.
	 * 
	 * @param interval
	 *          Array of size 2 with the interval [a, b) (retrieves objects from index a through b-1).
	 * 
	 * @return A list of existing entities in the given range (an empty list if none exist).
	 */
	List<T> list(int ... interval);

	/**
	 * List existing entities given a filter and a range.
	 * 
	 * @param filter
	 *          Type of filter to apply.
	 * @param filterParam
	 *          Filter parameter.
	 * @param interval
	 *          Array of size 2 with the interval [a, b) (retrieves objects from index a through b-1).
	 * 
	 * @return A list of existing entities in the given range that match the given filter (an empty list if none exist).
	 */
	List<T> filter(Filter<?> filter, String filterParam, int ... interval);

	/**
	 * Fetches all lazy attributes of the entity from the persistent store. If there are no lazy attributes, this method
	 * should return the entity itself.
	 * 
	 * @param entity
	 *          The entity whose lazy attributes should be retrieved.
	 * 
	 * @return The same entity, with lazy attributes loaded.
	 */
	T fetchLazy(T entity);
}
