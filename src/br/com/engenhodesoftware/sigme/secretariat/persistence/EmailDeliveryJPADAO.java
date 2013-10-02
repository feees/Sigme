package br.com.engenhodesoftware.sigme.secretariat.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @param cb
	 * @param cq
	 * @param root
	 * @param type
	 * @param whereClause
	 * @return
	 */
	private long countDeliveries(Mailing mailing, CriteriaBuilder cb, CriteriaQuery<Long> cq, Root<EmailDelivery> root, String type, Expression<Boolean> whereClause) {
		logger.log(Level.FINER, "Counting {0} email deliveries from mailing \"{1}\" (sent on {2})...", new Object[] { type, mailing.getSubject(), mailing.getSentDate() });

		// Filters the query with the specified where clause and returns the count.
		cq.select(cb.count(root));
		cq.where(whereClause);
		long count = ((Long) entityManager.createQuery(cq).getSingleResult()).longValue();
		logger.log(Level.FINE, "Counted {0} email deliveries from mailing \"{1}\" (sent on {2}): {3} object(s).", new Object[] { type, mailing.getSubject(), mailing.getSentDate(), count });
		return count;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO#countDeliveriesFromMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countDeliveriesFromMailing(Mailing mailing) {
		// Constructs the query over the EmailDelivery class to count instances.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<EmailDelivery> root = cq.from(EmailDelivery.class);
		return countDeliveries(mailing, cb, cq, root, "", cb.equal(root.get(EmailDelivery_.mailing), mailing));
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO#countPendingDeliveriesFromMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countPendingDeliveriesFromMailing(Mailing mailing) {
		// Constructs the query over the EmailDelivery class to count instances.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<EmailDelivery> root = cq.from(EmailDelivery.class);
		return countDeliveries(mailing, cb, cq, root, "pending", cb.and(cb.equal(root.get(EmailDelivery_.mailing), mailing), cb.isFalse(root.get(EmailDelivery_.messageSent))));
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO#countSentDeliveriesFromMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countSentDeliveriesFromMailing(Mailing mailing) {
		// Constructs the query over the EmailDelivery class to count instances.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<EmailDelivery> root = cq.from(EmailDelivery.class);
		return countDeliveries(mailing, cb, cq, root, "sent", cb.and(cb.equal(root.get(EmailDelivery_.mailing), mailing), cb.isTrue(root.get(EmailDelivery_.messageSent)), cb.isNull(root.get(EmailDelivery_.errorMessage))));
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO#countErrorDeliveriesFromMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countErrorDeliveriesFromMailing(Mailing mailing) {
		// Constructs the query over the EmailDelivery class to count instances.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<EmailDelivery> root = cq.from(EmailDelivery.class);
		return countDeliveries(mailing, cb, cq, root, "error", cb.and(cb.equal(root.get(EmailDelivery_.mailing), mailing), cb.isTrue(root.get(EmailDelivery_.messageSent)), cb.isNotNull(root.get(EmailDelivery_.errorMessage))));
	}
}
