package br.com.engenhodesoftware.sigme.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.core.application.CoreInformation;
import br.com.engenhodesoftware.sigme.core.controller.converters.CityJSFConverterFromNameAndStateAcronym;
import br.com.engenhodesoftware.sigme.core.controller.converters.ContactTypeConverterFromId;
import br.com.engenhodesoftware.sigme.core.controller.converters.InstitutionTypeConverterFromId;
import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionTypeDAO;
import br.com.engenhodesoftware.util.people.domain.ContactType;
import br.com.engenhodesoftware.util.people.persistence.CityDAO;
import br.com.engenhodesoftware.util.people.persistence.ContactTypeDAO;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
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
	private InstitutionTypeConverterFromId institutionTypeConverter;

	/** JSF Converter for City objects. */
	private CityJSFConverterFromNameAndStateAcronym cityConverter;

	/** JSF Converter for ContactType objects. */
	private ContactTypeConverterFromId contactTypeConverter;

	/** Getter for institutionTypes. */
	public List<InstitutionType> getInstitutionTypes() {
		// Lazily initialize the institution types list.
		if (institutionTypes == null) {
			institutionTypes = new ArrayList<InstitutionType>(coreInformation.getInstitutionTypes());
			logger.log(Level.INFO, "Initializing institution types list with {0} items", institutionTypes.size());
		}

		return institutionTypes;
	}
	
	public List<SelectItem> getInstitutionTypesAsSelectItems() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (InstitutionType type : getInstitutionTypes()) items.add(new SelectItem(type.getId(), type.getType()));
		return items;
	}

	/** Getter for contactTypes. */
	public List<ContactType> getContactTypes() {
		// Lazily initialize the contact types list.
		if (contactTypes == null) {
			contactTypes = new ArrayList<ContactType>(coreInformation.getContactTypes());
			logger.log(Level.INFO, "Initializing contact types list with {0} items", contactTypes.size());
		}

		return contactTypes;
	}

	/** Getter for institutionTypeConverter. */
	public InstitutionTypeConverterFromId getInstitutionTypeConverter() {
		// Lazily create the converter.
		if (institutionTypeConverter == null)
			institutionTypeConverter = new InstitutionTypeConverterFromId(institutionTypeDAO);
		return institutionTypeConverter;
	}

	/** Getter for cityConverter. */
	public Converter getCityConverter() {
		// Lazily create the converter.
		if (cityConverter == null)
			cityConverter = new CityJSFConverterFromNameAndStateAcronym(cityDAO);
		return cityConverter;
	}

	/** Getter for contactTypeConverter */
	public ContactTypeConverterFromId getContactTypeConverter() {
		// Lazily create the converter.
		if (contactTypeConverter == null)
			contactTypeConverter = new ContactTypeConverterFromId(contactTypeDAO);
		return contactTypeConverter;
	}
}
