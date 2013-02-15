package br.com.engenhodesoftware.sigme.secretariat.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList_;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing a DAO for objects of the MailingList domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingList
 * @see br.com.engenhodesoftware.sigme.secretariat.persistence.MailingListDAO
 */
@Stateless
public class MailingListJPADAO extends BaseJPADAO<MailingList> implements MailingListDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(MailingListJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<MailingList> getDomainClass() {
		return MailingList.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.MailingListDAO#retrieveByName(java.lang.String) */
	@Override
	public MailingList retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		logger.log(Level.FINE, "Retrieving the mailing list whose name is \"{0}\"...", name);

		// Constructs the query over the MailingList class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MailingList> cq = cb.createQuery(MailingList.class);
		Root<MailingList> root = cq.from(MailingList.class);

		// Filters the query with the name.
		cq.where(cb.equal(root.get(MailingList_.name), name));
		MailingList result = executeSingleResultQuery(cq, name);
		logger.log(Level.INFO, "Retrieve mailing list by the name \"{0}\" returned \"{1}\"", new Object[] { name, result });
		return result;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.MailingListDAO#findByName(java.lang.String) */
	@Override
	public List<MailingList> findByName(String param) {
		logger.log(Level.FINE, "Retrieving all mailing lists whose name contains \"{0}\"...", param);

		// Constructs the query over the MailingList class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MailingList> cq = cb.createQuery(MailingList.class);
		Root<MailingList> root = cq.from(MailingList.class);

		// Filters the query with the name or the acronym.
		cq.where(cb.like(cb.lower(root.get(MailingList_.name)), "%" + param.toLowerCase() + "%"));
		cq.orderBy(cb.asc(root.get(MailingList_.name)));

		// Returns the list of mailing lists.
		List<MailingList> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve mailing list by name containing \"{0}\" returned \"{1}\" mailing lists", new Object[] { param, result.size() });
		return result;
	}
}
