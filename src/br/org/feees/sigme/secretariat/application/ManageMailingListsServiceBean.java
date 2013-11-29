package br.org.feees.sigme.secretariat.application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.org.feees.sigme.secretariat.domain.MailingList;
import br.org.feees.sigme.secretariat.persistence.MailingListDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudException;
import br.ufes.inf.nemo.util.ejb3.application.CrudOperation;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing the "Manage Mailing Lists" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.application.ManageMailingListsService
 */
@Stateless
@RolesAllowed({ "USER" })
public class ManageMailingListsServiceBean extends CrudServiceBean<MailingList> implements ManageMailingListsService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageMailingListsServiceBean.class.getCanonicalName());

	/** The DAO for MailingList objects. */
	@EJB
	private MailingListDAO mailingListDAO;

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#createNewEntity() */
	@Override
	protected MailingList createNewEntity() {
		return new MailingList();
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudService#getDAO() */
	@Override
	public BaseDAO<MailingList> getDAO() {
		return mailingListDAO;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#authorize() */
	@Override
	public void authorize() {
		// Overridden to implement authorization. @RolesAllowed is placed in the whole class.
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#validateCreate(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateCreate(MailingList entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The mailing list \"" + entity.getName() + "\" cannot be created due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot have two institutions with the same name.
		try {
			MailingList anotherEntity = mailingListDAO.retrieveByName(entity.getName());
			if (anotherEntity != null) {
				logger.log(Level.INFO, "Creation of mailing list \"{0}\" violates validation rule 1: mailing list with id {1} has same name", new Object[] { entity.getName(), anotherEntity.getId() });
				crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageMailingLists.error.repeatedName");
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other mailing list with the same name: {0}", entity.getName());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of mailing list with name \"" + entity.getName() + "\" threw an exception: a query for a list with this name returned multiple results!", e);
			crudException = addValidationError(crudException, crudExceptionMessage, "email", "manageMailingLists.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#validateUpdate(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateUpdate(MailingList entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "The institution \"" + entity.getName() + "\" cannot be updated due to validation errors.";

		// Validates business rules.
		// Rule 1: cannot have two institutions with the same name.
		try {
			MailingList anotherEntity = mailingListDAO.retrieveByName(entity.getName());
			if ((anotherEntity != null) && (!anotherEntity.getId().equals(entity.getId()))) {
				logger.log(Level.INFO, "Update of mailing list \"{0}\" (id {1}) violates validation rule 1: mailing list with id {2} has same name", new Object[] { entity.getName(), entity.getId(), anotherEntity.getId() });
				crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageMailingLists.error.repeatedName");
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other mailing list with the same name: {0}", entity.getName());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of mailing list with name \"" + entity.getName() + "\" threw an exception: a query for a list with this name returned multiple results!", e);
			crudException = addValidationError(crudException, crudExceptionMessage, "email", "manageMailingLists.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#log(br.ufes.inf.nemo.util.ejb3.application.CrudOperation, br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	protected void log(CrudOperation operation, MailingList entity) {
		switch (operation) {
		case CREATE:
		case UPDATE:
			logger.log(Level.FINER, "Storing mailing list: \"{0}\"...", entity.getName());
			break;
		case RETRIEVE:
			logger.log(Level.FINE, "Loaded mailing list with id {0} ({1})...", new Object[] { entity.getId(), entity.getName() });
			break;
		case DELETE:
			logger.log(Level.FINE, "Deleted mailing list with id {0} ({1})...", new Object[] { entity.getId(), entity.getName() });
			break;
		default:
			logger.log(Level.WARNING, "CRUD bean received unknown operation logging: {0}", new Object[] { operation });
		}
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#log(br.ufes.inf.nemo.util.ejb3.application.CrudOperation, java.util.List, int[]) */
	@Override
	protected void log(CrudOperation operation, List<MailingList> entities, int ... interval) {
		switch (operation) {
		case LIST:
			logger.log(Level.FINE, "Retrieved mailing list in interval [{0}, {1}): {2} list(s) loaded.", new Object[] { interval[0], interval[1], entities.size() });
			break;
		default:
			logger.log(Level.WARNING, "CRUD bean received unknown operation logging: {0}", new Object[] { operation });
		}
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudService#fetchLazy(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	public MailingList fetchLazy(MailingList entity) {
		// Loads the addresses collection, which is lazy.
		logger.log(Level.FINER, "Fecthing lazy attributes for mailing list \"{0}\"", entity);
		entity = getDAO().merge(entity);
		entity.getAddressees().size();
		return entity;
	}
}
