package br.org.feees.sigme.core.application;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import br.org.feees.sigme.core.domain.Institution;
import br.org.feees.sigme.core.domain.Management;
import br.org.feees.sigme.core.persistence.InstitutionDAO;
import br.org.feees.sigme.core.persistence.ManagementDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudException;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Stateless
@TransactionAttribute
public class ManagementServiceBean extends CrudServiceBean<Management> implements ManagementService {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementServiceBean.class.getCanonicalName());

	@EJB
	private ManagementDAO managementDAO;

	@EJB
	private InstitutionDAO institutionDAO;
	
	@Override
	public void validateCreate(Management entity) throws CrudException {
		Management other = null;
		try {
			other = managementDAO.retrieveByInstitutionId(entity.getInstitution().getId());
			
		} catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Institution {0} didn't have an older management",
					entity.getInstitution().getName());
			
		} catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Institution \"" + entity.getInstitution().getName()
					+ "already have a management. ");
			//crudException = addValidationError(crudException, crudExceptionMessage, "badgeName",
				//	"institution.error.multipleInstancesError");
		}
		

		if (other != null) {
			/*
			 * Deleta a gestão antiga, se a mesma existir
			 */
			managementDAO.delete(other);
		}

		/*
		 * Retorna a instituição da entidade e adiciona a gestão
		 * atual ao histórico
		 */
		Institution i = institutionDAO.retrieveById(entity.getInstitution().getId());
		if (i.getManagementHistory() == null) {
			i.setManagementHistory(new ArrayList<Management>());
		}
				
		/*
		 * Adiciona a nova gestão no histórico
		 */
		i.getManagementHistory().add(entity);
				
		/*
		 * Salva a nova gestão
		 */
		managementDAO.save(entity);
		
		/*
		 * Atualiza a instituição
		 */
		institutionDAO.merge(i);

	}

	@Override
	public void validateUpdate(Management entity) throws CrudException {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateDelete(Management entity) throws CrudException {
		// TODO Auto-generated method stub

	}

	@Override
	public BaseDAO<Management> getDAO() {
		return managementDAO;
	}

	@Override
	protected Management createNewEntity() {
		return new Management();
	}

	@Override
	public CrudException noInstitution() {
		CrudException crudException = null;
		String crudExceptionMessage = "Cannot create a management position because haven't any selected institution";
		crudException = addValidationError(crudException, crudExceptionMessage, "email", "manageManagementPosition.error.noInstitution");
		return null;
	}

}
