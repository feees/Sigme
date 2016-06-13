package br.org.feees.sigme.event.application;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import br.org.feees.sigme.core.application.CoreInformation;
import br.org.feees.sigme.event.domain.Event;
import br.org.feees.sigme.event.persistence.EventDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudException;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
@TransactionAttribute
public class ManageEventsServiceBean extends CrudServiceBean<Event> implements ManageEventsService {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageEventsServiceBean.class.getCanonicalName());
	
	@EJB 
	private EventDAO eventsDAO;
	
	/** The information singleton for the core module. */
	@EJB
	private CoreInformation coreInformation;

	@Override
	protected Event createNewEntity() {
		return new Event();
	}

	@Override
	public BaseDAO<Event> getDAO() {
		return eventsDAO;
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
