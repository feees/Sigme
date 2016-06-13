package br.org.feees.sigme.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.feees.sigme.people.domain.City;
import org.feees.sigme.people.domain.ContactType;
import org.feees.sigme.people.persistence.CityDAO;
import org.feees.sigme.people.persistence.ContactTypeDAO;

import br.org.feees.sigme.core.application.CoreInformation;
import br.org.feees.sigme.core.domain.Attendance;
import br.org.feees.sigme.core.domain.Institution;
import br.org.feees.sigme.core.domain.InstitutionType;
import br.org.feees.sigme.core.domain.Management;
import br.org.feees.sigme.core.domain.ManagementType;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.persistence.AttendanceDAO;
import br.org.feees.sigme.core.persistence.InstitutionDAO;
import br.org.feees.sigme.core.persistence.InstitutionTypeDAO;
import br.org.feees.sigme.core.persistence.ManagementDAO;
import br.org.feees.sigme.core.persistence.ManagementTypeDAO;
import br.org.feees.sigme.core.persistence.SpiritistDAO;
import br.org.feees.sigme.event.domain.Event;
import br.org.feees.sigme.event.persistence.EventDAO;
import br.ufes.inf.nemo.util.ejb3.controller.PersistentObjectConverterFromId;

/**
 * Application-scoped bean that centralizes controller information for the core
 * package. This bean differs from the singleton EJB CoreInformation by
 * containing data relative to the presentation layer (controller and view,
 * i.e., the web).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@ApplicationScoped
public class CoreController implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(CoreController.class.getCanonicalName());

	/** The DAO for ContactType objects. */
	@EJB
	private ContactTypeDAO contactTypeDAO;

	/** The DAO for City objects. */
	@EJB
	private CityDAO cityDAO;

	/** The DAO for InstitutionType objects. */
	@EJB
	private InstitutionTypeDAO institutionTypeDAO;

	@EJB
	private InstitutionDAO institutionDAO;
	private PersistentObjectConverterFromId<Institution> institutionConverter;

	@Inject
	private Institution institution;

	@EJB
	private SpiritistDAO spiritistDAO;
	private PersistentObjectConverterFromId<Spiritist> spiritistConverter;

	@EJB
	private EventDAO eventDAO;
	private PersistentObjectConverterFromId<Event> eventConverter;

	@EJB
	private ManagementDAO managementDAO;
	private PersistentObjectConverterFromId<Management> managementConverter;

	@Inject
	private ManagementTypeDAO managementTypeDAO;
	private PersistentObjectConverterFromId<ManagementType> managementTypeConverter;

	@EJB
	private AttendanceDAO attendanceDAO;

	private List<Attendance> attendance;
	
	private List<ManagementType> managementTypes;

	/** Information on the whole application. */
	@EJB
	private CoreInformation coreInformation;

	/** List of institution types to be used in forms. */
	private List<InstitutionType> institutionTypes;

	/** List of contact types to be used in forms. */
	private List<ContactType> contactTypes;

	/** JSF Converter for InstitutionType objects. */
	private PersistentObjectConverterFromId<InstitutionType> institutionTypeConverter;

	/** JSF Converter for City objects. */
	private PersistentObjectConverterFromId<City> cityConverter;

	/** JSF Converter for ContactType objects. */
	private PersistentObjectConverterFromId<ContactType> contactTypeConverter;

	public List<Spiritist> getSpiritistByInstitution() {
		Institution a1 = (Institution) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("i");
		
		if (institution == null) {
			return new ArrayList<Spiritist>();
		} else {

			attendance = attendanceDAO.retrieveByInstitution(institution.getId());

			List<Spiritist> spiritist = null;
			spiritist = new ArrayList<Spiritist>();

			if (!attendance.isEmpty()) {
				for (Attendance a : attendance) {
					spiritist.add(a.getSpiritist());
				}
			}

			return spiritist;
		}
	}
	
	public List<ManagementType> getManagementTypes() {
		if (managementTypes == null) {
			managementTypes = new ArrayList<ManagementType>(coreInformation.getManagementTypes());
			logger.log(Level.FINEST, "Management types list initialized with {0} items", managementTypes.size());
		}
		
		return managementTypes;
	}
	
	/** Getter for contactTypes. */
	public List<ContactType> getContactTypes() {
		// Lazily initialize the contact types list.
		if (contactTypes == null) {
			contactTypes = new ArrayList<ContactType>(coreInformation.getContactTypes());
			logger.log(Level.FINEST, "Contact types list initialized with {0} items", contactTypes.size());
		}

		return contactTypes;
	}

	/** Getter for institutionTypes. */
	public List<InstitutionType> getInstitutionTypes() {
		// Lazily initialize the institution types list.
		if (institutionTypes == null) {
			institutionTypes = new ArrayList<InstitutionType>(coreInformation.getInstitutionTypes());
			logger.log(Level.FINEST, "Institution types list initialized with {0} items", institutionTypes.size());
		}

		return institutionTypes;
	}

	/** Getter for cityConverter. */
	public Converter getCityConverter() {
		// Lazily create the converter.
		if (cityConverter == null) {
			logger.log(Level.FINEST, "Creating a city converter...");
			cityConverter = new PersistentObjectConverterFromId<City>(cityDAO);
		}
		return cityConverter;
	}

	/** Getter for contactTypeConverter */
	public Converter getContactTypeConverter() {
		// Lazily create the converter.
		if (contactTypeConverter == null) {
			logger.log(Level.FINEST, "Creating a contact type converter...");
			contactTypeConverter = new PersistentObjectConverterFromId<ContactType>(contactTypeDAO);
		}
		return contactTypeConverter;
	}

	/** Getter for institutionTypeConverter. */
	public Converter getInstitutionTypeConverter() {
		// Lazily create the converter.
		if (institutionTypeConverter == null) {
			logger.log(Level.FINEST, "Creating an institution type converter...");
			institutionTypeConverter = new PersistentObjectConverterFromId<InstitutionType>(institutionTypeDAO);
		}
		return institutionTypeConverter;
	}

	public PersistentObjectConverterFromId<Institution> getInstitutionConverter() {
		if (institutionConverter == null) {
			logger.log(Level.FINEST, "Creating an institution type converter...");
			institutionConverter = new PersistentObjectConverterFromId<Institution>(institutionDAO);
		}
		return institutionConverter;
	}

	public PersistentObjectConverterFromId<Spiritist> getSpiritistConverter() {
		Map<String, Object> params = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();

		Institution i = (Institution) params.get("i");
		institution = i;

		if (spiritistConverter == null) {
			logger.log(Level.FINEST, "Creating an institution type converter...");
			spiritistConverter = new PersistentObjectConverterFromId<Spiritist>(spiritistDAO);
		}
		return spiritistConverter;
	}

	public PersistentObjectConverterFromId<Event> getEventConverter() {
		if (eventConverter == null) {
			logger.log(Level.FINEST, "Creating an event type converter...");
			eventConverter = new PersistentObjectConverterFromId<Event>(eventDAO);
		}
		return eventConverter;
	}

	public PersistentObjectConverterFromId<Management> getManagementConverter() {
		if (managementConverter == null) {
			logger.log(Level.FINEST, "Creating an management converter...");
			managementConverter = new PersistentObjectConverterFromId<Management>(managementDAO);
		}
		return managementConverter;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public PersistentObjectConverterFromId<ManagementType> getManagementTypeConverter() {
		if (managementTypeConverter == null) {
			logger.log(Level.FINEST, "Creating an managements type converter...");
			managementTypeConverter = new PersistentObjectConverterFromId<ManagementType>(managementTypeDAO);
		}
		return managementTypeConverter;
	}



}
