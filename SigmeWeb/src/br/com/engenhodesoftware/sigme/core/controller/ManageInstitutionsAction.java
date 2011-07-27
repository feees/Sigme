package br.com.engenhodesoftware.sigme.core.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.engenhodesoftware.sigme.core.application.ManageInstitutionsService;
import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;
import br.com.engenhodesoftware.sigme.core.domain.Regional;
import br.com.engenhodesoftware.sigme.core.persistence.RegionalDAO;
import br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal;
import br.com.engenhodesoftware.util.ejb3.application.filters.Criterion;
import br.com.engenhodesoftware.util.ejb3.application.filters.CriterionType;
import br.com.engenhodesoftware.util.ejb3.application.filters.LikeFilter;
import br.com.engenhodesoftware.util.ejb3.application.filters.MultipleChoiceFilter;
import br.com.engenhodesoftware.util.ejb3.application.filters.ReverseMultipleChoiceFilter;
import br.com.engenhodesoftware.util.ejb3.controller.CrudAction;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.Telephone;
import br.com.engenhodesoftware.util.people.persistence.CityDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Institutions". This use case is a CRUD.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class ManageInstitutionsAction extends CrudAction<Institution> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageInstitutionsAction.class.getCanonicalName());

	/** The "Manage Institutions" service. */
	@EJB
	private ManageInstitutionsService manageInstitutionsService;

	/** The DAO for City objects. */
	@EJB
	private CityDAO cityDAO;

	/** The DAO for Regional objects. */
	@EJB
	private RegionalDAO regionalDAO;

	/** The controller class that holds references to constant lists, converters, etc. */
	@Inject
	private CoreController coreController;

	/** Output: the list of telephone numbers. */
	private List<Telephone> telephones;

	/** Input: the telephone that is selected in the data table. */
	private Telephone selectedTelephone;

	/** A backup telephone in case the user cancels the edition. */
	private Telephone backupTelephone;

	/** Getter for telephones. */
	public List<Telephone> getTelephones() {
		return telephones;
	}

	/** Getter for selectedTelephone. */
	public Telephone getSelectedTelephone() {
		return selectedTelephone;
	}

	/** Setter for selectedTelephone. */
	public void setSelectedTelephone(Telephone selectedTelephone) {
		this.selectedTelephone = selectedTelephone;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#getCrudService() */
	@Override
	protected CrudServiceLocal<Institution> getCrudService() {
		// Checks if the current user has the authorization to use this functionality.
		manageInstitutionsService.authorize();
		
		return manageInstitutionsService;
	}

	@Override
	public boolean getFacesRedirect() {
		return true;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#getBundleName() */
	@Override
	public String getBundleName() {
		return "msgsCore";
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#createNewEntity() */
	@Override
	protected Institution createNewEntity() {
		logger.log(Level.INFO, "Initializing an empty institution");

		// Create an empty entity.
		Institution newEntity = new Institution();
		newEntity.setAddress(new Address());

		// Create an empty telephone list.
		telephones = new ArrayList<Telephone>();

		return newEntity;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#checkSelectedEntity() */
	@Override
	protected void checkSelectedEntity() {
		logger.log(Level.INFO, "Checking selected institution: {0}", selectedEntity);

		// The address must not be null.
		if (selectedEntity.getAddress() == null)
			selectedEntity.setAddress(new Address());

		// Create the list of telephones with the already existing telephones. Also check for null.
		if (selectedEntity.getTelephones() == null)
			selectedEntity.setTelephones(new TreeSet<Telephone>());
		telephones = new ArrayList<Telephone>(selectedEntity.getTelephones());
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#initFilters() */
	@Override
	protected void initFilters() {
		logger.log(Level.INFO, "Initializing filter types");

		// One can filter institutions by name, acronym, city or Regional.
		addFilter(new LikeFilter("manageInstitutions.filter.byName", "name", getI18nMessage("msgsCore", "manageInstitutions.text.filter.byName")));
		addFilter(new LikeFilter("manageInstitutions.filter.byAcronym", "acronym", getI18nMessage("msgsCore", "manageInstitutions.text.filter.byAcronym")));
		addFilter(new LikeFilter("manageInstitutions.filter.byCity", "address.city.name", getI18nMessage("msgsCore", "manageInstitutions.text.filter.byCity")));

		// Regional filter is multiple choice. The options and labels for the options must be supplied.
		List<Regional> regionals = regionalDAO.retrieveAll();
		Map<String, String> labels = new LinkedHashMap<String, String>();
		for (Regional regional : regionals)
			labels.put(regional.getId().toString(), regional.getName());
		addFilter(new ReverseMultipleChoiceFilter<Regional>("manageInstitutions.filter.byRegional", "address.city", getI18nMessage("msgsCore", "manageInstitutions.text.filter.byRegional"), regionals, labels, "cities", new Criterion("type.partOfRegional", CriterionType.EQUALS, Boolean.TRUE)));

		// Type filter is multiple choice. The options and labels for the options must be supplied.
		List<InstitutionType> institutionTypes = coreController.getInstitutionTypes();
		labels = new LinkedHashMap<String, String>();
		for (InstitutionType type : institutionTypes)
			labels.put(type.getId().toString(), type.getType());
		addFilter(new MultipleChoiceFilter<InstitutionType>("manageInstitutions.filter.byType", "type", getI18nMessage("msgsCore", "manageInstitutions.text.filter.byType"), institutionTypes, labels));
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#prepEntity() */
	@Override
	protected void prepEntity() {
		logger.log(Level.INFO, "Preparing entity for storage: {0}", selectedEntity);

		// Inserts the telephone list in the entity.
		selectedEntity.setTelephones(new TreeSet<Telephone>(telephones));

		// Set the regional again, just in case the type of institution is set or changed after the city.
		setRegional();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#listTrash() */
	@Override
	protected String listTrash() {
		// List the acronyms of the deleted institutions.
		StringBuilder acronyms = new StringBuilder();
		for (Institution entity : trashCan)
			acronyms.append(entity.getAcronym()).append(", ");

		// Removes the final comma and returns the string.
		int length = acronyms.length();
		if (length > 0)
			acronyms.delete(length - 2, length);

		logger.log(Level.INFO, "Listing the institutions in the trash can: {0}", acronyms.toString());
		return acronyms.toString();
	}

	/**
	 * Analyzes the name that was given for the institution and, if the acronym field is still empty, suggests a value for
	 * it based on the given name. This method is intended to be used with AJAX.
	 */
	public void suggestAcronym() {
		// If the name was filled and the acronym is still empty, generate one.
		String name = selectedEntity.getName();
		String acronym = selectedEntity.getAcronym();
		if ((name != null) && ((acronym == null) || (acronym.length() == 0))) {
			// Generate the acronym joining together all upper-case letters of the name.
			StringBuilder acronymBuilder = new StringBuilder();
			char[] chars = name.toCharArray();
			for (char ch : chars)
				if (Character.isUpperCase(ch))
					acronymBuilder.append(ch);
			selectedEntity.setAcronym(acronymBuilder.toString());

			logger.log(Level.INFO, "Suggesting {0} as acronym for {1}", new Object[] { selectedEntity.getAcronym(), name });
		}
		else logger.log(Level.INFO, "Acronym not suggested: name = {0}, acronym = {1}", new Object[] { name, acronym });
	}

	/**
	 * Analyzes what has been written so far in the city field and, if not empty, looks for cities that start with the
	 * given name and returns them in a list, so a dynamic pop-up list can be displayed. This method is intended to be
	 * used with AJAX.
	 * 
	 * @param event
	 * @return
	 */
	public List<String> suggestCities(String query) {
		if (query.length() > 0) {

			try {
				Thread.sleep(1000);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			List<City> cities = cityDAO.findByName(query);
			logger.log(Level.INFO, "Searching for cities beginning with \"{0}\" returned {1} results", new Object[] { query, cities.size() });
			List<String> suggestions = new ArrayList<String>();
			for (City city : cities)
				suggestions.add(city.getName() + ", " + city.getState().getAcronym());
			return suggestions;
		}
		return null;
	}

	/**
	 * Handles the event of selection in the instution type menu. This method is intended to be used with AJAX.
	 * 
	 * @param event
	 *          The change event supplied by JSF's select-one-menu component.
	 */
	public void handleInstitutionTypeChange(AjaxBehaviorEvent event) {
		logger.log(Level.INFO, "Handling change event for institution type selection: {0}", event);
		setRegional();
	}

	/**
	 * Handles the event of selection in the auto-complete city field. This method is intended to be used with AJAX.
	 * 
	 * @param event
	 *          The selection event supplied by PrimeFaces' auto-complete component.
	 */
	public void handleCitySelection(SelectEvent event) {
		logger.log(Level.INFO, "Handling select event for city selection: {0}", event.getObject());
		setRegional();
	}

	/**
	 * Sets the regional for the selected institution according to its type and city.
	 */
	private void setRegional() {
		// Check the selected entity for null.
		if (selectedEntity != null) {
			// Checks if the institution type indicates that the isntitution is part of a regional.
			if ((selectedEntity.getType() != null) && (selectedEntity.getType().isPartOfRegional())) {
				// Check if the city has been set and, if so, retrieve the regional of the city.
				if ((selectedEntity != null) && (selectedEntity.getAddress() != null) && (selectedEntity.getAddress().getCity() != null)) {
					try {
						Regional regional = regionalDAO.retrieveByCity(selectedEntity.getAddress().getCity());
						selectedEntity.setRegional(regional);
						logger.log(Level.INFO, "Institution type is part of regional. Setting regional: {0}", regional);
					}
					catch (PersistentObjectNotFoundException e) {
						logger.log(Level.INFO, "Regional not found for city: {0}. Setting regional to null.", e.getParams()[0]);
						selectedEntity.setRegional(null);
					}
					catch (MultiplePersistentObjectsFoundException e) {
						logger.log(Level.SEVERE, "Multiple regionals found for city: {0}. Setting regional to null.", e.getParams()[0]);
						selectedEntity.setRegional(null);
					}
				}
			}

			// It's not part of a regional, so set it to null.
			else {
				logger.log(Level.INFO, "Institution type is not part of regional. Setting regional to null.");
				selectedEntity.setRegional(null);
			}
		}
	}

	/**
	 * Creates a new and empty telephone so the telephone fields can be filled. This method is intended to be used with
	 * AJAX.
	 */
	public void newTelephone() {
		logger.log(Level.INFO, "Creating an empty telephone to be filled");
		selectedTelephone = new Telephone();
		backupTelephone = null;
	}

	/**
	 * Backs up the selected telephone and proceeds to edit it. This method is intended to be used with AJAX.
	 */
	public void editTelephone() {
		// Check for null and backs up the selected telephone in order to be able to cancel the changes.
		if (selectedTelephone != null) {
			logger.log(Level.INFO, "Backing up the selected telephone for an edit: {0}", selectedTelephone);
			backupTelephone = new Telephone();
			backupTelephone.setNumber(selectedTelephone.getNumber());
			backupTelephone.setType(selectedTelephone.getType());
		}
	}

	/**
	 * Indicates if the user is editing a telephone or not. If not, she is creating a new one. This method is intended to
	 * be used with AJAX.
	 * 
	 * @return <code>true</code> if the telephone is being edited, <code>false</code> if it's a new telephone being
	 *         created.
	 */
	public boolean isTelephoneEdit() {
		return (backupTelephone != null);
	}

	/**
	 * Adds the telephone to the list (in case of a new telephone). This method is intended to be used with AJAX.
	 */
	public void saveTelephone() {
		// If it's an edit, the information has already been changed. No need to do anything.
		if (isTelephoneEdit()) {
			logger.log(Level.INFO, "Telephone is already on the list. Just saving: {0}", selectedTelephone);
		}

		// If not, it's a new telephone. Add it to the list of telephones.
		else {
			logger.log(Level.INFO, "Adding telephone to list: {0}", selectedTelephone);
			telephones.add(selectedTelephone);
		}
	}

	/**
	 * Cancels a telephone edit, undoing eventual changes from the backup information. This method is intended to be used
	 * with AJAX.
	 */
	public void cancelTelephone() {
		// Checks if it's an edit.
		if (isTelephoneEdit()) {
			logger.log(Level.INFO, "Telephone may have been changed: {0}. Undoing changes to: {1}", new Object[] { selectedTelephone, backupTelephone });
			selectedTelephone.setNumber(backupTelephone.getNumber());
			selectedTelephone.setType(backupTelephone.getType());
		}
	}

	/**
	 * Removes one of the telephones of the list of telephones of the institution. This method is intended to be used with
	 * AJAX.
	 */
	public void removeTelephone() {
		logger.log(Level.INFO, "Removing a telephone from the list: {0}", selectedTelephone);
		telephones.remove(selectedTelephone);
	}
}
