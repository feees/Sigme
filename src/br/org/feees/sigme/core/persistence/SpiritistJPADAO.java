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

import org.feees.sigme.people.domain.Person_;

import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.domain.Spiritist_;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing a DAO for objects of the Spiritist domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Spiritist
 * @see br.org.feees.sigme.core.persistence.SpiritistDAO
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

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Spiritist> getDomainClass() {
		return Spiritist.class;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @see br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO#getOrderList(javax.persistence.criteria.CriteriaBuilder,
	 *      javax.persistence.criteria.Root)
	 */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<Spiritist> root) {
		// Orders by name.
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(Person_.name)));
		return orderList;
	}

	/** @see br.org.feees.sigme.core.persistence.SpiritistDAO#retrieveByEmail(java.lang.String) */
	@Override
	public Spiritist retrieveByEmail(String email) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the spiritist whose e-mail is \"{0}\"...", email);

		// Constructs the query over the Spiritist class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Spiritist> cq = cb.createQuery(Spiritist.class);
		Root<Spiritist> root = cq.from(Spiritist.class);

		// Filters the query with the email.
		cq.where(cb.equal(root.get(Spiritist_.email), email));
		Spiritist result = executeSingleResultQuery(cq, email);
		logger.log(Level.INFO, "Retrieve spiritist by the email \"{0}\" returned \"{1}\"", new Object[] { email, result });
		return result;
	}

	/** @see br.org.feees.sigme.core.persistence.SpiritistDAO#findByName(java.lang.String) */
	@Override
	public List<Spiritist> findByName(String param) {
		logger.log(Level.FINE, "Retrieving all spiritists whose name contain \"{0}\"...", param);

		// Constructs the query over the Spiritist class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Spiritist> cq = cb.createQuery(Spiritist.class);
		Root<Spiritist> root = cq.from(Spiritist.class);

		// Filters the query with the name.
		cq.where(cb.like(cb.lower(root.get(Person_.name)), "%" + param.toLowerCase() + "%"));
		cq.orderBy(cb.asc(root.get(Person_.name)));

		// Returns the list of spiritists.
		List<Spiritist> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve spiritist by name containing \"{0}\" returned \"{1}\" spiritists", new Object[] { param, result.size() });
		return result;
	}
}
