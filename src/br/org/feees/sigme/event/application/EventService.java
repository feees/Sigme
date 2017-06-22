package br.org.feees.sigme.event.application;

import java.util.List;

import javax.ejb.Local;

import br.org.feees.sigme.event.domain.Event;
import br.org.feees.sigme.event.domain.Subscriber;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

@Local
public interface EventService extends CrudService<Event> {	

}
