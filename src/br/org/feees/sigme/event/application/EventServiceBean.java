package br.org.feees.sigme.event.application;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import br.org.feees.sigme.core.application.CoreInformation;
import br.org.feees.sigme.event.domain.Event;
import br.org.feees.sigme.event.domain.Subscriber;
import br.org.feees.sigme.event.persistence.EventDAO;
import br.org.feees.sigme.event.persistence.SubscriberDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudException;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
@TransactionAttribute
public class EventServiceBean extends CrudServiceBean<Event> implements EventService {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(EventServiceBean.class.getCanonicalName());
	
	@EJB 
	private EventDAO eventDAO;
	
	/** The information singleton for the core module. */
	@EJB
	private CoreInformation coreInformation;

	@Override
	protected Event createNewEntity() {
		return new Event();
	}

	@Override
	public BaseDAO<Event> getDAO() {
		return eventDAO;
	}
	
	@Override
	public void validateCreate(Event entity) throws CrudException {

	}

	@Override
	public void validateUpdate(Event entity) throws CrudException {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateDelete(Event entity) throws CrudException {
		// TODO Auto-generated method stub

	}

	@Override
	public void authorize() {
		// TODO Auto-generated method stub
	}
}
