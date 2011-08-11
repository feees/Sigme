package br.com.engenhodesoftware.util.people.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.State;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing a DAO for objects of the City domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.people.domain.City
 * @see br.com.engenhodesoftware.util.people.persistence.CityDAO
 */
@Stateless
public class CityJPADAO extends BaseJPADAO<City> implements CityDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(CityJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<City> getDomainClass() {
		return City.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.util.people.persistence.CityDAO#findByName(java.lang.String) */
	@Override
	public List<City> findByName(String name) {
		logger.log(Level.FINE, "Retrieving all cities whose name begins with \"{0}\"...", name);
		
		// Constructs the query over the City class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<City> cq = cb.createQuery(City.class);
		Root<City> root = cq.from(City.class);

		// Filters the query with the name.
		cq.where(cb.like(cb.lower(root.get(CityJPAMetamodel.name)), name.toLowerCase() + "%"));
		cq.orderBy(cb.asc(root.get(CityJPAMetamodel.name)));

		// Returns the list of cities.
		List<City> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve city by name containing \"{0}\" returned \"{1}\" cities", new Object[] { name, result.size() });
		return result;
	}

	/** @see br.com.engenhodesoftware.util.people.persistence.CityDAO#retrieveByNameAndStateAcronym(java.lang.String, java.lang.String) */
	@Override
	public City retrieveByNameAndStateAcronym(String name, String stateAcronym) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the city whose name is \"{0}\" and whose state has the acronym \"{1}\"...", new Object[] { name, stateAcronym });

		// Constructs the query over the City class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<City> cq = cb.createQuery(City.class);
		Root<City> root = cq.from(City.class);
		Join<City, State> join = root.join(CityJPAMetamodel.state);

		// Filters the query with the name and state acronym.
		cq.where(cb.equal(root.get(CityJPAMetamodel.name), name), cb.equal(join.get(StateJPAMetamodel.acronym), stateAcronym));
		City result = executeSingleResultQuery(cq, name, stateAcronym);
		logger.log(Level.INFO, "Retrieve city by the name \"{0}\" and state acronym \"{1}\" returned \"{2}\"", new Object[] { name, stateAcronym, result });
		return result;
	}
}
