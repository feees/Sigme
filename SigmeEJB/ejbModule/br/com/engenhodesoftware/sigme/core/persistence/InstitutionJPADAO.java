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
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
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

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getDomainClass() */
	@Override
	protected Class<Institution> getDomainClass() {
		return Institution.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getOrderList(javax.persistence.criteria.CriteriaBuilder,
	 *      javax.persistence.criteria.Root)
	 */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Institution> root) {
		// Orders by name.
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(InstitutionJPAMetamodel.name)));
		return orderList;
	}

	/** @see br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO#retrieveInstitutionByName(java.lang.String) */
	@Override
	public Institution retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.INFO, "Retrieveing the institution whose name is \"{0}\"", name);

		// Constructs the query over the Institution class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Institution> cq = cb.createQuery(Institution.class);
		Root<Institution> root = cq.from(Institution.class);

		// Filters the query with the name.
		cq.where(cb.equal(root.get(InstitutionJPAMetamodel.name), name));
		return executeSingleResultQuery(cq, name);
	}

	/** @see br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO#retrieveByNameOrAcronym(java.lang.String) */
	@Override
	public List<Institution> findByNameOrAcronym(String param) {
		logger.log(Level.INFO, "Retrieveing all institutions whose name or acronym begins with \"{0}\"", param);

		// Constructs the query over the Institution class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Institution> cq = cb.createQuery(Institution.class);
		Root<Institution> root = cq.from(Institution.class);

		// Filters the query with the name or the acronym.
		String searchParam = param.toLowerCase() + "%";
		cq.where(cb.or(cb.like(cb.lower(root.get(InstitutionJPAMetamodel.name)), searchParam), cb.like(cb.lower(root.get(InstitutionJPAMetamodel.acronym)), searchParam)));
		cq.orderBy(cb.asc(root.get(InstitutionJPAMetamodel.name)));

		// Returns the list of institutions.
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<Institution> findByCity(String cityName) {
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
		return entityManager.createQuery(cq).getResultList();
	}
}
