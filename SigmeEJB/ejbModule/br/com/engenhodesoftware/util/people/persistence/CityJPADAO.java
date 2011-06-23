package br.com.engenhodesoftware.util.people.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.State;
import br.com.engenhodesoftware.util.people.persistence.exceptions.CityNotFoundException;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
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

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getDomainClass() */
	@Override
	protected Class<City> getDomainClass() {
		return City.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.util.people.persistence.CityDAO#retrieveByName(java.lang.String) */
	@Override
	public List<City> findByName(String name) {
		logger.log(Level.INFO, "Retrieving City by name: {0}", name);

		// Constructs the query over the City class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<City> cq = cb.createQuery(City.class);
		Root<City> root = cq.from(City.class);

		// Filters the query with the name.
		cq.where(cb.like(cb.lower(root.get(CityJPAMetamodel.name)), name.toLowerCase() + "%"));
		cq.orderBy(cb.asc(root.get(CityJPAMetamodel.name)));

		// Returns the list of cities.
		return entityManager.createQuery(cq).getResultList();
	}

	/**
	 * @see br.com.engenhodesoftware.util.people.persistence.CityDAO#retrieveByNameAndStateAcronym(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public City retrieveByNameAndStateAcronym(String cityName, String stateAcronym) throws CityNotFoundException {
		logger.log(Level.INFO, "Retrieving City by name and state acronym: {0}, {1}", new Object[] { cityName, stateAcronym });

		// Constructs the query over the City class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<City> cq = cb.createQuery(City.class);
		Root<City> root = cq.from(City.class);
		Join<City, State> join = root.join(CityJPAMetamodel.state);

		// Filters the query with the name and state acronym.
		cq.where(cb.equal(root.get(CityJPAMetamodel.name), cityName), cb.equal(join.get(StateJPAMetamodel.acronym), stateAcronym));

		// Looks for a single result. Throws a checked exception if the entity is not found.
		try {
			return entityManager.createQuery(cq).getSingleResult();
		}
		catch (NoResultException e) {
			throw new CityNotFoundException(cityName, stateAcronym);
		}

	}
}
