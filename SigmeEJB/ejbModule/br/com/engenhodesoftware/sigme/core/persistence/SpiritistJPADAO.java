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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Stateless
public class SpiritistJPADAO extends BaseJPADAO<Spiritist> implements SpiritistDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SpiritistJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Spiritist> getDomainClass() {
		return Spiritist.class;
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
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Spiritist> root) {
		// Orders by name.
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(SpiritistJPAMetamodel.name)));
		return orderList;
	}

	/** @see br.com.engenhodesoftware.sigme.core.persistence.SpiritistDAO#retrieveByEmail(java.lang.String) */
	@Override
	public Spiritist retrieveByEmail(String email) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.INFO, "Retrieveing the spiritist whose e-mail is \"{0}\"", email);

		// Constructs the query over the Spiritist class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Spiritist> cq = cb.createQuery(Spiritist.class);
		Root<Spiritist> root = cq.from(Spiritist.class);

		// Filters the query with the email.
		cq.where(cb.equal(root.get(SpiritistJPAMetamodel.email), email));
		return executeSingleResultQuery(cq, email);
	}

	/** @see br.com.engenhodesoftware.sigme.core.persistence.SpiritistDAO#retrieveByName(java.lang.String) */
	@Override
	public List<Spiritist> findByName(String param) {
		logger.log(Level.INFO, "Retrieveing all spiritists whose name contain \"{0}\"", param);

		// Constructs the query over the Institution class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Spiritist> cq = cb.createQuery(Spiritist.class);
		Root<Spiritist> root = cq.from(Spiritist.class);

		// Filters the query with the name or the acronym.
		cq.where(cb.like(cb.lower(root.get(SpiritistJPAMetamodel.name)), "%" + param.toLowerCase() + "%"));
		cq.orderBy(cb.asc(root.get(SpiritistJPAMetamodel.name)));

		// Returns the list of spiritists.
		return entityManager.createQuery(cq).getResultList();
	}
}
