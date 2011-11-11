package br.com.engenhodesoftware.sigme.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.core.application.ManageSpiritistsService;
import br.com.engenhodesoftware.sigme.core.domain.Attendance;
import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO;
import br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal;
import br.com.engenhodesoftware.util.ejb3.application.filters.Criterion;
import br.com.engenhodesoftware.util.ejb3.application.filters.CriterionType;
import br.com.engenhodesoftware.util.ejb3.application.filters.LikeFilter;
import br.com.engenhodesoftware.util.ejb3.application.filters.ManyToManyFilter;
import br.com.engenhodesoftware.util.ejb3.controller.CrudAction;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.ContactType;
import br.com.engenhodesoftware.util.people.domain.Telephone;
import br.com.engenhodesoftware.util.people.persistence.CityDAO;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage Spiritist".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3..
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class ManageSpiritistsAction extends CrudAction<Spiritist> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageSpiritistsAction.class.getCanonicalName());

	/** The "Manage Spiritist" service. */
	@EJB
	private ManageSpiritistsService manageSpiritistsService;

	/** The DAO for City objects. */
	@EJB
	private CityDAO cityDAO;

	/** The DAO for Institution objects. */
	@EJB
	private InstitutionDAO institutionDAO;

	/** Output: the list of telephone numbers. */
	private List<Telephone> telephones;

	/** Input: a telephone being added or edited. */
	private Telephone telephone;

	/** Output: the list of attendances. */
	private List<Attendance> attendances;

	/** Input: the attendance being added or edited. */
	private Attendance attendance;

	/** Input: the new password to set. */
	private String newPassword;

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#getCrudService() */
	@Override
	protected CrudServiceLocal<Spiritist> getCrudService() {
		// Checks if the current user has the authorization to use this functionality.
		manageSpiritistsService.authorize();

		return manageSpiritistsService;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#createNewEntity() */
	@Override
	protected Spiritist createNewEntity() {
		logger.log(Level.FINER, "Initializing an empty spiritist...");

		// Create an empty entity.
		Spiritist newEntity = new Spiritist();
		newEntity.setAddress(new Address());

		// Create empty telephone and attendance lists.
		telephones = new ArrayList<Telephone>();
		attendances = new ArrayList<Attendance>();

		return newEntity;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#checkSelectedEntity() */
	@Override
	protected void checkSelectedEntity() {
		logger.log(Level.FINER, "Checking selected spiritist ({0})...", selectedEntity);

		// The address must not be null.
		if (selectedEntity.getAddress() == null)
			selectedEntity.setAddress(new Address());

		// Create the list of telephones with the already existing telephones. Also check for null.
		if (selectedEntity.getTelephones() == null)
			selectedEntity.setTelephones(new TreeSet<Telephone>());
		telephones = new ArrayList<Telephone>(selectedEntity.getTelephones());

		// Same for attendances.
		if (selectedEntity.getAttendances() == null)
			selectedEntity.setAttendances(new TreeSet<Attendance>());
		attendances = new ArrayList<Attendance>(selectedEntity.getAttendances());
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#initFilters() */
	@Override
	protected void initFilters() {
		logger.log(Level.FINER, "Initializing filter types...");

		// One can filter spiritists by name or e-mail.
		addFilter(new LikeFilter("manageSpiritists.filter.byName", "name", getI18nMessage("msgsCore", "manageSpiritists.text.filter.byName")));
		addFilter(new LikeFilter("manageSpiritists.filter.byEmail", "email", getI18nMessage("msgsCore", "manageSpiritists.text.filter.byEmail")));
		addFilter(new ManyToManyFilter("manageSpiritists.filter.byActiveAttendance", "attendances", getI18nMessage("msgsCore", "manageSpiritists.text.filter.byActiveAttendance"), "institution.name, institution.acronym", true, new Criterion("endDate", CriterionType.IS_NULL)));
		addFilter(new ManyToManyFilter("manageSpiritists.filter.byInactiveAttendance", "attendances", getI18nMessage("msgsCore", "manageSpiritists.text.filter.byInactiveAttendance"), "institution.name, institution.acronym", true, new Criterion("endDate", CriterionType.IS_NOT_NULL)));
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#prepEntity() */
	@Override
	protected void prepEntity() {
		logger.log(Level.FINER, "Preparing spiritist for storage ({0})...", selectedEntity);

		// Sets the new password.
		selectedEntity.setPassword(newPassword);

		// Inserts telephone and attendance lists in the entity.
		selectedEntity.setTelephones(new TreeSet<Telephone>(telephones));
		selectedEntity.setAttendances(new TreeSet<Attendance>(attendances));
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#summarizeSelectedEntity() */
	@Override
	protected String summarizeSelectedEntity() {
		return (selectedEntity == null) ? "" : selectedEntity.getShortName();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#listTrash() */
	@Override
	protected String listTrash() {
		// List the short names of the deleted spiritists.
		StringBuilder acronyms = new StringBuilder();
		for (Spiritist entity : trashCan) {
			acronyms.append(entity.getShortName()).append(", ");
		}

		// Removes the final comma and returns the string.
		int length = acronyms.length();
		if (length > 0) acronyms.delete(length - 2, length);

		logger.log(Level.INFO, "List of spiritists in the trash can: {0}", acronyms.toString());
		return acronyms.toString();
	}

	/**
	 * Analyzes the name that was given to the spiritist and, if the short name field is still empty, suggests a value for
	 * it based on the given name. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void suggestShortName() {
		// If the name was filled and the short name is still empty, suggest the first name as short name.
		String name = selectedEntity.getName();
		String shortName = selectedEntity.getShortName();
		if ((name != null) && ((shortName == null) || (shortName.length() == 0))) {
			int idx = name.indexOf(" ");
			selectedEntity.setShortName((idx == -1) ? name : name.substring(0, idx).trim());

			logger.log(Level.FINE, "Suggested \"{0}\" as short name for \"{1}\"", new Object[] { selectedEntity.getShortName(), name });
		}
		else logger.log(Level.FINEST, "Short name not suggested: empty name or short name already filled (name is \"{0}\", shortName is \"{1}\"", new Object[] { name, shortName });
	}

	/**
	 * Analyzes what has been written so far in the city field and, if not empty, looks for cities that start with the
	 * given name and returns them in a list, so a dynamic pop-up list can be displayed. This method is intended to be
	 * used with AJAX.
	 * 
	 * @param query What has been written so far in the city field.
	 * 
	 * @return The list of City objects whose names match the specified query.
	 */
	public List<City> suggestCities(String query) {
		// Checks if something was indeed typed in the field.
		if (query.length() > 0) {
			// Uses the DAO to find the query and returns.
			List<City> cities = cityDAO.findByName(query);
			logger.log(Level.FINE, "Suggestion for cities beginning with \"{0}\" returned {1} results", new Object[] { query, cities.size() });
			return cities;
		}
		return null;
	}
	
	/**
	 * Analyzes what has been written so far in the institution field and, if not empty, looks for institutions that start
	 * with the given name or acronym and returns them in a list, so a dynamic pop-up list can be displayed. This method
	 * is intended to be used with AJAX.
	 * 
	 * @param event
	 *          The AJAX event.
	 * @return The list of institutions to be displayed in the drop-down auto-completion field.
	 */
	public List<Institution> suggestInstitutions(Object event) {
		if (event != null) {
			String param = event.toString();
			if (param.length() > 0) {
				List<Institution> suggestions = institutionDAO.findByNameOrAcronym(param);
				logger.log(Level.FINE, "Suggestion for institutions with name or acronym beginning with \"{0}\" returned {1} results", new Object[] { param, suggestions.size() });
				return suggestions;
			}
		}
		return null;
	}

	/** Getter for telephones. */
	public List<Telephone> getTelephones() {
		return telephones;
	}

	/** Setter for telephones. */
	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	/** Getter for telephone. */
	public Telephone getTelephone() {
		return telephone;
	}

	/** Setter for telephone. */
	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
		logger.log(Level.FINEST, "Telephone \"{0}\" has been selected", telephone);
	}

	/** 
	 * Getter for the type attribute of the telephone, created because PrimeFaces p:selectOneMenu complains of the EL 
	 * #{manageSpiritistsAction.telephone.type} if telephone is null. This method checks for nulls.
	 * 
	 * See: http://forum.primefaces.org/viewtopic.php?f=3&t=14128&p=43494#p43494 
	 */
	public ContactType getTelephoneType() {
		return (telephone == null) ? null : telephone.getType();
	}
	
	/** 
	 * Setter for the type attribute of the telephone, created because PrimeFaces p:selectOneMenu complains of the EL 
	 * #{manageSpiritistsAction.telephone.type} if telephone is null. This method checks for nulls.
	 * 
	 * See: http://forum.primefaces.org/viewtopic.php?f=3&t=14128&p=43494#p43494 
	 */
	public void setTelephoneType(ContactType type) {
		if (telephone != null) telephone.setType(type);
	}

	/**
	 * Creates a new and empty telephone so the telephone fields can be filled. 
	 * 
	 * This method is intended to be used with AJAX, through the PrimeFaces Collector component.
	 */
	public void newTelephone() {
		telephone = new Telephone();
		logger.log(Level.FINEST, "Empty telephone created as selected telephone");
	}
	
	/**
	 * TODO: document this method.
	 */
	public void resetTelephone() {
		telephone = null;
		logger.log(Level.FINEST, "Telephone has been reset -- no telephone is selected");
	}

	/** Getter for attendances. */
	public List<Attendance> getAttendances() {
		return attendances;
	}

	/** Setter for attendances. */
	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	/** Getter for attendance. */
	public Attendance getAttendance() {
		return attendance;
	}

	/** Setter for attendance. */
	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
		logger.log(Level.FINEST, "Attendance \"{0}\" has been selected", attendance);
	}

	/**
	 * Creates a new and empty attendance so the attendance fields can be filled. 
	 * 
	 * This method is intended to be used with AJAX, through the PrimeFaces Collector component.
	 */
	public void newAttendance() {
		attendance = new Attendance();
		logger.log(Level.FINEST, "Empty attendance created as selected attendance");
	}
	
	/**
	 * TODO: document this method.
	 */
	public void resetAttendance() {
		attendance = null;
		logger.log(Level.FINEST, "Attendance has been reset -- no attendance is selected");
	}
}
