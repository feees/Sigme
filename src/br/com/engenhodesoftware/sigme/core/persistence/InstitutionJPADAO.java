package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.Institution_;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing a DAO for objects of the Institution domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.Institution
 * @see br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO
 */
@Stateless
public class InstitutionJPADAO extends BaseJPADAO<Institution> implements InstitutionDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(InstitutionJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Institution> getDomainClass() {
		return Institution.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getOrderList(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Root) */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Institution> root) {
		// Orders by name.
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Institution_.name)));
		return orderList;
	}

	/** @see br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO#retrieveByName(java.lang.String) */
	@Override
	public Institution retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the institution whose name is \"{0}\"...", name);

		// Constructs the query over the Institution class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Institution> cq = cb.createQuery(Institution.class);
		Root<Institution> root = cq.from(Institution.class);

		// Filters the query with the name.
		cq.where(cb.equal(root.get(Institution_.name), name));
		Institution result = executeSingleResultQuery(cq, name);
		logger.log(Level.INFO, "Retrieve institution by the name \"{0}\" returned \"{1}\"", new Object[] { name, result });
		return result;
	}

	/** @see br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO#findByNameOrAcronym(java.lang.String) */
	@Override
	public List<Institution> findByNameOrAcronym(String param) {
		logger.log(Level.FINE, "Retrieving all institutions whose name or acronym contains \"{0}\"...", param);

		// Constructs the query over the Institution class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Institution> cq = cb.createQuery(Institution.class);
		Root<Institution> root = cq.from(Institution.class);

		// Filters the query with the name or the acronym.
		String searchParam = "%" + param.toLowerCase() + "%";
		cq.where(cb.or(cb.like(cb.lower(root.get(Institution_.name)), searchParam), cb.like(cb.lower(root.get(Institution_.acronym)), searchParam)));
		cq.orderBy(cb.asc(root.get(Institution_.name)));

		// Returns the list of institutions.
		List<Institution> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve institution by name or acronym containing \"{0}\" returned \"{1}\" institutions", new Object[] { param, result.size() });
		return result;
	}

	/** @see br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO#findByCity(java.lang.String) */
	@Override
	public List<Institution> findByCity(String cityName) {
		logger.log(Level.FINE, "Retrieving all institutions whose city name contains \"{0}\"...", cityName);

		// Constructs the query over the Institution class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Institution> cq = cb.createQuery(Institution.class);
		Root<Institution> root = cq.from(Institution.class);

		Metamodel metamodel = entityManager.getMetamodel();
		EntityType<Institution> institutionType = root.getModel();
		EntityType<Address> addressType = metamodel.entity(Address.class);
		EntityType<City> cityType = metamodel.entity(City.class);

		Join<Institution, Address> addressJoin = root.join(institutionType.getSingularAttribute("address", Address.class));
		Join<Address, City> cityJoin = addressJoin.join(addressType.getSingularAttribute("city", City.class));

		// Filters the query with the name or the acronym.
		String searchParam = "%" + cityName.toLowerCase() + "%";
		cq.where(cb.like(cb.lower(cityJoin.get(cityType.getSingularAttribute("name", String.class))), searchParam));

		// Returns the list of institutions.
		List<Institution> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve institution by city name containing \"{0}\" returned \"{1}\" institutions", new Object[] { cityName, result.size() });
		return result;
	}
}
