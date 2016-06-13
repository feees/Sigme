package br.org.feees.sigme.event.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.feees.sigme.event.domain.Event;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Stateless
public class EventJPADAO extends BaseJPADAO<Event> implements EventDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(EventJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Class<Event> getDomainClass() {
		return Event.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public Event retrieveByName(String name)
			throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean retrieveOwner(Long eventId, Long userId) {
		logger.log(Level.FINE, "Retrieving the event whose id is \"{0}\"...", eventId);
		
		Event event = this.retrieveById(eventId);
		
		if (event != null) {
			if (event.getSpiritistOwner().getId().equals(userId)) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}

}
