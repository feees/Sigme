package br.org.feees.sigme.event.controller;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.org.feees.sigme.core.application.SessionInformation;
import br.org.feees.sigme.core.controller.SessionController;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.persistence.SpiritistDAO;
import br.org.feees.sigme.event.application.SubscriberService;
import br.org.feees.sigme.event.domain.Event;
import br.org.feees.sigme.event.domain.Subscriber;
import br.org.feees.sigme.event.persistence.EventDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class SubscriberController extends CrudController<Subscriber>{

	/** Serialization Id */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SubscriberController.class.getCanonicalName());

	/** The "Manage Events" service */
	@EJB
	private SubscriberService subscriberService;
	
	@EJB
	private EventDAO eventDAO;
	
	@EJB
	private SpiritistDAO spiritistDAO;
	
	@Inject
	private SessionController sessionController;
	
	@Override
	protected CrudService<Subscriber> getCrudService() {
		subscriberService.authorize();
		return subscriberService;
	}

	@Override
	protected Subscriber createNewEntity() {
		logger.log(Level.FINER, "Initializing an empty subscriber...");
		Subscriber subscriber = new Subscriber();
		
		subscriber.setEvent(new Event());
		subscriber.setSpiritist(new Spiritist());
		
		return subscriber;
	}

	@Override
	protected void prepEntity() {
		logger.log(Level.FINER, "Preparing subscriber for storage ({0})...", selectedEntity);
		
		Spiritist currentUser = sessionController.getCurrentUser();
		selectedEntity.setSpiritist(currentUser);
		selectedEntity.setSubscribeDate(new Date());
	}
	
	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void checkSelectedEntity() {
		logger.log(Level.INFO, "hello");
		super.checkSelectedEntity();
	}
}
