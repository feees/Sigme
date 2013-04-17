package br.com.engenhodesoftware.util.ejb3.application;

import java.io.Serializable;
import java.util.List;

import br.com.engenhodesoftware.util.ejb3.application.filters.Filter;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * TODO: document this type.
 *
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public interface ListingService<T extends PersistentObject> extends Serializable {
	/**
	 * TODO: document this method.
	 * @return
	 */
	BaseDAO<T> getDAO();

	/**
	 * Empty method called by CrudAction every time the CrudService class is obtained. If this method is overridden in the
	 * concrete implementation of the CrudService and annotated with RolesAllowed authorization will be enforced by the
	 * Crud framework.
	 */
	void authorize();

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
