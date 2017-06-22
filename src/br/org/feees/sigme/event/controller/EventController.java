package br.org.feees.sigme.event.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.feees.sigme.people.domain.Address;
import org.feees.sigme.people.domain.City;
import org.feees.sigme.people.persistence.CityDAO;
import org.primefaces.event.SelectEvent;

import br.org.feees.sigme.core.application.SessionInformation;
import br.org.feees.sigme.core.controller.CoreController;
import br.org.feees.sigme.core.controller.SessionController;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.event.application.EventService;
import br.org.feees.sigme.event.application.SubscriberService;
import br.org.feees.sigme.event.domain.Event;
import br.org.feees.sigme.event.domain.Subscriber;
import br.org.feees.sigme.event.persistence.EventDAO;
import br.org.feees.sigme.event.persistence.SubscriberDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class EventController extends CrudController<Event> {
	/** Serialization Id */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger
			.getLogger(EventController.class.getCanonicalName());

	/** The "Manage Events" service */
	@EJB
	private EventService eventService;
	
	@EJB
	private SubscriberService subscriberService;

	@EJB
	private EventDAO eventDAO;

	/** Information on the current visitor. */	
	
	/** Information on the current visitor. */
	@Inject
	private SessionController sessionController;
	
	@Inject
	private SubscriberController subscriberController;

	/**
	 * The controller class that holds references to constant lists, converters,
	 * etc.
	 */
	@Inject
	private CoreController coreController;

	/** The DAO for City objects. */
	@EJB
	private CityDAO cityDAO;

	@Override
	protected CrudService<Event> getCrudService() {
		eventService.authorize();
		return eventService;
	}

	@Override
	protected Event createNewEntity() {
		logger.log(Level.FINER, "Initializing an empty event...");
		Event event = new Event();
		event.setEventAddress(new Address());

		return event;
	}

	@Override
	protected void initFilters() {
		// Filter Event by Name
		addFilter(new LikeFilter("event.filter.byName", "eventName",
				getI18nMessage("msgsCore", "event.text.filter.byName")));

		// Filter Event by Institution
		addFilter(new LikeFilter("event.filter.byInstitution",
				"institution", getI18nMessage("msgsCore",
						"event.text.filter.byInstitution")));
	}

	/**
	 * Handles the event of selection in the auto-complete city field.
	 * 
	 * This method is intended to be used with AJAX.
	 * 
	 * @param event
	 *            The selection event supplied by PrimeFaces' auto-complete
	 *            component.
	 */
	public void handleCitySelection(SelectEvent event) {
		Object obj = event.getObject();
		logger.log(Level.FINER, "Handling select event for city ({0})...", obj);

		// If the city has been properly passed by the event, set it in the
		// object so the regional is properly handled.
		if (obj instanceof City) {
			selectedEntity.getEventAddress().setCity((City) obj);
			logger.log(Level.FINE,
					"City of event \"{0}\" has been set to \"{1}\"",
					new Object[] { selectedEntity, obj });
		}
		// setRegional();
	}

	/**
	 * Analyzes what has been written so far in the city field and, if not
	 * empty, looks for cities that start with the given name and returns them
	 * in a list, so a dynamic pop-up list can be displayed. This method is
	 * intended to be used with AJAX.
	 * 
	 * @param query
	 *            What has been written so far in the city field.
	 * 
	 * @return The list of City objects whose names match the specified query.
	 */
	public List<City> suggestCities(String query) {
		// Checks if something was indeed typed in the field.
		if (query.length() > 0) {
			// Uses the DAO to find the query and returns.
			List<City> cities = cityDAO.findByName(query);
			logger.log(
					Level.FINE,
					"Suggestion for cities beginning with \"{0}\" returned {1} results",
					new Object[] { query, cities.size() });
			return cities;
		}
		return null;
	}

	public String subscribe() throws Exception {
		return subscribe(null);
	}

	private String subscribe(Long id) throws Exception {
		logger.log(Level.INFO, "Subscribe in event");

		Subscriber subscriber = new Subscriber();

		// Set the event as the selected
		Event event = eventDAO.retrieveById(selectedEntity.getId());
		subscriber.setEvent(event);

		// Set the spiritist of the subscribe as the current user loged
		Spiritist currentUser = sessionController.getCurrentUser();
		if (currentUser != null) {

			subscriber.setSpiritist(currentUser);

			subscriber.setBadgeName(currentUser.getName());
			subscriber.setSubscribeDate(new Date());

			subscriberController.setSelectedEntity(subscriber);
			return subscriberController.save();
		} else {
			throw new Exception("Current user cannot be null.");
		}
	}

	public String manage() {
		return manage(null);
	}

	public String manage(Long id) {
		logger.log(Level.INFO, "Manage for entity retrieval");

		// Retrieves the existing entity that was selected, if not already done
		// by the JSF component.
		if (selectedEntity == null)
			retrieveExistingEntity(id);
		else {
			// Asks the CRUD service to fetch any lazy collection that possibly
			// exists.
			selectedEntity = getCrudService().fetchLazy(selectedEntity);
			checkSelectedEntity();
		}

		// Goes to the form.
		return getViewPath() + "manage.xhtml?faces-redirect="
				+ getFacesRedirect();
	}

	public boolean isOwner() {
		logger.log(Level.INFO, "Retrieve Owner of Event");
		if (selectedEntity == null) {
			logger.log(Level.INFO, "Haven't selected any entity!");
			return false;
		}

		Spiritist currentUser = sessionController.getCurrentUser();
		if (currentUser == null) {
			logger.log(Level.INFO, "Cannot retrieve the current user.");
			return false;
		}

		return eventDAO.retrieveOwner(selectedEntity.getId(),
				currentUser.getId());
	}

	@Override
	protected void checkSelectedEntity() {
		logger.log(Level.INFO, "hello");
		super.checkSelectedEntity();
	}
	
	/**
	 * Retrieve the subscriber's list of an event
	 * @return
	 */
	public List<Subscriber> retrieveSubscribers() {
		if (this.selectedEntity != null) {
			return subscriberService.retrieveSubscribersByEvent(this.selectedEntity.getId());
		}
		return null;
	}
}
