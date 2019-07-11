package br.org.feees.sigme.core.application;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import br.org.feees.sigme.core.domain.ManagementRole;
import br.org.feees.sigme.core.persistence.ManagementPositionDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Stateless
@TransactionAttribute
public class ManagementPositionServiceBean extends CrudServiceBean<ManagementRole>
		implements ManagementPositionService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementRole.class.getCanonicalName());

	@EJB
	private ManagementPositionDAO managementPositionDAO;
	
	@Override
	public BaseDAO<ManagementRole> getDAO() {
		return managementPositionDAO;
	}

	@Override
	protected ManagementRole createNewEntity() {
		return new ManagementRole();
	}

}
