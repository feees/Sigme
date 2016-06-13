package br.org.feees.sigme.event.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.org.feees.sigme.event.domain.Subscriber;
import br.org.feees.sigme.event.domain.Subscriber_;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Stateless
public class SubscriberJPADAO extends BaseJPADAO<Subscriber> implements SubscriberDAO {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SubscriberJPADAO.class.getCanonicalName());

	/**
	 * The application's persistent context provided by the application server.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Class<Subscriber> getDomainClass() {
		return Subscriber.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Subscriber retrieveBySpiritistAndEvent(Long spiritisId, Long eventId)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Subscriber> cq = cb.createQuery(Subscriber.class);
		Root<Subscriber> root = cq.from(Subscriber.class);

		cq.where(cb.equal(root.get(Subscriber_.spiritist), spiritisId));
		cq.where(cb.equal(root.get(Subscriber_.event), eventId));

		Subscriber subscriber = executeSingleResultQuery(cq, new Object[] { spiritisId, eventId });
		logger.log(Level.INFO,
				"Retrieve Subscriber by the Spiritist with id \"{0}\" to the event \"{1}\" returned \"{1}\"",
				new Object[] { spiritisId, eventId, subscriber });

		return subscriber;

	}

}
