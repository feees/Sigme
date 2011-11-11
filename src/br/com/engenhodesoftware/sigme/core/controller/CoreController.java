package br.com.engenhodesoftware.sigme.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.core.application.CoreInformation;
import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionTypeDAO;
import br.com.engenhodesoftware.util.ejb3.controller.PersistentObjectConverterFromId;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.ContactType;
import br.com.engenhodesoftware.util.people.persistence.CityDAO;
import br.com.engenhodesoftware.util.people.persistence.ContactTypeDAO;

/**
 * Application-scoped bean that centralizes controller information for the core package. This bean differs from the
 * singleton EJB CoreInformation by containing data relative to the presentation layer (controller and view, i.e., the
 * web).
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
}
