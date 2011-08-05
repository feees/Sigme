package br.com.engenhodesoftware.sigme.core.application;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO;
import br.com.engenhodesoftware.util.ejb3.application.CrudException;
import br.com.engenhodesoftware.util.ejb3.application.CrudOperation;
import br.com.engenhodesoftware.util.ejb3.application.CrudService;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing the "Manage Institutions" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.application.ManageInstitutionsService
 */
@Stateless
@RolesAllowed({ "USER" })
public class ManageInstitutionsServiceBean extends CrudService<Institution> implements ManageInstitutionsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageInstitutionsServiceBean.class.getCanonicalName());

	/** The DAO for Institution objects. */
	@EJB
	private InstitutionDAO institutionDAO;

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#createNewEntity() */
	@Override
	protected Institution createNewEntity() {
		return new Institution();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#getDAO() */
	@Override
	protected BaseDAO<Institution> getDAO() {
		return institutionDAO;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#authorize() */
	@Override
	public void authorize() {
		// Overridden to implement authorization. @RolesAllowed is placed in the whole class.
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#validateCreate(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateCreate(Institution entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The institution \"" + entity.getName() + "\" cannot be created due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot have two institutions with the same name.
		try {
			Institution anotherEntity = institutionDAO.retrieveByName(entity.getName());
			if (anotherEntity != null) {
				logger.log(Level.INFO, "Update of institution \"{0}\" violates validation rule: institution with id {1} has same name", new Object[] { entity.getName(), anotherEntity.getId() });
				crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageInstitutions.error.repeatedName", anotherEntity.getLastUpdateDate());
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other institution with the same name: {0}", entity.getName());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of institution with name \"" + entity.getName() + "\" threw an exception: a query for institution with this name returned multiple results!", e);
			crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageInstitutions.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#validateUpdate(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateUpdate(Institution entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The institution \"" + entity.getName() + "\" cannot be updated due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot have two institutions with the same name.
		try {
			Institution anotherEntity = institutionDAO.retrieveByName(entity.getName());
			if ((anotherEntity != null) && (!anotherEntity.getId().equals(entity.getId()))) {
				logger.log(Level.INFO, "Update of institution \"{0}\" (id {1}) violates validation rule: institution with id {2} has same name", new Object[] { entity.getName(), entity.getId(), anotherEntity.getId() });
				crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageInstitutions.error.repeatedName", anotherEntity.getLastUpdateDate());
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other institution with the same name: {0}", entity.getName());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of institution with name \"" + entity.getName() + "\" threw an exception: a query for institution with this name returned multiple results!", e);
			crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageInstitutions.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#validate(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject, br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	protected Institution validate(Institution newEntity, Institution oldEntity) {
		// Sets the current date/time as last update date of the institution.
		newEntity.setLastUpdateDate(new Date(System.currentTimeMillis()));

		return super.validate(newEntity, oldEntity);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#log(br.com.engenhodesoftware.util.ejb3.application.CrudOperation, br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	protected void log(CrudOperation operation, Institution entity) {
		switch (operation) {
		case CREATE:
		case UPDATE:
			logger.log(Level.FINE, "Storing institution: {0} / {1} ({2})", new Object[] { entity.getName(), entity.getAcronym(), entity.getType() });
			logger.log(Level.FINE, "Persisting data:\n\t- Address = {0}\n\t- Regional = {4}\n\t- Web Page = {1}\n\t- Email = {2}\n\t- Telephones = {3}", new Object[] { entity.getAddress(), entity.getWebPage(), entity.getEmail(), entity.getTelephones(), entity.getRegional() });
			break;
		case RETRIEVE:
			logger.log(Level.FINE, "Loading institution with id {0}: {1}", new Object[] { entity.getId(), entity.getName() });
			break;
		case DELETE:
			logger.log(Level.FINE, "Deleting institution with id {0}: {1}", new Object[] { entity.getId(), entity.getName() });
			break;
		}
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#log(br.com.engenhodesoftware.util.ejb3.application.CrudOperation, java.util.List, int[]) */
	@Override
	protected void log(CrudOperation operation, List<Institution> entities, int ... interval) {
		switch (operation) {
		case LIST:
			logger.log(Level.FINE, "Listing institutions in interval [{0}, {1}): {2} institution(s) loaded.", new Object[] { interval[0], interval[1], entities.size() });
		}
	}
}
