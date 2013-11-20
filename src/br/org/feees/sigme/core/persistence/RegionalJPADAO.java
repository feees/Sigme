package br.org.feees.sigme.core.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;
import br.org.feees.sigme.core.domain.Regional;
import br.org.feees.sigme.core.domain.Regional_;

/**
 * Stateless session bean implementing a DAO for objects of the Regional domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Regional
 * @see br.org.feees.sigme.core.persistence.RegionalDAO
 */
@Stateless
public class RegionalJPADAO extends BaseJPADAO<Regional> implements RegionalDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(RegionalJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Regional> getDomainClass() {
		return Regional.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getOrderList(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Root) */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Regional> root) {
		// Orders by number.
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Regional_.number)));
		return orderList;
	}

	/** @see br.org.feees.sigme.core.persistence.RegionalDAO#retrieveByCity(br.com.engenhodesoftware.util.people.domain.City) */
	@Override
	public Regional retrieveByCity(City city) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the regional to which the city \"{0}\" belongs...", city);

		// Constructs the query over the Regional class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Regional> cq = cb.createQuery(Regional.class);
		Root<Regional> root = cq.from(Regional.class);

		// Filters the query with the city.
		cq.where(cb.isMember(city, root.get(Regional_.cities)));
		Regional result = executeSingleResultQuery(cq, city);
		logger.log(Level.INFO, "Retrieve regional of city \"{0}\" returned \"{1}\"", new Object[] { city, result });
		return result;
	}
}
