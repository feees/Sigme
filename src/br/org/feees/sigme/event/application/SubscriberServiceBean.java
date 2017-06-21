package br.org.feees.sigme.event.application;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.event.domain.Event;
import br.org.feees.sigme.event.domain.Subscriber;
import br.org.feees.sigme.event.persistence.SubscriberDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudException;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Stateless
@TransactionAttribute
public class SubscriberServiceBean extends CrudServiceBean<Subscriber> implements SubscriberService {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SubscriberServiceBean.class.getCanonicalName());

	@EJB
	private SubscriberDAO subscriberDAO;

	@Override
	public BaseDAO<Subscriber> getDAO() {
		return subscriberDAO;
	}

	@Override
	protected Subscriber createNewEntity() {
		Subscriber s = new Subscriber();
		s.setEvent(new Event());
		s.setSpiritist(new Spiritist());
		s.setSubscribeDate(new Date());
		
		return s;
	}

	@Override
	public void authorize() {

	}

	@Override
	public void validateCreate(Subscriber entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "Spiritist already in the event!";

		Long spiritistId = entity.getSpiritist().getId();
		Long eventId = entity.getEvent().getId();

		// Validates business rules.
		// Rule 1: Spiritist cannot subscribe to the same event
		try {
			Subscriber anotherEntity = subscriberDAO.retrieveBySpiritistAndEvent(spiritistId, eventId);
			if (anotherEntity != null) {
				logger.log(Level.INFO,
						"Subscribe in event \"{0}\" violates validation rule 1: Spiritis cannot subscribe twice to an event!",
						new Object[] { entity.getEvent().getEventName() });
				crudException = addValidationError(crudException, crudExceptionMessage, "badgeName",
						"subscriber.error.multipleSubscribers");
			}
		} catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - Spiritist haven't subscribed to the event: {0}",
					entity.getEvent().getEventName());
		} catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Spirititst \"" + entity.getSpiritist().getName()
					+ "is already subscribed to the event \"" + entity.getEvent().getEventName()+ "\", and throw the exception: " + e);
			crudException = addValidationError(crudException, crudExceptionMessage, "badgeName",
					"manageInstitutions.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;

	}
	
	
	
	@Override
	public Subscriber fetchLazy(Subscriber entity) {
		logger.log(Level.FINER, "Fecthing lazy attributes for subscribers \"{0}\"", entity);
		entity = getDAO().merge(entity);
		return entity;
	}
}
