package br.org.feees.sigme.core.persistence;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.feees.sigme.core.domain.ManagementType;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;

@Stateless
public class ManagementTypeJPADAO extends BaseJPADAO<ManagementType> implements ManagementTypeDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementTypeJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Class<ManagementType> getDomainClass() {
		return ManagementType.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
