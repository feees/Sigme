package br.org.feees.sigme.core.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import br.org.feees.sigme.core.domain.ManagementType;
import br.org.feees.sigme.core.persistence.ManagementTypeDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudException;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
@TransactionAttribute
public class ManagementTypeServiceBean extends CrudServiceBean<ManagementType> implements ManagementTypeService {

	private static final long serialVersionUID = 3790066241919286847L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementTypeServiceBean.class.getCanonicalName());

	@EJB
	private ManagementTypeDAO managementTypeDAO;

	@Override
	public BaseDAO<ManagementType> getDAO() {
		return this.managementTypeDAO;
	}

	@Override
	protected ManagementType createNewEntity() {
		return new ManagementType();
	}

	@Override
	public void validateDelete(ManagementType entity) throws CrudException {
		CrudException crudException = null;
		ManagementType managementType = managementTypeDAO.retrieveById(entity.getId());

		if (managementType.getInternoSistema()) {
			crudException = addValidationError(crudException, "Não pode excluir registros internos do sistema", "name");
			logger.log(Level.FINE, "");
		}
		
	
		if (crudException != null)
			throw crudException;
	}
}
