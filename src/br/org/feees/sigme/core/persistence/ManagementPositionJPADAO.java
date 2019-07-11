package br.org.feees.sigme.core.persistence;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.feees.sigme.core.domain.ManagementRole;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;

@Stateless
public class ManagementPositionJPADAO extends BaseJPADAO<ManagementRole> implements ManagementPositionDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementPositionJPADAO.class.getCanonicalName());
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Override
	public Class<ManagementRole> getDomainClass() {
		return ManagementRole.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
