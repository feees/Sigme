package br.org.feees.sigme.event.persistence;

import javax.ejb.Local;

import br.org.feees.sigme.event.domain.Subscriber;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Local
public interface SubscriberDAO extends BaseDAO<Subscriber>{

	public Subscriber retrieveBySpiritistAndEvent (Long spiritistId, Long eventId) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}
