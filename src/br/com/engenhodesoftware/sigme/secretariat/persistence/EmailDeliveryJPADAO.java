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

import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery;
import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery_;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;

/**
 * Stateless session bean implementing a DAO for objects of the EmailDelivery domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery
 * @see br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO
 */
@Stateless
public class EmailDeliveryJPADAO extends BaseJPADAO<EmailDelivery> implements EmailDeliveryDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(EmailDeliveryJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<EmailDelivery> getDomainClass() {
		return EmailDelivery.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO#findDeliveriesFromMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public List<EmailDelivery> findDeliveriesFromMailing(Mailing mailing) {
		logger.log(Level.FINE, "Retrieving all email deliveries from mailing \"{0}\" (sent on {1})...", new Object[] { mailing.getSubject(), mailing.getSentDate() });

		// Constructs the query over the EmailDelivery class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EmailDelivery> cq = cb.createQuery(EmailDelivery.class);
		Root<EmailDelivery> root = cq.from(EmailDelivery.class);

		// Filters the query with the mailing.
		cq.where(cb.equal(root.get(EmailDelivery_.mailing), mailing));
		cq.orderBy(cb.asc(root.get(EmailDelivery_.recipientEmail)));
		List<EmailDelivery> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve all email deliveries from mailing \"{0}\" (sent on {1}) returned {2} result(s).", new Object[] { mailing.getSubject(), mailing.getSentDate(), result.size() });
		return result;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO#findPendingDeliveriesFromMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public List<EmailDelivery> findPendingDeliveriesFromMailing(Mailing mailing) {
		logger.log(Level.FINE, "Retrieving pending email deliveries from mailing \"{0}\" (sent on {1})...", new Object[] { mailing.getSubject(), mailing.getSentDate() });

		// Constructs the query over the EmailDelivery class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EmailDelivery> cq = cb.createQuery(EmailDelivery.class);
		Root<EmailDelivery> root = cq.from(EmailDelivery.class);

		// Filters the query with the mailing and the pending status (i.e., only deliveries that have not been sent).
		cq.where(cb.and(cb.equal(root.get(EmailDelivery_.mailing), mailing)), cb.isFalse(root.get(EmailDelivery_.messageSent)));
		cq.orderBy(cb.asc(root.get(EmailDelivery_.recipientEmail)));
		List<EmailDelivery> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve pending email deliveries from mailing \"{0}\" (sent on {1}) returned {2} result(s).", new Object[] { mailing.getSubject(), mailing.getSentDate(), result.size() });
		return result;
	}
}
