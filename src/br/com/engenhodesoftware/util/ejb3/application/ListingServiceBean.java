package br.com.engenhodesoftware.util.ejb3.application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.engenhodesoftware.util.ejb3.application.filters.Filter;
import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * TODO: document this type.
 *
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public abstract class ListingServiceBean<T extends PersistentObject> implements ListingService<T> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ListingServiceBean.class.getCanonicalName());

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
	protected void log(CrudOperation operation, List<T> entities, int ... interval) {
		logger.log(Level.FINE, "Logging (for operations over multiple entities) not overridden by subclass. No need for this type of logging.");
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.ListingService#authorize() */
	public void authorize() {
		logger.log(Level.FINE, "Authorization not overridden by subclass. No need for authorization.");
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.ListingService#count() */
	@Override
	public long count() {
		logger.log(Level.FINER, "Retrieving the object count...");
		return getDAO().retrieveCount();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.ListingService#countFiltered(br.com.engenhodesoftware.util.ejb3.application.filters.Filter, java.lang.String) */
	@Override
	public long countFiltered(Filter<?> filter, String value) {
		logger.log(Level.FINER, "Retrieving a filtered object count (filter \"{0}\" with value \"{1}\")...", new Object[] { filter.getKey(), value });
		return getDAO().retrieveFilteredCount(filter, value);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.ListingService#list(int[]) */
	@Override
	public List<T> list(int ... interval) {
		List<T> entities = getDAO().retrieveSome(interval);
		log(CrudOperation.LIST, entities, interval);
		return entities;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.ListingService#filter(br.com.engenhodesoftware.util.ejb3.application.filters.Filter, java.lang.String, int[]) */
	@Override
	public List<T> filter(Filter<?> filter, String filterParam, int ... interval) {
		List<T> entities = getDAO().retrieveSomeWithFilter(filter, filterParam, interval);
		log(CrudOperation.LIST, entities, interval);
		return entities;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.ListingService#fetchLazy(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public T fetchLazy(T entity) {
		// Default implementation is to return the entity itself (there are no lazy attributes).
		logger.log(Level.FINEST, "Using default implementation for fetchLazy(): returning the same entity, unchanged");
		return entity;
	}
}
