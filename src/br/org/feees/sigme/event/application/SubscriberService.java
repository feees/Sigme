package br.org.feees.sigme.event.application;

import java.util.List;

import br.org.feees.sigme.event.domain.Subscriber;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

public interface SubscriberService extends CrudService<Subscriber>{
	
	public List<Subscriber> retrieveSubscribersByEvent(Long eventId);

}
