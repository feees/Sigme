package br.org.feees.sigme.event.persistence;

import javax.ejb.Local;

import br.org.feees.sigme.event.domain.Event;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Local
public interface EventDAO extends BaseDAO<Event> {

	Event retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	boolean retrieveOwner(Long eventId, Long userId);

}
