package br.org.feees.sigme.event.application;

import javax.ejb.Local;

import br.org.feees.sigme.event.domain.Event;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

@Local
public interface ManageEventsService extends CrudService<Event> {

}
