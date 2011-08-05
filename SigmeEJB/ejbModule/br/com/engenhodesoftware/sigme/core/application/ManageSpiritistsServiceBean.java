package br.com.engenhodesoftware.sigme.core.application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.sigme.core.persistence.SpiritistDAO;
import br.com.engenhodesoftware.util.ejb3.application.CrudException;
import br.com.engenhodesoftware.util.ejb3.application.CrudOperation;
import br.com.engenhodesoftware.util.ejb3.application.CrudService;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing the "Manage Spiritist" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.application.ManageSpiritistsService
 */
@Stateless
@RolesAllowed({ "USER" })
public class ManageSpiritistsServiceBean extends CrudService<Spiritist> implements ManageSpiritistsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageSpiritistsServiceBean.class.getCanonicalName());

	/** The DAO for Spiritist objects. */
	@EJB
	private SpiritistDAO spiritistDAO;

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#createNewEntity() */
	@Override
	protected Spiritist createNewEntity() {
		return new Spiritist();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#getDAO() */
	@Override
	protected BaseDAO<Spiritist> getDAO() {
		return spiritistDAO;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#validateCreate(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateCreate(Spiritist entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The spiritist \"" + entity.getEmail() + "\" cannot be created due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot have two spiritists with the same email.
		try {
			Spiritist anotherEntity = spiritistDAO.retrieveByEmail(entity.getEmail());
			if (anotherEntity != null) {
				logger.log(Level.INFO, "Update of spiritist \"{0}\" violates validation rule: spiritist with id {1} has same email", new Object[] { entity.getEmail(), anotherEntity.getId() });
				crudException = addValidationError(crudException, crudExceptionMessage, "email", "manageSpiritists.error.repeatedEmail", anotherEntity.getLastUpdateDate());
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other spiritist with the same email: {0}", entity.getEmail());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of spiritist with email \"" + entity.getEmail() + "\" threw an exception: a query for spiritists with this email returned multiple results!", e);
			crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageSpiritists.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#validateUpdate(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateUpdate(Spiritist entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The spiritist \"" + entity.getEmail() + "\" cannot be updated due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot have two spiritists with the same name.
		try {
			Spiritist anotherEntity = spiritistDAO.retrieveByEmail(entity.getEmail());
			if ((anotherEntity != null) && (!anotherEntity.getId().equals(entity.getId()))) {
				logger.log(Level.INFO, "Update of spiritist \"{0}\" violates validation rule: spiritist with id {1} has same email", new Object[] { entity.getEmail(), anotherEntity.getId() });
				crudException = addValidationError(crudException, crudExceptionMessage, "email", "manageSpiritists.error.repeatedEmail", anotherEntity.getLastUpdateDate());
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other spiritist with the same email: {0}", entity.getEmail());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of spiritist with email \"" + entity.getEmail() + "\" threw an exception: a query for spiritists with this email returned multiple results!", e);
			crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageSpiritists.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#validateDelete(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateDelete(Spiritist valueObject) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The spiritist \"" + valueObject.getEmail() + "\" cannot be deleted because of validation errors.";

		// FIXME: o administrador é o primeiro usuário cadastrado por enquanto. Assim que o cadastro da federativa e
		// de modelos de gestão estiverem funcionando, o admin será o presidente ou alguém que tenha uma delegação
		// válida dada pelo presidente. Pensar se esta regra de validação deve realmente ser aplicada e pensar em uma
		// outra regra: pode excluir a si mesmo?

		// Validates business rules.
		// Rule 1: cannot delete the administrator.
		if (new Long(1).equals(valueObject.getId())) {
			logger.log(Level.INFO, "Deletion of spiritist \"{0}\" violates validation rule: cannot delete the administrator", valueObject.getEmail());
			crudException = addValidationError(crudException, crudExceptionMessage, "manageSpiritists.error.deleteAdmin");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#log(br.com.engenhodesoftware.util.ejb3.application.CrudOperation, br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	protected void log(CrudOperation operation, Spiritist entity) {
		switch (operation) {
		case CREATE:
		case UPDATE:
			logger.log(Level.FINE, "Storing spiritist: {1} ({0})", new Object[] { entity.getName(), entity.getEmail() });
			logger.log(Level.FINE, "Persisting data:\n\t- Birthdate = {0}\n\t- Gender = {1}\n\t- Short name = {2}\n\t- Address = {3}\n\t- Telephones = {4}\n\t- {5} attendances", new Object[] { entity.getBirthDate(), entity.getGender(), entity.getShortName(), entity.getAddress(), entity.getTelephones(), entity.getAttendances().size() });
			break;
		case RETRIEVE:
			logger.log(Level.FINE, "Loading spiritist with id {0}: {1}", new Object[] { entity.getId(), entity.getEmail() });
			break;
		case DELETE:
			logger.log(Level.FINE, "Deleting spiritist with id {0}: {1}", new Object[] { entity.getId(), entity.getEmail() });
			break;
		}
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.CrudService#log(br.com.engenhodesoftware.util.ejb3.application.CrudOperation, java.util.List, int[]) */
	@Override
	protected void log(CrudOperation operation, List<Spiritist> entities, int ... interval) {
		switch (operation) {
		case LIST:
			logger.log(Level.FINE, "Listing spiritists in interval [{0}, {1}): {2} spiritist(s) loaded.", new Object[] { interval[0], interval[1], entities.size() });
		}
	}
}
